package a.a.a;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public abstract class MB implements TextWatcher, InputFilter {

    public Context context;
    public TextInputLayout textInputLayout;
    public EditText editText;
    public boolean isInputValid;
    public int e;

    public MB(Context context, TextInputLayout textInputLayout) {
        this.context = context;
        this.textInputLayout = textInputLayout;
        editText = textInputLayout.getEditText();
        editText.setFilters(new InputFilter[]{this});
        editText.addTextChangedListener(this);
    }

    public String a() {
        return editText.getText().toString();
    }

    public void a(String str) {
        isInputValid = true;
        editText.setText(str);
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
            textInputLayout.setErrorEnabled(false);
        }
    }

    public boolean b() {
        if (!isInputValid) editText.requestFocus();
        return isInputValid;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
