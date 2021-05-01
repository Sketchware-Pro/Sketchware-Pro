package mod.alucard.tn.compiler;

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
import mod.jbk.util.LogUtil;

public class ResourceCompiler {

    /**
     * About log tags: add ":" and the first letter of the function's name camelCase'd, for example in thisIsALongFunctionName, you should use this: TAG + ":tIALFN"
     */
    private static final String TAG = "AppBuilder";
    private final boolean generateAndroidAppBundle;
    private final File aaptFile;
    private final String compiledBuiltInLibraryResourcesDirectory;
    private final DesignActivity.a buildingDialog;
    private final Dp mDp;

    public ResourceCompiler(Dp dp, File aapt, boolean generateAAB, DesignActivity.a dialog) {
        generateAndroidAppBundle = generateAAB;
        aaptFile = aapt;
        compiledBuiltInLibraryResourcesDirectory = new File(dp.aapt2Dir.getParentFile(), "compiledLibs").getAbsolutePath();
        mDp = dp;
        buildingDialog = dialog;
    }

    public void compile() throws IOException, zy {
        String outputPath = mDp.f.t + File.separator + "res";
        emptyOrCreateDirectory(outputPath);
        long savedTimeMillis = System.currentTimeMillis();
        if (buildingDialog != null) buildingDialog.c("Compiling resources with AAPT2...");
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
    }

