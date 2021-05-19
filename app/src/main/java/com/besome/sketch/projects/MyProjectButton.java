package com.besome.sketch.projects;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.wB;

public class MyProjectButton extends LinearLayout {
    public Context a;
    public int b;
    public LinearLayout c;
    public ImageView d;
    public TextView e;

    public MyProjectButton(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        this.a = context;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        setLayoutParams(layoutParams);
        wB.a(context, this, 2131427580);
        this.c = (LinearLayout) findViewById(2131231120);
        this.d = (ImageView) findViewById(2131231090);
        this.e = (TextView) findViewById(2131231561);
    }
}
