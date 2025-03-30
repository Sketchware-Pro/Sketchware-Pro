package pro.sketchware.lib.validator;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import a.a.a.MB;
import a.a.a.uq;
import a.a.a.xB;
import pro.sketchware.R;

public class PackageNameValidator extends MB {

    private static final Pattern packagePattern = Pattern.compile("([a-zA-Z][a-zA-Z\\d]*\\.)*[a-zA-Z][a-zA-Z\\d]*");

    public PackageNameValidator(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.toString().trim().length() > 50) {
            b.setErrorEnabled(true);
            if (e == 0) {
                b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 50));
            } else {
                //what ???
                b.setError(xB.b().a(a, e, 50));
            }
            d = false;
            return;
        }
        b.setErrorEnabled(false);
        d = true;
        if (!packagePattern.matcher(s.toString()).matches()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_rule_2));
            d = false;
        } else {
            if (!s.toString().contains(".")) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.myprojects_settings_message_contain_dot));
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
            b.setError(xB.b().a(a, R.string.logic_editor_message_reserved_keywords));
            d = false;
        }
    }
}
