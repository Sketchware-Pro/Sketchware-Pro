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
        a(context);
    }

    public void a(Context context) {
        wB.a(context, this, R.layout.widget_layout);
        a = findViewById(R.id.img_widget);
        b = findViewById(R.id.tv_widget);
        b.setTextSize(12.0f);
        b.setGravity(3);
        b.setPadding((int)wB.a(getContext(), 2.0f), 0, 0, 0);
        setDrawingCacheEnabled(true);
    }

    public int getWidgetImageResId() {
        return c;
    }

    public String getWidgetName() {
        return b.getText().toString();
    }

    public int getWidgetType() {
        return d;
    }

    public void setWidgetImage(int widget_img_res_id) {
        c = widget_img_res_id;
        a.setImageResource(widget_img_res_id);
    }

    public void setWidgetName(String widgetName) {
        b.setText(widgetName);
    }

    public void setWidgetType(a widgetType) {
        setBackgroundResource(R.drawable.icon_bg);
        d = widgetType.ordinal();
    }

    public static enum a {
        a,
        b;
    }
}

