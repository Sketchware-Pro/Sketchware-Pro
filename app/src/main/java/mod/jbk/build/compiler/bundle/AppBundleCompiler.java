package mod.jbk.build.compiler.bundle;

import android.content.Context;

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
import a.a.a.Jp;
import a.a.a.yq;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.jbk.util.LogUtil;

public class AppBundleCompiler {

    public static final String MODULE_ARCHIVE_FILE_NAME = "module-main.zip";
    public static final String MODULE_ASSETS = "assets";
    public static final String MODULE_DEX = "dex";
    public static final String MODULE_LIB = "lib";
    public static final String MODULE_RES = "res";
    public static final String MODULE_ROOT = "root";
    public static final String MODULE_MANIFEST = "manifest";

    private static final String TAG = AppBundleCompiler.class.getSimpleName();
    private final DesignActivity.a buildingDialog;
    private final Dp mDp;
    public File mainModuleArchive;
    public File appBundle;
    public File apkSet;

    public AppBundleCompiler(Dp dp, DesignActivity.a designActivityA) {
        buildingDialog = designActivityA;
        mDp = dp;
        mainModuleArchive = new File(dp.f.t, MODULE_ARCHIVE_FILE_NAME);
        appBundle = new File(dp.f.t, getBundleFilename(dp.f.d));
        apkSet = new File(dp.f.t, getApkSetFilename(dp.f.d));
    }

    public static String getApkSetFilename(String sc_id) {
        return sc_id + ".apks";
    }

    public static String getBundleFilename(String sc_id) {
        return sc_id + ".aab";
    }

