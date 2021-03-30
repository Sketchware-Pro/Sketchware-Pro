package mod.jbk.bundle;

import android.content.Context;
import android.util.Log;

import com.android.apksigner.ApkSignerTool;
import com.android.tools.build.bundletool.BundleToolMain;
import com.besome.sketch.design.DesignActivity;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import a.a.a.Dp;
import a.a.a.bB;
import mod.agus.jcoderz.lib.FileUtil;

public class BundleToolCompiler {

    public static final String BUNDLE_FILE_NAME = "bundle.aab";
    public static final String MODULE_ARCHIVE_FILE_NAME = "module-main.zip";
    public static final String MODULE_MAIN_ROOT = "module-main";
    public static final String MODULE_DEX_DIRECTORY = new File(MODULE_MAIN_ROOT, "dex").getAbsolutePath();
    public static final String MODULE_LIB_DIRECTORY = new File(MODULE_MAIN_ROOT, "lib").getAbsolutePath();
    public static final String MODULE_RES_DIRECTORY = new File(MODULE_MAIN_ROOT, "res").getAbsolutePath();
    public static final String MODULE_ROOT_DIRECTORY = new File(MODULE_MAIN_ROOT, "root").getAbsolutePath();
    public static final String MODULE_MANIFEST_DIRECTORY = new File(MODULE_MAIN_ROOT, "manifest").getAbsolutePath();
    private static final String TAG = "BundleToolCompiler";
    public static String APK_SET_FILE_NAME;
    private final DesignActivity.a buildingDialog;
    private final Dp mDp;
    public File mainModuleArchive;
    public File mainModuleDirectory;
    public File mainModuleAssetsDirectory;
    public File mainModuleDexDirectory;
    public File mainModuleLibDirectory;
    public File mainModuleManifestDirectory;
    public File mainModuleResDirectory;
    public File mainModuleRootDirectory;
    public File appBundle;
    public File apkSet;

    public BundleToolCompiler(Dp dp, DesignActivity.a designActivity) {
        mDp = dp;
        buildingDialog = designActivity;
        APK_SET_FILE_NAME = mDp.f.d + ".apks";
        mainModuleArchive = new File(mDp.f.t, MODULE_ARCHIVE_FILE_NAME);
        appBundle = new File(mDp.f.t, BUNDLE_FILE_NAME);
        apkSet = new File(mDp.f.t, APK_SET_FILE_NAME);
        prepareModuleDirectory();
    }

