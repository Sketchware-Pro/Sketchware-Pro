package mod.jbk.build.compiler.dex;

import android.annotation.TargetApi;
import android.os.Build;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;

import a.a.a.Dp;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.ProjectSettings;

public class DexCompiler {

    @TargetApi(Build.VERSION_CODES.O)
    public static void compileDexFiles(Dp compileHelper) throws CompilationFailedException {
        int minApiLevel;

        try {
            minApiLevel = Integer.parseInt(compileHelper.settings.getValue(
                    ProjectSettings.SETTING_MINIMUM_SDK_VERSION, "21"));
        } catch (NumberFormatException e) {
            throw new CompilationFailedException("Invalid minSdkVersion specified in Project Settings", e);
        }

        if (!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
            throw new IllegalStateException("Can't use d8 as API level " + Build.VERSION.SDK_INT + " < 26");
        }

        Collection<Path> programFiles = new LinkedList<>();
        if (compileHelper.proguard.isProguardEnabled()) {
            programFiles.add(Paths.get(compileHelper.yq.classesProGuardPath));
        } else {
            for (File file : FileUtil.listFilesRecursively(new File(compileHelper.yq.compiledClassesPath), ".class")) {
                programFiles.add(file.toPath());
            }
        }

        Collection<Path> libraryFiles = new LinkedList<>();
        for (String jarPath : compileHelper.getClasspath().split(":")) {
            libraryFiles.add(Paths.get(jarPath));
        }

        D8.run(D8Command.builder()
                .setMode(CompilationMode.RELEASE)
                .setIntermediate(true)
                .setMinApiLevel(minApiLevel)
                .addLibraryFiles(libraryFiles)
                .setOutput(new File(compileHelper.yq.binDirectoryPath, "dex").toPath(), OutputMode.DexIndexed)
                .addProgramFiles(programFiles)
                .build());
    }
}
