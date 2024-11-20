package mod.hilal.saif.lib;

import android.app.Dialog;
import android.widget.EditText;

import a.a.a.Zx;
import mod.hilal.saif.activities.tools.BlocksManager;

public class PCP implements Zx.b {

    public final BlocksManager a;
    public final Dialog dialog;
    public final EditText e;
    public boolean ii;

    public PCP(BlocksManager blocksManager, EditText editText, Dialog alertDialog) {
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
        e.setText(String.format("#%08X", i));
    }
     @Override
    public void a(String i2, int i) {
        if (ii) {
            e.setText(String.format("#%08X", i));
            return;
        }
        e.setText(String.format("#%08X", i));
    }
}
