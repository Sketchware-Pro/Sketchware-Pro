package com.besome.sketch;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Process;
import android.util.Log;

import com.sketchware.remod.R;
import com.besome.sketch.tools.CollectErrorActivity;
import com.google.android.material.color.DynamicColors;

import com.google.android.gms.analytics.Tracker;

public class SketchApplication extends Application {

    private static Context mApplicationContext;
    private SharedPreferences MaterialThemeEnable;
    private SharedPreferences MaterialTheme;

    public static Context getContext() {
        return mApplicationContext;
    }

    public synchronized Tracker a() {
        return new Tracker();
    }
    
    public void setUpTheme(){
        MaterialThemeEnable.edit().putBoolean("applyMaterialThemeEnable",MaterialThemeEnable.getBoolean("MaterialThemeEnable",false)).commit();
        MaterialTheme.edit().putString("applyMaterialTheme",MaterialTheme.getString("MaterialTheme","Red")).commit();
    }
	public void applyDynamicThemeIfNeeded(){
		if (MaterialThemeEnable.getBoolean("applyMaterialThemeEnable",false)){
        	// DynamicColors
        	DynamicColors.applyToActivitiesIfAvailable(this);
        }
	}
    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();
        
        MaterialTheme = getSharedPreferences("MaterialTheme", Context.MODE_PRIVATE);
        MaterialThemeEnable = getSharedPreferences("MaterialThemeEnable",Context.MODE_PRIVATE);
        
        setUpTheme();
        applyDynamicThemeIfNeeded();
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            Log.e("SketchApplication", "Uncaught exception on thread " + thread.getName(), throwable);

            Intent intent = new Intent(getApplicationContext(), CollectErrorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("error", Log.getStackTraceString(throwable));
            ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                    .set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            1000,
                            PendingIntent.getActivity(getApplicationContext(), 11111, intent,
                                    PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE));
            Process.killProcess(Process.myPid());
            System.exit(1);
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        });
        super.onCreate();
    }
}
