package mod.bobur;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.util.TypedValue;
import android.widget.ImageView;

import mod.remaker.util.ThemeUtils;

public class BoburUtils {

    public static boolean isDarkModeEnabled(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (ThemeUtils.isDarkThemeEnabled(context)) {
            return true;
        } else if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            return true;
        } else {
            return false;
        }
    }
}
