package extensions.anbui.sketchware.project;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import extensions.anbui.sketchware.configs.Configs;
import extensions.anbui.sketchware.file.FileUtils;

public class ProjectFileInfo {

    //Read project file and convert data to Map.
    public static Map<String, Object> read(String projectID) {
        String contentProjectFile = ProjectDecryptor.decryptProjectFile(FileUtils.getInternalStorageDir() + Configs.projectInfoFolderDir + projectID + "/project");
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        return gson.fromJson(contentProjectFile, type);
    }
}
