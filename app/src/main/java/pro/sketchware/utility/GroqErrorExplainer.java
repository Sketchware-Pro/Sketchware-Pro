package pro.sketchware.utility;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Utility class to explain errors using Groq API
 * This class provides a way to get AI-powered explanations for errors
 * that occur in Sketchware Pro
 */
public class GroqErrorExplainer {
    
    private static final String TAG = "GroqErrorExplainer";
    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String GROQ_MODEL = "llama-3.3-70b-versatile";
    
    private final Network network;
    private final Gson gson;
    private final String apiKey;
    private final Context context;
    
    public GroqErrorExplainer(String apiKey) {
        this.network = new Network();
        this.gson = new Gson();
        this.apiKey = apiKey;
        this.context = null;
    }
    
    /**
     * Construtor que obtém a API key automaticamente do contexto
     */
    public GroqErrorExplainer(Context context) {
        this.network = new Network();
        this.gson = new Gson();
        this.apiKey = GroqConfig.getApiKeySafely(context);
        this.context = context;
    }
    
    /**
     * Explain an error using Groq API
     * @param errorMessage The error message to explain
     * @param context Additional context about the error (optional)
     * @param callback Callback to handle the response
     */
    public void explainError(String errorMessage, String context, Consumer<String> callback) {
        if (apiKey == null || apiKey.isEmpty()) {
            callback.accept("API key not configured. Please set GROQ_API_KEY environment variable.");
            return;
        }
        
        String prompt = buildPrompt(errorMessage, context);
        JsonObject requestBody = buildRequestBody(prompt);
        
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + apiKey);
        
