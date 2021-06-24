package mod.hilal.saif.asd.old;

import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;

import mod.SketchwareUtil;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;

public class AsdOldHandlerCodeEditorCancel implements View.OnClickListener {

    private final AsdOldDialog asdOldDialog;

    public AsdOldHandlerCodeEditorCancel(AsdOldDialog asdOldDialog) {
        this.asdOldDialog = asdOldDialog;
    }

    @Override
    public void onClick(View v) {
        SketchwareUtil.hideKeyboard(v);
        asdOldDialog.dismiss();
    }
}
