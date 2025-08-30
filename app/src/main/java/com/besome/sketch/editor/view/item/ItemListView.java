package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ItemView;

import java.util.ArrayList;

import a.a.a.wB;

public class ItemListView extends ListView implements ItemView {

    public ViewBean viewBean;

    public boolean selected;

    public boolean fixed;

    public Paint paint;

    public float dip;

    public ArrayList<String> exampleData;

    public ItemListView(Context context) {
        super(context);
        exampleData = new ArrayList<>();
        initialize(context);
    }

    public void initialize(Context context) {
        dip = wB.a(context, 1.0f);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(wB.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        exampleData.add("List item 1");
        exampleData.add("List item 2");
        exampleData.add("List item 3");
        setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, exampleData));
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
    public void onDraw(@NonNull Canvas canvas) {
        if (selected) {
            paint.setColor(0x9599d5d0);
            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        } else {
            paint.setColor(0x60000000);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            canvas.drawLine(0.0f, 0.0f, (float) measuredWidth, 0.0f, paint);
            canvas.drawLine(0.0f, 0.0f, 0.0f, (float) measuredHeight, paint);
            canvas.drawLine((float) measuredWidth, 0.0f, (float) measuredWidth, (float) measuredHeight, paint);
            canvas.drawLine(0.0f, (float) measuredHeight, (float) measuredWidth, (float) measuredHeight, paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding((int) (left * dip), (int) (top * dip), (int) (right * dip), (int) (bottom * dip));
    }
}
