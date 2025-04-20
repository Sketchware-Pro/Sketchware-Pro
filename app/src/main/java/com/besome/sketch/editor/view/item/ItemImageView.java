package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.widget.AppCompatImageView;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemImageView extends AppCompatImageView implements sy {

    public ViewBean viewBean;

    public boolean selected;

    public boolean fixed;

    public Paint paint;

    public float oneDp;

    public ItemImageView(Context context) {
        super(context);
        initialize(context);
    }

    public void initialize(Context context) {
        this.oneDp = wB.a(context, 1.0f);
        this.paint = new Paint(1);
        this.paint.setColor(0x9599d5d0);
        setDrawingCacheEnabled(true);
    }

    @Override
    public ViewBean getBean() {
        return this.viewBean;
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean getSelection() {
        return this.selected;
    }

    @Override
    public void setSelection(boolean selected) {
        this.selected = selected;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (this.selected) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        float oneDp = this.oneDp;
        super.setPadding((int) (left * oneDp), (int) (top * oneDp), (int) (right * oneDp), (int) (bottom * oneDp));
    }
}
