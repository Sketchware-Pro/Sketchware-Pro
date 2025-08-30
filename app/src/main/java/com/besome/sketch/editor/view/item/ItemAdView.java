package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;

import a.a.a.wB;
import pro.sketchware.R;

public class ItemAdView extends LinearLayout implements ItemView {

    private final Rect rect = new Rect();
    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean isFixed;
    private Paint paint;
    private float dip;
    private ImageView imgView;

    public ItemAdView(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        dip = wB.a(context, 1.0F);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
        imgView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        imgView.setLayoutParams(layoutParams);
        imgView.setImageResource(R.drawable.admob_banner);
        imgView.setScaleType(ScaleType.FIT_XY);
        imgView.setPadding(0, 0, 0, 0);
        addView(imgView);
        setGravity(17);
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
        return hasSelection;
    }

    @Override
    public void setSelection(boolean selection) {
        hasSelection = selection;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (hasSelection) {
            rect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRect(rect, paint);
        }
        super.onDraw(canvas);
    }

    public void setAdSize(String adSize) {
        switch (adSize) {
            case "BANNER", "SMART_BANNER" -> {
                imgView.setImageResource(R.drawable.admob_banner);
                imgView.getLayoutParams().width = (int) (dip * 320.0F);
                imgView.getLayoutParams().height = (int) (dip * 50.0F);
            }
            case "MEDIUM_RECTANGLE" -> {
                imgView.setImageResource(R.drawable.admob_medium_banner);
                imgView.getLayoutParams().width = (int) (dip * 300.0F);
                imgView.getLayoutParams().height = (int) (dip * 250.0F);
            }
            case "LARGE_BANNER" -> {
                imgView.setImageResource(R.drawable.admob_large_banner);
                imgView.getLayoutParams().width = (int) (dip * 320.0F);
                imgView.getLayoutParams().height = (int) (dip * 100.0F);
            }
        }
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(
                (int) (left * dip), (int) (top * dip),
                (int) (right * dip), (int) (bottom * dip));
    }
}
