package com.besome.sketch.lib.base;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class BaseWidget extends LinearLayout {
    private ImageView img_widget;
    private TextView tv_widget;
    @DrawableRes
    private int widgetImgResId;
    private int widgetType;

    public BaseWidget(Context context) {
        super(context);
        View.inflate(context, R.layout.widget_layout, this);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        a(context);
    }

    public void a(Context context) {
        img_widget = findViewById(R.id.img_widget);
        tv_widget = findViewById(R.id.tv_widget);

        setBackgroundResource(R.drawable.icon_bg);
        setDrawingCacheEnabled(true);
    }

    public int getWidgetImageResId() {
        return widgetImgResId;
    }

    public String getWidgetName() {
        return Helper.getText(tv_widget);
    }

    public void setWidgetName(String widgetName) {
        tv_widget.setText(widgetName);
    }

    public int getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(a widgetType) {
        this.widgetType = widgetType.ordinal();
    }

    public void setWidgetImage(@DrawableRes int image) {
        this.widgetImgResId = image;
        img_widget.setImageResource(image);
    }

    public void setWidgetNameTextSize(float sizeSp) {
        tv_widget.setTextSize(sizeSp);
    }

    public enum a {
        a,
        b
    }
}
