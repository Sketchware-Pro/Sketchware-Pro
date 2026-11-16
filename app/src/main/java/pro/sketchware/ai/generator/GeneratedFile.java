package pro.sketchware.ai.generator;

import androidx.annotation.NonNull;

/**
 * A file the AI created for a project, for showing it to the user.
 */
public final class GeneratedFile {

    private final String name;
    private final String description;
    private final String content;

    GeneratedFile(@NonNull String name, @NonNull String description, @NonNull String content) {
        this.name = name;
        this.description = description;
        this.content = content;
    }

    @NonNull
    public String getName() {
        return name;
    }

    /**
     * @return What the file is, e.g. "Layout of MainActivity".
     */
    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public int getLineCount() {
        int lines = 1;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '\n') {
                lines++;
            }
        }
        return lines;
    }
}
