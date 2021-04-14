package mod.agus.jcoderz.lib;

import android.os.Environment;
import java.io.File;

public class FilePathUtil {

    public String getPathPermission(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/permission"))).getAbsolutePath();
    }

    public String getPathImport(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/import"))).getAbsolutePath();
    }

    public String getPathBroadcast(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/broadcast"))).getAbsolutePath();
    }

    public String getPathService(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/service"))).getAbsolutePath();
    }

    public String getPathAssets(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/assets"))).getAbsolutePath();
    }

    public String getPathJava(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/java"))).getAbsolutePath();
    }

    public String getPathResource(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/resource"))).getAbsolutePath();
    }

    public String getPathLocalLibrary(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/local_library"))).getAbsolutePath();
    }

    public String getJarPathLocalLibrary(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/libs/local_libs/".concat(sc_id.concat("/classes.jar"))).getAbsolutePath();
    }

    public String getDexPathLocalLibrary(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/libs/local_libs/".concat(sc_id.concat("/classes.dex"))).getAbsolutePath();
    }

    public String getResPathLocalLibrary(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/libs/local_libs/".concat(sc_id.concat("/res"))).getAbsolutePath();
    }

    public String getJarPathLocalLibraryUser(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/library/jar"))).getAbsolutePath();
    }

    public String getDexPathLocalLibraryUser(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/library/dex"))).getAbsolutePath();
    }

    public String getResPathLocalLibraryUser(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/library/res"))).getAbsolutePath();
    }

    public static String getLastCompileLogPath(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/compile_log"))).getAbsolutePath();
    }

    public String getManifestJava(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/java"))).getAbsolutePath();
    }

    public String getManifestBroadcast(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/broadcast"))).getAbsolutePath();
    }

    public String getPathNativelibs(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/files/native_libs"))).getAbsolutePath();
    }

    public String getManifestService(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(sc_id.concat("/service"))).getAbsolutePath();
    }
}