package a.a.a;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;

import com.google.android.material.textfield.TextInputLayout;

public class TB extends MB {
    public int f;
    public int g;

    public TB(Context var1, TextInputLayout var2, int var3, int var4) {
        super(var1, var2);
        this.f = var3;
        this.g = var4;
        super.c = var2.getEditText();
        super.c.setFilters(new InputFilter[]{this});
        super.c.addTextChangedListener(this);
    }

    public void afterTextChanged(Editable var1) {
    }

    public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
        String var7 = var1.toString();
        TextInputLayout var5;
        String var8;
        if (var7.isEmpty()) {
            super.b.setErrorEnabled(true);
            var5 = super.b;
            var8 = String.format("%d ~ %d", this.f, this.g);
        } else {
            try {
                var2 = Integer.parseInt(var7);
                if (var2 >= this.f && var2 <= this.g) {
                    super.b.setErrorEnabled(false);
                    super.d = true;
                } else {
                    super.b.setErrorEnabled(true);
                    super.b.setError(String.format("%d ~ %d", this.f, this.g));
                    super.d = false;
                }

                return;
            } catch (NumberFormatException var6) {
                super.b.setErrorEnabled(true);
                var5 = super.b;
                var8 = String.format("%d ~ %d", this.f, this.g);
            }
        }

        var5.setError(var8);
        super.d = false;
    }
}