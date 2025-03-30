package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import pro.sketchware.R;

public class LB extends MB {

    private static final Pattern APP_NAME_PATTERN = Pattern.compile(".*[&\"'<>].*");

    public LB(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() == 0) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 1));
            d = false;
        } else if (s.toString().trim().length() > 50) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 50));
            d = false;
        } else if (APP_NAME_PATTERN.matcher(s.toString()).matches()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_rule_5));
            d = false;
        } else {
            b.setErrorEnabled(false);
            d = true;
        }

    }
}
