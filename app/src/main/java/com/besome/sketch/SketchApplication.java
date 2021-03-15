package com.besome.sketch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class SketchApplication extends MultiDexApplication {

    private static Context mApplicationContext;
    public Tracker a;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    public synchronized Tracker a() {
        if (a == null) {
            a = GoogleAnalytics.getInstance(this).newTracker("UA-80718117-1");
        }
        return a;
    }

    public void onCreate() {
        mApplicationContext = getApplicationContext();
        this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Intent intent = new Intent(SketchApplication.this.getApplicationContext(), DebugActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("error", Log.getStackTraceString(throwable));
                ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                        .set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                1000,
                                PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT));
                Process.killProcess(Process.myPid());
                System.exit(1);
                uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        });
        super.onCreate();
    }

    public static Context getContext() {
        return mApplicationContext;
    }
}