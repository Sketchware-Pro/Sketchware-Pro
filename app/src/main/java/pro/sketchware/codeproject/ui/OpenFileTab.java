package pro.sketchware.codeproject.ui;

import java.io.File;

public class OpenFileTab {

    private final File file;
    private String content;
    private boolean modified;

    public OpenFileTab(File file, String content) {
        this.file = file;
        this.content = content;
        this.modified = false;
    }

    public File getFile() {
        return file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
