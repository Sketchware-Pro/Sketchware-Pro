package a.a.a;

import android.widget.NumberPicker;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class pC implements NumberPicker.OnValueChangeListener {

    public final MyProjectSettingActivity a;

    public pC(MyProjectSettingActivity myProjectSettingActivity) {
        this.a = myProjectSettingActivity;
    }

    public void onValueChange(NumberPicker numberPicker, int i, int i2) {
        int i3;
        MyProjectSettingActivity myProjectSettingActivity = this.a;
        myProjectSettingActivity.V = i2;
        if (i > i2 && i2 < (i3 = myProjectSettingActivity.T) && myProjectSettingActivity.U < myProjectSettingActivity.S) {
            numberPicker.setValue(i3);
        }
    }
}