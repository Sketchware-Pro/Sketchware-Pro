package a.a.a;

import android.view.View;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;

public class uA implements View.OnClickListener {

    public final int a;
    public final aB b;
    public final BasePermissionAppCompatActivity c;

    public uA(BasePermissionAppCompatActivity basePermissionAppCompatActivity, int i, aB aBVar) {
        this.c = basePermissionAppCompatActivity;
        this.a = i;
        this.b = aBVar;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            this.c.h(this.a);
            this.b.dismiss();
        }
    }
}