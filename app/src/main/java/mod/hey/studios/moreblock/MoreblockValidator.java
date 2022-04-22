package mod.hey.studios.moreblock;

import android.content.Context;
import android.text.Spanned;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

import a.a.a.MB;
import a.a.a.xB;

public class MoreblockValidator extends MB {

    public String[] f;
    public String[] g;
    public ArrayList<String> h;
    public String i;
    public Pattern j = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_<>,|\\[\\] ]*");

    public MoreblockValidator(Context context, TextInputLayout textInputLayout, String[] strArr, String[] strArr2, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        f = strArr;
        g = strArr2;
        h = arrayList;
    }

    public void a(String[] strArr) {
        g = strArr;
    }

    public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
        return null;
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        int length = charSequence.toString().trim().length();
        if (length < 1) {
            b.setErrorEnabled(true);
            this.b.setError(xB.b().a(a, R.string.invalid_value_min_lenth, 1));
            d = false;
        } else if (charSequence.toString().trim().length() > 60) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.invalid_value_max_lenth, 60));
            d = false;
        } else {
            if (i != null && i.length() > 0 && charSequence.toString().equals(i)) {
                b.setErrorEnabled(false);
                d = true;
            } else if (h.contains(charSequence.toString())) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131624950, new Object[0]));
                d = false;
            } else {
                int length2 = g.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length2) {
                        z = false;
                        break;
                    }
                    if (charSequence.toString().equals(g[i5])) {
                        z = true;
                        break;
                    }
                    i5++;
                }
                if (z) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131624950, new Object[0]));
                    d = false;
                    return;
                }
                int length3 = f.length;
                int i6 = 0;
                while (true) {
                    if (i6 >= length3) {
                        z2 = false;
                        break;
                    }
                    if (charSequence.toString().equals(f[i6])) {
                        z2 = true;
                        break;
                    }
                    i6++;
                }
                if (z2) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131625495, new Object[0]));
                    d = false;
                } else if (!Character.isLetter(charSequence.charAt(0))) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131625497, new Object[0]));
                    d = false;
                } else {
                    if (j.matcher(charSequence.toString()).matches()) {
                        b.setErrorEnabled(false);
                        d = true;
                    } else {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, R.string.invalid_value_rule_3, new Object[0]));
                        d = false;
                    }
                    if (charSequence.toString().trim().length() < 1) {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, 2131625433, 1));
                        d = false;
                    }
                }
            }
        }
    }
}
