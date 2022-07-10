package mod.hey.studios.compiler.kotlin;

import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.export.ExportProjectActivity;

import java.io.File;

import a.a.a.Dp;
import a.a.a.Kp;
import a.a.a.yq;
import mod.agus.jcoderz.lib.FileUtil;
import mod.jbk.build.BuiltInLibraries;

public class KotlinCompilerBridge {

    public static void compileKotlinCodeIfPossible(DesignActivity.BuildAsyncTask task, Dp dp) throws Throwable {
        if (KotlinCompilerUtil.areAnyKtFilesPresent(dp)) {
            task.publicPublishProgress("Kotlin is compiling...");
            new KotlinCompiler(dp).compile();
        }
    }

    public static void compileKotlinCodeIfPossible(ExportProjectActivity.BuildingAsyncTask task, Dp dp) throws Throwable {
        if (KotlinCompilerUtil.areAnyKtFilesPresent(dp)) {
            task.publicPublishProgress("Kotlin is compiling...");
            new KotlinCompiler(dp).compile();
        }
    }

    public static void maybeAddKotlinBuiltInLibraryDependenciesIfPossible(Dp dp, Kp builtInLibraryManager) {
        if (KotlinCompilerUtil.areAnyKtFilesPresent(dp)) {
            builtInLibraryManager.a(BuiltInLibraries.KOTLIN_STDLIB);
        }
    }

    public static void maybeAddKotlinFilesToClasspath(StringBuilder classpath, yq workspace) {
        if (FileUtil.isExistFile(workspace.compiledClassesPath)) {
            classpath.append(workspace.compiledClassesPath);
            classpath.append(":");
        }
    }

    public static String getKotlinHome(yq workspace) {
        return workspace.binDirectoryPath + File.separator + "kotlin_home";
    }
}
