package mod.trindadedev.manage.theme;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    public static final String THEME_PREF = "themedata";
    public static final String THEME_KEY = "idetheme";

    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;

    public static void applyTheme(Context context, int type) {
        saveTheme(context, type);

        switch (type) {
            case THEME_SYSTEM:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public static int getCurrentTheme(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(THEME_KEY, THEME_SYSTEM);
    }

    private static void saveTheme(Context context, int theme) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(THEME_KEY, theme);
        editor.apply();
    }
}