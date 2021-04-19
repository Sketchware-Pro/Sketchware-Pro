package a.a.a;

import android.view.View;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class yC implements View.OnClickListener {

    public final aB a;
    public final MyProjectSettingActivity b;

    public yC(MyProjectSettingActivity myProjectSettingActivity, aB aBVar) {
        this.b = myProjectSettingActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}