package extensions.anbui.daydream.project;

import java.util.Map;
import java.util.Objects;

import extensions.anbui.daydream.configs.Configs;
import extensions.anbui.daydream.file.FileUtils;

public class ProjectDataLibrary {

    public static boolean isEnabledFirebase(String projectID) {
        return Objects.equals(getFirebaseSettingsData(projectID).get("useYn"), "Y");
    }

    public static boolean isEnabledAppCompat(String projectID) {
        return Objects.equals(getAppCompatSettingsData(projectID).get("useYn"), "Y");
    }

    public static boolean isEnabledAdmob(String projectID) {
        return Objects.equals(getAdmobSettingsData(projectID).get("useYn"), "Y");
    }

    public static boolean isEnabledGoogleMap(String projectID) {
        return Objects.equals(getGoogleMapSettingsData(projectID).get("useYn"), "Y");
    }

    public static Map<String, Object> getFirebaseSettingsData(String projectID) {
        return readLibraryData(projectID, "@firebaseDB");
    }

    public static Map<String, Object> getAppCompatSettingsData(String projectID) {
        return readLibraryData(projectID, "@compat");
    }

    public static Map<String, Object> getAdmobSettingsData(String projectID) {
        return readLibraryData(projectID, "@admob");
    }

    public static Map<String, Object> getGoogleMapSettingsData(String projectID) {
        return readLibraryData(projectID, "@googleMap");
    }

    public static Map<String, Object> readLibraryData(String projectID, String dataType) {
        String contentProjectFile = ProjectDataDecryptor.decryptProjectFile(FileUtils.getInternalStorageDir() + Configs.projectDataFolderDir + projectID + "/library");
        return ProjectData.readDataWithDataType(dataType, contentProjectFile);
    }
}
