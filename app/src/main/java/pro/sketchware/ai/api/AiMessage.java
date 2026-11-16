package pro.sketchware.ai.api;

import androidx.annotation.NonNull;

/**
 * One turn of a conversation with a model.
 */
public final class AiMessage {

    private final Role role;
    private final String content;

    private AiMessage(Role role, String content) {
        this.role = role;
        this.content = content;
    }

    @NonNull
    public static AiMessage user(@NonNull String content) {
        return new AiMessage(Role.USER, content);
    }

    @NonNull
    public static AiMessage assistant(@NonNull String content) {
        return new AiMessage(Role.ASSISTANT, content);
    }

    @NonNull
    public Role getRole() {
        return role;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public enum Role {
        USER,
        ASSISTANT
    }
}
