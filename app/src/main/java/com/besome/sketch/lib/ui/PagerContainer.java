package com.besome.sketch.lib.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private boolean isViewPagerScrolled = false;
    private Point containerCenter, touchDownPoint = new Point();

    public PagerContainer(Context context) {
        super(context);
        initialize();
    }

    public PagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
    }

    public PagerContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize();
    }

    private void initialize() {
        setClipChildren(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        isViewPagerScrolled = state != ViewPager.SCROLL_STATE_IDLE;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (isViewPagerScrolled) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        try {
            viewPager = (ViewPager) getChildAt(0);
            viewPager.addOnPageChangeListener(this);
        } catch (Exception var2) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        containerCenter = new Point(width / 2, height / 2);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            touchDownPoint = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
        }

        motionEvent.offsetLocation((float) (containerCenter.x - touchDownPoint.x), (float) (containerCenter.y - touchDownPoint.y));
        return viewPager.dispatchTouchEvent(motionEvent);
    }
}
