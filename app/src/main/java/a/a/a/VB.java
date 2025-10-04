package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import pro.sketchware.R;

public class VB extends MB {

    private final Pattern PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public VB(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() == 0) {
            b.setErrorEnabled(true);
            b.setError(a.getString(R.string.invalid_value_min_lenth, 1));
            d = false;
        } else if (s.toString().trim().length() > 20) {
            b.setErrorEnabled(true);
            b.setError(a.getString(R.string.invalid_value_max_lenth, 20));
            d = false;
        } else if (!Character.isLetter(s.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(a.getString(R.string.logic_editor_message_variable_name_must_start_letter));
            d = false;
        } else if (PATTERN.matcher(s.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(a.getString(R.string.invalid_value_rule_3));
            d = false;
        }
    }
}
