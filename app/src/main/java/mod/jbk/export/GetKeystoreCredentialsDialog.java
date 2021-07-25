package mod.jbk.export;

import android.app.Activity;
import android.util.Pair;

import java.util.EventListener;

import a.a.a.aB;
import mod.w3wide.dialog.SketchDialog;

public class GetKeystoreCredentialsDialog extends SketchDialog {

    private final Activity activity;
    private String noticeText = "";
    private EventListener listener;

    public GetKeystoreCredentialsDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public GetKeystoreCredentialsDialog(Activity activity, String noticeText) {
        this(activity);
        this.noticeText = noticeText;
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public interface CredentialsReceiver {
        void gotCredentials(Pair<String, String> credentials);
    }
}
