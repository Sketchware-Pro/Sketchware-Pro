package mod.agus.jcoderz.aapt2;

import android.util.Log;

import com.besome.sketch.design.DesignActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import a.a.a.Dp;
import a.a.a.Fp;
import a.a.a.Jp;
import mod.agus.jcoderz.lib.BinaryExecutor;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class ResourceCompiler {

    private Dp mDp;
    private final BinaryExecutor executor = new BinaryExecutor();
    private DesignActivity.a mDialog;
    private File aaptFile;

    public ResourceCompiler(Dp dp, DesignActivity.a aVar) {
        mDp = dp;
        mDialog = aVar;
    }

    public ResourceCompiler(Dp dp, File aapt, Fp fp) {
        mDp = dp;
        aaptFile = aapt;
    }

    private static void compileResProject(File aaptFile, String outputPath, ArrayList<String> resourcePathsToCompile) {
        ArrayList<String> commands = new ArrayList<>();
        commands.add(aaptFile.getAbsolutePath());
        commands.add("compile");
        commands.add("--dir");
        commands.add(resourcePathsToCompile.get(0));
        commands.add("-o");
        commands.add(outputPath + File.separator + "project.zip");
        Log.d(Dp.TAG + ":cRP", "Now executing: " + commands.toString());
        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(commands);
        if (!executor.execute().isEmpty()) {
            Log.e(Dp.TAG, executor.getLog());
        }
    }

    public void compile() {
        String outputPath = mDp.f.t + File.separator + "Resource";
        checkDir(outputPath);
        //mDialog.c("Compiling Main Resource . . .");
        compileResProject(outputPath);
        //mDialog.c("Compiling External Resource . . .");
        compileExternalResource(outputPath);
        //mDialog.c("Compiling Library Resource . . .");
        compileLocalLibraryResource(outputPath);
    }

    private void compileLocalLibraryResource(File aaptPath, String outputPath) {
        for (String next : mDp.mll.getResLocalLibrary()) {
            ArrayList<String> commands = new ArrayList<>();
            commands.add(aaptPath.getAbsolutePath());
            commands.add("compile");
            commands.add("--dir");
            commands.add(next);
            commands.add("-o");
            commands.add(outputPath + File.separator + new File(next).getParentFile().getName() + ".zip");
            Log.d(Dp.TAG + ":cLLR", "Now executing: " + commands.toString());
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(commands);
            if (!executor.execute().isEmpty()) {
                Log.e(Dp.TAG, executor.getLog());
            }
        }
        for (Jp next2 : mDp.n.a()) {
            if (next2.c()) {
                String realOutputPath = mDp.l.getAbsolutePath() + mDp.c + "libs" + mDp.c + next2.a() + mDp.c + "res";
                ArrayList<String> commands = new ArrayList<>();
                commands.add(aaptPath.getAbsolutePath());
                commands.add("compile");
                commands.add("--dir");
                commands.add(realOutputPath);
                commands.add("-o");
                commands.add(outputPath + File.separator + new File(realOutputPath).getParentFile().getName() + ".zip");
                Log.d(Dp.TAG + ":cLLR", "Now executing: " + commands.toString());
                BinaryExecutor executor = new BinaryExecutor();
                executor.setCommands(commands);
                if (!executor.execute().isEmpty()) {
                    Log.e(Dp.TAG, executor.getLog());
                }
            }
        }
    }

    private void compileExternalResource(File aaptFile, String outputPath) {
        FilePathUtil fpu = new FilePathUtil();
        if (FileUtil.isExistFile(fpu.getPathResource(mDp.f.b)) && new File(fpu.getPathResource(mDp.f.b)).length() != 0) {
            ArrayList<String> commands = new ArrayList<>();
            commands.add(aaptFile.getAbsolutePath());
            commands.add("compile");
            commands.add("--dir");
            commands.add(fpu.getPathResource(mDp.f.b));
            commands.add("-o");
            commands.add(outputPath + File.separator + "project-imported.zip");
            Log.d(Dp.TAG + ":cER", "Now executing: " + commands.toString());
            BinaryExecutor executor = new BinaryExecutor();
            executor.setCommands(commands);
            if (!executor.execute().isEmpty()) {
                Log.e(Dp.TAG, executor.getLog());
            }
        }
    }

    private static void checkDir(String str) {
        if (FileUtil.isExistFile(str)) {
            FileUtil.deleteFile(str);
            FileUtil.makeDir(str);
            return;
        }
        FileUtil.makeDir(str);
    }

    public void compile(String binDir, ArrayList<String> resourcePathsToCompile) {
        Log.d(Dp.TAG + ":c", "aaptFile=" + aaptFile.getAbsolutePath() + ", binDir=" + binDir + ", resourcePathsToCompile=" + resourcePathsToCompile.toString());
        String outputPath = binDir + File.separator + "res";
        checkDir(outputPath);
        long savedTimeMillis = System.currentTimeMillis();
        compileResProject(aaptFile, outputPath, resourcePathsToCompile);
        Log.d(Dp.TAG + ":c", "Compiling project generated resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        savedTimeMillis = System.currentTimeMillis();
        compileExternalResource(aaptFile, outputPath);
        Log.d(Dp.TAG + ":c", "Compiling project imported resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
        savedTimeMillis = System.currentTimeMillis();
        compileLocalLibraryResource(aaptFile, outputPath);
        Log.d(Dp.TAG + ":c", "Compiling local library resources took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    private void compileResProject(String outputPath) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mDp.i.getAbsolutePath());
        arrayList.add("compile");
        arrayList.add("--dir");
        arrayList.add(mDp.f.w);
        arrayList.add("-o");
        arrayList.add(outputPath + File.separator + new File(mDp.f.w).getParentFile().getName() + ".zip");
        executor.setCommands(arrayList);
        if (!executor.execute().isEmpty()) {
            mDialog.a(executor.getLog());
        }
    }

    private void compileLocalLibraryResource(String str) {
        for (String next : mDp.mll.getResLocalLibrary()) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(mDp.i.getAbsolutePath());
            arrayList.add("compile");
            arrayList.add("--dir");
            arrayList.add(next);
            arrayList.add("-o");
            arrayList.add(str + File.separator + new File(next).getParentFile().getName() + ".zip");
            executor.setCommands(arrayList);
            if (!executor.execute().isEmpty()) {
                mDialog.a(executor.getLog());
            }
        }
        for (Jp next2 : mDp.n.a()) {
            if (next2.c()) {
                String stringBuffer = mDp.l.getAbsolutePath() + mDp.c + "libs" + mDp.c + next2.a() + mDp.c + "res";
                ArrayList<String> arrayList2 = new ArrayList<>();
                arrayList2.add(mDp.i.getAbsolutePath());
                arrayList2.add("compile");
                arrayList2.add("--dir");
                arrayList2.add(stringBuffer);
                arrayList2.add("-o");
                arrayList2.add(str + File.separator + new File(stringBuffer).getParentFile().getName() + ".zip");
                executor.setCommands(arrayList2);
                if (!executor.execute().isEmpty()) {
                    mDialog.a(executor.getLog());
                }
            }
        }
    }

    private void compileExternalResource(String str) {
        if (FileUtil.isExistFile(mDp.fpu.getPathResource(mDp.f.b)) && new File(mDp.fpu.getPathResource(mDp.f.b)).length() != ((long) 0)) {
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(mDp.i.getAbsolutePath());
            arrayList.add("compile");
            arrayList.add("--dir");
            arrayList.add(mDp.fpu.getPathResource(mDp.f.b));
            arrayList.add("-o");
            arrayList.add(str + File.separator + new File(mDp.fpu.getPathResource(mDp.f.b)).getParentFile().getName() + ".zip");
            executor.setCommands(arrayList);
            if (!executor.execute().isEmpty()) {
                mDialog.a(executor.getLog());
            }
        }
    }

    public void link() {
        String stringBuffer = mDp.f.t + mDp.c + "Module";
        String stringBuffer2 = mDp.f.t + File.separator + "Resource";
        checkDir(stringBuffer);
        //mDialog.c("Linking Resource . . .");
        mergeResource(stringBuffer, stringBuffer2);
    }

    public void link(String directory, ArrayList<String> assetsDirectories, HashMap<String, String> metadata) {
        //String module = directory + File.separator + "Module";
        String resource = directory + File.separator + "res";
        //checkDir(module);
        mergeResources(resource);
    }

    private void mergeResource(String modulePath, String resourcePath) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mDp.i.getAbsolutePath());
        arrayList.add("link");
        arrayList.add("--proto-format");
        arrayList.add("--auto-add-overlay");
        arrayList.add("--min-sdk-version");
        arrayList.add(mDp.settings.getValue("min_sdk", "21"));
        arrayList.add("--allow-reserved-package-id");
        if (mDp.f.N.g) {
            arrayList.add("--no-version-vectors");
        }
        arrayList.add("--no-version-transitions");
        arrayList.add("--target-sdk-version");
        arrayList.add(mDp.settings.getValue("target_sdk", "28"));
        arrayList.add("--version-code");
        String str3 = mDp.f.l;
        arrayList.add((str3 == null || str3.isEmpty()) ? "1" : mDp.f.l);
        arrayList.add("--version-name");
        String str4 = mDp.f.m;
        arrayList.add((str4 == null || str4.isEmpty()) ? "1.0" : mDp.f.m);
        arrayList.add("-I");
        String value = mDp.settings.getValue("android_sdk", "");
        arrayList.add(value.isEmpty() ? mDp.o : value);
        arrayList.add("-A");
        arrayList.add(mDp.f.A);
        if (FileUtil.isExistFile(mDp.fpu.getPathAssets(mDp.f.b))) {
            arrayList.add("-A");
            arrayList.add(mDp.fpu.getPathAssets(mDp.f.b));
        }
        for (Jp next : mDp.n.a()) {
            if (next.d()) {
                arrayList.add("-A");
                arrayList.add(mDp.l.getAbsolutePath() + mDp.c + "libs" + mDp.c + next.a() + mDp.c + "assets");
            }
        }
        File[] listFiles = new File(resourcePath).listFiles();
        for (File file : listFiles) {
            if (!file.getName().equals("main.zip")) {
                arrayList.add("-R");
                arrayList.add(file.getAbsolutePath());
            }
        }
        arrayList.add("--manifest");
        arrayList.add(mDp.f.r);
        arrayList.add("--java");
        arrayList.add(mDp.f.v);
        String e = mDp.e();
        if (!e.isEmpty()) {
            arrayList.add("--extra-packages");
            arrayList.add(e);
        }
        arrayList.add("-o");
        arrayList.add(modulePath + mDp.c + "module.zip");
        arrayList.add(resourcePath + mDp.c + "main.zip");
        executor.setCommands(arrayList);
        if (!executor.execute().isEmpty()) {
            mDialog.a(executor.getLog());
        }
    }

    private void mergeResources(String resourcesPath) {
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
        commands.add("-A");
        commands.add(mDp.f.A);
        if (FileUtil.isExistFile(mDp.fpu.getPathAssets(mDp.f.b))) {
            commands.add("-A");
            commands.add(mDp.fpu.getPathAssets(mDp.f.b));
        }
        for (Jp next : mDp.n.a()) {
            if (next.d()) {
                commands.add("-A");
                commands.add(mDp.l.getAbsolutePath() + mDp.c + "libs" + mDp.c + next.a() + mDp.c + "assets");
            }
        }
        File[] listFiles = new File(resourcesPath).listFiles();
        for (File file : listFiles) {
            //if (!file.getName().equals("project.zip")) {
                commands.add("-R");
                commands.add(file.getAbsolutePath());
            //}
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
        //commands.add(modulePath + mDp.c + "module.zip");
        commands.add(mDp.f.C);
        Log.d(Dp.TAG, "Now executing: " + commands.toString());
        BinaryExecutor executor = new BinaryExecutor();
        executor.setCommands(commands);
        if (!executor.execute().isEmpty()) {
            Log.e(Dp.TAG, executor.getLog());
        }
    }

    public void extractModule() {
        String stringBuffer = mDp.f.t + mDp.c + "Module";
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(mDp.f.t + mDp.c + "Module" + mDp.c + "module.zip"));
            for (ZipEntry nextEntry = zipInputStream.getNextEntry(); nextEntry != null; nextEntry = zipInputStream.getNextEntry()) {
                if (nextEntry.getName().endsWith(mDp.c)) {
                    FileUtil.makeDir(stringBuffer + mDp.c + nextEntry.getName());
                } else if (nextEntry.getName().endsWith("AndroidManifest.xml")) {
                    FileUtil.makeDir(stringBuffer + mDp.c + "manifest");
                    FileUtil.extractFileFromZip(zipInputStream, new File(stringBuffer + mDp.c + "manifest" + mDp.c + nextEntry.getName()));
                } else {
                    new File(stringBuffer + mDp.c + nextEntry.getName()).getParentFile().mkdirs();
                    FileUtil.extractFileFromZip(zipInputStream, new File(stringBuffer + mDp.c + nextEntry.getName()));
                }
            }
            FileUtil.deleteFile(stringBuffer + mDp.c + "module.zip");
            FileUtil.makeDir(stringBuffer + mDp.c + "dex");
            FileUtil.makeDir(stringBuffer + mDp.c + "lib");
        } catch (IOException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            mDialog.a(stringWriter.toString());
        }
    }
}