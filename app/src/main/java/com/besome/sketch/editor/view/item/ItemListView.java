package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import a.a.a.sy;
import a.a.a.wB;

public class ItemListView extends ListView implements sy {

    public ViewBean viewBean;

    public boolean selected;

    public boolean fixed;

    public Paint paint;

    public float oneDp;

    public ArrayList<String> items;

    public ItemListView(Context context) {
        super(context);
        items = new ArrayList<>();
        initialize(context);
    }

    public void initialize(Context context) {
        oneDp = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(wB.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        items.add("List item 1");
        items.add("List item 2");
        items.add("List item 3");
        setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items));
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
            paint.setColor(0x9599d5d0);
            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        } else {
            paint.setColor(0x60000000);
            int measuredWidth2 = getMeasuredWidth();
            int measuredHeight2 = getMeasuredHeight();
            float measuredWidth = measuredWidth2;
            canvas.drawLine(0.0f, 0.0f, measuredWidth, 0.0f, paint);
            float measuredHeight = measuredHeight2;
            canvas.drawLine(0.0f, 0.0f, 0.0f, measuredHeight, paint);
            canvas.drawLine(measuredWidth, 0.0f, measuredWidth, measuredHeight, paint);
            canvas.drawLine(0.0f, measuredHeight, measuredWidth, measuredHeight, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        float oneDp = this.oneDp;
        super.setPadding((int) (left * oneDp), (int) (top * oneDp), (int) (right * oneDp), (int) (bottom * oneDp));
    }
}
