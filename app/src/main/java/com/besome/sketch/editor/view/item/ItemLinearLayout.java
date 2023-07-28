package com.besome.sketch.editor.view.item;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;

public class ItemLinearLayout extends LinearLayout implements sy, ty {

    public ViewBean a = null;
    public boolean b = false;
    public boolean c = false;
    public Paint d;
    public int e = 0;

    public ItemLinearLayout(Context var1) {
        super(var1);
        this.a(var1);
    }

    public void a() {
        int var1 = 0;

        int var4;
        for (int var2 = 0; var1 < this.getChildCount(); var2 = var4) {
            View var3 = this.getChildAt(var1);
            var4 = var2;
            if (var3 instanceof sy) {
                ((sy) var3).getBean().index = var2;
                var4 = var2 + 1;
            }

            ++var1;
        }

    }

    public final void a(Context var1) {
        this.setOrientation(0);
        this.setDrawingCacheEnabled(true);
        this.setMinimumWidth((int) wB.a(var1, 32.0F));
        this.setMinimumHeight((int) wB.a(var1, 32.0F));
        this.d = new Paint(1);
        this.d.setStrokeWidth(wB.a(this.getContext(), 2.0F));
    }

    public void addView(View var1, int var2) {
        int var3 = this.getChildCount();
        if (var2 > var3) {
            super.addView(var1);
        } else {
            byte var4 = -1;
            int var5 = 0;

            int var6;
            while (true) {
                var6 = var4;
                if (var5 >= var3) {
                    break;
                }

                if (this.getChildAt(var5).getVisibility() == 8) {
                    var6 = var5;
                    break;
                }

                ++var5;
            }

            if (var6 >= 0 && var2 >= var6) {
                super.addView(var1, var2 + 1);
            } else {
                super.addView(var1, var2);
            }
        }
    }

    public ViewBean getBean() {
        return this.a;
    }

    public boolean getFixed() {
        return this.c;
    }

    public int getLayoutGravity() {
        return this.e;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void onDraw(Canvas var1) {
        if (!this.c) {
            if (this.b) {
                this.d.setColor(-1785080368);
                var1.drawRect(new Rect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight()), this.d);
            }

            this.d.setColor(1610612736);
            int var2 = this.getMeasuredWidth();
            int var3 = this.getMeasuredHeight();
            float var4 = (float) var2;
            var1.drawLine(0.0F, 0.0F, var4, 0.0F, this.d);
            float var5 = (float) var3;
            var1.drawLine(0.0F, 0.0F, 0.0F, var5, this.d);
            var1.drawLine(var4, 0.0F, var4, var5, this.d);
            var1.drawLine(0.0F, var5, var4, var5, this.d);
        }

        super.onDraw(var1);
    }

    public void setBean(ViewBean var1) {
        this.a = var1;
    }

    public void setChildScrollEnabled(boolean var1) {
        for (int var2 = 0; var2 < this.getChildCount(); ++var2) {
            View var3 = this.getChildAt(var2);
            if (var3 instanceof ty) {
                ((ty) var3).setChildScrollEnabled(var1);
            }

            if (var3 instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) var3).setScrollEnabled(var1);
            }

            if (var3 instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) var3).setScrollEnabled(var1);
            }
        }

    }

    public void setFixed(boolean var1) {
        this.c = var1;
    }

    public void setLayoutGravity(int var1) {
        this.e = var1;
        super.setGravity(var1);
    }

    public void setPadding(int var1, int var2, int var3, int var4) {
        super.setPadding((int) wB.a(this.getContext(), (float) var1), (int) wB.a(this.getContext(), (float) var2), (int) wB.a(this.getContext(), (float) var3), (int) wB.a(this.getContext(), (float) var4));
    }

    public void setSelection(boolean var1) {
        this.b = var1;
        this.invalidate();
    }
}
