package a.a.a;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.android.sdklib.build.ApkBuilder;
import com.besome.sketch.design.DesignActivity.BuildAsyncTask;
import com.github.megatronking.stringfog.plugin.StringFogClassInjector;
import com.github.megatronking.stringfog.plugin.StringFogMappingPrinter;
import com.iyxan23.zipalignjava.InvalidZipException;
import com.iyxan23.zipalignjava.ZipAlign;

import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import kellinwood.security.zipsigner.ZipSigner;
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
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.build.compiler.dex.DexCompiler;
import mod.jbk.build.compiler.resource.ResourceCompiler;
import mod.jbk.util.LogUtil;
import proguard.Configuration;
import proguard.ConfigurationParser;
import proguard.ParseException;
import proguard.ProGuard;

public class Dp {

    public static final String TAG = "AppBuilder";
    /**
     * Command(s) to execute after extracting AAPT2 (put the filename to index 2 before using)
     */
    private final String[] makeExecutableCommand = {"chmod", "700", ""};
    private final File aapt2Binary;
    public BuildSettings build_settings;
    private BuildAsyncTask buildingDialog;
    private final Context context;
    public yq yq;
    public FilePathUtil fpu;
    private final oB fileUtil;
    private final Fp commandExecutor;
    public ManageLocalLibrary mll;
    public Kp builtInLibraryManager;
    public String androidJarPath;
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

