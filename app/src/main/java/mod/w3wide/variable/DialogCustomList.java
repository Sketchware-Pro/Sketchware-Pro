package mod.w3wide.variable;

import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.mB;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.besome.sketch.editor.LogicEditorActivity;

public class DialogCustomList implements OnClickListener {
	public aB mDialog;
	public EditText editFirst;
	public EditText editSecond;
	public LogicEditorActivity logic;
	public ZB validator;
	
	public DialogCustomList(LogicEditorActivity logicEditorActivity, ZB zb, EditText editText, EditText editText2, aB aBVar) {
		logic = logicEditorActivity;
		validator = zb;
		editFirst = editText;
		editSecond = editText2;
		mDialog = aBVar;
	}
	
	public void onClick(View view) {
		String firstStr = editFirst.getText().toString();
		String secondStr = editSecond.getText().toString();
		
		if (validator.b() && !TextUtils.isEmpty(firstStr) && !TextUtils.isEmpty(secondStr)) {
		    logic.a(4, firstStr +" "+ secondStr + " = new ArrayList<>()");
		    mB.a(logic.getApplicationContext(), editSecond);
		    mDialog.dismiss();
		}
	}
}
