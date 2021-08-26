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
import java.util.ArrayList;
import java.util.Collection;

import a.a.a.Dp;
import mod.agus.jcoderz.command.ProcessingFiles;
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

        assert Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
        Collection<Path> programFiles = new ArrayList<>();
        if (compileHelper.proguard.isProguardEnabled()) {
            programFiles.add(new File(compileHelper.f.classes_proguard).toPath());
        } else {
            for (String filePath : ProcessingFiles.getListResource(compileHelper.f.u)) {
                programFiles.add(new File(filePath).toPath());
            }
        }

        D8.run(D8Command.builder()
                .setMode(CompilationMode.RELEASE)
                .setIntermediate(true)
                .setMinApiLevel(minApiLevel)
                .addLibraryFiles(new File(compileHelper.o).toPath())
                .setOutput(new File(compileHelper.f.t, "dex").toPath(), OutputMode.DexIndexed)
                .addProgramFiles(programFiles)
                .build());
    }
}
