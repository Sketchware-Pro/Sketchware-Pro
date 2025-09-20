package a.a.a;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

public class gB {
    public static void a(View var0, float var1, int var2, int var3, Animator.AnimatorListener var4) {
        var0.animate().setListener(var4).rotation(var1).setStartDelay((long) var3).setDuration((long) var2).start();
    }

    public static void a(View var0, float var1, Animator.AnimatorListener var2) {
        var0.animate().setListener(var2).rotation(var1).start();
    }

    public static void a(View var0, int var1) {
        int var2 = Color.parseColor("#b2000000");
        ValueAnimator var3 = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{0, var2});
        var3.setDuration((long) var1);
        var3.addUpdateListener(new fB(var0));
        var3.start();
    }

    public static void a(View var0, int var1, int var2, Animator.AnimatorListener var3) {
        var0.setTranslationY((float) (-var0.getMeasuredHeight() * 5));
        var0.animate().translationY(0.0F).setInterpolator(new BounceInterpolator()).setDuration((long) var1).setStartDelay((long) var2).setListener((Animator.AnimatorListener) null).start();
    }

    public static void a(ViewGroup var0, int var1, Animator.AnimatorListener var2) {
        ValueAnimator var3 = ValueAnimator.ofInt(new int[]{var0.getHeight(), 0});
        var3.addUpdateListener(new eB(var0));
        var3.addListener(var2);
        var3.setDuration((long) var1);
        var3.start();
    }

    public static void b(View var0, int var1, int var2, Animator.AnimatorListener var3) {
        var0.setAlpha(0.0F);
        var0.setTranslationY(140.0F);
        var0.animate().setListener(var3).alpha(1.0F).translationY(0.0F).setStartDelay((long) var2).setDuration((long) var1).start();
    }

    public static void b(ViewGroup var0, int var1, Animator.AnimatorListener var2) {
        var0.measure(-1, -2);
        ValueAnimator var3 = ValueAnimator.ofInt(new int[]{0, var0.getMeasuredHeight()});
        var3.addUpdateListener(new dB(var0));
        if (var2 != null) {
            var3.addListener(var2);
        }

        var3.setDuration((long) var1);
        var3.start();
    }

    public static void c(View var0, int var1, int var2, Animator.AnimatorListener var3) {
        var0.setTranslationX((float) (-var0.getMeasuredWidth()));
        var0.animate().translationX(0.0F).setStartDelay((long) var2).setDuration((long) var1).setListener(var3).start();
    }

    public static void d(View var0, int var1, int var2, Animator.AnimatorListener var3) {
        var0.setTranslationX((float) var0.getMeasuredWidth());
        var0.animate().translationX(0.0F).setStartDelay((long) var2).setDuration((long) var1).setListener(var3).start();
    }
}
