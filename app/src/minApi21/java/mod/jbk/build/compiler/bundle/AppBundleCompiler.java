package mod.jbk.build.compiler.bundle;

import java.io.File;

import a.a.a.Dp;
import a.a.a.yq;

public class AppBundleCompiler {

    public AppBundleCompiler(Dp dp) {
    }

    public static File getDefaultAppBundleOutputFile(yq projectMetadata) {
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
