package a.a.a;

import static pro.sketchware.utility.theme.ThemeManager.getColorFromTheme;
import static pro.sketchware.utility.theme.ThemeManager.getCurrentTheme;

import android.app.Dialog;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.Window;
import android.widget.TextView;

import androidx.core.view.WindowCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import pro.sketchware.R;

import mod.hey.studios.util.Helper;

public class ZA extends Dialog {

    private final LottieAnimationView animationView;

    public ZA(Context context) {
        super(context, R.style.progress);
        setContentView(R.layout.progress);
        animationView = findViewById(R.id.anim_sketchware);
        TextView tvProgress = findViewById(R.id.tv_progress);
        initColor(context);
        tvProgress.setText(Helper.getResString(R.string.common_message_loading));
        super.setCancelable(false);

        Window window = getWindow();
        if (window != null) {
            window.setStatusBarColor(0);
            WindowCompat.setDecorFitsSystemWindows(window, false);
        }
    }

    public void cancelAnimation() {
        if (animationView != null && animationView.isAnimating()) {
            animationView.cancelAnimation();
        }
    }

    public void pauseAnimation() {
        if (animationView != null && animationView.isAnimating()) {
            animationView.pauseAnimation();
        }
    }

    public void resumeAnimation() {
        if (animationView != null && !animationView.isAnimating()) {
            animationView.resumeAnimation();
        }
    }

    private void initColor(Context context){
        animationView.addValueCallback(
                new KeyPath("group1", "**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(getColorFromTheme(animationView.getContext(), getCurrentTheme(context), com.google.android.material.R.attr.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        );

        animationView.addValueCallback(
                new KeyPath("group2", "**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(getColorFromTheme(animationView.getContext(), getCurrentTheme(context), com.google.android.material.R.attr.colorOnSecondary), PorterDuff.Mode.SRC_ATOP)
        );
    }

}
