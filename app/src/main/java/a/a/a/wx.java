package a.a.a;

import android.view.View;
import android.widget.EditText;
import com.besome.sketch.editor.property.PropertySizeItem;

public class wx implements View.OnClickListener {

    public final TB a;
    public final EditText b;
    public final aB c;
    public final PropertySizeItem d;

    public wx(PropertySizeItem propertySizeItem, TB tb, EditText editText, aB aBVar) {
        this.d = propertySizeItem;
        this.a = tb;
        this.b = editText;
        this.c = aBVar;
    }

    public void onClick(View view) {
        if (this.a.b()) {
            this.d.setValue(Integer.valueOf(this.b.getText().toString()).intValue());
            if (this.d.j != null) {
                this.d.j.a(this.d.b, Integer.valueOf(this.d.c));
            }
            this.c.dismiss();
        }
    }
}