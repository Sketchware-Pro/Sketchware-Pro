package mod.hey.studios.compiler.kotlin;

import com.besome.sketch.design.DesignActivity;

import a.a.a.Dp;

public class KotlinCompilerBridge {

    public static void compileKotlinCodeIfPossible(DesignActivity.BuildAsyncTask task, Dp dp) throws Throwable {
        if (KotlinCompilerUtil.areAnyKtFilesPresent(dp)) {
            task.publicPublishProgress("Kotlin is compiling...");
            dp.compileKotlin();
        }
    }
}
