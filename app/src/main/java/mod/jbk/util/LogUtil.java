package mod.jbk.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LogUtil {
    private static boolean loggingEnabled = true;
    private static boolean logToLogcatToo = false;

    public static void setLoggingEnabled(boolean enabled) {
        loggingEnabled = enabled;
    }

    public static void setLogToLogcatToo(boolean log) {
        logToLogcatToo = log;
    }

    /**
     * Similar to {@link Log#d(String, String)}, but to log to {@link System#out}, or while compiling,
     * /Internal storage/.sketchware/debug.txt
     */
    public static void d(String tag, String message) {
        d(tag, message, null);
    }

    /**
     * Similar to {@link Log#d(String, String, Throwable)}, but to log to {@link System#out}, or while compiling,
     * /Internal storage/.sketchware/debug.txt
     */
    public static void d(String tag, String message, Throwable throwable) {
        LogUtil.println('D', tag, message, throwable);
    }

    /**
     * Similar to {@link Log#w(String, String)}, but to log to {@link System#out}, or while compiling,
     * /Internal storage/.sketchware/debug.txt
     */
    public static void w(String tag, String message) {
        LogUtil.w(tag, message, null);
    }

    /**
     * Similar to {@link Log#w(String, String, Throwable)}, but to log to {@link System#out}, or while compiling,
     * /Internal storage/.sketchware/debug.txt
     */
    public static void w(String tag, String message, Throwable throwable) {
        LogUtil.println('W', tag, message, throwable);
    }

    /**
     * Similar to {@link Log#e(String, String)}, but to log to {@link System#out}, or while compiling,
     * * /Internal storage/.sketchware/debug.txt
     */
    public static void e(String tag, String message) {
        LogUtil.e(tag, message, null);
    }

    /**
     * Similar to {@link Log#e(String, String, Throwable)}, but to log to {@link System#out}, or while compiling,
     * * /Internal storage/.sketchware/debug.txt
     */
    public static void e(String tag, String message, Throwable throwable) {
        LogUtil.println('E', tag, message, throwable);
    }

    private static void println(char type, String tag, String message, Throwable throwable) {
        if (loggingEnabled) {
            StringBuilder toLog = new StringBuilder(getDateAndTime(System.currentTimeMillis()));
            toLog.append(" ");
            toLog.append(type);
            toLog.append('/');
            toLog.append(tag);
            toLog.append(": ");
            toLog.append(message);

            if (throwable != null) {
                toLog.append('\n');
                toLog.append(Log.getStackTraceString(throwable));
            }

            System.out.println(toLog);
        }

        if (logToLogcatToo) {
            switch (type) {
                case 'D':
                    Log.d(tag, message, throwable);
                    break;

                case 'W':
                    Log.w(tag, message, throwable);
                    break;

                case 'E':
                    Log.e(tag, message, throwable);
                    break;
            }
        }
    }

    private static String getDateAndTime(long millis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        return simpleDateFormat.format(new Date(millis));
    }

    public static void log(String tag, String prefixIfOneLiner, String logMessageIfMultipleLines, ArrayList<String> list) {
        String toLog = list.toString();
        if (toLog.length() > 1023) {
            ArrayList<String> loggableStrings = new ArrayList<>();
            int beginIndex = 0;
            while (beginIndex < (toLog.length() - (toLog.length() % 1024))) {
                loggableStrings.add(toLog.substring(beginIndex, beginIndex + 1024));
                beginIndex += 1024;
            }
            loggableStrings.add(toLog.substring(beginIndex));

            Log.d(tag, logMessageIfMultipleLines);
            for (String loggableString : loggableStrings) {
                Log.d(tag, loggableString);
            }
        } else {
            Log.d(tag, prefixIfOneLiner + list);
        }
    }
}
