package mod.jbk.build.compiler.resource;

import static com.besome.sketch.Config.VAR_DEFAULT_TARGET_SDK_VERSION;

import android.content.Context;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import a.a.a.Jp;
import a.a.a.ProjectBuilder;
import a.a.a.zy;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.diagnostic.MissingFileException;
import mod.jbk.util.LogUtil;
import pro.sketchware.SketchApplication;
import pro.sketchware.utility.BinaryExecutor;
import pro.sketchware.utility.FileUtil;

/**
 * A class responsible for compiling a Project's resources.
 * Supports AAPT2.
 */
public class ResourceCompiler {

    /**
     * About log tags: add ":" and the first letter of the function's name camelCase'd.
     * For example, in thisIsALongFunctionName, you should use this:
     * <pre>
     *     TAG + ":tIALFN"
     * </pre>
     */
    private static final String TAG = "AppBuilder";
    private final boolean willBuildAppBundle;
    private final File aaptFile;
    private final BuildProgressReceiver progressReceiver;
    private final ProjectBuilder builder;

    public ResourceCompiler(ProjectBuilder builder, File aapt, boolean willBuildAppBundle, BuildProgressReceiver receiver) {
        this.willBuildAppBundle = willBuildAppBundle;
        aaptFile = aapt;
        progressReceiver = receiver;
        this.builder = builder;
    }

    public void compile() throws IOException, zy, MissingFileException {
        Compiler resourceCompiler;
        resourceCompiler = new Aapt2Compiler(builder, aaptFile, willBuildAppBundle);

        resourceCompiler.setProgressListener(new Compiler.ProgressListener() {
            @Override
            void onProgressUpdate(String newProgress, int step) {
                if (progressReceiver != null) progressReceiver.onProgress(newProgress, step);
            }
        });
        resourceCompiler.compile();
    }

    /**
     * A base class of a resource compiler.
     */
    interface Compiler {

        /**
         * Compile a project's resources fully.
         */
        void compile() throws zy, MissingFileException;

        /**
         * Set a progress listener to compiling.
         *
         * @param listener The listener object
         */
        void setProgressListener(ProgressListener listener);

        /**
         * A listener for progress on compilation.
         */
        abstract class ProgressListener {
            /**
             * The compiler has reached a new phase the user should know about.
             *
             * @param newProgress A String provided by the resource compiler the user should see.
             */
            abstract void onProgressUpdate(String newProgress, int step);
        }
    }

    /**
     * A {@link Compiler} implementing AAPT2.
     */
    static class Aapt2Compiler implements Compiler {

        private final boolean buildAppBundle;

        private final File aapt2;
        private final ProjectBuilder buildHelper;
        private final File compiledBuiltInLibraryResourcesDirectory;
        private ProgressListener progressListener;

        public Aapt2Compiler(ProjectBuilder buildHelper, File aapt2, boolean buildAppBundle) {
            this.buildHelper = buildHelper;
            this.aapt2 = aapt2;
            this.buildAppBundle = buildAppBundle;
            compiledBuiltInLibraryResourcesDirectory = new File(SketchApplication.getContext().getCacheDir(), "compiledLibs");
        }

