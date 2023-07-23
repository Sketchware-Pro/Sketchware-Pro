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

    public ViewPager a;
    public boolean b = false;
    public Point c = new Point();
    public Point d = new Point();

    public PagerContainer(Context context) {
        super(context);
        a();
    }

    public PagerContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public PagerContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    public final void a() {
        setClipChildren(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        b = state != 0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (b) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    public ViewPager getViewPager() {
        return a;
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();

        try {
            a = (ViewPager) getChildAt(0);
            a.setOnPageChangeListener(this);
        } catch (Exception var2) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        c = new Point(width / 2, height / 2);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            d.x = (int) motionEvent.getX();
            d.y = (int) motionEvent.getY();
        }

        motionEvent.offsetLocation((float) (c.x - d.x), (float) (c.y - d.y));
        return a.dispatchTouchEvent(motionEvent);
    }
}
