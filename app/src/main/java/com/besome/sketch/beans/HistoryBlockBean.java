package com.besome.sketch.beans;

import java.util.ArrayList;

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

    public void actionAdd(ArrayList<BlockBean> addedData, int currentX, int currentY, BlockBean prevParentData, BlockBean currentParentData) {
        actionType = ACTION_TYPE_ADD;
        this.currentX = currentX;
        this.currentY = currentY;
        this.addedData = new ArrayList<>();
        this.prevParentData = prevParentData;
        this.currentParentData = currentParentData;
        for (BlockBean bean : addedData) {
            this.addedData.add(bean.clone());
        }
    }

    public void actionMove(ArrayList<BlockBean> beforeMove, ArrayList<BlockBean> afterMove, int prevX, int prevY, int currentX, int currentY, BlockBean prevOriginalParent, BlockBean currentOriginalParent, BlockBean preParentData, BlockBean currentParentData) {
        actionType = ACTION_TYPE_MOVE;
        this.prevX = prevX;
        this.prevY = prevY;
        this.currentX = currentX;
        this.currentY = currentY;
        this.prevParentData = preParentData;
        this.currentParentData = currentParentData;
        this.prevOriginalParent = prevOriginalParent;
        this.currentOriginalParent = currentOriginalParent;
        this.beforeMove = beforeMove;
        this.afterMove = afterMove;
    }

    public void actionRemove(ArrayList<BlockBean> removedData, int currentX, int currentY, BlockBean prevParentData, BlockBean currentParentData) {
        actionType = ACTION_TYPE_REMOVE;
        this.currentX = currentX;
        this.currentY = currentY;
        this.prevParentData = prevParentData;
        this.currentParentData = currentParentData;
        this.removedData = new ArrayList<>();
        for (BlockBean bean : removedData) {
            this.removedData.add(bean.clone());
        }
    }

    public void actionUpdate(BlockBean prevUpdateData, BlockBean currentUpdateData) {
        actionType = ACTION_TYPE_UPDATE;
        this.prevUpdateData = prevUpdateData.clone();
        this.currentUpdateData = currentUpdateData.clone();
    }

    public void copy(HistoryBlockBean bean) {
        actionType = bean.actionType;
        if (bean.prevUpdateData != null) {
            prevUpdateData = bean.prevUpdateData.clone();
        }
        if (bean.currentUpdateData != null) {
            currentUpdateData = bean.currentUpdateData.clone();
        }
        if (bean.beforeMove != null) {
            beforeMove = new ArrayList<>();
            for (BlockBean beforeMoveBean : bean.beforeMove) {
                beforeMove.add(beforeMoveBean.clone());
            }
        }
        if (bean.afterMove != null) {
            afterMove = new ArrayList<>();
            for (BlockBean afterMoveBean : bean.afterMove) {
                afterMove.add(afterMoveBean.clone());
            }
        }
        if (bean.addedData != null) {
            addedData = new ArrayList<>();
            for (BlockBean addedDatum : bean.addedData) {
                addedData.add(addedDatum.clone());
            }
        }
        if (bean.removedData != null) {
            removedData = new ArrayList<>();
            for (BlockBean removedDatum : bean.removedData) {
                removedData.add(removedDatum.clone());
            }
        }
        prevX = bean.prevX;
        prevY = bean.prevY;
        currentX = bean.currentX;
        currentY = bean.currentY;
        if (bean.prevParentData != null) {
            prevParentData = bean.prevParentData.clone();
        }
        if (bean.currentParentData != null) {
            currentParentData = bean.currentParentData.clone();
        }
        if (bean.prevOriginalParent != null) {
            prevOriginalParent = bean.prevOriginalParent.clone();
        }
        if (bean.currentOriginalParent != null) {
            currentOriginalParent = bean.currentOriginalParent.clone();
        }
    }

    public int getActionType() {
        return actionType;
    }

    public ArrayList<BlockBean> getAddedData() {
        return addedData;
    }

    public ArrayList<BlockBean> getAfterMoveData() {
        return afterMove;
    }

    public ArrayList<BlockBean> getBeforeMoveData() {
        return beforeMove;
    }

    public BlockBean getCurrentOriginalParent() {
        return currentOriginalParent;
    }

    public BlockBean getCurrentParentData() {
        return currentParentData;
    }

    public BlockBean getCurrentUpdateData() {
        return currentUpdateData;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public BlockBean getPrevOriginalParent() {
        return prevOriginalParent;
    }

    public BlockBean getPrevParentData() {
        return prevParentData;
    }

    public BlockBean getPrevUpdateData() {
        return prevUpdateData;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public ArrayList<BlockBean> getRemovedData() {
        return removedData;
    }

    @Override
    public HistoryBlockBean clone() {
        HistoryBlockBean historyBlockBean = new HistoryBlockBean();
        historyBlockBean.copy(this);
        return historyBlockBean;
    }
}
