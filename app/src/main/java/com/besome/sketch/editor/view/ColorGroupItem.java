package com.besome.sketch.editor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.wB;

public class ColorGroupItem extends RelativeLayout {
    public Context a;
    public TextView b;
    public ImageView c;

    public ColorGroupItem(Context context) {
        super(context);
        a(context);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        this.a = context;
        wB.a(context, this, 2131427374);
        this.b = (TextView) findViewById(2131231916);
        this.c = (ImageView) findViewById(2131231182);
        setPadding(0, 0, 4, 0);
    }
}
