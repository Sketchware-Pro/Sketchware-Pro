package mod.elfilibustero.sketch.lib.valid;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import a.a.a.MB;

public class VariableModifierValidator extends MB {
    public static final Pattern PATTERN_MODIFIER = Pattern.compile(
            "\\b(public|protected|private|static|final|transient|volatile)\\b"
    );

    public VariableModifierValidator(Context context, TextInputLayout textInputLayout) {
        super(context, textInputLayout);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        String input = charSequence.toString();
        String trimmedInput = input.trim();
        String[] words = trimmedInput.split("\\s+");
        String reconsInput = String.join(" ", words);

        if (!input.equals(reconsInput)) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Extra spaces between words or at the end are not allowed.");
            isInputValid = false;
            return;
        }
        Set<String> usedModifiers = new HashSet<>();
        boolean hasAccessModifier = false;

        for (String word : words) {
            if (!PATTERN_MODIFIER.matcher(word).matches()) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("Invalid modifier: " + word);
                isInputValid = false;
                return;
            }
            if (!usedModifiers.add(word)) {
                textInputLayout.setErrorEnabled(true);
                textInputLayout.setError("Duplicate modifier: " + word);
                isInputValid = false;
                return;
            }
            if (isAccessModifier(word)) {
                if (hasAccessModifier) {
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("Access modifier can only set one of public / protected / private");
                    isInputValid = false;
                    return;
                }
                hasAccessModifier = true;
            }
        }
        textInputLayout.setErrorEnabled(false);
        isInputValid = true;
    }

    private boolean isAccessModifier(String word) {
        return word.equals("public") || word.equals("protected") || word.equals("private");
    }

    public boolean isValid() {
        return b();
    }
}
