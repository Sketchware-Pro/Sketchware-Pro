package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class UB extends MB {

    private static final Pattern packagePattern = Pattern.compile("([a-zA-Z][a-zA-Z\\d]*\\.)*[a-zA-Z][a-zA-Z\\d]*");

    public UB(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() > 50) {
            b.setErrorEnabled(true);
            if (e == 0) {
                b.setError(xB.b().a(a, 0x7f0e05d8, 50));
            } else {
                b.setError(xB.b().a(a, e, 50));
            }
            d = false;
            return;
        }
        b.setErrorEnabled(false);
        d = true;
        if (!packagePattern.matcher(s.toString()).matches()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05db));
            d = false;
        } else {
            if (!s.toString().contains(".")) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 0x7f0e06d3));
                d = false;
                return;
            }
            b.setErrorEnabled(false);
            d = true;
        }
        boolean containsReservedWord = false;
        for (String packagePart : s.toString().split("\\.")) {
            String[] reservedWords = uq.b;
            int length = reservedWords.length;
            int reservedWordIndex = 0;
            while (true) {
                if (reservedWordIndex >= length) {
                    break;
                }
                if (reservedWords[reservedWordIndex].equals(packagePart)) {
                    containsReservedWord = true;
                    break;
                }
                reservedWordIndex++;
            }
        }
        if (containsReservedWord) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e0617));
            d = false;
        }
    }
}
