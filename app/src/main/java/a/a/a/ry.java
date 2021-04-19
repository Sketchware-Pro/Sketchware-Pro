package a.a.a;

import android.view.View;
import com.besome.sketch.editor.view.ViewProperty;

public class ry implements View.OnClickListener {

    public final aB a;
    public final ViewProperty b;

    public ry(ViewProperty viewProperty, aB aBVar) {
        this.b = viewProperty;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}