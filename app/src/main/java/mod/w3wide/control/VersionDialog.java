package mod.w3wide.control;

import a.a.a.mB;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;
import mod.hey.studios.util.Helper;
import mod.SketchwareUtil;
import mod.w3wide.dialog.SketchDialog;
import mod.w3wide.validator.VersionValidator;

public class VersionDialog {
  
  private MyProjectSettingActivity mpsAct;
  private VersionValidator mFilter;
  
  public VersionDialog(MyProjectSettingActivity mpsAct) {
    this.mpsAct = mpsAct;
  }
  
  public void show() {
    final SketchDialog mSketch = new SketchDialog(this.mpsAct);
    mSketch.setTitle("Advanced Version Control");
    mSketch.setIcon(Resources.drawable.numbers_48);
    LayoutInflater mViewInflate = (LayoutInflater) this.mpsAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    final View mView = (View) mViewInflate.inflate(Resources.layout.property_advanced_version_control, null);
    
    final TextInputLayout ver_code = (TextInputLayout) mView.findViewById(Resources.id.ver_code);
    final EditText version_code = (EditText) mView.findViewById(Resources.id.version_code);
    
    final TextInputLayout ver_name = (TextInputLayout) mView.findViewById(Resources.id.ver_name);
    final EditText version_name1 = (EditText) mView.findViewById(Resources.id.version_name1);
    
    final TextInputLayout extra_input_layout = (TextInputLayout) mView.findViewById(Resources.id.extra_input_layout);
    mFilter = new VersionValidator(this.mpsAct, extra_input_layout);
    final EditText version_name2 = (EditText) mView.findViewById(Resources.id.version_name2);
    
    version_code.setText(String.valueOf(Integer.parseInt(mpsAct.I.getText().toString())));
    version_name1.setText(mpsAct.J.getText().toString().split("-")[0]);
    if (mpsAct.J.getText().toString().split("-").length > 1) {
      version_name2.setText(mpsAct.J.getText().toString().split("-")[1]);
    }
    mSketch.setView(mView);
    final String versionCode = version_code.getText().toString();
    final String versionName1 = version_name1.getText().toString();
    final String versionName2 = version_name2.getText().toString();
    mSketch.setPositiveButton("Save", new View.OnClickListener() {
      @Override
      public void onClick(View view) {
      if (!mB.a() && !TextUtils.isEmpty(versionCode) && !TextUtils.isEmpty(versionName1)) {
        mpsAct.I.setText(String.valueOf((long)(Integer.parseInt(versionCode))));
        mpsAct.J.setText(versionName2.length() > 0 ? versionName1.concat("-".concat(versionName2)) : versionName1);
        SketchwareUtil.showMessage(mpsAct.getApplicationContext(), "Saved");
        mSketch.dismiss();
      } else {
        SketchwareUtil.showMessage(mpsAct.getApplicationContext(), "fill all the blanks");
      }
      }
    });
    mSketch.setNegativeButton("Cancel", Helper.getDialogDismissListener(mSketch));
    mSketch.show();
  }
}
