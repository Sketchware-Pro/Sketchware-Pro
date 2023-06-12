package com.besome.sketch.beans;

import com.besome.sketch.lib.base.BaseBean;

public class CollapsibleBean extends BaseBean {
    public int buttonPressed = -1;
    public boolean isCollapsed = true;
    public boolean isConfirmation = false;
    public boolean isSelected = false;

    public void initValue() {
        isCollapsed = true;
        isConfirmation = false;
        isSelected = false;
        buttonPressed = -1;
    }
}
