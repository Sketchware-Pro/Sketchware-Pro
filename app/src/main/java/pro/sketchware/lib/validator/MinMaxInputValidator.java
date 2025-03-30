package pro.sketchware.lib.validator;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import a.a.a.MB;

public class MinMaxInputValidator extends MB {
    public int minValue;
    public int maxValue;

    public MinMaxInputValidator(Context context, TextInputLayout textInputLayout, int minValue, int maxValue) {
        super(context, textInputLayout);
        this.minValue = minValue;
        this.maxValue = maxValue;
        c = textInputLayout.getEditText();
        c.setFilters(new InputFilter[]{this});
        c.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String inputString = s.toString();
        if (inputString.isEmpty()) {
            b.setError(String.format(Locale.US, "%d ~ %d", minValue, maxValue));
            d = false;
        } else {
            try {
                int inputNumber = Integer.parseInt(inputString);
                if (inputNumber >= minValue && inputNumber <= maxValue) {
                    b.setError(null);
                    d = true;
                } else {
                    b.setError(String.format(Locale.US, "%d ~ %d", minValue, maxValue));
                    d = false;
                }
            } catch (NumberFormatException e) {
                b.setError(String.format(Locale.US, "%d ~ %d", minValue, maxValue));
                d = false;
            }
        }
    }
}
