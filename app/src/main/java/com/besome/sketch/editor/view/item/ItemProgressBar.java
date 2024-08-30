//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.editor.view.item;

import a.a.a.sy;
import a.a.a.wB;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import com.besome.sketch.beans.ViewBean;

public class ItemProgressBar extends LinearLayout implements sy {
    public ViewBean a;
    public boolean b;
    public boolean c;
    public Paint d;
    public float e;
    public ImageView f;

    public ItemProgressBar(Context var1) {
        super(var1);
        this.a(var1);
    }

    public void a(Context var1) {
        this.e = wB.a(var1, 1.0F);
        this.d = new Paint(1);
        this.d.setColor(-1785080368);
        ((LinearLayout)this).setDrawingCacheEnabled(true);
        this.f = new ImageView(((LinearLayout)this).getContext());
        LinearLayout.LayoutParams var2 = new LinearLayout.LayoutParams(-1, -2);
        this.f.setLayoutParams(var2);
        this.f.setImageResource(2131166032);
        this.f.setScaleType(ScaleType.FIT_XY);
        this.f.setPadding(0, 0, 0, 0);
        ((LinearLayout)this).addView(this.f);
        ((LinearLayout)this).setGravity(17);
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
        synchronized(this){}

        try {
            if (this.b) {
                Rect var2 = new Rect(0, 0, ((LinearLayout)this).getMeasuredWidth(), ((LinearLayout)this).getMeasuredHeight());
                var1.drawRect(var2, this.d);
            }

            super.onDraw(var1);
        } finally {
            ;
        }

    }

    public void setBean(ViewBean var1) {
        this.a = var1;
    }

    public void setFixed(boolean var1) {
        this.c = var1;
    }

    public void setPadding(int var1, int var2, int var3, int var4) {
        float var5 = (float)var1;
        float var6 = this.e;
        super.setPadding((int)(var5 * var6), (int)((float)var2 * var6), (int)((float)var3 * var6), (int)((float)var4 * var6));
    }

    public void setProgressBarStyle(String var1) {
        byte var3;
        label26: {
            int var2 = var1.hashCode();
            if (var2 != -1631599723) {
                if (var2 == 583759257 && var1.equals("?android:progressBarStyleHorizontal")) {
                    var3 = 1;
                    break label26;
                }
            } else if (var1.equals("?android:progressBarStyle")) {
                var3 = 0;
                break label26;
            }

            var3 = -1;
        }

        if (var3 != 0) {
            if (var3 == 1) {
                this.f.setImageResource(2131166033);
                this.f.getLayoutParams().width = (int)(this.e * 320.0F);
                this.f.getLayoutParams().height = (int)(this.e * 30.0F);
            }
        } else {
            this.f.setImageResource(2131166032);
            this.f.getLayoutParams().width = (int)(this.e * 30.0F);
            this.f.getLayoutParams().height = (int)(this.e * 30.0F);
        }

    }

    public void setSelection(boolean var1) {
        this.b = var1;
        ((LinearLayout)this).invalidate();
    }
}
