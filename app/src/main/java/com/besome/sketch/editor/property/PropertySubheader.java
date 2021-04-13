package com.besome.sketch.editor.property;

import a.a.a.wB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PropertySubheader extends RelativeLayout {
    public ImageView a;
    public TextView b;

    public PropertySubheader(Context context) {
        super(context);
        a(context);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        wB.a(context, this, 2131427649);
        this.b = (TextView) findViewById(2131232055);
        this.a = (ImageView) findViewById(2131231105);
    }

    public void setHeaderName(String str) {
        this.b.setText(str);
    }

    @SuppressLint("WrongConstant")
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.a.setVisibility(0);
        this.a.setOnClickListener(onClickListener);
    }
}
