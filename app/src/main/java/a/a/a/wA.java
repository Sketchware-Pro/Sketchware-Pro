package a.a.a;

import android.content.DialogInterface;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;

public class wA implements DialogInterface.OnDismissListener {

    public final /* synthetic */ BasePermissionAppCompatActivity a;

    public wA(BasePermissionAppCompatActivity basePermissionAppCompatActivity) {
        this.a = basePermissionAppCompatActivity;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        Sp.a = false;
    }
}