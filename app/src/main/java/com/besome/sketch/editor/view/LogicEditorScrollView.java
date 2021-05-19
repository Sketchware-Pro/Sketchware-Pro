package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.google.android.flexbox.FlexItem;

public class LogicEditorScrollView extends FrameLayout {

    public float a = FlexItem.FLEX_GROW_DEFAULT;
    public float b = FlexItem.FLEX_GROW_DEFAULT;
    public int c = 0;
    public boolean d = false;
    public boolean e = true;
    public boolean f = true;
    public float g = -1.0f;
    public float h = -1.0f;

    public LogicEditorScrollView(Context context) {
        super(context);
        a(context);
    }

    public LogicEditorScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public void a(Context context) {
        c = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void addView(View view) {
        if (getChildCount() <= 1) {
            super.addView(view);
            return;
        }
        throw new IllegalStateException("BothDirectionScrollView should have child View only one");
    }

    public boolean getScrollEnabled() {
        return e;
    }

    public void setScrollEnabled(boolean z) {
        e = z;
    }

    public boolean getUseScroll() {
        return f;
    }

    public void setUseScroll(boolean z) {
        f = z;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!a()) {
            return false;
        }

        switch (motionEvent.getAction()) {
            case 2:
                if (d) return true;
                d = Math.abs(a - motionEvent.getX()) > c || Math.abs(b - motionEvent.getY()) > c;
                break;

            case 0:
                a = motionEvent.getX();
                b = motionEvent.getY();
                d = false;
                break;

            case 1:
                d = false;
                break;
        }
        return d;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        int i2 = 0;
        if (!a()) {
            return false;
        }
        View childAt = getChildAt(0);
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (action == 0) {
            g = x;
            h = y;
        } else if (action == 1) {
            g = -1.0f;
            h = -1.0f;
        } else if (action == 2) {
            if (g < FlexItem.FLEX_GROW_DEFAULT) {
                g = x;
            }
            if (h < FlexItem.FLEX_GROW_DEFAULT) {
                h = y;
            }
            int i3 = (int) (g - x);
            int i4 = (int) (h - y);
            g = x;
            h = y;
            if (i3 <= 0) {
                if (getScrollX() <= 0) {
                    i3 = 0;
                }
                i = Math.max(-getScrollX(), i3);
            } else {
                int right = ((childAt.getRight() - getScrollX()) - getWidth()) - getPaddingRight();
                i = right > 0 ? Math.min(right, i3) : 0;
            }
            if (i4 <= 0) {
                if (getScrollY() <= 0) {
                    i4 = 0;
                }
                i2 = Math.max(-getScrollY(), i4);
            } else {
                int bottom = ((childAt.getBottom() - getScrollY()) - getHeight()) - getPaddingBottom();
                if (bottom > 0) {
                    i2 = Math.min(bottom, i4);
                }
            }
            if (!(i == 0 && i2 == 0)) {
                scrollBy(i, i2);
            }
        }
        return true;
    }

    public boolean a() {
        if (getChildCount() <= 0 || !f || !e) {
            return false;
        }
        View childAt = getChildAt(0);
        int width = childAt.getWidth();
        int height = childAt.getHeight();
        return getWidth() < width + getPaddingLeft() + getPaddingRight() || getHeight() < height + getPaddingTop() + getPaddingBottom();
    }
}
