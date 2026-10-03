package pro.sketchware.lib.validator;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

import a.a.a.MB;
import pro.sketchware.R;

public class FontNameValidator extends MB {
    public String[] reservedKeywords;
    public ArrayList<String> fontNames;
    public String h;
    public Pattern pattern;

    public FontNameValidator(Context context, TextInputLayout textInputLayout, String[] reservedKeywordsArr, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        pattern = Pattern.compile("^[a-z][a-z0-9_]*");
        reservedKeywords = reservedKeywordsArr;
        fontNames = arrayList;
    }

    public FontNameValidator(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        pattern = Pattern.compile("^[a-z][a-z0-9_]*");
        reservedKeywords = strArr;
        fontNames = arrayList;
        h = str;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String a2;
        int msgRes;
        String trim = charSequence.toString().trim();
        if (trim.length() < 3) {
            a2 = a.getString(R.string.invalid_value_min_lenth, 3);
        } else if (trim.length() > 70) {
            a2 = a.getString(R.string.invalid_value_max_lenth, 70);
        } else if (trim.equals("default_image") || "NONE".equalsIgnoreCase(trim) || (!trim.equals(h) && (fontNames != null && fontNames.contains(trim)))) {
            a2 = a.getString(R.string.common_message_name_unavailable);
        } else {
            boolean isReserved = false;
            if (reservedKeywords != null) {
                for (String keyword : reservedKeywords) {
                    if (charSequence.toString().equals(keyword)) {
                        isReserved = true;
                        break;
                    }
                }
            }
            if (isReserved) {
                msgRes = R.string.logic_editor_message_reserved_keywords;
            } else if (!Character.isLetter(charSequence.charAt(0))) {
                msgRes = R.string.logic_editor_message_variable_name_must_start_letter;
            } else if (pattern.matcher(charSequence.toString()).matches()) {
                b.setError(null);
                d = true;
                return;
            } else {
                b.setError(a.getString(R.string.invalid_value_rule_4));
                d = false;
                return;
            }
            a2 = a.getString(msgRes);
        }
        b.setError(a2);
        d = false;
    }
}
