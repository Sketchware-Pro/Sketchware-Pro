package <?package_name?>;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SketchApplication extends Application {

    private static Context mApplicationContext;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public static Context getContext() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
                DebugActivity.saveErrorLog(SketchApplication.this.getApplicationContext(), Log.getStackTraceString(throwable));
                System.exit(1);
            }
        });

        //Look for saved crash logs and show them
        if (!DebugActivity.getLastSavedErrorLog(getApplicationContext()).isEmpty())
            startActivity(new Intent(getApplicationContext(), DebugActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        super.onCreate();
    }
}