package pro.sketchware.util;

import android.content.Context;
import android.os.Environment;

import com.besome.sketch.design.DesignActivity;

import java.io.File;

import mod.hey.studios.project.ProjectSettings;

public class SketchwareUtil {

    public static boolean isManualManifestEditingEnabled(String scId) {
        ProjectSettings projectSettings = new ProjectSettings(scId);
        return projectSettings.getValue(ProjectSettings.MANUAL_ANDROID_MANIFEST, "false").equals("true");
    }

    public static File getManualManifestFile(String scId) {
        // File projectDir = new File(Config.getProjectSettingsPath(scId)).getParentFile();
        File projectDir = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/" + scId);
        return new File(projectDir, "app/src/main/AndroidManifest.xml");
    }

    public static String getProjectID(Context context) {
        if (context instanceof DesignActivity) {
            return DesignActivity.sc_id;
        }
        return "";
    }
}
