package mod.hilal.saif.asd;

import android.view.View;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import mod.SketchwareUtil;

public class AsdHandler implements View.OnClickListener {

    public final boolean b;
    public final AsdOrigin asdOrigin;
    public final LogicEditorActivity logicEditorActivity;
    public final EditText editText;
    public final Ss ss;

    public AsdHandler(LogicEditorActivity logicEditorActivity, EditText editText, boolean z, Ss ss, AsdOrigin asdOrigin) {
        this.logicEditorActivity = logicEditorActivity;
        this.editText = editText;
        b = z;
        this.ss = ss;
        this.asdOrigin = asdOrigin;
    }

    @Override
    public void onClick(View v) {
        String content = editText.getText().toString();
        if (b) {
            try {
                double editableDouble = Double.parseDouble(content);
                if (Double.isNaN(editableDouble) || Double.isInfinite(editableDouble)) {
                    content = "";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                content = "";
            }
        } else if (content.length() > 0 && content.charAt(0) == '@') {
            content = " " + content;
        }
        logicEditorActivity.a(ss, (Object) content);
        SketchwareUtil.hideKeyboard(editText);
        asdOrigin.dismiss();
    }
}
