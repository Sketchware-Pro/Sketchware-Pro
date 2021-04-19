package a.a.a;

import android.view.View;
import android.widget.RadioButton;
import com.besome.sketch.editor.property.PropertyStringPairSelectorItem;

public class yx implements View.OnClickListener {

    public final aB a;
    public final PropertyStringPairSelectorItem b;

    public yx(PropertyStringPairSelectorItem propertyStringPairSelectorItem, aB aBVar) {
        this.b = propertyStringPairSelectorItem;
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
                this.b.setValue(radioButton.getTag().toString());
                break;
            }
            i++;
        }
        if (this.b.j != null) {
            this.b.j.a(this.b.a, this.b.b);
        }
        this.a.dismiss();
    }
}