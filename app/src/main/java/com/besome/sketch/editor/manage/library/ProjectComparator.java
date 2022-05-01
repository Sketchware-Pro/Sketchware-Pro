package com.besome.sketch.editor.manage.library;

import java.util.Comparator;
import java.util.HashMap;

import a.a.a.yB;

public class ProjectComparator implements Comparator<HashMap<String, Object>> {

    public ProjectComparator() {
    }

    @Override
    public int compare(HashMap<String, Object> first, HashMap<String, Object> second) {
        return Integer.compare(
                Integer.parseInt(yB.c(first, "sc_id")),
                Integer.parseInt(yB.c(second, "sc_id"))) * -1;
    }
}
