package mod.hilal.saif.asd.old;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import mod.SketchwareUtil;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;

public class AsdOldHandlerCodeEditorCancel implements View.OnClickListener {

    public final CodeEditorEditText a;
    public final AsdOldDialog b;
    public final LogicEditorActivity c;

    public AsdOldHandlerCodeEditorCancel(LogicEditorActivity logicEditorActivity, CodeEditorEditText codeEditorEditText, AsdOldDialog asdOldDialog) {
        a = codeEditorEditText;
        b = asdOldDialog;
        c = logicEditorActivity;
    }

    public void onClick(View v) {
        SketchwareUtil.hideKeyboard();
        b.dismiss();
    }
}