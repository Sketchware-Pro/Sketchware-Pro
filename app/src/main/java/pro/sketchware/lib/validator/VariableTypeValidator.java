package pro.sketchware.lib.validator;

import android.content.Context;
import android.text.TextUtils;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import a.a.a.MB;

public class VariableTypeValidator extends MB {
    public static final Pattern PATTERN_TYPE = Pattern.compile(
            "^[a-zA-Z0-9._]+(<[a-zA-Z0-9.,_ ?<>\\[\\]]+>)?(\\[\\])*?$"
    );

    public VariableTypeValidator(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String variableType = charSequence.toString();
        String trimmedInput = variableType.trim();
        String[] words = trimmedInput.split("\\s+");
        String reconsInput = String.join(" ", words);

        if (!variableType.equals(reconsInput)) {
            b.setError("Extra spaces between or at the end are not allowed.");
            d = false;
            return;
        }

        if (!TextUtils.isEmpty(charSequence)) {
            if (!Character.isLetter(charSequence.charAt(0))) {
                b.setError("Variable data type must start with a letter");
                d = false;
                return;
            }
        }

        if (!isValidAngleBracket(variableType)) {
            b.setError("Angle bracket not matched");
            d = false;
            return;
        }

        if (!isValidBoxBracket(variableType)) {
            b.setError("Box bracket not matched");
            d = false;
            return;
        }

        if (!PATTERN_TYPE.matcher(variableType).matches()) {
            b.setError("Invalid variable data type");
            d = false;
            return;
        }
        b.setError(null);
        b.setError(null);
        d = true;
    }

    public boolean isValid() {
        return b();
    }

    private boolean isValidBracket(String _input, char _openingBracket, char _closingBracket) {
        int bracketCount = 0;
        for (char c : _input.toCharArray()) {
            if (c == _openingBracket) {
                bracketCount++;
            } else if (c == _closingBracket) {
                bracketCount--;
                if (bracketCount < 0) {
                    return false;
                }
            }
        }
        return bracketCount == 0;
    }

    private boolean isValidBoxBracket(String _input) {
        return isValidBracket(_input, '[', ']');
    }

    private boolean isValidAngleBracket(String _input) {
        return isValidBracket(_input, '<', '>');
    }
}
