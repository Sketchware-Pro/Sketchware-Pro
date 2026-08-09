package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pro.sketchware.R;

public class PB extends MB {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*");

    public String[] f;
    public ArrayList<String> g;
    public String h;
    public int i;
    public Pattern j = VALID_NAME_PATTERN;

    public PB(Context context, TextInputLayout textInputLayout, String[] reservedKeywords, ArrayList<String> existingNames) {
        this(context, textInputLayout, reservedKeywords, existingNames, null);
    }

    public PB(Context context, TextInputLayout textInputLayout, String[] reservedKeywords, ArrayList<String> existingNames, String originalName) {
        super(context, textInputLayout);
        f = reservedKeywords;
        g = existingNames;
        h = originalName;
        i = 1;
    }

    public void a(int count) {
        i = count;
        if (a().length() > 0) {
            b(a());
        }
    }

    public final void b(String name) {
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
        if (i != 1) {
            ArrayList<String> duplicates = new ArrayList<>();
            for (int index = 1; index <= i; index++) {
                String candidate = name + "_" + index;
                if (g.contains(candidate)) {
                    duplicates.add(candidate);
                }
            }
            if (!duplicates.isEmpty()) {
                String baseMsg = a.getString(R.string.common_message_name_unavailable);
                setError(baseMsg + "\n[" + String.join(", ", duplicates) + "]");
                return;
            }
        } else if (!name.equals(h) && g.contains(name)) {
            setError(a.getString(R.string.common_message_name_unavailable));
            return;
        }

        if (isReservedKeyword(name)) {
            setError(a.getString(R.string.logic_editor_message_reserved_keywords));
        } else if (!Character.isLetter(name.charAt(0))) {
            setError(a.getString(R.string.logic_editor_message_variable_name_must_start_letter));
        } else if (j.matcher(name).matches()) {
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
        b(charSequence.toString().trim());
    }
}
