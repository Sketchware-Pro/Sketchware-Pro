package a.a.a;

import android.view.View;
import android.view.View.OnClickListener;
import com.besome.sketch.editor.property.ViewPropertyItems;

public class Dx implements OnClickListener {
    private final ViewPropertyItems a;

    public Dx(ViewPropertyItems viewPropertyItems) {
        a = viewPropertyItems;
    }

    public void onClick(View view) {
        if (mB.a() || a.b) {
            return;
        }
        a.c();
    }
}