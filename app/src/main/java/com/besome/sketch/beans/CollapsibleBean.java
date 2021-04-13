package com.besome.sketch.beans;

import a.a.a.nA;

public class CollapsibleBean extends nA {
    public int buttonPressed = -1;
    public boolean isCollapsed = true;
    public boolean isConfirmation = false;
    public boolean isSelected = false;

    public void initValue() {
        this.isCollapsed = true;
        this.isConfirmation = false;
        this.isSelected = false;
        this.buttonPressed = -1;
    }
}
