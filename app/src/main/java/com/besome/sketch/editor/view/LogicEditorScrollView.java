package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class LogicEditorScrollView extends FrameLayout {
    private float a = 0;
    private float b = 0;
    private int scaledTouchSlop = 0;
    private boolean d = false;
    private boolean scrollEnabled = true;
    private boolean useScroll = true;
    private float g = -1;
    private float h = -1;

    public LogicEditorScrollView(Context context) {
        this(context, null);
    }

    public LogicEditorScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogicEditorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LogicEditorScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
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
        return scrollEnabled;
    }

    public void setScrollEnabled(boolean z) {
        scrollEnabled = z;
    }

    public boolean getUseScroll() {
        return useScroll;
    }

    public void setUseScroll(boolean z) {
        useScroll = z;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!a()) {
            return false;
        }
        int action = ev.getAction();
        if (action == 2 && d) {
            return true;
        }
        float x = ev.getX();
        float y = ev.getY();
        if (action == 0) {
            a = x;
            b = y;
            d = false;
        } else if (action == 1) {
            d = false;
        } else if (action == 2) {
            int abs = (int) Math.abs(a - x);
            int abs2 = (int) Math.abs(b - y);
            int i = scaledTouchSlop;
            if (abs > i || abs2 > i) {
                d = true;
            }
        }
        return d;
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
            g = x;
            h = y;
        } else if (action == 1) {
            g = -1.0f;
            h = -1.0f;
        } else if (action == 2) {
            if (g < 0.0f) {
                g = x;
            }
            if (h < 0.0f) {
                h = y;
            }
            int i2 = (int) (g - x);
            int i3 = (int) (h - y);
            g = x;
            h = y;
            if (i2 <= 0) {
                if (getScrollX() <= 0) {
                    i2 = 0;
                }
                min = Math.max(-getScrollX(), i2);
            } else {
                int right = ((child.getRight() - getScrollX()) - getWidth()) - getPaddingRight();
                min = right > 0 ? Math.min(right, i2) : 0;
            }
            if (i3 <= 0) {
                if (getScrollY() <= 0) {
                    i3 = 0;
                }
                i = Math.max(-getScrollY(), i3);
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

    private boolean a() {
        if (getChildCount() <= 0 || !useScroll || !scrollEnabled) {
            return false;
        }
        View childAt = getChildAt(0);
        return getWidth() < (childAt.getWidth() + getPaddingLeft()) + getPaddingRight() || getHeight() < (childAt.getHeight() + getPaddingTop()) + getPaddingBottom();
    }
}
