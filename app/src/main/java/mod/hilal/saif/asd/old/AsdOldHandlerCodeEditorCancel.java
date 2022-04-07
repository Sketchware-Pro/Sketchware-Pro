package mod.hilal.saif.asd.old;

import android.view.View;

import mod.SketchwareUtil;

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
