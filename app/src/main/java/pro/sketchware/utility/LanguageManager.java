package pro.sketchware.utility;

import android.app.LocaleManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

public class LanguageManager {

    public static final String LANGUAGE_SYSTEM = "";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_TURKISH = "tr";
    private static final String LANGUAGE_PREF = "languagedata";
    private static final String LANGUAGE_KEY = "language";

    public static String getLanguage(Context context) {
        return getPreferences(context).getString(LANGUAGE_KEY, LANGUAGE_SYSTEM);
    }

    /**
     * Returns a context whose resources use the selected language. Used for the application
     * context, which {@code Helper.getResString()} reads from.
     */
    public static Context wrap(Context base) {
        String language = getLanguage(base);
        if (language.isEmpty()) return base;
        Configuration config = new Configuration(base.getResources().getConfiguration());
        config.setLocale(Locale.forLanguageTag(language));
        return base.createConfigurationContext(config);
    }

    /** Applies the selected language to all activities. */
    public static void applyLanguage(Context context) {
        String language = getLanguage(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // AppCompatDelegate ignores the call on API 33+ while no activity exists yet,
            // so talk to the framework directly. The system persists this per-app locale.
            context.getSystemService(LocaleManager.class).setApplicationLocales(LocaleList.forLanguageTags(language));
        } else {
            AppCompatDelegate.setApplicationLocales(language.isEmpty()
                    ? LocaleListCompat.getEmptyLocaleList()
                    : LocaleListCompat.forLanguageTags(language));
        }
    }

    /** Saves the language and restarts the app so every screen and the application context pick it up. */
    public static void setLanguageAndRestart(Context context, String language) {
        getPreferences(context).edit().putString(LANGUAGE_KEY, language).commit();
        applyLanguage(context);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Runtime.getRuntime().exit(0);
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(LANGUAGE_PREF, Context.MODE_PRIVATE);
    }
}
