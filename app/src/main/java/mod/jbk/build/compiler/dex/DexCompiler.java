package mod.jbk.build.compiler.dex;

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

import a.a.a.ProjectBuilder;
import mod.hey.studios.project.ProjectSettings;
import pro.sketchware.utility.FileUtil;

public class DexCompiler {
    public static void compileDexFiles(ProjectBuilder builder) throws CompilationFailedException {
        int minApiLevel;

        try {
            minApiLevel = Integer.parseInt(builder.settings.getValue(
                    ProjectSettings.SETTING_MINIMUM_SDK_VERSION, "21"));
        } catch (NumberFormatException e) {
            throw new CompilationFailedException("Invalid minSdkVersion specified in Project Settings" + e.getMessage());
        }

        Collection<Path> programFiles = new LinkedList<>();
        if (builder.proguard.isShrinkingEnabled()) {
            programFiles.add(Paths.get(builder.yq.proguardClassesPath));
        } else {
            for (File file : FileUtil.listFilesRecursively(new File(builder.yq.compiledClassesPath), ".class")) {
                programFiles.add(file.toPath());
            }
        }

        Collection<Path> libraryFiles = new LinkedList<>();
        for (String jarPath : builder.getClasspath().split(":")) {
            libraryFiles.add(Paths.get(jarPath));
        }

        D8.run(D8Command.builder()
                .setMode(CompilationMode.RELEASE)
                .setIntermediate(true)
                .setMinApiLevel(minApiLevel)
                .addLibraryFiles(libraryFiles)
                .setOutput(new File(builder.yq.binDirectoryPath, "dex").toPath(), OutputMode.DexIndexed)
                .addProgramFiles(programFiles)
                .build());
    }
}
