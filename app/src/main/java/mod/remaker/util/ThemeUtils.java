package mod.remaker.util;

import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;

public class ThemeUtils {
    private ThemeUtils() {
    }

    public static @ColorInt int getColor(@NonNull View view, @AttrRes int resourceId) {
        return MaterialColors.getColor(view, resourceId);
    }
}
