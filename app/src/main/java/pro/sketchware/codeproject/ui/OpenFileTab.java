package pro.sketchware.codeproject.ui;

import java.io.File;

import io.github.rosemoe.sora.text.Content;

public class OpenFileTab {

    private final File file;
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

    public Content getEditorContent() {
        return editorContent;
    }

    public String getContent() {
        return editorContent.toString();
    }

    public void setContent(String content) {
        this.editorContent = new Content(content);
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
