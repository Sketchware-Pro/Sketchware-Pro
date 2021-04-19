package a.a.a;

import android.view.View;
import com.besome.sketch.editor.manage.view.PresetSettingActivity;

public class Hw implements View.OnClickListener {

    public final aB a;
    public final PresetSettingActivity b;

    public Hw(PresetSettingActivity presetSettingActivity, aB aBVar) {
        this.b = presetSettingActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}