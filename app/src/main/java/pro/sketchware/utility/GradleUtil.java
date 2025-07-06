package pro.sketchware.utility;

import android.content.Context;

public class GradleUtil {

    public static Context context;
    public static String getjvmargs() {
        if (context == null) {
            return "";
        }
        long availableRAMInMB = MemoryUtil.getAvailableRAMInMB(context);
        if (availableRAMInMB >= 1024) {
            return "org.gradle.jvmargs=-Xmx" + (int) (availableRAMInMB / 1.5) + "m -Dfile.encoding=UTF-8 -XX:+UseParallelGC";
        }
        return "";
    }
}
