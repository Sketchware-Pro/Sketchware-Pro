package mod.hasrat.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ComponentHelper implements TextWatcher {

    private final EditText[] mEditArray;
    private final EditText mTypeClass;

    public ComponentHelper(EditText[] editTextArr, EditText editText) {
        mEditArray = editTextArr;
        mTypeClass = editText;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String charSequence2 = s.toString();
        for (EditText editText : mEditArray) {
            editText.setText(charSequence2);
            mTypeClass.setText("Component." + charSequence2);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
}