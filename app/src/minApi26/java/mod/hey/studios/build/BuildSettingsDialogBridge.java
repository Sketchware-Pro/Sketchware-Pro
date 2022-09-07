package mod.hey.studios.build;

import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_10;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_11;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_7;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_8;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_9;

import mod.SketchwareUtil;

public class BuildSettingsDialogBridge {
    public static String[] getAvailableJavaVersions() {
        return new String[] {
                SETTING_JAVA_VERSION_1_7,
                SETTING_JAVA_VERSION_1_8,
                SETTING_JAVA_VERSION_1_9,
                SETTING_JAVA_VERSION_10,
                SETTING_JAVA_VERSION_11,
        };
    }

    public static void handleJavaVersionChange(String choice) {
        if (!choice.equals(SETTING_JAVA_VERSION_1_7)) {
            SketchwareUtil.toast("Don't forget to enable D8 to be able to compile Java 8+ code");
        }
    }

    public static void handleDexerChange(String choice) {
    }
}