        aapt2Binary = new File(context.getCacheDir(), "aapt2");
        build_settings = new BuildSettings(yqVar.b);
        this.context = context;
        yq = yqVar;
        fpu = new FilePathUtil();
        fileUtil = new oB(false);
        commandExecutor = new Fp();
        mll = new ManageLocalLibrary(yqVar.b);
        builtInLibraryManager = new Kp();
        File defaultAndroidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        androidJarPath = build_settings.getValue(BuildSettings.SETTING_ANDROID_JAR_PATH, defaultAndroidJar.getAbsolutePath());
        proguard = new ProguardHandler(yqVar.b);
        settings = new ProjectSettings(yqVar.b);
    }

    public Dp(BuildAsyncTask buildAsyncTask, Context context, yq yqVar) {
        this(context, yqVar);
        buildingDialog = buildAsyncTask;
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
            signer.signZip(yq.G, yq.I);
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
        long lengthOfFileInAssets = fileUtil.a(context, fileInAssets);
        if (compareToFile.exists()) {
            length = compareToFile.length();
        } else {
            length = 0;
        }
        if (lengthOfFileInAssets == length) {
            return false;
        }

        /* Delete the file */
        fileUtil.a(compareToFile);
        /* Copy the file from assets to local storage */
        fileUtil.a(context, fileInAssets, targetFile);
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
                aapt2Binary,
                buildAppBundle,
                buildingDialog);
        compiler.compile();
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
        FileUtil.makeDir(yq.t + File.separator + "dex");
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
                    "--output=" + yq.t + File.separator + "dex",
                    proguard.isProguardEnabled() ? yq.classes_proguard : yq.u
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
        classpath.append(androidJarPath);

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY,
                BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            classpath.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(BuiltInLibraries.HTTP_LEGACY_ANDROID_28));
        }

        /* Include MultiDex library if needed */
        if (settings.getMinSdkVersion() < 21) {
            classpath.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(BuiltInLibraries.ANDROIDX_MULTIDEX));
        }

        /* Add lambda helper classes */
        if (build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION,
                        BuildSettings.SETTING_JAVA_VERSION_1_7)
                .equals(BuildSettings.SETTING_JAVA_VERSION_1_8)) {
            classpath.append(":").append(new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "core-lambda-stubs.jar").getAbsolutePath());
        }

        /* Add used built-in libraries to the classpath */
        for (Jp library : builtInLibraryManager.a()) {
            classpath.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(library.a()));
        }

        /* Add local libraries to the classpath */
        classpath.append(mll.getJarLocalLibrary());

        /* Append user's custom classpath */
        if (!build_settings.getValue(BuildSettings.SETTING_CLASSPATH, "").equals("")) {
            classpath.append(":").append(build_settings.getValue(BuildSettings.SETTING_CLASSPATH, ""));
        }

        /* Add JARs from project's classpath */
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + (yq.b) + "/files/classpath/";
        ArrayList<String> jars = FileUtil.listFiles(path, "jar");
        classpath.append(":").append(TextUtils.join(":", jars));

        return classpath.toString();
    }

    /**
     * @return Similar to {@link Dp#d()}, but doesn't return some local libraries' JARs if ProGuard full mode is enabled
     */
    public final String classpath() {
        StringBuilder baseClasses = new StringBuilder(androidJarPath);
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            baseClasses.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(BuiltInLibraries.HTTP_LEGACY_ANDROID_28));
        }

        StringBuilder builtInLibrariesClasses = new StringBuilder();
        for (Jp builtInLibrary : builtInLibraryManager.a()) {
            builtInLibrariesClasses.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(builtInLibrary.a()));
        }

        StringBuilder localLibraryClasses = new StringBuilder();
        for (HashMap<String, Object> hashMap : mll.list) {
            Object nameObject = hashMap.get("name");
            Object jarPathObject = hashMap.get("jarPath");
            if (nameObject instanceof String && jarPathObject instanceof String) {
                String name = (String) nameObject;
                String jarPath = (String) jarPathObject;
                if (hashMap.containsKey("jarPath") && !proguard.libIsProguardFMEnabled(name)) {
                    localLibraryClasses.append(":").append(jarPath);
                }
            }
        }

        String customClasspath = build_settings.getValue(BuildSettings.SETTING_CLASSPATH, "");
        if (!TextUtils.isEmpty(customClasspath)) {
            localLibraryClasses.append(":").append(customClasspath);
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
        for (Jp library : builtInLibraryManager.a()) {
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
                mBuffer.append((char) b);
            }

            public String getOut() {
                return mBuffer.toString();
            }
        }

        class EclipseErrOutputStream extends OutputStream {

            private final StringBuffer mBuffer = new StringBuffer();

            @Override
            public void write(int b) {
                mBuffer.append((char) b);
            }

            public String getOut() {
                return mBuffer.toString();
            }
        }

        try (EclipseOutOutputStream outOutputStream = new EclipseOutOutputStream();
             PrintWriter outWriter = new PrintWriter(outOutputStream);
             EclipseErrOutputStream errOutputStream = new EclipseErrOutputStream();
             PrintWriter errWriter = new PrintWriter(errOutputStream)) {

            ArrayList<String> args = new ArrayList<>();
            args.add("-" + build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION,
                    BuildSettings.SETTING_JAVA_VERSION_1_7));
            args.add("-nowarn");
            if (build_settings.getValue(BuildSettings.SETTING_NO_WARNINGS,
                    BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
                args.add("-deprecation");
            }
            args.add("-d");
            args.add(yq.u);
            args.add("-cp");
            args.add(d());
            args.add("-proc:none");
            args.add(yq.y);
            args.add(yq.v);
            String pathJava = fpu.getPathJava(yq.b);
            if (FileUtil.isExistFile(pathJava)) {
                args.add(pathJava);
            }
            String pathBroadcast = fpu.getPathBroadcast(yq.b);
            if (FileUtil.isExistFile(pathBroadcast)) {
                args.add(pathBroadcast);
            }
            String pathService = fpu.getPathService(yq.b);
            if (FileUtil.isExistFile(pathService)) {
                args.add(pathService);
            }

            /* Avoid "package ;" line in that file causing issues while compiling */
            File rJavaFileWithoutPackage = new File(yq.v, "R.java");
            if (rJavaFileWithoutPackage.exists() && !rJavaFileWithoutPackage.delete()) {
                LogUtil.w(TAG, "Failed to delete file " + rJavaFileWithoutPackage.getAbsolutePath());
            }

            /* Start compiling */
            org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false, null, null);
            LogUtil.d(TAG, "Running Eclipse compiler with these arguments: " + args);
            main.compile(args.toArray(new String[0]));

            LogUtil.d(TAG, "System.out of Eclipse compiler: " + outOutputStream.getOut());
            if (main.globalErrorsCount <= 0) {
                LogUtil.d(TAG, "System.err of Eclipse compiler: " + errOutputStream.getOut());
                LogUtil.d(TAG, "Compiling Java files took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            } else {
                LogUtil.e(TAG, "Failed to compile Java files");
                throw new zy(errOutputStream.getOut());
            }
        }
    }

    /**
     * Builds an APK, used when clicking "Run" in DesignActivity
     */
    public void g() {
        String firstDexPath = dexesToAddButNotMerge.isEmpty() ? yq.E : dexesToAddButNotMerge.remove(0);

        ApkBuilder apkBuilder = new ApkBuilder(new File(yq.G), new File(yq.C), new File(firstDexPath), null, null, System.out);

        for (Jp library : builtInLibraryManager.a()) {
            apkBuilder.addResourcesFromJar(BuiltInLibraries.getLibraryClassesJarPath(library.a()));
        }

        for (String jarPath : mll.getJarLocalLibrary().split(":")) {
            if (!jarPath.trim().isEmpty()) {
                apkBuilder.addResourcesFromJar(new File(jarPath));
            }
        }

        /* Add project's native libraries */
        File nativeLibrariesDirectory = new File(fpu.getPathNativelibs(yq.b));
        if (nativeLibrariesDirectory.exists()) {
            apkBuilder.addNativeLibraries(nativeLibrariesDirectory);
        }

        /* Add Local libraries' native libraries */
        for (String nativeLibraryDirectory : mll.getNativeLibs()) {
            apkBuilder.addNativeLibraries(new File(nativeLibraryDirectory));
        }

        if (dexesToAddButNotMerge.isEmpty()) {
            List<String> dexFiles = FileUtil.listFiles(yq.t, "dex");
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
            FileUtil.listDir(yq.classes_proguard, arrayList);
            if (arrayList.size() > 0) {
                return yq.classes_proguard;
            }
        }
        return yq.u;
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
            dexes.add(BuiltInLibraries.getLibraryDexFilePath(BuiltInLibraries.ANDROIDX_MULTIDEX));
        }

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, ProjectSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(ProjectSettings.SETTING_GENERIC_VALUE_TRUE)) {
            dexes.add(BuiltInLibraries.getLibraryDexFilePath(BuiltInLibraries.HTTP_LEGACY_ANDROID_28));
        }

        /* Add used built-in libraries' DEX files */
        for (Jp builtInLibrary : builtInLibraryManager.a()) {
            dexes.add(BuiltInLibraries.getLibraryDexFilePath(builtInLibrary.a()));
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

        dexes.addAll(FileUtil.listFiles(yq.t + File.separator + "dex", "dex"));

        LogUtil.d(TAG, "Will merge these " + dexes.size() + " DEX files to classes.dex: " + dexes);

        if (settings.getMinSdkVersion() < 21 || !yq.N.f) {
            dexLibraries(yq.E, dexes);
        } else {
            dexesToAddButNotMerge = dexes;
        }

        LogUtil.d(TAG, "Merging project DEX file(s) and libraries' took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    /**
     * Extracts AAPT2 binaries (if they need to be extracted).
     *
     * @throws By If anything goes wrong while extracting
     */
    public void i() throws By {
        String aapt2PathInAssets = "aapt/";
        if (GB.a().toLowerCase().contains("x86")) {
            aapt2PathInAssets += "aapt2-x86";
        } else {
            aapt2PathInAssets += "aapt2-arm";
        }
        try {
            /* Check if we need to update AAPT2's binary */
            if (a(aapt2PathInAssets, aapt2Binary.getAbsolutePath())) {
                makeExecutableCommand[2] = aapt2Binary.getAbsolutePath();
                commandExecutor.a(makeExecutableCommand);
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
        if (!fileUtil.e(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath())) {
            fileUtil.f(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath());
        }
        String androidJarArchiveName = "android.jar.zip";
        String dexsArchiveName = "dexs.zip";
        String coreLambdaStubsJarName = "core-lambda-stubs.jar";
        String libsArchiveName = "libs.zip";
        String testkeyArchiveName = "testkey.zip";

        String androidJarPath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, androidJarArchiveName).getAbsolutePath();
        String dexsArchivePath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, dexsArchiveName).getAbsolutePath();
        String coreLambdaStubsJarPath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, coreLambdaStubsJarName).getAbsolutePath();
        String libsArchivePath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, libsArchiveName).getAbsolutePath();
        String testkeyArchivePath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, testkeyArchiveName).getAbsolutePath();
        String dexsDirectoryPath = BuiltInLibraries.EXTRACTED_BUILT_IN_LIBRARY_DEX_FILES_PATH.getAbsolutePath();
        String libsDirectoryPath = BuiltInLibraries.EXTRACTED_BUILT_IN_LIBRARIES_PATH.getAbsolutePath();
        String testkeyDirectoryPath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "testkey").getAbsolutePath();

        /* If necessary, update android.jar.zip */
        String baseAssetsPath = "libs" + File.separator;
        if (a(baseAssetsPath + androidJarArchiveName, androidJarPath)) {
            if (buildingDialog != null) {
                buildingDialog.setProgress("Extracting built-in android.jar...");
            }
            /* Delete android.jar */
            fileUtil.c(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath() + File.separator + "android.jar");
            /* Extract android.jar.zip to android.jar */
            new KB().a(androidJarPath, BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath());
        }
        /* If necessary, update dexs.zip */
        if (a(baseAssetsPath + dexsArchiveName, dexsArchivePath)) {
            if (buildingDialog != null) {
                buildingDialog.setProgress("Extracting built-in libraries' DEX files...");
            }
            /* Delete the directory */
            fileUtil.b(dexsDirectoryPath);
            /* Create the directories */
            fileUtil.f(dexsDirectoryPath);
            /* Extract dexs.zip to dexs/ */
            new KB().a(dexsArchivePath, dexsDirectoryPath);
        }
        /* If necessary, update libs.zip */
        if (a(baseAssetsPath + libsArchiveName, libsArchivePath)) {
            if (buildingDialog != null) {
                buildingDialog.setProgress("Extracting built-in libraries' resources...");
            }
            /* Delete the directory */
            fileUtil.b(libsDirectoryPath);
            /* Create the directories */
            fileUtil.f(libsDirectoryPath);
            /* Extract libs.zip to libs/ */
            new KB().a(libsArchivePath, libsDirectoryPath);
        }
        /* If necessary, update core-lambda-stubs.jar */
        a(baseAssetsPath + coreLambdaStubsJarName, coreLambdaStubsJarPath);
        /* If necessary, update testkey.zip */
        if (a(baseAssetsPath + testkeyArchiveName, testkeyArchivePath)) {
            if (buildingDialog != null) {
                buildingDialog.setProgress("Extracting built-in signing keys...");
            }
            /* Delete the directory */
            fileUtil.b(testkeyDirectoryPath);
            /* Create the directories */
            fileUtil.f(testkeyDirectoryPath);
            /* Extract testkey.zip to testkey/ */
            new KB().a(testkeyArchivePath, testkeyDirectoryPath);
        }
        if (yq.N.g) {
            builtInLibraryManager.a(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            builtInLibraryManager.a(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT);
            builtInLibraryManager.a(BuiltInLibraries.MATERIAL);
        }
        if (yq.N.h) {
            builtInLibraryManager.a(BuiltInLibraries.FIREBASE_COMMON);
        }
        if (yq.N.i) {
            builtInLibraryManager.a(BuiltInLibraries.FIREBASE_AUTH);
        }
        if (yq.N.j) {
            builtInLibraryManager.a(BuiltInLibraries.FIREBASE_DATABASE);
        }
        if (yq.N.k) {
            builtInLibraryManager.a(BuiltInLibraries.FIREBASE_STORAGE);
        }
        if (yq.N.m) {
            builtInLibraryManager.a(BuiltInLibraries.PLAY_SERVICES_MAPS);
        }
        if (yq.N.l) {
            builtInLibraryManager.a(BuiltInLibraries.PLAY_SERVICES_ADS);
        }
        if (yq.N.o) {
            builtInLibraryManager.a(BuiltInLibraries.GSON);
        }
        if (yq.N.n) {
            builtInLibraryManager.a(BuiltInLibraries.GLIDE);
        }
        if (yq.N.p) {
            builtInLibraryManager.a(BuiltInLibraries.OKHTTP);
        }
        if (yq.N.isDynamicLinkUsed) {
            builtInLibraryManager.a(BuiltInLibraries.FIREBASE_DYNAMIC_LINKS);
        }
        ExtLibSelected.addUsedDependencies(yq.N.x, builtInLibraryManager);
    }

    /**
     * Sign the APK file with testkey.
     * This method supports APK Signature Scheme V1 (JAR signing) only.
     */
    public void k() throws GeneralSecurityException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ZipSigner zipSigner = new ZipSigner();
        KeyStoreFileManager.setProvider(new BouncyCastleProvider());
        zipSigner.setKeymode(ZipSigner.KEY_TESTKEY);
        zipSigner.signZip(yq.G, yq.H);
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
        for (Jp library : builtInLibraryManager.a()) {
            File config = BuiltInLibraries.getLibraryProGuardConfiguration(library.a());
            if (config.exists()) {
                args.add("-include");
                args.add(config.getAbsolutePath());
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
        for (Jp jp : builtInLibraryManager.a()) {
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
        FileUtil.writeFile(yq.rules_generated, sb.toString());
        args.add("-include");
        args.add(yq.rules_generated);
    }

    public void runProguard() throws IOException {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();

        /* Include global ProGuard rules */
        args.add("-include");
        args.add(ProguardHandler.ANDROID_PROGUARD_RULES_PATH);

        /* Include ProGuard rules generated by AAPT2 */
        args.add("-include");
        args.add(yq.aapt_rules);

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
        args.add(yq.u);

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
        args.add(yq.classes_proguard);
        if (proguard.isDebugFilesEnabled()) {
            args.add("-printseeds");
            args.add(yq.printseeds);
            args.add("-printusage");
            args.add(yq.printusage);
            args.add("-printmapping");
            args.add(yq.printmapping);
        }
        LogUtil.d(TAG, "About to run ProGuard with these arguments: " + args);

        Configuration configuration = new Configuration();
        ConfigurationParser parser = new ConfigurationParser(args.toArray(new String[0]), System.getProperties());

        try {
            parser.parse(configuration);
        } catch (ParseException e) {
            throw new IOException(e);
        } finally {
            parser.close();
        }

        new ProGuard(configuration).execute();

        LogUtil.d(TAG, "ProGuard took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void runStringfog() {
        try {
            StringFogMappingPrinter stringFogMappingPrinter = new StringFogMappingPrinter(new File(yq.t,
                    "stringFogMapping.txt"));
            StringFogClassInjector stringFogClassInjector = new StringFogClassInjector(new String[0],
                    "UTF-8",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    stringFogMappingPrinter);
            stringFogMappingPrinter.startMappingOutput();
            stringFogMappingPrinter.ouputInfo("UTF-8", "com.github.megatronking.stringfog.xor.StringFogImpl");
            stringFogClassInjector.doFog2ClassInDir(new File(yq.u));
            KB.a(context, "stringfog/stringfog.zip", yq.u);
        } catch (Exception e) {
            LogUtil.e("Stringfog", e.toString());
        }
    }

    /**
     * Calls {@link #runZipalign(String, String)} with {@link yq#G} and {@link yq#alignedApkPath}.
     */
    public void runZipalign() throws By {
        runZipalign(yq.G, yq.alignedApkPath);
    }

    public void runZipalign(String inPath, String outPath) throws By {
        LogUtil.d(TAG, "About to zipalign " + inPath + " to " + outPath);
        long savedTimeMillis = System.currentTimeMillis();

        try (RandomAccessFile in = new RandomAccessFile(inPath, "r");
             FileOutputStream out = new FileOutputStream(outPath)) {
            ZipAlign.alignZip(in, out);
        } catch (IOException e) {
            throw new By("Couldn't run zipalign on " + inPath + " with output path " + outPath + ": " + Log.getStackTraceString(e));
        } catch (InvalidZipException e) {
            throw new By("Failed to zipalign due to the given zip being invalid: " + Log.getStackTraceString(e));
        }

        LogUtil.d(TAG, "zipalign took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

}
