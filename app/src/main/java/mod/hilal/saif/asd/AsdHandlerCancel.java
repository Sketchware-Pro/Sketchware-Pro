package mod.hilal.saif.asd;

import android.view.View;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

import mod.SketchwareUtil;

public class AsdHandlerCancel implements View.OnClickListener {

    public final EditText a;
    public final AsdOrigin b;
    public final LogicEditorActivity c;

    public AsdHandlerCancel(LogicEditorActivity logicEditorActivity, EditText editText, AsdOrigin asdOrigin) {
        a = editText;
        b = asdOrigin;
        c = logicEditorActivity;
    }

    public void onClick(View v) {
        SketchwareUtil.hideKeyboard();
        b.dismiss();
    }
}