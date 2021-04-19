package a.a.a;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.besome.sketch.editor.property.PropertyIndentItem;

public class Yw implements View.OnClickListener {

    public final CheckBox a;
    public final EditText b;
    public final EditText c;
    public final EditText d;
    public final EditText e;
    public final EditText f;
    public final PropertyIndentItem g;

    public Yw(PropertyIndentItem propertyIndentItem, CheckBox checkBox, EditText editText, EditText editText2, EditText editText3, EditText editText4, EditText editText5) {
        this.g = propertyIndentItem;
        this.a = checkBox;
        this.b = editText;
        this.c = editText2;
        this.d = editText3;
        this.e = editText4;
        this.f = editText5;
    }

    public void onClick(View view) {
        if (this.a.isChecked()) {
            this.b.setEnabled(true);
            this.c.clearFocus();
            this.d.clearFocus();
            this.e.clearFocus();
            this.f.clearFocus();
            this.c.setEnabled(false);
            this.d.setEnabled(false);
            this.e.setEnabled(false);
            this.f.setEnabled(false);
            return;
        }
        this.b.clearFocus();
        this.b.setEnabled(false);
        this.c.setEnabled(true);
        this.d.setEnabled(true);
        this.e.setEnabled(true);
        this.f.setEnabled(true);
    }
}