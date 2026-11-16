package pro.sketchware.ai.api;

import androidx.annotation.NonNull;

import pro.sketchware.ai.config.AiConfiguration;

/**
 * Creates the {@link AiClient} that speaks a configuration's provider's protocol.
 */
public final class AiClientFactory {

    private AiClientFactory() {
    }

    @NonNull
    public static AiClient create(@NonNull AiConfiguration configuration) {
        return switch (configuration.getProvider().getApiStyle()) {
            case ANTHROPIC -> new AnthropicClient(configuration);
            case GEMINI -> new GeminiClient(configuration);
            case OPENAI -> new OpenAiCompatibleClient(configuration);
        };
    }
}
