package mod.tyron.compiler;

import android.os.AsyncTask;
import android.os.Looper;

import a.a.a.yq;
import a.a.a.zy;

public class IncrementalCompiler {

    private final IncrementalJavaCompiler javaCompiler;
    private final IncrementalD8Compiler d8Compiler;
    private final IncrementalDexMerger dexMerger;

    private final yq projectConfig;

    private Compiler.Result resultListener;

    public void setResultListener(Compiler.Result resultListener) {
        this.resultListener = resultListener;
    }

    public IncrementalCompiler(yq projectConfig) {
        this.projectConfig = projectConfig;

        javaCompiler = new IncrementalJavaCompiler(projectConfig);
        d8Compiler = new IncrementalD8Compiler(projectConfig);
        dexMerger = new IncrementalDexMerger(projectConfig, javaCompiler.getBuiltInLibraries());

    }

    public void performCompilation(){

        if (resultListener == null) {
            throw new IllegalAccessError("No result listener were set");
        }
        if(Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Unable to perform compilation on the main thread, call this function on a background thread");
        }

        compileJava();
    }

    private void compileJava() {
        javaCompiler.setOnResultListener(new Compiler.Result(){
            @Override
            public void onResult(boolean success, String message) {
                if (success) {
                    compileD8();
                } else {
                    resultListener.onResult(false, message);
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
        resultListener.onResult(true, "");
    }
}
