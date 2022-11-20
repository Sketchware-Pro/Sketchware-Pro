package com.besome.sketch.beans;

import android.graphics.Color;

public class ColorBean {
    public int colorCode;
    public String colorName;
    public int displayNameColor;
    public int icon;

    public ColorBean(String color, String name, String displayColor, int icon) {
        this(Color.parseColor(color), name, Color.parseColor(displayColor), icon);
    }

    public ColorBean(int color, String name, int displayColor, int icon) {
        colorCode = color;
        colorName = name;
        displayNameColor = displayColor;
        this.icon = icon;
    }

    public String getColorCode(boolean isWideFilling) {
        int color = colorCode;
        if (color == 0) return "TRANSPARENT";

        if (color == 0xffffff) return "NONE";

        if (isWideFilling) return String.format("#%08X", color);

        return String.format("#%06X", color & 0xffffff);
    }
}
