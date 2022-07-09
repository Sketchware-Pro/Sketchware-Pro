package mod.jbk.build.compiler.bundle;

import android.content.Context;

import com.android.tools.build.bundletool.commands.BuildBundleCommand;
import com.android.tools.build.bundletool.flags.FlagParser;

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
import a.a.a.zy;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.util.LogUtil;

public class AppBundleCompiler {

    private static final String MODULE_ARCHIVE_FILE_NAME = "module-main.zip";
    private static final String MODULE_ASSETS = "assets";
    private static final String MODULE_DEX = "dex";
    private static final String MODULE_LIB = "lib";
    private static final String MODULE_RES = "res";
    private static final String MODULE_ROOT = "root";
    private static final String MODULE_MANIFEST = "manifest";

    private static final String TAG = AppBundleCompiler.class.getSimpleName();
    private final Dp mDp;
    private final File mainModuleArchive;
    private final File appBundle;

    public AppBundleCompiler(Dp dp) {
        mDp = dp;
        mainModuleArchive = new File(dp.yq.binDirectoryPath, MODULE_ARCHIVE_FILE_NAME);
        appBundle = new File(dp.yq.binDirectoryPath, getBundleFilename(dp.yq.projectName));
    }

    public static String getBundleFilename(String sc_id) {
        return sc_id + ".aab";
    }

    public static File getDefaultAppBundleOutputFile(Context context, String sc_id) {
        yq projectMetadata = new yq(context, sc_id);
        return new File(projectMetadata.binDirectoryPath, projectMetadata.projectName + ".aab");
    }

    public void buildBundle() throws zy {
        long savedTimeMillis = System.currentTimeMillis();
        ArrayList<String> flags = new ArrayList<>();
        flags.add("--modules=" + mainModuleArchive.getAbsolutePath());
        flags.add("--overwrite");
        flags.add("--output=" + appBundle.getAbsolutePath());
        if (mDp.proguard.isDebugFilesEnabled()) {
            /* Add ProGuard mapping if available for automatic import to ProGuard mappings in Google Play */
            File mapping = new File(mDp.yq.proGuardMappingPath);
            if (mapping.exists()) {
                flags.add("--metadata-file=com.android.tools.build.obfuscation/proguard.map:" +
                        mapping.getAbsolutePath());
            }
        }

        LogUtil.d(TAG, "Running BuildBundleCommand with these flags: " + flags);
        try {
            BuildBundleCommand.fromFlags(new FlagParser().parse(flags.toArray(new String[0]))).execute();
        } catch (Exception e) {
            throw new zy("Failed to build bundle: " + e.getMessage());
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
                    try (FileInputStream apkResStream = new FileInputStream(mDp.yq.resourcesApkPath)) {
                        /* Create an automatically closed ZipInputStream of <project name>.apk.res */
                        try (ZipInputStream zipInputStream = new ZipInputStream(apkResStream)) {

                            /* First, compress DEX files into module-main.zip */
                            File[] binDirectoryContent = new File(mDp.yq.binDirectoryPath).listFiles();
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

                    File nativeLibrariesDirectory = new File(new FilePathUtil().getPathNativelibs(mDp.yq.sc_id));
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
                    ArrayList<File> jars = new ManageLocalLibrary(mDp.yq.sc_id).getLocalLibraryJars();

                    /* Add built-in libraries' JARs */
                    for (Jp library : mDp.builtInLibraryManager.a()) {
                        jars.add(BuiltInLibraries.getLibraryClassesJarPath(library.a()));
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
}
