package mod.hilal.saif.asd;

import android.view.View;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.SketchwareUtil;

public class AsdHandler implements View.OnClickListener {

    public final boolean b;
    public final AsdOrigin dialog;
    public final LogicEditorActivity e;
    public final EditText edittext;
    public final Ss s;

    public AsdHandler(LogicEditorActivity logicEditorActivity, EditText editText, boolean z, Ss ss, AsdOrigin asdOrigin) {
        e = logicEditorActivity;
        edittext = editText;
        b = z;
        s = ss;
        dialog = asdOrigin;
    }

    public void onClick(View v) {
        String str = "";
        String editable = edittext.getText().toString();
        if (b) {
            try {
                double parseDouble = Double.parseDouble(editable);
                if (!Double.isNaN(parseDouble) && !Double.isInfinite(parseDouble)) {
                    str = editable;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            editable = str;
        } else if (editable.length() > 0 && editable.charAt(0) == '@') {
            editable = " " + editable;
        }
        e.a(s, (Object)editable);
        SketchwareUtil.hideKeyboard();
        dialog.dismiss();
    }
}
