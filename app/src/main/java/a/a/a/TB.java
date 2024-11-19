package a.a.a;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class TB extends MB {
    public int minValue;
    public int maxValue;

    public TB(Context context, TextInputLayout textInputLayout, int minValue, int maxValue) {
        super(context, textInputLayout);
        this.minValue = minValue;
        this.maxValue = maxValue;
        super.c = textInputLayout.getEditText();
        super.c.setFilters(new InputFilter[]{this});
        super.c.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String inputString = s.toString();
        if (inputString.isEmpty()) {
            super.b.setError(String.format(Locale.US, "%d ~ %d", minValue, maxValue));
            super.d = false;
        } else {
            try {
                int inputNumber = Integer.parseInt(inputString);
                if (inputNumber >= minValue && inputNumber <= maxValue) {
                    super.b.setError(null);
                    super.d = true;
                } else {
                    super.b.setError(String.format(Locale.US, "%d ~ %d", minValue, maxValue));
                    super.d = false;
                }
            } catch (NumberFormatException e) {
                super.b.setError(String.format(Locale.US, "%d ~ %d", minValue, maxValue));
                super.d = false;
            }
        }
    }
}
