package <?package_name?>;

import android.content.Intent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.content.Context;
import android.util.Log;

public class SketchLogger {

/* Logcat Reader Class
Available Methods:
SketchLogger.startLogging(Context)
SketchLogger.startLogging()

SketchLogger.broadcastLog(Context, String)
SketchLogger.broadcastLog(String)

SketchLogger.stopLogging()

Use Cases: 
Use "SketchLogger.startLogging(Context)" to Start the Logger from anywhere
Or, Use "SketchLogger.broadcastLog(String)" directly to Send A Log. 
Send Context along when logging directly when "startLogging(Context)" hasn't been called yet
Use "SketchLogger.stopLogging()" to stop from anywhere

**DO NOT CALL THIS MORE THAN ONCE WITHOUT STOPPING THE PREVIOUS**
**OTHERWISE IT WILL RESULT IN DUPLICATE LOGS**
*/

    static Thread loggerThread;
    static Context context;
    static boolean isRunning = false;
    static String packageName = "Undefined";

    public static void startLogging() {
        loggerThread = new Thread() {
            @Override
            public void run() {
                isRunning = true;
                try {
                    Runtime.getRuntime().exec("logcat -c");
                    Process process = Runtime.getRuntime().exec("logcat");
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
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
                } catch (Exception e) {
                    broadcastLog(e.toString());
                    //Auto Restart Logger When Crashed
                    startLogging();
                }
            }
        };
        loggerThread.start();

    }

    public static void startLogging(Context _context) {
        context = _context;
        packageName = _context.getPackageName().toString();
        startLogging();
    }

    public static void broadcastLog(String log) {
        Intent intent = new Intent();
        intent.setAction("RECEIVE_NUB_LOGS");
        intent.putExtra("log", log);
        intent.putExtra("pkgName", packageName);
        if (context != null) {
            context.sendBroadcast(intent);
        }
    }

    public static void broadcastLog(Context c, String log) {
        Intent intent = new Intent();
        intent.setAction("RECEIVE_NUB_LOGS");
        intent.putExtra("log", log);
        intent.putExtra("pkgName", c.getPackageName());
        c.sendBroadcast(intent);

    }

    public static void stopLogging() {
        if (loggerThread != null) {
            isRunning = false;
            Log.i("stopLogging()", "Stopping Logger By User Request");
            loggerThread = null;
        }
    }
}
