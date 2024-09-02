package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class LogicEditorScrollView extends FrameLayout {
    public float a;
    public float b;

    public int scaledTouchSlop;
    public boolean d;

    public boolean scrollEnabled;

    public boolean useScroll;
    public float g;
    public float h;

    public LogicEditorScrollView(Context context) {
        super(context);
        this.a = 0.0f;
        this.b = 0.0f;
        this.scaledTouchSlop = 0;
        this.d = false;
        this.scrollEnabled = true;
        this.useScroll = true;
        this.g = -1.0f;
        this.h = -1.0f;
        initialize(context);
    }

    public final void initialize(Context context) {
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public void addView(View child) {
        if (getChildCount() <= 1) {
            super.addView(child);
            return;
        }
        throw new IllegalStateException("BothDirectionScrollView should have child View only one");
    }

    public boolean getScrollEnabled() {
        return this.scrollEnabled;
    }

    public boolean getUseScroll() {
        return this.useScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!a()) {
            return false;
        }
        int action = ev.getAction();
        if (action == 2 && this.d) {
            return true;
        }
        float x = ev.getX();
        float y = ev.getY();
        if (action == 0) {
            this.a = x;
            this.b = y;
            this.d = false;
        } else if (action == 1) {
            this.d = false;
        } else if (action == 2) {
            int abs = (int) Math.abs(this.a - x);
            int abs2 = (int) Math.abs(this.b - y);
            int i = this.scaledTouchSlop;
            if (abs > i || abs2 > i) {
                this.d = true;
            }
        }
        return this.d;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int min;
        int i = 0;
        if (!a()) {
            return false;
        }
        View child = getChildAt(0);
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        if (action == 0) {
            this.g = x;
            this.h = y;
        } else if (action == 1) {
            this.g = -1.0f;
            this.h = -1.0f;
        } else if (action == 2) {
            if (this.g < 0.0f) {
                this.g = x;
            }
            if (this.h < 0.0f) {
                this.h = y;
            }
            int i2 = (int) (this.g - x);
            int i3 = (int) (this.h - y);
            this.g = x;
            this.h = y;
            if (i2 <= 0) {
                if (getScrollX() <= 0) {
                    i2 = 0;
                }
                min = Math.max(0 - getScrollX(), i2);
            } else {
                int right = ((child.getRight() - getScrollX()) - getWidth()) - getPaddingRight();
                min = right > 0 ? Math.min(right, i2) : 0;
            }
            if (i3 <= 0) {
                if (getScrollY() <= 0) {
                    i3 = 0;
                }
                i = Math.max(0 - getScrollY(), i3);
            } else {
                int bottom = ((child.getBottom() - getScrollY()) - getHeight()) - getPaddingBottom();
                if (bottom > 0) {
                    i = Math.min(bottom, i3);
                }
            }
            if (min != 0 || i != 0) {
                scrollBy(min, i);
            }
        }
        return true;
    }

    public void setScrollEnabled(boolean z) {
        this.scrollEnabled = z;
    }

    public void setUseScroll(boolean z) {
        this.useScroll = z;
    }

    public boolean a() {
        if (getChildCount() <= 0 || !this.useScroll || !this.scrollEnabled) {
            return false;
        }
        View childAt = getChildAt(0);
        return getWidth() < (childAt.getWidth() + getPaddingLeft()) + getPaddingRight() || getHeight() < (childAt.getHeight() + getPaddingTop()) + getPaddingBottom();
    }

    public LogicEditorScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = 0.0f;
        this.b = 0.0f;
        this.scaledTouchSlop = 0;
        this.d = false;
        this.scrollEnabled = true;
        this.useScroll = true;
        this.g = -1.0f;
        this.h = -1.0f;
        initialize(context);
    }
}
