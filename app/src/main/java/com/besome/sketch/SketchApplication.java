package com.besome.sketch;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.analytics.Tracker;

public class SketchApplication extends Application {

    private static Context mApplicationContext;

    public static Context getContext() {
        return mApplicationContext;
    }

    public synchronized Tracker a() {
        return new Tracker();
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();

        // Setup handler for uncaught exceptions.
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            throwable.printStackTrace();
            DebugActivity.saveErrorLog(getContext(), Log.getStackTraceString(throwable));
            System.exit(1);
        });

        //Look for saved crash logs and show them
        if (!DebugActivity.getLastSavedErrorLog(getContext()).isEmpty())
            startActivity(new Intent(getContext(), DebugActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        super.onCreate();
    }
}
