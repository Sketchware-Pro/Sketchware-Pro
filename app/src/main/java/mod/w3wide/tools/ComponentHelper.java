package mod.w3wide.tools;

import android.app.Activity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.DialogFragment;

public class ComponentHelper implements TextWatcher {

	private final EditText[] mEditArray;
	private final EditText mTypeClass;
	
	//public ComponentHelper(EditText mEdits[]) {
		//this.mEditArray = mEdits;
		//this.mTypeClass = null;
	//}
	
	public ComponentHelper(EditText mEdits[], EditText typeClass) {
		this.mEditArray = mEdits;
		this.mTypeClass = typeClass;
	}
	
	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		final String charSeq = arg0.toString();
		for (EditText mEdits : mEditArray) {
			mEdits.setText(charSeq);
			mTypeClass.setText("Component." + charSeq);
		}
		//for(int i= 0; i < editText.length; i++) {
			//editText[i].setText(charSeq);
		//}
		
	}
	
	@Override
	public void afterTextChanged(Editable editable) {
		/*String text = editable.toString();
		for (EditText mEdits : mEditArray) {
			mEdits.setText(text);
			mTypeClass.setText("Component." + text);
		}*/
	}
	
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
	
}
