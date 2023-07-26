package a.a.a;

import android.view.View;
import android.view.View.OnClickListener;
import com.besome.sketch.editor.property.ViewPropertyItems;

/* loaded from: a/a/a/Ex */
public class Ex implements OnClickListener {
    public final ViewPropertyItems a;

    public Ex(ViewPropertyItems viewPropertyItems) {
        this.a = viewPropertyItems;
    }

    public void onClick(View view) {
        boolean z;
        if (mB.a()) {
            return;
        }
        z = this.a.b;
        if (z) {
            return;
        }
        this.a.c();
    }
}