    public static File getDefaultAppBundleOutputFile(Context context, String sc_id) {
        yq projectMetadata = new yq(context, sc_id);
        return new File(projectMetadata.t, projectMetadata.d + ".aab");
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

        LogUtil.d(TAG, "Running BundleToolMain with these arguments: " + args);
        try {
            BundleToolMain.main(args.toArray(new String[0]));
        } catch (Exception e) {
            if (buildingDialog != null) buildingDialog.aWithMessage(e.getMessage());
            LogUtil.e(TAG, "Failed to build APK Set: " + e.getMessage(), e);
        }
        LogUtil.d(TAG, "Building APK Set took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
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
            if (buildingDialog != null) buildingDialog.aWithMessage(e.getMessage());
            LogUtil.e(TAG, "Failed to extract Install APK from APK Set: " + e.getMessage(), e);
        }
        LogUtil.d(TAG, "Extracting universal.apk from APK Set took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    public void buildBundle() {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> args = new ArrayList<>();
        args.add("build-bundle");
        args.add("--modules=" + mainModuleArchive.getAbsolutePath());
        args.add("--overwrite");
        args.add("--output=" + appBundle.getAbsolutePath());
        if (mDp.proguard.isDebugFilesEnabled()) {
            /* Add ProGuard mapping if available for automatic import to ProGuard mappings in Google Play */
            File mapping = new File(mDp.f.printmapping);
            if (mapping.exists()) {
                args.add("--metadata-file=com.android.tools.build.obfuscation/proguard.map:" +
                        mapping.getAbsolutePath());
            }
        }

        LogUtil.d(TAG, "Running BundleToolMain with these arguments: " + args);
        try {
            BundleToolMain.main(args.toArray(new String[0]));
        } catch (Exception e) {
            if (buildingDialog != null) buildingDialog.aWithMessage(e.getMessage());
            LogUtil.e(TAG, "Failed to build bundle: " + e.getMessage(), e);
        }
        LogUtil.d(TAG, "Building app bundle took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }

    /**
     * Re-compresses &lt;project name&gt;.apk.res to module-main.zip in the right format.
     *
     * @throws IOException Thrown if any I/O exception occurs while creating the archive
     */
    public void createModuleMainArchive() throws IOException {
        /* Get an automatically closed FileOutputStream of module-main.zip */
        try (FileOutputStream mainModuleStream = new FileOutputStream(mainModuleArchive)) {
            /* Buffer writing to module-main.zip */
            try (BufferedOutputStream bufferedMainModuleStream = new BufferedOutputStream(mainModuleStream)) {
                /* Finally, use it as ZipOutputStream */
                try (ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedMainModuleStream)) {
                    /* Get an automatically closed FileInputStream of <project name>.apk.res */
                    try (FileInputStream apkResStream = new FileInputStream(mDp.f.C)) {
                        /* Create an automatically closed ZipInputStream of <project name>.apk.res */
                        try (ZipInputStream zipInputStream = new ZipInputStream(apkResStream)) {

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

                                int entryMethod = entry.getMethod();
                                toCompress.setMethod(entryMethod);
                                if (entryMethod == ZipEntry.STORED) {
                                    toCompress.setCompressedSize(entry.getCompressedSize());
                                    toCompress.setSize(entry.getSize());
                                    toCompress.setCrc(entry.getCrc());
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

                    File nativeLibrariesDirectory = new File(new FilePathUtil().getPathNativelibs(mDp.f.b));
                    File[] architectures = nativeLibrariesDirectory.listFiles();

                    if (architectures != null) {
                        for (File architecture : architectures) {
                            File[] nativeLibraries = architecture.listFiles();
                            if (nativeLibraries != null) {
                                for (File nativeLibrary : nativeLibraries) {
                                    /* Create a ZIP-entry of the native library */
                                    ZipEntry dexZipEntry = new ZipEntry(MODULE_LIB + File.separator +
                                            architecture.getName() + File.separator + nativeLibrary.getName());
                                    zipOutputStream.putNextEntry(dexZipEntry);

                                    /* Read the native binary and compress into module-main.zip */
                                    try (FileInputStream dexInputStream = new FileInputStream(nativeLibrary)) {
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
                    }

                    /* Start with enabled Local libraries' JARs */
                    ArrayList<File> jars = new ManageLocalLibrary(mDp.f.b).getLocalLibraryJars();

                    /* Add built-in libraries' JARs */
                    String prependToLibraryName = mDp.l.getAbsolutePath() + File.separator + mDp.m + File.separator;
                    String appendToLibraryName = File.separator + "classes.jar";
                    for (Jp library : mDp.n.a()) {
                        jars.add(new File(prependToLibraryName + library.a() + appendToLibraryName));
                    }

                    for (File jar : jars) {
                        try (FileInputStream jarStream = new FileInputStream(jar)) {
                            try (ZipInputStream jarArchiveStream = new ZipInputStream(jarStream)) {

                                ZipEntry jarArchiveEntry = jarArchiveStream.getNextEntry();

                                while (jarArchiveEntry != null) {
                                    String pathInJar = jarArchiveEntry.getName();
                                    if (!jarArchiveEntry.isDirectory() && !pathInJar.equals("META-INF/MANIFEST.MF") && !pathInJar.endsWith(".class")) {
                                        ZipEntry nonClassFileToAddToModule = new ZipEntry(MODULE_ROOT + File.separator +
                                                pathInJar);
                                        zipOutputStream.putNextEntry(nonClassFileToAddToModule);

                                        byte[] buffer = new byte[1024];
                                        int length;
                                        while ((length = jarArchiveStream.read(buffer)) > 0) {
                                            zipOutputStream.write(buffer, 0, length);
                                        }
                                        zipOutputStream.closeEntry();
                                    }

                                    jarArchiveEntry = jarArchiveStream.getNextEntry();
                                }
                            }
                        }
                    }
                }
            }
        }
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

        LogUtil.d(TAG, "Running ApkSignerTool with these arguments: " + args);
        try {
            ApkSignerTool.main(args.toArray(new String[0]));
        } catch (Exception e) {
            if (buildingDialog != null) buildingDialog.aWithMessage(e.getMessage());
            LogUtil.e(TAG, "Failed to sign Install-APK: " + e.getMessage(), e);
        }
        LogUtil.d(TAG, "Signing Install-APK took " + (System.currentTimeMillis() - savedTimeMillis) + " ms");
    }
}
