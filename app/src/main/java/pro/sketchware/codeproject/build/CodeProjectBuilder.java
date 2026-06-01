package pro.sketchware.codeproject.build;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import mod.hey.studios.compiler.kotlin.DiagnosticCollector;
import mod.jbk.build.BuiltInLibraries;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler;
import org.jetbrains.kotlin.config.Services;
import pro.sketchware.codeproject.model.CodeProject;
import pro.sketchware.utility.BinaryExecutor;
import pro.sketchware.utility.FileUtil;

public class CodeProjectBuilder {

    private final Context context;
    private final CodeProject project;
    private final File binDir;
    private final File genDir;
    private final File classesDir;
    private final File dexDir;
    private File aapt2Binary;

    public CodeProjectBuilder(Context context, CodeProject project) {
        this.context = context;
        this.project = project;
        this.binDir = new File(project.getBinPath());
        this.genDir = new File(project.getGenPath());
        this.classesDir = new File(binDir, "classes");
        this.dexDir = new File(binDir, "dex");
    }

    public File build(BuildProgressListener listener) throws Exception {
        if (listener != null) listener.onProgress("Preparing...", 1);
        prepare();

        if (listener != null) listener.onProgress("Extracting AAPT2...", 2);
        extractAapt2();

        if (listener != null) listener.onProgress("Compiling resources...", 3);
        compileResources();

        if (listener != null) listener.onProgress("Compiling Kotlin...", 4);
        compileKotlin();

        if (listener != null) listener.onProgress("Compiling Java...", 5);
        compileJava();

        if (listener != null) listener.onProgress("Creating DEX...", 6);
        createDex();

        if (listener != null) listener.onProgress("Building APK...", 7);
        File apk = buildApk();

        if (listener != null) listener.onProgress("Signing APK...", 8);
        File signedApk = signApk(apk);

        if (listener != null) listener.onProgress("Done", 9);
        return signedApk;
    }

    private void prepare() {
        if (binDir.exists()) {
            FileUtil.deleteFile(binDir.getAbsolutePath());
        }
        binDir.mkdirs();
        classesDir.mkdirs();
        dexDir.mkdirs();
        genDir.mkdirs();
    }

    private void extractAapt2() throws Exception {
        String abi = Build.SUPPORTED_ABIS[0];
        String assetName = "aapt/aapt2-" + abi;
        aapt2Binary = new File(context.getFilesDir(), "aapt2");

        // Only re-extract if the file does not exist or has a different size than the asset
        long assetSize;
        try (InputStream sizeIs = context.getAssets().open(assetName)) {
            assetSize = 0;
            byte[] buf = new byte[8192];
            int n;
            while ((n = sizeIs.read(buf)) != -1) {
                assetSize += n;
            }
        }

        if (aapt2Binary.exists() && aapt2Binary.length() == assetSize && aapt2Binary.canExecute()) {
            return;
        }

        try (InputStream is = context.getAssets().open(assetName);
             FileOutputStream fos = new FileOutputStream(aapt2Binary)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        }
        aapt2Binary.setExecutable(true);
    }

