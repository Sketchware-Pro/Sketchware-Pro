package com.besome.sketch.beans;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.nA;

public class HistoryBlockBean extends nA {
    public static final int ACTION_TYPE_ADD = 0;
    public static final int ACTION_TYPE_MOVE = 3;
    public static final int ACTION_TYPE_REMOVE = 2;
    public static final int ACTION_TYPE_UPDATE = 1;
    public int actionType;
    public ArrayList<BlockBean> addedData;
    public ArrayList<BlockBean> afterMove;
    public ArrayList<BlockBean> beforeMove;
    public BlockBean currentOriginalParent;
    public BlockBean currentParentData;
    public BlockBean currentUpdateData;
    public int currentX;
    public int currentY;
    public BlockBean prevOriginalParent;
    public BlockBean prevParentData;
    public BlockBean prevUpdateData;
    public int prevX;
    public int prevY;
    public ArrayList<BlockBean> removedData;

    public void actionAdd(ArrayList<BlockBean> arrayList, int i, int i2, BlockBean blockBean, BlockBean blockBean2) {
        this.actionType = 0;
        this.currentX = i;
        this.currentY = i2;
        this.addedData = new ArrayList<>();
        this.prevParentData = blockBean;
        this.currentParentData = blockBean2;
        for (BlockBean bean : arrayList) {
            this.addedData.add(bean.clone());
        }
    }

    public void actionMove(ArrayList<BlockBean> arrayList, ArrayList<BlockBean> arrayList2, int i, int i2, int i3, int i4, BlockBean blockBean, BlockBean blockBean2, BlockBean blockBean3, BlockBean blockBean4) {
        this.actionType = 3;
        this.prevX = i;
        this.prevY = i2;
        this.currentX = i3;
        this.currentY = i4;
        this.prevParentData = blockBean3;
        this.currentParentData = blockBean4;
        this.prevOriginalParent = blockBean;
        this.currentOriginalParent = blockBean2;
        this.beforeMove = arrayList;
        this.afterMove = arrayList2;
    }

    public void actionRemove(ArrayList<BlockBean> arrayList, int i, int i2, BlockBean blockBean, BlockBean blockBean2) {
        this.actionType = 2;
        this.currentX = i;
        this.currentY = i2;
        this.prevParentData = blockBean;
        this.currentParentData = blockBean2;
        this.removedData = new ArrayList<>();
        for (BlockBean bean : arrayList) {
            this.removedData.add(bean.clone());
        }
    }

    public void actionUpdate(BlockBean blockBean, BlockBean blockBean2) {
        this.actionType = 1;
        this.prevUpdateData = blockBean.clone();
        this.currentUpdateData = blockBean2.clone();
    }

    public void copy(HistoryBlockBean historyBlockBean) {
        this.actionType = historyBlockBean.actionType;
        BlockBean blockBean = historyBlockBean.prevUpdateData;
        if (blockBean != null) {
            this.prevUpdateData = blockBean.clone();
        }
        BlockBean blockBean2 = historyBlockBean.currentUpdateData;
        if (blockBean2 != null) {
            this.currentUpdateData = blockBean2.clone();
        }
        if (historyBlockBean.beforeMove != null) {
            this.beforeMove = new ArrayList<>();
            for (BlockBean bean : historyBlockBean.beforeMove) {
                this.beforeMove.add(bean.clone());
            }
        }
        if (historyBlockBean.afterMove != null) {
            this.afterMove = new ArrayList<>();
            for (BlockBean bean : historyBlockBean.afterMove) {
                this.afterMove.add(bean.clone());
            }
        }
        if (historyBlockBean.addedData != null) {
            this.addedData = new ArrayList<>();
            for (BlockBean addedDatum : historyBlockBean.addedData) {
                this.addedData.add(addedDatum.clone());
            }
        }
        if (historyBlockBean.removedData != null) {
            this.removedData = new ArrayList<>();
            for (BlockBean removedDatum : historyBlockBean.removedData) {
                this.removedData.add(removedDatum.clone());
            }
        }
        this.prevX = historyBlockBean.prevX;
        this.prevY = historyBlockBean.prevY;
        this.currentX = historyBlockBean.currentX;
        this.currentY = historyBlockBean.currentY;
        BlockBean blockBean3 = historyBlockBean.prevParentData;
        if (blockBean3 != null) {
            this.prevParentData = blockBean3.clone();
        }
        BlockBean blockBean4 = historyBlockBean.currentParentData;
        if (blockBean4 != null) {
            this.currentParentData = blockBean4.clone();
        }
        BlockBean blockBean5 = historyBlockBean.prevOriginalParent;
        if (blockBean5 != null) {
            this.prevOriginalParent = blockBean5.clone();
        }
        BlockBean blockBean6 = historyBlockBean.currentOriginalParent;
        if (blockBean6 != null) {
            this.currentOriginalParent = blockBean6.clone();
        }
    }

    public int getActionType() {
        return this.actionType;
    }

    public ArrayList<BlockBean> getAddedData() {
        return this.addedData;
    }

    public ArrayList<BlockBean> getAfterMoveData() {
        return this.afterMove;
    }

    public ArrayList<BlockBean> getBeforeMoveData() {
        return this.beforeMove;
    }

    public BlockBean getCurrentOriginalParent() {
        return this.currentOriginalParent;
    }

    public BlockBean getCurrentParentData() {
        return this.currentParentData;
    }

    public BlockBean getCurrentUpdateData() {
        return this.currentUpdateData;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public BlockBean getPrevOriginalParent() {
        return this.prevOriginalParent;
    }

    public BlockBean getPrevParentData() {
        return this.prevParentData;
    }

    public BlockBean getPrevUpdateData() {
        return this.prevUpdateData;
    }

    public int getPrevX() {
        return this.prevX;
    }

    public int getPrevY() {
        return this.prevY;
    }

    public ArrayList<BlockBean> getRemovedData() {
        return this.removedData;
    }

    @Override
    public HistoryBlockBean clone() {
        HistoryBlockBean historyBlockBean = new HistoryBlockBean();
        historyBlockBean.copy(this);
        return historyBlockBean;
    }
}
