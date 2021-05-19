package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.CalendarView;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemCalendarView extends CalendarView implements sy {
    public ViewBean a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;

    public ItemCalendarView(Context context) {
        super(context);
        a(context);
    }

    public void a(Context context) {
        this.e = wB.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        setFocusable(false);
        setClickable(false);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.a;
    }

    public void setBean(ViewBean viewBean) {
        this.a = viewBean;
    }

    public boolean getFixed() {
        return this.c;
    }

    public void setFixed(boolean z) {
        this.c = z;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void setSelection(boolean z) {
        this.b = z;
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        if (this.b) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
        }
        super.onDraw(canvas);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f = this.e;
        super.setPadding((int) (((float) i) * f), (int) (((float) i2) * f), (int) (((float) i3) * f), (int) (((float) i4) * f));
    }
}
