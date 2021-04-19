package a.a.a;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

import com.besome.sketch.projects.MyProjectButtonLayout;

public class mC extends AnimatorListenerAdapter {

    public final MyProjectButtonLayout a;

    public mC(MyProjectButtonLayout myProjectButtonLayout) {
        this.a = myProjectButtonLayout;
    }

    public void onAnimationEnd(Animator animator) {
        this.a.p.setEnabled(false);
        this.a.e.setEnabled(true);
        this.a.h.setVisibility(View.INVISIBLE);
    }

    public void onAnimationStart(Animator animator) {
        super.onAnimationStart(animator);
        this.a.b.setVisibility(View.VISIBLE);
        this.a.p.setEnabled(false);
        this.a.e.setEnabled(false);
    }
}