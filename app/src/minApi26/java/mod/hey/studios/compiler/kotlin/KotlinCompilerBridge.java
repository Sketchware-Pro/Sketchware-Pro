package mod.hey.studios.compiler.kotlin;

import android.app.Activity;
import android.os.Build;

import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.export.ExportProjectActivity;
import com.sketchware.remod.R;

import java.io.File;

import a.a.a.Dp;
import a.a.a.Kp;
import a.a.a.aB;
import a.a.a.xB;
import a.a.a.yq;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
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
        if (FileUtil.isExistFile(workspace.u)) {
            classpath.append(workspace.u);
            classpath.append(":");
        }
    }

    public static String getKotlinHome(yq workspace) {
        return workspace.t + File.separator + "kotlin_home";
    }

    /**
     * Checks if the device supports compiling Kotlin code. If not, a warning dialog telling the user
     * to remove Kotlin source files from the project gets shown.
     *
     * @return <code>true</code> if flavor allows compiling Kotlin code and device is on Android 8 or above,
     * or the flavor doesn't allow compiling Kotlin code. <code>false</code> if not on Android 8 or above
     * but with Kotlin compilation available.
     */
    public static boolean maybeCheckIfDeviceSupportsKotlinc(Activity context, yq workspace) {
                             //     please learn to write beautiful code!
        if (Build.VERSION.SDK_INT >= 26 && KotlinCompilerUtil.areAnyKtFilesPresent(workspace)) {
            aB dialog = new aB(context);
            dialog.a(R.drawable.high_priority_96_red);
            dialog.b("Warning");
            dialog.a("It would seem that you have some Kotlin files in your project but your " +
                    "Android version isn't compatible with kotlinc (which only works on devices " +
                    "with Android 8 and higher)." +
                    "\n\n" +
                    "Please delete those to proceed with the compilation.");
            dialog.b(xB.b().a(context.getApplicationContext(), R.string.common_word_ok),
                    Helper.getDialogDismissListener(dialog));
            dialog.show();

            return false;
        }

        return true;
    }
}