    /**
     * Links the project's resources using AAPT2.
     *
     * @throws zy Thrown to be caught by DesignActivity to show an error Snackbar.
     */
    public void link() throws zy {
        long savedTimeMillis = System.currentTimeMillis();
        String resourcesPath = mDp.f.t + File.separator + "res";
        if (buildingDialog != null) buildingDialog.c("Linking resources with AAPT2...");

        ArrayList<String> args = new ArrayList<>();
        args.add(aaptFile.getAbsolutePath());
        args.add("link");
        if (generateAndroidAppBundle) {
            args.add("--proto-format");
        }
        args.add("--allow-reserved-package-id");
        args.add("--auto-add-overlay");
        args.add("--no-version-vectors");
        args.add("--no-version-transitions");

        args.add("--min-sdk-version");
        args.add(mDp.settings.getValue("min_sdk", "21"));
        args.add("--target-sdk-version");
        args.add(mDp.settings.getValue("target_sdk", "28"));

        args.add("--version-code");
        String versionCode = mDp.f.l;
        args.add((versionCode == null || versionCode.isEmpty()) ? "1" : mDp.f.l);
        args.add("--version-name");
        String versionName = mDp.f.m;
        args.add((versionName == null || versionName.isEmpty()) ? "1.0" : mDp.f.m);

        args.add("-I");
        String customAndroidSdk = mDp.settings.getValue("android_sdk", "");
        args.add(customAndroidSdk.isEmpty() ? mDp.o : customAndroidSdk);

        /* Add assets imported by vanilla method */
        args.add("-A");
        args.add(mDp.f.A);

        /* Add imported assets */
        if (FileUtil.isExistFile(mDp.fpu.getPathAssets(mDp.f.b))) {
            args.add("-A");
            args.add(mDp.fpu.getPathAssets(mDp.f.b));
        }

        /* Add built-in libraries' assets */
        for (Jp library : mDp.n.a()) {
            if (library.d()) {
                args.add("-A");
                args.add(mDp.l.getAbsolutePath() + File.separator + "libs" + File.separator + library.a() + File.separator + "assets");
            }
        }

        /* Add local libraries' assets */
        for (String localLibraryAssetsDirectory : new ManageLocalLibrary(mDp.f.b).getAssets()) {
            args.add("-A");
            args.add(localLibraryAssetsDirectory);
        }

        /* Include compiled built-in library resources */
        for (Jp library : mDp.n.a()) {
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
        args.add(mDp.f.v);

        /* Output AAPT2's generated ProGuard rules to a.a.a.yq.aapt_rules */
        args.add("--proguard");
        args.add(mDp.f.aapt_rules);

        /* Add AndroidManifest.xml */
        args.add("--manifest");
        args.add(mDp.f.r);

        /* Use the generated R.java for used libraries */
        String extraPackages = mDp.e();
        if (!extraPackages.isEmpty()) {
            args.add("--extra-packages");
            args.add(extraPackages);
        }

        /* Output the APK only with resources to a.a.a.yq.C */
        args.add("-o");
        args.add(mDp.f.C);

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
        commands.add(aaptFile.getAbsolutePath());
        commands.add("compile");
        commands.add("--dir");
        commands.add(mDp.f.w);
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
        Log.d(TAG + ":cLLR", "About to compile " + mDp.mll.getResLocalLibrary().size() + " local " + (mDp.mll.getResLocalLibrary().size() == 1 ? "library" : "libraries") + ".");
        for (String next : mDp.mll.getResLocalLibrary()) {
            ArrayList<String> commands = new ArrayList<>();
            commands.add(aaptFile.getAbsolutePath());
            commands.add("compile");
            commands.add("--dir");
            commands.add(next);
            commands.add("-o");
            commands.add(outputPath + File.separator + new File(next).getParentFile().getName() + ".zip");
            Log.d(TAG + ":cLLR", "Now executing: " + commands.toString());
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(commands);
            if (!executor.execute().isEmpty()) {
                Log.e(TAG, executor.getLog());
                throw new zy(executor.getLog());
            }
        }
    }

    private void compileBuiltInLibraryResources() throws zy {
        for (Jp library : mDp.n.a()) {
            LogUtil.dump(TAG + ":cBILR", library);
            if (library.c()) {
                String libraryResources = mDp.l.getAbsolutePath() + File.separator + "libs" + File.separator + library.a() + File.separator + "res";
                if (isBuiltInLibraryRecompilingNeeded(libraryResources)) {
                    ArrayList<String> commands = new ArrayList<>();
                    commands.add(aaptFile.getAbsolutePath());
                    commands.add("compile");
                    commands.add("--dir");
                    commands.add(libraryResources);
                    commands.add("-o");
                    commands.add(compiledBuiltInLibraryResourcesDirectory + File.separator + library.a() + ".zip");
                    Log.d(TAG + ":cBILR", "Now executing: " + commands.toString());
                    BinaryExecutor executor = new BinaryExecutor();
                    executor.setCommands(commands);
                    if (!executor.execute().isEmpty()) {
                        Log.e(TAG + ":cBILR", executor.getLog());
                        throw new zy(executor.getLog());
                    }
                } else {
                    Log.d(TAG + ":cBILR", "Skipped resource recompilation for built-in library " + library.a() + ".");
                }
            }
        }
    }

    private boolean isBuiltInLibraryRecompilingNeeded(String libraryResourcesPath) {
        File file = new File(compiledBuiltInLibraryResourcesDirectory, new File(libraryResourcesPath).getParentFile().getName() + ".zip");
        if (file.getParentFile().mkdirs()) return true;
        if (!file.exists()) return true;
        try {
            return mDp.e.getPackageManager().getPackageInfo(mDp.e.getPackageName(), 0).lastUpdateTime > file.lastModified();
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG + ":iBILRN", "Couldn't get package info about ourselves: " + e.getMessage(), e);
            return true;
        }
    }

    private void compileImportedResources(String outputPath) throws zy {
        if (FileUtil.isExistFile(mDp.fpu.getPathResource(mDp.f.b)) && new File(mDp.fpu.getPathResource(mDp.f.b)).length() != 0) {
            ArrayList<String> commands = new ArrayList<>();
            commands.add(aaptFile.getAbsolutePath());
            commands.add("compile");
            commands.add("--dir");
            commands.add(mDp.fpu.getPathResource(mDp.f.b));
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
}