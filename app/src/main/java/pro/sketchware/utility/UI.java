package pro.sketchware.utility;

import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class UI {
    public static void loadImageFromUrl(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .into(image);
    }

    public static void advancedCorners(View view, int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
        gd.setColor(color);
        view.setBackground(gd);
    }

    public static void shadAnim(View view, String propertyName, double value, double duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(view);
        anim.setPropertyName(propertyName);
        anim.setFloatValues((float) value);
        anim.setDuration((long) duration);
        anim.start();
    }

    public static void animateLayoutChanges(ViewGroup view) {
        AutoTransition autoTransition = new AutoTransition();
        autoTransition.setDuration(300);
        TransitionManager.beginDelayedTransition(view, autoTransition);
    }

    public static void rippleRound(View view, int focus, int pressed, double round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setCornerRadius((float) round);
        GG.setColor(focus);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{pressed}), GG, null);
        view.setBackground(RE);
    }

    public static void addSystemWindowInsetToPadding(
            View view, boolean left, boolean top, boolean right, boolean bottom) {
        final int initialLeft = view.getPaddingLeft();
        final int initialTop = view.getPaddingTop();
        final int initialRight = view.getPaddingRight();
        final int initialBottom = view.getPaddingBottom();

        ViewCompat.setOnApplyWindowInsetsListener(
                view,
                (v, windowInsets) -> {
                    Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                    view.setPadding(
                            initialLeft + (left ? insets.left : 0),
                            initialTop + (top ? insets.top : 0),
                            initialRight + (right ? insets.right : 0),
                            initialBottom + (bottom ? insets.bottom : 0));
                    return windowInsets;
                });
    }
}
