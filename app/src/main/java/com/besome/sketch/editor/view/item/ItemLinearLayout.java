package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.ty;
import a.a.a.wB;

public class ItemLinearLayout extends LinearLayout implements sy, ty {

    private ViewBean viewBean = null;
    private boolean isSelected = false;
    private boolean isFixed = false;
    private Paint paint;

    private Rect rect;
    private int layoutGravity = 0;

    public ItemLinearLayout(Context context) {
        super(context);
        initialize(context);
    }

    @Override
    public void a() {
        int var1 = 0;

        int var4;
        for (int i = 0; var1 < getChildCount(); i = var4) {
            View child = getChildAt(var1);
            var4 = i;
            if (child instanceof sy) {
                ((sy) child).getBean().index = i;
                var4 = i + 1;
            }

            ++var1;
        }

    }

    private void initialize(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(context, 32.0F));
        setMinimumHeight((int) wB.a(context, 32.0F));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(wB.a(getContext(), 2.0F));
        rect = new Rect();
    }

    @Override
    public void addView(View child, int index) {
        int childCount = getChildCount();
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

                if (getChildAt(var5).getVisibility() == View.GONE) {
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
        return viewBean;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    @Override
    public boolean getFixed() {
        return isFixed;
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    public int getLayoutGravity() {
        return layoutGravity;
    }

    public void setLayoutGravity(int layoutGravity) {
        this.layoutGravity = layoutGravity;
        super.setGravity(layoutGravity);
    }

    public boolean getSelection() {
        return isSelected;
    }

    @Override
    public void setSelection(boolean selected) {
        isSelected = selected;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!isFixed) {
            if (isSelected) {
                paint.setColor(0x9599d5d0);
                rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
                canvas.drawRect(rect, paint);
            }
            paint.setColor(0x60000000);

            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();

            canvas.drawLine(0.0F, 0.0F, (float) measuredWidth, 0.0F, paint);
            canvas.drawLine(0.0F, 0.0F, 0.0F, (float) measuredHeight, paint);
            canvas.drawLine((float) measuredWidth, 0.0F, (float) measuredWidth, (float) measuredHeight, paint);
            canvas.drawLine(0.0F, (float) measuredHeight, (float) measuredWidth, (float) measuredHeight, paint);
        }

        super.onDraw(canvas);
    }

    @Override
    public void setChildScrollEnabled(boolean childScrollEnabled) {
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
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

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) wB.a(getContext(), (float) left), (int) wB.a(getContext(), (float) top), (int) wB.a(getContext(), (float) right), (int) wB.a(getContext(), (float) bottom));
    }
}
