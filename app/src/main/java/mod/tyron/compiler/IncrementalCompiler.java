package mod.tyron.compiler;

import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import com.android.sdklib.build.ApkBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import a.a.a.yq;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FileUtil;

public class IncrementalCompiler {

    public static final String TAG = IncrementalCompiler.class.getSimpleName();

    private final IncrementalJavaCompiler javaCompiler;
    private final IncrementalD8Compiler d8Compiler;
    private final IncrementalDexMerger dexMerger;

    private final yq projectConfig;
    private final ManageLocalLibrary mll;

    private Compiler.Result resultListener;

    public IncrementalCompiler(yq projectConfig) {
        this.projectConfig = projectConfig;
        this.mll = new ManageLocalLibrary(projectConfig.b);
        javaCompiler = new IncrementalJavaCompiler(projectConfig);
        d8Compiler = new IncrementalD8Compiler(projectConfig);
        dexMerger = new IncrementalDexMerger(projectConfig, javaCompiler.getBuiltInLibraries());

        dexMerger.setOnResultListener(new Compiler.Result() {
            @Override
            public void onResult(boolean success, int compileType, Object... args) {
                if (success) {
                    String message = "Dex merge successful, building APK.";
                    Log.d(TAG, message);
                    resultListener.onResult(true, Compiler.TYPE_MERGE, message);
                    //we are sure that the second argument is a list of string
                    //noinspection unchecked
                    buildApk((List<String>) args[1]);
                } else {
                    Log.e(TAG, "Failed to merge dexes, reason: " + args[0]);
                    resultListener.onResult(false, compileType, args);
                }
            }
        });
    }

    public void setResultListener(Compiler.Result resultListener) {
        this.resultListener = resultListener;
    }

    public void performCompilation() {

        if (resultListener == null) {
            throw new IllegalAccessError("No result listener were set");
        }
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Unable to perform compilation on the main thread, call this function on a background thread");
        }

        compileJava();
    }

    private void compileJava() {
        javaCompiler.setOnResultListener(new Compiler.Result() {
            @Override
            public void onResult(boolean success, int compileType, Object... args) {
                if (success) {
                    compileD8();
                } else {
                    resultListener.onResult(false, compileType, args);
                }
            }
        });

        javaCompiler.compile();
    }

    private void compileD8() {
        d8Compiler.compile();
        mergeDexes();
    }

    private void mergeDexes() {
        dexMerger.compile();
    }

    private void buildApk(List<String> dexes) {

        Log.d(TAG, "Starting apk build.");

        ApkBuilder builder = new ApkBuilder(new File(projectConfig.G), new File(projectConfig.c), new File(projectConfig.E), null, null, System.out);
        for (HashMap<String, Object> localLibraries : mll.list) {
            String jarPath = String.valueOf(localLibraries.get("jarPath"));
            if (!jarPath.equals("null")) {
                builder.addResourcesFromJar(new File(jarPath));
            }
        }
        File file = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/".concat(projectConfig.b.concat("/files/native_libs")));
        if (FileUtil.isExistFile(file.getAbsolutePath())) {
            builder.addNativeLibraries(file);
        }
        for (String nativeLibrary : mll.getNativeLibs()) {
            builder.addNativeLibraries(new File(nativeLibrary));
        }
        for (String dex : dexes) {
            builder.addFile(new File(dex), Uri.parse(dex).getLastPathSegment());
        }
        builder.setDebugMode(false);
        builder.sealApk();

    }
}
