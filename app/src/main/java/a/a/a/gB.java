package a.a.a;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

public class gB {
    public static void a(View view, float rotation, int duration, int delay, Animator.AnimatorListener listener) {
        view.animate().setListener(listener).rotation(rotation)
                .setStartDelay(delay).setDuration(duration).start();
    }

    public static void a(View view, float rotation, Animator.AnimatorListener listener) {
        view.animate().setListener(listener).rotation(rotation).start();
    }

    public static void a(View view, int duration) {
        int color = Color.parseColor("#b2000000");
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), 0, color);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(animation -> {
            view.setBackgroundColor((int) animation.getAnimatedValue());
        });
        valueAnimator.start();
    }

    public static void a(View view, int duration, int startDelay, Animator.AnimatorListener listener) {
        view.setTranslationY((float) (-view.getMeasuredHeight() * 5));
        view.animate().translationY(0.0F)
                .setInterpolator(new BounceInterpolator())
                .setDuration(duration)
                .setStartDelay(startDelay)
                .setListener(null).start();
    }

    public static void a(ViewGroup viewGroup, int duration, Animator.AnimatorListener listener) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(viewGroup.getHeight(), 0);
        valueAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();

            ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
            params.height = animatedValue;

            viewGroup.setLayoutParams(params);
        });
        valueAnimator.addListener(listener);
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void b(View view, int duration, int startDelay, Animator.AnimatorListener listener) {
        view.setAlpha(0.0F);
        view.setTranslationY(140.0F);
        view.animate().setListener(listener)
                .alpha(1.0F)
                .translationY(0.0F)
                .setStartDelay(startDelay)
                .setDuration(duration).start();
    }

    public static void b(ViewGroup viewGroup, int duration, Animator.AnimatorListener listener) {
        viewGroup.measure(-1, -2);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, viewGroup.getMeasuredHeight());
        valueAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();

            ViewGroup.LayoutParams params = viewGroup.getLayoutParams();
            params.height = animatedValue;

            viewGroup.setLayoutParams(params);
        });
        if (listener != null) {
            valueAnimator.addListener(listener);
        }

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void c(View view, int duration, int startDelay, Animator.AnimatorListener listener) {
        view.setTranslationX((float) (-view.getMeasuredWidth()));
        view.animate().translationX(0.0F).setStartDelay(startDelay).setDuration(duration).setListener(listener).start();
    }

    public static void d(View view, int duration, int startDelay, Animator.AnimatorListener listener) {
        view.setTranslationX((float) view.getMeasuredWidth());
        view.animate().translationX(0.0F)
                .setStartDelay(startDelay)
                .setDuration(duration)
                .setListener(listener)
                .start();
    }
}
