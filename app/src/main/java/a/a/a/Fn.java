package a.a.a;

import android.content.DialogInterface;
import com.besome.sketch.MainDrawer;

public class Fn implements DialogInterface.OnClickListener {

    public final MainDrawer a;

    public Fn(MainDrawer mainDrawer) {
        this.a = mainDrawer;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == 0) {
            this.a.f();
        } else if (i == 1) {
            this.a.e();
        }
    }
}