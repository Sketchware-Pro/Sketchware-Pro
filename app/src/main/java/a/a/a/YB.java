package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class YB extends MB {

    private String[] f;
    private ArrayList<String> g;
    private String h;
    private Pattern i = Pattern.compile("^[a-z][a-z0-9_]*");

    public YB(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        f = strArr;
        g = arrayList;
    }

    public YB(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        f = strArr;
        g = arrayList;
        h = str;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().length() < 3) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d9, 3));
            d = false;
            return;
        }
        if (charSequence.toString().trim().length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d8, 100));
            d = false;
            return;
        }
        String str = h;
        if (str != null && str.length() > 0 && charSequence.toString().equals(h)) {
            b.setErrorEnabled(false);
            d = true;
            return;
        }
        for (String str2 : f) {
            if (charSequence.toString().equals(str2)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 0x7f0e0617));
                d = false;
                return;
            }
        }
        if ("main".equals(charSequence.toString()) || g.contains(charSequence.toString())) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e03f6));
            d = false;
        } else if (!Character.isLetter(charSequence.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e0619));
            d = false;
        } else if (this.i.matcher(charSequence.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05dd));
            d = false;
        }
    }
}
