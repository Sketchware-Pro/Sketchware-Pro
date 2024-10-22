package com.besome.sketch

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Process
import android.util.Log
import com.besome.sketch.tools.CollectErrorActivity
import mod.trindadedev.manage.theme.ThemeManager

class SketchApplication : Application() {

    companion object {
        private lateinit var mApplicationContext: Context

        @JvmStatic
        fun getContext(): Context = mApplicationContext
    }

    override fun onCreate() {
        super.onCreate()
        mApplicationContext = applicationContext

        val defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("SketchApplication", "Uncaught exception on thread ${thread.name}", throwable)

            // Prepare intent to launch CollectErrorActivity
            val intent = Intent(applicationContext, CollectErrorActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("error", Log.getStackTraceString(throwable))
            }

            // Schedule the CollectErrorActivity to be launched after a delay
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                11111,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                1000,
                pendingIntent
            )

            // Kill the current process and exit
            Process.killProcess(Process.myPid())
            System.exit(1)

            // Call the previous uncaught exception handler if there is one
            defaultUncaughtExceptionHandler?.uncaughtException(thread, throwable)
        }

        // Apply the current theme using ThemeManager
        ThemeManager.applyTheme(this, ThemeManager.getCurrentTheme(this))
    }
}
