package mod.agus.jcoderz.lib;

import com.besome.sketch.design.DesignActivity;

import java.io.File;

import a.a.a.Dp;
import a.a.a.KB;

public class AssetsExtractor {

    private final DesignActivity.a mDialog;
    private final Dp mDp;

    public AssetsExtractor(Dp dp, DesignActivity.a aVar) {
        mDp = dp;
        mDialog = aVar;
    }

    public void extractFiles() {
        //mDialog.c("Extracting Additional Files . . .");
        String absolutePath = new File(mDp.l, "testkey.zip").getAbsolutePath();
        String absolutePath2 = new File(mDp.l, "testkey").getAbsolutePath();
        if (mDp.a("libs" + mDp.c + "testkey.zip", absolutePath)) {
            mDp.g.b(absolutePath2);
            mDp.g.f(absolutePath2);
            new KB().a(absolutePath, absolutePath2);
        }
        String absolutePath3 = new File(mDp.l, "rt.jar.zip").getAbsolutePath();
        if (mDp.a("libs" + mDp.c + "rt.jar.zip", absolutePath3)) {
            mDp.g.c(mDp.l.getAbsolutePath() + mDp.c + "rt.jar");
            new KB().a(absolutePath3, mDp.l.getAbsolutePath());
        }
    }
}