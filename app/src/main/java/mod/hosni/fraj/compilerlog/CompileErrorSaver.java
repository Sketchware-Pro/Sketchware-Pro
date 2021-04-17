package mod.hosni.fraj.compilerlog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import java.io.File;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.CompileLogHelper;
import mod.agus.jcoderz.lib.FilePathUtil;

import a.a.a.bB;

public class CompileErrorSaver {

	public String sc_id;
	public Activity activity;
	public FilePathUtil filePathUtil = new FilePathUtil();
        public String path;

	public CompileErrorSaver(Activity act, String str) {
		sc_id = str ;
		activity = act;
                path = filePathUtil.getPathErrorLog(str);
		check();
	}

	public CompileErrorSaver() {}

	public void setErrorText(String str) {
		FileUtil.deleteFile(path);
		FileUtil.writeFile(path, str);
	}

	public void show() {
		AlertDialog dialog = new AlertDialog.Builder(activity)
			.setTitle("Compile Log:")
			.setMessage(CompileLogHelper.colorErrsAndWarnings(getLog()))

			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dia, int which) {

				}
			})
            .setNeutralButton("Clear", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dia, int which) {
					bB.a(activity, "Cleared", 0).show();
					clear();
                }
			})
			.setNegativeButton("Cancel", null)
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
			FileUtil.writeFile(path, "No error detected in the last compile !");
		}
    }

}
