package mod.hilal.saif.moreblock;

import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.Arrays;

import a.a.a.dt;
import a.a.a.uq;
import mod.w3wide.lib.BaseTextWatcher;

public class MoreBlockCustomParams {

    public static boolean err = false;

    public static void customParams(final dt dt) {

        final EditText parameter = dt.findViewById(Resources.id.parameter);
        final EditText name = dt.findViewById(Resources.id.name);
        final Button add = dt.findViewById(Resources.id.add);

        final TextInputLayout p_input = (TextInputLayout) parameter.getParent().getParent();
        p_input.setHint("Parameter: m.name");
        final TextInputLayout n_input = (TextInputLayout) name.getParent().getParent();
        n_input.setHint("Variable name");
        parameter.addTextChangedListener(new BaseTextWatcher() {
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
        });

        add.setOnClickListener(v -> {
            if (!err && !name.equals("") && !parameter.equals("")) {
                dt.l.add(new Pair<>(parameter.getText().toString(), name.getText().toString()));
                dt.a(dt.b, dt.c, dt.p, dt.g.getText().toString(), dt.l);
                parameter.setText("");
                name.setText("");
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(uq.a()));
                for (Pair<String, String> next : dt.l) {
                    if (!(next.first).equals("t")) {
                        arrayList.add(next.second);
                    }
                }
                dt.m.a(arrayList.toArray(new String[0]));
            }
        });
    }
}
