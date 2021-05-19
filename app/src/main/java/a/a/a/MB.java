package a.a.a;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public abstract class MB implements TextWatcher, InputFilter {
    public Context a;
    public TextInputLayout b;
    public EditText c;
    public boolean d;
    public int e;

    public MB(Context context, TextInputLayout textInputLayout) {
        this.a = context;
        this.b = textInputLayout;
        this.c = textInputLayout.getEditText();
        this.c.setFilters(new InputFilter[]{this});
        this.c.addTextChangedListener(this);
    }

    public String a() {
        return this.c.getText().toString();
    }

    public void a(String str) {
        this.d = true;
        this.c.setText(str);
    }

    public void afterTextChanged(Editable editable) {
        if (editable.toString().isEmpty()) {
            this.b.setErrorEnabled(false);
        }
    }

    public boolean b() {
        if (!this.d) {
            this.c.requestFocus();
        }
        return this.d;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
