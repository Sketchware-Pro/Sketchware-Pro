package pro.sketchware;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.besome.sketch.tools.CollectErrorActivity;

import pro.sketchware.utility.AnalyticsHelper;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.LanguageManager;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.theme.ThemeManager;

public class SketchApplication extends Application {
    private static final String SHRINKER_PREFS = "shrinker_failure";
    private static final String PREF_ENGINE = "engine";
    private static final String PREF_PATH = "path";
    private static Context mApplicationContext;
    private static volatile String runningShrinker;

    public static Context getContext() {
        return mApplicationContext;
    }

    /** Non-null only while R8/ProGuard runs, so an OOM there is reported as a skipped shrinker. */
    public static void setRunningShrinker(String engine) {
        runningShrinker = engine;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageManager.wrap(base));
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();
        SharedPreferences prefs = getSharedPreferences(SHRINKER_PREFS, MODE_PRIVATE);
        String engine = prefs.getString(PREF_ENGINE, null);
        String path = prefs.getString(PREF_PATH, null);
        if (engine != null && path != null) {
            prefs.edit().clear().apply();
            SketchwareUtil.toast(getString("R8".equals(engine)
                    ? R.string.shrinker_skipped_r8 : R.string.shrinker_skipped_proguard, path),
                    Toast.LENGTH_LONG);
        }

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
                String activeShrinker = runningShrinker;
                if (throwable instanceof OutOfMemoryError && activeShrinker != null) {
                    /* The heap can run out on the main thread while the shrinker is still
                     * working. Save a small log and defer the toast to the next launch
                     * instead of showing the full crash dialog. */
                    try {
                        String path = FileUtil.getExternalStorageDir() + "/.sketchware/shrinker_crash.log";
                        FileUtil.writeFile(path, activeShrinker
                                + " failed on this device (OutOfMemoryError)\n" + throwable.getMessage());
                        boolean saved = getSharedPreferences(SHRINKER_PREFS, MODE_PRIVATE).edit()
                                .putString(PREF_ENGINE, activeShrinker)
                                .putString(PREF_PATH, path)
                                .commit();
                        if (!saved) {
                            Log.w("SketchApplication", "Failed to persist shrinker OOM notification");
                        }
                    } catch (Throwable ignored) {
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), CollectErrorActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("error", Log.getStackTraceString(throwable));
                    startActivity(intent);
                }
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });
        super.onCreate();
        ThemeManager.applyTheme(this, ThemeManager.getCurrentTheme(this));
        LanguageManager.applyLanguage(this);
        AnalyticsHelper.logDeviceAbi(this);
    }
}
