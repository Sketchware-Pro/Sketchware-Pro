package a.a.a;

import android.view.View;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class xC implements View.OnClickListener {

    public final aB a;
    public final MyProjectSettingActivity b;

    public xC(MyProjectSettingActivity myProjectSettingActivity, aB aBVar) {
        this.b = myProjectSettingActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}