package mod.hilal.saif.lib;

import android.app.AlertDialog;
import android.widget.EditText;

import a.a.a.Zx;
import mod.hilal.saif.activities.tools.BlocksManager;

public class PCP implements Zx.b {


    public final BlocksManager a;
    public final AlertDialog dialog;
    public final EditText e;
    public boolean ii = false;

    public PCP(BlocksManager blocksManager, EditText editText, AlertDialog alertDialog) {
        this.a = blocksManager;
        this.e = editText;
        dialog = alertDialog;
    }

    public PCP(EditText editText) {
        this.e = editText;
        this.ii = true;
        dialog = null;
        this.a = null;
    }

    @Override
    public void a(int i) {
        if (ii) {
            e.setText(String.format("#%08X", i));
            return;
        }
        if (dialog != null) {
            dialog.show();
        }
        e.setText(String.format("#%08X", i));
    }
}
