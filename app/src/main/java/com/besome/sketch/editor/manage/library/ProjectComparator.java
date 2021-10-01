package com.besome.sketch.editor.manage.library;

import java.util.Comparator;
import java.util.HashMap;

import a.a.a.yB;

public class ProjectComparator implements Comparator<HashMap<String, Object>> {

    public ProjectComparator() {}

    public int compare(HashMap<String, Object> map1, HashMap<String, Object> map2) {
        return yB.c(map1, "sc_id").compareTo(yB.c(map2, "sc_id")) * -1;
    }
}
