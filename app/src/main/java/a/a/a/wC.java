package a.a.a;

import android.view.View;

import com.besome.sketch.projects.MyProjectSettingActivity;

public class wC implements View.OnClickListener {

    public final aB a;
    public final MyProjectSettingActivity b;

    public wC(MyProjectSettingActivity myProjectSettingActivity, aB aBVar) {
        this.b = myProjectSettingActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.b.H.setImageResource(000000000); // (R.drawable.default_icon); // replace this with sk default icon.
            this.b.N = false;
            this.a.dismiss();
        }
    }
}