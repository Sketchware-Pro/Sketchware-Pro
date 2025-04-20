package a.a.a;

import static android.system.OsConstants.S_IRUSR;
import static android.system.OsConstants.S_IWUSR;
import static android.system.OsConstants.S_IXUSR;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.system.Os;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.android.sdklib.build.ApkBuilder;
import com.android.sdklib.build.ApkCreationException;
import com.android.sdklib.build.DuplicateFileException;
import com.android.sdklib.build.SealedApkException;
import com.github.megatronking.stringfog.plugin.StringFogClassInjector;
import com.github.megatronking.stringfog.plugin.StringFogMappingPrinter;
import com.iyxan23.zipalignjava.InvalidZipException;
import com.iyxan23.zipalignjava.ZipAlign;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.compiler.kotlin.KotlinCompilerBridge;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.util.SystemLogPrinter;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.build.compiler.dex.DexCompiler;
import mod.jbk.build.compiler.resource.ResourceCompiler;
import mod.jbk.util.LogUtil;
import mod.jbk.util.TestkeySignBridge;
import mod.pranav.build.JarBuilder;
import mod.pranav.build.R8Compiler;
import mod.pranav.viewbinding.ViewBindingBuilder;
import pro.sketchware.SketchApplication;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ProjectBuilder {
    public static final String TAG = "AppBuilder";

    private final File aapt2Binary;
    private final Context context;
    public BuildSettings build_settings;
    public yq yq;
    public FilePathUtil fpu;
    public ManageLocalLibrary mll;
    public BuiltInLibraryManager builtInLibraryManager;
    public String androidJarPath;
    public ProguardHandler proguard;
    public ProjectSettings settings;
    private BuildProgressReceiver progressReceiver;
    private boolean buildAppBundle = false;
    private ArrayList<File> dexesToAddButNotMerge = new ArrayList<>();

    /**
     * Timestamp keeping track of when compiling the project's resources started, needed for stats of how long compiling took.
     */
    private long timestampResourceCompilationStarted;

    public ProjectBuilder(Context context, yq yqVar) {
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
        build_settings = new BuildSettings(yqVar.sc_id);
        this.context = context;
        yq = yqVar;
        fpu = new FilePathUtil();
        mll = new ManageLocalLibrary(yqVar.sc_id);
        builtInLibraryManager = new BuiltInLibraryManager(yqVar.sc_id);
        File defaultAndroidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        androidJarPath = build_settings.getValue(BuildSettings.SETTING_ANDROID_JAR_PATH, defaultAndroidJar.getAbsolutePath());
        proguard = new ProguardHandler(yqVar.sc_id);
        settings = new ProjectSettings(yqVar.sc_id);
    }

    public ProjectBuilder(BuildProgressReceiver buildAsyncTask, Context context, yq yqVar) {
        this(context, yqVar);
        progressReceiver = buildAsyncTask;
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
    public static boolean hasFileChanged(String fileInAssets, String targetFile) {
        long length;
        File compareToFile = new File(targetFile);
        oB fileUtil = new oB();
        long lengthOfFileInAssets = fileUtil.a(SketchApplication.getContext(), fileInAssets);
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
        fileUtil.a(SketchApplication.getContext(), fileInAssets, targetFile);
        return true;
    }

    /**
     * Compile resources and log time needed.
     *
     * @throws Exception Thrown when anything goes wrong while compiling resources
     */
    public void compileResources() throws Exception {
        timestampResourceCompilationStarted = System.currentTimeMillis();
        ResourceCompiler compiler = new ResourceCompiler(
                this,
                aapt2Binary,
                buildAppBundle,
                progressReceiver);
        compiler.compile();
        LogUtil.d(TAG, "Compiling resources took " + (System.currentTimeMillis() - timestampResourceCompilationStarted) + " ms");
    }

    public void generateViewBinding() throws IOException, SAXException {
        if (settings.getValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, ProjectSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(ProjectSettings.SETTING_GENERIC_VALUE_FALSE)) {
            return;
        }
        File outputDirectory = new File(yq.javaFilesPath + File.separator + yq.packageName.replace(".", File.separator) + File.separator + "databinding");
        outputDirectory.mkdirs();

        List<File> layouts = FileUtil.listFiles(yq.layoutFilesPath, "xml").stream()
                .map(File::new)
                .collect(Collectors.toList());

        ViewBindingBuilder builder = new ViewBindingBuilder(layouts, outputDirectory, yq.packageName);

        builder.generateBindings();
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
     * @throws Exception Thrown if the compiler had any problems compiling
     */
    public void createDexFilesFromClasses() throws Exception {
        FileUtil.makeDir(yq.binDirectoryPath + File.separator + "dex");
        if (proguard.isShrinkingEnabled()) return;

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
                    "--output=" + yq.binDirectoryPath + File.separator + "dex",
                    proguard.isShrinkingEnabled() ? yq.proguardClassesPath : yq.compiledClassesPath
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

    public String getClasspath() {
        StringBuilder classpath = new StringBuilder();

        /*
         * Add yq#u (.sketchware/mysc/xxx/bin/classes) if it exists
         * since there might be compiled Kotlin files for ecj to use classpath as.
         */
        KotlinCompilerBridge.maybeAddKotlinFilesToClasspath(classpath, yq);

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

        /*
         * Add lambda helper classes
         * Since all versions above java 7 supports lambdas, this should work
         */
        if (!build_settings.getValue(BuildSettings.SETTING_JAVA_VERSION,
                        BuildSettings.SETTING_JAVA_VERSION_1_7)
                .equals(BuildSettings.SETTING_JAVA_VERSION_1_7)) {
            classpath.append(":").append(new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "core-lambda-stubs.jar").getAbsolutePath());
        }

        /* Add used built-in libraries to the classpath */
        for (Jp library : builtInLibraryManager.getLibraries()) {
            classpath.append(":").append(BuiltInLibraries.getLibraryClassesJarPathString(library.getName()));
        }

        /* Add local libraries to the classpath */
        classpath.append(mll.getJarLocalLibrary());

        /* Append user's custom classpath */
        if (!build_settings.getValue(BuildSettings.SETTING_CLASSPATH, "").isEmpty()) {
            classpath.append(":").append(build_settings.getValue(BuildSettings.SETTING_CLASSPATH, ""));
        }

        /* Add JARs from project's classpath */
        String path = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + yq.sc_id + "/files/classpath/";
        ArrayList<String> jars = FileUtil.listFiles(path, "jar");
        classpath.append(":").append(TextUtils.join(":", jars));

        return classpath.toString();
    }

    /**
     * @return Similar to {@link ProjectBuilder#getClasspath()}, but doesn't return some local libraries' JARs if ProGuard full mode is enabled
     */
    public String getProguardClasspath() {
        Collection<String> localLibraryJarsWithFullModeOn = new LinkedList<>();

        for (HashMap<String, Object> localLibrary : mll.list) {
            Object nameObject = localLibrary.get("name");
            Object jarPathObject = localLibrary.get("jarPath");

            if (nameObject instanceof String name && jarPathObject instanceof String jarPath) {

                if (localLibrary.containsKey("jarPath") && proguard.libIsProguardFMEnabled(name)) {
                    localLibraryJarsWithFullModeOn.add(jarPath);
                }
            }
        }

        String normalClasspath = getClasspath();
        StringBuilder classpath = new StringBuilder();
        normalClasspathLoop:
        for (String classpathPart : normalClasspath.split(":")) {
            for (String jarPathToExclude : localLibraryJarsWithFullModeOn) {
                if (classpathPart.equals(jarPathToExclude)) {
                    localLibraryJarsWithFullModeOn.remove(jarPathToExclude);
                    continue normalClasspathLoop;
                }
            }

            if (!classpathPart.equals(yq.compiledClassesPath)) {
                classpath.append(classpathPart).append(':');
            }
        }

        // remove trailing delimiter
        classpath.deleteCharAt(classpath.length() - 1);

        return classpath.toString();
    }

    /**
     * Dexes libraries.
     *
     * @return List of result DEX files which were merged or couldn't be merged with others.
     * @throws Exception Thrown if merging had problems
     */
    private Collection<File> dexLibraries(File outputDirectory, List<File> dexes) throws Exception {
        int lastDexNumber = 1;
        String nextMergedDexFilename;
        Collection<File> resultDexFiles = new LinkedList<>();
        LinkedList<Dex> dexObjects = new LinkedList<>();
        Iterator<File> toMergeIterator = dexes.iterator();

        List<FieldId> mergedDexFields;
        List<MethodId> mergedDexMethods;
        List<ProtoId> mergedDexProtos;
        List<Integer> mergedDexTypes;

        {
            // Closable gets closed automatically
            Dex firstDex = new Dex(new FileInputStream(toMergeIterator.next()));
            dexObjects.add(firstDex);
            mergedDexFields = new LinkedList<>(firstDex.fieldIds());
            mergedDexMethods = new LinkedList<>(firstDex.methodIds());
            mergedDexProtos = new LinkedList<>(firstDex.protoIds());
            mergedDexTypes = new LinkedList<>(firstDex.typeIds());
        }

        while (toMergeIterator.hasNext()) {
            File dexFile = toMergeIterator.next();
            nextMergedDexFilename = lastDexNumber == 1 ? "classes.dex" : "classes" + lastDexNumber + ".dex";

            // Closable gets closed automatically
            Dex dex = new Dex(new FileInputStream(dexFile));

            boolean canMerge = true;
            List<FieldId> newDexFieldIds = new LinkedList<>();
            List<MethodId> newDexMethodIds = new LinkedList<>();
            List<ProtoId> newDexProtoIds = new LinkedList<>();
            List<Integer> newDexTypeIds = new LinkedList<>();

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
                LogUtil.d(TAG, "Merging DEX #" + dexes.indexOf(dexFile) + " as well to " + nextMergedDexFilename);
                dexObjects.add(dex);
                mergedDexFields.addAll(newDexFieldIds);
                mergedDexMethods.addAll(newDexMethodIds);
                mergedDexProtos.addAll(newDexProtoIds);
                mergedDexTypes.addAll(newDexTypeIds);
            } else {
                File target = new File(outputDirectory, nextMergedDexFilename);
                mergeDexes(target, dexObjects);
                resultDexFiles.add(target);
                dexObjects.clear();
                dexObjects.add(dex);

                mergedDexFields = new ArrayList<>(dex.fieldIds());
                mergedDexMethods = new ArrayList<>(dex.methodIds());
                mergedDexProtos = new ArrayList<>(dex.protoIds());
                mergedDexTypes = new ArrayList<>(dex.typeIds());
                lastDexNumber++;
            }
        }
        if (!dexObjects.isEmpty()) {
            File file = new File(outputDirectory, lastDexNumber == 1 ? "classes.dex" : "classes" + lastDexNumber + ".dex");
            mergeDexes(file, dexObjects);
            resultDexFiles.add(file);
        }

        return resultDexFiles;
    }

    /**
     * Get package names of in-use libraries which have resources, separated by <code>:</code>.
     */
    public String getLibraryPackageNames() {
        StringBuilder extraPackages = new StringBuilder();
        for (Jp library : builtInLibraryManager.getLibraries()) {
            if (library.hasResources()) {
                extraPackages.append(library.getPackageName()).append(":");
            }
        }
        return extraPackages + mll.getPackageNameLocalLibrary();
    }

    /**
     * Run Eclipse Compiler to compile Java files.
     */
    public void compileJavaCode() throws zy, IOException {
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
            if (!build_settings.getValue(BuildSettings.SETTING_NO_WARNINGS,
                    BuildSettings.SETTING_GENERIC_VALUE_TRUE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
                args.add("-deprecation");
            }
            args.add("-d");
            args.add(yq.compiledClassesPath);
            args.add("-cp");
            args.add(getClasspath());
            args.add("-proc:none");
            args.add(yq.javaFilesPath);
            args.add(yq.rJavaDirectoryPath);
            String pathJava = fpu.getPathJava(yq.sc_id);
            if (FileUtil.isExistFile(pathJava)) {
                args.add(pathJava);
            }
            String pathBroadcast = fpu.getPathBroadcast(yq.sc_id);
            if (FileUtil.isExistFile(pathBroadcast)) {
                args.add(pathBroadcast);
            }
            String pathService = fpu.getPathService(yq.sc_id);
            if (FileUtil.isExistFile(pathService)) {
                args.add(pathService);
            }

            /* Avoid "package ;" line in that file causing issues while compiling */
            File rJavaFileWithoutPackage = new File(yq.rJavaDirectoryPath, "R.java");
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

    public void buildApk() throws By {
        String firstDexPath = dexesToAddButNotMerge.isEmpty() ? yq.classesDexPath : dexesToAddButNotMerge.remove(0).getAbsolutePath();
        try {
            ApkBuilder apkBuilder = new ApkBuilder(new File(yq.unsignedUnalignedApkPath), new File(yq.resourcesApkPath), new File(firstDexPath), null, null, System.out);

            for (Jp library : builtInLibraryManager.getLibraries()) {
                apkBuilder.addResourcesFromJar(BuiltInLibraries.getLibraryClassesJarPath(library.getName()));
            }

            for (String jarPath : mll.getJarLocalLibrary().split(":")) {
                if (!jarPath.trim().isEmpty()) {
                    apkBuilder.addResourcesFromJar(new File(jarPath));
                }
            }

            /* Add project's native libraries */
            File nativeLibrariesDirectory = new File(fpu.getPathNativelibs(yq.sc_id));
            if (nativeLibrariesDirectory.exists()) {
                apkBuilder.addNativeLibraries(nativeLibrariesDirectory);
            }

            /* Add Local libraries' native libraries */
            for (String nativeLibraryDirectory : mll.getNativeLibs()) {
                apkBuilder.addNativeLibraries(new File(nativeLibraryDirectory));
            }

            if (dexesToAddButNotMerge.isEmpty()) {
                List<String> dexFiles = FileUtil.listFiles(yq.binDirectoryPath, "dex");
                for (String dexFile : dexFiles) {
                    if (!Uri.fromFile(new File(dexFile)).getLastPathSegment().equals("classes.dex")) {
                        apkBuilder.addFile(new File(dexFile), Uri.parse(dexFile).getLastPathSegment());
                    }
                }
            } else {
                int dexNumber = 2;

                for (File dexFile : dexesToAddButNotMerge) {
                    apkBuilder.addFile(dexFile, "classes" + dexNumber + ".dex");
                    dexNumber++;
                }
            }

            apkBuilder.setDebugMode(false);
            apkBuilder.sealApk();
        } catch (ApkCreationException | SealedApkException e) {
            throw new By(e.getMessage());
        } catch (DuplicateFileException e) {
            String message = "Duplicate files from two libraries detected \r\n";
            message += "File1: " + e.getFile1() + " \r\n";
            message += "File2: " + e.getFile2() + " \r\n";
            message += "Archive path: " + e.getArchivePath();
            throw new By(message);
        }
        LogUtil.d(TAG, "Time passed since starting to compile resources until building the unsigned APK: " +
                (System.currentTimeMillis() - timestampResourceCompilationStarted) + " ms");
    }

    /**
     * Either merges DEX files to as few as possible, or adds list of DEX files to add to the APK to
     * {@link #dexesToAddButNotMerge}.
     * <p>
     * Will merge DEX files if either the project's minSdkVersion is lower than 21, or if {@link jq#isDebugBuild}
     * of {@link yq#N} in {@link #yq} is false.
     *
     * @throws Exception Thrown if merging failed
     */
    public void getDexFilesReady() throws Exception {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<File> dexes = new ArrayList<>();

        /* Add AndroidX MultiDex library if needed */
        if (settings.getMinSdkVersion() < 21) {
            dexes.add(BuiltInLibraries.getLibraryDexFile(BuiltInLibraries.ANDROIDX_MULTIDEX));
        }

        /* Add HTTP legacy files if wanted */
        if (!build_settings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, ProjectSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(ProjectSettings.SETTING_GENERIC_VALUE_TRUE)) {
            dexes.add(BuiltInLibraries.getLibraryDexFile(BuiltInLibraries.HTTP_LEGACY_ANDROID_28));
        }

        /* Add used built-in libraries' DEX files */
        for (Jp builtInLibrary : builtInLibraryManager.getLibraries()) {
            dexes.add(BuiltInLibraries.getLibraryDexFile(builtInLibrary.getName()));
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
                        dexes.add(new File((String) localLibraryDexPath));
                        /* Add library's extra DEX files */
                        File localLibraryDirectory = new File((String) localLibraryDexPath).getParentFile();

                        if (localLibraryDirectory != null) {
                            File[] localLibraryFiles = localLibraryDirectory.listFiles();

                            if (localLibraryFiles != null) {
                                for (File localLibraryFile : localLibraryFiles) {
                                    String filename = localLibraryFile.getName();

                                    if (!filename.equals("classes.dex")
                                            && filename.startsWith("classes") && filename.endsWith(".dex")) {
                                        dexes.add(localLibraryFile);
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

        for (String file : FileUtil.listFiles(yq.binDirectoryPath + File.separator + "dex", "dex")) {
            dexes.add(new File(file));
        }

        LogUtil.d(TAG, "Will merge these " + dexes.size() + " DEX files to classes.dex: " + dexes);

        if (settings.getMinSdkVersion() < 21 || !yq.N.isDebugBuild) {
            dexLibraries(new File(yq.binDirectoryPath), dexes);
            LogUtil.d(TAG, "Merging DEX files took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        } else {
            dexesToAddButNotMerge = dexes;
            LogUtil.d(TAG, "Skipped merging DEX files due to debug build with minSdkVersion >= 21");
        }
    }

    /**
     * Extracts AAPT2 binaries (if they need to be extracted).
     *
     * @throws By If anything goes wrong while extracting
     */
    public void maybeExtractAapt2() throws By {
        var abi = Build.SUPPORTED_ABIS[0];
        try {
            if (hasFileChanged("aapt/aapt2-" + abi, aapt2Binary.getAbsolutePath())) {
                Os.chmod(aapt2Binary.getAbsolutePath(), S_IRUSR | S_IWUSR | S_IXUSR);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "Failed to extract AAPT2 binaries", e);
            // noinspection ConstantValue: the bytecode's lying
            throw new By(
                    e instanceof FileNotFoundException fileNotFoundException ?
                            "Looks like the device's architecture (" + abi + ") isn't supported.\n"
                                    + Log.getStackTraceString(fileNotFoundException)
                            : "Couldn't extract AAPT2 binaries! Message: " + e.getMessage()
            );
        }
    }

    /**
     * Checks if we need to extract any library/dependency from assets to filesDir,
     * and extracts them, if needed. Also initializes used built-in libraries.
     */
    public void buildBuiltInLibraryInformation() {
        if (yq.N.g) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            builtInLibraryManager.addLibrary(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT);
            builtInLibraryManager.addLibrary(BuiltInLibraries.MATERIAL);
        }
        if (yq.N.isFirebaseEnabled) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.FIREBASE_COMMON);
        }
        if (yq.N.isFirebaseAuthUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.FIREBASE_AUTH);
        }
        if (yq.N.isFirebaseDatabaseUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.FIREBASE_DATABASE);
        }
        if (yq.N.isFirebaseStorageUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.FIREBASE_STORAGE);
        }
        if (yq.N.isMapUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.PLAY_SERVICES_MAPS);
        }
        if (yq.N.isAdMobEnabled) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.PLAY_SERVICES_ADS);
        }
        if (yq.N.isGsonUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.GSON);
        }
        if (yq.N.isGlideUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.GLIDE);
        }
        if (yq.N.isHttp3Used) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.OKHTTP);
        }
        if (yq.N.isDynamicLinkUsed) {
            builtInLibraryManager.addLibrary(BuiltInLibraries.FIREBASE_DYNAMIC_LINKS);
        }

        KotlinCompilerBridge.maybeAddKotlinBuiltInLibraryDependenciesIfPossible(this, builtInLibraryManager);

        ExtLibSelected.addUsedDependencies(yq.N.x, builtInLibraryManager);
    }

    public BuiltInLibraryManager getBuiltInLibraryManager() {
        return builtInLibraryManager;
    }

    /**
     * Sign the debug APK file with testkey.
     * <p>
     * This method uses apksigner, but kellinwood's zipsigner as fallback.
     */
    public void signDebugApk() throws GeneralSecurityException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        TestkeySignBridge.signWithTestkey(yq.unsignedUnalignedApkPath, yq.finalToInstallApkPath);
    }

    private void mergeDexes(File target, List<Dex> dexes) throws IOException {
        DexMerger merger = new DexMerger(dexes.toArray(new Dex[0]), CollisionPolicy.KEEP_FIRST, new DxContext());
        merger.merge().writeTo(target);
    }

    private String getRJavaRules() {
        StringBuilder sb = new StringBuilder("# R.java rules");
        for (Jp jp : builtInLibraryManager.getLibraries()) {
            if (jp.hasResources() && !jp.getPackageName().isEmpty()) {
                sb.append("\n");
                sb.append("-keep class ");
                sb.append(jp.getPackageName());
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
        sb.append("-keep class ").append(yq.packageName).append(".R { *; }").append('\n');
        return sb.toString();
    }

    public void runR8() throws IOException {
        long savedTimeMillis = System.currentTimeMillis();

        ArrayList<String> config = new ArrayList<>();
        config.add(ProguardHandler.ANDROID_PROGUARD_RULES_PATH);
        config.add(yq.proguardAaptRules);
        config.add(proguard.getCustomProguardRules());
        var rules = new ArrayList<>(Arrays.asList(getRJavaRules().split("\n")));
        for (Jp library : builtInLibraryManager.getLibraries()) {
            File f = BuiltInLibraries.getLibraryProguardConfiguration(library.getName());
            if (f.exists()) {
                config.add(f.getAbsolutePath());
            }
        }
        config.addAll(mll.getPgRules());
        ArrayList<String> jars = new ArrayList<>();
        jars.add(yq.compiledClassesPath + ".jar");

        for (HashMap<String, Object> hashMap : mll.list) {
            String obj = hashMap.get("name").toString();
            if (hashMap.containsKey("jarPath") && proguard.libIsProguardFMEnabled(obj)) {
                jars.add(hashMap.get("jarPath").toString());
            }
        }
        try {
            JarBuilder.INSTANCE.generateJar(new File(yq.compiledClassesPath));
            new R8Compiler(rules, config.toArray(new String[0]), getProguardClasspath().split(":"), jars.toArray(new String[0]), settings.getMinSdkVersion(), yq).compile();
        } catch (Exception e) {
            throw new IOException(e);
        }
        LogUtil.d(TAG, "R8 took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void runStringfog() {
        try {
            StringFogMappingPrinter stringFogMappingPrinter = new StringFogMappingPrinter(new File(yq.binDirectoryPath,
                    "stringFogMapping.txt"));
            StringFogClassInjector stringFogClassInjector = new StringFogClassInjector(new String[0],
                    "UTF-8",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    "com.github.megatronking.stringfog.xor.StringFogImpl",
                    stringFogMappingPrinter);
            stringFogMappingPrinter.startMappingOutput();
            stringFogMappingPrinter.ouputInfo("UTF-8", "com.github.megatronking.stringfog.xor.StringFogImpl");
            stringFogClassInjector.doFog2ClassInDir(new File(yq.compiledClassesPath));
            KB.a(context, "stringfog/stringfog.zip", yq.compiledClassesPath);
        } catch (Exception e) {
            LogUtil.e("StringFog", "Failed to run StringFog", e);
        }
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

    public void setBuildAppBundle(boolean buildAppBundle) {
        this.buildAppBundle = buildAppBundle;
    }
}
