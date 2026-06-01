package ide.sketchware.codeproject.model;

import java.io.File;
import java.util.HashMap;

import a.a.a.wq;
import a.a.a.yB;

public class CodeProject {

    public static final String PROJECT_TYPE_CODE = "code";
    public static final String KEY_PROJECT_TYPE = "sc_project_type";

    private String scId;
    private String projectName;
    private String packageName;
    private String appName;
    private String versionCode;
    private String versionName;

    public CodeProject() {
    }

    public CodeProject(String scId, String projectName, String packageName, String appName, String versionCode, String versionName) {
        this.scId = scId;
        this.projectName = projectName;
        this.packageName = packageName;
        this.appName = appName;
        this.versionCode = versionCode;
        this.versionName = versionName;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getProjectMyscPath() {
        return wq.d(scId);
    }

    public String getSourcePath() {
        return getProjectMyscPath() + File.separator + "app" + File.separator + "src" + File.separator + "main";
    }

    public String getJavaSourcePath() {
        return getSourcePath() + File.separator + "java";
    }

    public String getKotlinSourcePath() {
        return getJavaSourcePath();
    }

    public String getResPath() {
        return getSourcePath() + File.separator + "res";
    }

    public String getManifestPath() {
        return getSourcePath() + File.separator + "AndroidManifest.xml";
    }

    public String getBinPath() {
        return getProjectMyscPath() + File.separator + "bin";
    }

    public String getGenPath() {
        return getProjectMyscPath() + File.separator + "gen";
    }

    public String getLibsPath() {
        return getProjectMyscPath() + File.separator + "app" + File.separator + "libs";
    }

    public static boolean isCodeProject(HashMap<String, Object> projectMap) {
        if (projectMap == null) return false;
        return PROJECT_TYPE_CODE.equals(yB.c(projectMap, KEY_PROJECT_TYPE));
    }

    public static CodeProject fromMetadata(HashMap<String, Object> metadata) {
        if (metadata == null) return null;
        CodeProject project = new CodeProject();
        project.setScId(yB.c(metadata, "sc_id"));
        project.setProjectName(yB.c(metadata, "my_ws_name"));
        project.setPackageName(yB.c(metadata, "my_sc_pkg_name"));
        project.setAppName(yB.c(metadata, "my_app_name"));
        project.setVersionCode(yB.c(metadata, "sc_ver_code"));
        project.setVersionName(yB.c(metadata, "sc_ver_name"));
        return project;
    }
}
