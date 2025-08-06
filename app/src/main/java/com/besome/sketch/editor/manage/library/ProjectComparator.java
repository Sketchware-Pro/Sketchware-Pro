package com.besome.sketch.editor.manage.library;

import java.util.Comparator;
import java.util.HashMap;

import a.a.a.yB;

public class ProjectComparator implements Comparator<HashMap<String, Object>> {

    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_ID = 2;
    public static final int SORT_ORDER_ASCENDING = 4;
    public static final int SORT_ORDER_DESCENDING = 8;
    public static final int DEFAULT = SORT_BY_ID | SORT_ORDER_DESCENDING;

    private int sortBy = 0;

    public ProjectComparator() {
    }

    public ProjectComparator(int sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public int compare(HashMap<String, Object> first, HashMap<String, Object> second) {
        boolean isSortOrderAscending = (sortBy & SORT_ORDER_ASCENDING) == SORT_ORDER_ASCENDING;
        if ((sortBy & SORT_BY_ID) == SORT_BY_ID) {
            return Integer.compare(
                    Integer.parseInt(yB.c(first, "sc_id")),
                    Integer.parseInt(yB.c(second, "sc_id"))) * (isSortOrderAscending ? 1 : -1);
        } else if ((sortBy & SORT_BY_NAME) == SORT_BY_NAME) {
            return yB.c(first, "my_ws_name").compareTo(yB.c(second, "my_ws_name")) * (isSortOrderAscending ? 1 : -1);
        } else {
            return Integer.compare(
                    Integer.parseInt(yB.c(first, "sc_id")),
                    Integer.parseInt(yB.c(second, "sc_id"))) * -1;
        }
    }
}
