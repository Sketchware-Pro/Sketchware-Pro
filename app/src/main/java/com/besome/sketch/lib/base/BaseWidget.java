package com.besome.sketch.lib.base;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

public class BaseWidget extends LinearLayout {
    private ImageView img_widget;
    private TextView tv_widget;
    private int widgetImgResId;
    private int widgetType;

    public BaseWidget(Context context) {
        super(context);
        a(context);
    }

    public void a(Context context) {
        wB.a(context, this, R.layout.widget_layout);
        img_widget = findViewById(R.id.img_widget);
        tv_widget = findViewById(R.id.tv_widget);
        tv_widget.setTextSize(12);
        tv_widget.setGravity(Gravity.LEFT);
        tv_widget.setPadding((int) wB.a(getContext(), 2.0f), 0, 0, 0);
        setDrawingCacheEnabled(true);
    }

    public int getWidgetImageResId() {
        return widgetImgResId;
    }

    public String getWidgetName() {
        return tv_widget.getText().toString();
    }

    public int getWidgetType() {
        return widgetType;
    }

    public void setWidgetImage(int widgetImgResId) {
        this.widgetImgResId = widgetImgResId;
        img_widget.setImageResource(widgetImgResId);
    }

    public void setWidgetName(String widgetName) {
        tv_widget.setText(widgetName);
    }

    public void setWidgetNameTextSize(float sizeSp) {
        tv_widget.setTextSize(sizeSp);
    }

    public void setWidgetType(a widgetType) {
        setBackgroundResource(R.drawable.icon_bg);
        this.widgetType = widgetType.ordinal();
    }

    public enum a {
        a,
        b
    }
}
