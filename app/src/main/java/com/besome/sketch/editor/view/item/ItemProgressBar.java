package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;

import a.a.a.wB;
import pro.sketchware.R;

public class ItemProgressBar extends LinearLayout implements ItemView {

    private ViewBean viewBean;
    private boolean isSelected;
    private boolean isFixed;
    private Paint paint;
    private float dip;
    private ImageView imageView;

    public ItemProgressBar(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        dip = wB.a(context, 1.0F);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
        imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.progressbar_circle_48dp);
        imageView.setScaleType(ScaleType.FIT_XY);
        imageView.setPadding(0, 0, 0, 0);
        addView(imageView);
        setGravity(Gravity.CENTER);
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
    public void setSelection(boolean selection) {
        isSelected = selection;
        invalidate();
    }

    @Override
    public void onDraw(Canvas var1) {
        if (isSelected) {
            var1.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        }
        super.onDraw(var1);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) (left * dip),
                (int) (top * dip),
                (int) (right * dip),
                (int) (bottom * dip));
    }

    public void setProgressBarStyle(String progressBarStyle) {
        if ("?android:progressBarStyle".equals(progressBarStyle)) {
            imageView.setImageResource(R.drawable.progressbar_circle_48dp);
            imageView.getLayoutParams().width = (int) (dip * 30F);
            imageView.getLayoutParams().height = (int) (dip * 30F);
        } else if ("?android:progressBarStyleHorizontal".equals(progressBarStyle)) {
            imageView.setImageResource(R.drawable.progressbar_horizontal_48dp);
            imageView.getLayoutParams().width = (int) (dip * 320F);
            imageView.getLayoutParams().height = (int) (dip * 30F);
        }
    }
}