        network.post(GROQ_API_URL, headers, gson.toJson(requestBody), response -> {
            if (response != null) {
                try {
                    String explanation = parseResponse(response);
                    callback.accept(explanation);
                } catch (Exception e) {
                    Log.e(TAG, "Error parsing Groq response", e);
                    callback.accept("Failed to parse AI response: " + e.getMessage());
                }
            } else {
                callback.accept("Failed to get response from Groq API. Please check your internet connection.");
            }
        });
    }
    
    /**
     * Explain an error using Groq API (simplified version)
     * @param errorMessage The error message to explain
     * @param callback Callback to handle the response
     */
    public void explainError(String errorMessage, Consumer<String> callback) {
        explainError(errorMessage, null, callback);
    }
    
    /**
     * Build the prompt for the AI
     */
    private String buildPrompt(String errorMessage, String context) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a helpful programming assistant. A user is getting an error in their Android development project. ");
        prompt.append("Please explain what this error means in simple terms and provide steps to fix it. ");
        prompt.append("Keep your response concise but helpful. ");
        prompt.append("**IMPORTANT: Format your response using Markdown syntax for better readability.** ");
        
        // Use the language configured by the user
        String selectedLanguage = this.context != null ? GroqConfig.getResponseLanguage(this.context) : GroqConfig.DEFAULT_LANGUAGE;
        String languageInstruction = getLanguageInstruction(selectedLanguage);
        prompt.append(languageInstruction).append("\n\n");
        
        prompt.append("**Error message:**\n");
        prompt.append("```\n");
        prompt.append(errorMessage);
        prompt.append("\n```\n\n");
        
        if (context != null && !context.isEmpty()) {
            prompt.append("**Additional context:**\n");
            prompt.append(context);
            prompt.append("\n\n");
        }
        
        prompt.append("**Please provide your response in the following format:**\n\n");
        prompt.append("## 📋 **Explanation**\n");
        prompt.append("[Simple explanation of what the error means]\n\n");
        prompt.append("## 🔍 **Common Causes**\n");
        prompt.append("- [Cause 1]\n");
        prompt.append("- [Cause 2]\n");
        prompt.append("- [Cause 3]\n\n");
        prompt.append("## 🛠️ **How to Fix**\n");
        prompt.append("1. [Step 1]\n");
        prompt.append("2. [Step 2]\n");
        prompt.append("3. [Step 3]\n\n");
        prompt.append("## 💡 **Prevention Tips**\n");
        prompt.append("- [Tip 1]\n");
        prompt.append("- [Tip 2]\n");
        prompt.append("- [Tip 3]\n\n");
        prompt.append("**Use proper Markdown formatting with headers, lists, code blocks, and emphasis for better readability.**");
        
        return prompt.toString();
    }
    
    /**
     * Gets the language instruction based on user configuration
     * @param languageCode Configured language code
     * @return Instruction for AI to respond in the correct language
     */
    private String getLanguageInstruction(String languageCode) {
        switch (languageCode) {
            case GroqConfig.LANGUAGE_PORTUGUESE_BRAZIL:
                return "Respond in Portuguese (Brazil). Use clear and simple Portuguese.";
            case GroqConfig.LANGUAGE_ENGLISH:
                return "Respond in English. Use clear and simple English.";
            case GroqConfig.LANGUAGE_SPANISH:
                return "Respond in Spanish. Use clear and simple Spanish.";
            case GroqConfig.LANGUAGE_FRENCH:
                return "Respond in French. Use clear and simple French.";
            case GroqConfig.LANGUAGE_GERMAN:
                return "Respond in German. Use clear and simple German.";
            case GroqConfig.LANGUAGE_ITALIAN:
                return "Respond in Italian. Use clear and simple Italian.";
            case GroqConfig.LANGUAGE_JAPANESE:
                return "Respond in Japanese. Use clear and simple Japanese.";
            case GroqConfig.LANGUAGE_KOREAN:
                return "Respond in Korean. Use clear and simple Korean.";
            case GroqConfig.LANGUAGE_CHINESE_SIMPLIFIED:
                return "Respond in Simplified Chinese. Use clear and simple Chinese.";
            case GroqConfig.LANGUAGE_CHINESE_TRADITIONAL:
                return "Respond in Traditional Chinese. Use clear and simple Chinese.";
            case GroqConfig.LANGUAGE_RUSSIAN:
                return "Respond in Russian. Use clear and simple Russian.";
            case GroqConfig.LANGUAGE_ARABIC:
                return "Respond in Arabic. Use clear and simple Arabic.";
            case GroqConfig.LANGUAGE_HINDI:
                return "Respond in Hindi. Use clear and simple Hindi.";
            default:
                return "Respond in English. Use clear and simple English.";
        }
    }
    
    /**
     * Build the request body for Groq API
     */
    private JsonObject buildRequestBody(String prompt) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", GROQ_MODEL);
        
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        
        JsonObject[] messages = new JsonObject[]{message};
        requestBody.add("messages", gson.toJsonTree(messages));
        
        return requestBody;
    }
    
    /**
     * Parse the response from Groq API
     */
    private String parseResponse(String response) throws IOException {
        try {
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            
            if (jsonResponse.has("error")) {
                JsonObject error = jsonResponse.getAsJsonObject("error");
                String errorMessage = error.has("message") ? error.get("message").getAsString() : "Unknown API error";
                throw new IOException("Groq API error: " + errorMessage);
            }
            
            if (!jsonResponse.has("choices") || jsonResponse.getAsJsonArray("choices").size() == 0) {
                throw new IOException("No choices in response");
            }
            
            JsonObject choice = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject();
            JsonObject message = choice.getAsJsonObject("message");
            
            if (message.has("content")) {
                return message.get("content").getAsString();
            } else {
                throw new IOException("No content in response");
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON response", e);
            throw new IOException("Failed to parse response: " + e.getMessage());
        }
    }
    
    /**
     * Get a formatted error explanation with loading state
     */
    public static String getLoadingMessage() {
        // This method is static, so we need to use a different approach
        // The strings will be accessed through the context in the calling methods
        return "🤖 Analyzing error with AI...\n\nPlease wait while we analyze the problem.";
    }
    
    /**
     * Get a formatted error message when API is not available
     */
    public static String getNoApiMessage() {
        // This method is static, so we need to use a different approach
        // The strings will be accessed through the context in the calling methods
        return "⚠️ AI Explanation Not Available\n\n" +
               "To use AI error explanations, configure the GROQ_API_KEY environment variable.";
    }
} 