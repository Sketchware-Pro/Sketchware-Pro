package a.a.a;

import android.view.View;
import com.besome.sketch.editor.property.PropertySelectorItem;

public class vx implements View.OnClickListener {

    public final aB a;
    public final PropertySelectorItem b;

    public vx(PropertySelectorItem propertySelectorItem, aB aBVar) {
        this.b = propertySelectorItem;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}