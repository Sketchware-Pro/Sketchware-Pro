package mod.hilal.saif.asd;

import android.view.View;

import io.github.rosemoe.sora.widget.CodeEditor;
import mod.SketchwareUtil;

public class AsdHandlerCodeEditorCancel implements View.OnClickListener {

    private final CodeEditor codeEditor;
    private final AsdDialog asdDialog;

    public AsdHandlerCodeEditorCancel(CodeEditor codeEditor, AsdDialog asdDialog) {
        this.codeEditor = codeEditor;
        this.asdDialog = asdDialog;
    }

    @Override
    public void onClick(View v) {
        SketchwareUtil.hideKeyboard(codeEditor);
        asdDialog.dismiss();
    }
}
