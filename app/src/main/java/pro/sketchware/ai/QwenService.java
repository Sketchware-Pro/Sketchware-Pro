package pro.sketchware.ai;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QwenService implements AIService {
    // DashScope API Key
    private static final String DEFAULT_API_KEY = ""; // User must provide their own key via Settings 
    
    // OpenAI compatible endpoint for DashScope
    private static final String BASE_URL = "https://dashscope-intl.aliyuncs.com/compatible-mode/v1/chat/completions";
    // Default model - can be changed to qwen-max, qwen-turbo, etc.
    private static final String MODEL = "qwen-plus"; 
    
    private final OkHttpClient client;
    private final Gson gson;
    private final Handler mainHandler;
    private final android.content.Context context;

    public QwenService(android.content.Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
        this.gson = new Gson();
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    private String getApiKey() {
        android.content.SharedPreferences prefs = context.getSharedPreferences("ai_settings", android.content.Context.MODE_PRIVATE);
        return prefs.getString("api_key", DEFAULT_API_KEY);
    }

    private String systemContext = "";

    @Override
    public void setSystemContext(String context) {
        this.systemContext = context;
    }

    @Override
    public void chat(String prompt, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        // Fallback or single-turn implementation
        chatWithHistory(java.util.Collections.singletonList(new pro.sketchware.ai.ui.adapter.ChatMessage(prompt, true)), onSuccess, onError);
    }

    @Override
    public void chatWithHistory(java.util.List<pro.sketchware.ai.ui.adapter.ChatMessage> history, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        String baseSystemPrompt = "You are an expert Android Developer and Sketchware Pro Specialist. " +
                "You are deeply familiar with Sketchware Pro's internal structure, source code generation, and block-based logic.\n\n" +
                "**Core Knowledge:**\n" +
                "- **Environment:** Sketchware Pro projects compile to standard Android Java/Kotlin projects. You are working within the `app/src/main` source set context.\n" +
                "- **File Structure:** standard Activities (`MainActivity.java`), resources (`res/layout`, `res/values`), and the `AndroidManifest.xml`.\n" +
                "- **Common Libraries:** Projects often use Gson, Glide/Picasso, OkHttp, and Material Components. Assume these are available unless told otherwise.\n" +
                "- **Ids:** IDs typically follow `R.id.viewName`.\n\n" +
                "**Capabilities:**\n" +
                "1. **Chat & Context:** You MUST remember the conversation history. If the user says 'fix it' or 'add this', refer to the code or errors discussed in previous messages.\n" +
                "2. **Code Generation:** When asked for code, provide full, compile-ready Java code snippets. Use markdown (e.g., ```java ... ```).\n" +
                "3. **Actions:**\n" +
                "   - To create a project, return JSON: {\"action\": \"create_project\", \"name\": \"...\", \"package\": \"...\"}\n" +
                "   - To fix a specific compile error, return JSON: {\"action\": \"fix_error\", \"file\": \"<RelativePath>\", \"description\": \"...\", \"target\": \"<ExactExistingCode>\", \"replacement\": \"<NewCode>\"}\n\n" +
                "**Behavioral Rules:**\n" +
                "- Be concise but helpful.\n" +
                "- If you see an error log, analyze it deeply. Look for 'cannot find symbol', 'syntax error', etc., and propose a concrete `fix_error` action if you are sure.\n" +
                "- Use the `CODE_SNIPPET` format (Backticks) for all code blocks.\n" +
                "- Maintain a professional, 'Deep Understanding' persona.";
        
        String finalSystemPrompt = baseSystemPrompt;
        if (!systemContext.isEmpty()) {
            finalSystemPrompt += " " + systemContext;
        }

        JsonObject json = new JsonObject();
        json.addProperty("model", MODEL);
        json.addProperty("stream", false);

        JsonArray messages = new JsonArray();

        // Add System Prompt
        JsonObject systemMsg = new JsonObject();
        systemMsg.addProperty("role", "system");
        systemMsg.addProperty("content", finalSystemPrompt);
        messages.add(systemMsg);

        // Add User History
        for (pro.sketchware.ai.ui.adapter.ChatMessage msg : history) {
            // Skip thinking messages or internal UI updates if any, filtering valid types
            if (msg.isThinking()) continue;

            JsonObject msgJson = new JsonObject();
            msgJson.addProperty("role", msg.isUser() ? "user" : "assistant");
            
            String content = msg.getContent();
            String imagePath = msg.getImagePath();
            
            if (imagePath != null && msg.isUser()) {
                // Multimodal Request
                 JsonArray contentArray = new JsonArray();
                 
                 // Add Text if present
                 if (content != null && !content.isEmpty()) {
                     JsonObject textPart = new JsonObject();
                     textPart.addProperty("type", "text");
                     textPart.addProperty("text", content);
                     contentArray.add(textPart);
                 }
                 
                 // Add Image
                 JsonObject imagePart = new JsonObject();
                 imagePart.addProperty("type", "image_url");
                 JsonObject urlObj = new JsonObject();
                 
                 String base64 = msg.getImageBase64();
                 if (base64 != null) {
                     urlObj.addProperty("url", base64);
                 } else {
                     urlObj.addProperty("url", imagePath); // Fallback
                 }
                 
                 imagePart.add("image_url", urlObj);
                 contentArray.add(imagePart);
                 
                 msgJson.add("content", contentArray);
            } else {
                 // Text Only
                 if (content == null) content = "";
                 msgJson.addProperty("content", content);
            }
            messages.add(msgJson);
        }

        json.add("messages", messages);
        
        sendRequest(json, onSuccess, onError);
    }

    // Extracted method to avoid duplication between generic chat and block gen
    private void sendRequest(JsonObject json, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        String apiKey = getApiKey();
        if (apiKey.isEmpty()) {
            mainHandler.post(() -> onError.accept(new IllegalStateException("API Key is missing. Please set it in Settings.")));
            return;
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + getApiKey().trim())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> onError.accept(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    mainHandler.post(() -> onError.accept(new IOException("API Error " + response.code() + ": " + errorBody)));
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    JsonObject responseJson = gson.fromJson(responseBody, JsonObject.class);
                    
                    String content = responseJson.getAsJsonArray("choices")
                            .get(0).getAsJsonObject()
                            .getAsJsonObject("message")
                            .get("content").getAsString();
                    
                    content = extractJsonFromResponse(content);

                    final String finalContent = content;
                    mainHandler.post(() -> onSuccess.accept(finalContent));
                } catch (Exception e) {
                    mainHandler.post(() -> onError.accept(e));
                }
            }
        });
    }

    @Override
    public void generateBlock(String prompt, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        String systemPrompt = "You are an expert Sketchware Pro block generator. " +
                "Convert the user's natural language request into a valid Sketchware block JSON structure. " +
                "Only return the valid JSON, no markdown formatting. " +
                "Example format: [{\"id\":\"...\", \"spec\":\"...\", ...}]";
        processRequest(prompt, systemPrompt, onSuccess, onError);
    }

    public void generateCustomBlock(String prompt, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        String systemPrompt = "You are an expert Sketchware Pro Custom Block developer. " +
                "Convert the user's request into a JSON object defining a custom block. " +
                "Fields: name (string), spec (string, e.g. 'toast %s'), type (string: ' ' for regular, 's' string, 'b' boolean, 'd' number, 'c' if, 'e' if-else), " +
                "code (string, java code), color (hex string), imports (string, optional). " +
                "Return ONLY valid JSON. " +
                "Example: {\"name\": \"myToast\", \"spec\": \"toast %s\", \"type\": \" \", \"code\": \"Toast.makeText(getApplicationContext(), %1$s, Toast.LENGTH_SHORT).show();\", \"color\": \"#FF0000\"}";
        processRequest(prompt, systemPrompt, onSuccess, onError);
    }

    public void generateComponent(String prompt, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        String systemPrompt = "You are an expert Sketchware Pro Custom Component developer. " +
                "Convert the user's request into a JSON object defining a custom component. " +
                "Requirements:\n" +
                "- Return ONLY valid JSON. No markdown.\n" +
                "- Fields:\n" +
                "  - `name` (Required): Component name, e.g., 'MyComp'.\n" +
                "  - `id` (Required): Unique ID, e.g., 'my_comp'.\n" +
                "  - `icon` (Required): Material icon ID, e.g., 'ic_android'.\n" +
                "  - `description` (Optional): Brief description.\n" +
                "  - `varName` (Required): Variable name in class, e.g., 'myComp'.\n" +
                "  - `typeName` (Required): Type name shown in blocks, e.g., 'MyComp'.\n" +
                "  - `buildClass` (Required): Class to build/initialize, e.g., 'com.example.MyComp'.\n" +
                "  - `class` (Required): Variable type class, e.g., 'com.example.MyComp'.\n" +
                "  - `url` (Optional): Documentation URL.\n" +
                "  - `additionalVar` (Optional): Extra fields, use ### as placeholder.\n" +
                "  - `defineAdditionalVar` (Optional): Initialization for extra fields.\n" +
                "  - `imports` (Optional): Required imports.\n" +
                "Example JSON: {\"name\": \"Network\", \"id\": \"net\", \"icon\": \"ic_network_check\", \"description\": \"Network helper\", \"varName\": \"net\", \"typeName\": \"Network\", \"buildClass\": \"com.example.Network\", \"class\": \"com.example.Network\", \"imports\": \"import com.example.Network;\"}";
        processRequest(prompt, systemPrompt, onSuccess, onError);
    }

    private void processRequest(String userPrompt, String systemPrompt, Consumer<String> onSuccess, Consumer<Throwable> onError) {
        JsonObject json = new JsonObject();
        json.addProperty("model", MODEL);
        json.addProperty("stream", false);

        JsonArray messages = new JsonArray();

        if (systemPrompt != null) {
            JsonObject systemMsg = new JsonObject();
            systemMsg.addProperty("role", "system");
            systemMsg.addProperty("content", systemPrompt);
            messages.add(systemMsg);
        }

        JsonObject userMsg = new JsonObject();
        userMsg.addProperty("role", "user");
        userMsg.addProperty("content", userPrompt);
        messages.add(userMsg);

        json.add("messages", messages);

        String apiKey = getApiKey();
        if (apiKey.isEmpty()) {
            mainHandler.post(() -> onError.accept(new IllegalStateException("API Key is missing. Please set it in Settings.")));
            return;
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + getApiKey().trim())
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainHandler.post(() -> onError.accept(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    mainHandler.post(() -> onError.accept(new IOException("API Error " + response.code() + ": " + errorBody)));
                    return;
                }

                try {
                    String responseBody = response.body().string();
                    JsonObject responseJson = gson.fromJson(responseBody, JsonObject.class);
                    
                    // Parse OpenAI-compatible response structure
                    String content = responseJson.getAsJsonArray("choices")
                            .get(0).getAsJsonObject()
                            .getAsJsonObject("message")
                            .get("content").getAsString();
                    
                    // Clean markdown if present and extract JSON
                    content = extractJsonFromResponse(content);

                    final String finalContent = content;
                    mainHandler.post(() -> onSuccess.accept(finalContent));
                } catch (Exception e) {
                    mainHandler.post(() -> onError.accept(e));
                }
            }
        });
    }

    private String extractJsonFromResponse(String content) {
        String trimmed = content.trim();
        
        // Only attempt to extract JSON if we are expecting a JSON action (heuristic)
        // If it starts with { and ends with }, it's likely a JSON object
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
             return trimmed;
        }

        // Taking a closer look: The previous logic aggressively stripped ```json ... ``` wrapper.
        // This is good for "create_project" actions which return ONLY JSON.
        // BUT, for normal chat with code snippets, the AI might return:
        // "Here is code:\n```java\n...\n```"
        // The previous logic might be searching for the first ``` and returning ONLY the content inside, 
        // effectively stripping the explanation text AND the backticks!
        
        // Let's verify:
        // Old logic: int codeStart = content.indexOf("```"); ... return content.substring...
        // This returns ONLY what is INSIDE the backticks. 
        // So if AI says: "Hi, check this:\n```java\ncode\n```\nBye.", the old logic returns "java\ncode".
        // This completely breaks the ChatActivity's ability to see backticks and separate text from code!
        
        // FIX: Only extract inner content if the WHOLE message seems to be a SINGLE JSON block
        // intended for an action. 
        // How to detect?
        // If the content *contains* "action": "create_project" or "action": "fix_error", we probably want just the JSON.
        
        if (trimmed.contains("\"action\"") && (trimmed.contains("\"create_project\"") || trimmed.contains("\"fix_error\""))) {
             // Try to extract the JSON block
            int jsonStart = content.indexOf("```json");
            if (jsonStart != -1) {
                int jsonEnd = content.indexOf("```", jsonStart + 7);
                if (jsonEnd != -1) {
                    return content.substring(jsonStart + 7, jsonEnd).trim();
                }
            }
            // Fallback for generic block
             int codeStart = content.indexOf("```");
            if (codeStart != -1) {
                int codeEnd = content.indexOf("```", codeStart + 3);
                if (codeEnd != -1) {
                    return content.substring(codeStart + 3, codeEnd).trim();
                }
            }
            // Fallback brace matching
             int firstBrace = content.indexOf("{");
             int lastBrace = content.lastIndexOf("}");
             if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
                  return content.substring(firstBrace, lastBrace + 1).trim();
             }
        }

        // Otherwise (normal chat), return the FULL content, properly responding to the user.
        return content;
    }
}
