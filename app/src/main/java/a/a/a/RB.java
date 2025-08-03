package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RB extends MB {

    private final Pattern NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_ ]*");

    public RB(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() == 0) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625433, 1));
            d = false;
        } else if (NAME_PATTERN.matcher(s.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625437));
            d = false;
        }

    }
}
