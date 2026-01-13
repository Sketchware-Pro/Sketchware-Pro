package pro.sketchware.ai;

import android.content.Context;
import java.io.File;
import java.util.HashMap;
import a.a.a.GB;
import a.a.a.lC;
import a.a.a.nB;
import a.a.a.oB;
import a.a.a.wq;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.ProjectFile;

public class ProjectBuilder {

    public static String createProject(Context context, String projectName, String packageName, String appName) {
        // 1. Generate ID and Init Data
        String sc_id = lC.b(); // Generate new SC_ID
        
        HashMap<String, Object> data = new HashMap<>();
        data.put("sc_id", sc_id);
        data.put("my_sc_pkg_name", packageName);
        data.put("my_ws_name", projectName);
        data.put("my_app_name", appName);
        data.put("my_sc_reg_dt", new nB().a("yyyyMMddHHmmss"));
        data.put("custom_icon", false);
        data.put("isIconAdaptive", true);
        data.put("sc_ver_code", "1");
        data.put("sc_ver_name", "1.0");
        data.put("sketchware_ver", GB.d(context));
        
        // Default Colors (Teal/Blue theme to match new UI)
        data.put("color_accent", 0xFF00BCD4);
        data.put("color_primary", 0xFF0097A7);
        data.put("color_primary_dark", 0xFF006064);
        data.put("color_control_highlight", 0x2000BCD4);
        data.put("color_control_normal", 0xFF546E7A);

        // 2. Save to Database/List
        lC.a(sc_id, data);
        
        // 3. Initialize Workspace Folders
        wq.a(context, sc_id);
        
        // 4. Initialize Logic/View Data
        // wq.b(sc_id) returns the project directory path
        new oB().b(wq.b(sc_id)); 
        
        // 5. Apply Default Settings
        ProjectSettings projectSettings = new ProjectSettings(sc_id);
        projectSettings.setValue(ProjectSettings.SETTING_NEW_XML_COMMAND, ProjectSettings.SETTING_GENERIC_VALUE_TRUE);
        projectSettings.setValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, ProjectSettings.SETTING_GENERIC_VALUE_TRUE);

        return sc_id;
    }
}
