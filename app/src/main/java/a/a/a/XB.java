package a.a.a;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class XB extends MB {

    private final Pattern f = Pattern.compile("[A-Fa-f0-9]*");
    private final View g;

    public XB(Context var1, TextInputLayout var2, View var3) {
        super(var1, var2);
        g = var3;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        String var6 = var1.toString().trim();
        if (var6.length() > 8) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, 2131625432, 8));
            d = false;
        } else {
            if (f.matcher(var6).matches()) {
                try {
                    var6 = String.format("#%8s", var6).replaceAll(" ", "F");
                    g.setBackgroundColor(Color.parseColor(var6));
                } catch (Exception var5) {
                    b.setErrorEnabled(true);
                    b.setError(xB.b().a(a, 2131625431));
                    d = false;
                    g.setBackgroundColor(-592138);
                }

                b.setErrorEnabled(false);
                d = true;
            } else {
                b.setErrorEnabled(true);
                b.setError(xB.b().a(a, 2131625431));
                g.setBackgroundColor(-592138);
                d = false;
            }

        }
    }
}
