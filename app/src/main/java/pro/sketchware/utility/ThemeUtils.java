package pro.sketchware.utility;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;

import com.google.android.material.color.MaterialColors;

public class ThemeUtils {
    ThemeUtils() {
    }

    @ColorInt
    public static int getColor(View view, @AttrRes int resourceId) {
        return MaterialColors.getColor(view, resourceId);
    }

    public static boolean isDarkThemeEnabled(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        int nightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
