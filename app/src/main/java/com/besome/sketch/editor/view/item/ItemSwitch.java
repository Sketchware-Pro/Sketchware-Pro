package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.appcompat.widget.SwitchCompat;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemSwitch extends SwitchCompat implements sy {
    public ViewBean O;
    public boolean P;
    public boolean Q;
    public Paint R;
    public float S;

    public ItemSwitch(Context context) {
        super(context);
        a(context);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.view.item.ItemSwitch */
    /* JADX WARN: Multi-variable type inference failed */
    public void a(Context context) {
        this.S = wB.a(context, 1.0f);
        this.R = new Paint(1);
        this.R.setColor(-1785080368);
        setDrawingCacheEnabled(true);
    }

    public ViewBean getBean() {
        return this.O;
    }

    public void setBean(ViewBean viewBean) {
        this.O = viewBean;
    }

    public boolean getFixed() {
        return this.Q;
    }

    public void setFixed(boolean z) {
        this.Q = z;
    }

    public boolean getSelection() {
        return this.P;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: com.besome.sketch.editor.view.item.ItemSwitch */
    /* JADX WARN: Multi-variable type inference failed */
    public void setSelection(boolean z) {
        this.P = z;
        invalidate();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.view.item.ItemSwitch */
    /* JADX WARN: Multi-variable type inference failed */
    public void onDraw(Canvas canvas) {
        if (this.P) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.R);
        }
        ItemSwitch.super.onDraw(canvas);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: com.besome.sketch.editor.view.item.ItemSwitch */
    /* JADX WARN: Multi-variable type inference failed */
    public void setPadding(int i, int i2, int i3, int i4) {
        float f = this.S;
        ItemSwitch.super.setPadding((int) (((float) i) * f), (int) (((float) i2) * f), (int) (((float) i3) * f), (int) (((float) i4) * f));
    }
}
