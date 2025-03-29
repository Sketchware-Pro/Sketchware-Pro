//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.content.Context;
import android.text.Spanned;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class NB extends MB {
    public ArrayList<String> f;
    public String g;

    public NB(Context var1, TextInputLayout var2, ArrayList<String> var3) {
        super(var1, var2);
        this.f = var3;
    }

    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
        return null;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        String var5 = var1.toString();
        String var6 = this.g;
        if (var6 != null && var6.length() > 0 && var5.equals(this.g)) {
            super.b.setErrorEnabled(false);
            super.d = true;
        } else if (this.f.indexOf(var5) >= 0) {
            super.b.setErrorEnabled(true);
            super.b.setError(xB.b().a(super.a, 2131624950));
            super.d = false;
        } else {
            super.b.setErrorEnabled(false);
            super.d = true;
        }
    }
}
