package ide.sketchware.codeproject.build;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import ide.sketchware.codeproject.model.CodeProject;
import ide.sketchware.utility.BinaryExecutor;
import ide.sketchware.utility.FileUtil;

public class CodeProjectBuilder {

    private final Context context;
    private final CodeProject project;
    private final File binDir;
    private final File genDir;
    private final File classesDir;
    private final File dexDir;
    private File aapt2Binary;
    private List<File> cachedLibraryJars;
    private boolean usesKotlin = false;

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

                                    // Determine expected .flat artifact name
                                    String flatName = dir.getName() + "_" + file.getName().replaceFirst("\\.[^.]+$", "") + ".arsc.flat";
                                    if (!file.getName().endsWith(".xml")) {
                                        flatName = dir.getName() + "_" + file.getName() + ".flat";
                                    }
                                    File expectedFlat = new File(compiledResDir, flatName);

                                    // Only fail if no .flat artifact was produced (output may contain non-fatal warnings)
                                    if (!expectedFlat.exists()) {
                                        // Fallback: accept a .flat file whose basename exactly matches the resource stem
                                        boolean found = false;
                                        String stem = file.getName().replaceFirst("\\.[^.]+$", "");
                                        File[] flatFiles = compiledResDir.listFiles();
                                        if (flatFiles != null) {
                                            for (File f : flatFiles) {
                                                if (f.getName().endsWith(".flat")) {
                                                    // Extract the basename (everything before .arsc.flat or .flat)
                                                    String flatBase = f.getName()
                                                            .replace(".arsc.flat", "")
                                                            .replace(".flat", "");
                                                    // AAPT2 names flats as "dir_stem.arsc.flat" or "dir_stem.ext.flat"
                                                    // The stem we need to match is after the last underscore-separated dir prefix
                                                    String expectedPrefix = dir.getName() + "_" + stem;
                                                    if (flatBase.equals(expectedPrefix) || flatBase.startsWith(expectedPrefix + ".")) {
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (!found) {
                                            String errorDetail = output.isEmpty()
                                                    ? "No compiled output produced for " + file.getName()
                                                    : output;
                                            throw new Exception("AAPT2 compile error: " + errorDetail);
                                        }
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
        usesKotlin = true;

        File androidJar = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "android.jar");
        File lambdaStubs = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "core-lambda-stubs.jar");
        File kotlinStdlib = BuiltInLibraries.getLibraryClassesJarPath(BuiltInLibraries.JETBRAINS_KOTLIN_STDLIB);

        StringBuilder classpath = new StringBuilder();
        classpath.append(androidJar.getAbsolutePath());
        if (lambdaStubs.exists()) {
            classpath.append(":").append(lambdaStubs.getAbsolutePath());
        }
        // Kotlin stdlib must be on the classpath to resolve standard library symbols
        if (kotlinStdlib.exists()) {
            classpath.append(":").append(kotlinStdlib.getAbsolutePath());
        }
        for (File jar : getLibraryJars()) {
            classpath.append(":").append(jar.getAbsolutePath());
        }

        List<String> arguments = new ArrayList<>();
        arguments.add("-cp");
        arguments.add(classpath.toString());

        // Pass generated R sources and project Java sources as source roots so Kotlin
        // can resolve R and other project Java types. With setCompileJava(false) the
        // compiler reads these .java files for resolution only (it does not emit them).
        if (genDir.exists()) {
            arguments.add(genDir.getAbsolutePath());
        }
        // The Kotlin source dir is the same as the Java source dir; passing the
        // directory compiles all .kt files within it and reads sibling .java files.
        arguments.add(sourceDir.getAbsolutePath());

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
            throw new Exception("Kotlin compilation failed: " + collector.getDiagnostics(true));
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
        for (File jar : getLibraryJars()) {
            classpath.append(":").append(jar.getAbsolutePath());
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
        for (File file : ide.sketchware.utility.FileUtil.listFilesRecursively(classesDir, ".class")) {
            programFiles.add(file.toPath());
        }
        for (File jar : getLibraryJars()) {
            programFiles.add(jar.toPath());
        }
        // When the project uses Kotlin, the stdlib classes must be dexed and packaged
        // so kotlin runtime symbols are available at runtime.
        if (usesKotlin) {
            File kotlinStdlib = BuiltInLibraries.getLibraryClassesJarPath(BuiltInLibraries.JETBRAINS_KOTLIN_STDLIB);
            if (kotlinStdlib.exists()) {
                programFiles.add(kotlinStdlib.toPath());
            }
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

    private File buildApk() throws Exception {
        File resourcesApk = new File(binDir, "resources.apk");
        File unsignedApk = new File(binDir, "unsigned.apk");
        File alignedApk = new File(binDir, "aligned.apk");

        // Build the unsigned APK by copying all entries from resources.apk
        // and adding classes.dex using Java zip APIs (no host zip binary needed)
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(resourcesApk));
             ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(unsignedApk))) {

            // Copy all existing entries preserving compression method and metadata
            ZipEntry entry;
            byte[] buffer = new byte[8192];
            while ((entry = zis.getNextEntry()) != null) {
                ZipEntry outEntry = new ZipEntry(entry.getName());
                outEntry.setTime(entry.getTime());

                if (entry.getMethod() == ZipEntry.STORED) {
                    // For STORED entries, we must set size, compressed size, and CRC
                    outEntry.setMethod(ZipEntry.STORED);
                    outEntry.setSize(entry.getSize());
                    outEntry.setCompressedSize(entry.getCompressedSize());
                    outEntry.setCrc(entry.getCrc());
                } else {
                    outEntry.setMethod(ZipEntry.DEFLATED);
                }

                zos.putNextEntry(outEntry);
                int read;
                while ((read = zis.read(buffer)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
                zis.closeEntry();
            }

            // Add every dex file D8 produced (classes.dex, classes2.dex, ...).
            // D8 in DexIndexed mode may emit multiple dex files when method count is high.
            File[] dexFiles = dexDir.listFiles((dir, name) ->
                    name.startsWith("classes") && name.endsWith(".dex"));
            if (dexFiles == null || dexFiles.length == 0) {
                throw new Exception("No dex files found to package.");
            }
            java.util.Arrays.sort(dexFiles, java.util.Comparator.comparing(File::getName));
            for (File dex : dexFiles) {
                ZipEntry dexEntry = new ZipEntry(dex.getName());
                dexEntry.setMethod(ZipEntry.DEFLATED);
                zos.putNextEntry(dexEntry);
                try (FileInputStream dexIn = new FileInputStream(dex)) {
                    int read;
                    while ((read = dexIn.read(buffer)) != -1) {
                        zos.write(buffer, 0, read);
                    }
                }
                zos.closeEntry();
            }
        }

        // Zipalign the APK (4-byte alignment) for resources.arsc and uncompressed entries
        zipalign(unsignedApk, alignedApk);

        return alignedApk;
    }

    private void zipalign(File input, File output) throws Exception {
        // Use the bundled zipalign binary if available, otherwise skip alignment
        // (APK will still work, just slightly less efficient memory-mapping)
        File zipalignBinary = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "zipalign");
        if (!zipalignBinary.exists()) {
            // Fallback: just rename unaligned as aligned
            if (!input.renameTo(output)) {
                try (FileInputStream in = new FileInputStream(input);
                     FileOutputStream out = new FileOutputStream(output)) {
                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                }
            }
            return;
        }

        zipalignBinary.setExecutable(true);
        ArrayList<String> args = new ArrayList<>();
        args.add(zipalignBinary.getAbsolutePath());
        args.add("-f");
        args.add("4");
        args.add(input.getAbsolutePath());
        args.add(output.getAbsolutePath());

        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(args);
        executor.execute();

        if (!output.exists()) {
            // Zipalign failed silently; use unaligned APK
            if (!input.renameTo(output)) {
                try (FileInputStream in = new FileInputStream(input);
                     FileOutputStream out = new FileOutputStream(output)) {
                    byte[] buffer = new byte[8192];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                }
            }
        }
    }

    private File signApk(File apk) throws Exception {
        File signedApk = new File(binDir, "signed.apk");
        mod.jbk.util.TestkeySignBridge.signWithTestkey(
                apk.getAbsolutePath(), signedApk.getAbsolutePath());
        return signedApk;
    }

    private List<File> getLibraryJars() {
        if (cachedLibraryJars != null) {
            return cachedLibraryJars;
        }
        List<File> jars = new ArrayList<>();
        File libsDir = new File(project.getLibsPath());
        if (libsDir.exists() && libsDir.isDirectory()) {
            File[] files = libsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".jar")) {
                        jars.add(file);
                    }
                }
            }
        }
        cachedLibraryJars = jars;
        return cachedLibraryJars;
    }

    public interface BuildProgressListener {
        void onProgress(String message, int step);
    }
}
