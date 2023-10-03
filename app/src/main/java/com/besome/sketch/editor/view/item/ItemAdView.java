package com.besome.sketch.editor.view.item;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
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

    public ItemAdView(Context var1) {
        super(var1);
        this.a(var1);
    }

    public void a(Context var1) {
        this.e = wB.a(var1, 1.0F);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        this.setDrawingCacheEnabled(true);
        this.f = new ImageView(this.getContext());
        LinearLayout.LayoutParams var2 = new LinearLayout.LayoutParams(-1, -2);
        this.f.setLayoutParams(var2);
        this.f.setImageResource(2131165302);
        this.f.setScaleType(ScaleType.FIT_XY);
        this.f.setPadding(0, 0, 0, 0);
        this.addView(this.f);
        this.setGravity(17);
    }

    public ViewBean getBean() {
        return this.a;
    }

    public boolean getFixed() {
        return this.c;
    }

    public boolean getSelection() {
        return this.b;
    }

    public void onDraw(Canvas var1) {
        if (this.b) {
            var1.drawRect(new Rect(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight()), this.d);
        }

        super.onDraw(var1);
    }

    public void setAdSize(String var1) {
        byte var2;
        label38:
        {
            switch (var1) {
                case "LARGE_BANNER":
                    var2 = 3;
                    break label38;
                case "SMART_BANNER":
                    var2 = 1;
                    break label38;
                case "MEDIUM_RECTANGLE":
                    var2 = 2;
                    break label38;
                case "BANNER":
                    var2 = 0;
                    break label38;
            }

            var2 = -1;
        }

        if (var2 != 0) {
            if (var2 != 1) {
                if (var2 != 2) {
                    if (var2 == 3) {
                        this.f.setImageResource(2131165303);
                        this.f.getLayoutParams().width = (int) (this.e * 320.0F);
                        this.f.getLayoutParams().height = (int) (this.e * 100.0F);
                    }
                } else {
                    this.f.setImageResource(2131165304);
                    this.f.getLayoutParams().width = (int) (this.e * 300.0F);
                    this.f.getLayoutParams().height = (int) (this.e * 250.0F);
                }
            } else {
                this.f.setImageResource(2131165302);
                this.f.getLayoutParams().width = (int) (this.e * 320.0F);
                this.f.getLayoutParams().height = (int) (this.e * 50.0F);
            }
        } else {
            this.f.setImageResource(2131165302);
            this.f.getLayoutParams().width = (int) (this.e * 320.0F);
            this.f.getLayoutParams().height = (int) (this.e * 50.0F);
        }

    }

    public void setBean(ViewBean var1) {
        this.a = var1;
    }

    public void setFixed(boolean var1) {
        this.c = var1;
    }

    public void setPadding(int var1, int var2, int var3, int var4) {
        float var5 = (float) var1;
        float var6 = this.e;
        super.setPadding((int) (var5 * var6), (int) ((float) var2 * var6), (int) ((float) var3 * var6), (int) ((float) var4 * var6));
    }

    public void setSelection(boolean var1) {
        this.b = var1;
        this.invalidate();
    }
}
