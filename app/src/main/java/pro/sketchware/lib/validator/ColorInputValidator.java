package pro.sketchware.lib.validator;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import a.a.a.MB;
import a.a.a.xB;
import pro.sketchware.R;

public class ColorInputValidator extends MB {

    private final Pattern hexPattern = Pattern.compile("[A-Fa-f0-9]*");
    private final View colorPreview;

    public ColorInputValidator(Context var1, TextInputLayout textInputLayout, View colorPreview) {
        super(var1, textInputLayout);
        this.colorPreview = colorPreview;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String hexCode = s.toString().trim();
        if (hexCode.length() > 8) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 8));
            d = false;
        } else {
            if (hexPattern.matcher(hexCode).matches()) {
                try {
                    hexCode = String.format("#%8s", hexCode).replaceAll(" ", "F");
                    colorPreview.setBackgroundColor(Color.parseColor(hexCode));
                } catch (Exception var5) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, R.string.invalid_value_format));
                    d = false;
                    colorPreview.setBackgroundColor(0xfff6f6f6);
                }

                b.setErrorEnabled(false);
                d = true;
            } else {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.invalid_value_format));
                colorPreview.setBackgroundColor(0xfff6f6f6);
                d = false;
            }

        }
    }
}
