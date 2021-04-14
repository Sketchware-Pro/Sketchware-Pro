package mod.agus.jcoderz.variable;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.mB;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.besome.sketch.editor.LogicEditorActivity;

public class DialogImport implements View.OnClickListener {
    public final ZB c;
    public final aB dialog;
    public final LogicEditorActivity e;
    public final EditText editText;
    public final RadioGroup radioGroup;

    public DialogImport(LogicEditorActivity logicEditorActivity, RadioGroup radioGroup2, EditText editText2, ZB zb, aB aBVar) {
        this.e = logicEditorActivity;
        this.radioGroup = radioGroup2;
        this.editText = editText2;
        this.c = zb;
        this.dialog = aBVar;
    }

    public void onClick(View view) {
        int i = 9;
        int checkedRadioButtonId = this.radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == 2131231651) {
            i = 4;
        } else if (checkedRadioButtonId != 2131231653) {
            if (checkedRadioButtonId == 2131231657) {
                i = 6;
            } else if (checkedRadioButtonId == 2131231654) {
                i = 7;
            }
        }
        this.e.b(i, this.editText.getText().toString());
        mB.a(this.e.getApplicationContext(), this.editText);
        this.dialog.dismiss();
    }
}
