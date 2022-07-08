package mod.jbk.build.compiler.bundle;

import android.content.Context;

import java.io.File;

import a.a.a.Dp;
import a.a.a.yq;

public class AppBundleCompiler {

    public AppBundleCompiler(Dp dp) {
    }

    public static File getDefaultAppBundleOutputFile(Context context, String sc_id) {
        yq projectMetadata = new yq(context, sc_id);
        return new File(projectMetadata.binDirectoryPath, projectMetadata.projectName + ".aab");
    }

    public void buildBundle() {
        throw new UnsupportedOperationException("Not built to support exporting AABs!");
    }

    /**
     * Re-compresses &lt;project name&gt;.apk.res to module-main.zip in the right format.
     */
    public void createModuleMainArchive() {
        throw new UnsupportedOperationException("Not built to support exporting AABs!");
    }
}
