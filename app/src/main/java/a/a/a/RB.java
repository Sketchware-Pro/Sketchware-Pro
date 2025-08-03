//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import android.content.Context;
import android.text.Spanned;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

public class RB extends MB {
    public Pattern f = Pattern.compile("^[a-z][a-z0-9_ ]*");

    public RB(Context var1, TextInputLayout var2) {
        super(var1, var2);
    }

    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
        return null;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        if (var1.toString().trim().length() <= 0) {
            super.b.setErrorEnabled(true);
            super.b.setError(xB.b().a(super.a, 2131625433, new Object[]{1}));
            super.d = false;
        } else if (this.f.matcher(var1.toString()).matches()) {
            super.b.setErrorEnabled(false);
            super.d = true;
        } else {
            super.b.setErrorEnabled(true);
            super.b.setError(xB.b().a(super.a, 2131625437));
            super.d = false;
        }

    }
}
