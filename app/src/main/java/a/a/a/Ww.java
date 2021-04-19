package a.a.a;

import android.view.View;
import android.widget.CheckBox;
import com.besome.sketch.editor.property.PropertyGravityItem;

public class Ww implements View.OnClickListener {

    public final CheckBox a;
    public final CheckBox b;
    public final CheckBox c;
    public final CheckBox d;
    public final CheckBox e;
    public final CheckBox f;
    public final aB g;
    public final PropertyGravityItem h;

    public Ww(PropertyGravityItem propertyGravityItem, CheckBox checkBox, CheckBox checkBox2, CheckBox checkBox3, CheckBox checkBox4, CheckBox checkBox5, CheckBox checkBox6, aB aBVar) {
        this.h = propertyGravityItem;
        this.a = checkBox;
        this.b = checkBox2;
        this.c = checkBox3;
        this.d = checkBox4;
        this.e = checkBox5;
        this.f = checkBox6;
        this.g = aBVar;
    }

    public void onClick(View view) {
        int i = this.a.isChecked() ? 3 : 0;
        if (this.b.isChecked()) {
            i |= 5;
        }
        if (this.c.isChecked()) {
            i |= 1;
        }
        if (this.d.isChecked()) {
            i |= 48;
        }
        if (this.e.isChecked()) {
            i |= 80;
        }
        if (this.f.isChecked()) {
            i |= 16;
        }
        this.h.setValue(i);
        if (this.h.i != null) {
            this.h.i.a(this.h.a, Integer.valueOf(this.h.b));
        }
        this.g.dismiss();
    }
}