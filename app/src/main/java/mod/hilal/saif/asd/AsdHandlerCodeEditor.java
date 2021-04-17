package mod.hilal.saif.asd;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import io.github.rosemoe.editor.widget.CodeEditor;
import mod.SketchwareUtil;

public class AsdHandlerCodeEditor implements View.OnClickListener {
    
    public final boolean b;
    public final String con;
    public final AsdDialog dialog;
    public final LogicEditorActivity e;
    public final CodeEditor editt;
    public final Ss s;

    public AsdHandlerCodeEditor(LogicEditorActivity logicEditorActivity, String str, boolean z, Ss ss, AsdDialog asdDialog, CodeEditor codeEditor) {
        e = logicEditorActivity;
        con = str;
        b = z;
        s = ss;
        dialog = asdDialog;
        editt = codeEditor;
    }

    public void onClick(View v) {
        String str = "";
        String content = editt.getText().toString();
        if (b) {
            try {
                double parseDouble = Double.parseDouble(content);
                if (!Double.isNaN(parseDouble) && !Double.isInfinite(parseDouble)) {
                    str = content;
                }
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
            content = str;
        } else if (content.length() > 0 && content.charAt(0) == '@') {
            content = " " + content;
        }
        e.a(s, (Object) content);
        SketchwareUtil.hideKeyboard();
        dialog.dismiss();
    }
}