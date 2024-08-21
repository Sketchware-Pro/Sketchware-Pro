//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.widget.ImageButton;

import com.besome.sketch.beans.ViewBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import a.a.a.sy;

public class ItemFloatingActionButton extends FloatingActionButton implements sy {
    public int maincolor;
    public ViewBean r;
    public boolean s;
    public boolean t;

    public ItemFloatingActionButton(Context var1) {
        super(var1);
        this.a(var1);
    }

    public void a(Context var1) {
        ((FloatingActionButton)this).setCompatElevation(0.0F);
        ((ImageButton)this).setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.r;
    }

    public boolean getFixed() {
        return this.t;
    }

    public boolean getSelection() {
        return this.s;
    }

    public void onDraw(Canvas var1) {
        if (this.s) {
            this.setBackgroundTintList(ColorStateList.valueOf(-1785080368));
        } else {
            ColorStateList var2 = ColorStateList.valueOf(this.getResources().getColor(2131034159));
            if (this.maincolor != 0) {
                var2 = ColorStateList.valueOf(this.maincolor);
            }

            this.setBackgroundTintList(var2);
        }

        super.onDraw(var1);
    }

    public void setBean(ViewBean var1) {
        this.r = var1;
    }

    public void setFixed(boolean var1) {
        this.t = var1;
    }

    public void setMainColor(int var1) {
        this.maincolor = var1;
    }

    public void setSelection(boolean var1) {
        this.s = var1;
        ((ImageButton)this).invalidate();
    }
}
