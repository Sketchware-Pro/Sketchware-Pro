package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import pro.sketchware.R;

public class RB extends MB {

    private final Pattern NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_ ]*");

    public RB(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() == 0) {
            b.setErrorEnabled(true);
            b.setError(a.getString(R.string.invalid_value_min_lenth, 1));
            d = false;
        } else if (NAME_PATTERN.matcher(s.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(a.getString(R.string.invalid_value_rule_4));
            d = false;
        }

    }
}
