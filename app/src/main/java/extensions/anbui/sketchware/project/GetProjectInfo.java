package extensions.anbui.sketchware.project;

import androidx.annotation.NonNull;

import java.util.Objects;
public class GetProjectInfo {

    @NonNull
    public static String getProjectName(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("my_ws_name")).toString();
    }

    @NonNull
    public static String getDateCreated(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("my_sc_reg_dt")).toString();
    }

    @NonNull
    public static String getSketchwareVersionCode(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("sketchware_ver")).toString();
    }

    public static int getSketchwareVersionCodeInt(String projectID) {
        return Integer.parseInt(Objects.requireNonNull(ProjectFileInfo.read(projectID).get("sketchware_ver")).toString());
    }

    @NonNull
    public static String getAppName(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("my_app_name")).toString();
    }

    @NonNull
    public static String getVersionName(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("sc_ver_name")).toString();
    }

    @NonNull
    public static String getVersionCode(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("sc_ver_code")).toString();
    }

    @NonNull
    public static String getPackageName(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("my_sc_pkg_name")).toString();
    }

    public static double getAccentColor(String projectID) {
        return (double) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_accent"));
    }

    public static int getAccentHexColor(String projectID) {
        return (int) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_accent"));
    }

    public static double getPrimaryColor(String projectID) {
        return (double) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_primary"));
    }

    public static int getPrimaryHexColor(String projectID) {
        return (int) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_primary"));
    }

    public static double getPrimaryDarkColor(String projectID) {
        return (double) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_primary_dark"));
    }

    public static int getPrimaryDarkHexColor(String projectID) {
        return (int) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_primary_dark"));
    }

    public static double getControlHighLightColor(String projectID) {
        return (double) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_control_highlight"));
    }

    public static int getControlHighLightHexColor(String projectID) {
        return (int) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_control_highlight"));
    }

    public static double getControlNormalColor(String projectID) {
        return (double) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_control_normal"));
    }

    public static int getControlNormalHexColor(String projectID) {
        return (int) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("color_control_normal"));
    }

    public static boolean isCustomIcon(String projectID) {
        return (boolean) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("custom_icon"));
    }

    public static boolean isIconAdaptive(String projectID) {
        return (boolean) Objects.requireNonNull(ProjectFileInfo.read(projectID).get("isIconAdaptive"));
    }

    //It can be used for matching.
    public static String getProjectID(String projectID) {
        return Objects.requireNonNull(ProjectFileInfo.read(projectID).get("sc_id")).toString();
    }
}
