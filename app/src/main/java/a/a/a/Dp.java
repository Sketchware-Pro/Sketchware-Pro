package a.a.a;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.widget.Toast;

import com.android.sdklib.build.ApkBuilder;
import com.besome.sketch.design.DesignActivity;
import com.github.megatronking.stringfog.plugin.StringFogClassInjector;
import com.github.megatronking.stringfog.plugin.StringFogMappingPrinter;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;
import mod.SketchwareUtil;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.FieldId;
import mod.agus.jcoderz.dex.MethodId;
import mod.agus.jcoderz.dex.ProtoId;
import mod.agus.jcoderz.dx.command.dexer.DxContext;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.dx.merge.CollisionPolicy;
import mod.agus.jcoderz.dx.merge.DexMerger;
import mod.agus.jcoderz.editor.library.ExtLibSelected;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.util.SystemLogPrinter;
import mod.jbk.build.compiler.dex.DexCompiler;
import mod.jbk.build.compiler.resource.ResourceCompiler;
import mod.jbk.util.LogUtil;
import proguard.Configuration;
import proguard.ConfigurationParser;
import proguard.ProGuard;

public class Dp {

    public static final String TAG = "AppBuilder";
    /**
     * Default minSdkVersion (?)
     */
    public final String a = "21";
    /**
     * Default targetSdkVersion (?)
     */
    public final String b = "28";
    public final String c = File.separator;
    public final String m = "libs";
    /**
     * Command(s) to execute after extracting AAPT2 (put the filename to index 2 before using)
     */
    private final String[] makeExecutableCommand = {"chmod", "700", ""};
    public File aapt2Dir;
    public BuildSettings build_settings;
    public DesignActivity.a buildingDialog;
    public Context e;
    public yq f;
    public FilePathUtil fpu;
    public oB g;
    /**
     * Directory "tmp" in files directory, where libs are extracted and compiled
     */
    public File h;
    public Fp j;
    /**
     * A StringBuffer with System.err of Eclipse compiler. If compilation succeeds, it doesn't have any content, if it doesn't, there is.
     */
    public StringBuffer k = new StringBuffer();
    /**
     * Extracted built-in libraries directory
     */
    public File l;
    public ManageLocalLibrary mll;
    public Kp n;
    /**
     * Path of the android.jar to use
     */
    public String o;
    public ProguardHandler proguard;
    public ProjectSettings settings;
    private boolean buildAppBundle = false;
    private ArrayList<String> dexesToAddButNotMerge = new ArrayList<>();

    /**
     * Timestamp keeping track of when compiling the project's resources started, needed for stats of how long compiling took.
     */
    private long timestampResourceCompilationStarted;

    public Dp(Context context, yq yqVar) {
        /* Detect some bad behaviour of the app */
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        );

