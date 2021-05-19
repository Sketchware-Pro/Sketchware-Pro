package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;

import com.besome.sketch.beans.ViewBean;
import com.google.android.flexbox.FlexItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import a.a.a.sy;

public class ItemFloatingActionButton extends FloatingActionButton implements sy {
    public int maincolor;
    public ViewBean r;
    public boolean s;
    public boolean t;

    public ItemFloatingActionButton(Context context) {
        super(context);
        a(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.besome.sketch.editor.view.item.ItemFloatingActionButton */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(Context context) {
        setCompatElevation((float) FlexItem.FLEX_GROW_DEFAULT);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.r;
    }

    public void setBean(ViewBean viewBean) {
        this.r = viewBean;
    }

    public boolean getFixed() {
        return this.t;
    }

    public void setFixed(boolean z) {
        this.t = z;
    }

    public boolean getSelection() {
        return this.s;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.besome.sketch.editor.view.item.ItemFloatingActionButton */
    /* JADX WARN: Multi-variable type inference failed */
    public void setSelection(boolean z) {
        this.s = z;
        invalidate();
    }

    public void setMainColor(int i) {
        this.maincolor = i;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.besome.sketch.editor.view.item.ItemFloatingActionButton */
    /* JADX WARN: Multi-variable type inference failed */
    public void onDraw(Canvas canvas) {
        if (this.s) {
            setBackgroundTintList(ColorStateList.valueOf(-1785080368));
        } else {
            setBackgroundTintList(this.maincolor != 0 ? ColorStateList.valueOf(this.maincolor) : ColorStateList.valueOf(getResources().getColor(2131034159)));
        }
        ItemFloatingActionButton.super.onDraw(canvas);
    }
}
