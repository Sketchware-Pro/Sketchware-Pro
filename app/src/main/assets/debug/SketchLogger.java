package <?package_name?>;

import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class SketchLogger {

    /**
     * Logcat Reader Class
     * <p>
     * Use Cases:
     * Use "SketchLogger.startLogging(Context)" to Start the Logger from anywhere
     * Or, Use "SketchLogger.broadcastLog(String)" directly to Send A Log.
     * Send Context along when logging directly when "startLogging(Context)" hasn't been called yet
     * Use "SketchLogger.stopLogging()" to stop from anywhere
     */

    private static Thread loggerThread = new Thread() {
        @Override
        public void run() {
            isRunning = true;

            try {
                Runtime.getRuntime().exec("logcat -c");
                Process process = Runtime.getRuntime().exec("logcat");

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String logTxt = bufferedReader.readLine();
                    do {
                        broadcastLog(logTxt);
                    } while (isRunning && ((logTxt = bufferedReader.readLine()) != null));
                    //Thread Stopped, Restarting If Not Stopped Manually
                    if (isRunning) {
                        broadcastLog("Logger Got Killed. Restarting Complete");
                        startLogging();
                    } else {
                        broadcastLog("Logger Stopped");
                        return;
                    }
                }
            } catch (Exception e) {
                broadcastLog(e.toString());
                //Auto Restart Logger When Crashed
                startLogging();
            }
        }
    };

    private static boolean isRunning = false;
    private static String packageName = "Undefined";

    public static void startLogging() {
        if (!isRunning) {
            loggerThread.start();
        } else {
            throw new IllegalStateException("Logger already running");
        }
    }

    public static void broadcastLog(String log) {
        Intent intent = new Intent();
        intent.setAction("com.sketchware.remod.ACTION_NEW_DEBUG_LOG");
        intent.putExtra("log", log);
        intent.putExtra("pkgName", packageName);
        SketchApplication.getContext().sendBroadcast(intent);
    }

    public static void stopLogging() {
        if (isRunning) {
            isRunning = false;
            Log.i("stopLogging()", "Stopping Logger By User Request");
        } else {
            throw new IllegalStateException("Logger not running");
        }
    }
}
