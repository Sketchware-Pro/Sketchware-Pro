package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;

import com.besome.sketch.beans.ViewBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sketchware.remod.R;

import a.a.a.sy;

public class ItemFloatingActionButton extends FloatingActionButton implements sy {

    public int maincolor;
    public ViewBean r;
    public boolean s;
    public boolean t;

    public ItemFloatingActionButton(Context var1) {
        super(var1);
        a(var1);
    }

    public void a(Context var1) {
        setCompatElevation(0.0F);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return r;
    }

    public boolean getFixed() {
        return t;
    }

    public boolean getSelection() {
        return s;
    }

    public void onDraw(Canvas var1) {
        if (s) {
            setBackgroundTintList(ColorStateList.valueOf(0x9599d5d0));
        } else {
            ColorStateList var2 = ColorStateList.valueOf(getResources().getColor(R.color.color_accent));
            if (maincolor != 0) {
                var2 = ColorStateList.valueOf(maincolor);
            }

            setBackgroundTintList(var2);
        }

        super.onDraw(var1);
    }

    public void setBean(ViewBean var1) {
        r = var1;
    }

    public void setFixed(boolean var1) {
        t = var1;
    }

    public void setMainColor(int var1) {
        maincolor = var1;
    }

    public void setSelection(boolean var1) {
        s = var1;
        invalidate();
    }
}
