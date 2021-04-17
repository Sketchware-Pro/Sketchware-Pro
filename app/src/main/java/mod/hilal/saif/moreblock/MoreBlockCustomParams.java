package mod.hilal.saif.moreblock;

import a.a.a.dt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MoreBlockCustomParams {
    public static boolean err = false;

    public static void customParams(final dt dtVar) {
        final String[] strArr = {"onCreate", "setContentView", "initialize", "initializeLogic", "getRandom", "showMessage", "getDip", "getDisplayWidthPixels", "getDisplayHeightPixels"};
        final EditText editText = (EditText) dtVar.findViewById(2131232617);
        final EditText editText2 = (EditText) dtVar.findViewById(2131232618);
        final TextInputLayout parent = (TextInputLayout) editText.getParent().getParent();
        parent.setHint("param : m.name");
        /**
         * idk but this getParent().setHint(String) doesn't work.
         */
        // editText2.getParent().getParent().setHint("variable name");
        editText2.setHint("Variable name");
        editText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.matches("[mldb]\\.[a-zA-Z]+")) {
                    MoreBlockCustomParams.err = false;
                } else if (charSequence2.equals("")) {
                    MoreBlockCustomParams.err = false;
                } else {
                    MoreBlockCustomParams.err = true;
                }
                parent.setError("invalid format");
                parent.setErrorEnabled(MoreBlockCustomParams.err);
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
            }
        });
        ((Button) dtVar.findViewById(2131232619)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (!MoreBlockCustomParams.err && !editText2.equals("") && !editText.equals("")) {
                    dtVar.l.add(new Pair<>(editText.getText().toString(), editText2.getText().toString()));
                    dtVar.a(dtVar.b, dtVar.c, dtVar.p, dtVar.g.getText().toString(), dtVar.l);
                    editText.setText("");
                    editText2.setText("");
                    ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
                    Iterator<Pair<String, String>> it = dtVar.l.iterator();
                    while (it.hasNext()) {
                        Pair<String, String> next = it.next();
                        if (!((String) next.first).equals("t")) {
                            arrayList.add(next.second);
                        }
                    }
                    dtVar.m.a((String[]) arrayList.toArray(new String[arrayList.size()]));
                }
            }
        });
    }
}
