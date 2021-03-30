package mod.hosni.fraj.compilerlog;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class CompileLogSaver {

    public String sc_id;

    public CompileLogSaver(String sc_id) {
        this.sc_id = sc_id;
    }

    public void clearLastLog() {
        FileUtil.deleteFile(getLastLogPath());
    }

    public String getLastLog() {
        String readFile = FileUtil.readFile(getLastLogPath());
        if (readFile.equals("")) {
            readFile = "No errors have occurred during the last build.";
        }
        return readFile;
    }

    public void setLastLog(String log) {
        FileUtil.writeFile(getLastLogPath(), log);
    }

    private String getLastLogPath() {
        return FilePathUtil.getLastCompileLogPath(sc_id);
    }
}