package extensions.anbui.sketchware.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import pro.sketchware.utility.FileUtil;

public class JavaFileUtils {

    public static boolean isAdded = false;

    //Add files to project's java directory
    public static void addJavaFileToProject(String projectID, String fileName, String Content) {

        isAdded = false;

        File dir = new File(FileUtil.getExternalStorageDir() +"/.sketchware/data/" + projectID + "/files/java/");
        if (!dir.exists()) {
            if (!dir.mkdirs()) return;
        }

        try (FileWriter writer = new FileWriter(FileUtil.getExternalStorageDir() +"/.sketchware/data/" + projectID + "/files/java/" + fileName)) {
            writer.write(Content);
            isAdded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Check if Java exists in the project's java directory.
    public static boolean isJavaFileExistInProject(String projectID, String fileName) {
        File dir = new File(FileUtil.getExternalStorageDir() +"/.sketchware/data/" + projectID + "/files/java/" + fileName);
        return dir.exists();
    }

    //Add files to project's java/lab directory.
    public static void addJavaFileToProjectLab(String projectID, String fileName, String Content) {

        isAdded = false;

        File dir = new File(FileUtil.getExternalStorageDir() +"/.sketchware/data/" + projectID + "/files/java/lab/");
        if (!dir.exists()) {
            if (!dir.mkdirs()) return;
        }

        try (FileWriter writer = new FileWriter(FileUtil.getExternalStorageDir() +"/.sketchware/data/" + projectID + "/files/java/lab/" + fileName)) {
            writer.write(Content);
            isAdded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Check if Java exists in the project's java/lab directory.
    public static boolean isJavaFileExistInProjectLab(String projectID, String fileName) {
        File dir = new File(FileUtil.getExternalStorageDir() +"/.sketchware/data/" + projectID + "/files/java/lab/" + fileName);
        return dir.exists();
    }
}
