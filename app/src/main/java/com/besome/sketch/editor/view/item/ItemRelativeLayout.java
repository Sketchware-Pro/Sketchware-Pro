package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;
import com.besome.sketch.editor.view.ScrollContainer;

import a.a.a.wB;

public class ItemRelativeLayout extends RelativeLayout implements ItemView, ScrollContainer {

    private ViewBean viewBean = null;
    private boolean isSelected = false;
    private boolean isFixed = false;
    private Paint paint;

    private Rect rect;

    public ItemRelativeLayout(Context context) {
        super(context);
        initialize(context);
    }

    @Override
    public void reindexChildren() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ItemView editorItem) {
                editorItem.getBean().index = i;
            }
        }
    }

    private void initialize(Context context) {
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
            return;
        }
        int i = 0;
        while (i < childCount && getChildAt(i).getVisibility() != View.GONE) {
            i++;
        }
        super.addView(child, index);
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

    @Override
    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
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
    public void onDraw(@NonNull Canvas canvas) {
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
    public void setChildScrollEnabled(boolean scrollEnabled) {
        for (int i = 0; i < getChildCount(); ++i) {
            View child = getChildAt(i);
            if (child instanceof ScrollContainer) {
                ((ScrollContainer) child).setChildScrollEnabled(scrollEnabled);
            }

            if (child instanceof ItemHorizontalScrollView) {
                ((ItemHorizontalScrollView) child).setScrollEnabled(scrollEnabled);
            }

            if (child instanceof ItemVerticalScrollView) {
                ((ItemVerticalScrollView) child).setScrollEnabled(scrollEnabled);
            }
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) wB.a(getContext(), (float) left), (int) wB.a(getContext(), (float) top), (int) wB.a(getContext(), (float) right), (int) wB.a(getContext(), (float) bottom));
    }
}
