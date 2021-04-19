package a.a.a;

import android.content.DialogInterface;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;

public class tA implements DialogInterface.OnDismissListener {

    public final BasePermissionAppCompatActivity a;

    public tA(BasePermissionAppCompatActivity basePermissionAppCompatActivity) {
        this.a = basePermissionAppCompatActivity;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        Sp.a = false;
    }
}