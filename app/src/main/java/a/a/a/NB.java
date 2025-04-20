package a.a.a;

import android.content.Context;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import pro.sketchware.R;

public class NB extends MB {

    private final ArrayList<String> preDefNames;

    public NB(Context var1, TextInputLayout var2, ArrayList<String> names) {
        super(var1, var2);
        preDefNames = names;
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String inputValue = charSequence.toString();
        if (preDefNames.contains(inputValue)) {
            b.setErrorEnabled(true);
            b.setError(xB.b().a(a, R.string.common_message_name_unavailable));
            d = false;
        } else {
            b.setErrorEnabled(false);
            d = true;
        }
    }
}
