package mod.jbk.build.compiler.resource;

import android.content.pm.PackageManager;
import android.util.Log;

import com.besome.sketch.design.DesignActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import a.a.a.Dp;
import a.a.a.Jp;
import a.a.a.zy;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.BinaryExecutor;
import mod.agus.jcoderz.lib.FileUtil;

/**
 * A class responsible for compiling a Project's resources.
 * Supports AAPT and AAPT2.
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
    private final boolean useAapt2;
    private final boolean willBuildAppBundle;
    private final File aaptFile;
    private final DesignActivity.a buildingDialog;
    private final Dp dp;

    public ResourceCompiler(Dp dp, File aapt, boolean willBuildAppBundle, DesignActivity.a dialog, boolean useAapt2) {
        this.useAapt2 = useAapt2;
        this.willBuildAppBundle = willBuildAppBundle;
        aaptFile = aapt;
        buildingDialog = dialog;
        this.dp = dp;
    }

    public void compile() throws IOException, zy {
        Compiler resourceCompiler;
        if (useAapt2) {
            resourceCompiler = new Aapt2Compiler(dp, aaptFile, willBuildAppBundle);
        } else {
            resourceCompiler = new AaptCompiler(dp);
        }

        resourceCompiler.setProgressListener(new Compiler.ProgressListener() {
            @Override
            void onProgressUpdate(String newProgress) {
                if (buildingDialog != null) buildingDialog.c(newProgress);
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
        void compile() throws zy;

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
            abstract void onProgressUpdate(String newProgress);
        }
    }

    /**
     * A {@link Compiler} implementing AAPT.
     */
    static class AaptCompiler implements Compiler {

        private final Dp buildHelper;

        public AaptCompiler(Dp buildHelper) {
            this.buildHelper = buildHelper;
        }

        @Override
        public void compile() throws zy {
            /* Start generating arguments for AAPT */
            ArrayList<String> args = new ArrayList<>();

            args.add(buildHelper.i.getAbsolutePath());
            args.add("package");

            /* Generate R.java for libraries */
            String extraPackages = buildHelper.e();
            if (!extraPackages.isEmpty()) {
                args.add("--extra-packages");
                args.add(extraPackages);
            }

            /* Set minSdkVersion */
            args.add("--min-sdk-version");
            args.add(buildHelper.settings.getValue("min_sdk", buildHelper.a));

            /* Set targetSdkVersion */
            args.add("--target-sdk-version");
            args.add(buildHelper.settings.getValue("target_sdk", buildHelper.b));

            /* Set versionCode */
            args.add("--version-code");
            args.add(((buildHelper.f.l == null) || buildHelper.f.l.isEmpty()) ? "1" : buildHelper.f.l);

            /* Set versionName */
            args.add("--version-name");
            args.add(((buildHelper.f.m == null) || buildHelper.f.m.isEmpty()) ? "1.0" : buildHelper.f.m);

            args.add("--auto-add-overlay");
            args.add("--generate-dependencies");

            /* Force overwriting of existing files */
            args.add("-f");

            args.add("-m");

            /* Don't generate final R.java ID fields */
            args.add("--non-constant-id");

            /* Generate a text file containing resource symbols */
            args.add("--output-text-symbols");
            args.add(buildHelper.f.t);

            if (buildHelper.f.N.g) {
                args.add("--no-version-vectors");
            }

            /* Specify resources directory */
            args.add("-S");
            args.add(buildHelper.f.w);

            /* Specify local libraries' resource directories */
            for (String localLibraryResDirectory : buildHelper.mll.getResLocalLibrary()) {
                args.add("-S");
                args.add(localLibraryResDirectory);
            }

            /* Specify imported resources directory */
            if (FileUtil.isExistFile(buildHelper.fpu.getPathResource(buildHelper.f.b))) {
                args.add("-S");
                args.add(buildHelper.fpu.getPathResource(buildHelper.f.b));
            }

            /* Add assets added by vanilla method */
            args.add("-A");
            args.add(buildHelper.f.A);

            /* Add imported assets */
            if (FileUtil.isExistFile(buildHelper.fpu.getPathAssets(buildHelper.f.b))) {
                args.add("-A");
                args.add(buildHelper.fpu.getPathAssets(buildHelper.f.b));
            }

            /* Add local libraries' assets */
            for (String localLibraryAssetsDirectory : buildHelper.mll.getAssets()) {
                args.add("-A");
                args.add(localLibraryAssetsDirectory);
            }

            /* Add built-in libraries' assets */
            for (Jp library : buildHelper.n.a()) {
                if (library.d()) {
                    args.add("-A");
                    args.add(buildHelper.l.getAbsolutePath() + File.separator + "libs" + File.separator + library.a() + File.separator + "assets");
                }

                if (library.c()) {
                    args.add("-S");
                    args.add(buildHelper.l.getAbsolutePath() + File.separator + "libs" + File.separator + library.a() + File.separator + "res");
                }
            }

            /* Specify R.java output directory */
            args.add("-J");
            args.add(buildHelper.f.v);

            /* Specify where to output ProGuard options to */
            args.add("-G");
            args.add(buildHelper.f.aapt_rules);

            /* Specify AndroidManifest.xml's path */
            args.add("-M");
            args.add(buildHelper.f.r);

            /* Specify android.jar */
            args.add("-I");
            args.add(buildHelper.o);

            /* Specify output APK file */
            args.add("-F");
            args.add(buildHelper.f.C);

            Log.d(TAG, "Compiling resources with AAPT and these arguments: " + args.toString());
            if (buildHelper.j.a(args.toArray(new String[0])) != 0) {
                throw new zy(buildHelper.j.a.toString());
            }
        }

        @Override
        public void setProgressListener(ProgressListener listener) {
            // no-op
        }
    }

    /**
     * A {@link Compiler} implementing AAPT2.
     */
    static class Aapt2Compiler implements Compiler {

        private final boolean buildAppBundle;

        private final File aapt2;
        private final Dp buildHelper;
        private final String compiledBuiltInLibraryResourcesDirectory;
        private ProgressListener progressListener;

        public Aapt2Compiler(Dp buildHelper, File aapt2, boolean buildAppBundle) {
            this.buildHelper = buildHelper;
            this.aapt2 = aapt2;
            this.buildAppBundle = buildAppBundle;
            compiledBuiltInLibraryResourcesDirectory = new File(buildHelper.aapt2Dir.getParentFile(), "compiledLibs").getAbsolutePath();
        }

        @Override
        public void compile() throws zy {
            String outputPath = buildHelper.f.t + File.separator + "res";
            emptyOrCreateDirectory(outputPath);
            long savedTimeMillis = System.currentTimeMillis();
            if (progressListener != null)
                progressListener.onProgressUpdate("Compiling resources with AAPT2...");
            compileProjectResources(outputPath);
            Log.d(TAG + ":c", "Compiling project generated resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            savedTimeMillis = System.currentTimeMillis();
            compileImportedResources(outputPath);
            Log.d(TAG + ":c", "Compiling project imported resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            savedTimeMillis = System.currentTimeMillis();
            compileLocalLibraryResources(outputPath);
            Log.d(TAG + ":c", "Compiling local library resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
            savedTimeMillis = System.currentTimeMillis();
            compileBuiltInLibraryResources();
            Log.d(TAG + ":c", "Compiling built-in library resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");

            savedTimeMillis = System.currentTimeMillis();
            link();
            Log.d(TAG + ":c", "Linking resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        }

        /**
         * Links the project's resources using AAPT2.
         *
         * @throws zy Thrown to be caught by DesignActivity to show an error Snackbar.
         */
        public void link() throws zy {
            long savedTimeMillis = System.currentTimeMillis();
            String resourcesPath = buildHelper.f.t + File.separator + "res";
            if (progressListener != null)
                progressListener.onProgressUpdate("Linking resources with AAPT2...");

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
            args.add(buildHelper.settings.getValue("min_sdk", "21"));
            args.add("--target-sdk-version");
            args.add(buildHelper.settings.getValue("target_sdk", "28"));

            args.add("--version-code");
            String versionCode = buildHelper.f.l;
            args.add((versionCode == null || versionCode.isEmpty()) ? "1" : versionCode);
            args.add("--version-name");
            String versionName = buildHelper.f.m;
            args.add((versionName == null || versionName.isEmpty()) ? "1.0" : versionName);

            args.add("-I");
            String customAndroidSdk = buildHelper.settings.getValue("android_sdk", "");
            args.add(customAndroidSdk.isEmpty() ? buildHelper.o : customAndroidSdk);

            /* Add assets imported by vanilla method */
            args.add("-A");
            args.add(buildHelper.f.A);

            /* Add imported assets */
            if (FileUtil.isExistFile(buildHelper.fpu.getPathAssets(buildHelper.f.b))) {
                args.add("-A");
                args.add(buildHelper.fpu.getPathAssets(buildHelper.f.b));
            }

            /* Add built-in libraries' assets */
            for (Jp library : buildHelper.n.a()) {
                if (library.d()) {
                    args.add("-A");
                    args.add(buildHelper.l.getAbsolutePath() + File.separator + "libs" + File.separator + library.a() + File.separator + "assets");
                }
            }

            /* Add local libraries' assets */
            for (String localLibraryAssetsDirectory : new ManageLocalLibrary(buildHelper.f.b).getAssets()) {
                args.add("-A");
                args.add(localLibraryAssetsDirectory);
            }

            /* Include compiled built-in library resources */
            for (Jp library : buildHelper.n.a()) {
                if (library.c()) {
                    args.add("-R");
                    args.add(new File(compiledBuiltInLibraryResourcesDirectory, library.a() + ".zip").getAbsolutePath());
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
            args.add("--java");
            args.add(buildHelper.f.v);

            /* Output AAPT2's generated ProGuard rules to a.a.a.yq.aapt_rules */
            args.add("--proguard");
            args.add(buildHelper.f.aapt_rules);

            /* Add AndroidManifest.xml */
            args.add("--manifest");
            args.add(buildHelper.f.r);

            /* Use the generated R.java for used libraries */
            String extraPackages = buildHelper.e();
            if (!extraPackages.isEmpty()) {
                args.add("--extra-packages");
                args.add(extraPackages);
            }

            /* Output the APK only with resources to a.a.a.yq.C */
            args.add("-o");
            args.add(buildHelper.f.C);

            Log.d(TAG + ":l", "Now executing: " + args.toString());
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(args);
            if (!executor.execute().isEmpty()) {
                Log.e(TAG + ":l", executor.getLog());
                throw new zy(executor.getLog());
            }
            Log.d(TAG + ":l", "Linking resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        }

        private void compileProjectResources(String outputPath) throws zy {
            ArrayList<String> commands = new ArrayList<>();
            commands.add(aapt2.getAbsolutePath());
            commands.add("compile");
            commands.add("--dir");
            commands.add(buildHelper.f.w);
            commands.add("-o");
            commands.add(outputPath + File.separator + "project.zip");
            Log.d(TAG + ":cPR", "Now executing: " + commands.toString());
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(commands);
            if (!executor.execute().isEmpty()) {
                Log.e(TAG, executor.getLog());
                throw new zy(executor.getLog());
            }
        }

        private void emptyOrCreateDirectory(String path) {
            if (FileUtil.isExistFile(path)) {
                FileUtil.deleteFile(path);
                FileUtil.makeDir(path);
                return;
            }
            FileUtil.makeDir(path);
        }

        private void compileLocalLibraryResources(String outputPath) throws zy {
            Log.d(TAG + ":cLLR", "About to compile " + buildHelper.mll.getResLocalLibrary().size() + " local " + (buildHelper.mll.getResLocalLibrary().size() == 1 ? "library" : "libraries"));
            for (String localLibraryResDirectory : buildHelper.mll.getResLocalLibrary()) {
                File localLibraryDirectory = new File(localLibraryResDirectory).getParentFile();
                if (localLibraryDirectory != null) {
                    ArrayList<String> commands = new ArrayList<>();
                    commands.add(aapt2.getAbsolutePath());
                    commands.add("compile");
                    commands.add("--dir");
                    commands.add(localLibraryResDirectory);
                    commands.add("-o");
                    commands.add(outputPath + File.separator + localLibraryDirectory.getName() + ".zip");
                    Log.d(TAG + ":cLLR", "Now executing: " + commands.toString());
                    BinaryExecutor executor = new BinaryExecutor();
                    executor.setCommands(commands);
                    if (!executor.execute().isEmpty()) {
                        Log.e(TAG, executor.getLog());
                        throw new zy(executor.getLog());
                    }
                }
            }
        }

        private void compileBuiltInLibraryResources() throws zy {
            for (Jp builtInLibrary : buildHelper.n.a()) {
                if (builtInLibrary.c()) {
                    String libraryResources = buildHelper.l.getAbsolutePath() + File.separator + "libs" + File.separator + builtInLibrary.a() + File.separator + "res";
                    if (isBuiltInLibraryRecompilingNeeded(libraryResources)) {
                        ArrayList<String> commands = new ArrayList<>();
                        commands.add(aapt2.getAbsolutePath());
                        commands.add("compile");
                        commands.add("--dir");
                        commands.add(libraryResources);
                        commands.add("-o");
                        commands.add(compiledBuiltInLibraryResourcesDirectory + File.separator + builtInLibrary.a() + ".zip");
                        Log.d(TAG + ":cBILR", "Now executing: " + commands.toString());
                        BinaryExecutor executor = new BinaryExecutor();
                        executor.setCommands(commands);
                        if (!executor.execute().isEmpty()) {
                            Log.e(TAG + ":cBILR", executor.getLog());
                            throw new zy(executor.getLog());
                        }
                    } else {
                        Log.d(TAG + ":cBILR", "Skipped resource recompilation for built-in library " + builtInLibrary.a() + ".");
                    }
                }
            }
        }

        private boolean isBuiltInLibraryRecompilingNeeded(String libraryResourcesPath) {
            File file = new File(compiledBuiltInLibraryResourcesDirectory, new File(libraryResourcesPath).getParentFile().getName() + ".zip");
            if (file.getParentFile().mkdirs()) return true;
            if (!file.exists()) return true;
            try {
                return buildHelper.e.getPackageManager().getPackageInfo(buildHelper.e.getPackageName(), 0).lastUpdateTime > file.lastModified();
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG + ":iBILRN", "Couldn't get package info about ourselves: " + e.getMessage(), e);
                return true;
            }
        }

        private void compileImportedResources(String outputPath) throws zy {
            if (FileUtil.isExistFile(buildHelper.fpu.getPathResource(buildHelper.f.b))
                    && new File(buildHelper.fpu.getPathResource(buildHelper.f.b)).length() != 0) {
                ArrayList<String> commands = new ArrayList<>();
                commands.add(aapt2.getAbsolutePath());
                commands.add("compile");
                commands.add("--dir");
                commands.add(buildHelper.fpu.getPathResource(buildHelper.f.b));
                commands.add("-o");
                commands.add(outputPath + File.separator + "project-imported.zip");
                Log.d(TAG + ":cIR", "Now executing: " + commands.toString());
                BinaryExecutor executor = new BinaryExecutor();
                executor.setCommands(commands);
                if (!executor.execute().isEmpty()) {
                    Log.e(TAG, executor.getLog());
                    throw new zy(executor.getLog());
                }
            }
        }

        @Override
        public void setProgressListener(ProgressListener listener) {
            progressListener = listener;
        }
    }
}