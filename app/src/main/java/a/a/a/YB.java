package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

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
            b.setError(xB.b().a(a, 0x7f0e05d9, 3));
            d = false;
            return;
        }
        if (charSequence.toString().trim().length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d8, 100));
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
                b.setError(xB.b().a(a, 0x7f0e0617));
                d = false;
                return;
            }
        }
        if ("main".equals(charSequence.toString()) || existingNames.contains(charSequence.toString())) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e03f6));
            d = false;
        } else if (!Character.isLetter(charSequence.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e0619));
            d = false;
        } else if (YB.namePattern.matcher(charSequence.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05dd));
            d = false;
        }
    }
}
