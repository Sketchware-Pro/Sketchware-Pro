package a.a.a;

import android.animation.Animator;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class zC implements Animator.AnimatorListener {

    public final MyProjectSettingActivity a;

    public zC(MyProjectSettingActivity myProjectSettingActivity) {
        this.a = myProjectSettingActivity;
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationEnd(Animator animator) {
        this.a.G.setVisibility(8);
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
    }
}