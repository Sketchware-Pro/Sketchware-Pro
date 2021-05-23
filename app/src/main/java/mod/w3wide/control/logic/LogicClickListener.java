package mod.w3wide.control.logic;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.jC;
import a.a.a.nr;
import a.a.a.uq;
import a.a.a.xB;

import android.graphics.Color;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.editor.LogicEditorActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Iterator;

import mod.SketchwareUtil;
import mod.w3wide.variable.DialogCustomList;
import mod.w3wide.variable.DialogCustomVariable;

public class LogicClickListener implements View.OnClickListener {
	
	private String javaName = "";
	private LogicEditorActivity logicEditor;
	private ProjectFileBean projectFile;
	private String sc_id = "";
	private String xmlName = "";
	
	public LogicClickListener(LogicEditorActivity logicEditor) {
		this.logicEditor = logicEditor;
		this.sc_id = logicEditor.B;
		this.projectFile = logicEditor.M;
		this.javaName = this.projectFile.getJavaName();
		this.xmlName = this.projectFile.getXmlName();
	}
	
    public void onClick(View view) {
        Object tag = view.getTag();
        if (view.getTag() != null) {
          if (tag.equals("listAddCustom")) {
              addCustomList();
          } else if (tag.equals("variableAddNew")) {
              addCustomVariable();
          }
       }
    }
    
    public void addCustomVariable() {
        aB ab = new aB(logicEditor);
        ab.b("Create Your Own Object");
        ab.a(2131165191);
        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout first = commonTextInputLayout();
        EditText editFirst = commonEditText("Type: File");
        first.addView(editFirst);

        TextInputLayout second = commonTextInputLayout();
        EditText editSecond = commonEditText("name: mFile");
        second.addView(editSecond);
        
        TextInputLayout third = commonTextInputLayout();
        EditText editThird = commonEditText("initialize: new File() (Optional)");
        third.addView(editThird);

        root.addView(first);
        root.addView(second);
        root.addView(third);
        
        ZB zb = new ZB(logicEditor.getApplicationContext(), second, uq.b, uq.a(), jC.a(sc_id).a(projectFile));
        
        ab.a(root);
        ab.b(xB.b().a(logicEditor.getApplicationContext(), 2131624970), new DialogCustomVariable(logicEditor, editFirst, editSecond, editThird, zb, ab));
        ab.a(xB.b().a(logicEditor.getApplicationContext(), 2131624974), new nr(logicEditor, editSecond, ab));
        ab.show();
    }
    
    public void addCustomList() {
        aB ab = new aB(logicEditor);
        ab.b("Add your Custom List");
        ab.a(2131165298);
        LinearLayout root = new LinearLayout(logicEditor);
        root.setOrientation(LinearLayout.VERTICAL);

        TextInputLayout first = commonTextInputLayout();
        EditText editFirst = commonEditText("Type: ArrayList<Data>");
        first.addView(editFirst);

        TextInputLayout second = commonTextInputLayout();
        EditText editSecond = commonEditText("name: mArrayList");
        second.addView(editSecond);

        root.addView(first);
        root.addView(second);
        
        ZB zb = new ZB(logicEditor.getApplicationContext(), second, uq.b, uq.a(), jC.a(sc_id).a(projectFile));
        
        ab.a(root);
        ab.b(xB.b().a(logicEditor.getApplicationContext(), 2131624970), new DialogCustomList(logicEditor, zb, editFirst, editSecond, ab));
        ab.a(xB.b().a(logicEditor.getApplicationContext(), 2131624974), new nr(logicEditor, editSecond, ab));
        ab.show();
    }

	public TextInputLayout commonTextInputLayout() {
		TextInputLayout textInputLayout = new TextInputLayout(logicEditor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(getDip(8), 0, getDip(8), 0);
        textInputLayout.setLayoutParams(layoutParams);
        return textInputLayout;
	}
	
	public TextView commonTextView(String text) {
		TextView textView = new TextView(logicEditor);
		textView.setText(text);
		textView.setPadding((int)getDip(2), (int)getDip(4), (int)getDip(4), (int)getDip(4));
		textView.setTextSize((float) 14);
		return textView;
	}
	
	public EditText commonEditText(String hint) {
		EditText editText = new EditText(logicEditor);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        editText.setPadding((int)getDip(4), (int)getDip(8), (int)getDip(8), (int)getDip(8));
        editText.setTextSize((float) 16);
        editText.setTextColor(-16777216);
        editText.setHint(hint);
        editText.setHintTextColor(Color.parseColor("#607D8B"));
        editText.setInputType(1);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        return editText;
	}
	
	private RadioGroup commonRadioGroup(ArrayList<Pair<Integer, String>> variables, String defaultVariable) {
        RadioGroup radioGroup = new RadioGroup(logicEditor);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(getDip(8), 0, getDip(8), 0);
        radioGroup.setLayoutParams(param);
        for (Pair<Integer, String> variable : variables) {
            RadioButton radioButton = new RadioButton(logicEditor);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDip(40), 1.0f);
            layoutParams.setMargins(0, 0, 0, 0);
            radioButton.setLayoutParams(layoutParams);
            radioButton.setId(variable.first);
            radioButton.setText(variable.second);
            radioButton.setTextColor(-16777216);
            radioButton.setTextSize((float) 14);
            if (defaultVariable.equals(variable.second)) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        return radioGroup;
    }
    
    private RadioButton commonRadioButton(int id, String title) {
        RadioButton radioButton = new RadioButton(logicEditor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getDip(40), 1.0f);
        layoutParams.setMargins(0, 0, 0, 0);
        radioButton.setLayoutParams(layoutParams);
        radioButton.setId(id);
        radioButton.setText(title);
        radioButton.setTextColor(-16777216);
        radioButton.setTextSize((float) 14);
        return radioButton;
    }
	private int getDip(int i) {
        return (int) SketchwareUtil.getDip(logicEditor, i);
    }
}
