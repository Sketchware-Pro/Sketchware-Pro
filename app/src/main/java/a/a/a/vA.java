package a.a.a;

import android.view.View;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;

public class vA implements View.OnClickListener {

    public final aB a;
    public final BasePermissionAppCompatActivity b;

    public vA(BasePermissionAppCompatActivity basePermissionAppCompatActivity, aB aBVar) {
        this.b = basePermissionAppCompatActivity;
        this.a = aBVar;
    }

    public void onClick(View view) {
        this.b.m();
        this.a.dismiss();
    }
}