package mod.trindadedev.manage.theme;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.color.DynamicColors;

import com.besome.sketch.SketchApplication;
import com.sketchware.remod.R;

public class ThemeManager {
    
    public static final String THEME_PREF = "themedata";
    
    public static final String THEME_KEY = "theme";
    public static final String DYNAMIC_KEY = "dynamic_theme";
    public static final String VIEW_PANE_KEY = "view_pane_theme";
    
    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;
    
    public static final boolean VIEW_PANE_MD3 = false;

    // apply Sketchware theme (system, light, dark)
    public static void applyTheme(Context context, int type) {
        savePreference(context, THEME_KEY, type);

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
     
    // apply dynamic colors if possible and if enabled.
    public static void applyTheme (Context context, SketchApplication app) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        if (preferences.getBoolean(DYNAMIC_KEY, true)) {
            DynamicColors.applyToActivitiesIfAvailable(app);
        }
    }
    
    // apply view pane theme (md2(0) md3(1))
    public static void applyTheme (Context context, boolean type) {
        savePreference(context, VIEW_PANE_KEY, type);
    }
    
    // get sketchware theme (system, light, dark)
    public static int getSketchwareTheme(Context context) {
        return getPreference(THEME_KEY, THEME_SYSTEM, context);
    }
    
    // get view pane theme (md2(0), md3(1)) 
    public static boolean getViewPaneUseMd3(Context context) {
        return getPreference(VIEW_PANE_KEY, VIEW_PANE_MD3, context);
    }
    
    public static Context viewPaneContextWrapper (Context context) {
        if (getViewPaneUseMd3(context)) {
             return new ContextThemeWrapper(context, R.style.ViewEditorThemeMd3);
        } else {
             return new ContextThemeWrapper(context, R.style.ViewEditorThemeMd2);
        }
    }
    
    // get monet android 12+ theme (true or false)
    public static boolean getDynamicTheme(Context context) {
        return getPreference(DYNAMIC_KEY, true, context);
    }
    
    public static void setUseMonet(Context context, boolean use) {
        savePreference(context, DYNAMIC_KEY, use);
    }
    
    // get preference string
    public static String getPreference(String key, String defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }
    
    // get preference int
    public static int getPreference(String key, int defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }
    
    // get preference boolean
    public static boolean getPreference(String key, boolean defaultValue, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }
    
    // save string
    private static void savePreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    
    // save int
    private static void savePreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    
    // save boolean
    private static void savePreference(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}