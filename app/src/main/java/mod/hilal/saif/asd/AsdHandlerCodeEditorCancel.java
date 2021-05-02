package mod.hilal.saif.asd;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import io.github.rosemoe.editor.widget.CodeEditor;
import mod.SketchwareUtil;

public class AsdHandlerCodeEditorCancel implements View.OnClickListener {

    public final CodeEditor codeEditor;
    public final AsdDialog asdDialog;
    public final LogicEditorActivity logicEditorActivity;

    public AsdHandlerCodeEditorCancel(LogicEditorActivity logicEditorActivity, CodeEditor codeEditor, AsdDialog asdDialog) {
        this.codeEditor = codeEditor;
        this.asdDialog = asdDialog;
        this.logicEditorActivity = logicEditorActivity;
    }

    public void onClick(View v) {
        SketchwareUtil.hideKeyboard(codeEditor);
        asdDialog.dismiss();
    }
}