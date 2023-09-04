package mod.elfilibustero.sketch.lib.valid;

import a.a.a.MB;
import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            b.setErrorEnabled(true);
            b.setError("Extra spaces between words or at the end are not allowed.");
            d = false;
            return;
        }
        Set<String> usedModifiers = new HashSet<>();
        boolean hasAccessModifier = false;

        for (String word : words) {
            if (!PATTERN_MODIFIER.matcher(word).matches()) {
                b.setErrorEnabled(true);
                b.setError("Invalid modifier: " + word);
                d = false;
                return;
            }
            if (!usedModifiers.add(word)) {
                b.setErrorEnabled(true);
                b.setError("Duplicate modifier: " + word);
                d = false;
                return;
            }
            if (isAccessModifier(word)) {
                if (hasAccessModifier) {
                    b.setErrorEnabled(true);
                    b.setError("Access modifier can only set one of public / protected / private");
                    d = false;
                    return;
                }
                hasAccessModifier = true;
            }
        }
        b.setErrorEnabled(false);
        d = true;
    }

    private boolean isAccessModifier(String word) {
        return word.equals("public") || word.equals("protected") || word.equals("private");
    }

    public boolean isValid() {
        return b();
    }
}
