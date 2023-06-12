package com.besome.sketch.beans;

import com.besome.sketch.lib.base.BaseBean;

public class HistoryBean extends BaseBean {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    public int actionType;
    public BaseBean currentData;
    public BaseBean prevData;

    public HistoryBean(int actionType, BaseBean prevData, BaseBean currentData) {
        actionType = actionType;
        prevData = prevData;
        currentData = currentData;
    }

    public int getActionType() {
        return actionType;
    }

    public BaseBean getCurrentData() {
        return currentData;
    }

    public BaseBean getPrevData() {
        return prevData;
    }
}
