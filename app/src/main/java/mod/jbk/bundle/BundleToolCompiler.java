package mod.jbk.bundle;

import android.util.Log;

import com.android.apksigner.ApkSignerTool;
import com.android.tools.build.bundletool.BundleToolMain;
import com.besome.sketch.design.DesignActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import a.a.a.Dp;
import mod.agus.jcoderz.lib.FileUtil;

public class BundleToolCompiler {

    public static final String MODULE_ARCHIVE_FILE_NAME = "module-main.zip";
    public static final String MODULE_ASSETS = "assets";
    public static final String MODULE_DEX = "dex";
    public static final String MODULE_LIB = "lib";
    public static final String MODULE_RES = "res";
    public static final String MODULE_ROOT = "root";
    public static final String MODULE_MANIFEST = "manifest";
    private static final String TAG = "BundleToolCompiler";
    public static String BUNDLE_FILE_NAME;
    public static String APK_SET_FILE_NAME;
    private final DesignActivity.a buildingDialog;
    private final Dp mDp;
    public File mainModuleArchive;
    public File appBundle;
    public File apkSet;

    public BundleToolCompiler(Dp dp, DesignActivity.a designActivityA) {
        BUNDLE_FILE_NAME = dp.f.d + ".aab";
        APK_SET_FILE_NAME = dp.f.d + ".apks";
        buildingDialog = designActivityA;
        mDp = dp;
        mainModuleArchive = new File(mDp.f.t, MODULE_ARCHIVE_FILE_NAME);
        appBundle = new File(mDp.f.t, BUNDLE_FILE_NAME);
        apkSet = new File(mDp.f.t, APK_SET_FILE_NAME);
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
            buildingDialog.aWithMessage(e.getMessage());
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
            buildingDialog.aWithMessage(e.getMessage());
            Log.e(TAG, "Failed to build bundle: " + e.getMessage(), e);
        }
        Log.d(TAG, "Building app bundle took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void extractInstallApkFromApkSet() {
        long savedTimeMillis = System.currentTimeMillis();
        try (FileInputStream apkSetInputStream = new FileInputStream(apkSet)) {
            try (ZipInputStream apkSetStream = new ZipInputStream(apkSetInputStream)) {
                ZipEntry entryInApkSet = apkSetStream.getNextEntry();

                while (entryInApkSet != null) {
                    if (entryInApkSet.getName().equals("universal.apk")) {
                        FileUtil.writeBytes(new File(mDp.f.G), FileUtil.readFromInputStream(apkSetStream));
                    }

                    apkSetStream.closeEntry();
                    entryInApkSet = apkSetStream.getNextEntry();
                }
            }
        } catch (IOException e) {
            buildingDialog.aWithMessage(e.getMessage());
            Log.e(TAG, "Failed to extract Install APK from APK Set: " + e.getMessage(), e);
        }
        Log.d(TAG, "Extracting universal.apk from APK Set took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void signInstallApk() {
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
            buildingDialog.aWithMessage(e.getMessage());
            Log.e(TAG, "Failed to sign Install-APK: " + e.getMessage(), e);
        }
        Log.d(TAG, "Signing Install-APK took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    /**
     * Re-compresses <project name>.apk.res to module-main.zip in the right format.
     *
     * @throws IOException Thrown if any I/O exception occurs while creating the archive
     */
    public void createModuleMainArchive() throws IOException {
        /* Get an automatically closed FileInputStream of <project name>.apk.res */
        try (FileInputStream apkResStream = new FileInputStream(mDp.f.C)) {
            /* Create an automatically closed ZipInputStream of <project name>.apk.res */
            try (ZipInputStream zipInputStream = new ZipInputStream(apkResStream)) {
                /* Get an automatically closed FileOutputStream of module-main.zip */
                try (FileOutputStream mainModuleStream = new FileOutputStream(mainModuleArchive)) {
                    /* Buffer writing to module-main.zip */
                    try (BufferedOutputStream bufferedMainModuleStream = new BufferedOutputStream(mainModuleStream)) {
                        /* Finally, use it as ZipOutputStream */
                        try (ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedMainModuleStream)) {

                            /* First, compress DEX files into module-main.zip */
                            File[] binDirectoryContent = new File(mDp.f.t).listFiles();
                            if (binDirectoryContent != null) {
                                for (File file : binDirectoryContent) {
                                    if (file.isFile() && file.getName().endsWith(".dex")) {
                                        /* Create a ZIP-entry of the DEX file */
                                        ZipEntry dexZipEntry = new ZipEntry(MODULE_DEX + File.separator + file.getName());
                                        zipOutputStream.putNextEntry(dexZipEntry);

                                        /* Read the DEX file and compress into module-main.zip */
                                        try (FileInputStream dexInputStream = new FileInputStream(file)) {
                                            byte[] buffer = new byte[1024];
                                            int length;
                                            while ((length = dexInputStream.read(buffer)) > 0) {
                                                zipOutputStream.write(buffer, 0, length);
                                            }
                                        }
                                        zipOutputStream.closeEntry();
                                    }
                                }
                            }

                            ZipEntry entry = zipInputStream.getNextEntry();

                            while (entry != null) {
                                ZipEntry toCompress;
                                if (entry.getName().startsWith("assets/")) {
                                    String entryName = entry.getName().substring(7);
                                    toCompress = new ZipEntry(MODULE_ASSETS + File.separator + entryName);
                                } else if (entry.getName().startsWith("lib/")) {
                                    String entryName = entry.getName().substring(4);
                                    toCompress = new ZipEntry(MODULE_LIB + File.separator + entryName);
                                } else if (entry.getName().startsWith("res/")) {
                                    String entryName = entry.getName().substring(4);
                                    toCompress = new ZipEntry(MODULE_RES + File.separator + entryName);
                                } else if (entry.getName().equals("AndroidManifest.xml")) {
                                    toCompress = new ZipEntry(MODULE_MANIFEST + File.separator + "AndroidManifest.xml");
                                } else if (entry.getName().equals("resources.pb")) {
                                    toCompress = new ZipEntry("resources.pb");
                                } else {
                                    String entryName = entry.getName();
                                    toCompress = new ZipEntry(MODULE_ROOT + File.separator + entryName);
                                }
                                zipOutputStream.putNextEntry(toCompress);

                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = zipInputStream.read(buffer)) > 0) {
                                    zipOutputStream.write(buffer, 0, length);
                                }

                                zipOutputStream.closeEntry();
                                zipInputStream.closeEntry();
                                entry = zipInputStream.getNextEntry();
                            }
                            bufferedMainModuleStream.flush();
                        }
                    }
                }
            }
        }
    }
}
