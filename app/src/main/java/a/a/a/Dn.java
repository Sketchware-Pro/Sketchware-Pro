package a.a.a;

import android.view.View;
import com.besome.sketch.MainDrawer;

public class Dn implements View.OnClickListener {

    public final MainDrawer a;

    public Dn(MainDrawer mainDrawer) {
        this.a = mainDrawer;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.a.a();
        }
    }
}