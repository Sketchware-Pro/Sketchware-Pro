package a.a.a;

import android.view.View;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;

public class rA implements View.OnClickListener {

    public final int a;
    public final aB b;
    public final BasePermissionAppCompatActivity c;

    public rA(BasePermissionAppCompatActivity basePermissionAppCompatActivity, int i, aB aBVar) {
        this.c = basePermissionAppCompatActivity;
        this.a = i;
        this.b = aBVar;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            nd.a(this.c, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, this.a);
            this.b.dismiss();
        }
    }
}