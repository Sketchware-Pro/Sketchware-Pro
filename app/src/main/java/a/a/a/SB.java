package a.a.a;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import com.google.android.material.textfield.TextInputLayout;

public class SB extends MB {
    public int f;
    public int g;

    public SB(Context var1, TextInputLayout var2, int var3, int var4) {
        super(var1, var2);
        this.f = var3;
        this.g = var4;
        super.c = var2.getEditText();
        super.c.setFilters(new InputFilter[]{this});
        super.c.addTextChangedListener(this);
    }

    public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
        return null;
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        if (var1.toString().trim().length() < this.f) {
            super.b.setErrorEnabled(true);
            if (super.e == 0) {
                super.b.setError(xB.b().a(super.a, 2131625433, new Object[]{this.f}));
            } else {
                super.b.setError(xB.b().a(super.a, super.e, new Object[]{this.f}));
            }

            super.d = false;
        } else {
            if (var1.toString().trim().length() > this.g) {
                super.b.setErrorEnabled(true);
                if (super.e == 0) {
                    super.b.setError(xB.b().a(super.a, 2131625432, new Object[]{this.g}));
                } else {
                    super.b.setError(xB.b().a(super.a, super.e, new Object[]{this.g}));
                }

                super.d = false;
            } else {
                super.b.setErrorEnabled(false);
                super.d = true;
            }

        }
    }
}
