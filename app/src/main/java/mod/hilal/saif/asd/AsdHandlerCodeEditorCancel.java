package mod.hilal.saif.asd;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;
import io.github.rosemoe.editor.widget.CodeEditor;
import mod.SketchwareUtil;

public class AsdHandlerCodeEditorCancel implements View.OnClickListener {

    public final CodeEditor a;
    public final AsdDialog b;
    public final LogicEditorActivity c;

    public AsdHandlerCodeEditorCancel(LogicEditorActivity logicEditorActivity, CodeEditor codeEditor, AsdDialog asdDialog) {
        a = codeEditor;
        b = asdDialog;
        c = logicEditorActivity;
    }

    public void onClick(View v) {
        SketchwareUtil.hideKeyboard();
        b.dismiss();
    }
}