    private void compileResources() throws Exception {
        File resDir = new File(project.getResPath());
        File compiledResDir = new File(binDir, "compiled_res");
        compiledResDir.mkdirs();

        // Compile individual resource files
        if (resDir.exists()) {
            File[] resDirs = resDir.listFiles();
            if (resDirs != null) {
                for (File dir : resDirs) {
                    if (dir.isDirectory()) {
                        File[] files = dir.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                if (file.isFile()) {
                                    ArrayList<String> args = new ArrayList<>();
                                    args.add(aapt2Binary.getAbsolutePath());
                                    args.add("compile");
                                    args.add("-o");
                                    args.add(compiledResDir.getAbsolutePath());
                                    args.add(file.getAbsolutePath());

                                    BinaryExecutor executor = new BinaryExecutor();
                                    executor.setCommands(args);
                                    String output = executor.execute();
                                    if (!output.isEmpty()) {
                                        throw new Exception("AAPT2 compile error: " + output);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Link resources
        File androidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        File outputApk = new File(binDir, "resources.apk");
        File manifestFile = new File(project.getManifestPath());

        ArrayList<String> args = new ArrayList<>();
        args.add(aapt2Binary.getAbsolutePath());
        args.add("link");
        args.add("-I");
        args.add(androidJar.getAbsolutePath());
        args.add("--manifest");
        args.add(manifestFile.getAbsolutePath());
        args.add("-o");
        args.add(outputApk.getAbsolutePath());
        args.add("--java");
        args.add(genDir.getAbsolutePath());
        args.add("--auto-add-overlay");

        // Add all compiled resources
        File[] compiledFiles = compiledResDir.listFiles();
        if (compiledFiles != null) {
            for (File f : compiledFiles) {
                args.add("-R");
                args.add(f.getAbsolutePath());
            }
        }

        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(args);
        String output = executor.execute();

        // Check if expected output file exists as a reliable indicator of success
        if (!outputApk.exists()) {
            String errorDetail = output.isEmpty() ? "AAPT2 link produced no output file" : output;
            throw new Exception("AAPT2 link error: " + errorDetail);
        }
    }

    private void compileKotlin() throws Exception {
        File sourceDir = new File(project.getKotlinSourcePath());
        List<File> ktFiles = new ArrayList<>();
        collectKotlinFiles(sourceDir, ktFiles);

        if (ktFiles.isEmpty()) return;

        File androidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        File lambdaStubs = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "core-lambda-stubs.jar");

        StringBuilder classpath = new StringBuilder();
        classpath.append(androidJar.getAbsolutePath());
        if (lambdaStubs.exists()) {
            classpath.append(":").append(lambdaStubs.getAbsolutePath());
        }

        List<String> arguments = new ArrayList<>();
        arguments.add("-cp");
        arguments.add(classpath.toString());

        for (File ktFile : ktFiles) {
            arguments.add(ktFile.getAbsolutePath());
        }

        K2JVMCompiler compiler = new K2JVMCompiler();
        DiagnosticCollector collector = new DiagnosticCollector();

        K2JVMCompilerArguments args = new K2JVMCompilerArguments();
        args.setCompileJava(false);
        args.setIncludeRuntime(false);
        args.setNoJdk(true);
        args.setNoReflect(true);
        args.setNoStdlib(true);
        args.setDestination(classesDir.getAbsolutePath());

        File kotlinHome = new File(binDir, "kotlin_home");
        kotlinHome.mkdirs();
        args.setKotlinHome(kotlinHome.getAbsolutePath());

        compiler.parseArguments(arguments.toArray(new String[0]), args);

        ExitCode exitCode = compiler.exec(collector, Services.EMPTY, args);

        // Clean up META-INF that kotlinc generates (causes D8 issues)
        File metaInf = new File(classesDir, "META-INF");
        if (metaInf.exists()) {
            FileUtil.deleteFile(metaInf.getAbsolutePath());
        }

        if (exitCode != ExitCode.OK) {
            throw new Exception("Kotlin compilation failed");
        }
    }

    private void collectKotlinFiles(File dir, List<File> ktFiles) {
        if (dir == null || !dir.exists()) return;
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                collectKotlinFiles(file, ktFiles);
            } else if (file.getName().endsWith(".kt")) {
                ktFiles.add(file);
            }
        }
    }

    private void compileJava() throws Exception {
        File androidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        File lambdaStubs = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "core-lambda-stubs.jar");

        StringBuilder classpath = new StringBuilder();
        classpath.append(androidJar.getAbsolutePath());
        if (lambdaStubs.exists()) {
            classpath.append(":").append(lambdaStubs.getAbsolutePath());
        }

        // Add compiled Kotlin classes to classpath so Java can reference them
        File[] classFiles = classesDir.listFiles();
        if (classesDir.exists() && classFiles != null && classFiles.length > 0) {
            classpath.append(":").append(classesDir.getAbsolutePath());
        }

        List<String> args = new ArrayList<>();
        args.add("-1.7");
        args.add("-nowarn");
        args.add("-d");
        args.add(classesDir.getAbsolutePath());
        args.add("-cp");
        args.add(classpath.toString());
        args.add("-proc:none");
        args.add(project.getJavaSourcePath());
        args.add(genDir.getAbsolutePath());

        org.eclipse.jdt.internal.compiler.batch.Main ecjMain =
                new org.eclipse.jdt.internal.compiler.batch.Main(
                        new java.io.PrintWriter(System.out),
                        new java.io.PrintWriter(System.err),
                        false, null, null);

        boolean success = ecjMain.compile(args.toArray(new String[0]));
        if (!success) {
            throw new Exception("Java compilation failed. Check source files for errors.");
        }
    }

    private void createDex() throws Exception {
        File outputDex = new File(dexDir, "classes.dex");

        java.util.Collection<java.nio.file.Path> programFiles = new java.util.LinkedList<>();
        for (File file : pro.sketchware.utility.FileUtil.listFilesRecursively(classesDir, ".class")) {
            programFiles.add(file.toPath());
        }

        java.util.Collection<java.nio.file.Path> libraryFiles = new java.util.LinkedList<>();
        File androidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        libraryFiles.add(androidJar.toPath());

        com.android.tools.r8.D8.run(com.android.tools.r8.D8Command.builder()
                .setMode(com.android.tools.r8.CompilationMode.RELEASE)
                .setIntermediate(true)
                .setMinApiLevel(26)
                .addLibraryFiles(libraryFiles)
                .setOutput(dexDir.toPath(), com.android.tools.r8.OutputMode.DexIndexed)
                .addProgramFiles(programFiles)
                .build());

        if (!outputDex.exists()) {
            throw new Exception("DEX creation failed. classes.dex not found.");
        }
    }

    @SuppressWarnings("unused")
    private void addClassFiles(File dir, List<String> args) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    addClassFiles(file, args);
                } else if (file.getName().endsWith(".class")) {
                    args.add(file.getAbsolutePath());
                }
            }
        }
    }

    private File buildApk() throws Exception {
        File resourcesApk = new File(binDir, "resources.apk");
        File unsignedApk = new File(binDir, "unsigned.apk");
        File dexFile = new File(dexDir, "classes.dex");

        // Build the unsigned APK by copying all entries from resources.apk
        // and adding classes.dex using Java zip APIs (no host zip binary needed)
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(resourcesApk));
             ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(unsignedApk))) {

            // Copy all existing entries from resources.apk
            ZipEntry entry;
            byte[] buffer = new byte[8192];
            while ((entry = zis.getNextEntry()) != null) {
                zos.putNextEntry(new ZipEntry(entry.getName()));
                int read;
                while ((read = zis.read(buffer)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
                zis.closeEntry();
            }

            // Add classes.dex
            zos.putNextEntry(new ZipEntry("classes.dex"));
            try (FileInputStream dexIn = new FileInputStream(dexFile)) {
                int read;
                while ((read = dexIn.read(buffer)) != -1) {
                    zos.write(buffer, 0, read);
                }
            }
            zos.closeEntry();
        }

        return unsignedApk;
    }

    private File signApk(File unsignedApk) throws Exception {
        File signedApk = new File(binDir, "signed.apk");
        mod.jbk.util.TestkeySignBridge.signWithTestkey(
                unsignedApk.getAbsolutePath(), signedApk.getAbsolutePath());
        return signedApk;
    }

    private void copyFile(File src, File dst) throws IOException {
        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dst)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
    }

    public interface BuildProgressListener {
        void onProgress(String message, int step);
    }
}
