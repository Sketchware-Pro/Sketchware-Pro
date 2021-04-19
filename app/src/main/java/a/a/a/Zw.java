package a.a.a;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.besome.sketch.editor.property.PropertyIndentItem;

public class Zw implements TextWatcher {

    public final TB a;
    public final EditText b;
    public final TB c;
    public final TB d;
    public final TB e;
    public final PropertyIndentItem f;

    public Zw(PropertyIndentItem propertyIndentItem, TB tb, EditText editText, TB tb2, TB tb3, TB tb4) {
        this.f = propertyIndentItem;
        this.a = tb;
        this.b = editText;
        this.c = tb2;
        this.d = tb3;
        this.e = tb4;
    }

    public void afterTextChanged(Editable editable) {
        this.a.a(this.b.getText().toString());
        this.c.a(this.b.getText().toString());
        this.d.a(this.b.getText().toString());
        this.e.a(this.b.getText().toString());
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}