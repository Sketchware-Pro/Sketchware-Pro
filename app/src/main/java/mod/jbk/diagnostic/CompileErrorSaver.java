package mod.jbk.diagnostic;

import android.content.Context;
import android.content.Intent;

import com.besome.sketch.tools.CompileLogActivity;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class CompileErrorSaver {

    private static final String MESSAGE_NO_COMPILE_ERRORS_SAVED = "No compile errors have been saved yet.";

    private final String sc_id;
    private final String path;

    /**
     * Create this helper class for saving compile errors.
     *
     * @param sc_id The Sketchware project ID for the project to operate on, like 605
     */
    public CompileErrorSaver(String sc_id) {
        this.sc_id = sc_id;
        path = FilePathUtil.getLastCompileLogPath(sc_id);
    }

    /**
     * Save a compile error in the project's last compile error file.
     *
     * @param errorText The text to save, if possible, with detailed messages
     */
    public void writeLogsToFile(String errorText) {
        if (logFileExists()) FileUtil.deleteFile(path);
        FileUtil.writeFile(path, errorText);
    }

    /**
     * Opens {@link CompileLogActivity} and shows the user the last compile error.
     */
    public void showLastErrors(Context context) {
        Intent intent = new Intent(context, CompileLogActivity.class);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("showingLastError", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * Clear the last saved error text.
     */
    public void deleteSavedLogs() {
        FileUtil.deleteFile(path);
    }

    /**
     * @return The last saved error text
     */
    public String getLogsFromFile() {
        if (!logFileExists()) return MESSAGE_NO_COMPILE_ERRORS_SAVED;
        return FileUtil.readFile(path);
    }

    /**
     * Check if the last saved error text file exists.
     */
    public boolean logFileExists() {
        return FileUtil.isExistFile(path);
    }
}
