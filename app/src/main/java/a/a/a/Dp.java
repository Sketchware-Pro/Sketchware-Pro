package a.a.a;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;

import com.android.sdklib.build.ApkBuilder;
import com.android.tools.r8.D8;
import com.besome.sketch.design.DesignActivity;
import com.github.megatronking.stringfog.plugin.StringFogClassInjector;
import com.github.megatronking.stringfog.plugin.StringFogMappingPrinter;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;
import mod.agus.jcoderz.builder.DexMerge;
import mod.agus.jcoderz.command.ProcessingFiles;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.dx.merge.CollisionPolicy;
import mod.agus.jcoderz.dx.merge.DexMerger;
import mod.agus.jcoderz.editor.library.ExtLibSelected;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.alucard.tn.shrinker.R8Executor;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.util.SystemLogPrinter;
import mod.jbk.build.compiler.resource.ResourceCompiler;
import mod.jbk.util.LogUtil;
import proguard.ProGuard;

public class Dp {

    public final static String TAG = "AppBuilder";
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
    public File aapt2Dir;
    public BuildSettings build_settings;
    public DesignActivity.a buildingDialog;
    /**
     * Command(s) to execute after extracting AAPT/AAPT2 (fill in 2 with the file name before using)
     */
    public String[] d = {"chmod", "744", ""};
    public Context e;
    public yq f;
    public FilePathUtil fpu;
    public oB g;
    /**
     * Directory "tmp" in files directory, where libs are extracted and compiled
     */
    public File h;
    /**
     * File object that represents aapt
     */
    public File i;
    public Fp j;
    /**
     * A StringBuffer with System.err of Eclipse compiler. If compilation succeeds, it doesn't have any content, if it doesn't, there is.
     */
    public StringBuffer k = new StringBuffer();
    /**
     * Extracted built-in libraries directory
     */
    public File l;
    public DexMerge merge;
    public ManageLocalLibrary mll;
    public Kp n;
    /**
     * Path of the android.jar to use
     */
    public String o;
    public ProguardHandler proguard;
    public ProjectSettings settings;
    private boolean buildAppBundle = false;
    private ArrayList<String> dexesGenerated;
    /**
     * An ArrayList that contains DEX files generated from R.java classes and project files.
     * Gets initialized right after calling {@link Dp#c()}
     */
    private ArrayList<String> extraDexes;

    public Dp(Context context, yq yqVar) {
        /*
         * Detect some bad behaviour of the app.
         */
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        );
        /*
         * Start logging to debug.txt stored in /Internal storage/.sketchware/.
         */
        SystemLogPrinter.start();
        e = context;
        f = yqVar;
        g = new oB(false);
        j = new Fp();
        h = new File(context.getFilesDir(), "tmp");
        if (!h.exists()) {
            h.mkdir();
        }
        i = new File(h, "aapt");
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
        long savedTimeMillis = System.currentTimeMillis();
        b();
        Log.d(TAG, "Compiling resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void a(iI iIVar, String str) {
        ZipSigner signer = iIVar.b(new CB().a(str));
        try {
            signer.signZip(f.G, f.I);
        } catch (IOException | GeneralSecurityException e) {
            Log.e(TAG, "Failed to sign APK: " + e.getMessage(), e);
        }
    }

