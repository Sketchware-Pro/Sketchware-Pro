package a.a.a;

import android.content.Context;
import android.text.Spanned;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class WB2 extends MB {
    public String[] f;
    public ArrayList<String> g;
    public String h;
    public Pattern i;

    public WB2(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList) {
        super(context, textInputLayout);
        this.i = Pattern.compile("^[a-z][a-z0-9_]*");
        this.f = strArr;
        this.g = arrayList;
    }

    public WB2(Context context, TextInputLayout textInputLayout, String[] strArr, ArrayList<String> arrayList, String str) {
        super(context, textInputLayout);
        this.i = Pattern.compile("^[a-z][a-z0-9_]*");
        this.f = strArr;
        this.g = arrayList;
        this.h = str;
    }

    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return null;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        TextInputLayout textInputLayout;
        String a2;
        xB b;
        Context context;
        int i4;
        String trim = charSequence.toString().trim();
        if (trim.length() < 3) {
            ((MB) this).b.setErrorEnabled(true);
            textInputLayout = ((MB) this).b;
            a2 = xB.b().a(((MB) this).a, 0x7f0e05d9, new Object[]{3});
        } else if (trim.length() > 70) {
            ((MB) this).b.setErrorEnabled(true);
            textInputLayout = ((MB) this).b;
            a2 = xB.b().a(((MB) this).a, 0x7f0e05d8, new Object[]{70});
        } else if (trim.equals("default_image") || "NONE".toLowerCase().equals(trim.toLowerCase()) || (!trim.equals(this.h) && this.g.indexOf(trim) >= 0)) {
            ((MB) this).b.setErrorEnabled(true);
            textInputLayout = ((MB) this).b;
            a2 = xB.b().a(((MB) this).a, 0x7f0e03f6);
        } else {
            String[] strArr = this.f;
            int length = strArr.length;
            int i5 = 0;
            while (true) {
                if (i5 < length) {
                    if (charSequence.toString().equals(strArr[i5])) {
                        ((MB) this).b.setErrorEnabled(true);
                        textInputLayout = ((MB) this).b;
                        b = xB.b();
                        context = ((MB) this).a;
                        i4 = 0x7f0e0617;
                        break;
                    }
                    i5++;
                } else if (Character.isLetter(charSequence.charAt(0))) {
                    if (this.i.matcher(charSequence.toString()).matches()) {
                        ((MB) this).b.setErrorEnabled(false);
                        ((MB) this).d = true;
                        return;
                    }
                    ((MB) this).b.setErrorEnabled(true);
                    ((MB) this).b.setError(xB.b().a(((MB) this).a, 0x7f0e05dd));
                    ((MB) this).d = false;
                    return;
                } else {
                    ((MB) this).b.setErrorEnabled(true);
                    textInputLayout = ((MB) this).b;
                    b = xB.b();
                    context = ((MB) this).a;
                    i4 = 0x7f0e0619;
                }
            }
            a2 = b.a(context, i4);
        }
        textInputLayout.setError(a2);
        ((MB) this).d = false;
    }
}
