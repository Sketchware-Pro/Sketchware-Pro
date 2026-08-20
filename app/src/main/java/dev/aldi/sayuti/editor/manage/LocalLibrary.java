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

    /**
     * Like {@link #fromFile(File)}, but accepts a pre-computed (e.g. cached)
     * formatted size instead of recalculating it by walking the directory tree.
     */
    public static LocalLibrary fromFile(File file, String formattedSize) {
        return new LocalLibrary(file.getName(), formattedSize);
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