    public void buildApkSet() {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();
        args.add("build-apks");
        args.add("--bundle=" + appBundle.getAbsolutePath());
        args.add("--overwrite");
        args.add("--output=" + apkSet.getAbsolutePath());
        args.add("--mode=universal");
        args.add("--aapt2=" + mDp.aapt2Dir.getAbsolutePath());

        Log.d(TAG, "Running BundleToolMain with these arguments: " + args.toString());
        try {
            BundleToolMain.main(args.toArray(new String[0]));
        } catch (Exception e) {
            buildingDialog.a(e.toString());
            Log.e(TAG, "Failed to build APK Set: " + e.getMessage(), e);
        }
        Log.d(TAG, "Building APK Set took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void buildBundle() {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();
        args.add("build-bundle");
        args.add("--modules=" + mainModuleArchive.getAbsolutePath());
        args.add("--overwrite");
        args.add("--output=" + appBundle.getAbsolutePath());

        Log.d(TAG, "Running BundleToolMain with these arguments: " + args.toString());
        try {
            BundleToolMain.main(args.toArray(new String[0]));
        } catch (Exception e) {
            buildingDialog.a(e.toString());
            Log.e(TAG, "Failed to build bundle: " + e.getMessage(), e);
        }
        Log.d(TAG, "Building app bundle took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void extractInstallApkFromApkSet() {
        long savedTimeMillis = System.currentTimeMillis();
        try {
            ZipInputStream apkSetStream = new ZipInputStream(new FileInputStream(apkSet));
            ZipEntry entryInApkSet = apkSetStream.getNextEntry();

            while (entryInApkSet != null) {
                Log.d(TAG, "Entry in APK Set's name: " + entryInApkSet.getName());
                if (entryInApkSet.getName().equals("universal.apk")) {
                    FileUtil.writeBytes(new File(mDp.f.G), FileUtil.readFromInputStream(apkSetStream));
                }

                apkSetStream.closeEntry();
                entryInApkSet = apkSetStream.getNextEntry();
            }
        } catch (IOException e) {
            //TODO: Fix not working Toast (NPE)
            bB.b(mDp.e, e.getMessage(), 0).show();
            Log.e(TAG, e.getMessage(), e);
        }
        Log.d(TAG, "Extracting universal.apk from APK Set took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void signInstallApk() {
        try {
            Context c = mDp.e;
            String[] libsContent = c.getAssets().list("libs");
            for (String fileInLibs : libsContent) {
                if (fileInLibs.equals("testkey.zip")) {
                    ZipInputStream testkeyZip = new ZipInputStream(
                            c.getAssets().open(
                                    "libs" + File.separator
                                            + "testkey.zip"
                            )
                    );
                    ZipEntry entry = testkeyZip.getNextEntry();

                    while (entry != null) {
                        if (!entry.isDirectory()) {
                            FileUtil.writeBytes(
                                    new File(
                                            c.getFilesDir(),
                                            "tmp" + File.separator
                                                    + "testkey" + File.separator
                                                    + entry.getName()
                                    ),
                                    FileUtil.readFromInputStream(
                                            testkeyZip
                                    )
                            );
                        }

                        testkeyZip.closeEntry();
                        entry = testkeyZip.getNextEntry();
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Couldn't extract testkey from assets!");
        }
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();
        args.add("sign");
        args.add("--in");
        args.add(mDp.f.G);
        args.add("--out");
        args.add(mDp.f.H);
        args.add("--key");
        args.add((new File(mDp.l, "testkey")).getAbsolutePath() + mDp.c + "testkey.pk8");
        args.add("--cert");
        args.add((new File(mDp.l, "testkey")).getAbsolutePath() + mDp.c + "testkey.x509.pem");

        Log.d(TAG, "Running ApkSignerTool with these arguments: " + args.toString());
        try {
            ApkSignerTool.main(args.toArray(new String[0]));
        } catch (Exception e) {
            buildingDialog.a(e.toString());
            Log.e(TAG, "Failed to sign Install-APK: " + e.getMessage(), e);
        }
        Log.d(TAG, "Signing Install-APK took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void zipMainModule() {
        ZipFile mainModuleZip = new ZipFile(mainModuleArchive);
        File[] mainModuleDirectoryFiles = mainModuleDirectory.listFiles();
        if (mainModuleDirectoryFiles != null) {
            for (File file : mainModuleDirectoryFiles) {
                try {
                    if (file.isDirectory()) {
                        mainModuleZip.addFolder(file);
                    } else {
                        mainModuleZip.addFile(file);
                    }
                } catch (ZipException e) {
                    buildingDialog.a(e.toString());
                }
            }
        }
    }

    private void prepareModuleDirectory() {
        mainModuleDirectory = new File(mDp.f.t, "module-main");
        if (!mainModuleDirectory.exists()) {
            mainModuleDirectory.mkdirs();
        }

        mainModuleAssetsDirectory = new File(mainModuleDirectory, "assets");
        if (!mainModuleAssetsDirectory.exists()) {
            mainModuleAssetsDirectory.mkdir();
        }

        mainModuleDexDirectory = new File(mainModuleDirectory, "dex");
        if (!mainModuleDexDirectory.exists()) {
            mainModuleDexDirectory.mkdir();
        }

        mainModuleLibDirectory = new File(mainModuleDirectory, "lib");
        if (!mainModuleLibDirectory.exists()) {
            mainModuleLibDirectory.mkdir();
        }

        mainModuleManifestDirectory = new File(mainModuleDirectory, "manifest");
        if (!mainModuleManifestDirectory.exists()) {
            mainModuleManifestDirectory.mkdir();
        }

        mainModuleResDirectory = new File(mainModuleDirectory, "res");
        if (!mainModuleResDirectory.exists()) {
            mainModuleResDirectory.mkdir();
        }

        mainModuleRootDirectory = new File(mainModuleDirectory, "root");
        if (!mainModuleRootDirectory.exists()) {
            mainModuleRootDirectory.mkdirs();
        }
    }

    private void copyDexesToMainModuleDirectory() {
        File[] binDirectoryContent = new File(mDp.f.t).listFiles();
        if (binDirectoryContent != null) {
            for (File file : binDirectoryContent) {
                if (file.getName().endsWith(".dex")) {
                    FileUtil.copyFile(file.getAbsolutePath(), new File(mainModuleDexDirectory, file.getName()).getAbsolutePath());
                }
            }
        }
    }

    public void copyFilesToMainModuleDirectory() throws IOException {
        //TODO: Extract <project name>.apk.res to module-main directory directly and work from there instead of copying files
        copyDexesToMainModuleDirectory();

        File extractedResApkDirectory = new File(new File(mDp.f.C).getParent(), "resCompiled");
        FileUtil.extractZipTo(new ZipInputStream(new FileInputStream(mDp.f.C)), extractedResApkDirectory.getAbsolutePath());

        File[] resApkContents = extractedResApkDirectory.listFiles();
        if (resApkContents != null) {
            for (File resApkRootElement : resApkContents) {
                switch (resApkRootElement.getName()) {
                    case "assets":
                        FileUtil.copyDirectory(resApkRootElement, mainModuleAssetsDirectory);
                        break;

                    case "lib":
                        FileUtil.copyDirectory(resApkRootElement, mainModuleLibDirectory);
                        break;

                    case "res":
                        FileUtil.copyDirectory(resApkRootElement, mainModuleResDirectory);
                        break;

                    case "AndroidManifest.xml":
                        FileUtil.copyFile(resApkRootElement.getAbsolutePath(), new File(mainModuleManifestDirectory, "AndroidManifest.xml").getAbsolutePath());
                        break;

                    case "resources.pb":
                        FileUtil.copyFile(resApkRootElement.getAbsolutePath(), new File(mainModuleDirectory, "resources.pb").getAbsolutePath());
                        break;

                    default:
                        if (resApkRootElement.isFile()) {
                            FileUtil.copyFile(resApkRootElement.getAbsolutePath(), new File(mainModuleRootDirectory, resApkRootElement.getName()).getAbsolutePath());
                        } else {
                            FileUtil.copyDirectory(resApkRootElement, new File(mainModuleRootDirectory, resApkRootElement.getName()));
                        }
                        break;
                }
            }
        }
    }

    //TODO: clean up module-main while extracting Install-APK from APK Set
}
