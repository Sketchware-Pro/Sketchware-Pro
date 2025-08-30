package com.besome.sketch.editor.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class LogicEditorScrollView extends FrameLayout {
    private float offsetX = 0;
    private float offsetY = 0;
    private int scaledTouchSlop = 0;
    private boolean isDragged = false;
    private boolean scrollEnabled = true;
    private float lastPosX = -1;
    private float lastPosY = -1;

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
        if (getChildCount() > 1) {
            throw new IllegalStateException("BothDirectionScrollView should have child View only one");
        }
        super.addView(child);
    }

    public boolean getScrollEnabled() {
        return scrollEnabled;
    }

    public void setScrollEnabled(boolean z) {
        scrollEnabled = z;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isPossibleScroll()) {
            return false;
        }
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE && isDragged) {
            return true;
        }
        float x = ev.getX();
        float y = ev.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            offsetX = x;
            offsetY = y;
            isDragged = false;
        } else if (action == MotionEvent.ACTION_UP) {
            isDragged = false;
        } else if (action == MotionEvent.ACTION_MOVE) {
            int abs = (int) Math.abs(offsetX - x);
            int abs2 = (int) Math.abs(offsetY - y);
            if (abs > scaledTouchSlop || abs2 > scaledTouchSlop) {
                isDragged = true;
            }
        }
        return isDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int min;
        int i = 0;
        if (!isPossibleScroll()) {
            return false;
        }
        View child = getChildAt(0);
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            lastPosX = x;
            lastPosY = y;
        } else if (action == MotionEvent.ACTION_UP) {
            lastPosX = -1.0f;
            lastPosY = -1.0f;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (lastPosX < 0.0f) {
                lastPosX = x;
            }
            if (lastPosY < 0.0f) {
                lastPosY = y;
            }
            int i2 = (int) (lastPosX - x);
            int i3 = (int) (lastPosY - y);
            lastPosX = x;
            lastPosY = y;
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

    private boolean isPossibleScroll() {
        if (getChildCount() <= 0 || !scrollEnabled) {
            return false;
        }
        View firstView = getChildAt(0);
        int width = firstView.getWidth();
        int height = firstView.getHeight();
        if (getWidth() < (getPaddingLeft() + width) + getPaddingRight()) return true;
        return getHeight() < (getPaddingTop() + height) + getPaddingBottom();
    }
}
