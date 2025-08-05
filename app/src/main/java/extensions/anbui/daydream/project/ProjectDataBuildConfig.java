package extensions.anbui.daydream.project;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import extensions.anbui.daydream.configs.Configs;
import extensions.anbui.daydream.file.FileUtils;

public class ProjectDataBuildConfig {

    public static void setDataForFirstTimeProjectCreation(String projectID) {
        Configs.currentProjectID = projectID;
        //There is some code that will temporarily block after the project is created so wait a second.
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                writeDataFile(projectID, "{\"dexer\":\"D8\",\"classpath\":\"\",\"enable_logcat\":\"true\",\"no_http_legacy\":\"false\",\"android_jar\":\"\",\"no_warn\":\"true\",\"java_ver\":\"11\"}");
            } catch (InterruptedException e) {
                Log.e("ProjectDataBuildConfig", "setDataForFirstTimeProjectCreation: " + e.getMessage());
            }
        }).start();
    }

    public static void setDataString(String projectID, String key, String value) {
        JsonObject json = JsonParser.parseString(readDataFile(projectID)).getAsJsonObject();
        json.addProperty(key, value);
        writeDataFile(projectID, new Gson().toJson(json));
    }

    public static String readDataFile(String projectID) {
        String contentProjectFile = FileUtils.readTextFile(FileUtils.getInternalStorageDir() + Configs.projectDataFolderDir + projectID + "/build_config");
        if (contentProjectFile.isEmpty()) contentProjectFile = "{}";
        return contentProjectFile;
    }

    public static void writeDataFile(String projectID, String content) {
        FileUtils.writeTextFile(FileUtils.getInternalStorageDir() + Configs.projectDataFolderDir + projectID + "/build_config", content);
    }
}
