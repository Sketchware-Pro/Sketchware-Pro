package a.a.a;

import android.view.View;
import com.besome.sketch.editor.property.PropertyInputItem;

public class gx implements View.OnClickListener {

    public final aB a;
    public final PropertyInputItem b;

    public gx(PropertyInputItem propertyInputItem, aB aBVar) {
        this.b = propertyInputItem;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}