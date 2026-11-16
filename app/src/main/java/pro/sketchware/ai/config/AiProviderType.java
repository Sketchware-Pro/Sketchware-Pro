package pro.sketchware.ai.config;

import androidx.annotation.NonNull;

/**
 * The AI providers that can be configured. Each provider speaks one of the wire protocols in
 * {@link ApiStyle}, so adding a new OpenAI-compatible provider only takes a new constant here.
 */
public enum AiProviderType {

    GEMINI("Google Gemini", "https://generativelanguage.googleapis.com/v1beta", ApiStyle.GEMINI,
            "https://aistudio.google.com/apikey"),
    CLAUDE("Claude (Anthropic)", "https://api.anthropic.com/v1", ApiStyle.ANTHROPIC,
            "https://console.anthropic.com/settings/keys"),
    OPENROUTER("OpenRouter", "https://openrouter.ai/api/v1", ApiStyle.OPENAI,
            "https://openrouter.ai/keys"),
    COARENA("Coarena AI", "https://coarena.ai/api/v1", ApiStyle.OPENAI,
            "https://coarena.ai/account/api/keys"),
    NVIDIA("NVIDIA NIM", "https://integrate.api.nvidia.com/v1", ApiStyle.OPENAI,
            "https://build.nvidia.com/settings/api-keys"),
    OPENAI("OpenAI", "https://api.openai.com/v1", ApiStyle.OPENAI,
            "https://platform.openai.com/api-keys"),
    CUSTOM("Custom (OpenAI compatible)", "", ApiStyle.OPENAI, "");

    private final String displayName;
    private final String defaultBaseUrl;
    private final ApiStyle apiStyle;
    private final String apiKeyUrl;

    AiProviderType(String displayName, String defaultBaseUrl, ApiStyle apiStyle, String apiKeyUrl) {
        this.displayName = displayName;
        this.defaultBaseUrl = defaultBaseUrl;
        this.apiStyle = apiStyle;
        this.apiKeyUrl = apiKeyUrl;
    }

    @NonNull
    public static String[] displayNames() {
        AiProviderType[] types = values();
        String[] names = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            names[i] = types[i].displayName;
        }
        return names;
    }

    @NonNull
    public static AiProviderType fromDisplayName(String displayName) {
        for (AiProviderType type : values()) {
            if (type.displayName.equals(displayName)) {
                return type;
            }
        }
        return CUSTOM;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @NonNull
    public String getDefaultBaseUrl() {
        return defaultBaseUrl;
    }

    @NonNull
    public ApiStyle getApiStyle() {
        return apiStyle;
    }

    /**
     * @return The page where the user can create an API key, or an empty string if unknown.
     */
    @NonNull
    public String getApiKeyUrl() {
        return apiKeyUrl;
    }

    /**
     * The wire protocol a provider speaks.
     */
    public enum ApiStyle {
        OPENAI,
        ANTHROPIC,
        GEMINI
    }
}
