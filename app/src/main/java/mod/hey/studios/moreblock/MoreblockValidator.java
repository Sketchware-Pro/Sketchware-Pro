package mod.hey.studios.moreblock;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

import a.a.a.MB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class MoreblockValidator extends MB {

    private final String[] reservedKeywords;
    private final ArrayList<String> registeredVariables;
    private final Pattern j = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_<>,|\\[\\] ]*");
    private String[] eventNames;
    private String i;

    public MoreblockValidator(Context context, TextInputLayout textInputLayout, String[] reservedKeywords, String[] eventNames, ArrayList<String> registeredVariables) {
        super(context, textInputLayout);
        this.reservedKeywords = reservedKeywords;
        this.eventNames = eventNames;
        this.registeredVariables = registeredVariables;
    }

    public void a(String[] strArr) {
        eventNames = strArr;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String name = charSequence.toString();
        int trimmedLength = name.trim().length();
        if (trimmedLength < 1) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 1));
            d = false;
        } else if (name.length() > 60) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 60));
            d = false;
        } else {
            if (i != null && i.length() > 0 && name.equals(i)) {
                b.setErrorEnabled(false);
                d = true;
            } else if (registeredVariables.contains(name)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, R.string.common_message_name_unavailable, 0));
                d = false;
            } else {
                boolean z = false;
                for (String eventsName : eventNames) {
                    if (name.equals(eventsName)) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    b.setErrorEnabled(true);
                    b.setError(Helper.getResString(R.string.common_message_name_unavailable));
                    d = false;
                    return;
                }
                boolean isReservedKeyUsed = false;
                for (String keyword : reservedKeywords) {
                    if (name.equals(keyword)) {
                        isReservedKeyUsed = true;
                        break;
                    }
                }

                if (isReservedKeyUsed) {
                    b.setErrorEnabled(true);
                    b.setError(Helper.getResString(R.string.logic_editor_message_reserved_keywords));
                    d = false;
                } else if (!Character.isLetter(charSequence.charAt(0))) {
                    b.setErrorEnabled(true);
                    b.setError(Helper.getResString(R.string.logic_editor_message_variable_name_must_start_letter));
                    d = false;
                } else {
                    if (j.matcher(name).matches()) {
                        b.setErrorEnabled(false);
                        d = true;
                    } else {
                        b.setErrorEnabled(true);
                        b.setError(Helper.getResString(R.string.invalid_value_rule_3));
                        d = false;
                    }
                    if (name.trim().length() < 1) {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 1));
                        d = false;
                    }
                }
            }
        }
    }
}
