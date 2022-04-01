package mod.agus.jcoderz.command;

import java.io.File;
import java.util.ArrayList;

public class ProcessingFiles {

    private ProcessingFiles() {
    }

    public static void checkFile(File path, ArrayList<String> filesList) {
        if (path.isDirectory()) {
            File[] filesArray = path.listFiles();

            if (filesArray != null) {
                for (File file : filesArray) {
                    checkFile(file, filesList);
                }
            }
        } else {
            filesList.add(path.getAbsolutePath());
        }

    }

    public static ArrayList<String> getListResource(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file1 = new File(path);
        if (file1.exists()) {
            if (file1.isDirectory()) {
                File[] filesArray = file1.listFiles();
                if (filesArray != null) {
                    for (File file : filesArray) {
                        checkFile(file, files);
                    }
                }
            } else {
                files.add(file1.getAbsolutePath());
            }
        }
        return files;
    }
}
