package extensions.anbui.sketchware.file;

import android.os.Environment;

import java.io.File;

public class FileUtils {

    //Get the internal storage directory.
    public static String getInternalStorageDir() {
        File storageDir = Environment.getExternalStorageDirectory();
        return storageDir.getAbsolutePath();
    }
}
