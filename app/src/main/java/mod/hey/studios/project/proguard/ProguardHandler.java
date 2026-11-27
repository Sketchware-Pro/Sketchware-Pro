package mod.hey.studios.project.proguard;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;

import a.a.a.ProjectBuilder;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.util.LogUtil;
import pro.sketchware.R;
import pro.sketchware.SketchApplication;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ProguardHandler {
    public static String ANDROID_PROGUARD_RULES_PATH = createAndroidRules();
    public static String DEFAULT_PROGUARD_RULES_PATH = "";
    private final String scId;
    private final String config_path;
    private final String fm_config_path;

    private static final String[] DEFAULT_SINGLE_LINE_RULES = {
            "-repackageclasses",
            "-ignorewarnings",
            "-dontwarn",
            "-dontnote",
            "-allowaccessmodification",
            "-keepattributes Signature,InnerClasses,EnclosingMethod,Exceptions,*Annotation*"
    };

    public ProguardHandler(String sc_id) {
        scId = sc_id;
        DEFAULT_PROGUARD_RULES_PATH = createDefaultRules(sc_id);
        config_path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/proguard";
        fm_config_path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/proguard_fm";

        if (!FileUtil.isExistFile(config_path)) {
            FileUtil.writeFile(config_path, getDefaultConfig());
        } else if (isShrinkingEnabled()) {
            ensureDefaultRules();
        }
    }

    private static String createAndroidRules() {
        String rulePath = FileUtil.getExternalStorageDir() + "/.sketchware/libs/android-proguard-rules.pro";

        if (!FileUtil.isExistFile(rulePath)) {
            FileUtil.writeFile(rulePath, """
                    -dontusemixedcaseclassnames
                    -dontskipnonpubliclibraryclasses
                    -verbose
                    
                    
                    -keepattributes *Annotation*
                    -keep public class com.google.vending.licensing.ILicensingService
                    -keep public class com.android.vending.licensing.ILicensingService
                    
                    -keepclasseswithmembernames class * {
                        native <methods>;
                    }
                    
                    -keepclassmembers public class * extends android.view.View {
                       void set*(***);
                       *** get*();
                    }
                    
                    -keepclassmembers class * extends android.app.Activity {
                       public void *(android.view.View);
                    }
                    
                    -keepclassmembers enum * {
                        public static **[] values();
                        public static ** valueOf(java.lang.String);
                    }
                    
                    -keepclassmembers class * implements android.os.Parcelable {
                      public static final android.os.Parcelable$Creator CREATOR;
                    }
                    
                    -keepclassmembers class **.R$* {
                        public static <fields>;
                    }
                    
                    -dontwarn android.support.**
                    
                    -keep class android.support.annotation.Keep
                    
                    -keep @android.support.annotation.Keep class * {*;}
                    
                    -keepclasseswithmembers class * {
                        @android.support.annotation.Keep <methods>;
                    }
                    
                    -keepclasseswithmembers class * {
                        @android.support.annotation.Keep <fields>;
                    }
                    
                    -keepclasseswithmembers class * {
                        @android.support.annotation.Keep <init>(...);
                    }
                    
                    -keepclassmembers class * {
                        @android.webkit.JavascriptInterface <methods>;\
                    }
                    
                    -dontwarn android.arch.**
                    -dontwarn android.lifecycle.**
                    
                    -dontwarn androidx.arch.**
                    -dontwarn androidx.lifecycle.**
                    """);
        }

        return rulePath;
    }

    private static String createDefaultRules(String sc_id) {
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + sc_id + "/proguard-rules.pro";

        if (!FileUtil.isExistFile(path)) {
            FileUtil.writeFile(path, """
                    -repackageclasses
                    -ignorewarnings
                    -dontwarn
                    -dontnote
                    -allowaccessmodification
                    
                    -keepattributes Signature,InnerClasses,EnclosingMethod,Exceptions,*Annotation*
                    
                    -keepclassmembers class * {
                        @com.google.gson.annotations.SerializedName <fields>;
                    }
                    """);
        }

        return path;
    }

    private String getDefaultConfig() {
        HashMap<String, String> defaultConfig = new HashMap<>();

        defaultConfig.put("enabled", "false");
        defaultConfig.put("r8", "false");
        defaultConfig.put("shrink", "true");
        defaultConfig.put("obfuscate", "true");
        defaultConfig.put("optimize", "false");
        defaultConfig.put("debug", "false");

        return new Gson().toJson(defaultConfig);
    }

    public String getCustomProguardRules() {
        return DEFAULT_PROGUARD_RULES_PATH;
    }

    /**
     * Adds the default rules new projects start with to a project's rules file if they're missing,
     * so old projects show and use the same rules. Existing rules are never changed or removed.
     */
    private void ensureDefaultRules() {
        String path = DEFAULT_PROGUARD_RULES_PATH;
        if (!FileUtil.isExistFile(path)) {
            return;
        }

        String existing = FileUtil.readFile(path);

        Set<String> existingLines = new HashSet<>();
        for (String line : existing.split("\n")) {
            existingLines.add(line.trim());
        }

        StringBuilder missing = new StringBuilder();
        for (String rule : DEFAULT_SINGLE_LINE_RULES) {
            if (!existingLines.contains(rule)) {
                missing.append(rule).append("\n");
            }
        }
        if (!existing.contains("@com.google.gson.annotations.SerializedName")) {
            missing.append("-keepclassmembers class * {\n    @com.google.gson.annotations.SerializedName <fields>;\n}\n");
        }

        if (missing.length() > 0) {
            String separator = existing.isEmpty() ? "" : (existing.endsWith("\n") ? "\n" : "\n\n");
            FileUtil.writeFile(path, existing + separator + missing);
        }
    }
    /** Global build logging switch. */
    public boolean isDebugModeEnabled() {
        return isDebugFilesEnabled();
    }

    public void setDebugModeEnabled(boolean enabled) {
        setDebugEnabled(enabled);
    }

    public void setOptimizeEnabled(boolean enabled) {
        writeConfigBoolean("optimize", enabled);
    }

    /** Removes classes, methods and fields the app never uses. Missing key means enabled. */
    public boolean isShrinkEnabled() {
        return readConfigBoolean("shrink", true);
    }

    public void setShrinkEnabled(boolean enabled) {
        writeConfigBoolean("shrink", enabled);
    }

    /** Renames classes and members. Missing key means enabled. */
    public boolean isObfuscateEnabled() {
        return readConfigBoolean("obfuscate", true);
    }

    public void setObfuscateEnabled(boolean enabled) {
        writeConfigBoolean("obfuscate", enabled);
    }

    private boolean readConfigBoolean(String key, boolean defaultValue) {
        String value = readConfigOrEmpty().get(key);
        return value == null ? defaultValue : "true".equals(value);
    }

    private void writeConfigBoolean(String key, boolean enabled) {
        HashMap<String, String> config = readConfigOrEmpty();
        config.put(key, String.valueOf(enabled));
        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }

    public boolean isDebugFilesEnabled() {
        boolean debugFiles = true;
        if (FileUtil.isExistFile(config_path)) {
            try {
                HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), Helper.TYPE_STRING_MAP);

                if (!config.containsKey("debug")) return false;

                String debug = config.get("debug");
                if (debug != null) {
                    debugFiles = debug.equals("true");
                }

            } catch (Exception e) {
                debugFiles = false;
            }
        }

        return debugFiles;
    }

    /** Worker count for R8: all available cores, unless r8_threads overrides it. */
    public int getR8Threads() {
        int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
        String configured = readConfigOrEmpty().get("r8_threads");
        if (configured != null) {
            try {
                int threads = Integer.parseInt(configured.trim());
                if (threads >= 1 && threads <= cores) {
                    return threads;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return cores;
    }

    public boolean isShrinkingEnabled() {
        boolean proguardEnabled = true;
        if (FileUtil.isExistFile(config_path)) {
            try {
                HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), Helper.TYPE_STRING_MAP);

                String enabled = config.get("enabled");
                if (enabled == null) {
                    proguardEnabled = false;
                } else {
                    proguardEnabled = enabled.equals("true");
                }

            } catch (Exception e) {
                proguardEnabled = false;
            }
        }

        return proguardEnabled;
    }

    public void setProguardEnabled(boolean proguardEnabled) {
        HashMap<String, String> config = readConfigOrEmpty();
        config.put("enabled", String.valueOf(proguardEnabled));

        if (proguardEnabled) {
            /* Old projects get the default rules new projects get, the project's own settings are kept as they are */
            ensureDefaultRules();
        }

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }

    /**
     * Reads the project's ProGuard configuration, an empty or corrupt file gives an empty map instead of a NullPointerException.
     */
    private HashMap<String, String> readConfigOrEmpty() {
        try {
            HashMap<String, String> config = new Gson().fromJson(FileUtil.readFile(config_path), Helper.TYPE_STRING_MAP);
            if (config != null) {
                return config;
            }
        } catch (RuntimeException ignored) {
        }

        return new HashMap<>();
    }

    public boolean isR8Enabled() {
        boolean r8Enabled = true;
        if (FileUtil.isExistFile(config_path)) {
            try {
                var config = new Gson().fromJson(FileUtil.readFile(config_path), Helper.TYPE_STRING_MAP);

                String enabled = config.get("r8");
                if (enabled == null) {
                    r8Enabled = false;
                } else {
                    r8Enabled = enabled.equals("true");
                }

            } catch (Exception e) {
                r8Enabled = false;
            }
        }

        return r8Enabled;
    }

    public void setR8Enabled(boolean r8Enabled) {
        var config = readConfigOrEmpty();
        config.put("r8", String.valueOf(r8Enabled));

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }


    /**
     * Whether R8 may optimize the code (inlining, class merging, ...). Off by default, because it raises the risk of
     * runtime problems in existing apps. Enable per project by setting "optimize":"true" in the project's proguard config.
     */
    public boolean isOptimizeEnabled() {
        return readConfigBoolean("optimize", false);
    }

    public boolean libIsProguardFMEnabled(String library) {
        boolean enabled;
        if (isShrinkingEnabled() && isShrinkEnabled() && FileUtil.isExistFile(fm_config_path)) {
            String configContent = FileUtil.readFile(fm_config_path);

            if (configContent.isEmpty()) {
                return false;
            }

            try {
                ArrayList<String> config = new Gson().fromJson(configContent, Helper.TYPE_STRING);
                enabled = config.contains(library);
                return enabled;
            } catch (Exception ignored) {
            }
        }

        return false;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        HashMap<String, String> config = readConfigOrEmpty();
        config.put("debug", String.valueOf(debugEnabled));

        FileUtil.writeFile(config_path, new Gson().toJson(config));
    }

    public void setProguardFMLibs(ArrayList<String> fullModeLibs) {
        FileUtil.writeFile(fm_config_path, new Gson().toJson(fullModeLibs));
    }

    public ArrayList<String> getFullModeLibs() {
        if (FileUtil.isExistFile(fm_config_path)) {
            String content = FileUtil.readFile(fm_config_path);
            if (!content.isEmpty()) {
                try {
                    ArrayList<String> libs = new Gson().fromJson(content, Helper.TYPE_STRING);
                    if (libs != null) return libs;
                } catch (RuntimeException ignored) {
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * A -dont* rule in the project's rules file cannot be undone by a later config, so it wins over
     * the equivalent switch. Report the flags that contradict a switch that is currently on.
     */
    public ArrayList<String> getConflictingDontRules() {
        ArrayList<String> conflicts = new ArrayList<>();
        if (!FileUtil.isExistFile(DEFAULT_PROGUARD_RULES_PATH)) return conflicts;

        for (String line : FileUtil.readFile(DEFAULT_PROGUARD_RULES_PATH).split("\n")) {
            int comment = line.indexOf('#');
            String rule = (comment < 0 ? line : line.substring(0, comment)).trim();
            if (rule.equalsIgnoreCase("-dontshrink") && isShrinkEnabled()) {
                conflicts.add("-dontshrink");
            } else if (rule.equalsIgnoreCase("-dontobfuscate") && isObfuscateEnabled()) {
                conflicts.add("-dontobfuscate");
            } else if (rule.equalsIgnoreCase("-dontoptimize") && isOptimizeEnabled()) {
                conflicts.add("-dontoptimize");
            }
        }
        return conflicts;
    }

    public void start(BuildProgressReceiver progressReceiver, ProjectBuilder builder) throws IOException {
        if (!isShrinkingEnabled()) return;

        boolean r8 = isR8Enabled();
        String engine = r8 ? "R8" : "ProGuard";
        LogUtil.d("Shrinker", "Starting " + engine + " for project " + scId
                + " (shrink=" + isShrinkEnabled() + ", obfuscate=" + isObfuscateEnabled()
                + ", optimize=" + isOptimizeEnabled() + ", threads=" + getR8Threads() + ")");
        builder.logShrinkerTelemetry("shrinker_start");
        SketchApplication.setRunningShrinker(engine);
        try {
            if (r8) {
                progressReceiver.onProgress("Running R8 on classes...", 15);
                builder.runR8();
            } else {
                progressReceiver.onProgress("ProGuarding classes...", 16);
                builder.runProguard();
            }
            builder.logShrinkerTelemetry("shrinker_success");
        } catch (Throwable t) {
            builder.logShrinkerTelemetry("shrinker_failure");
            /* Bad rules are a user error and must still fail loudly; only device/resource
             * failures are skipped so the build can finish with plain D8 dexing. */
            if (!isDeviceFailure(t)) {
                if (t instanceof IOException io) throw io;
                if (t instanceof RuntimeException runtime) throw runtime;
                if (t instanceof Error error) throw error;
                throw new IOException(t);
            }
            LogUtil.e("Shrinker", "Starting D8 fallback after a device failure");
            builder.shrinkerSkipped = true;
            System.gc();
            String path = logFailure(engine, t);
            progressReceiver.onProgress(engine + " skipped, dexing with D8...", 17);
            SketchwareUtil.toast(SketchApplication.getContext().getString(
                    r8 ? R.string.shrinker_skipped_r8 : R.string.shrinker_skipped_proguard, path),
                    Toast.LENGTH_LONG);
        } finally {
            SketchApplication.setRunningShrinker(null);
        }
    }

    private static boolean isDeviceFailure(Throwable t) {
        for (int i = 0; t != null && i < 6; i++) {
            if (t instanceof VirtualMachineError || t instanceof RejectedExecutionException) return true;
            t = t.getCause();
        }
        return false;
    }

    private String logFailure(String engine, Throwable t) {
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + scId + "/shrinker_error.log";
        try {
            String detail;
            try {
                detail = Log.getStackTraceString(t);
            } catch (Throwable ignored) {
                detail = String.valueOf(t.getClass().getName());
            }
            FileUtil.writeFile(path, engine + " failed on this device\n" + detail);
        } catch (Throwable ignored) {
            path = FileUtil.getExternalStorageDir() + "/.sketchware/debug.txt";
        }
        return path;
    }
}
