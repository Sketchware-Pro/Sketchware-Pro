package mod.w3wide.control.logic;

import static mod.SketchwareUtil.getDip;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.util.ArrayList;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.jC;
import a.a.a.uq;
import a.a.a.xB;
import mod.SketchwareUtil;
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
        if (v.getTag() != null) {
            if (tag.equals("listAddCustom")) {
                addCustomList();
            } else if (tag.equals("variableAddNew")) {
                addCustomVariable();
            }
        }
    }

    public void addCustomVariable() {
        aB ab = new aB(logicEditor);
        ab.b("Add a new custom variable");
        ab.a(Resources.drawable.abc_96_color);

        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout first = commonTextInputLayout();
        EditText editFirst = commonEditText("Type, e.g. File");
        first.addView(editFirst);
        root.addView(first);

        TextInputLayout second = commonTextInputLayout();
        EditText editSecond = commonEditText("Name, e.g. file");
        second.addView(editSecond);
        root.addView(second);

        TextInputLayout third = commonTextInputLayout();
        EditText editThird = commonEditText("Initializer, e.g. new File() (optional)");
        third.addView(editThird);
        root.addView(third);

        ZB zb = new ZB(logicEditor.getApplicationContext(), second, uq.b, uq.a(), jC.a(sc_id).a(projectFile));

        ab.a(root);
        ab.b(xB.b().a(logicEditor.getApplicationContext(), Resources.string.common_word_add),
                new DialogCustomVariable(logicEditor, first, second, third, zb, ab));
        ab.a(xB.b().a(logicEditor.getApplicationContext(), Resources.string.common_word_cancel),
                new NewFieldDialogListener(editSecond, ab));
        ab.show();
    }

    public void addCustomList() {
        aB ab = new aB(logicEditor);
        ab.b("Add a new custom List");
        ab.a(Resources.drawable.add_96_blue);

        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout first = commonTextInputLayout();
        EditText editFirst = commonEditText("Type, e.g. ArrayList<Data>");
        first.addView(editFirst);

        TextInputLayout second = commonTextInputLayout();
        EditText editSecond = commonEditText("Name, e.g. dataList");
        second.addView(editSecond);

        root.addView(first);
        root.addView(second);

        ZB zb = new ZB(logicEditor.getApplicationContext(), second, uq.b, uq.a(), jC.a(sc_id).a(projectFile));

        ab.a(root);
        ab.b(xB.b().a(logicEditor.getApplicationContext(), Resources.string.common_word_add),
                new DialogCustomList(logicEditor, zb, first, second, ab));
        ab.a(xB.b().a(logicEditor.getApplicationContext(), Resources.string.common_word_cancel),
                new NewFieldDialogListener(editSecond, ab));
        ab.show();
    }

    public TextInputLayout commonTextInputLayout() {
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

    public TextView commonTextView(String text) {
        TextView textView = new TextView(logicEditor);
        textView.setText(text);
        textView.setPadding(
                (int) getDip(2),
                (int) getDip(4),
                (int) getDip(4),
                (int) getDip(4)
        );
        textView.setTextSize(14f);
        return textView;
    }

    public EditText commonEditText(String hint) {
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

    private RadioGroup commonRadioGroup(ArrayList<Pair<Integer, String>> variables, String defaultVariable) {
        RadioGroup radioGroup = new RadioGroup(logicEditor);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(
                (int) getDip(8),
                0,
                (int) getDip(8),
                0
        );
        radioGroup.setLayoutParams(param);
        for (Pair<Integer, String> variable : variables) {
            RadioButton radioButton = new RadioButton(logicEditor);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int) getDip(40),
                    1.0f);
            layoutParams.setMargins(
                    0,
                    0,
                    0,
                    0
            );
            radioButton.setLayoutParams(layoutParams);
            radioButton.setId(variable.first);
            radioButton.setText(variable.second);
            radioButton.setTextColor(0xff000000);
            radioButton.setTextSize(14f);
            if (defaultVariable.equals(variable.second)) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        return radioGroup;
    }

    private RadioButton commonRadioButton(int id, String title) {
        RadioButton radioButton = new RadioButton(logicEditor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getDip(40),
                1.0f);
        layoutParams.setMargins(
                0,
                0,
                0,
                0
        );
        radioButton.setLayoutParams(layoutParams);
        radioButton.setId(id);
        radioButton.setText(title);
        radioButton.setTextColor(0xff000000);
        radioButton.setTextSize(14f);
        return radioButton;
    }

    private static class NewFieldDialogListener implements View.OnClickListener {

        private final EditText editText;
        private final aB dialog;

        NewFieldDialogListener(EditText editText, aB dialog) {
            this.editText = editText;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            SketchwareUtil.hideKeyboard(editText);
            dialog.dismiss();
        }
    }
}
