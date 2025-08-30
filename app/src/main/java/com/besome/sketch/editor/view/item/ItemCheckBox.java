package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;

import a.a.a.wB;

public class ItemCheckBox extends AppCompatCheckBox implements ItemView {

    public ViewBean viewBean;

    public boolean selected;

    public boolean fixed;

    public Paint paint;

    public float dip;

    public ItemCheckBox(Context context) {
        super(context);
        initialize(context);
    }

    public void initialize(Context context) {
        dip = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
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
        return fixed;
    }

    @Override
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean getSelection() {
        return selected;
    }

    @Override
    public void setSelection(boolean selected) {
        this.selected = selected;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (selected) {
            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) (left * dip), (int) (top * dip), (int) (right * dip), (int) (bottom * dip));
    }
}
