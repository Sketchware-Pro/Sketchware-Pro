package mod.hilal.saif.asd;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import io.github.rosemoe.sora.widget.CodeEditor;

public class AsdHandlerCodeEditor implements View.OnClickListener {

    private final boolean enteringNumber;
    private final AsdDialog asdDialog;
    private final LogicEditorActivity logicEditorActivity;
    private final CodeEditor codeEditor;
    private final Ss ss;

    public AsdHandlerCodeEditor(LogicEditorActivity logicEditorActivity, boolean enteringNumber, Ss ss, AsdDialog asdDialog, CodeEditor codeEditor) {
        this.logicEditorActivity = logicEditorActivity;
        this.enteringNumber = enteringNumber;
        this.ss = ss;
        this.asdDialog = asdDialog;
        this.codeEditor = codeEditor;
    }

    @Override
    public void onClick(View v) {
        String content = codeEditor.getText().toString();
        if (enteringNumber) {
            try {
                double parseDouble = Double.parseDouble(content);
                if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                    content = "";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                content = "";
            }
        } else if (!content.isEmpty() && content.charAt(0) == '@') {
            content = " " + content;
        }
        logicEditorActivity.a(ss, content);
        asdDialog.dismiss();
    }
}
