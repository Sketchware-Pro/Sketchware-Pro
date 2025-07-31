package extensions.anbui.sketchware.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class FileUtils {

    //Get the internal storage directory.
    public static String getInternalStorageDir() {
        File storageDir = Environment.getExternalStorageDirectory();
        return storageDir.getAbsolutePath();
    }

    //Is file exist.
    public static boolean isFileExist(String path) {
        if (path == null || path.isEmpty()) return false;
        File file = new File(path);
        return file.exists();
    }

    //Get file path from uri.
    public static String getFilePathFromUri(Context context, Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {MediaStore.Files.FileColumns.DATA};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    filePath = cursor.getString(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }
}
