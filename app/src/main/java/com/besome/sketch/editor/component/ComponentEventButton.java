package com.besome.sketch.editor.component;

import a.a.a.wB;
import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ComponentEventButton extends LinearLayout {
    public LinearLayout a;
    public LinearLayout b;
    public ImageView c;
    public ImageView d;
    public TextView e;

    public ComponentEventButton(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, 2131427434);
        this.a = (LinearLayout) findViewById(2131230931);
        this.d = (ImageView) findViewById(2131230756);
        this.b = (LinearLayout) findViewById(2131231092);
        this.c = (ImageView) findViewById(2131231090);
        this.e = (TextView) findViewById(2131231561);
    }

    public void b() {
        this.d.setVisibility(8);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(1.0f);
        this.c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        this.a.setOnClickListener(onClickListener);
    }

    public void a() {
        this.d.setVisibility(0);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        this.c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }
}
