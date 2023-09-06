package mod.hilal.saif.lib;

import android.app.Dialog;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import a.a.a.Zx;
import mod.hilal.saif.activities.tools.BlocksManager;

public class PCP implements Zx.b {

    public final BlocksManager a;
    public final AlertDialog dialog;
    public final EditText e;
    public boolean ii = false;

    public PCP(BlocksManager blocksManager, EditText editText, AlertDialog alertDialog) {
        a = blocksManager;
        e = editText;
        dialog = alertDialog;
    }

    public PCP(EditText editText) {
        e = editText;
        ii = true;
        dialog = null;
        a = null;
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
