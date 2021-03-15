package mod.jbk.util;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class LogUtil {

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
            Log.d(tag, prefixIfOneLiner + list.toString());
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
                        toLog.append(fieldAsObject.toString());
                    }
                } catch (IllegalAccessException e) {
                    toLog.append("???");
                }
            }
            index++;
        }

        log(tag, "Dumping a " + obj.getClass().getCanonicalName() + ": ",
                "Dumping a " + obj.getClass().getCanonicalName() + " over multiple lines because of message length: ",
                toLog.toString());
    }
}
