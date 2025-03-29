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
            b.setError(xB.b().a(a, 2131625433, 1));
            d = false;
        } else if (trimmedLowerName.length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625432, 100));
            d = false;
        } else {
            String var6;
            if (value != null && value.length() > 0 && trimmedLowerName.equals(value.toLowerCase())) {
                b.setErrorEnabled(false);
                d = true;
            } else if (fileNames.contains(trimmedLowerName)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131624950));
                d = false;
            } else {
                before = reservedMethodNames.length;
                start = 0;

                while (true) {
                    if (start >= before) {
                        start = 0;
                        break;
                    }

                    if (trimmedLowerName.equals(((Object[]) reservedMethodNames)[start])) {
                        start = 1;
                        break;
                    }

                    ++start;
                }

                if (start) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131624950));
                    d = false;
                } else {
                    int counter = 0;

                    while (true) {
                        if (counter >= reservedNames.length) {
                            counter = 0;
                            break;
                        }

                        if (trimmedLowerName.equals(reservedNames[counter])) {
                            counter = 1;
                            break;
                        }

                        ++counter;
                    }

                    if (counter) {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, 0x7f0e0617));
                        d = false;
                    } else if (!Character.isLetter(trimmedLowerName.charAt(0))) {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, 2131625497));
                        d = false;
                    } else {
                        if (pattern.matcher(charSequence).matches()) {
                            b.setErrorEnabled(false);
                            d = true;
                        } else {
                            b.setErrorEnabled(true);
                            b.setError(xB.b().a(a, 2131625436));
                            d = false;
                        }

                    }
                }
            }
        }
    }
}
