package mod.hilal.saif.asd;

import android.view.View;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.SketchwareUtil;

public class AsdHandler implements View.OnClickListener {

    public final AsdOrigin asdOrigin;
    public final LogicEditorActivity logicEditorActivity;
    public final EditText editText;
    public final Ss ss;

    public AsdHandler(LogicEditorActivity logicEditorActivity, EditText editText, Ss ss, AsdOrigin asdOrigin) {
        this.logicEditorActivity = logicEditorActivity;
        this.editText = editText;
        this.ss = ss;
        this.asdOrigin = asdOrigin;
    }

    @Override
    public void onClick(View v) {
        String content = editText.getText().toString();
        if (content.length() > 0 && content.charAt(0) == '@') {
            content = " " + content;
        }
        logicEditorActivity.a(ss, (Object) content);
        SketchwareUtil.hideKeyboard(editText);
        asdOrigin.dismiss();
    }
}
