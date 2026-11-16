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
 * Client for Anthropic's Messages API, which Claude models are served through.
 */
class AnthropicClient implements AiClient {

    private static final String API_VERSION = "2023-06-01";
    private static final int MODELS_PAGE_SIZE = 1000;

    /**
     * The Messages API requires an output limit. These are tried in order, because a limit above
     * what the chosen model supports is rejected instead of being capped.
     */
    private static final int[] MAX_TOKENS_CANDIDATES = {32000, 16000, 8192, 4096};

    private final AiConfiguration configuration;

    AnthropicClient(@NonNull AiConfiguration configuration) {
        this.configuration = configuration;
    }

    @NonNull
    @Override
    public List<String> fetchModels() throws AiException {
        JsonObject response = AiHttp.get(url("/models?limit=" + MODELS_PAGE_SIZE), headers());
        JsonElement data = response.get("data");
        if (data == null || !data.isJsonArray()) {
            throw new AiException("The server's model list has an unexpected format.");
        }

        List<String> models = new ArrayList<>();
        for (JsonElement model : data.getAsJsonArray()) {
            String id = model.isJsonObject() ? AiHttp.optString(model.getAsJsonObject(), "id") : null;
            if (id != null && !id.isEmpty()) {
                models.add(id);
            }
        }
        Collections.sort(models);
        return models;
    }

    @NonNull
    @Override
    public String complete(@NonNull String systemPrompt, @NonNull List<AiMessage> messages) throws AiException {
        JsonArray jsonMessages = new JsonArray();
        for (AiMessage message : messages) {
            JsonObject jsonMessage = new JsonObject();
            jsonMessage.addProperty("role", message.getRole() == AiMessage.Role.USER ? "user" : "assistant");
            jsonMessage.addProperty("content", message.getContent());
            jsonMessages.add(jsonMessage);
        }

        JsonObject body = new JsonObject();
        body.addProperty("model", configuration.getModel().trim());
        if (!systemPrompt.isEmpty()) {
            body.addProperty("system", systemPrompt);
        }
        body.add("messages", jsonMessages);

        AiException lastError = null;
        for (int maxTokens : MAX_TOKENS_CANDIDATES) {
            body.addProperty("max_tokens", maxTokens);
            try {
                return readReply(AiHttp.post(url("/messages"), headers(), body));
            } catch (AiException e) {
                if (!isMaxTokensTooHigh(e)) {
                    throw e;
                }
                lastError = e;
            }
        }
        throw lastError;
    }

    private boolean isMaxTokensTooHigh(AiException e) {
        return e.getStatusCode() == 400 && e.getMessage() != null && e.getMessage().contains("max_tokens");
    }

    private String readReply(JsonObject response) throws AiException {
        if ("max_tokens".equals(AiHttp.optString(response, "stop_reason"))) {
            throw new AiException("The answer was cut off because it hit the model's output limit. Ask for a smaller app, or pick a model with a larger output limit.");
        }

        StringBuilder text = new StringBuilder();
        JsonElement content = response.get("content");
        if (content != null && content.isJsonArray()) {
            for (JsonElement block : content.getAsJsonArray()) {
                if (!block.isJsonObject()) {
                    continue;
                }
                JsonObject blockObject = block.getAsJsonObject();
                if ("text".equals(AiHttp.optString(blockObject, "type"))) {
                    String blockText = AiHttp.optString(blockObject, "text");
                    if (blockText != null) {
                        text.append(blockText);
                    }
                }
            }
        }
        if (text.toString().trim().isEmpty()) {
            throw new AiException("The model returned an empty answer.");
        }
        return text.toString();
    }

    private String url(String path) {
        return AiHttp.joinUrl(configuration.getBaseUrl(), path);
    }

    private Map<String, String> headers() {
        return Map.of(
                "x-api-key", configuration.getApiKey().trim(),
                "anthropic-version", API_VERSION);
    }
}
