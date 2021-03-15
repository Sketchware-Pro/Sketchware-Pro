package mod.agus.jcoderz.lib;

import android.os.Environment;
import java.io.File;

public class FilePathUtil {
    public File file;

    public String getPathPermission(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/permission")));
        return file.getAbsolutePath();
    }

    public String getPathImport(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/import")));
        return file.getAbsolutePath();
    }

    public String getPathBroadcast(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/broadcast")));
        return file.getAbsolutePath();
    }

    public String getPathService(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/service")));
        return file.getAbsolutePath();
    }

    public String getPathAssets(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/assets")));
        return file.getAbsolutePath();
    }

    public String getPathJava(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/java")));
        return file.getAbsolutePath();
    }

    public String getPathResource(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/resource")));
        return file.getAbsolutePath();
    }

    public String getPathLocalLibrary(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/local_library")));
        return file.getAbsolutePath();
    }

    public String getJarPathLocalLibrary(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/libs/local_libs/".concat(str.concat("/classes.jar")));
        return file.getAbsolutePath();
    }

    public String getDexPathLocalLibrary(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/libs/local_libs/".concat(str.concat("/classes.dex")));
        return file.getAbsolutePath();
    }

    public String getResPathLocalLibrary(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/libs/local_libs/".concat(str.concat("/res")));
        return file.getAbsolutePath();
    }

    public String getJarPathLocalLibraryUser(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/library/jar")));
        return file.getAbsolutePath();
    }

    public String getDexPathLocalLibraryUser(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/library/dex")));
        return file.getAbsolutePath();
    }

    public String getResPathLocalLibraryUser(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/library/res")));
        return file.getAbsolutePath();
    }

    public String getManifestJava(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/java")));
        return file.getAbsolutePath();
    }

    public String getManifestBroadcast(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/broadcast")));
        return file.getAbsolutePath();
    }

    public String getPathNativelibs(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/files/native_libs")));
        return file.getAbsolutePath();
    }

    public String getManifestService(String str) {
        file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(str.concat("/service")));
        return file.getAbsolutePath();
    }
}