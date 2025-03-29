//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
        this.f = var3;
        this.g = var4;
        this.h = var5;
        this.i = var6;
    }

    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
        return null;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        String var5 = var1.toString().trim().toLowerCase();
        if (var5.length() < 1) {
            super.b.setErrorEnabled(true);
            super.b.setError(xB.b().a(super.a, 2131625433, new Object[]{1}));
            super.d = false;
        } else if (var5.length() > 100) {
            super.b.setErrorEnabled(true);
            super.b.setError(xB.b().a(super.a, 2131625432, new Object[]{100}));
            super.d = false;
        } else {
            String var6 = this.i;
            if (var6 != null && var6.length() > 0 && var5.equals(this.i.toLowerCase())) {
                super.b.setErrorEnabled(false);
                super.d = true;
            } else if (this.h.indexOf(var5) >= 0) {
                super.b.setErrorEnabled(true);
                super.b.setError(xB.b().a(super.a, 2131624950));
                super.d = false;
            } else {
                var6 = this.g;
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
                    super.b.setErrorEnabled(true);
                    super.b.setError(xB.b().a(super.a, 2131624950));
                    super.d = false;
                } else {
                    var6 = this.f;
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
                        super.b.setErrorEnabled(true);
                        super.b.setError(xB.b().a(super.a, 2131625495));
                        super.d = false;
                    } else if (!Character.isLetter(var5.charAt(0))) {
                        super.b.setErrorEnabled(true);
                        super.b.setError(xB.b().a(super.a, 2131625497));
                        super.d = false;
                    } else {
                        if (this.j.matcher(var1).matches()) {
                            super.b.setErrorEnabled(false);
                            super.d = true;
                        } else {
                            super.b.setErrorEnabled(true);
                            super.b.setError(xB.b().a(super.a, 2131625436));
                            super.d = false;
                        }

                    }
                }
            }
        }
    }
}
