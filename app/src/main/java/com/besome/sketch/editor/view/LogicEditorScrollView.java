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

    public void a(Context context) {
        this.c = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void addView(View view) {
        if (getChildCount() <= 1) {
            super.addView(view);
            return;
        }
        throw new IllegalStateException("BothDirectionScrollView should have child View only one");
    }

    public boolean getScrollEnabled() {
        return this.e;
    }

    public boolean getUseScroll() {
        return this.f;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!a()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 2 && this.d) {
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (action == 0) {
            this.a = x;
            this.b = y;
            this.d = false;
        } else if (action == 1) {
            this.d = false;
        } else if (action == 2) {
            int abs = (int) Math.abs(this.a - x);
            int abs2 = (int) Math.abs(this.b - y);
            int i = this.c;
            if (abs > i || abs2 > i) {
                this.d = true;
            }
        }
        return this.d;
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
            this.g = x;
            this.h = y;
        } else if (action == 1) {
            this.g = -1.0f;
            this.h = -1.0f;
        } else if (action == 2) {
            if (this.g < FlexItem.FLEX_GROW_DEFAULT) {
                this.g = x;
            }
            if (this.h < FlexItem.FLEX_GROW_DEFAULT) {
                this.h = y;
            }
            int i3 = (int) (this.g - x);
            int i4 = (int) (this.h - y);
            this.g = x;
            this.h = y;
            if (i3 <= 0) {
                if (getScrollX() <= 0) {
                    i3 = 0;
                }
                i = Math.max(0 - getScrollX(), i3);
            } else {
                int right = ((childAt.getRight() - getScrollX()) - getWidth()) - getPaddingRight();
                i = right > 0 ? Math.min(right, i3) : 0;
            }
            if (i4 <= 0) {
                if (getScrollY() <= 0) {
                    i4 = 0;
                }
                i2 = Math.max(0 - getScrollY(), i4);
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

    public void setScrollEnabled(boolean z) {
        this.e = z;
    }

    public void setUseScroll(boolean z) {
        this.f = z;
    }

    public boolean a() {
        if (getChildCount() <= 0 || !this.f || !this.e) {
            return false;
        }
        View childAt = getChildAt(0);
        int width = childAt.getWidth();
        int height = childAt.getHeight();
        if (getWidth() < width + getPaddingLeft() + getPaddingRight() || getHeight() < height + getPaddingTop() + getPaddingBottom()) {
            return true;
        }
        return false;
    }

    public LogicEditorScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }
}
