package mod.hosni.fraj.compilerlog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.sketchware.remod.Resources;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.CompileLogHelper;

public class CompileErrorSaver {

    public String sc_id;
    public Activity activity;
    public FilePathUtil filePathUtil = new FilePathUtil();
    public String path;

    public CompileErrorSaver(Activity activity, String sc_id) {
        this.sc_id = sc_id;
        this.activity = activity;
        path = FilePathUtil.getLastCompileLogPath(sc_id);
        check();
    }

    public void setErrorText(String errorText) {
        FileUtil.deleteFile(path);
        FileUtil.writeFile(path, errorText);
    }

    public void show() {
        AlertDialog dialog = new AlertDialog.Builder(activity)
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

    public void clear() {
        FileUtil.deleteFile(path);
    }

    public String getLog() {
        return FileUtil.readFile(path);
    }

    public void check() {
        if (!FileUtil.isExistFile(path)) {
            FileUtil.writeFile(path, "No compile errors have been saved yet.");
        }
    }
}
