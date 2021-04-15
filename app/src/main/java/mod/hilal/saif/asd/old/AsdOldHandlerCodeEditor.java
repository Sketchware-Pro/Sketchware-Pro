package mod.hilal.saif.asd.old;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.SketchwareUtil;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;

public class AsdOldHandlerCodeEditor implements View.OnClickListener {
    
    public final boolean b;
    public final String con;
    public final AsdOldDialog dialog;
    public final LogicEditorActivity e;
    public final CodeEditorEditText editt;
    public final Ss s;

    public AsdOldHandlerCodeEditor(LogicEditorActivity logicEditorActivity, String str, boolean z, Ss ss, AsdOldDialog asdOldDialog, CodeEditorEditText codeEditorEditText) {
        e = logicEditorActivity;
        con = str;
        b = z;
        s = ss;
        dialog = asdOldDialog;
        editt = codeEditorEditText;
    }

    public void onClick(View v) {
        String str = "";
        String editable = editt.getText().toString();
        if (b) {
            try {
                double parseDouble = Double.parseDouble(editable);
                if (!Double.isNaN(parseDouble) && !Double.isInfinite(parseDouble)) {
                    str = editable;
                }
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
            editable = str;
        } else if (editable.length() > 0 && editable.charAt(0) == '@') {
            editable = " " + editable;
        }
        e.a(s, (Object) editable);
        SketchwareUtil.hideKeyboard();
        dialog.dismiss();
    }
}