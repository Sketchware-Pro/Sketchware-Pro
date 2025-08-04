package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class VB extends MB {

    private final Pattern PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public VB(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() == 0) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625433, 1));
            d = false;
        } else if (s.toString().trim().length() > 20) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625432, 20));
            d = false;
        } else if (!Character.isLetter(s.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625497));
            d = false;
        } else if (PATTERN.matcher(s.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625436));
            d = false;
        }
    }
}
