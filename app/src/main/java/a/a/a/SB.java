package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import pro.sketchware.R;

public class SB extends MB {

    private final int min;
    private final int max;

    public SB(Context context, TextInputLayout textInputLayout, int min, int max) {
        super(context, textInputLayout);
        this.min = min;
        this.max = max;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() < min) {
            b.setErrorEnabled(true);
            if (e == 0) {
                b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, min));
            } else {
                b.setError(xB.b().a(a, e, min));
            }

            d = false;
        } else {
            if (s.toString().trim().length() > max) {
                b.setErrorEnabled(true);
                if (e == 0) {
                    b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, max));
                } else {
                    b.setError(xB.b().a(a, e, max));
                }

                d = false;
            } else {
                b.setErrorEnabled(false);
                d = true;
            }

        }
    }
}
