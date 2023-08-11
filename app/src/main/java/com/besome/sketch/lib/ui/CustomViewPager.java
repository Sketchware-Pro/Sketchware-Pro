package com.besome.sketch.lib.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    private boolean isTouchEnabled = true;

    public CustomViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void disableTouchEvent() {
        isTouchEnabled = false;
    }

    public void enableTouchEvent() {
        isTouchEnabled = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isTouchEnabled) {
            try {
                return super.onInterceptTouchEvent(motionEvent);
            } catch (Exception var3) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isTouchEnabled) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }
}
