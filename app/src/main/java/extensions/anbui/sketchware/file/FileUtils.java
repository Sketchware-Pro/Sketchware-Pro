package extensions.anbui.sketchware.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
                System.err.println("Error getting file path: " + e.getMessage());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
    }

    public static String readTextFile(String path) {
        if (!isFileExist(path)) return "";

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return content.toString();
    }

    public static void writeTextFile(String path, String content) {

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
