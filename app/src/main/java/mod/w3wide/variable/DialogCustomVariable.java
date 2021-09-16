package mod.w3wide.variable;

import android.text.TextUtils;
import android.view.View;

import com.besome.sketch.editor.LogicEditorActivity;
import com.google.android.material.textfield.TextInputLayout;

import a.a.a.ZB;
import a.a.a.aB;

public class DialogCustomVariable implements View.OnClickListener {

    public final aB dialog;
    public final LogicEditorActivity logicEditor;
    public final TextInputLayout tilType;
    public final TextInputLayout tilName;
    public final TextInputLayout tilInitializer;
    public final ZB validator;

    public DialogCustomVariable(LogicEditorActivity logicEditorActivity, TextInputLayout varType, TextInputLayout varName, TextInputLayout varExtra, ZB zb, aB aB) {
        dialog = aB;
        tilName = varName;
        tilType = varType;
        tilInitializer = varExtra;
        logicEditor = logicEditorActivity;
        validator = zb;
    }

    @Override
    public void onClick(View v) {
        String variableType = tilType.getEditText().getText().toString();
        String variableName = tilName.getEditText().getText().toString();
        String variableInitializer = tilInitializer.getEditText().getText().toString();

        boolean validType = !TextUtils.isEmpty(variableType);
        boolean validName = !TextUtils.isEmpty(variableName);
        boolean getsInitialized = !TextUtils.isEmpty(variableInitializer);

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

        if (validName && validType && validator.b()) {
            String toAdd = variableType + " " + variableName;
            if (getsInitialized) {
                toAdd += " = " + variableInitializer;
            }
            logicEditor.b(5, toAdd);
            dialog.dismiss();
        }
    }
}
