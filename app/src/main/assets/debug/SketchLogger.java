package <?package_name?>;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class provides a mechanism to read and broadcast logcat messages to a designated receiver.
 * It utilizes a dedicated thread to continuously read logcat output and send it via a broadcast intent.
 *
 * Usage:
 * - Call `SketchLogger.startLogging()` to begin logging.
 * - Call `SketchLogger.stopLogging()` to stop logging.
 * - Use `SketchLogger.broadcastLog(String)` to manually send a debug log message.
 */
public class SketchLogger {
    private static Thread loggerThread;
    private static volatile boolean isRunning = false;

    public static void startLogging() {
        if (!isRunning) {
            loggerThread = new Thread(() -> {
                isRunning = true;
                try {
                    Runtime.getRuntime().exec("logcat -c");
                    Process process = Runtime.getRuntime().exec("logcat");

                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String logTxt;
                        while (isRunning && (logTxt = bufferedReader.readLine()) != null) {
                            broadcastLog(logTxt);
                        }

                        if (isRunning) {
                            broadcastLog("Logger got killed. Restarting.");
                            startLogging();
                        } else {
                            broadcastLog("Logger stopped.");
                        }
                    }
                } catch (IOException e) {
                    broadcastLog(e.getMessage());
                }
            });
            loggerThread.start();
        } else {
            throw new IllegalStateException("Logger already running");
        }
    }

    public static void stopLogging() {
        if (isRunning) {
            isRunning = false;
            broadcastLog("Stopping logger by user request.");
        } else {
            throw new IllegalStateException("Logger not running");
        }
    }

    public static void broadcastLog(String log) {
        Context context = SketchApplication.getContext();

        Intent intent = new Intent();
        intent.setAction("com.sketchware.remod.ACTION_NEW_DEBUG_LOG");
        intent.putExtra("log", log);
        intent.putExtra("packageName", context.getPackageName());
        context.sendBroadcast(intent);
    }
}
