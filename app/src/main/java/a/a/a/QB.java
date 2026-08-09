package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import pro.sketchware.R;

public class QB extends MB {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*");

    public String[] f;
    public ArrayList<String> g;
    public ArrayList<String> h;
    public String i;
    public int j;
    public Pattern k = VALID_NAME_PATTERN;

    public QB(Context context, TextInputLayout textInputLayout, String[] reservedKeywords, ArrayList<String> existingNames, ArrayList<String> additionalExistingNames) {
        super(context, textInputLayout);
        f = reservedKeywords;
        g = existingNames;
        h = additionalExistingNames;
        j = 1;
    }

    public void a(int count) {
        j = count;
        if (a().length() > 0) {
            b(a());
        }
    }

    public void a(ArrayList<String> list) {
        h = list;
    }

    public void c(String originalName) {
        i = originalName;
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
        if (j != 1) {
            ArrayList<String> duplicates = new ArrayList<>();
            for (int index = 1; index <= j; index++) {
                String candidate = name + "_" + index;
                if (g != null && g.contains(candidate)) {
                    duplicates.add(candidate);
                }
            }
            if (!duplicates.isEmpty()) {
                String baseMsg = a.getString(R.string.common_message_name_unavailable);
                setError(baseMsg + "\n[" + String.join(", ", duplicates) + "]");
                return;
            }
        } else if (!name.equals(i)) {
            if (g != null && g.contains(name)) {
                setError(a.getString(R.string.common_message_name_unavailable));
                return;
            }
            if (h != null && h.contains(name)) {
                setError(a.getString(R.string.common_message_name_unavailable));
                return;
            }
        }

        if (isReservedKeyword(name)) {
            setError(a.getString(R.string.logic_editor_message_reserved_keywords));
        } else if (!Character.isLetter(name.charAt(0))) {
            setError(a.getString(R.string.logic_editor_message_variable_name_must_start_letter));
        } else if (k.matcher(name).matches()) {
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
