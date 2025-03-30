//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.content.Context;
import android.text.Spanned;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

public class UB extends MB {
    public Pattern f = Pattern.compile("([a-zA-Z][a-zA-Z\\d]*\\.)*[a-zA-Z][a-zA-Z\\d]*");

    public UB(Context var1, TextInputLayout var2) {
        super(var1, var2);
    }

    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
        return null;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        var2 = var1.toString().trim().length();
        Integer var5 = 50;
        if (var2 > 50) {
            super.b.setErrorEnabled(true);
            if (super.e == 0) {
                super.b.setError(xB.b().a(super.a, 2131625432, new Object[]{var5}));
            } else {
                super.b.setError(xB.b().a(super.a, super.e, new Object[]{var5}));
            }

            super.d = false;
        } else {
            super.b.setErrorEnabled(false);
            super.d = true;
            if (this.f.matcher(var1.toString()).matches()) {
                if (var1.toString().indexOf(".") < 0) {
                    super.b.setErrorEnabled(true);
                    super.b.setError(xB.b().a(super.a, 2131625683));
                    super.d = false;
                    return;
                }

                super.b.setErrorEnabled(false);
                super.d = true;
            } else {
                super.b.setErrorEnabled(true);
                super.b.setError(xB.b().a(super.a, 2131625435));
                super.d = false;
            }

            String[] var15 = var1.toString().split("\\.");
            int var6 = var15.length;
            var2 = 0;

            for(var3 = 0; var2 < var6; var3 = var4) {
                String var7 = var15[var2];
                String[] var10 = uq.b;
                int var8 = var10.length;
                int var9 = 0;

                while(true) {
                    var4 = var3;
                    if (var9 >= var8) {
                        break;
                    }

                    if (var10[var9].equals(var7)) {
                        var4 = 1;
                        break;
                    }

                    ++var9;
                }

                ++var2;
            }

            if (var3) {
                super.b.setErrorEnabled(true);
                super.b.setError(xB.b().a(super.a, 2131625495));
                super.d = false;
            }

        }
    }
}
