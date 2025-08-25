package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;
import com.besome.sketch.editor.view.ScrollContainer;
import com.google.android.material.card.MaterialCardView;

import a.a.a.wB;

public class ItemCardView extends MaterialCardView implements ItemView, ScrollContainer {

    private final Rect rect = new Rect();
    private ViewBean viewBean;
    private boolean isSelected;
    private boolean isFixed;
    private Paint paint;

    public ItemCardView(Context context) {
        super(context);
        initialize(context);
    }

    @Override
    public void reindexChildren() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof ItemView) {
                ((ItemView) child).getBean().index = i;
            }
        }
    }

    private void initialize(Context context) {
        setDrawingCacheEnabled(true);
        setMinimumWidth((int) wB.a(context, 32.0f));
        setMinimumHeight((int) wB.a(context, 32.0f));
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void addView(View child, int i) {
        int childCount = getChildCount();
        if (i > childCount) {
            addView(child);
            return;
        }
        int index = 0;
        while (index < childCount && getChildAt(index).getVisibility() != View.GONE) {
            index++;
        }
        super.addView(child, i);
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
    public void setFixed(boolean fixed) {
        isFixed = fixed;
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
        if (isSelected) {
            paint.setColor(0x9599D5D0);
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setCardBackgroundColor(color == 0x00FFFFFF ? 0xFFFFFFFF : color);
    }

    @Override
    public void setChildScrollEnabled(boolean scrollEnabled) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
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
    public void setContentPadding(int left, int top, int right, int bottom) {
        super.setContentPadding((int) wB.a(getContext(), (float) left),
                (int) wB.a(getContext(), (float) top),
                (int) wB.a(getContext(), (float) right),
                (int) wB.a(getContext(), (float) bottom));
    }
}