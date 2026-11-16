package pro.sketchware.ai.api;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import pro.sketchware.ai.config.AiConfiguration;

/**
 * Client for Google's Generative Language API, which Gemini models are served through.
 */
class GeminiClient implements AiClient {

    private static final String MODEL_NAME_PREFIX = "models/";
    private static final String GENERATE_METHOD = "generateContent";
    private static final int MODELS_PAGE_SIZE = 1000;

    private final AiConfiguration configuration;

    GeminiClient(@NonNull AiConfiguration configuration) {
        this.configuration = configuration;
    }

    @NonNull
    @Override
    public List<String> fetchModels() throws AiException {
        JsonObject response = AiHttp.get(url("/models?pageSize=" + MODELS_PAGE_SIZE), headers());
        JsonElement data = response.get("models");
        if (data == null || !data.isJsonArray()) {
            throw new AiException("The server's model list has an unexpected format.");
        }

        List<String> models = new ArrayList<>();
        for (JsonElement model : data.getAsJsonArray()) {
            if (!model.isJsonObject() || !canGenerateContent(model.getAsJsonObject())) {
                continue;
            }
            String name = AiHttp.optString(model.getAsJsonObject(), "name");
            if (name != null && !name.isEmpty()) {
                models.add(name.startsWith(MODEL_NAME_PREFIX) ? name.substring(MODEL_NAME_PREFIX.length()) : name);
            }
        }
        Collections.sort(models);
        return models;
    }

    /**
     * The list also contains embedding and other special purpose models that can't chat.
     */
    private boolean canGenerateContent(JsonObject model) {
        JsonElement methods = model.get("supportedGenerationMethods");
        if (methods == null || !methods.isJsonArray()) {
            return true;
        }
        for (JsonElement method : methods.getAsJsonArray()) {
            if (method.isJsonPrimitive() && GENERATE_METHOD.equals(method.getAsString())) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    @Override
    public String complete(@NonNull String systemPrompt, @NonNull List<AiMessage> messages) throws AiException {
        JsonArray contents = new JsonArray();
        for (AiMessage message : messages) {
            JsonObject content = textContent(message.getContent());
            content.addProperty("role", message.getRole() == AiMessage.Role.USER ? "user" : "model");
            contents.add(content);
        }

        JsonObject body = new JsonObject();
        if (!systemPrompt.isEmpty()) {
            body.add("systemInstruction", textContent(systemPrompt));
        }
        body.add("contents", contents);

        String path = "/" + MODEL_NAME_PREFIX + configuration.getModel().trim() + ":" + GENERATE_METHOD;
        return readReply(AiHttp.post(url(path), headers(), body));
    }

    private String readReply(JsonObject response) throws AiException {
        JsonElement candidates = response.get("candidates");
        if (candidates == null || !candidates.isJsonArray() || candidates.getAsJsonArray().isEmpty()) {
            throw new AiException("The model returned no answer. The request may have been blocked by its safety filters.");
        }
        JsonObject candidate = candidates.getAsJsonArray().get(0).getAsJsonObject();
        if ("MAX_TOKENS".equals(AiHttp.optString(candidate, "finishReason"))) {
            throw new AiException("The answer was cut off because it hit the model's output limit. Ask for a smaller app, or pick a model with a larger output limit.");
        }

        StringBuilder text = new StringBuilder();
        JsonElement content = candidate.get("content");
        JsonElement parts = content != null && content.isJsonObject() ? content.getAsJsonObject().get("parts") : null;
        if (parts != null && parts.isJsonArray()) {
            for (JsonElement part : parts.getAsJsonArray()) {
                if (!part.isJsonObject() || isThought(part.getAsJsonObject())) {
                    continue;
                }
                String partText = AiHttp.optString(part.getAsJsonObject(), "text");
                if (partText != null) {
                    text.append(partText);
                }
            }
        }
        if (text.toString().trim().isEmpty()) {
            throw new AiException("The model returned an empty answer.");
        }
        return text.toString();
    }

    /**
     * Thinking models can include their reasoning as parts flagged with <code>thought</code>.
     */
    private boolean isThought(JsonObject part) {
        JsonElement thought = part.get("thought");
        return thought != null && thought.isJsonPrimitive() && thought.getAsBoolean();
    }

    private JsonObject textContent(String text) {
        JsonObject part = new JsonObject();
        part.addProperty("text", text);
        JsonArray parts = new JsonArray();
        parts.add(part);
        JsonObject content = new JsonObject();
        content.add("parts", parts);
        return content;
    }

    private String url(String path) {
        return AiHttp.joinUrl(configuration.getBaseUrl(), path);
    }

    /**
     * The key goes in a header rather than the <code>key</code> query parameter, so it can't end up
     * in logs that record request URLs.
     */
    private Map<String, String> headers() {
        return Map.of("x-goog-api-key", configuration.getApiKey().trim());
    }
}
