package com.besome.sketch.beans;

import a.a.a.nA;

public class HistoryBean extends nA {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    public int actionType;
    public nA currentData;
    public nA prevData;

    public HistoryBean(int i, nA nAVar, nA nAVar2) {
        actionType = i;
        prevData = nAVar;
        currentData = nAVar2;
    }

    public int getActionType() {
        return actionType;
    }

    public nA getCurrentData() {
        return currentData;
    }

    public nA getPrevData() {
        return prevData;
    }
}
