package mod.w3wide.variable;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.mB;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

public class DialogCustomVariable implements View.OnClickListener {
    public final aB dialog;
    public final EditText editText;
    public final EditText edittext2;
    public final EditText edittext3;
    public final LogicEditorActivity logicEditor;
    public final ZB validator;

    public DialogCustomVariable(LogicEditorActivity logicEditorActivity, EditText varType, EditText varName, EditText varExtra, ZB zb, aB aBVar) {
        this.dialog = aBVar;
        this.editText = varName;
        this.edittext2 = varType;
        this.edittext3 = varExtra;
        this.logicEditor = logicEditorActivity;
        this.validator = zb;
    }

    public void onClick(View view) {
        String variableName = editText.getText().toString();
        String variableType = edittext2.getText().toString();
        String variableExtra = TextUtils.isEmpty(edittext3.getText().toString()) ? "" : edittext3.getText().toString();
        
        if (!TextUtils.isEmpty(variableName) && validator.b()) {
            logicEditor.b(5, variableType + " " + variableName + " = " + variableExtra );
            mB.a(logicEditor.getApplicationContext(), this.editText);
            this.dialog.dismiss();
        }
    }
}
