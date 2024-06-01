package <?package_name?>;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

public class SketchApplication extends Application {

    private static Context mApplicationContext;

    public static Context getContext() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable throwable) {
                        Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("stacktrace", Log.getStackTraceString(throwable));
                        startActivity(intent);
                        Process.killProcess(Process.myPid());
                        System.exit(1);
                    }
                });
        super.onCreate();
    }
}
