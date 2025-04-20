package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pro.sketchware.R;

public class YB extends MB {

    private static final Pattern namePattern = Pattern.compile("^[a-z][a-z0-9_]*");
    private final String[] reservedNames;
    private final ArrayList<String> existingNames;
    private String originalName;

    public YB(Context context, TextInputLayout textInputLayout, String[] reservedNames, ArrayList<String> existingNames) {
        super(context, textInputLayout);
        this.reservedNames = reservedNames;
        this.existingNames = existingNames;
    }

    public YB(Context context, TextInputLayout textInputLayout, String[] reservedNames, ArrayList<String> existingNames, String originalName) {
        super(context, textInputLayout);
        this.reservedNames = reservedNames;
        this.existingNames = existingNames;
        this.originalName = originalName;
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence.toString().trim().length() < 3) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 3));
            d = false;
            return;
        }
        if (charSequence.toString().trim().length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 100));
            d = false;
            return;
        }
        String str = originalName;
        if (str != null && !str.isEmpty() && charSequence.toString().equals(originalName)) {
            b.setErrorEnabled(false);
            d = true;
            return;
        }
        for (String reservedName : reservedNames) {
            if (charSequence.toString().equals(reservedName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.logic_editor_message_reserved_keywords));
                d = false;
                return;
            }
        }
        if ("main".equals(charSequence.toString()) || existingNames.contains(charSequence.toString())) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.common_message_name_unavailable));
            d = false;
        } else if (!Character.isLetter(charSequence.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.logic_editor_message_variable_name_must_start_letter));
            d = false;
        } else if (YB.namePattern.matcher(charSequence.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_rule_4));
            d = false;
        }
    }
}
