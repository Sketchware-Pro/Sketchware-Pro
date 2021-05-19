package com.besome.sketch.editor.view.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;
import com.google.android.flexbox.FlexItem;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

public class ItemLinearLayout extends LinearLayout implements sy, ty {
    public ViewBean a = null;
    public boolean b = false;
    public boolean c = false;
    public Paint d;
    public int e = 0;

    public ItemLinearLayout(Context context) {
        super(context);
        a(context);
    }

    @SuppressLint("WrongConstant")
    public final void a(Context context) {
        setOrientation(0);
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(context, 32.0f));
        setMinimumHeight((int) wB.a(context, 32.0f));
        this.d = new Paint(1);
        this.d.setStrokeWidth(wB.a(getContext(), 2.0f));
    }

    @SuppressLint("WrongConstant")
    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        int childCount = getChildCount();
        if (i > childCount) {
            super.addView(view);
            return;
        }
        int i2 = -1;
        int i3 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            } else if (getChildAt(i3).getVisibility() == 8) {
                i2 = i3;
                break;
            } else {
                i3++;
            }
        }
        if (i2 < 0 || i < i2) {
            super.addView(view, i);
        } else {
            super.addView(view, i + 1);
        }
    }

    public ViewBean getBean() {
        return this.a;
    }

    public void setBean(ViewBean viewBean) {
        this.a = viewBean;
    }

    public boolean getFixed() {
        return this.c;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public int getLayoutGravity() {
        return this.e;
    }

    public void setLayoutGravity(int i) {
        this.e = i;
        super.setGravity(i);
    }

    public boolean getSelection() {
        return this.b;
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (!this.c) {
            if (this.b) {
                this.d.setColor(-1785080368);
                canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
            }
            this.d.setColor(1610612736);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = (float) measuredWidth;
            canvas.drawLine(FlexItem.FLEX_GROW_DEFAULT, FlexItem.FLEX_GROW_DEFAULT, f, FlexItem.FLEX_GROW_DEFAULT, this.d);
            float f2 = (float) measuredHeight;
            canvas.drawLine(FlexItem.FLEX_GROW_DEFAULT, FlexItem.FLEX_GROW_DEFAULT, FlexItem.FLEX_GROW_DEFAULT, f2, this.d);
            canvas.drawLine(f, FlexItem.FLEX_GROW_DEFAULT, f, f2, this.d);
            canvas.drawLine(FlexItem.FLEX_GROW_DEFAULT, f2, f, f2, this.d);
        }
        super.onDraw(canvas);
    }

    public void setChildScrollEnabled(boolean z) {
        for (int i = 0; i < getChildCount(); i++) {
            ty childAt = (ty) getChildAt(i);
            if (childAt != null) {
                childAt.setChildScrollEnabled(z);
            }
            if (childAt instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) childAt).setScrollEnabled(z);
            }
            if (childAt instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) childAt).setScrollEnabled(z);
            }
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding((int) wB.a(getContext(), (float) i), (int) wB.a(getContext(), (float) i2), (int) wB.a(getContext(), (float) i3), (int) wB.a(getContext(), (float) i4));
    }

    public void a() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            sy childAt = (sy) getChildAt(i2);
            if (childAt != null) {
                childAt.getBean().index = i;
                i++;
            }
        }
    }
}
