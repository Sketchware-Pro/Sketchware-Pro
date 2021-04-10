package mod.w3wide.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Space;
import android.widget.TextView;

public class SketchDialog extends Dialog {
	private Context mContext;
	//initialize view by id
	public LinearLayout sdialog_root;
	public ImageView dialog_img;
	public TextView dialog_title;
	public TextView dialog_msg;
	public FrameLayout custom_view;
	public LinearLayout layout_button;
	public TextView dialog_btn_no;
	public TextView dialog_btn_yes;
	//View.OnClickListener
	private View.OnClickListener mPositiveClick;// = ((View.OnClickListener) null);
	private View.OnClickListener mNegativeClick;// = ((View.OnClickListener) null);
	private View.OnClickListener mNeutralClick;// = ((View.OnClickListener) null);
	//Custom View
	public View mCustomView;
	//Dialog Icon
	public int mIcon = -1;
	//Defaults Strings
	public String mTitle = "";
	public String mMessage = "";
	public String mPostiveStr = "Yes";
	public String mNegativeStr = "No";
	public String mNeutralStr = "";
   
	public SketchDialog(Context mContext) {
		super(mContext);
		this.mContext = mContext;
	}
	
	public void onCreate(Bundle mBundle) {
        super.onCreate(mBundle);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(0x7f0b0052);
        initialize(mBundle);
        initializeLogic();
	}
	
	private void initialize(Bundle mBundle) {
		sdialog_root = (LinearLayout) findViewById(0x7f0803d0);
		dialog_img = (ImageView) findViewById(0x7f0800fe);
		dialog_title = (TextView) findViewById(0x7f080100);
		dialog_msg = (TextView) findViewById(0x7f0800ff);
		custom_view = (FrameLayout) findViewById(0x7f0800dd);
		layout_button = (LinearLayout) findViewById(0x7f080258);
		dialog_btn_no = (TextView) findViewById(0x7f0800fc);
		dialog_btn_yes  = (TextView) findViewById(0x7f0800fd);
		
	}
	
	public void initializeLogic() {
		//OnClickListeners
		dialog_btn_no.setOnClickListener(mNegativeClick);
		dialog_btn_yes.setOnClickListener(mPositiveClick);
		//Initialization
		if (mIcon != -1) {
			dialog_img.setVisibility(View.VISIBLE);
			dialog_img.setImageResource(mIcon);
		} else {
			dialog_img.setVisibility(View.GONE);
		}
		dialog_title.setText(mTitle);
		dialog_btn_no.setText(mNegativeStr);
		_RippleEffect(dialog_btn_no, "#ffffff");
		dialog_btn_yes.setText(mPostiveStr);
		_RippleEffect(dialog_btn_yes, "#ffffff");
		if (mNeutralStr.length() != 0) {
			layout_button.addView(NeutralText(mNeutralStr), 0);
			layout_button.addView(NeutralSpace(), 1);
		}
		if (mMessage.length() == 0) {
			dialog_msg.setVisibility(View.GONE);
		} else {
			dialog_msg.setVisibility(View.VISIBLE);
			dialog_msg.setText(mMessage);
		}
		if (mCustomView != null) {
			custom_view.setVisibility(View.VISIBLE);
			custom_view.addView(mCustomView);
		} else {
			custom_view.setVisibility(View.GONE);
		}
	}
	
	public TextView NeutralText(String mStr) {
		TextView mNeutral = new TextView(mContext);
		mNeutral.setText(mStr);
		mNeutral.setTextColor(-1);
		mNeutral.setTextSize((float)14);
		mNeutral.setPadding(8, 0, 8, 0);
		mNeutral.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
		mNeutral.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		mNeutral.setOnClickListener(mNeutralClick);
		_RippleEffect(mNeutral, "#ffffff");
		return mNeutral;
	}
	
	public Space NeutralSpace() {
		Space mSpace = new Space(mContext);
		android.widget.LinearLayout.LayoutParams mParam = new android.widget.LinearLayout.LayoutParams(0, 0, 1.0F);
		mSpace.setLayoutParams(mParam);
		return mSpace;
	}
	
	public void setIcon(int mIcon) {
		this.mIcon = mIcon;
	}
	
	public void setView(View mView) {
		mCustomView = mView;
	}
	
	public void setTitle(String mStr) {
		mTitle = mStr;
	}
	
	public void setMessage(String mStr) {
		mMessage = mStr;
	}
	
	public void setPositiveButton(String mStr, View.OnClickListener mClickListener) {
		mPostiveStr = mStr;
		mPositiveClick = mClickListener;
	}
	
	public void setNeutralButton(String mStr, View.OnClickListener mClickListener) {
		mNeutralStr = mStr;
		mNeutralClick = mClickListener;
	}
	
	public void setNegativeButton(String mStr, View.OnClickListener mClickListener) {
		mNegativeStr = mStr;
		mNegativeClick = mClickListener;
	}
	
	public void _RippleEffect (final View _view, final String _c) {
		ColorStateList clr = new ColorStateList(new int[][] {
			    new int[] {}
		}, new int[] {
			    Color.parseColor(_c)
		});
		RippleDrawable ripdr = new RippleDrawable(clr, null, null);
		if (!_view.isClickable()) {
			_view.setClickable(true);
		}
		_view.setBackground(ripdr);
	}
	
	public void show() {
		super.show();
	}
	
}
