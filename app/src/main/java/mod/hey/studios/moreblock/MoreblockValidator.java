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
    private final Pattern j = Pattern.compile("^[a-zA-Z]\\w*(?:\\[[\\w\\[\\],<>?| ]*])?$");
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
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(xB.b().a(context, R.string.invalid_value_min_lenth, 1));
            isInputValid = false;
        } else if (name.length() > 60) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(xB.b().a(context, R.string.invalid_value_max_lenth, 60));
            isInputValid = false;
        } else {
            if (i != null && i.length() > 0 && name.equals(i)) {
                textInputLayout.setErrorEnabled(false);
                isInputValid = true;
            } else if (registeredVariables.contains(name)) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError(xB.b().a(context, R.string.common_message_name_unavailable, 0));
                isInputValid = false;
            } else {
                boolean z = false;
                for (String eventsName : eventNames) {
                    if (name.equals(eventsName)) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(Helper.getResString(R.string.common_message_name_unavailable));
                    isInputValid = false;
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
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(Helper.getResString(R.string.logic_editor_message_reserved_keywords));
                    isInputValid = false;
                } else if (!Character.isLetter(charSequence.charAt(0))) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError(Helper.getResString(R.string.logic_editor_message_variable_name_must_start_letter));
                    isInputValid = false;
                } else {
                    if (j.matcher(name).matches()) {
                        textInputLayout.setErrorEnabled(false);
                        isInputValid = true;
                    } else {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(Helper.getResString(R.string.invalid_value_rule_3));
                        isInputValid = false;
                    }
                    if (name.trim().length() < 1) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(xB.b().a(context, R.string.invalid_value_min_lenth, 1));
                        isInputValid = false;
                    }
                }
            }
        }
    }
}
