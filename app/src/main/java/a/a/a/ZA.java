package a.a.a;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class ZA extends Dialog {

    private final LottieAnimationView animationView;

    public ZA(Context context) {
        super(context, R.style.progress);
        setContentView(R.layout.progress);
        animationView = findViewById(R.id.anim_sketchware);
        TextView tvProgress = findViewById(R.id.tv_progress);
        tvProgress.setText(Helper.getResString(R.string.common_message_loading));
        super.setCancelable(false);
    }

    public void a() {
        if (animationView != null & animationView.h()) {
            animationView.e();
        }
    }

    public void b() {
        if (animationView != null & animationView.h()) {
            animationView.i();
        }
    }

    public void c() {
        if (animationView != null & !animationView.h()) {
            animationView.l();
        }
    }

    public void d() {
        super.setCancelable(true);
    }

    @Override
    public void show() {
        super.show();
    }
}
