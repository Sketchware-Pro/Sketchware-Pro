package mod.hilal.saif.asd.asdforall;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import mod.SketchwareUtil;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;

public class AsdAllEditorNo implements View.OnClickListener {

    public final CodeEditorEditText a;
    public final AsdAllEditor b;
    public final LogicEditorActivity c;

    public AsdAllEditorNo(LogicEditorActivity logicEditorActivity, CodeEditorEditText codeEditorEditText, AsdAllEditor asdAllEditor) {
        a = codeEditorEditText;
        b = asdAllEditor;
        c = logicEditorActivity;
    }

    @Override
    public void onClick(View v) {
        SketchwareUtil.hideKeyboard();
        b.dismiss();
    }
}
