package a.a.a;

import android.view.View;
import android.widget.RadioButton;
import com.besome.sketch.editor.property.PropertySelectorItem;

public class ux implements View.OnClickListener {

    public final aB a;
    public final PropertySelectorItem b;

    public ux(PropertySelectorItem propertySelectorItem, aB aBVar) {
        this.b = propertySelectorItem;
        this.a = aBVar;
    }

    public void onClick(View view) {
        int childCount = this.b.i.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.b.i.getChildAt(i);
            if (radioButton.isChecked()) {
                this.b.setValue(Integer.valueOf(radioButton.getTag().toString()).intValue());
                break;
            }
            i++;
        }
        if (this.b.j != null) {
            this.b.j.a(this.b.a, Integer.valueOf(this.b.b));
        }
        this.a.dismiss();
    }
}