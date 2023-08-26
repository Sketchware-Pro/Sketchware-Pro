package com.besome.sketch.lib.base;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

public class BaseWidget extends LinearLayout {
    public ImageView a;
    public TextView b;
    public int c;
    public int d;

    public BaseWidget(Context context) {
        super(context);
        this.a(context);
    }

    public void a(Context context) {
        wB.a((Context)context, (ViewGroup)this, (int)R.layout.widget_layout);
        this.a = (ImageView)this.findViewById(R.id.img_widget);
        this.b = (TextView)this.findViewById(R.id.tv_widget);
        this.b.setTextSize(12.0f);
        this.b.setGravity(3);
        this.b.setPadding((int)wB.a((Context)this.getContext(), (float)2.0f), 0, 0, 0);
        this.setDrawingCacheEnabled(true);
    }

    public int getWidgetImageResId() {
        return this.c;
    }

    public String getWidgetName() {
        return this.b.getText().toString();
    }

    public int getWidgetType() {
        return this.d;
    }

    public void setWidgetImage(int n) {
        this.c = n;
        this.a.setImageResource(n);
    }

    public void setWidgetName(String string) {
        this.b.setText((CharSequence)string);
    }

    public void setWidgetType(a a2) {
        this.setBackgroundResource(R.drawable.icon_bg);
        this.d = a2.ordinal();
    }

    public static enum a {
        a,
        b;
    }
}

