package pro.sketchware.lib;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Base class for scenarios where the user only wants to implement one method of {@link TextWatcher}.
 */
public class BaseTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
