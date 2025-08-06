package dev.aldi.sayuti.editor.manage;

import static pro.sketchware.utility.FileUtil.formatFileSize;
import static pro.sketchware.utility.FileUtil.getFileSize;

import java.io.File;

public class LocalLibrary {
    private final String name;
    private final String size;
    private boolean isSelected;

    private LocalLibrary(String name, String size) {
        this.name = name;
        this.size = size;
    }

    public static LocalLibrary fromFile(File file) {
        return new LocalLibrary(file.getName(), formatFileSize(getFileSize(file)));
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
