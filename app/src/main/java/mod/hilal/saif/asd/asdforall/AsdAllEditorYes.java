package mod.hilal.saif.asd.asdforall;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.SketchwareUtil;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;

public class AsdAllEditorYes implements View.OnClickListener {

    public final String con;
    public final AsdAllEditor dialog;
    public final LogicEditorActivity e;
    public final CodeEditorEditText editt;
    public final Ss s;

    public AsdAllEditorYes(LogicEditorActivity logicEditorActivity, String str, Ss ss, AsdAllEditor asdAllEditor, CodeEditorEditText codeEditorEditText) {
        e = logicEditorActivity;
        con = str;
        s = ss;
        dialog = asdAllEditor;
        editt = codeEditorEditText;
    }

    @Override
    public void onClick(View v) {
        e.a(s, (Object) editt.getText().toString());
        SketchwareUtil.hideKeyboard();
        dialog.dismiss();
    }
}
