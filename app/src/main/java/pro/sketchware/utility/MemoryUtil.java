package pro.sketchware.utility;

import android.app.ActivityManager;
import android.content.Context;

public class MemoryUtil {
    public static long getAvailableRAMInMB(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        // Convert to megabytes
        return memoryInfo.availMem / (1024 * 1024);
    }
}
