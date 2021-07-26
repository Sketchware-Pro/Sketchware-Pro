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
                        dump.append((Boolean) fieldAsObject);
                    } else if (fieldAsObject instanceof Byte) {
                        dump.append((Byte) fieldAsObject);
                    } else if (fieldAsObject instanceof Character) {
                        dump.append((Character) fieldAsObject);
                    } else if (fieldAsObject instanceof Double) {
                        dump.append((Double) fieldAsObject);
                    } else if (fieldAsObject instanceof Integer) {
                        dump.append((Integer) fieldAsObject);
                    } else if (fieldAsObject instanceof Long) {
                        dump.append((Long) fieldAsObject);
                    } else if (fieldAsObject instanceof Short) {
                        dump.append((Short) fieldAsObject);
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
