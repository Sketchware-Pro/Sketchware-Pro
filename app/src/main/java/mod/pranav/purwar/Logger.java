package mod.pranav.purwar;

import java.lang.StringBuilder;

public class Logger {

    static String logPath = "/storage/emulated/0/.sketchware/logs.txt";
    public static void log(String info) {
        try {
            FileUtil.writeFile(logPath, info);
        } catch(Exception e) {
              e.printStackTrace();
        }
    }
}
