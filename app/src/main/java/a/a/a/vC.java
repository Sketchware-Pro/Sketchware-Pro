package a.a.a;

import android.content.DialogInterface;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class vC implements DialogInterface.OnClickListener {

    public final MyProjectSettingActivity a;

    public vC(MyProjectSettingActivity myProjectSettingActivity) {
        this.a = myProjectSettingActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == 0) {
            this.a.r();
        } else if (i == 1) {
            this.a.s();
        } else if (i == 2 && this.a.N) {
            this.a.n();
        }
    }
}