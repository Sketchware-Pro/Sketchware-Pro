package com.besome.sketch.beans;

import android.graphics.Color;

import com.google.android.flexbox.FlexItem;

public class ColorBean {
    public int colorCode;
    public String colorName;
    public int displayNameColor;
    public int icon;

    public ColorBean(String color, String name, String displayColor, int icon) {
        this(Color.parseColor(color), name, Color.parseColor(displayColor), icon);
    }

    public ColorBean(int color, String name, int displayColor, int icon) {
        this.colorCode = color;
        this.colorName = name;
        this.displayNameColor = displayColor;
        this.icon = icon;
    }

    public String getColorCode(boolean isWideFilling) {
        int color = this.colorCode;
        if (color == 0) return "TRANSPARENT";

        if (color == 16777215) return "NONE";

        if (isWideFilling) return String.format("#%08X", Integer.valueOf(color));

        return String.format("#%06X", Integer.valueOf(color & FlexItem.MAX_SIZE));
    }
}
