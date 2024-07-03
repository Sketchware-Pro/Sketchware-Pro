package mod.trindadedev.manage.theme;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    
    public static final String THEME_PREF = "themedata";
    public static final String THEME_KEY = "theme";
    public static final String DYNAMIC_KEY = "dynamic_theme";
    
    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;

    public static void applyTheme(Context context, int type) {
        saveTheme(context, THEME_KEY, type);

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
    
    public static int getThemeInt(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(THEME_KEY, THEME_SYSTEM);
    }
    
    public static void useDynamicColors(Context context, boolean use) {
        saveTheme(context, DYNAMIC_KEY, use);
    }
    
    public static boolean isUseDynamic(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        return preferences.getBoolean(DYNAMIC_KEY, true);
    }

    private static void saveTheme(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    
    private static void saveTheme(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}