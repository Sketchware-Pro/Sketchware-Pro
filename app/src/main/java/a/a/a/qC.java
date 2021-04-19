package a.a.a;

import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.besome.sketch.projects.MyProjectSettingActivity;

public class qC implements View.OnClickListener {

    public final NumberPicker a;
    public final aB b;
    public final MyProjectSettingActivity c;

    public qC(MyProjectSettingActivity myProjectSettingActivity, NumberPicker numberPicker, aB aBVar) {
        this.c = myProjectSettingActivity;
        this.a = numberPicker;
        this.b = aBVar;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.c.I.setText(String.valueOf(this.a.getValue()));
            TextView textView = this.c.J;
            textView.setText(String.valueOf(this.c.U) + "." + String.valueOf(this.c.V));
            this.b.dismiss();
        }
    }
}