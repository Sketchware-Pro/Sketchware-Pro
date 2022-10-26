package dev.aldi.sayuti.editor.injection;

import android.os.Environment;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import a.a.a.Nx;
import a.a.a.jq;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class AppCompatInjection {

    private static final Map<String, List<Map<String, Object>>> INJECTIONS = new HashMap<>();

    private final String sc_id;
    private final ProjectFileBean projectFile;

    public AppCompatInjection(jq jq, ProjectFileBean projectFileBean) {
        sc_id = jq.sc_id;
        projectFile = projectFileBean;
    }

    public static String a() {
        return "[{\"type\":\"toolbar\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"toolbar\",\"value\":\"android:layout_height=\\\"?attr/actionBarSize\\\"\"},{\"type\":\"toolbar\",\"value\":\"android:background=\\\"?attr/colorPrimary\\\"\"},{\"type\":\"toolbar\",\"value\":\"app:popupTheme=\\\"@style/AppTheme.PopupOverlay\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:layout_height=\\\"wrap_content\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:theme=\\\"@style/AppTheme.AppBarOverlay\\\"\"},{\"type\":\"coordinatorlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"coordinatorlayout\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"tools:openDrawer=\\\"start\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_width=\\\"320dp\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_gravity=\\\"start\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:background=\\\"#EEEEEE\\\"\"}]";
    }

    public void inject(Nx nx, String str) {
        if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR) ||
                projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER) ||
                projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
            if (!INJECTIONS.containsKey(sc_id)) {
                INJECTIONS.put(sc_id, readAppCompatInjections(sc_id, projectFile.fileName));
            }

            for (Map<String, Object> bruh : Objects.requireNonNull(INJECTIONS.get(sc_id))) {
                Object value;
                if (str.toLowerCase().equals(bruh.get("type")) && (value = bruh.get("value")) instanceof String) {
                    nx.b((String) value);
                }
            }
        }
    }

    private static List<Map<String, Object>> readAppCompatInjections(String sc_id, String activityFilename) {
        String toParse;

        File injectionFile = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/" + sc_id + "/injection/appcompat/" + activityFilename);
        String fileContent;
        if (injectionFile.exists() && (fileContent = FileUtil.readFile(injectionFile.getAbsolutePath())).length() != 0) {
            toParse = fileContent;
        } else {
            toParse = a();
        }

        return new Gson().fromJson(toParse, Helper.TYPE_MAP_LIST);
    }
}
