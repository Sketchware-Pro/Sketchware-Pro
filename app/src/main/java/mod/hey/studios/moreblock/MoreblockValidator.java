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
    public ArrayList h;
    public String i;
    public Pattern j = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_\\<\\>\\,\\|\\[\\] ]*");

    public MoreblockValidator(Context context, TextInputLayout textInputLayout, String[] strArr, String[] strArr2, ArrayList arrayList) {
        super(context, textInputLayout);
        this.f = strArr;
        this.g = strArr2;
        this.h = arrayList;
    }

    public void a(String[] strArr) {
        this.g = strArr;
    }

    public CharSequence filter(CharSequence charSequence, int i2, int i3, Spanned spanned, int i4, int i5) {
        return null;
    }

    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        int length = charSequence.toString().trim().length();
        Integer num = new Integer(1);
        if (length < 1) {
            ((MB) this).b.setErrorEnabled(true);
            TextInputLayout textInputLayout = ((MB) this).b;
            xB b = xB.b();
            Context context = ((MB) this).a;
            Object[] objArr = new Object[1];
            objArr[0] = num;
            textInputLayout.setError(b.a(context, 2131625433, objArr));
            ((MB) this).d = false;
        } else if (charSequence.toString().trim().length() > 60) {
            ((MB) this).b.setErrorEnabled(true);
            TextInputLayout textInputLayout2 = ((MB) this).b;
            xB b2 = xB.b();
            Context context2 = ((MB) this).a;
            Object[] objArr2 = new Object[1];
            objArr2[0] = new Integer(60);
            textInputLayout2.setError(b2.a(context2, 2131625432, objArr2));
            ((MB) this).d = false;
        } else {
            String str = this.i;
            if (str != null && str.length() > 0 && charSequence.toString().equals(this.i)) {
                ((MB) this).b.setErrorEnabled(false);
                ((MB) this).d = true;
            } else if (this.h.indexOf(charSequence.toString()) >= 0) {
                ((MB) this).b.setErrorEnabled(true);
                ((MB) this).b.setError(xB.b().a(((MB) this).a, 2131624950, new Object[0]));
                ((MB) this).d = false;
            } else {
                String[] strArr = this.g;
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
                    ((MB) this).b.setErrorEnabled(true);
                    ((MB) this).b.setError(xB.b().a(((MB) this).a, 2131624950, new Object[0]));
                    ((MB) this).d = false;
                    return;
                }
                String[] strArr2 = this.f;
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
                    ((MB) this).b.setErrorEnabled(true);
                    ((MB) this).b.setError(xB.b().a(((MB) this).a, 2131625495, new Object[0]));
                    ((MB) this).d = false;
                } else if (!Character.isLetter(charSequence.charAt(0))) {
                    ((MB) this).b.setErrorEnabled(true);
                    ((MB) this).b.setError(xB.b().a(((MB) this).a, 2131625497, new Object[0]));
                    ((MB) this).d = false;
                } else {
                    if (this.j.matcher(charSequence.toString()).matches()) {
                        ((MB) this).b.setErrorEnabled(false);
                        ((MB) this).d = true;
                    } else {
                        ((MB) this).b.setErrorEnabled(true);
                        ((MB) this).b.setError(xB.b().a(((MB) this).a, 2131625436, new Object[0]));
                        ((MB) this).d = false;
                    }
                    if (charSequence.toString().trim().length() < 1) {
                        ((MB) this).b.setErrorEnabled(true);
                        TextInputLayout textInputLayout3 = ((MB) this).b;
                        xB b3 = xB.b();
                        Context context3 = ((MB) this).a;
                        Object[] objArr3 = new Object[1];
                        objArr3[0] = num;
                        textInputLayout3.setError(b3.a(context3, 2131625433, objArr3));
                        ((MB) this).d = false;
                    }
                }
            }
        }
    }
}
