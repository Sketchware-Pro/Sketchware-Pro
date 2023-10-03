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
        a(var1);
    }

    public void a(Context var1) {
        e = wB.a(var1, 1.0F);
        d = new Paint(1);
        d.setColor(-1785080368);
        setDrawingCacheEnabled(true);
        f = new ImageView(getContext());
        LinearLayout.LayoutParams var2 = new LinearLayout.LayoutParams(-1, -2);
        f.setLayoutParams(var2);
        f.setImageResource(2131165302);
        f.setScaleType(ScaleType.FIT_XY);
        f.setPadding(0, 0, 0, 0);
        addView(f);
        setGravity(17);
    }

    @Override
    public ViewBean getBean() {
        return a;
    }

    @Override
    public boolean getFixed() {
        return c;
    }

    public boolean getSelection() {
        return b;
    }

    @Override
    public void onDraw(Canvas var1) {
        if (b) {
            var1.drawRect(new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight()), d);
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
                        f.setImageResource(2131165303);
                        f.getLayoutParams().width = (int) (e * 320.0F);
                        f.getLayoutParams().height = (int) (e * 100.0F);
                    }
                } else {
                    f.setImageResource(2131165304);
                    f.getLayoutParams().width = (int) (e * 300.0F);
                    f.getLayoutParams().height = (int) (e * 250.0F);
                }
            } else {
                f.setImageResource(2131165302);
                f.getLayoutParams().width = (int) (e * 320.0F);
                f.getLayoutParams().height = (int) (e * 50.0F);
            }
        } else {
            f.setImageResource(2131165302);
            f.getLayoutParams().width = (int) (e * 320.0F);
            f.getLayoutParams().height = (int) (e * 50.0F);
        }

    }

    @Override
    public void setBean(ViewBean var1) {
        a = var1;
    }

    public void setFixed(boolean var1) {
        c = var1;
    }

    @Override
    public void setPadding(int var1, int var2, int var3, int var4) {
        float var6 = e;
        super.setPadding((int) (var5 * var6), (int) (var2 * var6), (int) (var3 * var6), (int) (var4 * var6));
    }

    @Override
    public void setSelection(boolean var1) {
        b = var1;
        invalidate();
    }
}
