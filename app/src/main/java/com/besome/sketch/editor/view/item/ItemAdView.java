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

public class ItemAdView extends LinearLayout implements sy {
    public ViewBean a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;
    public ImageView f;

    public ItemAdView(Context context) {
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
        this.f.setImageResource(2131165302);
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

    public void onDraw(Canvas canvas) {
        if (this.b) {
            canvas.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), this.d);
        }
        super.onDraw(canvas);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    @SuppressLint("ResourceType")
    public void setAdSize(String str) {
        char c2;
        switch (str.hashCode()) {
            case -1966536496:
                if (str.equals("LARGE_BANNER")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case -140586366:
                if (str.equals("SMART_BANNER")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -96588539:
                if (str.equals("MEDIUM_RECTANGLE")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1951953708:
                if (str.equals("BANNER")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        if (c2 == 0) {
            this.f.setImageResource(2131165302);
            this.f.getLayoutParams().width = (int) (this.e * 320.0f);
            this.f.getLayoutParams().height = (int) (this.e * 50.0f);
        } else if (c2 == 1) {
            this.f.setImageResource(2131165302);
            this.f.getLayoutParams().width = (int) (this.e * 320.0f);
            this.f.getLayoutParams().height = (int) (this.e * 50.0f);
        } else if (c2 == 2) {
            this.f.setImageResource(2131165304);
            this.f.getLayoutParams().width = (int) (this.e * 300.0f);
            this.f.getLayoutParams().height = (int) (this.e * 250.0f);
        } else if (c2 == 3) {
            this.f.setImageResource(2131165303);
            this.f.getLayoutParams().width = (int) (this.e * 320.0f);
            this.f.getLayoutParams().height = (int) (this.e * 100.0f);
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        float f2 = this.e;
        super.setPadding((int) (((float) i) * f2), (int) (((float) i2) * f2), (int) (((float) i3) * f2), (int) (((float) i4) * f2));
    }
}
