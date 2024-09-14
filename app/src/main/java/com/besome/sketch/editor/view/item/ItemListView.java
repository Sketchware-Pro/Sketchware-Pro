package com.besome.sketch.editor.view.item;

import a.a.a.sy;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.besome.sketch.beans.ViewBean;
import java.util.ArrayList;

public class ItemListView extends ListView implements sy {

    public ViewBean viewBean;

    public boolean selected;

    public boolean fixed;

    public Paint paint;

    public float oneDp;

    public ArrayList<String> items;

    public ItemListView(Context context) {
        super(context);
        this.items = new ArrayList<>();
        initialize(context);
    }

    public void initialize(Context context) {
        this.oneDp = wB.a(context, 1.0f);
        this.paint = new Paint(1);
        this.paint.setStrokeWidth(wB.a(getContext(), 2.0f));
        setDrawingCacheEnabled(true);
        this.items.add("List item 1");
        this.items.add("List item 2");
        this.items.add("List item 3");
        setAdapter((ListAdapter) new ArrayAdapter(context, 17367043, this.items));
    }

    @Override
    public ViewBean getBean() {
        return this.viewBean;
    }

    @Override
    public boolean getFixed() {
        return this.fixed;
    }

    public boolean getSelection() {
        return this.selected;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (this.selected) {
            this.paint.setColor(-1785080368);
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.paint);
        } else {
            this.paint.setColor(1610612736);
            int measuredWidth2 = getMeasuredWidth();
            int measuredHeight2 = getMeasuredHeight();
            float measuredWidth = measuredWidth2;
            canvas.drawLine(0.0f, 0.0f, measuredWidth, 0.0f, this.paint);
            float measuredHeight = measuredHeight2;
            canvas.drawLine(0.0f, 0.0f, 0.0f, measuredHeight, this.paint);
            canvas.drawLine(measuredWidth, 0.0f, measuredWidth, measuredHeight, this.paint);
            canvas.drawLine(0.0f, measuredHeight, measuredWidth, measuredHeight, this.paint);
        }
        super.onDraw(canvas);
    }

    @Override
    public void setBean(ViewBean viewBean) {
        this.viewBean = viewBean;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        float oneDp = this.oneDp;
        super.setPadding((int) (left * oneDp), (int) (top * oneDp), (int) (right * oneDp), (int) (bottom * oneDp));
    }

    @Override
    public void setSelection(boolean selected) {
        this.selected = selected;
        invalidate();
    }
}
