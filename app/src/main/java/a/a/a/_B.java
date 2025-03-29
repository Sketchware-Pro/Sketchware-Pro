package a.a.a;

import android.content.Context;
import android.text.Spanned;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class _B extends MB {

    public String[] f;
    public String[] g;
    public ArrayList<String> h;
    public String i;
    public Pattern j = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]*");

    public _B(Context var1, TextInputLayout var2, String[] var3, String[] var4, ArrayList<String> var5, String var6) {
        super(var1, var2);
        f = var3;
        g = var4;
        h = var5;
        i = var6;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        String var5 = var1.toString().trim().toLowerCase();
        if (var5.length() < 1) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625433, 1));
            d = false;
        } else if (var5.length() > 100) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625432, 100));
            d = false;
        } else {
            String var6 = i;
            if (var6 != null && var6.length() > 0 && var5.equals(i.toLowerCase())) {
                b.setErrorEnabled(false);
                d = true;
            } else if (h.contains(var5)) {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131624950));
                d = false;
            } else {
                var6 = g;
                var3 = ((Object[]) var6).length;
                var2 = 0;

                while (true) {
                    if (var2 >= var3) {
                        var2 = 0;
                        break;
                    }

                    if (var5.equals(((Object[]) var6)[var2])) {
                        var2 = 1;
                        break;
                    }

                    ++var2;
                }

                if (var2) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131624950));
                    d = false;
                } else {
                    var6 = f;
                    var3 = ((Object[]) var6).length;
                    var2 = 0;

                    while (true) {
                        if (var2 >= var3) {
                            var2 = 0;
                            break;
                        }

                        if (var5.equals(((Object[]) var6)[var2])) {
                            var2 = 1;
                            break;
                        }

                        ++var2;
                    }

                    if (var2) {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, 2131625495));
                        d = false;
                    } else if (!Character.isLetter(var5.charAt(0))) {
                        b.setErrorEnabled(true);
                        b.setError(xB.b().a(a, 2131625497));
                        d = false;
                    } else {
                        if (j.matcher(var1).matches()) {
                            b.setErrorEnabled(false);
                            d = true;
                        } else {
                            b.setErrorEnabled(true);
                            b.setError(xB.b().a(a, 2131625436));
                            d = false;
                        }

                    }
                }
            }
        }
    }
}
