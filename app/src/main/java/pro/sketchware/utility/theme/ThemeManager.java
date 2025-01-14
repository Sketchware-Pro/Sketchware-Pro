package pro.sketchware.utility.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.color.DynamicColors;

import java.util.ArrayList;

import pro.sketchware.R;

public class ThemeManager {

    private static final String THEME_PREF = "themedata";
    private static final String MODE_KEY = "idetheme";// theme mode : light , dark and follow system
    private static final String THEME_KEY = "idethemecolor"; // eg : green apple , sakura...
    private static final String AMOLED_KEY = "ideisamoled";

    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;

    public static void applyMode(Context context, int type) {
        saveThemeMode(context, type);
        applyTheme(context, getCurrentTheme(context));
        if (isAmoledEnabled(context)) applyAmoled(context);

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
                context.setTheme(R.style.Theme_SketchwarePro_Default);
                break;
            case (1):
                context.setTheme(R.style.Theme_SketchwarePro);
                break;
            case (2):
                context.setTheme(R.style.Theme_SketchwarePro_Lavender);
                break;
            case (3):
                context.setTheme(R.style.Theme_SketchwarePro_yogNesh);
                break;
            case (4):
                context.setTheme(R.style.Theme_SketchwarePro_YinYang);
                break;
            case (5):
                context.setTheme(R.style.Theme_SketchwarePro_GreenApple);
                break;
            case (6):
                context.setTheme(R.style.Theme_SketchwarePro_Sakura);
                break;
            case (7):
                context.setTheme(R.style.Theme_SketchwarePro_AquaMist);
                break;
            case (8):
                context.setTheme(R.style.Theme_SketchwarePro_Tako);
                break;
            default:
                context.setTheme(R.style.Theme_SketchwarePro_Default);
        }
        saveTheme(context, theme);
    }

    public static boolean isAmoledEnabled(Context context) {
        return getPreferences(context).getBoolean(AMOLED_KEY, false);
    }

    public static int getSystemAppliedTheme(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeId, outValue, true);
        return outValue.data;
    }

    public static void applyAmoled(Context context) {
        context.getTheme().applyStyle(R.style.Theme_SketchwarePro_Amoled, true);
    }

    public static void setAmoled(Context context, boolean bool) {
        getPreferences(context).edit().putBoolean(AMOLED_KEY, bool).apply();
        if (bool) applyAmoled(context);
    }

    public static int getCurrentTheme(Context context) {
        return getPreferences(context).getInt(THEME_KEY, 0);
    }

    public static int getCurrentMode(Context context) {
        return getPreferences(context).getInt(MODE_KEY, THEME_SYSTEM);
    }

    public static ArrayList<ThemeItem> getThemesList() {
        ArrayList<ThemeItem> themeList = new ArrayList<>();

        themeList.add(new ThemeItem("Default", R.style.Theme_SketchwarePro_Default, 0));
        if (DynamicColors.isDynamicColorAvailable()) themeList.add(new ThemeItem("Dynamic", R.style.Theme_SketchwarePro, 1));
        themeList.add(new ThemeItem("Lavender", R.style.Theme_SketchwarePro_Lavender, 2));
        themeList.add(new ThemeItem("Cherry & yogurt", R.style.Theme_SketchwarePro_yogNesh, 3));
        themeList.add(new ThemeItem("Yin & Yang", R.style.Theme_SketchwarePro_YinYang, 4));
        themeList.add(new ThemeItem("Green Apple", R.style.Theme_SketchwarePro_GreenApple, 5));
        themeList.add(new ThemeItem("Sakura", R.style.Theme_SketchwarePro_Sakura, 6));
        themeList.add(new ThemeItem("AquaMist", R.style.Theme_SketchwarePro_AquaMist, 7));
        themeList.add(new ThemeItem("Tako", R.style.Theme_SketchwarePro_Tako, 8));

        return themeList;
    }

    public static int getColorFromTheme(Context context, int themeResId, int attrResId) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, themeResId);
        TypedArray typedArray = themeWrapper.obtainStyledAttributes(new int[]{attrResId});
        int color = typedArray.getColor(0, 0xbdbdbd);
        typedArray.recycle();
        return color;
    }


    private static void saveThemeMode(Context context, int theme) {
        getPreferences(context).edit().putInt(MODE_KEY, theme).apply();
    }


    public static void saveTheme(Context context, int theme) {
        getPreferences(context).edit().putInt(THEME_KEY, theme).apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
    }
}