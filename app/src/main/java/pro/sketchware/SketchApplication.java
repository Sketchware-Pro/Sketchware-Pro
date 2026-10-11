package pro.sketchware;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;

import com.besome.sketch.tools.CollectErrorActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import pro.sketchware.utility.theme.ThemeManager;

public class SketchApplication extends Application {
    private static Context mApplicationContext;

    /**
     * Builds without a real google-services.json never initialize Firebase, which makes
     * FirebaseCrashlytics.getInstance() and FirebaseAnalytics.getInstance() crash.
     * Initialize it with placeholder options so the rest of the app can run; nothing is reported.
     */
    private void initFirebaseIfMissing() {
        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this, new FirebaseOptions.Builder()
                        .setApplicationId("1:000000000000:android:0000000000000000")
                        .setApiKey("AIzaSyDummyKeyDummyKeyDummyKeyDummyKey0")
                        .setProjectId("dummy-project")
                        .build());
            }
        } catch (Exception e) {
            Log.w("SketchApplication", "Failed to initialize placeholder Firebase", e);
        }
    }

    public static Context getContext() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
                Intent intent = new Intent(getApplicationContext(), CollectErrorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("error", Log.getStackTraceString(throwable));
                startActivity(intent);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        });
        super.onCreate();
        initFirebaseIfMissing();
        ThemeManager.applyTheme(this, ThemeManager.getCurrentTheme(this));
    }
}
