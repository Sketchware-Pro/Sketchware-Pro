package pro.sketchware.lib.validator;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import a.a.a.MB;
import a.a.a.xB;
import pro.sketchware.R;

public class PropertyNameValidator extends MB {

    private final String[] reservedNames;
    private final String[] reservedMethodNames;
    private final ArrayList<String> fileNames;
    private final String value;
    private final Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public PropertyNameValidator(Context context, TextInputLayout textInputLayout,
                                 String[] reservedNames, String[] reservedMethodNames,
                                 ArrayList<String> fileNames, String value) {

        super(context, textInputLayout);
        this.reservedNames = reservedNames;
        this.reservedMethodNames = reservedMethodNames;
        this.fileNames = fileNames;
        this.value = value;
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String trimmedLowerName = charSequence.toString().trim().toLowerCase();
        if (trimmedLowerName.length() < 1) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 1));
            d = false;
            return;
        }
        if (trimmedLowerName.length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 100));
            d = false;
            return;
        }
        if (value != null && value.length() > 0 && trimmedLowerName.equals(value.toLowerCase())) {
            b.setErrorEnabled(false);
            d = true;
            return;
        }
        if (fileNames.contains(trimmedLowerName)) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.common_message_name_unavailable));
            d = false;
            return;
        }
        for (String reservedMethodName : reservedMethodNames) {
            if (trimmedLowerName.equals(reservedMethodName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.common_message_name_unavailable));
                d = false;
                return;
            }
        }
        for (String reservedName : reservedNames) {
            if (trimmedLowerName.equals(reservedName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.logic_editor_message_reserved_keywords));
                d = false;
                return;
            }
        }
        if (!Character.isLetter(trimmedLowerName.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.logic_editor_message_variable_name_must_start_letter));
            d = false;
        } else if (pattern.matcher(charSequence).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_rule_3));
            d = false;
        }
    }
}
