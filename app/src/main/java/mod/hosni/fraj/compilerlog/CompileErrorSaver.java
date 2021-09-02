package mod.hosni.fraj.compilerlog;

import static mod.SketchwareUtil.getDip;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.CompileLogHelper;

public class CompileErrorSaver {

    private static final String MESSAGE_NO_COMPILE_ERRORS_SAVED = "No compile errors have been saved yet.";

    public String PROJECT_ID;
    public FilePathUtil filePathUtil = new FilePathUtil();
    public String log_path;

    /**
     * Create this helper class for saving compile errors.
     *
     * @param sc_id The Sketchware project ID for the project to operate on, like 605
     */
    public CompileErrorSaver(String sc_id) {
        this.PROJECT_ID = sc_id;
        log_path = FilePathUtil.getLastCompileLogPath(sc_id);
        check();
    }

    /**
     * Save a compile error in the project's last compile error file.
     *
     * @param errorText The text to save, if possible, with detailed messages
     */
    public void setErrorText(String errorText) {
        FileUtil.deleteFile(log_path);
        FileUtil.writeFile(log_path, errorText);
    }

    /**
     * Show an {@link AlertDialog} that displays the last saved error to the user.
     *
     * @param context The context to show the dialog on
     */
    public void showDialog(Context context) {
        ScrollView scrollView = new ScrollView(context);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        TextView errorLogTxt = new TextView(context);
        errorLogTxt.setText(CompileLogHelper.colorErrsAndWarnings(getLog()));
        errorLogTxt.setTextSize(12);
        errorLogTxt.setTextIsSelectable(true);
        errorLogTxt.setTypeface(Typeface.MONOSPACE);
        horizontalScrollView.addView(errorLogTxt);
        scrollView.addView(horizontalScrollView);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Last compile log")
                .setPositiveButton(Resources.string.common_word_ok, null)
                .setNegativeButton("Clear", (dialogInterface, which) -> {
                    clear();
                    SketchwareUtil.toast("Cleared log");
                })
                .create();

        dialog.setView(scrollView,
                (int) getDip(24),
                (int) getDip(8),
                (int) getDip(24),
                (int) getDip(8));

        dialog.show();

        if (errorLogTxt.getText().toString().equals(MESSAGE_NO_COMPILE_ERRORS_SAVED)) {
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setVisibility(View.GONE);
        }
    }

    /**
     * Clear the last saved error text.
     */
    public void clear() {
        FileUtil.deleteFile(log_path);
    }

    /**
     * @return The last saved error text
     */
    public String getLog() {
        return FileUtil.readFile(log_path);
    }

    /**
     * Check if the last saved error text file exists, if not, it'll get created and
     * {@link CompileErrorSaver#MESSAGE_NO_COMPILE_ERRORS_SAVED} will be written to it.
     */
    public void check() {
        if (!FileUtil.isExistFile(log_path)) {
            FileUtil.writeFile(log_path, MESSAGE_NO_COMPILE_ERRORS_SAVED);
        }
    }
}
