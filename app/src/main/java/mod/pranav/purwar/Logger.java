package mod.pranav.purwar;

import java.lang.StringBuilder;

public class Logger {

    static String logPath = "/storage/emulated/0/.sketchware/"+"logs.txt";
    public static void logExcept(String info, Exception e) {
        try {
            String except = info+e.toString();
            e.printStackTrace();
            FileUtil.writeFile(logPath, s);
        } catch(Exception exception) {
              e.printStackTrace();
        }
    }
}
