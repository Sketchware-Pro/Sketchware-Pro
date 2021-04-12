package mod.hosni.fraj.compilerlog;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class CompileLogSaver {

    public static String sc_id;
    public static FileUtil fileUtil;

    public CompileLogSaver(String sc_id) {
        this.sc_id = sc_id;
    }

    public static void clearLastLog() {
        FileUtil.deleteFile(getLastLogPath());
    }

    public static String getLastLog() {
        String readFile = FileUtil.readFile(getLastLogPath());
        if (readFile.equals("")) {
            readFile = "No errors have occurred during the last build.";
        }
        return readFile;
    }

    public static void setLastLog(String log) {
        FileUtil.writeFile(getLastLogPath(), log);
    }

    private static String getLastLogPath() {
        return FilePathUtil.getLastCompileLogPath(sc_id);
    }
}
