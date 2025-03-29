package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class _B extends MB {

    private final String[] reservedNames;
    private final String[] reservedMethodNames;
    private final ArrayList<String> fileNames;
    private final String value;
    private final Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public _B(Context context, TextInputLayout textInputLayout,
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
            b.setError(xB.b().a(a, 0x7f0e05d9, 1));
            d = false;
            return;
        }
        if (trimmedLowerName.length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d8, 100));
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
            b.setError(xB.b().a(a, 0x7f0e03f6));
            d = false;
            return;
        }
        for (String reservedMethodName : reservedMethodNames) {
            if (trimmedLowerName.equals(reservedMethodName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 0x7f0e03f6));
                d = false;
                return;
            }
        }
        for (String reservedName : reservedNames) {
            if (trimmedLowerName.equals(reservedName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 0x7f0e0617));
                d = false;
                return;
            }
        }
        if (!Character.isLetter(trimmedLowerName.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e0619));
            d = false;
        } else if (pattern.matcher(charSequence).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05dc));
            d = false;
        }
    }
}
