package mod.elfilibustero.sketch.lib.valid;

import a.a.a.MB;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.List;
import java.util.regex.Pattern;

import mod.elfilibustero.sketch.beans.ResourceXmlBean;
import mod.hey.studios.util.Helper;

public class ResNameValidator extends MB {

    private List<String> alreadyUsed;
    private Pattern namePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9._]*$");
    private int resType = -1;

    public ResNameValidator(Context context, TextInputLayout textInputLayout, List<String> alreadyUsed, int resType) {
        super(context, textInputLayout);
        this.alreadyUsed = alreadyUsed;
        this.resType = resType;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        String inputText = charSequence.toString();
        int inputLength = inputText.trim().length();

        if (inputLength < 1) {
            showError(Helper.getResString(R.string.invalid_value_min_lenth, 1));
            return;
        }

        if (alreadyUsed.contains(inputText)) {
            showError(Helper.getResString(R.string.common_message_name_unavailable));
            return;
        }

        if (!Character.isLetter(inputText.charAt(0))) {
            showError(Helper.getResString(R.string.logic_editor_message_variable_name_must_start_letter));
            return;
        }

        if (resType != ResourceXmlBean.RES_TYPE_STYLE && resType != ResourceXmlBean.RES_TYPE_STYLE_ITEM) {
            if (inputText.contains(".")) {
                showError(Helper.getResString(R.string.common_message_cannot_contain_periods));
                return;
            }
        }

        if (resType != ResourceXmlBean.RES_TYPE_STYLE_ITEM) {
            if (!namePattern.matcher(inputText).matches()) {
                showError(Helper.getResString(R.string.invalid_value_rule_3));
                return;
            }
        }

        clearError();
        d = true;
    }

    private void showError(String errorMessage) {
        b.setErrorEnabled(true);
        b.setError(errorMessage);
        d = false;
    }

    private void clearError() {
        b.setErrorEnabled(false);
        d = true;
    }
    
    public boolean isValid() {
        return b();
    }
}
