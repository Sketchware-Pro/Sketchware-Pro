package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class UB extends MB {

    public Pattern f = Pattern.compile("([a-zA-Z][a-zA-Z\\d]*\\.)*[a-zA-Z][a-zA-Z\\d]*");

    public UB(Context conte, TextInputLayout textInputLayout) {
        super(conte, textInputLayout);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        start = s.toString().trim().length();
        Integer var5 = 50;
        if (start > 50) {
            b.setErrorEnabled(true);
            if (e == 0) {
                b.setError(xB.b().a(a, 2131625432, var5));
            } else {
                b.setError(xB.b().a(a, e, var5));
            }

            d = false;
        } else {
            b.setErrorEnabled(false);
            d = true;
            if (f.matcher(s.toString()).matches()) {
                if (s.toString().indexOf(".") < 0) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131625683));
                    d = false;
                    return;
                }

                b.setErrorEnabled(false);
                d = true;
            } else {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131625435));
                d = false;
            }

            String[] var15 = s.toString().split("\\.");
            int var6 = var15.length;
            start = 0;

            for (before = 0; start < var6; before = count) {
                String var7 = var15[start];
                String[] var10 = uq.b;
                int var8 = var10.length;
                int var9 = 0;

                while (true) {
                    count = before;
                    if (var9 >= var8) {
                        break;
                    }

                    if (var10[var9].equals(var7)) {
                        count = 1;
                        break;
                    }

                    ++var9;
                }

                ++start;
            }

            if (before) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131625495));
                d = false;
            }

        }
    }
}
