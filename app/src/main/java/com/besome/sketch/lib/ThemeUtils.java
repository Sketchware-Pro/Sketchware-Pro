package com.besome.sketch.lib;

import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;

import com.google.android.material.color.MaterialColors;

public class ThemeUtils {

    @ColorInt
    public static int getColor(View view, @AttrRes int resourceId) {
        return MaterialColors.getColor(view, resourceId);
    }
}
