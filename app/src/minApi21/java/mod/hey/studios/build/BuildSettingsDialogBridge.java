package mod.hey.studios.build;

import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_7;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_8;
import static mod.hey.studios.build.BuildSettings.SETTING_SHRINKER_PROGUARD;

import android.os.Build;
import android.widget.Toast;

import mod.SketchwareUtil;

public class BuildSettingsDialogBridge {
    public static String[] getAvailableJavaVersions() {
        return new String[] {
                SETTING_JAVA_VERSION_1_7
        };
    }
    
    public static String[] getAvailableShrinkers() {
        return new String[] {
            SETTING_SHRINKER_PROGUARD
        };
    }

    public static void handleJavaVersionChange(String choice) {
        if (choice.equals(SETTING_JAVA_VERSION_1_8)) {
            SketchwareUtil.toast("Don't forget to enable D8 to be able to compile Java 8 code");
        }
    }

    public static void handleDexerChange(String choice) {
        if (choice.equals(BuildSettings.SETTING_DEXER_D8) && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            SketchwareUtil.toast("Your Android version isn't compatible with D8 (requires Android 8+).\nIf you proceed to use it, compilation will fail",
                    Toast.LENGTH_LONG);
        }
    }
}