        @Override
        public void compile() throws zy, MissingFileException {
            String outputPath = buildHelper.yq.binDirectoryPath + File.separator + "res";
            emptyOrCreateDirectory(outputPath);

            long savedTimeMillis = System.currentTimeMillis();
            if (progressListener != null) {
                progressListener.onProgressUpdate("Compiling resources with AAPT2...", 9);
            }
            compileBuiltInLibraryResources();
            LogUtil.d(TAG + ":c", "Compiling built-in library resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            savedTimeMillis = System.currentTimeMillis();
            compileLocalLibraryResources(outputPath);
            LogUtil.d(TAG + ":c", "Compiling local library resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            savedTimeMillis = System.currentTimeMillis();
            compileProjectResources(outputPath);
            LogUtil.d(TAG + ":c", "Compiling project generated resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            savedTimeMillis = System.currentTimeMillis();
            compileImportedResources(outputPath);
            LogUtil.d(TAG + ":c", "Compiling project imported resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");

            savedTimeMillis = System.currentTimeMillis();
            link();
            LogUtil.d(TAG + ":c", "Linking resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        }

        /**
         * Links the project's resources using AAPT2.
         *
         * @throws zy Thrown to be caught by DesignActivity to show an error Snackbar.
         */
        public void link() throws zy, MissingFileException {
            String resourcesPath = buildHelper.yq.binDirectoryPath + File.separator + "res";
            if (progressListener != null)
                progressListener.onProgressUpdate("Linking resources with AAPT2...", 10);

            ArrayList<String> args = new ArrayList<>();
            args.add(aapt2.getAbsolutePath());
            args.add("link");
            if (buildAppBundle) {
                args.add("--proto-format");
            }
            args.add("--allow-reserved-package-id");
            args.add("--auto-add-overlay");
            args.add("--no-version-vectors");
            args.add("--no-version-transitions");

            args.add("--min-sdk-version");
            args.add(String.valueOf(buildHelper.settings.getMinSdkVersion()));
            args.add("--target-sdk-version");
            args.add(buildHelper.settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, String.valueOf(VAR_DEFAULT_TARGET_SDK_VERSION)));

            args.add("--version-code");
            String versionCode = buildHelper.yq.versionCode;
            args.add((versionCode == null || versionCode.isEmpty()) ? "1" : versionCode);
            args.add("--version-name");
            String versionName = buildHelper.yq.versionName;
            args.add((versionName == null || versionName.isEmpty()) ? "1.0" : versionName);

            args.add("-I");
            String customAndroidSdk = buildHelper.build_settings.getValue(BuildSettings.SETTING_ANDROID_JAR_PATH, "");
            if (customAndroidSdk.isEmpty()) {
                args.add(buildHelper.androidJarPath);
            } else {
                linkingAssertFileExists(customAndroidSdk);
                args.add(customAndroidSdk);
            }

            /* Add assets imported by vanilla method */
            linkingAssertDirectoryExists(buildHelper.yq.assetsPath);
            args.add("-A");
            args.add(buildHelper.yq.assetsPath);

            /* Add imported assets */
            String importedAssetsPath = buildHelper.fpu.getPathAssets(buildHelper.yq.sc_id);
            if (FileUtil.isExistFile(importedAssetsPath)) {
                args.add("-A");
                args.add(importedAssetsPath);
            }

            /* Add built-in libraries' assets */
            for (Jp library : buildHelper.builtInLibraryManager.getLibraries()) {
                if (library.hasAssets()) {
                    String assetsPath = BuiltInLibraries.getLibraryAssetsPath(library.getName());

                    linkingAssertDirectoryExists(assetsPath);
                    args.add("-A");
                    args.add(assetsPath);
                }
            }

            /* Add local libraries' assets */
            for (String localLibraryAssetsDirectory : new ManageLocalLibrary(buildHelper.yq.sc_id).getAssets()) {
                linkingAssertDirectoryExists(localLibraryAssetsDirectory);
                args.add("-A");
                args.add(localLibraryAssetsDirectory);
            }

            /* Include compiled built-in library resources */
            for (Jp library : buildHelper.builtInLibraryManager.getLibraries()) {
                if (library.hasResources()) {
                    args.add("-R");
                    args.add(new File(compiledBuiltInLibraryResourcesDirectory, library.getName() + ".zip").getAbsolutePath());
                }
            }

            /* Include compiled local libraries' resources */
            File[] filesInCompiledResourcesPath = new File(resourcesPath).listFiles();
            if (filesInCompiledResourcesPath != null) {
                for (File file : filesInCompiledResourcesPath) {
                    if (file.isFile()) {
                        if (!file.getName().equals("project.zip") || !file.getName().equals("project-imported.zip")) {
                            args.add("-R");
                            args.add(file.getAbsolutePath());
                        }
                    }
                }
            }

            /* Include compiled project resources */
            File projectArchive = new File(resourcesPath, "project.zip");
            if (projectArchive.exists()) {
                args.add("-R");
                args.add(projectArchive.getAbsolutePath());
            }

            /* Include compiled imported project resources */
            File projectImportedArchive = new File(resourcesPath, "project-imported.zip");
            if (projectImportedArchive.exists()) {
                args.add("-R");
                args.add(projectImportedArchive.getAbsolutePath());
            }

            /* Add R.java */
            linkingAssertDirectoryExists(buildHelper.yq.rJavaDirectoryPath);
            args.add("--java");
            args.add(buildHelper.yq.rJavaDirectoryPath);

            /* Output AAPT2's generated ProGuard rules to a.a.a.yq.aapt_rules */
            args.add("--proguard");
            args.add(buildHelper.yq.proguardAaptRules);

            /* Add AndroidManifest.xml */
            linkingAssertFileExists(buildHelper.yq.androidManifestPath);
            args.add("--manifest");
            args.add(buildHelper.yq.androidManifestPath);

            /* Use the generated R.java for used libraries */
            String extraPackages = buildHelper.getLibraryPackageNames();
            if (!extraPackages.isEmpty()) {
                args.add("--extra-packages");
                args.add(extraPackages);
            }

            /* Output the APK only with resources to a.a.a.yq.C */
            args.add("-o");
            args.add(buildHelper.yq.resourcesApkPath);

            LogUtil.d(TAG + ":l", args.toString());
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(args);
            if (!executor.execute().isEmpty()) {
                LogUtil.e(TAG + ":l", executor.getLog());
                throw new zy(executor.getLog());
            }
        }

        private void compileProjectResources(String outputPath) throws zy, MissingFileException {
            compilingAssertDirectoryExists(buildHelper.yq.resDirectoryPath);

            ArrayList<String> commands = new ArrayList<>();
            commands.add(aapt2.getAbsolutePath());
            commands.add("compile");
            commands.add("--dir");
            commands.add(buildHelper.yq.resDirectoryPath);
            commands.add("-o");
            commands.add(outputPath + File.separator + "project.zip");
            LogUtil.d(TAG + ":cPR", "Now executing: " + commands);
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(commands);
            if (!executor.execute().isEmpty()) {
                LogUtil.e(TAG, executor.getLog());
                throw new zy(executor.getLog());
            }
        }

        private void emptyOrCreateDirectory(String path) {
            if (FileUtil.isExistFile(path)) {
                FileUtil.deleteFile(path);
            }
            FileUtil.makeDir(path);
        }

        private void compileLocalLibraryResources(String outputPath) throws zy, MissingFileException {
            int localLibrariesCount = buildHelper.mll.getResLocalLibrary().size();
            LogUtil.d(TAG + ":cLLR", "About to compile " + localLibrariesCount
                    + " local " + (localLibrariesCount == 1 ? "library" : "libraries"));
            for (String localLibraryResDirectory : buildHelper.mll.getResLocalLibrary()) {
                File localLibraryDirectory = new File(localLibraryResDirectory).getParentFile();
                if (localLibraryDirectory != null) {
                    compilingAssertDirectoryExists(localLibraryResDirectory);

                    ArrayList<String> commands = new ArrayList<>();
                    commands.add(aapt2.getAbsolutePath());
                    commands.add("compile");
                    commands.add("--dir");
                    commands.add(localLibraryResDirectory);
                    commands.add("-o");
                    commands.add(outputPath + File.separator + localLibraryDirectory.getName() + ".zip");

                    LogUtil.d(TAG + ":cLLR", "Now executing: " + commands);
                    BinaryExecutor executor = new BinaryExecutor();
                    executor.setCommands(commands);
                    if (!executor.execute().isEmpty()) {
                        LogUtil.e(TAG, executor.getLog());
                        throw new zy(executor.getLog());
                    }
                }
            }
        }

        private void compileBuiltInLibraryResources() throws zy, MissingFileException {
            compiledBuiltInLibraryResourcesDirectory.mkdirs();
            for (Jp builtInLibrary : buildHelper.builtInLibraryManager.getLibraries()) {
                if (builtInLibrary.hasResources()) {
                    File cachedCompiledResources = new File(compiledBuiltInLibraryResourcesDirectory, builtInLibrary.getName() + ".zip");
                    String libraryResources = BuiltInLibraries.getLibraryResourcesPath(builtInLibrary.getName());

                    compilingAssertDirectoryExists(libraryResources);

                    if (isBuiltInLibraryRecompilingNeeded(cachedCompiledResources)) {
                        ArrayList<String> commands = new ArrayList<>();
                        commands.add(aapt2.getAbsolutePath());
                        commands.add("compile");
                        commands.add("--dir");
                        commands.add(libraryResources);
                        commands.add("-o");
                        commands.add(cachedCompiledResources.getAbsolutePath());

                        LogUtil.d(TAG + ":cBILR", "Now executing: " + commands);
                        BinaryExecutor executor = new BinaryExecutor();
                        executor.setCommands(commands);
                        if (!executor.execute().isEmpty()) {
                            LogUtil.e(TAG + ":cBILR", executor.getLog());
                            throw new zy(executor.getLog());
                        }
                    } else {
                        LogUtil.d(TAG + ":cBILR", "Skipped resource recompilation for built-in library " + builtInLibrary.getName());
                    }
                }
            }
        }

        private boolean isBuiltInLibraryRecompilingNeeded(File cachedCompiledResources) {
            if (cachedCompiledResources.exists()) {
                try {
                    Context context = SketchApplication.getContext();
                    return context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                            .lastUpdateTime > cachedCompiledResources.lastModified();
                } catch (PackageManager.NameNotFoundException e) {
                    LogUtil.e(TAG + ":iBILRN", "Couldn't get package info about ourselves: " + e.getMessage(), e);
                }
            } else {
                LogUtil.d(TAG + ":iBILRN", "File " + cachedCompiledResources.getAbsolutePath()
                        + " doesn't exist, forcing compilation");
            }
            return true;
        }

        private void compileImportedResources(String outputPath) throws zy {
            if (FileUtil.isExistFile(buildHelper.fpu.getPathResource(buildHelper.yq.sc_id))
                    && new File(buildHelper.fpu.getPathResource(buildHelper.yq.sc_id)).length() != 0) {
                ArrayList<String> commands = new ArrayList<>();
                commands.add(aapt2.getAbsolutePath());
                commands.add("compile");
                commands.add("--dir");
                commands.add(buildHelper.fpu.getPathResource(buildHelper.yq.sc_id));
                commands.add("-o");
                commands.add(outputPath + File.separator + "project-imported.zip");
                LogUtil.d(TAG + ":cIR", "Now executing: " + commands);
                BinaryExecutor executor = new BinaryExecutor();
                executor.setCommands(commands);
                if (!executor.execute().isEmpty()) {
                    LogUtil.e(TAG, executor.getLog());
                    throw new zy(executor.getLog());
                }
            }
        }

        private void compilingAssertDirectoryExists(String directoryPath) throws MissingFileException {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                throw new MissingFileException(directory, MissingFileException.STEP_RESOURCE_COMPILING, true);
            }
        }

        public void linkingAssertFileExists(String filePath) throws MissingFileException {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new MissingFileException(file, MissingFileException.STEP_RESOURCE_LINKING, false);
            }
        }

        public void linkingAssertDirectoryExists(String filePath) throws MissingFileException {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new MissingFileException(file, MissingFileException.STEP_RESOURCE_LINKING, true);
            }
        }

        @Override
        public void setProgressListener(ProgressListener listener) {
            progressListener = listener;
        }
    }
}
