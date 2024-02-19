package a.a.a;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
        a = context;
        b = textInputLayout;
        c = textInputLayout.getEditText();
        c.setFilters(new InputFilter[]{this});
        c.addTextChangedListener(this);
    }

    public String a() {
        return c.getText().toString();
    }

    public void a(String str) {
        d = true;
        c.setText(str);
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        return null;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().isEmpty()) {
            b.setErrorEnabled(false);
        }
    }

    public boolean b() {
        if (!d) c.requestFocus();
        return d;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
