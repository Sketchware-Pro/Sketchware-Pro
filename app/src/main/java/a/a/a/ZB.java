package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ZB extends MB {

    public String[] f;
    public String[] g;
    public ArrayList<String> h;
    public String i;
    public Pattern j;

    public ZB(Context context, TextInputLayout textInputLayout, String[] strArr, String[] strArr2, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        j = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");
        f = strArr;
        g = strArr2;
        h = arrayList;
    }

    public void a(String[] strArr) {
        g = strArr;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence.toString().trim().isEmpty()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d9, 1));
            d = false;
            return;
        }
        if (charSequence.toString().trim().length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d8, 100));
            d = false;
            return;
        }
        String str = this.i;
        if (str != null && !str.isEmpty() && charSequence.toString().equals(this.i)) {
            b.setErrorEnabled(false);
            d = true;
            return;
        }
        if (h.contains(charSequence.toString())) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e03f6));
            d = false;
            return;
        }
        for (String str2 : g) {
            if (charSequence.toString().equals(str2)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 0x7f0e03f6));
                d = false;
                return;
            }
        }
        for (String str3 : f) {
            if (charSequence.toString().equals(str3)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 0x7f0e0617));
                d = false;
                return;
            }
        }
        if (!Character.isLetter(charSequence.charAt(0))) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e0619));
            d = false;
            return;
        }
        if (j.matcher(charSequence.toString()).matches()) {
            b.setErrorEnabled(false);
            d = true;
        } else {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05dc));
            d = false;
        }
        if (charSequence.toString().trim().isEmpty()) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 0x7f0e05d9, 1));
            d = false;
        }
    }
}
