package pro.sketchware.activities.main.fragments.ai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pro.sketchware.ai.generator.AiProjectGenerator;
import pro.sketchware.ai.generator.GeneratedFile;

/**
 * One row of the AI chat. Rows are immutable: a change replaces the row with a new one that has
 * the same {@link #getId()}.
 */
public final class ChatItem {

    private final long id;
    private final Type type;
    /**
     * The user's prompt, the working model's name, or the error message, depending on the type.
     */
    private final String text;
    /**
     * For {@link Type#ERROR}: the prompt that failed, to offer trying it again.
     * For {@link Type#USER}: the name of the project the prompt changes, if any.
     */
    private final String detail;
    private final AiProjectGenerator.Stage stage;
    private final AiProjectGenerator.Result result;
    private final GeneratedFile file;

    private ChatItem(long id, Type type, String text, String detail, AiProjectGenerator.Stage stage,
                     AiProjectGenerator.Result result, GeneratedFile file) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.detail = detail;
        this.stage = stage;
        this.result = result;
        this.file = file;
    }

    /**
     * @param projectName The project the prompt changes, or null if it asks for a new one.
     */
    @NonNull
    static ChatItem user(long id, @NonNull String prompt, @Nullable String projectName) {
        return new ChatItem(id, Type.USER, prompt, projectName, null, null, null);
    }

    /**
     * A plain message from the AI, e.g. confirming an undo.
     */
    @NonNull
    static ChatItem info(long id, @NonNull String message) {
        return new ChatItem(id, Type.INFO, message, null, null, null, null);
    }

    @NonNull
    static ChatItem progress(long id, @NonNull AiProjectGenerator.Stage stage, @NonNull String modelName) {
        return new ChatItem(id, Type.PROGRESS, modelName, null, stage, null, null);
    }

    @NonNull
    static ChatItem result(long id, @NonNull AiProjectGenerator.Result result) {
        return new ChatItem(id, Type.RESULT, null, null, null, result, null);
    }

    @NonNull
    static ChatItem error(long id, @NonNull String message, @NonNull String failedPrompt) {
        return new ChatItem(id, Type.ERROR, message, failedPrompt, null, null, null);
    }

    @NonNull
    static ChatItem file(long id, @NonNull GeneratedFile file) {
        return new ChatItem(id, Type.FILE, null, null, null, null, file);
    }

    public long getId() {
        return id;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @Nullable
    public String getFailedPrompt() {
        return type == Type.ERROR ? detail : null;
    }

    @Nullable
    public String getProjectName() {
        return type == Type.USER ? detail : null;
    }

    @Nullable
    public AiProjectGenerator.Stage getStage() {
        return stage;
    }

    @Nullable
    public AiProjectGenerator.Result getResult() {
        return result;
    }

    @Nullable
    public GeneratedFile getFile() {
        return file;
    }

    public enum Type {
        USER,
        PROGRESS,
        RESULT,
        ERROR,
        INFO,
        FILE
    }
}
