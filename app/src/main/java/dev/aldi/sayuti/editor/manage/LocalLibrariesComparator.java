package dev.aldi.sayuti.editor.manage;

import java.io.File;
import java.util.Comparator;

public class LocalLibrariesComparator implements Comparator<File> {
    @Override
    public int compare(File first, File second) {
        return first.getName().compareTo(second.getName());
    }
}
