package mod.hasrat.validator;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import a.a.a.MB;

public class VersionNamePostfixValidator extends MB {

    private static final Pattern VERSION_NAME_POSTFIX_PATTERN = Pattern.compile("^[a-zA-Z0-9_]*");

    public VersionNamePostfixValidator(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String se = s.toString();
        if (VERSION_NAME_POSTFIX_PATTERN.matcher(se).matches()) {
            textInputLayout.setErrorEnabled(false);
            isInputValid = true;
        } else if (se.contains(" ")) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Spaces aren't allowed to prevent crashes");
            isInputValid = false;
        } else {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Only use letters (a-zA-Z), numbers and Special characters (_)");
            isInputValid = false;
        }
    }
}
