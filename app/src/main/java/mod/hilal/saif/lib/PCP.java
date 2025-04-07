package mod.hilal.saif.lib;

import android.widget.TextView;

import com.besome.sketch.lib.ui.ColorPickerDialog;

public class PCP implements ColorPickerDialog.b {

    private final TextView toSetText;

    public PCP(TextView toSetText) {
        this.toSetText = toSetText;
    }

    @Override
    public void a(int color) {
        toSetText.setText(String.format("#%08X", color));
    }

    @Override
    public void a(String color, int i2) {
        toSetText.setText(String.format("#%08X", color));
    }
}
