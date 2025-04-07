//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.content.Context;
import android.graphics.Color;
import android.text.Spanned;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class XB extends MB {
    public Pattern f = Pattern.compile("[A-Fa-f0-9]*");
    public View g;

    public XB(Context var1, TextInputLayout var2, View var3) {
        super(var1, var2);
        this.g = var3;
    }

    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
        return null;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        String var6 = var1.toString().trim();
        if (var6.length() > 8) {
            super.b.setErrorEnabled(true);
            super.b.setError(xB.b().a(super.a, 2131625432, new Object[]{8}));
            super.d = false;
        } else {
            if (this.f.matcher(var6).matches()) {
                try {
                    var6 = String.format("#%8s", var6).replaceAll(" ", "F");
                    this.g.setBackgroundColor(Color.parseColor(var6));
                } catch (Exception var5) {
                    super.b.setErrorEnabled(true);
                    super.b.setError(xB.b().a(super.a, 2131625431));
                    super.d = false;
                    this.g.setBackgroundColor(-592138);
                }

                super.b.setErrorEnabled(false);
                super.d = true;
            } else {
                super.b.setErrorEnabled(true);
                super.b.setError(xB.b().a(super.a, 2131625431));
                this.g.setBackgroundColor(-592138);
                super.d = false;
            }

        }
    }
}