    /**
     * Stub simply calling {@link Dp#dexLibraries(String, ArrayList)}
     *
     * @param outputPath The output file, usually classes2.dex
     * @param dexes      The path of DEX files to merge
     * @throws Exception Thrown if dexing had problems
     */
    public final void a(String outputPath, ArrayList<String> dexes) throws Exception {
        dexLibraries(outputPath, dexes);
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
     * Compile the project's resources, either with AAPT or AAPT2 automatically.
     *
     * @throws Exception Thrown in case AAPT/AAPT2 has an error while compiling resources.
     */
    public void b() throws Exception {
        ResourceCompiler compiler = new ResourceCompiler(
                this,
                aapt2Dir,
                buildAppBundle,
                buildingDialog,
                true);
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
            Log.e(TAG, "Failed to sign APK: " + e.getMessage(), e);
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
        if (isD8Enabled()) {
            long savedTimeMillis = System.currentTimeMillis();
            ArrayList<String> args = new ArrayList<>();
            args.add("--release");
            args.add("--intermediate");
            args.add("--min-api");
            args.add(settings.getValue(ProjectSettings.SETTING_MINIMUM_SDK_VERSION, "21"));
            args.add("--lib");
            args.add(o);
            args.add("--output");
            args.add(f.t);
            if (proguard.isProguardEnabled()) {
                args.add(f.classes_proguard);
            } else {
                args.addAll(ProcessingFiles.getListResource(f.u));
            }
            try {
                /* Not "supported"/tested out yet */
                if (false) {
                    Log.d(TAG, "Running R8");
                    R8Executor r8Executor = new R8Executor(this, buildingDialog);
                    r8Executor.preparingEnvironment();
                    r8Executor.compile();
                }
                Log.d(TAG, "Running D8 with these arguments: " + args);
                D8.main(args.toArray(new String[0]));
                Log.d(TAG, "D8 took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } catch (Exception e) {
                Log.e(TAG, "D8 error: " + e.getMessage(), e);
                throw e;
            }
        } else {
            Main.dexOutputArrays = new ArrayList<>();
            Main.dexOutputFutures = new ArrayList<>();
            long savedTimeMillis = System.currentTimeMillis();
            ArrayList<String> args = new ArrayList<>();
            args.add("--debug");
            args.add("--verbose");
            args.add("--multi-dex");
            args.add("--output=" + f.t);
            if (proguard.isProguardEnabled()) {
                args.add(f.classes_proguard);
            } else {
                args.add(f.u);
            }
            try {
                Log.d(TAG, "Running Dx with these arguments: " + args);
                Main.main(args.toArray(new String[0]));
                Log.d(TAG, "Dx took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } catch (Exception e) {
                Log.e(TAG, "Dx error: " + e.getMessage(), e);
                throw e;
            }
        }
        findExtraDexes();
    }

    public final String d() {
        StringBuilder classpath = new StringBuilder();

        /* Add android.jar */
        classpath.append(f.v)
                .append(":")
                .append(o);

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY,
                BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            classpath.append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append("libs")
                    .append(File.separator)
                    .append("http-legacy-android-28")
                    .append(File.separator)
                    .append("classes.jar");
        }

        /* Include MultiDex library if needed */
        int minSdkVersion;
        try {
            minSdkVersion = Integer.parseInt(settings.getValue(ProjectSettings.SETTING_MINIMUM_SDK_VERSION,
                    "21"));
        } catch (NumberFormatException e) {
            minSdkVersion = 21;
        }
        if (minSdkVersion < 21) {
            classpath.append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append("libs")
                    .append(File.separator)
                    .append("multidex-2.0.1")
                    .append(File.separator)
                    .append("classes.jar");
        }

        /* Add lambda helper classes */
        if (build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION,
                BuildSettings.SETTING_JAVA_VERSION_1_7)
                .equals(BuildSettings.SETTING_JAVA_VERSION_1_8)) {
            classpath.append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append("core-lambda-stubs.jar");
        }

        /* Add used built-in libraries to the classpath */
        for (Jp library : n.a()) {
            classpath.append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append("libs")
                    .append(File.separator)
                    .append(library.a())
                    .append(File.separator)
                    .append("classes.jar");
        }

        /* Add local libraries to the classpath */
        classpath.append(mll.getJarLocalLibrary());

        /* Append user's custom classpath */
        if (!build_settings.getValue(BuildSettings.SETTING_CLASSPATH, "").equals("")) {
            classpath.append(":");
            classpath.append(build_settings.getValue(BuildSettings.SETTING_CLASSPATH, ""));
        }

        /* Add jars from project's classpath */
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + (f.b) + "/files/classpath/";
        ArrayList<String> jars = FileUtil.listFiles(path, "jar");
        classpath.append(":")
                .append(TextUtils.join(":", jars));

        return classpath.toString();
    }

    /**
     * @return Similar to {@link Dp#d}, but doesn't return some local libraries' JARs if ProGuard full mode is enabled
     */
    public final String classpath() {
        StringBuilder baseClasses = new StringBuilder(f.v)
                .append(":")
                .append(o);
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            baseClasses
                    .append(":")
                    .append(l.getAbsolutePath())
                    .append(File.separator)
                    .append("libs")
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
                    .append("libs")
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
        dexesGenerated = new ArrayList<>();
        int lastDexNumber = findLastDexNo();
        ArrayList<Dex> dexObjects = new ArrayList<>();
        int methodsMergedFile = 0;
        for (String dexPath : dexes) {
            Dex dex = new Dex(new FileInputStream(dexPath));
            int currentDexMethods = dex.methodIds().size();
            if (currentDexMethods + methodsMergedFile >= 65536) {
                mergeDexes(outputPath.replace("classes2.dex", "classes" + lastDexNumber + ".dex"), dexObjects);
                dexObjects.clear();
                dexObjects.add(dex);
                methodsMergedFile = currentDexMethods;
                lastDexNumber++;
            } else {
                dexObjects.add(dex);
                methodsMergedFile += currentDexMethods;
            }
        }
        if (dexObjects.size() > 0) {
            mergeDexes(outputPath.replace("classes2.dex", "classes" + lastDexNumber + ".dex"), dexObjects);
        }
    }

    /**
     * Get extra packages used in this project, needed for AAPT/AAPT2.
     */
    public final String e() {
        StringBuilder extraPackages = new StringBuilder();
        for (Jp library : n.a()) {
            if (library.c()) {
                extraPackages.append(library.b()).append(":");
            }
        }
        return extraPackages + mll.getPackageNameLocalLibrary();
    }

    /**
     * Run Eclipse Compiler to compile Java classes from Java source code
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

        EclipseOutOutputStream outOutputStream = new EclipseOutOutputStream();
        /* System.out for Eclipse compiler */
        PrintWriter outWriter = new PrintWriter(outOutputStream);

        EclipseErrOutputStream errOutputStream = new EclipseErrOutputStream();
        /* System.err for Eclipse compiler */
        PrintWriter errWriter = new PrintWriter(errOutputStream);

        try {
            ArrayList<String> args = new ArrayList<>();
            args.add("-" + build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION, BuildSettings.SETTING_JAVA_VERSION_1_7));
            args.add("-nowarn");
            if (!build_settings.getValue(BuildSettings.SETTING_NO_WARNINGS, "false").equals("true")) {
                args.add("-deprecation");
            }
            args.add("-d");
            args.add(f.u);
            args.add("-cp");
            args.add(d());
            args.add("-proc:none");
            args.add("-sourcepath");
            args.add(f.y);
            args.add(f.o);
            args.add(f.q);
            if (FileUtil.isExistFile(fpu.getPathJava(f.b))) {
                args.add(fpu.getPathJava(f.b));
            }
            if (FileUtil.isExistFile(fpu.getPathBroadcast(f.b))) {
                args.add(fpu.getPathBroadcast(f.b));
            }
            if (FileUtil.isExistFile(fpu.getPathService(f.b))) {
                args.add(fpu.getPathService(f.b));
            }

            /* Adding built-in libraries' R.java files */
            for (Jp library : n.a()) {
                if (library.c()) {
                    args.add(f.v + File.separator + library.b().replace(".", File.separator) + File.separator + "R.java");
                }
            }

            /* Adding local libraries' R.java files */
            args.addAll(mll.getGenLocalLibrary());

            /* Start compiling */
            org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false, null, null);
            LogUtil.log(TAG, "Running Eclipse compiler with these arguments: ", "About to log Eclipse compiler's arguments in multiple lines because of length.", args);
            main.compile(args.toArray(new String[0]));

