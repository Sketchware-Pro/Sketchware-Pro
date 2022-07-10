package com.besome.sketch.beans;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.nA;

public class HistoryViewBean extends nA {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_MOVE = 3;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    public int actionType;
    public ArrayList<ViewBean> addedData;
    public ViewBean currentUpdateData;
    public ViewBean moveData;
    public ViewBean prevUpdateData;
    public ArrayList<ViewBean> removedData;

    public void actionAdd(ArrayList<ViewBean> arrayList) {
        this.actionType = 0;
        this.addedData = new ArrayList<>();
        for (ViewBean bean : arrayList) {
            ViewBean viewBean = new ViewBean();
            viewBean.copy(bean);
            this.addedData.add(viewBean);
        }
    }

    public void actionMove(ViewBean viewBean) {
        this.actionType = 3;
        this.moveData = new ViewBean();
        this.moveData.copy(viewBean);
    }

    public void actionRemove(ArrayList<ViewBean> arrayList) {
        this.actionType = 2;
        this.removedData = new ArrayList<>();
        for (ViewBean bean : arrayList) {
            ViewBean viewBean = new ViewBean();
            viewBean.copy(bean);
            this.removedData.add(viewBean);
        }
    }

    public void actionUpdate(ViewBean viewBean, ViewBean viewBean2) {
        this.actionType = 1;
        this.prevUpdateData = new ViewBean();
        this.prevUpdateData.copy(viewBean);
        this.currentUpdateData = new ViewBean();
        this.currentUpdateData.copy(viewBean2);
    }

    public void copy(HistoryViewBean historyViewBean) {
        this.actionType = historyViewBean.actionType;
        if (historyViewBean.prevUpdateData != null) {
            this.prevUpdateData = new ViewBean();
            this.prevUpdateData.copy(historyViewBean.prevUpdateData);
        }
        if (historyViewBean.currentUpdateData != null) {
            this.currentUpdateData = new ViewBean();
            this.currentUpdateData.copy(historyViewBean.currentUpdateData);
        }
        if (historyViewBean.moveData != null) {
            this.moveData = new ViewBean();
            this.moveData.copy(historyViewBean.moveData);
        }
        if (historyViewBean.addedData != null) {
            this.addedData = new ArrayList<>();
            for (ViewBean addedDatum : historyViewBean.addedData) {
                ViewBean viewBean = new ViewBean();
                viewBean.copy(addedDatum);
                this.addedData.add(viewBean);
            }
        }
        if (historyViewBean.removedData != null) {
            this.removedData = new ArrayList<>();
            for (ViewBean removedDatum : historyViewBean.removedData) {
                ViewBean viewBean2 = new ViewBean();
                viewBean2.copy(removedDatum);
                this.removedData.add(viewBean2);
            }
        }
    }

    public int getActionType() {
        return this.actionType;
    }

    public ArrayList<ViewBean> getAddedData() {
        return this.addedData;
    }

    public ViewBean getCurrentUpdateData() {
        return this.currentUpdateData;
    }

    public ViewBean getMovedData() {
        return this.moveData;
    }

    public ViewBean getPrevUpdateData() {
        return this.prevUpdateData;
    }

    public ArrayList<ViewBean> getRemovedData() {
        return this.removedData;
    }

    @Override
    public HistoryViewBean clone() {
        HistoryViewBean historyViewBean = new HistoryViewBean();
        historyViewBean.copy(this);
        return historyViewBean;
    }
}
