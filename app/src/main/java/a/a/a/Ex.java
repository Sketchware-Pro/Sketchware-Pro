package a.a.a;

import android.view.View;
import android.view.View.OnClickListener;
import com.besome.sketch.editor.property.ViewPropertyItems;

public class Ex implements OnClickListener {
    // still used in some non-decompiled classes
    public final ViewPropertyItems a;

    public Ex(ViewPropertyItems viewPropertyItems) {
        a = viewPropertyItems;
    }

    public void onClick(View view) {
        if (mB.a() || a.b) {
            return;
        }
        a.c();
    }
}