            if (main.globalErrorsCount <= 0) {
                try {
                    outOutputStream.close();
                    errOutputStream.close();
                    outWriter.close();
                    errWriter.close();
                    LogUtil.log(TAG, "System.out of Eclipse compiler: ", "About to log System.out of Eclipse compiler on multiple lines because of length.", outOutputStream.getOut());
                    LogUtil.log(TAG, "System.err of Eclipse compiler: ", "About to log System.err of Eclipse compiler on multiple lines because of length.", k.toString());
                } catch (IOException ignored) {
                }
                Log.d(TAG, "Compiling Java files took " + (System.currentTimeMillis() - savedTimeMillis) + " ms.");
            } else {
                throw new zy(k.toString());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw e;
        }
    }

    public final void findExtraDexes() {
        extraDexes = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(f.t, arrayList);
        for (String str : arrayList) {
            if (str.contains("classes") && str.contains(".dex") && !Uri.parse(str).getLastPathSegment().equals("classes.dex")) {
                extraDexes.add(str);
            }
        }
    }

    public final int findLastDexNo() {
        ArrayList<String> arrayList = new ArrayList<>();
        FileUtil.listDir(f.t, arrayList);
        ArrayList<String> arrayList2 = new ArrayList<>();
        for (String str : arrayList) {
            if (str.contains("classes") && str.contains(".dex")) {
                arrayList2.add(Uri.parse(str).getLastPathSegment());
            }
        }
        if (arrayList2.size() == 1 && arrayList2.get(0).equals("classes.dex")) {
            return 2;
        }
        int i2 = 1;
        for (String str2 : arrayList2) {
            String replace = str2.replace("classes", "").replace(".dex", "");
            i2 = Math.max(i2, replace.isEmpty() ? 1 : java.lang.Integer.parseInt(replace));
        }
        return i2 + 1;
    }

    /**
     * Builds an APK, used when clicking "Run" in DesignActivity
     */
    public void g() {
        ApkBuilder apkBuilder = new ApkBuilder(new File(f.G), new File(f.C), new File(f.E), null, null, System.out);
        for (HashMap<String, Object> localLibraries : mll.list) {
            apkBuilder.addResourcesFromJar(new File(localLibraries.get("jarPath").toString()));
        }
        File file = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/".concat(f.b.concat("/files/native_libs")));
        if (FileUtil.isExistFile(file.getAbsolutePath())) {
            apkBuilder.addNativeLibraries(file);
        }
        for (String nativeLibrary : mll.getNativeLibs()) {
            apkBuilder.addNativeLibraries(new File(nativeLibrary));
        }
        for (String extraDex : extraDexes) {
            apkBuilder.addFile(new File(extraDex), Uri.parse(extraDex).getLastPathSegment());
        }
        for (String generatedDex : dexesGenerated) {
            apkBuilder.addFile(new File(generatedDex), Uri.parse(generatedDex).getLastPathSegment());
        }
        apkBuilder.setDebugMode(false);
        apkBuilder.sealApk();
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
     * Merges all DEX files, of used built-in libraries, used local libraries,
     * and if running on Android Marshmallow and lower, also the AndroidX MultiDex library.
     * If adding the HTTP legacy has not been disabled in Build Settings, it gets merged too.
     *
     * @throws Exception Thrown if merging failed
     */
    public void h() throws Exception {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> dexes = new ArrayList<>();

        /* Add AndroidX MultiDex library if needed */
        int minSdkVersion;
        try {
            minSdkVersion = Integer.parseInt(
                    settings.getValue(ProjectSettings.SETTING_MINIMUM_SDK_VERSION,
                            "21"));
        } catch (NumberFormatException e) {
            minSdkVersion = 21;
        }

        if (minSdkVersion < 21) {
            dexes.add(l.getAbsolutePath() + c + "dexs" + c + "multidex-2.0.1" + ".dex");
        }

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, "false").equals("true")) {
            dexes.add(l.getAbsolutePath() + c + "dexs" + c + "http-legacy-android-28" + ".dex");
        }

        /* Add used built-in libraries' DEX files */
        for (Jp builtInLibrary : n.a()) {
            dexes.add(l.getAbsolutePath() + c + "dexs" + c + builtInLibrary.a() + ".dex");
        }

        /* Add local libraries' main DEX files */
        for (HashMap<String, Object> localLibrary : mll.list) {
            String localLibraryName = localLibrary.get("name").toString();
            if (localLibrary.containsKey("dexPath") && !proguard.libIsProguardFMEnabled(localLibraryName)) {
                dexes.add(localLibrary.get("dexPath").toString());
            }
        }

        /* Add local libraries' extra DEX files */
        dexes.addAll(mll.getExtraDexes());
        LogUtil.log(TAG, "Will merge these " + dexes.size() + " DEX files to classes2.dex: ",
                "Will merge these " + dexes.size() + " DEX files to classes2.dex: ", dexes);
        dexLibraries(f.F, dexes);
        Log.d(TAG, "Merging project DEX file(s) and libraries' took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    /**
     * Extracts AAPT binaries (if they need to be extracted).
     *
     * @throws Exception If anything goes wrong while extracting
     */
    public void i() throws Exception {
        String aaptPathInAssets;
        String aapt2PathInAssets;
        if (GB.a().toLowerCase().matches("x86")) {
            aaptPathInAssets = "aapt/aapt-x86";
            aapt2PathInAssets = "aapt/aapt2-x86";
        } else {
            aaptPathInAssets = "aapt/aapt-arm";
            aapt2PathInAssets = "aapt/aapt2-arm";
        }
        try {
            /* Check if we need to update aapt */
            if (a(aaptPathInAssets, i.getAbsolutePath())) {
                d[2] = i.getAbsolutePath();
                j.a(d);
            }

            /* Check if we need to update aapt2 */
            if (a(aapt2PathInAssets, aapt2Dir.getAbsolutePath())) {
                d[2] = aapt2Dir.getAbsolutePath();
                j.a(d);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            throw new By("Couldn't extract AAPT binaries! Message: " + e.getMessage());
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
            buildingDialog.c("Extracting built-in android.jar...");
            /* Delete android.jar */
            g.c(l.getAbsolutePath() + c + "android.jar");
            /* Extract android.jar.zip to android.jar */
            new KB().a(androidJarPath, l.getAbsolutePath());
        }
        /* If necessary, update dexs.zip */
        if (a(m + File.separator + dexsArchiveName, dexsArchivePath)) {
            buildingDialog.c("Extracting built-in libraries' DEX files...");
            /* Delete the directory */
            g.b(dexsDirectoryPath);
            /* Create the directories */
            g.f(dexsDirectoryPath);
            /* Extract dexs.zip to dexs/ */
            new KB().a(dexsArchivePath, dexsDirectoryPath);
        }
        /* If necessary, update libs.zip */
        if (a(m + File.separator + libsArchiveName, libsArchivePath)) {
            buildingDialog.c("Extracting built-in libraries' resources...");
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
            buildingDialog.c("Extracting built-in signing keys...");
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
            Log.e(TAG, "Failed to sign APK: " + e.getMessage(), e);
        }
        return false;
    }

    private void mergeDexes(String target, ArrayList<Dex> dexes) throws IOException {
        new DexMerger(dexes.toArray(new Dex[0]), CollisionPolicy.KEEP_FIRST).merge().writeTo(new File(target));
        dexesGenerated.add(target);
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

    public void runProguard() {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();

        /* Include global ProGuard rules */
        args.add("-include");
        args.add(ProguardHandler.ANDROID_PROGUARD_RULES_PATH);

        /* Include ProGuard rules generated by AAPT/AAPT2 */
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
        LogUtil.log(TAG,
                "About to run ProGuard with these arguments: ",
                "About to log ProGuard's arguments on multiple lines because of length",
                args);
        ProGuard.main(args.toArray(new String[0]));
        Log.d(TAG, "ProGuard took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
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
            Log.e("Stringfog", e.toString());
        }
    }
}
