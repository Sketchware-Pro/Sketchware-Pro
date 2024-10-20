package mod.ilyasse.utils;

import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class UI {
    public static void loadImageFromUrl(ImageView image, String url) {
        Glide.with(image.getContext())
                .load(url)
                .into(image);
    }

    public static void advancedCorners(View view, int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadii(new float[]{0, 0, 30, 30, 30, 30, 0, 0});
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
        GG.setColor(focus);
        GG.setCornerRadius((float) round);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{pressed}), GG, null);
        view.setBackground(RE);
    }
}
