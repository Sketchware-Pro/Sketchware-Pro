package a.a.a;

import android.view.View;
import android.widget.EditText;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class sC implements View.OnFocusChangeListener {

    public final MyProjectSettingActivity a;

    public sC(MyProjectSettingActivity myProjectSettingActivity) {
        this.a = myProjectSettingActivity;
    }

    public void onFocusChange(View view, boolean z) {
        if (z) {
            EditText editText = (EditText) view;
            if (!this.a.W && !editText.getText().toString().trim().contains("com.my.newproject")) {
                this.a.u();
            }
        }
    }
}