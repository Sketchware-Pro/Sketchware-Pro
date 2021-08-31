package mod.hilal.saif.moreblock;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.Arrays;

import a.a.a.dt;

public class MoreBlockCustomParams {

    public static boolean err = false;

    public static void customParams(final dt dt) {
        final String[] m = {"onCreate", "setContentView", "initialize", "initializeLogic", "getRandom", "showMessage", "getDip", "getDisplayWidthPixels", "getDisplayHeightPixels"};

        final EditText parameter = dt.findViewById(Resources.id.parameter);
        final EditText name = dt.findViewById(Resources.id.name);
        final Button add = dt.findViewById(Resources.id.add);

        final TextInputLayout p_input = (TextInputLayout) parameter.getParent().getParent();
        p_input.setHint("Parameter: m.name");
        final TextInputLayout n_input = (TextInputLayout) name.getParent().getParent();
        n_input.setHint("Variable name");
        parameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                final String s = sequence.toString();

                if (s.matches("[mldb]\\.[a-zA-Z]+")) {
                    err = false;
                } else {
                    err = !s.equals("");
                }
                p_input.setError("Invalid format");
                p_input.setErrorEnabled(err);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        add.setOnClickListener(v -> {
            if (!err && !name.equals("") && !parameter.equals("")) {
                dt.l.add(new Pair<>(parameter.getText().toString(), name.getText().toString()));
                dt.a(dt.b, dt.c, dt.p, dt.g.getText().toString(), dt.l);
                parameter.setText("");
                name.setText("");
                ArrayList<Object> arrayList = new ArrayList<>(Arrays.asList(m));
                for (Pair<String, String> next : dt.l) {
                    if (!(next.first).equals("t")) {
                        arrayList.add(next.second);
                    }
                }
                dt.m.a((String[]) arrayList.toArray(new String[0]));
            }
        });
    }
}
