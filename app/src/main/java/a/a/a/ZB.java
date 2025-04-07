package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pro.sketchware.R;

public class ZB extends MB {

    private final String[] restrictedNames;
    private String[] reservedNames;
    private final ArrayList<String> excludedNames;
    private String lastValidName;
    private static final Pattern validNamePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public ZB(Context context, TextInputLayout textInputLayout, String[] newReservedNames, String[] strArr2, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        restrictedNames = newReservedNames;
        reservedNames = strArr2;
        excludedNames = arrayList;
    }

    public void a(String[] newReservedNames) {
        reservedNames = newReservedNames;
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().isEmpty()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 1));
            d = false;
            return;
        }
        if (s.toString().trim().length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 100));
            d = false;
            return;
        }
        String previousValidName = lastValidName;
        if (previousValidName != null && !previousValidName.isEmpty() && s.toString().equals(lastValidName)) {
            b.setErrorEnabled(false);
            d = true;
            return;
        }
        if (excludedNames.contains(s.toString())) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.common_message_name_unavailable));
            d = false;
            return;
        }
        for (String reservedName : reservedNames) {
            if (s.toString().equals(reservedName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.common_message_name_unavailable));
                d = false;
                return;
            }
        }
        for (String restrictedName : restrictedNames) {
            if (s.toString().equals(restrictedName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.logic_editor_message_reserved_keywords));
                d = false;
                return;
            }
        }
        if (!Character.isLetter(s.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.logic_editor_message_variable_name_must_start_letter));
            d = false;
            return;
        }
        if (validNamePattern.matcher(s.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05dc));
            d = false;
        }
        if (s.toString().trim().isEmpty()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d9, 1));
            d = false;
        }
    }
}
