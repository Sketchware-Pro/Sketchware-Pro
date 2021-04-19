package a.a.a;

import android.widget.NumberPicker;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class AC implements NumberPicker.OnValueChangeListener {

    public final MyProjectSettingActivity a;

    public AC(MyProjectSettingActivity myProjectSettingActivity) {
        this.a = myProjectSettingActivity;
    }

    public void onValueChange(NumberPicker numberPicker, int i, int i2) {
        int i3;
        if (i > i2 && i2 < (i3 = this.a.Q)) {
            numberPicker.setValue(i3);
        }
    }
}