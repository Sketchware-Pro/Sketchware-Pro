package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemAdView extends LinearLayout implements sy {

    private ViewBean viewBean;
    private boolean hasSelection;
    private boolean isFixed;
    private Paint paint;
    private float paddingFactor;
    private ImageView imgView;
    private final Rect rect = new Rect();

    public ItemAdView(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        paddingFactor = wB.a(context, 1.0F);
        paint = new Paint(1);
        paint.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        imgView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        imgView.setLayoutParams(layoutParams);
        imgView.setImageResource(2131165302);
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
    public boolean getFixed() {
        return isFixed;
    }

    public boolean getSelection() {
        return hasSelection;
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
                imgView.setImageResource(2131165302);
                imgView.getLayoutParams().width = (int) (paddingFactor * 320.0F);
                imgView.getLayoutParams().height = (int) (paddingFactor * 50.0F);
            }
            case "MEDIUM_RECTANGLE" -> {
                imgView.setImageResource(2131165304);
                imgView.getLayoutParams().width = (int) (paddingFactor * 300.0F);
                imgView.getLayoutParams().height = (int) (paddingFactor * 250.0F);
            }
            case "LARGE_BANNER" -> {
                imgView.setImageResource(2131165303);
                imgView.getLayoutParams().width = (int) (paddingFactor * 320.0F);
                imgView.getLayoutParams().height = (int) (paddingFactor * 100.0F);
            }
        }
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    public void setFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(
                (int) (left * paddingFactor), (int) (top * paddingFactor),
                (int) (right * paddingFactor), (int) (bottom * paddingFactor));
    }

    @Override
    public void setSelection(boolean selection) {
        hasSelection = selection;
        invalidate();
    }
}
