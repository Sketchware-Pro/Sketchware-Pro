package mod.hey.studios.build;

import android.os.Environment;

import com.android.tools.r8.w.S;

import java.io.File;

import mod.hey.studios.project.ProjectSettings;

public class BuildSettings extends ProjectSettings {

    public static final String SETTING_ANDROID_JAR_PATH = "android_jar";
    public static final String SETTING_CLASSPATH = "classpath";
    public static final String SETTING_DEXER = "dexer";
    public static final String SETTING_DEX_SHRINKER = "dex_shrinker";
    public static final String SETTING_RESOURCE_PROCESSOR = "resource_processor";
    public static final String SETTING_OUTPUT_FORMAT = "output_format";
    public static final String SETTING_JAVA_VERSION = "java_ver";
    public static final String SETTING_NO_HTTP_LEGACY = "no_http_legacy";
    public static final String SETTING_NO_WARNINGS = "no_warn";
    public static final String SETTING_BUILD_INCREMENTAL ="build_incremental";

    public static final String SETTING_DEXER_D8 = "D8";
    public static final String SETTING_DEXER_DX = "Dx";
    public static final String SETTING_JAVA_VERSION_1_7 = "1.7";
    public static final String SETTING_JAVA_VERSION_1_8 = "1.8";
    public static final String SETTING_RESOURCE_PROCESSOR_AAPT = "AAPT";
    public static final String SETTING_RESOURCE_PROCESSOR_AAPT2 = "AAPT2";
    public static final String SETTING_DEX_SHRINKER_PROGUARD = "ProGuard";
    public static final String SETTING_DEX_SHRINKER_R8 = "R8";
    public static final String SETTING_OUTPUT_FORMAT_AAB = "AAB";
    public static final String SETTING_OUTPUT_FORMAT_APK = "APK";

    public BuildSettings(String sc_id) {
        super(sc_id);
    }

    @Override
    public String getPath() {
        return (new File(Environment.getExternalStorageDirectory(), ".sketchware/data/".concat(super.sc_id.concat("/build_config")))).getAbsolutePath();
    }
}
