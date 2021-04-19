package a.a.a;

import android.view.View;
import android.widget.EditText;
import com.besome.sketch.editor.property.PropertyInputItem;

public class fx implements View.OnClickListener {

    public final TB a;
    public final EditText b;
    public final aB c;
    public final PropertyInputItem d;

    public fx(PropertyInputItem propertyInputItem, TB tb, EditText editText, aB aBVar) {
        this.d = propertyInputItem;
        this.a = tb;
        this.b = editText;
        this.c = aBVar;
    }

    public void onClick(View view) {
        if (this.a.b()) {
            this.d.setValue(this.b.getText().toString());
            if (this.d.l != null) {
                this.d.l.a(this.d.b, this.d.c);
            }
            this.c.dismiss();
        }
    }
}