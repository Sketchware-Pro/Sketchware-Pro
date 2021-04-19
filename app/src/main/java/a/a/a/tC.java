package a.a.a;

import android.view.View;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class tC implements View.OnClickListener {

    public final MyProjectSettingActivity a;

    public tC(MyProjectSettingActivity myProjectSettingActivity) {
        this.a = myProjectSettingActivity;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.a.g(((Integer) view.getTag()).intValue());
        }
    }
}