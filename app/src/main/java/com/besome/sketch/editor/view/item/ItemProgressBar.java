package com.besome.sketch.editor.view.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;

import a.a.a.sy;
import a.a.a.wB;

public class ItemProgressBar extends LinearLayout implements sy {
    public ViewBean a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;
    public ImageView f;

    public ItemProgressBar(Context context) {
        super(context);
        a(context);
    }

    @SuppressLint("ResourceType")
    public void a(Context context) {
        this.e = wB.a(context, 1.0f);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        this.f = new ImageView(getContext());
        this.f.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.f.setImageResource(2131166032);
        this.f.setScaleType(ImageView.ScaleType.FIT_XY);
        this.f.setPadding(0, 0, 0, 0);
        addView(this.f);
        setGravity(17);
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

    public synchronized void onDraw(Canvas canvas) {
        if (this.b) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
        }
        super.onDraw(canvas);
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.e;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (((float) i4) * f2));
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0051  */
    @SuppressLint("ResourceType")
    public void setProgressBarStyle(String str) {
        char c2;
        int hashCode = str.hashCode();
        if (hashCode != -1631599723) {
            if (hashCode == 583759257 && str.equals(ViewBean.PROGRESSBAR_STYLE_HORIZONTAL)) {
                c2 = 1;
                if (c2 == 0) {
                    this.f.setImageResource(2131166032);
                    this.f.getLayoutParams().width = (int) (this.e * 30.0f);
                    this.f.getLayoutParams().height = (int) (this.e * 30.0f);
                    return;
                } else if (c2 == 1) {
                    this.f.setImageResource(2131166033);
                    this.f.getLayoutParams().width = (int) (this.e * 320.0f);
                    this.f.getLayoutParams().height = (int) (this.e * 30.0f);
                    return;
                } else {
                    return;
                }
            }
        } else if (str.equals(ViewBean.PROGRESSBAR_STYLE_CIRCLE)) {
            c2 = 0;
            if (c2 == 0) {
            }
        }
        c2 = 65535;
        if (c2 == 0) {
        }
    }
}
