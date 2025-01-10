package pro.sketchware.utility.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;

import java.util.ArrayList;
import java.util.List;

import pro.sketchware.R;

public class ThemeManager {

    private static final String THEME_PREF = "themedata";
    private static final String MODE_KEY = "idetheme"; // theme mode : light , dark and follow system
    private static final String THEME_KEY = "idethemecolor"; // theme : greenapple , dark and follow system

    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;

    public static void applyMode(Context context, int type) {
        saveThemeMode(context, type);
        applyTheme(context, getCurrentTheme(context));

        switch (type) {
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

    public static void applyTheme(Context context, int theme) {
        switch (theme) {
            case (0):
                context.setTheme(R.style.GreenApple);
                break;
            case (1):
                context.setTheme(R.style.Lavender);
                break;
            case (2):
                context.setTheme(R.style.yogNesh);
                break;
            default:
                context.setTheme(R.style.GreenApple);
        }
        saveTheme(context,theme);
    }

    public static int getCurrentMode(Context context) {
        return getPreferences(context).getInt(MODE_KEY, THEME_SYSTEM);
    }

    public static int getCurrentTheme(Context context) {
        return getPreferences(context).getInt(THEME_KEY, THEME_SYSTEM);
    }

    public static int getSystemAppliedTheme(Context context){
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeName, outValue, true);
        return switch (outValue.string.toString()) {
            case ("GreenApple") -> 0;
            case ("Lavender") -> 1;
            case ("yogNesh") -> 2;
            default -> 0;
        };
    }

    public static boolean isSystemMode(Context context) {
        return getCurrentMode(context) == THEME_SYSTEM;
    }


    private static void saveThemeMode(Context context, int mode) {
        getPreferences(context).edit().putInt(MODE_KEY, mode).apply();
    }

    public static void saveTheme(Context context, int theme) {
        getPreferences(context).edit().putInt(THEME_KEY, theme).apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
    }

    public static ArrayList<ThemeItem> getThemesList(){
        ArrayList<ThemeItem> themeList = new ArrayList<>();

        themeList.add(new ThemeItem("GreenApple", R.style.GreenApple, 0));
        themeList.add(new ThemeItem("Lavender", R.style.Lavender, 1));
        themeList.add(new ThemeItem("Yog & esh", R.style.yogNesh, 2));

        return themeList;
    }

    public static int getColorFromTheme(Context context, int themeResId, int attrResId) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, themeResId);
        TypedArray typedArray = themeWrapper.obtainStyledAttributes(new int[]{attrResId});
        int color = typedArray.getColor(0, 0xbdbdbd);
        typedArray.recycle();
        return color;
    }

    public static int getSystemAppliedMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        return switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_NO -> THEME_LIGHT;
            case Configuration.UI_MODE_NIGHT_YES -> THEME_DARK;
            default -> THEME_SYSTEM;
        };
    }



}