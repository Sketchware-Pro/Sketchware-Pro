package a.a.a;

import android.widget.NumberPicker;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class oC implements NumberPicker.OnValueChangeListener {

    public final NumberPicker a;
    public final MyProjectSettingActivity b;

    public oC(MyProjectSettingActivity myProjectSettingActivity, NumberPicker numberPicker) {
        this.b = myProjectSettingActivity;
        this.a = numberPicker;
    }

    public void onValueChange(NumberPicker numberPicker, int i, int i2) {
        MyProjectSettingActivity myProjectSettingActivity = this.b;
        myProjectSettingActivity.U = i2;
        if (i > i2) {
            int i3 = myProjectSettingActivity.S;
            if (i2 < i3) {
                numberPicker.setValue(i3);
            }
            MyProjectSettingActivity myProjectSettingActivity2 = this.b;
            if (myProjectSettingActivity2.U == myProjectSettingActivity2.S || myProjectSettingActivity2.V <= myProjectSettingActivity2.T) {
                this.a.setValue(this.b.T);
                MyProjectSettingActivity myProjectSettingActivity3 = this.b;
                myProjectSettingActivity3.V = myProjectSettingActivity3.T;
            }
        }
    }
}