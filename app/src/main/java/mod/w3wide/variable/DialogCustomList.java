package mod.w3wide.variable;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.besome.sketch.editor.LogicEditorActivity;
import com.google.android.material.textfield.TextInputLayout;

import a.a.a.ZB;
import a.a.a.aB;

public class DialogCustomList implements OnClickListener {

    public LogicEditorActivity activity;
    public aB dialog;
    public TextInputLayout tilType;
    public TextInputLayout tilName;
    public ZB validator;

    public DialogCustomList(LogicEditorActivity logicEditorActivity, ZB zb, TextInputLayout textInputLayoutType, TextInputLayout textInputLayoutName, aB aB) {
        activity = logicEditorActivity;
        validator = zb;
        tilType = textInputLayoutType;
        tilName = textInputLayoutName;
        dialog = aB;
    }

    @Override
    public void onClick(View v) {
        String variableType = tilType.getEditText().getText().toString();
        String variableName = tilName.getEditText().getText().toString();

        boolean validType = !TextUtils.isEmpty(variableType);
        boolean validName = !TextUtils.isEmpty(variableName);

        if (validType) {
            tilType.setError(null);
        } else {
            if (validName) tilType.requestFocus();
            tilType.setError("Type can't be empty");
        }

        CharSequence nameError = tilName.getError();
        if (nameError == null || "Name can't be empty".contentEquals(nameError)) {
            if (validName) {
                tilName.setError(null);
            } else {
                tilName.requestFocus();
                tilName.setError("Name can't be empty");
            }
        }

        if (validType && validName && validator.b()) {
            activity.a(4, variableType + " " + variableName + " = new ArrayList<>()");
            dialog.dismiss();
        }
    }
}
