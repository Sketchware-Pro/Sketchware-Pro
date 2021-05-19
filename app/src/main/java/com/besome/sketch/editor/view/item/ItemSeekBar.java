package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemSeekBar extends AppCompatSeekBar implements sy {
    public ViewBean b;
    public boolean c;
    public boolean d;
    public Paint e;
    public float f;

    public ItemSeekBar(Context context) {
        super(context);
        a(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.view.item.ItemSeekBar */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(Context context) {
        this.f = wB.a(context, 1.0f);
        this.e = new Paint(1);
        this.e.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.b;
    }

    public void setBean(ViewBean viewBean) {
        this.b = viewBean;
    }

    public boolean getFixed() {
        return this.d;
    }

    public void setFixed(boolean z) {
        this.d = z;
    }

    public boolean getSelection() {
        return this.c;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.besome.sketch.editor.view.item.ItemSeekBar */
    /* JADX WARN: Multi-variable type inference failed */
    public void setSelection(boolean z) {
        this.c = z;
        invalidate();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.view.item.ItemSeekBar */
    /* JADX WARN: Multi-variable type inference failed */
    public void onDraw(Canvas canvas) {
        if (this.c) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.e);
        }
        ItemSeekBar.super.onDraw(canvas);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.besome.sketch.editor.view.item.ItemSeekBar */
    /* JADX WARN: Multi-variable type inference failed */
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.f;
        ItemSeekBar.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (((float) i4) * f2));
    }
}
