package mod.jbk.util;

import android.util.Log;

import java.lang.reflect.Field;
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
        if (loggingEnabled) {
            System.out.println(new StringBuilder(getDateAndTime(System.currentTimeMillis()))
                    .append(" D/").append(tag).append(": ").append(message));
        }

        if (logToLogcatToo) {
            Log.d(tag, message);
        }
    }

    /**
     * Similar to {@link Log#d(String, String)}, but to log to {@link System#out}, or while compiling,
     * /Internal storage/.sketchware/debug.txt
     */
    public static void d(String tag, String message, Throwable throwable) {
        if (loggingEnabled) {
            System.out.println(new StringBuilder(getDateAndTime(System.currentTimeMillis()))
                    .append(" D/").append(tag).append(": ").append(message).append('\n')
                    .append(Log.getStackTraceString(throwable)));
        }

        if (logToLogcatToo) {
            Log.d(tag, message, throwable);
        }
    }

    /**
     * Similar to {@link Log#e(String, String)}, but to log to {@link System#out}, or while compiling,
     * * /Internal storage/.sketchware/debug.txt
     */
    public static void e(String tag, String message) {
        if (loggingEnabled) {
            System.out.println(new StringBuilder(getDateAndTime(System.currentTimeMillis()))
                    .append(" E/").append(tag).append(": ").append(message));
        }

        if (logToLogcatToo) {
            Log.e(tag, message);
        }
    }

    /**
     * Similar to {@link Log#e(String, String, Throwable)}, but to log to {@link System#out}, or while compiling,
     * * /Internal storage/.sketchware/debug.txt
     */
    public static void e(String tag, String message, Throwable throwable) {
        if (loggingEnabled) {
            System.out.println(new StringBuilder(getDateAndTime(System.currentTimeMillis()))
                    .append(" E/").append(tag).append(": ").append(message).append('\n')
                    .append(Log.getStackTraceString(throwable)));
        }

        if (logToLogcatToo) {
            Log.e(tag, message, throwable);
        }
    }

    private static String getDateAndTime(long millis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        return simpleDateFormat.format(new Date(millis));
    }

    public static void log(String tag, ArrayList<String> list) {
        String toLog = list.toString();
        if (toLog.length() > 1023) {
            ArrayList<String> loggableStrings = new ArrayList<>();
            int beginIndex = 0;
            while (beginIndex < (toLog.length() - (toLog.length() % 1024))) {
                loggableStrings.add(toLog.substring(beginIndex, beginIndex + 1024));
                beginIndex += 1024;
            }
            loggableStrings.add(toLog.substring(beginIndex));

            for (String loggableString : loggableStrings) {
                Log.d(tag, loggableString);
            }
        } else {
            Log.d(tag, list.toString());
        }
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

    public static void log(String tag, String prefixIfOneLiner, String logMessageIfMultipleLines, String toLog) {
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
            Log.d(tag, prefixIfOneLiner + toLog);
        }
    }

    public static void dump(String tag, Object obj) {
        StringBuilder toLog = new StringBuilder();
        Field[] fields = obj.getClass().getDeclaredFields();
        int index = 0;
        while (index < fields.length) {
            Field current = fields[index];
            if (index == 0) {
                toLog.append(current.getName()).append("=");
                try {
                    Object fieldAsObject = current.get(obj);
                    if (fieldAsObject == null) {
                        toLog.append("null");
                    } else {
                        toLog.append(fieldAsObject);
                    }
                } catch (IllegalAccessException e) {
                    toLog.append("???");
                }
            }
            index++;
        }

        log(tag,
                "Dumping a L" + obj.getClass().getCanonicalName().replace(".", "/") + ": ",
                "Dumping a L" + obj.getClass().getCanonicalName().replace(".", "/") + " over multiple lines because of message length: ",
                toLog.toString());
    }

    /**
     * Returns the representation of an Object, revealing (most) of its fields' values, as String.
     * For example, a class having two String fields getting dumped would look like this:
     * <pre>
     *     class ClassWithFields {
     *         public String aField = "defVal1";
     *         public String anotherField = "defVal2";
     *     }
     * </pre>And the returned dumped version of an Instance of {@code ClassWithFields}:
     * <p>
     * <pre>aField="defVal1", anotherField="defVal2"</pre>
     *
     * @param object The Object to dump
     * @return A String representation of the object
     */
    public static String dump(Object object) {
        StringBuilder dump = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        int index = 0;
        while (index < fields.length) {
            Field current = fields[index];
            if (index != 0 && index != (fields.length - 1)) {
                dump.append(", ");
            }
            dump
                    .append(current.getName())
                    .append("=");
            current.setAccessible(true);
            try {
                Object fieldAsObject = current.get(object);
                if (fieldAsObject == null) {
                    dump.append("null");
                } else {
                    if (fieldAsObject instanceof String) {
                        dump
                                .append("\"")
                                .append((String) fieldAsObject)
                                .append("\"");
                    } else if (fieldAsObject instanceof Boolean) {
                        dump.append(fieldAsObject);
                    } else if (fieldAsObject instanceof Byte) {
                        dump.append(fieldAsObject);
                    } else if (fieldAsObject instanceof Character) {
                        dump.append(fieldAsObject);
                    } else if (fieldAsObject instanceof Double) {
                        dump.append(fieldAsObject);
                    } else if (fieldAsObject instanceof Integer) {
                        dump.append(fieldAsObject);
                    } else if (fieldAsObject instanceof Long) {
                        dump.append(fieldAsObject);
                    } else if (fieldAsObject instanceof Short) {
                        dump.append(fieldAsObject);
                    } else {
                        dump.append(fieldAsObject);
                    }
                }
            } catch (IllegalAccessException e) {
                dump.append("???");
            }
            index++;
        }
        return dump.toString();
    }
}
