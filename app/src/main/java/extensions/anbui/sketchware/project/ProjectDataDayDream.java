package extensions.anbui.sketchware.project;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.Map;

import extensions.anbui.sketchware.configs.Configs;
import extensions.anbui.sketchware.file.FileUtils;

public class ProjectDataDayDream {

    public static boolean isEnableEdgeToEdge(String projectID, String activity) {
        return getDataBoolean(projectID, "EdgeToEdge", activity);
    }

    public static void setEnableEdgeToEdge(String projectID, String activity, boolean isEnable) {
        setDataBoolean(projectID, "EdgeToEdge", activity, isEnable);
    }

    public static boolean isEnableWindowInsetsHandling(String projectID, String activity) {
        return getDataBoolean(projectID, "WindowInsetsHandling", activity);
    }

    public static void setEnableWindowInsetsHandling(String projectID, String activity, boolean isEnable) {
        setDataBoolean(projectID, "WindowInsetsHandling", activity, isEnable);
    }

    public static boolean getDataBoolean(String projectID, String toplevelkey, String key) {
        JsonObject json = JsonParser.parseString(readDayDreamDataFile(projectID)).getAsJsonObject();
        if (json.has(toplevelkey)) {
            JsonObject edge = json.getAsJsonObject(toplevelkey);
            try {
                return edge.get(key).getAsBoolean();
            } catch (Exception ignored) {
            }
        }
        return false;
    }

    public static void setDataBoolean(String projectID, String toplevelkey, String key, boolean value) {
        JsonObject json = JsonParser.parseString(readDayDreamDataFile(projectID)).getAsJsonObject();
        if (!json.has(toplevelkey)) {
            JsonObject edge = new JsonObject();
            edge.addProperty(key, value);
            json.add(toplevelkey, edge);
        } else {
            JsonObject edge = json.getAsJsonObject(toplevelkey);
            edge.addProperty(key, value);
        }
        writeDayDreamDataFile(projectID, new Gson().toJson(json));
    }

    public static Map<String, Object> readData(String projectID) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        return gson.fromJson(readDayDreamDataFile(projectID), type);
    }

    public static String readDayDreamDataFile(String projectID) {
        String contentProjectFile = FileUtils.readTextFile(FileUtils.getInternalStorageDir() + Configs.projectDataFolderDir + projectID + "/DataDayDream.json");
        if (contentProjectFile.isEmpty()) contentProjectFile = "{}";
        return contentProjectFile;
    }

    public static void writeDayDreamDataFile(String projectID, String content) {
        FileUtils.writeTextFile(FileUtils.getInternalStorageDir() + Configs.projectDataFolderDir + projectID + "/DataDayDream.json", content);
    }
}
