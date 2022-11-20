package com.besome.sketch.beans;

import a.a.a.nA;

public class SelectableBean extends nA {
    public static final int SAVED_POS_ETC = 2;
    public static final int SAVED_POS_NONE = -1;
    public static final int SAVED_POS_SKETCHWARE_DATA = 0;
    public static final int SAVED_POS_STORAGE = 1;
    public boolean isNew;
    public boolean isSelected;
    public int savedPos;

    public SelectableBean() {
        this(-1);
    }

    public SelectableBean(int position) {
        this(position, false);
    }

    public SelectableBean(int savedPosition, boolean isnew) {
        savedPos = -1;
        isSelected = false;
        isNew = false;
        isSelected = false;
        savedPos = savedPosition;
        isNew = isnew;
    }
}
