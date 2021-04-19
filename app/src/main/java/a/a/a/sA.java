package a.a.a;

import android.view.View;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;

public class sA implements View.OnClickListener {

    public final aB a;
    public final BasePermissionAppCompatActivity b;

    public sA(BasePermissionAppCompatActivity basePermissionAppCompatActivity, aB aBVar) {
        this.b = basePermissionAppCompatActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.b.l();
        this.a.dismiss();
    }
}