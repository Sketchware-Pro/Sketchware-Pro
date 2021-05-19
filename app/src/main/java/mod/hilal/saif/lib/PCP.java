package mod.hilal.saif.lib;

import android.app.AlertDialog;
import android.widget.EditText;

import a.a.a.Zx;
import mod.hilal.saif.activities.tools.BlocksManager;

public class PCP implements Zx.b {


    public final BlocksManager a;
    public final AlertDialog d;
    public final EditText e;
    public boolean ii = false;

    public PCP(BlocksManager blocksManager, EditText editText, AlertDialog alertDialog) {
        this.a = blocksManager;
        this.e = editText;
        this.d = alertDialog;
    }

    public PCP(EditText editText) {
        this.e = editText;
        this.ii = true;
        this.d = null;
        this.a = null;
    }

    public void a(int i) {
        if (this.ii) {
            this.e.setText(String.format("#%08X", Integer.valueOf(i & -1)));
            return;
        }
        this.d.show();
        this.e.setText(String.format("#%08X", Integer.valueOf(i & -1)));
    }
}
