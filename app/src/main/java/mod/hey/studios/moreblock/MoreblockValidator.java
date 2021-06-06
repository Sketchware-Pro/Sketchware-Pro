package mod.hey.studios.moreblock;

import android.content.Context;
import android.text.Spanned;

import com.google.android.material.textfield.TextInputLayout;

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
        int num = 1;
        if (length < 1) {
            b.setErrorEnabled(true);
            TextInputLayout textInputLayout = b;
            xB b = xB.b();
            Context context = a;
            Object[] objArr = new Object[1];
            objArr[0] = num;
            textInputLayout.setError(b.a(context, 2131625433, objArr));
            d = false;
        } else if (charSequence.toString().trim().length() > 60) {
            b.setErrorEnabled(true);
            TextInputLayout textInputLayout2 = b;
            xB b2 = xB.b();
            Context context2 = a;
            Object[] objArr2 = new Object[1];
            objArr2[0] = 60;
            textInputLayout2.setError(b2.a(context2, 2131625432, objArr2));
            d = false;
        } else {
            String str = i;
            if (str != null && str.length() > 0 && charSequence.toString().equals(i)) {
                b.setErrorEnabled(false);
                d = true;
            } else if (h.contains(charSequence.toString())) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131624950, new Object[0]));
                d = false;
            } else {
                String[] strArr = g;
                int length2 = strArr.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length2) {
                        z = false;
                        break;
                    }
                    if (charSequence.toString().equals(strArr[i5])) {
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
                String[] strArr2 = f;
                int length3 = strArr2.length;
                int i6 = 0;
                while (true) {
                    if (i6 >= length3) {
                        z2 = false;
                        break;
                    }
                    if (charSequence.toString().equals(strArr2[i6])) {
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
                        b.setError(xB.b().a(a, 2131625436, new Object[0]));
                        d = false;
                    }
                    if (charSequence.toString().trim().length() < 1) {
                        b.setErrorEnabled(true);
                        TextInputLayout textInputLayout3 = b;
                        xB b3 = xB.b();
                        Context context3 = a;
                        Object[] objArr3 = new Object[1];
                        objArr3[0] = num;
                        textInputLayout3.setError(b3.a(context3, 2131625433, objArr3));
                        d = false;
                    }
                }
            }
        }
    }
}
