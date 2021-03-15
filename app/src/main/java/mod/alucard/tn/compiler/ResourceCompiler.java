package mod.alucard.tn.compiler;

import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import a.a.a.Dp;
import a.a.a.Jp;
import mod.agus.jcoderz.lib.BinaryExecutor;
import mod.agus.jcoderz.lib.FileUtil;

public class ResourceCompiler {

    private final File aaptFile;
    private final String compiledBuiltInLibraryResourcesDirectory;
    private final Dp mDp;
    /**
     * About log tags: add ":" and the first letter of the function's name un-camelCase'd, for example in thisIsALongFunctionName, you should use this: TAG + ":tIALFN"
     */
    private static final String TAG = "AppBuilder";

    public ResourceCompiler(Dp dp, File aapt) {
        aaptFile = aapt;
        compiledBuiltInLibraryResourcesDirectory = new File(dp.aapt2Dir.getParentFile(), "compiledLibs").getAbsolutePath();
        mDp = dp;
    }

    public void compile() {
        Log.d(TAG + ":c", "Called!");
        String outputPath = mDp.f.t + File.separator + "res";
        checkDir(outputPath);
        long savedTimeMillis = System.currentTimeMillis();
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

    public void link() {
        Log.d(TAG + ":l", "Called!");
        String resourcesPath = mDp.f.t + File.separator + "res";

        ArrayList<String> commands = new ArrayList<>();
        commands.add(aaptFile.getAbsolutePath());
        commands.add("link");
        //commands.add("--proto-format");
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ only useful for generating app bundle
        commands.add("--auto-add-overlay");
        commands.add("--min-sdk-version");
        commands.add(mDp.settings.getValue("min_sdk", "21"));
        commands.add("--allow-reserved-package-id");
        commands.add("--no-version-vectors");
        commands.add("--no-version-transitions");
        commands.add("--target-sdk-version");
        commands.add(mDp.settings.getValue("target_sdk", "28"));
        commands.add("--version-code");
        String versionCode = mDp.f.l;
        commands.add((versionCode == null || versionCode.isEmpty()) ? "1" : mDp.f.l);
        commands.add("--version-name");
        String versionName = mDp.f.m;
        commands.add((versionName == null || versionName.isEmpty()) ? "1.0" : mDp.f.m);
        commands.add("-I");
        String customAndroidSdk = mDp.settings.getValue("android_sdk", "");
        commands.add(customAndroidSdk.isEmpty() ? mDp.o : customAndroidSdk);

        /* Add assets imported normally */
        commands.add("-A");
        commands.add(mDp.f.A);

        /* Add imported assets */
        if (FileUtil.isExistFile(mDp.fpu.getPathAssets(mDp.f.b))) {
            commands.add("-A");
            commands.add(mDp.fpu.getPathAssets(mDp.f.b));
        }

        /* Add local libraries' assets */
        for (Jp next : mDp.n.a()) {
            if (next.d()) {
                commands.add("-A");
                commands.add(mDp.l.getAbsolutePath() + File.separator + "libs" + File.separator + next.a() + File.separator + "assets");
            }
        }

        /* Include previously compiled "modules" */
        for (File file : new File(resourcesPath).listFiles()) {
            commands.add("-R");
            commands.add(file.getAbsolutePath());
        }

        /* Include compiled built-in library resources */
        for (File file : new File(compiledBuiltInLibraryResourcesDirectory).listFiles()) {
            commands.add("-R");
            commands.add(file.getAbsolutePath());
        }

        commands.add("--manifest");
        commands.add(mDp.f.r);
        commands.add("--java");
        commands.add(mDp.f.v);
        String e = mDp.e();
        if (!e.isEmpty()) {
            commands.add("--extra-packages");
            commands.add(e);
        }
        commands.add("-o");
        commands.add(mDp.f.C);
        Log.d(TAG + ":l", "Now executing: " + commands.toString());
        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(commands);
        if (!executor.execute().isEmpty()) {
            Log.e(TAG + ":l", executor.getLog());
        }
    }

    private void compileProjectResources(String outputPath) {
        Log.d(TAG + ":cPR", "Called!");
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
        }
    }

    private void checkDir(String str) {
        if (FileUtil.isExistFile(str)) {
            FileUtil.deleteFile(str);
            FileUtil.makeDir(str);
            return;
        }
        FileUtil.makeDir(str);
    }

    private void compileLocalLibraryResources(String outputPath) {
        Log.d(TAG + ":cLLR", "Called!");
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
            }
        }
    }

    private void compileBuiltInLibraryResources() {
        Log.d(TAG + ":cBILR", "Called!");
        for (Jp library : mDp.n.a()) {
            Log.d(TAG + ":cBILR", "Dumping a a.a.a.Jp: a=" + library.a + ", b=" + library.b + ", c=" + library.c + ", d=" + library.d);
            if (library.c()) {
                String libraryResources = mDp.l.getAbsolutePath() + File.separator + "libs" + File.separator + library.a() + File.separator + "res";
                if (isBuiltInLibraryRecompilingNeeded(libraryResources)) {
                    ArrayList<String> commands = new ArrayList<>();
                    commands.add(aaptFile.getAbsolutePath());
                    commands.add("compile");
                    commands.add("--dir");
                    commands.add(libraryResources);
                    commands.add("-o");
                    commands.add(compiledBuiltInLibraryResourcesDirectory + File.separator + library.a + ".zip");
                    Log.d(TAG + ":cBILR", "Now executing: " + commands.toString());
                    BinaryExecutor executor = new BinaryExecutor();
                    executor.setCommands(commands);
                    if (!executor.execute().isEmpty()) {
                        Log.e(TAG + ":cBILR", executor.getLog());
                    }
                } else {
                    Log.d(TAG + ":cBILR", "Skipped resource recompilation for built-in library " + library.a() + ".");
                }
            }
        }
    }

    private boolean isBuiltInLibraryRecompilingNeeded(String libraryResourcesPath) {
        Log.d(TAG + ":iBILRN", "Called!");
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

    private void compileImportedResources(String outputPath) {
        Log.d(TAG + ":cIR", "Called!");
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
            }
        }
    }
}