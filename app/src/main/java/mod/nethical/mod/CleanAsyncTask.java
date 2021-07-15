package mod.nethical.mod;

import android.os.AsyncTask;

import a.a.a.yq;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class CleanAsyncTask extends AsyncTask<Void, Void, Void> {

    private final String myscFolder;

    public CleanAsyncTask(yq projectMetadata) {
        myscFolder = projectMetadata.c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        FileUtil.deleteFile(myscFolder);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        SketchwareUtil.toast("Done cleaning temporary files!");
    }
}
