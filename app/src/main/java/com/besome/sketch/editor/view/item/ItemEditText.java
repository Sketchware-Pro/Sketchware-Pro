package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.appcompat.widget.AppCompatEditText;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemEditText extends AppCompatEditText implements sy {
    public ViewBean c;
    public boolean d;
    public boolean e;
    public Paint f;
    public float g;
    public Drawable h;

    public ItemEditText(Context context) {
        super(context);
        a(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.view.item.ItemEditText */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(Context context) {
        this.g = wB.a(context, 1.0f);
        this.f = new Paint(1);
        this.f.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        setFocusable(false);
        this.h = getBackground();
    }

    public ViewBean getBean() {
        return this.c;
    }

    public void setBean(ViewBean viewBean) {
        this.c = viewBean;
    }

    public boolean getFixed() {
        return this.e;
    }

    public void setFixed(boolean z) {
        this.e = z;
    }

    public boolean getSelection() {
        return this.d;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.besome.sketch.editor.view.item.ItemEditText */
    /* JADX WARN: Multi-variable type inference failed */
    public void setSelection(boolean z) {
        this.d = z;
        invalidate();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.view.item.ItemEditText */
    /* JADX WARN: Multi-variable type inference failed */
    public void onDraw(Canvas canvas) {
        if (this.d) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.f);
        }
        ItemEditText.super.onDraw(canvas);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.besome.sketch.editor.view.item.ItemEditText */
    /* JADX WARN: Multi-variable type inference failed */
    public void setBackgroundColor(int i) {
        if (i == 16777215) {
            setBackground(this.h);
        } else {
            ItemEditText.super.setBackgroundColor(i);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.besome.sketch.editor.view.item.ItemEditText */
    /* JADX WARN: Multi-variable type inference failed */
    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.g;
        ItemEditText.super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (((float) i4) * f2));
    }
}
