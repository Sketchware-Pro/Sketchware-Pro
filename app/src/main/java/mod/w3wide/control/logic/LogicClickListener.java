package mod.w3wide.control.logic;

import static com.besome.sketch.SketchApplication.getContext;
import static mod.SketchwareUtil.getDip;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.jC;
import a.a.a.uq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import mod.w3wide.variable.DialogCustomList;
import mod.w3wide.variable.DialogCustomVariable;

public class LogicClickListener implements View.OnClickListener {

    private final LogicEditorActivity logicEditor;
    private final ProjectFileBean projectFile;
    private final String sc_id;

    public LogicClickListener(LogicEditorActivity logicEditor) {
        this.logicEditor = logicEditor;
        this.sc_id = logicEditor.B;
        this.projectFile = logicEditor.M;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();

        if ("listAddCustom".equals(tag)) {
            addCustomList();
        } else if ("variableAddNew".equals(tag)) {
            addCustomVariable();
        }
    }

    private void addCustomVariable() {
        aB dialog = new aB(logicEditor);
        dialog.a(Resources.drawable.abc_96_color);
        dialog.b("Add a new custom variable");

        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout typeLayout = commonTextInputLayout();
        EditText type = commonEditText("Type, e.g. File");
        typeLayout.addView(type);
        root.addView(typeLayout);

        TextInputLayout nameLayout = commonTextInputLayout();
        EditText name = commonEditText("Name, e.g. file");
        nameLayout.addView(name);
        root.addView(nameLayout);

        TextInputLayout initializerLayout = commonTextInputLayout();
        EditText initializer = commonEditText("Initializer, e.g. new File() (optional)");
        initializerLayout.addView(initializer);
        root.addView(initializerLayout);

        ZB zb = new ZB(getContext(), nameLayout, uq.b, uq.a(), jC.a(sc_id).a(projectFile));

        dialog.a(root);
        dialog.b(Helper.getResString(Resources.string.common_word_add),
                new DialogCustomVariable(logicEditor, typeLayout, nameLayout, initializerLayout, zb, dialog));
        dialog.a(Helper.getResString(Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        typeLayout.requestFocus();
    }

    private void addCustomList() {
        aB dialog = new aB(logicEditor);
        dialog.a(Resources.drawable.add_96_blue);
        dialog.b("Add a new custom List");

        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout typeLayout = commonTextInputLayout();
        EditText type = commonEditText("Type, e.g. ArrayList<Data>");
        typeLayout.addView(type);

        TextInputLayout nameLayout = commonTextInputLayout();
        EditText name = commonEditText("Name, e.g. dataList");
        nameLayout.addView(name);

        root.addView(typeLayout);
        root.addView(nameLayout);

        ZB zb = new ZB(getContext(), nameLayout, uq.b, uq.a(), jC.a(sc_id).a(projectFile));

        dialog.a(root);
        dialog.b(Helper.getResString(Resources.string.common_word_add),
                new DialogCustomList(logicEditor, zb, typeLayout, nameLayout, dialog));
        dialog.a(Helper.getResString(Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        typeLayout.requestFocus();
    }

    private TextInputLayout commonTextInputLayout() {
        TextInputLayout textInputLayout = new TextInputLayout(logicEditor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(
                (int) getDip(8),
                0,
                (int) getDip(8),
                0
        );
        textInputLayout.setLayoutParams(layoutParams);
        return textInputLayout;
    }

    private EditText commonEditText(String hint) {
        EditText editText = new EditText(logicEditor);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        editText.setPadding(
                (int) getDip(4),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        editText.setTextSize(16f);
        editText.setTextColor(0xff000000);
        editText.setHint(hint);
        editText.setHintTextColor(0xff607d8b);
        editText.setInputType(1);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        return editText;
    }
}
