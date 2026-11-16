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
 * Client for every provider that implements OpenAI's <code>/models</code> and
 * <code>/chat/completions</code> endpoints: OpenAI itself, OpenRouter, Coarena and most others.
 */
class OpenAiCompatibleClient implements AiClient {

    private static final String MAX_TOKENS = "max_tokens";
    private static final String MAX_COMPLETION_TOKENS = "max_completion_tokens";

    /**
     * The output limits to try, from most to least room for a whole project.
     */
    private static final int[] MAX_TOKENS_CANDIDATES = {32768, 16384, 8192};

    private final AiConfiguration configuration;

    OpenAiCompatibleClient(@NonNull AiConfiguration configuration) {
        this.configuration = configuration;
    }

    @NonNull
    @Override
    public List<String> fetchModels() throws AiException {
        JsonObject response = AiHttp.get(url("/models"), headers());
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
        if (!systemPrompt.isEmpty()) {
            jsonMessages.add(message("system", systemPrompt));
        }
        for (AiMessage message : messages) {
            jsonMessages.add(message(message.getRole() == AiMessage.Role.USER ? "user" : "assistant", message.getContent()));
        }

        JsonObject body = new JsonObject();
        body.addProperty("model", configuration.getModel().trim());
        body.add("messages", jsonMessages);

        // An output limit has to be sent, because some providers default to a small one that
        // can't fit a whole project. Most cap a too-high limit to the model's maximum, but some
        // reject it, and newer OpenAI models only accept the parameter's newer name.
        String limitParameter = MAX_TOKENS;
        int candidate = 0;
        while (true) {
            body.remove(MAX_TOKENS);
            body.remove(MAX_COMPLETION_TOKENS);
            if (candidate < MAX_TOKENS_CANDIDATES.length) {
                body.addProperty(limitParameter, MAX_TOKENS_CANDIDATES[candidate]);
            }
            try {
                return readReply(AiHttp.post(url("/chat/completions"), headers(), body));
            } catch (AiException e) {
                if (candidate >= MAX_TOKENS_CANDIDATES.length || !isRejectedLimit(e)) {
                    throw e;
                }
                if (limitParameter.equals(MAX_TOKENS) && e.getMessage().contains(MAX_COMPLETION_TOKENS)) {
                    limitParameter = MAX_COMPLETION_TOKENS;
                } else {
                    // Past the last candidate, the request is sent without any limit
                    candidate++;
                }
            }
        }
    }

    private boolean isRejectedLimit(AiException e) {
        String message = e.getMessage() == null ? "" : e.getMessage();
        boolean isClientError = e.getStatusCode() == 400 || e.getStatusCode() == 422;
        return isClientError && (message.contains(MAX_TOKENS) || message.contains(MAX_COMPLETION_TOKENS));
    }

    private String readReply(JsonObject response) throws AiException {
        JsonElement choices = response.get("choices");
        if (choices == null || !choices.isJsonArray() || choices.getAsJsonArray().isEmpty()) {
            throw new AiException("The model returned no answer.");
        }
        JsonObject choice = choices.getAsJsonArray().get(0).getAsJsonObject();
        if ("length".equals(AiHttp.optString(choice, "finish_reason"))) {
            throw new AiException("The answer was cut off because it hit the model's output limit. Ask for a smaller app, or pick a model with a larger output limit.");
        }

        JsonElement message = choice.get("message");
        String content = message != null && message.isJsonObject() ? readContent(message.getAsJsonObject().get("content")) : "";
        if (content.trim().isEmpty()) {
            throw new AiException("The model returned an empty answer.");
        }
        return content;
    }

    /**
     * The content is a plain string for most providers, but some send a list of typed parts.
     */
    private String readContent(JsonElement content) {
        if (content == null || content.isJsonNull()) {
            return "";
        }
        if (content.isJsonPrimitive()) {
            return content.getAsString();
        }
        StringBuilder text = new StringBuilder();
        if (content.isJsonArray()) {
            for (JsonElement part : content.getAsJsonArray()) {
                String partText = part.isJsonObject() ? AiHttp.optString(part.getAsJsonObject(), "text") : null;
                if (partText != null) {
                    text.append(partText);
                }
            }
        }
        return text.toString();
    }

    private JsonObject message(String role, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("role", role);
        message.addProperty("content", content);
        return message;
    }

    private String url(String path) {
        return AiHttp.joinUrl(configuration.getBaseUrl(), path);
    }

    private Map<String, String> headers() {
        return Map.of("Authorization", "Bearer " + configuration.getApiKey().trim());
    }
}
