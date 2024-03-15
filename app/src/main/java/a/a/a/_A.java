package a.a.a;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.sketchware.remod.R;

import java.util.Objects;

public class _A extends Dialog {

    private final TextView tvProgress;
    private boolean isCancelable;

    public _A(Context var1) {
        super(var1);
        Objects.requireNonNull(getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_msg_box);
        setTitle(xB.b().a(var1, R.string.common_message_progress));
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        tvProgress.setText(xB.b().a(var1, R.string.common_message_loading));
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(true);
    }

    public void a(String var1) {
        tvProgress.setText(var1);
    }

    public void a(boolean var1) {
        isCancelable = var1;
    }

    public boolean a() {
        return isCancelable;
    }

    @Override
    public void onBackPressed() {
        if (!isCancelable) {
            super.onBackPressed();
        }
    }
}
