package mod.jbk.build.compiler.bundle;

import android.content.Context;

import com.besome.sketch.design.DesignActivity.BuildAsyncTask;

import java.io.File;
import java.io.IOException;

import a.a.a.Dp;
import a.a.a.yq;

public class AppBundleCompiler {

    public static final String MODULE_ARCHIVE_FILE_NAME = "module-main.zip";
    public static final String MODULE_ASSETS = "assets";
    public static final String MODULE_DEX = "dex";
    public static final String MODULE_LIB = "lib";
    public static final String MODULE_RES = "res";
    public static final String MODULE_ROOT = "root";
    public static final String MODULE_MANIFEST = "manifest";

    public File mainModuleArchive;
    public File appBundle;
    public File apkSet;

    public AppBundleCompiler(Dp dp, BuildAsyncTask designActivityBuildAsyncTask) {
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

    public void buildBundle() {
        throw new UnsupportedOperationException("Not built to support exporting AABs!");
    }

    /**
     * Re-compresses &lt;project name&gt;.apk.res to module-main.zip in the right format.
     *
     * @throws IOException Thrown if any I/O exception occurs while creating the archive
     */
    public void createModuleMainArchive() throws IOException {
        throw new UnsupportedOperationException("Not built to support exporting AABs!");
    }
}
