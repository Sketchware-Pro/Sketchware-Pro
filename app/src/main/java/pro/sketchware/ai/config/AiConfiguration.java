package pro.sketchware.ai.config;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * One configured AI model: which provider to talk to, with which key, and which model to use.
 */
public class AiConfiguration {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("provider")
    private AiProviderType provider;

    @SerializedName("base_url")
    private String baseUrl;

    @SerializedName("api_key")
    private String apiKey;

    @SerializedName("model")
    private String model;

    /**
     * Whether the last connection test with exactly these values succeeded.
     */
    @SerializedName("verified")
    private boolean verified;

    public AiConfiguration() {
        this(AiProviderType.GEMINI);
    }

    public AiConfiguration(@NonNull AiProviderType provider) {
        id = UUID.randomUUID().toString();
        name = "";
        this.provider = provider;
        baseUrl = provider.getDefaultBaseUrl();
        apiKey = "";
        model = "";
    }

    @NonNull
    public AiConfiguration copy() {
        AiConfiguration copy = new AiConfiguration(getProvider());
        copy.id = id;
        copy.name = name;
        copy.baseUrl = baseUrl;
        copy.apiKey = apiKey;
        copy.model = model;
        copy.verified = verified;
        return copy;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The name to show for this configuration, falling back to provider and model.
     */
    @NonNull
    public String getDisplayName() {
        if (!getName().trim().isEmpty()) {
            return getName().trim();
        }
        return getModel().isEmpty() ? getProvider().getDisplayName() : getModel();
    }

    @NonNull
    public AiProviderType getProvider() {
        // Unknown providers written by a newer app version deserialize to null
        return provider == null ? AiProviderType.CUSTOM : provider;
    }

    public void setProvider(@NonNull AiProviderType provider) {
        this.provider = provider;
    }

    @NonNull
    public String getBaseUrl() {
        return baseUrl == null ? "" : baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @NonNull
    public String getApiKey() {
        return apiKey == null ? "" : apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @NonNull
    public String getModel() {
        return model == null ? "" : model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * @return Whether this configuration has everything needed to send a request.
     */
    public boolean isComplete() {
        return !getBaseUrl().trim().isEmpty()
                && !getApiKey().trim().isEmpty()
                && !getModel().trim().isEmpty();
    }
}
