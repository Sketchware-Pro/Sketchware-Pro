package mod.agus.jcoderz.variable;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.mB;

public class DialogVariable implements View.OnClickListener {
    public final ZB c;
    public final aB dialog;
    public final LogicEditorActivity e;
    public final EditText editText;
    public final RadioGroup radioGroup;

    public DialogVariable(LogicEditorActivity logicEditorActivity, RadioGroup radioGroup2, EditText editText2, ZB zb, aB aBVar) {
        e = logicEditorActivity;
        radioGroup = radioGroup2;
        editText = editText2;
        c = zb;
        dialog = aBVar;
    }

    public void onClick(View view) {
        int i = 5;
        int checkedRadioButtonId = this.radioGroup.getCheckedRadioButtonId();

        switch (checkedRadioButtonId) {
            case 2131231651:
                i = 4;
                break;

            case 2131231657:
                i = 6;
                break;

            case 2131231654:
                i = 7;
                break;
        }

        e.b(i, editText.getText().toString());
        mB.a(e.getApplicationContext(), editText);
        dialog.dismiss();
    }
}
