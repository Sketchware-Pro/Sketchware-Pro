package mod.hey.studios.compiler.kotlin;

import android.app.Activity;

import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.export.ExportProjectActivity;

import a.a.a.Dp;
import a.a.a.Kp;
import a.a.a.yq;

public class KotlinCompilerBridge {

    public static void compileKotlinCodeIfPossible(DesignActivity.BuildAsyncTask task, Dp dp) throws Throwable {
    }

    public static void compileKotlinCodeIfPossible(ExportProjectActivity.BuildingAsyncTask task, Dp dp) throws Throwable {
    }

    public static void maybeAddKotlinBuiltInLibraryDependenciesIfPossible(Dp dp, Kp builtInLibraryManager) {
    }

    public static void maybeAddKotlinFilesToClasspath(StringBuilder classpath, yq workspace) {
    }

    public static String getKotlinHome(yq workspace) {
        return null;
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
        return true;
    }
}