        SystemLogPrinter.start();

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            LogUtil.d(TAG, "Running Sketchware Pro " + info.versionName + " (" + info.versionCode + ")");

            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);

            long fileSizeInBytes = new File(applicationInfo.sourceDir).length();
            LogUtil.d(TAG, "base.apk's size is " + Formatter.formatFileSize(context, fileSizeInBytes) + " (" + fileSizeInBytes + " B)");
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, "Somehow failed to get package info about us!", e);
        }

        e = context;
        f = yqVar;
        g = new oB(false);
        j = new Fp();
        h = new File(context.getFilesDir(), "tmp");
        if (!h.exists()) {
            if (!h.mkdir()) {
                throw new IllegalStateException("Couldn't create directory " + h.getAbsolutePath());
            }
        }
        aapt2Dir = new File(h, "aapt2");
        l = new File(context.getFilesDir(), "libs");
        n = new Kp();
        o = new File(l, "android.jar").getAbsolutePath();
        mll = new ManageLocalLibrary(f.b);
        fpu = new FilePathUtil();
        settings = new ProjectSettings(f.b);
        proguard = new ProguardHandler(f.b);
        build_settings = new BuildSettings(f.b);
        o = build_settings.getValue(BuildSettings.SETTING_ANDROID_JAR_PATH, o);
    }

    public Dp(DesignActivity.a anA, Context context, yq yqVar) {
        this(context, yqVar);
        buildingDialog = anA;
    }

    public Dp(Context context, yq yq, boolean buildAppBundle) {
        this(context, yq);
        this.buildAppBundle = buildAppBundle;
    }

    /**
     * Compile resources and log time needed.
     *
     * @throws Exception Thrown when anything goes wrong while compiling resources
     */
    public void a() throws Exception {
        timestampResourceCompilationStarted = System.currentTimeMillis();
        b();
        LogUtil.d(TAG, "Compiling resources took " + (System.currentTimeMillis() - timestampResourceCompilationStarted) + " ms");
    }

    public void a(iI iIVar, String str) {
        ZipSigner signer = iIVar.b(new CB().a(str));
        try {
            signer.signZip(f.G, f.I);
        } catch (IOException | GeneralSecurityException e) {
            LogUtil.e(TAG, "Failed to sign APK: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if a file on local storage differs from a file in assets, and if so,
     * replaces the file on local storage with the one in assets.
     * <p/>
     * The files' sizes are compared, not content.
     *
     * @param fileInAssets The file in assets relative to assets/ in the APK
     * @param targetFile   The file on local storage
     * @return If the file in assets has been extracted
     */
    public final boolean a(String fileInAssets, String targetFile) {
        long length;
        File compareToFile = new File(targetFile);
        long lengthOfFileInAssets = g.a(e, fileInAssets);
        if (compareToFile.exists()) {
            length = compareToFile.length();
        } else {
            length = 0;
        }
        if (lengthOfFileInAssets == length) {
            return false;
        }

        /* Delete the file */
        g.a(compareToFile);
        /* Copy the file from assets to local storage */
        g.a(e, fileInAssets, targetFile);
        return true;
    }

    /**
     * Compile the project's resources, with AAPT2 automatically.
     *
     * @throws Exception Thrown in case AAPT2 has an error while compiling resources.
     */
    public void b() throws Exception {
        ResourceCompiler compiler = new ResourceCompiler(
                this,
                aapt2Dir,
                buildAppBundle,
                buildingDialog);
        compiler.compile();
    }

    public void b(String password, String alias) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            CustomKeySigner.signZip(
                    new ZipSigner(),
                    wq.j(),
                    password.toCharArray(),
                    alias,
                    password.toCharArray(),
                    "SHA1WITHRSA",
                    f.G,
                    f.I
            );
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to sign APK: " + e.getMessage(), e);
        }
    }

    public boolean isD8Enabled() {
        return build_settings.getValue(
                BuildSettings.SETTING_DEXER,
                BuildSettings.SETTING_DEXER_DX
        ).equals(BuildSettings.SETTING_DEXER_D8);
    }

    public String getDxRunningText() {
        return (isD8Enabled() ? "D8" : "Dx") + " is running...";
    }

    /**
     * Compile Java classes into DEX file(s)
     *
     * @throws Exception Thrown if the compiler has any problems compiling
     */
    public void c() throws Exception {
        FileUtil.makeDir(f.t + File.separator + "dex");
        if (isD8Enabled()) {
            long savedTimeMillis = System.currentTimeMillis();
            try {
                DexCompiler.compileDexFiles(this);
                LogUtil.d(TAG, "D8 took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } catch (Exception e) {
                LogUtil.e(TAG, "D8 failed to process .class files", e);
                throw e;
            }
        } else {
            long savedTimeMillis = System.currentTimeMillis();
            List<String> args = Arrays.asList(
                    "--debug",
                    "--verbose",
                    "--multi-dex",
                    "--output=" + f.t + File.separator + "dex",
                    proguard.isProguardEnabled() ? f.classes_proguard : f.u
            );

            try {
                LogUtil.d(TAG, "Running Dx with these arguments: " + args);

                Main.clearInternTables();
                Main.Arguments arguments = new Main.Arguments();
                Method parseMethod = Main.Arguments.class.getDeclaredMethod("parse", String[].class);
                parseMethod.setAccessible(true);
                parseMethod.invoke(arguments, (Object) args.toArray(new String[0]));

                Main.run(arguments);
                LogUtil.d(TAG, "Dx took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } catch (Exception e) {
                LogUtil.e(TAG, "Dx failed to process .class files", e);
                throw e;
            }
        }
    }

    /**
     * @return Classpath for regular building of the project
     */
    public final String d() {
        StringBuilder classpath = new StringBuilder();

        /* Add android.jar */
        classpath.append(o);

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY,
                BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            classpath.append(":").append(l.getAbsolutePath()).append(File.separator).append(m).append(File.separator)
                    .append("http-legacy-android-28").append(File.separator).append("classes.jar");
        }

        /* Include MultiDex library if needed */
        if (settings.getMinSdkVersion() < 21) {
            classpath.append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append(m)
                    .append(File.separator)
                    .append("multidex-2.0.1")
                    .append(File.separator)
                    .append("classes.jar");
        }

        /* Add lambda helper classes */
        if (build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION,
                BuildSettings.SETTING_JAVA_VERSION_1_7)
                .equals(BuildSettings.SETTING_JAVA_VERSION_1_8)) {
            classpath.append(":").append(l.getAbsolutePath()).append(File.separator).append("core-lambda-stubs.jar");
        }

        /* Add used built-in libraries to the classpath */
        for (Jp library : n.a()) {
            classpath.append(":").append(l.getAbsolutePath()).append(File.separator).append(m).append(File.separator)
                    .append(library.a()).append(File.separator).append("classes.jar");
        }

        /* Add local libraries to the classpath */
        classpath.append(mll.getJarLocalLibrary());

        /* Append user's custom classpath */
        if (!build_settings.getValue(BuildSettings.SETTING_CLASSPATH, "").equals("")) {
            classpath.append(":");
            classpath.append(build_settings.getValue(BuildSettings.SETTING_CLASSPATH, ""));
        }

        /* Add JARs from project's classpath */
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + (f.b) + "/files/classpath/";
        ArrayList<String> jars = FileUtil.listFiles(path, "jar");
        classpath.append(":")
                .append(TextUtils.join(":", jars));

        return classpath.toString();
    }

    /**
     * @return Similar to {@link Dp#d()}, but doesn't return some local libraries' JARs if ProGuard full mode is enabled
     */
    public final String classpath() {
        StringBuilder baseClasses = new StringBuilder(o);
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            baseClasses
                    .append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append(m)
                    .append(File.separator)
                    .append("http-legacy-android-28")
                    .append(File.separator)
                    .append("classes.jar");
        }

        StringBuilder builtInLibrariesClasses = new StringBuilder();
        for (Jp builtInLibrary : n.a()) {
            builtInLibrariesClasses
                    .append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append(m)
                    .append(File.separator)
                    .append(builtInLibrary.a())
                    .append(File.separator)
                    .append("classes.jar");
        }

        StringBuilder localLibraryClasses = new StringBuilder();
        for (HashMap<String, Object> hashMap : mll.list) {
            Object nameObject = hashMap.get("name");
            Object jarPathObject = hashMap.get("jarPath");
            if (nameObject instanceof String && jarPathObject instanceof String) {
                String name = (String) nameObject;
                String jarPath = (String) jarPathObject;
                if (hashMap.containsKey("jarPath") && !proguard.libIsProguardFMEnabled(name)) {
                    localLibraryClasses
                            .append(":")
                            .append(jarPath);
                }
            }
        }

        String customClasspath = build_settings.getValue(BuildSettings.SETTING_CLASSPATH, "");
        if (!TextUtils.isEmpty(customClasspath)) {
            localLibraryClasses
                    .append(":")
                    .append(customClasspath);
        }

        return baseClasses.toString()
                + builtInLibrariesClasses
                + localLibraryClasses;
    }

    /**
     * Dexes libraries.
     *
     * @param outputPath The output path, needs to be a folder in case merging DEX files results in multiple
     * @param dexes      The paths of DEX files to merge
     * @throws Exception Thrown if merging had problems
     */
    private void dexLibraries(String outputPath, ArrayList<String> dexes) throws Exception {
        int lastDexNumber = 1;
        String nextMergedDexFilename;
        ArrayList<Dex> dexObjects = new ArrayList<>();
        Iterator<String> toMergeIterator = dexes.iterator();

        List<FieldId> mergedDexFields;
        List<MethodId> mergedDexMethods;
        List<ProtoId> mergedDexProtos;
        List<Integer> mergedDexTypes;

        {
            // Closable gets closed automatically
            Dex firstDex = new Dex(new FileInputStream(toMergeIterator.next()));
            dexObjects.add(firstDex);
            mergedDexFields = new ArrayList<>(firstDex.fieldIds());
            mergedDexMethods = new ArrayList<>(firstDex.methodIds());
            mergedDexProtos = new ArrayList<>(firstDex.protoIds());
            mergedDexTypes = new ArrayList<>(firstDex.typeIds());
        }

        while (toMergeIterator.hasNext()) {
            String dexPath = toMergeIterator.next();
            nextMergedDexFilename = lastDexNumber == 1 ? "classes.dex" : "classes" + lastDexNumber + ".dex";

            // Closable gets closed automatically
            Dex dex = new Dex(new FileInputStream(dexPath));

            boolean canMerge = true;
            List<FieldId> newDexFieldIds = new ArrayList<>();
            List<MethodId> newDexMethodIds = new ArrayList<>();
            List<ProtoId> newDexProtoIds = new ArrayList<>();
            List<Integer> newDexTypeIds = new ArrayList<>();

            bruh:
            {
                for (FieldId fieldId : dex.fieldIds()) {
                    if (!mergedDexFields.contains(fieldId)) {
                        if (mergedDexFields.size() + newDexFieldIds.size() + 1 > 0xffff) {
                            LogUtil.d(TAG, "Can't merge DEX file to " + nextMergedDexFilename +
                                    " because it has too many new field IDs. "
                                    + nextMergedDexFilename + " will have " + mergedDexFields.size() + " field IDs");
                            canMerge = false;
                            break bruh;
                        } else {
                            newDexFieldIds.add(fieldId);
                        }
                    }
                }

                for (MethodId methodId : dex.methodIds()) {
                    if (!newDexMethodIds.contains(methodId)) {
                        if (mergedDexMethods.size() + newDexMethodIds.size() + 1 > 0xffff) {
                            LogUtil.d(TAG, "Can't merge DEX file to " + nextMergedDexFilename +
                                    " because it has too many new method IDs. "
                                    + nextMergedDexFilename + " will have " + mergedDexMethods.size() + " method IDs");
                            canMerge = false;
                            break bruh;
                        } else {
                            newDexMethodIds.add(methodId);
                        }
                    }
                }

                for (ProtoId protoId : dex.protoIds()) {
                    if (!newDexProtoIds.contains(protoId)) {
                        if (mergedDexProtos.size() + newDexProtoIds.size() + 1 > 0xffff) {
                            LogUtil.d(TAG, "Can't merge DEX file to " + nextMergedDexFilename +
                                    " because it has too many new proto IDs. "
                                    + nextMergedDexFilename + " will have " + mergedDexProtos.size() + " proto IDs");
                            canMerge = false;
                            break bruh;
                        } else {
                            newDexProtoIds.add(protoId);
                        }
                    }
                }

                for (Integer typeId : dex.typeIds()) {
                    if (!newDexTypeIds.contains(typeId)) {
                        if (mergedDexTypes.size() + newDexProtoIds.size() + 1 > 0xffff) {
                            LogUtil.d(TAG, "Can't merge DEX file to " + nextMergedDexFilename +
                                    " because it has too many new type IDs. "
                                    + nextMergedDexFilename + " will have " + mergedDexTypes.size() + " type IDs");
                            canMerge = false;
                            break bruh;
                        } else {
                            newDexTypeIds.add(typeId);
                        }
                    }
                }
            }

            if (canMerge) {
                LogUtil.d(TAG, "Merging DEX #" + dexes.indexOf(dexPath) + " as well to " + nextMergedDexFilename);
                dexObjects.add(dex);
                mergedDexFields.addAll(newDexFieldIds);
                mergedDexMethods.addAll(newDexMethodIds);
                mergedDexProtos.addAll(newDexProtoIds);
                mergedDexTypes.addAll(newDexTypeIds);
            } else {
                mergeDexes(outputPath.replace("classes.dex", nextMergedDexFilename), dexObjects);
                dexObjects.clear();
                dexObjects.add(dex);

                mergedDexFields = new ArrayList<>(dex.fieldIds());
                mergedDexMethods = new ArrayList<>(dex.methodIds());
                mergedDexProtos = new ArrayList<>(dex.protoIds());
                mergedDexTypes = new ArrayList<>(dex.typeIds());
                lastDexNumber++;
            }
        }
        if (dexObjects.size() > 0) {
            String filename = lastDexNumber == 1 ? "classes.dex" : "classes" + lastDexNumber + ".dex";
            mergeDexes(outputPath.replace("classes.dex", filename), dexObjects);
        }
    }

    /**
     * Get package names of in-use libraries which have resources.
     */
    public String e() {
        StringBuilder extraPackages = new StringBuilder();
        for (Jp library : n.a()) {
            if (library.c()) {
                extraPackages.append(library.b()).append(":");
            }
        }
        return extraPackages + mll.getPackageNameLocalLibrary();
    }

    /**
     * Run Eclipse Compiler to compile Java files.
     *
     * @throws Throwable Thrown when Eclipse has problems compiling
     */
    public void f() throws Throwable {
        long savedTimeMillis = System.currentTimeMillis();

        class EclipseOutOutputStream extends OutputStream {

            private final StringBuffer mBuffer = new StringBuffer();

            @Override
            public void write(int b) {
                mBuffer.append(b);
            }

            public String getOut() {
                return mBuffer.toString();
            }
        }

        class EclipseErrOutputStream extends OutputStream {

            @Override
            public void write(int b) throws IOException {
                k.append((char) b);
            }
        }

        try (EclipseOutOutputStream outOutputStream = new EclipseOutOutputStream();
             PrintWriter outWriter = new PrintWriter(outOutputStream);
             EclipseErrOutputStream errOutputStream = new EclipseErrOutputStream();
             PrintWriter errWriter = new PrintWriter(errOutputStream)) {
            try {
                ArrayList<String> args = new ArrayList<>();
                args.add("-" + build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION,
                        BuildSettings.SETTING_JAVA_VERSION_1_7));
                args.add("-nowarn");
                if (!build_settings.getValue(BuildSettings.SETTING_NO_WARNINGS,
                        BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
                    args.add("-deprecation");
                }
                args.add("-d");
                args.add(f.u);
                args.add("-cp");
                args.add(d());
                args.add("-proc:none");
                args.add(f.y);
                args.add(f.v);
                String pathJava = fpu.getPathJava(f.b);
                if (FileUtil.isExistFile(pathJava)) {
                    args.add(pathJava);
                }
                String pathBroadcast = fpu.getPathBroadcast(f.b);
                if (FileUtil.isExistFile(pathBroadcast)) {
                    args.add(pathBroadcast);
                }
                String pathService = fpu.getPathService(f.b);
                if (FileUtil.isExistFile(pathService)) {
                    args.add(pathService);
                }

                /* Avoid "package ;" line in that file causing issues while compiling */
                File rJavaFileWithoutPackage = new File(f.v, "R.java");
                if (rJavaFileWithoutPackage.exists() && !rJavaFileWithoutPackage.delete()) {
                    LogUtil.w(TAG, "Failed to delete file " + rJavaFileWithoutPackage.getAbsolutePath());
                }

                /* Start compiling */
                org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false, null, null);
                LogUtil.d(TAG, "Running Eclipse compiler with these arguments: " + args);
                main.compile(args.toArray(new String[0]));

                if (main.globalErrorsCount <= 0) {
                    LogUtil.d(TAG, "System.out of Eclipse compiler: " + outOutputStream.getOut());
                    LogUtil.d(TAG, "System.err of Eclipse compiler: " + k);
                    LogUtil.d(TAG, "Compiling Java files took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
                } else {
                    throw new zy(k.toString());
                }
            } catch (Exception e) {
                LogUtil.e(TAG, "Failed to compile Java files", e);
                throw e;
            }
        }
    }

    /**
     * Builds an APK, used when clicking "Run" in DesignActivity
     */
    public void g() {
        String firstDexPath = dexesToAddButNotMerge.isEmpty() ? f.E : dexesToAddButNotMerge.remove(0);

        ApkBuilder apkBuilder = new ApkBuilder(new File(f.G), new File(f.C), new File(firstDexPath), null, null, System.out);

        for (Jp library : n.a()) {
            apkBuilder.addResourcesFromJar(new File(l, m + File.separator + library.a() + File.separator + "classes.jar"));
        }

        for (String jarPath : mll.getJarLocalLibrary().split(":")) {
            if (!jarPath.trim().isEmpty()) {
                apkBuilder.addResourcesFromJar(new File(jarPath));
            }
        }

        /* Add project's native libraries */
        File nativeLibrariesDirectory = new File(fpu.getPathNativelibs(f.b));
        if (nativeLibrariesDirectory.exists()) {
            apkBuilder.addNativeLibraries(nativeLibrariesDirectory);
        }

        /* Add Local libraries' native libraries */
        for (String nativeLibraryDirectory : mll.getNativeLibs()) {
            apkBuilder.addNativeLibraries(new File(nativeLibraryDirectory));
        }

        if (dexesToAddButNotMerge.isEmpty()) {
            List<String> dexFiles = FileUtil.listFiles(f.t, "dex");
            for (String dexFile : dexFiles) {
                if (!Uri.fromFile(new File(dexFile)).getLastPathSegment().equals("classes.dex")) {
                    apkBuilder.addFile(new File(dexFile), Uri.parse(dexFile).getLastPathSegment());
                }
            }
        } else {
            int dexNumber = 2;

            for (String dexPath : dexesToAddButNotMerge) {
                File dexFile = new File(dexPath);

                apkBuilder.addFile(dexFile, "classes" + dexNumber + ".dex");
                dexNumber++;
            }
        }

        apkBuilder.setDebugMode(false);
        apkBuilder.sealApk();

        LogUtil.d(TAG, "Time passed since starting to compile resources until building the unsigned APK: " +
                (System.currentTimeMillis() - timestampResourceCompilationStarted) + " ms");
    }

    /**
     * Currently unused?
     *
     * @return Directory/file path with Java classes ready to get transformed to DEX file(s).
     */
    public String getJava() {
        if (proguard.isProguardEnabled()) {
            ArrayList<String> arrayList = new ArrayList<>();
            FileUtil.listDir(f.classes_proguard, arrayList);
            if (arrayList.size() > 0) {
                return f.classes_proguard;
            }
        }
        return f.u;
    }

    /**
     * Merges all DEX files, of used built-in libraries, used Local libraries,and if the project's
     * minimum SDK version was set to 20 or lower, also the AndroidX MultiDex library.
     * If adding the HTTP legacy has not been disabled in Build Settings, it gets merged too.
     *
     * @throws Exception Thrown if merging failed
     */
    public void h() throws Exception {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> dexes = new ArrayList<>();

        /* Add AndroidX MultiDex library if needed */
        if (settings.getMinSdkVersion() < 21) {
            dexes.add(l.getAbsolutePath() + File.separator + "dexs" + File.separator + "multidex-2.0.1.dex");
        }

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, ProjectSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(ProjectSettings.SETTING_GENERIC_VALUE_TRUE)) {
            dexes.add(l.getAbsolutePath() + File.separator + "dexs" + File.separator + "http-legacy-android-28.dex");
        }

        /* Add used built-in libraries' DEX files */
        for (Jp builtInLibrary : n.a()) {
            dexes.add(l.getAbsolutePath() + File.separator + "dexs" + File.separator + builtInLibrary.a() + ".dex");
        }

        /* Add local libraries' main DEX files */
        ArrayList<HashMap<String, Object>> list = mll.list;
        for (int i1 = 0, listSize = list.size(); i1 < listSize; i1++) {
            HashMap<String, Object> localLibrary = list.get(i1);
            Object localLibraryName = localLibrary.get("name");

            if (localLibraryName instanceof String) {
                Object localLibraryDexPath = localLibrary.get("dexPath");

                if (localLibraryDexPath instanceof String) {
                    if (!proguard.libIsProguardFMEnabled((String) localLibraryName)) {
                        dexes.add((String) localLibraryDexPath);
                        /* Add library's extra DEX files */
                        File localLibraryDirectory = new File((String) localLibraryDexPath).getParentFile();

                        if (localLibraryDirectory != null) {
                            File[] localLibraryFiles = localLibraryDirectory.listFiles();

                            if (localLibraryFiles != null) {
                                for (File localLibraryFile : localLibraryFiles) {
                                    String filename = localLibraryFile.getName();

                                    if (!filename.equals("classes.dex")
                                            && filename.startsWith("classes") && filename.endsWith(".dex")) {
                                        dexes.add(localLibraryFile.getAbsolutePath());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    SketchwareUtil.toastError("Invalid DEX file path of enabled Local library #" + i1, Toast.LENGTH_LONG);
                }
            } else {
                SketchwareUtil.toastError("Invalid name of enabled Local library #" + i1, Toast.LENGTH_LONG);
            }
        }

        dexes.addAll(FileUtil.listFiles(f.t + File.separator + "dex", "dex"));

        LogUtil.d(TAG, "Will merge these " + dexes.size() + " DEX files to classes.dex: " + dexes);

        if (settings.getMinSdkVersion() < 21 || !f.N.f) {
            dexLibraries(f.E, dexes);
        } else {
            dexesToAddButNotMerge = dexes;
        }

        LogUtil.d(TAG, "Merging project DEX file(s) and libraries' took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    /**
     * Extracts AAPT2 binaries (if they need to be extracted).
     *
     * @throws Exception If anything goes wrong while extracting
     */
    public void i() throws Exception {
        String aapt2PathInAssets = "aapt/";
        if (GB.a().toLowerCase().contains("x86")) {
            aapt2PathInAssets += "aapt2-x86";
        } else {
            aapt2PathInAssets += "aapt2-arm";
        }
        try {
            /* Check if we need to update AAPT2's binary */
            if (a(aapt2PathInAssets, aapt2Dir.getAbsolutePath())) {
                makeExecutableCommand[2] = aapt2Dir.getAbsolutePath();
                j.a(makeExecutableCommand);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to extract AAPT2 binaries", e);
            throw new By("Couldn't extract AAPT2 binaries! Message: " + e.getMessage());
        }
    }

    /**
     * Checks if we need to extract any library/dependency from assets to filesDir,
     * and extracts them, if needed. Also initializes used built-in libraries.
     */
    public void j() {
        /* If l doesn't exist, create it */
        if (!g.e(l.getAbsolutePath())) {
            g.f(l.getAbsolutePath());
        }
        String androidJarArchiveName = "android.jar.zip";
        String dexsArchiveName = "dexs.zip";
        String coreLambdaStubsJarName = "core-lambda-stubs.jar";
        String libsArchiveName = "libs.zip";
        String testkeyArchiveName = "testkey.zip";

        String androidJarPath = new File(l, androidJarArchiveName).getAbsolutePath();
        String dexsArchivePath = new File(l, dexsArchiveName).getAbsolutePath();
        String coreLambdaStubsJarPath = new File(l, coreLambdaStubsJarName).getAbsolutePath();
        String libsArchivePath = new File(l, libsArchiveName).getAbsolutePath();
        String testkeyArchivePath = new File(l, testkeyArchiveName).getAbsolutePath();
        String dexsDirectoryPath = new File(l, "dexs").getAbsolutePath();
        String libsDirectoryPath = new File(l, "libs").getAbsolutePath();
        String testkeyDirectoryPath = new File(l, "testkey").getAbsolutePath();
        /* If necessary, update android.jar.zip */
        if (a(m + File.separator + androidJarArchiveName, androidJarPath)) {
            if (buildingDialog != null) {
                buildingDialog.c("Extracting built-in android.jar...");
            }
            /* Delete android.jar */
            g.c(l.getAbsolutePath() + c + "android.jar");
            /* Extract android.jar.zip to android.jar */
            new KB().a(androidJarPath, l.getAbsolutePath());
        }
        /* If necessary, update dexs.zip */
        if (a(m + File.separator + dexsArchiveName, dexsArchivePath)) {
            if (buildingDialog != null) {
                buildingDialog.c("Extracting built-in libraries' DEX files...");
            }
            /* Delete the directory */
            g.b(dexsDirectoryPath);
            /* Create the directories */
            g.f(dexsDirectoryPath);
            /* Extract dexs.zip to dexs/ */
            new KB().a(dexsArchivePath, dexsDirectoryPath);
        }
        /* If necessary, update libs.zip */
        if (a(m + File.separator + libsArchiveName, libsArchivePath)) {
            if (buildingDialog != null) {
                buildingDialog.c("Extracting built-in libraries' resources...");
            }
            /* Delete the directory */
            g.b(libsDirectoryPath);
            /* Create the directories */
            g.f(libsDirectoryPath);
            /* Extract libs.zip to libs/ */
            new KB().a(libsArchivePath, libsDirectoryPath);
        }
        /* If necessary, update core-lambda-stubs.jar */
        a(m + File.separator + coreLambdaStubsJarName, coreLambdaStubsJarPath);
        /* If necessary, update testkey.zip */
        if (a(m + File.separator + testkeyArchiveName, testkeyArchivePath)) {
            if (buildingDialog != null) {
                buildingDialog.c("Extracting built-in signing keys...");
            }
            /* Delete the directory */
            g.b(testkeyDirectoryPath);
            /* Create the directories */
            g.f(testkeyDirectoryPath);
            /* Extract testkey.zip to testkey/ */
            new KB().a(testkeyArchivePath, testkeyDirectoryPath);
        }
        if (f.N.g) {
            n.a("appcompat-1.0.0");
            n.a("coordinatorlayout-1.0.0");
            n.a("material-1.0.0");
        }
        if (f.N.h) {
            n.a("firebase-common-19.0.0");
        }
        if (f.N.i) {
            n.a("firebase-auth-19.0.0");
        }
        if (f.N.j) {
            n.a("firebase-database-19.0.0");
        }
        if (f.N.k) {
            n.a("firebase-storage-19.0.0");
        }
        if (f.N.m) {
            n.a("play-services-maps-17.0.0");
        }
        if (f.N.l) {
            n.a("play-services-ads-18.2.0");
        }
        if (f.N.o) {
            n.a("gson-2.8.0");
        }
        if (f.N.n) {
            n.a("glide-4.11.0");
        }
        if (f.N.p) {
            n.a("okhttp-3.9.1");
        }
        ExtLibSelected.addUsedDependencies(f.N.x, n);
    }

    /**
     * Sign the APK file with testkey.
     * This method supports APK Signature Scheme V1 (JAR signing) only.
     */
    public boolean k() {
        try {
            ZipSigner zipSigner = new ZipSigner();
            KeyStoreFileManager.setProvider(new BouncyCastleProvider());
            zipSigner.setKeymode(ZipSigner.KEY_TESTKEY);
            zipSigner.signZip(f.G, f.H);
            return true;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IOException | GeneralSecurityException e) {
            LogUtil.e(TAG, "Failed to sign APK: " + e.getMessage(), e);
        }
        return false;
    }

    private void mergeDexes(String target, ArrayList<Dex> dexes) throws IOException {
        DexMerger merger = new DexMerger(dexes.toArray(new Dex[0]), CollisionPolicy.KEEP_FIRST, new DxContext());
        merger.merge().writeTo(new File(target));
    }

    /**
     * Adds all built-in libraries' ProGuard rules to {@code args}, if any.
     *
     * @param args List of arguments to add built-in libraries' ProGuard roles to.
     */
    private void proguardAddLibConfigs(List<String> args) {
        for (Jp jp : n.a()) {
            String str = l.getAbsolutePath() + c + jp.a() + c + "proguard.txt";
            if (FileUtil.isExistFile(str)) {
                args.add("-include");
                args.add(str);
            }
        }
    }

    /**
     * Generates default ProGuard R.java rules and adds them to {@code args}.
     *
     * @param args List of arguments to add R.java rules to.
     */
    private void proguardAddRjavaRules(List<String> args) {
        StringBuilder sb = new StringBuilder("# R.java rules");
        for (Jp jp : n.a()) {
            if (jp.c() && !jp.b().isEmpty()) {
                sb.append("\n");
                sb.append("-keep class ");
                sb.append(jp.b());
                sb.append(".** { *; }");
            }
        }
        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("packageName") && !proguard.libIsProguardFMEnabled(obj)) {
                sb.append("\n");
                sb.append("-keep class ");
                sb.append(hashMap.get("packageName").toString());
                sb.append(".** { *; }");
            }
        }
        sb.append("\n");
        FileUtil.writeFile(f.rules_generated, sb.toString());
        args.add("-include");
        args.add(f.rules_generated);
    }

    public void runProguard() throws Exception {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();

        /* Include global ProGuard rules */
        args.add("-include");
        args.add(ProguardHandler.ANDROID_PROGUARD_RULES_PATH);

        /* Include ProGuard rules generated by AAPT2 */
        args.add("-include");
        args.add(f.aapt_rules);

        /* Include custom ProGuard rules */
        args.add("-include");
        args.add(proguard.getCustomProguardRules());

        proguardAddLibConfigs(args);
        proguardAddRjavaRules(args);

        /* Include local libraries' ProGuard rules */
        for (String localLibraryProGuardRule : mll.getPgRules()) {
            args.add("-include");
            args.add(localLibraryProGuardRule);
        }

        /* Include compiled Java classes (?) IT SAYS -in*jar*s, so why include .class es? */
        args.add("-injars");
        args.add(f.u);

        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("jarPath") && proguard.libIsProguardFMEnabled(obj)) {
                args.add("-injars");
                args.add(hashMap.get("jarPath").toString());
            }
        }
        args.add("-libraryjars");
        args.add(classpath());
        args.add("-outjars");
        args.add(f.classes_proguard);
        if (proguard.isDebugFilesEnabled()) {
            args.add("-printseeds");
            args.add(f.printseeds);
            args.add("-printusage");
            args.add(f.printusage);
            args.add("-printmapping");
            args.add(f.printmapping);
        }
        LogUtil.d(TAG, "About to run ProGuard with these arguments: " + args);

        Configuration configuration = new Configuration();
        ConfigurationParser parser = new ConfigurationParser(args.toArray(new String[0]), System.getProperties());

        try {
            parser.parse(configuration);
        } finally {
            parser.close();
        }

        new ProGuard(configuration).execute();

        LogUtil.d(TAG, "ProGuard took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void runStringfog() {
        try {
            StringFogMappingPrinter stringFogMappingPrinter = new StringFogMappingPrinter(new File(f.t,
                    "stringFogMapping.txt"));
            StringFogClassInjector stringFogClassInjector = new StringFogClassInjector(new String[0],
                    "UTF-8",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    stringFogMappingPrinter);
            stringFogMappingPrinter.startMappingOutput();
            stringFogMappingPrinter.ouputInfo("UTF-8", "com.github.megatronking.stringfog.xor.StringFogImpl");
            stringFogClassInjector.doFog2ClassInDir(new File(f.u));
            KB.a(e, "stringfog/stringfog.zip", f.u);
        } catch (Exception e) {
            LogUtil.e("Stringfog", e.toString());
        }
    }
}
