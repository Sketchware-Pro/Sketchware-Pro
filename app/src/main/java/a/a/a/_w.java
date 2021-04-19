package a.a.a;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.besome.sketch.editor.property.PropertyIndentItem;

public class _w implements View.OnClickListener {

    public final CheckBox a;
    public final TB b;
    public final TB c;
    public final TB d;
    public final TB e;
    public final TB f;
    public final EditText g;
    public final EditText h;
    public final EditText i;
    public final EditText j;
    public final aB k;
    public final PropertyIndentItem l;

    public _w(PropertyIndentItem propertyIndentItem, CheckBox checkBox, TB tb, TB tb2, TB tb3, TB tb4, TB tb5, EditText editText, EditText editText2, EditText editText3, EditText editText4, aB aBVar) {
        this.l = propertyIndentItem;
        this.a = checkBox;
        this.b = tb;
        this.c = tb2;
        this.d = tb3;
        this.e = tb4;
        this.f = tb5;
        this.g = editText;
        this.h = editText2;
        this.i = editText3;
        this.j = editText4;
        this.k = aBVar;
    }

    public void onClick(View view) {
        if (this.a.isChecked()) {
            if (this.b.b() && this.c.b() && this.d.b() && this.e.b() && this.f.b()) {
                int intValue = Integer.valueOf(this.g.getText().toString()).intValue();
                int intValue2 = Integer.valueOf(this.h.getText().toString()).intValue();
                int intValue3 = Integer.valueOf(this.i.getText().toString()).intValue();
                int intValue4 = Integer.valueOf(this.j.getText().toString()).intValue();
                this.l.a(intValue, intValue2, intValue3, intValue4);
                if (this.l.n != null) {
                    this.l.n.a(this.l.b, new int[]{intValue, intValue2, intValue3, intValue4});
                }
                this.k.dismiss();
            }
        } else if (this.c.b() && this.d.b() && this.e.b() && this.f.b()) {
            int intValue5 = Integer.valueOf(this.g.getText().toString()).intValue();
            int intValue6 = Integer.valueOf(this.h.getText().toString()).intValue();
            int intValue7 = Integer.valueOf(this.i.getText().toString()).intValue();
            int intValue8 = Integer.valueOf(this.j.getText().toString()).intValue();
            this.l.a(intValue5, intValue6, intValue7, intValue8);
            if (this.l.n != null) {
                this.l.n.a(this.l.b, new int[]{intValue5, intValue6, intValue7, intValue8});
            }
            this.k.dismiss();
        }
    }
}