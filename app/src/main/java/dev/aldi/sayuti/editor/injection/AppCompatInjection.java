package dev.aldi.sayuti.editor.injection;

import android.os.Environment;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import a.a.a.jq;
import mod.hey.studios.util.Helper;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.xml.XmlBuilder;

public class AppCompatInjection {

    // maps sc_id to (activity filename mapped to (list of injections))
    private static final Map<String, Map<String, List<? extends Map<String, Object>>>> INJECTIONS = new HashMap<>();

    private final String sc_id;
    private final ProjectFileBean projectFile;

    public AppCompatInjection(jq jq, ProjectFileBean projectFileBean) {
        sc_id = jq.sc_id;
        projectFile = projectFileBean;
    }

    public static String getDefaultActivityInjections() {
        return "[{\"type\":\"toolbar\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"toolbar\",\"value\":\"android:layout_height=\\\"?attr/actionBarSize\\\"\"},{\"type\":\"toolbar\",\"value\":\"android:background=\\\"?attr/colorPrimary\\\"\"},{\"type\":\"toolbar\",\"value\":\"app:popupTheme=\\\"@style/AppTheme.PopupOverlay\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:layout_height=\\\"wrap_content\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:theme=\\\"@style/AppTheme.AppBarOverlay\\\"\"},{\"type\":\"coordinatorlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"coordinatorlayout\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"tools:openDrawer=\\\"start\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_width=\\\"320dp\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_gravity=\\\"start\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:background=\\\"#EEEEEE\\\"\"}]";
    }

    private static List<? extends Map<String, Object>> readAppCompatInjections(String sc_id, String activityFilename) {
        String toParse;

        File injectionFile = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/" + sc_id + "/injection/appcompat/" + activityFilename);
        String fileContent;
        if (injectionFile.exists() && !(fileContent = FileUtil.readFile(injectionFile.getAbsolutePath())).isEmpty()) {
            toParse = fileContent;
        } else {
            toParse = getDefaultActivityInjections();
        }

        return new Gson().fromJson(toParse, Helper.TYPE_MAP_LIST);
    }

    public static void refreshInjections() {
        for (Map.Entry<String, Map<String, List<? extends Map<String, Object>>>> project : INJECTIONS.entrySet()) {
            Map<String, List<? extends Map<String, Object>>> projectInjections = project.getValue();

            List<String> activityFilenames = new LinkedList<>();
            for (Iterator<String> iterator = Objects.requireNonNull(projectInjections).keySet().iterator(); iterator.hasNext(); iterator.remove()) {
                activityFilenames.add(iterator.next());
            }

            for (String activityFilename : activityFilenames) {
                projectInjections.put(activityFilename, readAppCompatInjections(project.getKey(), activityFilename));
            }
        }
    }

    public void inject(XmlBuilder nx, String str) {
        if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR) ||
                projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER) ||
                projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
            if (!INJECTIONS.containsKey(sc_id)) {
                INJECTIONS.put(sc_id, new HashMap<>());
            }
            Map<String, List<? extends Map<String, Object>>> projectInjections = INJECTIONS.get(sc_id);
            if (!Objects.requireNonNull(projectInjections).containsKey(projectFile.fileName)) {
                projectInjections.put(projectFile.fileName, readAppCompatInjections(sc_id, projectFile.fileName));
            }

            for (Map<String, Object> injection : Objects.requireNonNull(projectInjections.get(projectFile.fileName))) {
                Object value;
                if (str.toLowerCase().equals(injection.get("type")) && (value = injection.get("value")) instanceof String) {
                    nx.addAttributeValue((String) value);
                }
            }
        }
    }
}
