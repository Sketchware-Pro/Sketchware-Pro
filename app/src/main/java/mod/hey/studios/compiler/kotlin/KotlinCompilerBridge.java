package mod.hey.studios.compiler.kotlin;

import java.io.File;

import a.a.a.BuiltInLibraryManager;
import a.a.a.ProjectBuilder;
import a.a.a.yq;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;
import pro.sketchware.utility.FileUtil;

public class KotlinCompilerBridge {
    public static void compileKotlinCodeIfPossible(BuildProgressReceiver receiver, ProjectBuilder builder) throws Throwable {
        if (KotlinCompilerUtil.areAnyKtFilesPresent(builder)) {
            receiver.onProgress("Kotlin is compiling...", 12);
            new KotlinCompiler(builder).compile();
        }
    }

    public static void maybeAddKotlinBuiltInLibraryDependenciesIfPossible(ProjectBuilder builder, BuiltInLibraryManager builtInLibraryManager) {
        if (KotlinCompilerUtil.areAnyKtFilesPresent(builder)) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.KOTLIN_STDLIB);
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
