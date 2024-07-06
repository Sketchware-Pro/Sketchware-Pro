package mod.trindadedev.tools;

import android.content.Context;
import android.app.Activity;

import a.a.a.aB;
import com.sketchware.remod.R;

public class ApkSignatures {

    private Context context;
    private String abMsg;
    private ApkUtils projectApk;

    public ApkSignatures(Context context, String path) {
        this.context = context;
        projectApk = new ApkUtils(context);
        projectApk.setApkPath(path);
        try {
            abMsg = "SHA-1: " + getSha1() + "\n\nSHA-256: " + getSha256();
        } catch (Exception e) {
            abMsg = "Error to get signatures: " + e.getMessage();
        }
    }

    public String getSha1() {
        String sha1 = projectApk.getSHA1();
        return sha1 != null ? sha1 : "No SHA 1 key found.";
    }

    public String getSha256() {
        String sha256 = projectApk.getSHA256();
        return sha256 != null ? sha256 : "No SHA 256 key found.";
    }

    public void showSignaturesDialog() {
        aB signaturesDialog = new aB((Activity) context);
        signaturesDialog.b(context.getResources().getString(R.string.signatures_title));
        signaturesDialog.a(abMsg);
        signaturesDialog.setMessageIsSelectable(true);
        signaturesDialog.b(context.getResources().getString(R.string.common_word_ok), null);
        signaturesDialog.show();
    }
}