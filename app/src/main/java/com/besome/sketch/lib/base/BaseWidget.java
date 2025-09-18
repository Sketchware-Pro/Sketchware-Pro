package com.besome.sketch.lib.base;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.WidgetLayoutBinding;

public class BaseWidget extends LinearLayout {
    @DrawableRes
    private int widgetImgResId;
    private int widgetType;

    private final WidgetLayoutBinding binding;

    public BaseWidget(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.widget_layout, this, true);
        binding = WidgetLayoutBinding.bind(this);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        initialize();
    }

    private void initialize() {
        setBackgroundResource(R.drawable.icon_bg);
        setDrawingCacheEnabled(true);
    }

    public int getWidgetImageResId() {
        return widgetImgResId;
    }

    public String getWidgetName() {
        return Helper.getText(binding.tvWidget);
    }

    public void setWidgetName(String widgetName) {
        binding.tvWidget.setText(widgetName);
    }

    public int getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(a widgetType) {
        this.widgetType = widgetType.ordinal();
    }

    public void setWidgetImage(@DrawableRes int image) {
        widgetImgResId = image;
        binding.imgWidget.setImageResource(image);
    }

    public void setWidgetNameTextSize(float sizeSp) {
        binding.tvWidget.setTextSize(sizeSp);
    }

    public enum a {
        a,
        b
    }
}
