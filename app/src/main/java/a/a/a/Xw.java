package a.a.a;

import android.view.View;
import com.besome.sketch.editor.property.PropertyGravityItem;

public class Xw implements View.OnClickListener {

    public final aB a;
    public final PropertyGravityItem b;

    public Xw(PropertyGravityItem propertyGravityItem, aB aBVar) {
        this.b = propertyGravityItem;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}