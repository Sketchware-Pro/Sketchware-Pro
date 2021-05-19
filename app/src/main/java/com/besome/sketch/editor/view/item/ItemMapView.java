package com.besome.sketch.editor.view.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.widget.AppCompatTextView;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemMapView extends AppCompatTextView implements sy {
    public ViewBean d;
    public boolean e;
    public boolean f;
    public Paint g;
    public float h;

    public ItemMapView(Context context) {
        super(context);
        a(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.view.item.ItemMapView */
    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("WrongConstant")
    public void a(Context context) {
        setGravity(17);
        setTypeface(null, 1);
        setText("MapView");
        this.h = wB.a(context, 1.0f);
        this.g = new Paint(1);
        this.g.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.d;
    }

    public void setBean(ViewBean viewBean) {
        this.d = viewBean;
    }

    public boolean getFixed() {
        return this.f;
    }

    public void setFixed(boolean z) {
        this.f = z;
    }

    public boolean getSelection() {
        return this.e;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.besome.sketch.editor.view.item.ItemMapView */
    /* JADX WARN: Multi-variable type inference failed */
    public void setSelection(boolean z) {
        this.e = z;
        invalidate();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.view.item.ItemMapView */
    /* JADX WARN: Multi-variable type inference failed */
    public void onDraw(Canvas canvas) {
        if (this.e) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.g);
        }
        ItemMapView.super.onDraw(canvas);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.besome.sketch.editor.view.item.ItemMapView */
    /* JADX WARN: Multi-variable type inference failed */
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.h;
        ItemMapView.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (((float) i4) * f2));
    }
}
