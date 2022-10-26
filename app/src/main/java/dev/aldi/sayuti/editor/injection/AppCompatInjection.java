package dev.aldi.sayuti.editor.injection;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Nx;
import a.a.a.jq;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class AppCompatInjection {

    private String path;
    private final ProjectFileBean projectFile;

    public AppCompatInjection(jq jqVar, ProjectFileBean fileBean) {
        projectFile = fileBean;
        String str = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + jqVar.sc_id + "/injection/appcompat/" + fileBean.fileName;
        path = str;
        path = str;
    }

    public static String a() {
        return "[{\"type\":\"toolbar\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"toolbar\",\"value\":\"android:layout_height=\\\"?attr/actionBarSize\\\"\"},{\"type\":\"toolbar\",\"value\":\"android:background=\\\"?attr/colorPrimary\\\"\"},{\"type\":\"toolbar\",\"value\":\"app:popupTheme=\\\"@style/AppTheme.PopupOverlay\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:layout_height=\\\"wrap_content\\\"\"},{\"type\":\"appbarlayout\",\"value\":\"android:theme=\\\"@style/AppTheme.AppBarOverlay\\\"\"},{\"type\":\"coordinatorlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"coordinatorlayout\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"android:layout_width=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"drawerlayout\",\"value\":\"tools:openDrawer=\\\"start\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_width=\\\"320dp\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_height=\\\"match_parent\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:layout_gravity=\\\"start\\\"\"},{\"type\":\"navigationdrawer\",\"value\":\"android:background=\\\"#EEEEEE\\\"\"}]";
    }

    public void inject(Nx nx, String str) {
        if (projectFile.hasActivityOption(1) || projectFile.hasActivityOption(4) || projectFile.hasActivityOption(8)) {
            ArrayList<HashMap<String, Object>> listMap;
            if (!FileUtil.isExistFile(path) || FileUtil.readFile(path).equals("")) {
                listMap = new Gson().fromJson(a(), Helper.TYPE_MAP_LIST);
            } else {
                listMap = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            }
            for (int i = 0; i < listMap.size(); i++) {
                if (listMap.get(i).get("type").toString().equals(str.toLowerCase())) {
                    nx.b(listMap.get(i).get("value").toString());
                }
            }
        }
    }
}
