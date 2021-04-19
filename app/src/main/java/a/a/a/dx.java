package a.a.a;

import android.view.View;
import android.widget.EditText;
import com.besome.sketch.editor.property.PropertyInputItem;

public class dx implements View.OnClickListener {

    public final SB a;
    public final EditText b;
    public final aB c;
    public final PropertyInputItem d;

    public dx(PropertyInputItem propertyInputItem, SB sb, EditText editText, aB aBVar) {
        this.d = propertyInputItem;
        this.a = sb;
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