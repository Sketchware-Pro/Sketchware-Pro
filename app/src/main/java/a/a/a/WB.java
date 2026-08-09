package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pro.sketchware.R;

public class WB extends MB {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*");

    public String[] f;
    public ArrayList<String> g;
    public String h;
    public Pattern i = VALID_NAME_PATTERN;

    public WB(Context context, TextInputLayout textInputLayout, String[] reservedKeywords, ArrayList<String> existingNames) {
        this(context, textInputLayout, reservedKeywords, existingNames, null);
    }

    public WB(Context context, TextInputLayout textInputLayout, String[] reservedKeywords, ArrayList<String> existingNames, String originalName) {
        super(context, textInputLayout);
        f = reservedKeywords;
        g = existingNames;
        h = originalName;
    }

    public final void validate(String name) {
        if (name.length() < 3) {
            setError(a.getString(R.string.invalid_value_min_lenth, 3));
            return;
        }
        if (name.length() > 70) {
            setError(a.getString(R.string.invalid_value_max_lenth, 70));
            return;
        }
        if ("default_image".equals(name) || "NONE".equalsIgnoreCase(name)) {
            setError(a.getString(R.string.common_message_name_unavailable));
            return;
        }
        if (!name.equals(h) && g != null && g.contains(name)) {
            setError(a.getString(R.string.common_message_name_unavailable));
            return;
        }

        if (isReservedKeyword(name)) {
            setError(a.getString(R.string.logic_editor_message_reserved_keywords));
        } else if (!Character.isLetter(name.charAt(0))) {
            setError(a.getString(R.string.logic_editor_message_variable_name_must_start_letter));
        } else if (i.matcher(name).matches()) {
            clearError();
        } else {
            setError(a.getString(R.string.invalid_value_rule_4));
        }
    }

    private boolean isReservedKeyword(String name) {
        if (f == null) return false;
        for (String keyword : f) {
            if (name.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    private void setError(String errorMessage) {
        b.setErrorEnabled(true);
        b.setError(errorMessage);
        d = false;
    }

    private void clearError() {
        b.setErrorEnabled(false);
        b.setError(null);
        d = true;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        validate(charSequence.toString().trim());
    }
}
