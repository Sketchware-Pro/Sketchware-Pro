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

    private ViewBean viewBean = null;
    private boolean isSelected = false;
    private boolean isFixed = false;
    private Paint paint;
    private int layoutGravity = 0;

    public ItemLinearLayout(Context context) {
        super(context);
        this.initialize(context);
    }

    @Override
    public void a() {
        int var1 = 0;

        int var4;
        for (int i = 0; var1 < this.getChildCount(); i = var4) {
            View child = this.getChildAt(var1);
            var4 = i;
            if (child instanceof sy) {
                ((sy) child).getBean().index = i;
                var4 = i + 1;
            }

            ++var1;
        }

    }

    private void initialize(Context context) {
        this.setOrientation(0);
        this.setDrawingCacheEnabled(true);
        this.setMinimumWidth((int) wB.a(context, 32.0F));
        this.setMinimumHeight((int) wB.a(context, 32.0F));
        this.paint = new Paint(1);
        this.paint.setStrokeWidth(wB.a(this.getContext(), 2.0F));
    }

    @Override
    public void addView(View child, int index) {
        int childCount = this.getChildCount();
        if (index > childCount) {
            super.addView(child);
        } else {
            byte var4 = -1;
            int var5 = 0;

            int var6;
            while (true) {
                var6 = var4;
                if (var5 >= childCount) {
                    break;
                }

                if (this.getChildAt(var5).getVisibility() == 8) {
                    var6 = var5;
                    break;
                }

                ++var5;
            }

            if (var6 >= 0 && index >= var6) {
                super.addView(child, index + 1);
            } else {
                super.addView(child, index);
            }
        }
    }

    @Override
    public ViewBean getBean() {
        return this.viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.isFixed;
    }

    public int getLayoutGravity() {
        return this.layoutGravity;
    }

    public boolean getSelection() {
        return this.isSelected;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!this.isFixed) {
            if (this.isSelected) {
                this.paint.setColor(-1785080368);
                canvas.drawRect(new Rect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight()), this.paint);
            }
            this.paint.setColor(1610612736);

            int measuredWidth = this.getMeasuredWidth();
            int measuredHeight = this.getMeasuredHeight();

            canvas.drawLine(0.0F, 0.0F, (float) measuredWidth, 0.0F, this.paint);
            canvas.drawLine(0.0F, 0.0F, 0.0F, (float) measuredHeight, this.paint);
            canvas.drawLine((float) measuredWidth, 0.0F, (float) measuredWidth, (float) measuredHeight, this.paint);
            canvas.drawLine(0.0F, (float) measuredHeight, (float) measuredWidth, (float) measuredHeight, this.paint);
        }

        super.onDraw(canvas);
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    @Override
    public void setChildScrollEnabled(boolean childScrollEnabled) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            View child = this.getChildAt(i);
            if (child instanceof ty) {
                ((ty) child).setChildScrollEnabled(childScrollEnabled);
            }

            if (child instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) child).setScrollEnabled(childScrollEnabled);
            }

            if (child instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) child).setScrollEnabled(childScrollEnabled);
            }
        }
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public void setLayoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
        super.setGravity(layoutGravity);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) wB.a(this.getContext(), (float) left), (int) wB.a(this.getContext(), (float) top), (int) wB.a(this.getContext(), (float) right), (int) wB.a(this.getContext(), (float) bottom));
    }

    @Override
    public void setSelection(boolean selected) {
        this.isSelected = selected;
        this.invalidate();
    }
}
