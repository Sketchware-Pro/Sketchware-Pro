package mod.hosni.fraj.compilerlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.CompileLogHelper;

public class CompileErrorSaver {

    public String sc_id;
    public FilePathUtil filePathUtil = new FilePathUtil();
    public String path;

    /**
     * Create this helper class for saving compile errors.
     *
     * @param sc_id The Sketchware project ID for the project to operate on, like 605
     */
    public CompileErrorSaver(String sc_id) {
        this.sc_id = sc_id;
        path = FilePathUtil.getLastCompileLogPath(sc_id);
        check();
    }

    /**
     * Save a compile error in the project's last compile error file.
     *
     * @param errorText The text to save, if possible, with detailed messages
     */
    public void setErrorText(String errorText) {
        FileUtil.deleteFile(path);
        FileUtil.writeFile(path, errorText);
    }

    /**
     * Show an {@link AlertDialog} that displays the last saved error to the user.
     *
     * @param context The context to show the dialog on
     */

    public void showDialog(Context context) {

        TextView errorLogTxt = new TextView(context);
        errorLogTxt.setText(CompileLogHelper.colorErrsAndWarnings(getLog()));
        errorLogTxt.setTextIsSelectable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(errorLogTxt)
                .setTitle("Last compile log")
                .setPositiveButton(Resources.string.common_word_ok, null)

                .setNegativeButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SketchwareUtil.toast("Cleared log");
                        clear();
                    }
                })

                .create()
                .show();

        // if (errorLogTxt.toString().contains("No compile errors")) {
        //   builder.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        // }
    }
    /*
    *old method in case something went wrong

    public void showDialog(Context context) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Last compile log")
                .setMessage(CompileLogHelper.colorErrsAndWarnings(getLog()))
                .setPositiveButton(Resources.string.common_word_ok, null)
                .setNegativeButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SketchwareUtil.toast("Cleared log");
                        clear();
                    }
                })
                .create();
        dialog.show();
    }
    */


    /**
     * Clear the last saved error text.
     */
    public void clear() {
        FileUtil.deleteFile(path);
    }

    /**
     * @return The last saved error text
     */
    public String getLog() {
        return FileUtil.readFile(path);
    }

    /**
     * Check if the last saved error text file exists, if not, it'll get created and
     * "No compile errors have been saved yet." will be written to it.
     */
    public void check() {
        if (!FileUtil.isExistFile(path)) {
            FileUtil.writeFile(path, "No compile errors have been saved yet.");
        }
    }
}
