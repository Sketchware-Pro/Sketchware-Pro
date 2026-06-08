package ide.sketchware.codeproject.ui;

import java.io.File;
import java.util.Objects;

import io.github.rosemoe.sora.text.Content;

public class OpenFileTab {

    private File file;
    private Content editorContent;
    private boolean modified;

    public OpenFileTab(File file, String content) {
        this.file = file;
        this.editorContent = new Content(content);
        this.modified = false;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Content getEditorContent() {
        return editorContent;
    }

    public void setEditorContent(Content editorContent) {
        this.editorContent = Objects.requireNonNull(editorContent, "editorContent cannot be null");
    }

    public String getContent() {
        return editorContent.toString();
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
