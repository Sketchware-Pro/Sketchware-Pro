package mod.hilal.saif.asd;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import io.github.rosemoe.editor.widget.CodeEditor;
import mod.SketchwareUtil;

public class AsdHandlerCodeEditor implements View.OnClickListener {

    public final boolean b;
    public final String content;
    public final AsdDialog asdDialog;
    public final LogicEditorActivity logicEditorActivity;
    public final CodeEditor codeEditor;
    public final Ss ss;

    public AsdHandlerCodeEditor(LogicEditorActivity logicEditorActivity, String str, boolean z, Ss ss, AsdDialog asdDialog, CodeEditor codeEditor) {
        this.logicEditorActivity = logicEditorActivity;
        content = str;
        b = z;
        this.ss = ss;
        this.asdDialog = asdDialog;
        this.codeEditor = codeEditor;
    }

    public void onClick(View v) {
        String content = codeEditor.getText().toString();
        if (b) {
            try {
                double parseDouble = Double.parseDouble(content);
                if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                    content = "";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                content = "";
            }
        } else if (content.length() > 0 && content.charAt(0) == '@') {
            content = " " + content;
        }
        logicEditorActivity.a(ss, (Object) content);
        SketchwareUtil.hideKeyboard(codeEditor);
        asdDialog.dismiss();
    }
}