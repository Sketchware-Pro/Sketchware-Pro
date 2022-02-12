package mod.w3wide.dialog;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import mod.hey.studios.util.Helper;

@SuppressLint("ResourceType")
public class SketchDialog extends Dialog {

    private final Context mContext;
    //initialize view by id
    public LinearLayout sdialog_root;
    public LinearLayout mDialogHeader;
    public ImageView dialog_img;
    public ImageView dialog_img_right;
    public TextView dialog_title;
    public TextView dialog_msg;
    public FrameLayout custom_view;
    public LinearLayout layout_button;
    public TextView dialog_btn_no;
    public TextView dialog_btn_yes;
    public TextView dialog_btn_neutral;
    //Custom View
    public View mCustomView;
    //Dialog Icon
    public int mIcon = -1;
    public int mRightIcon = -1;
    //Defaults Strings
    public String mTitle = "";
    public String mMessage = "";
    public String mPostiveStr = "Ok";
    public String mNegativeStr = "Cancel";
    public String mNeutralStr = "";
    //View.OnClickListener
    private View.OnClickListener mPositiveClick;
    private View.OnClickListener mNegativeClick;
    private View.OnClickListener mNeutralClick;
    private View.OnClickListener mRightIconClick;

    public SketchDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(Resources.layout.dialog);
        initialize(savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle savedInstanceState) {
        sdialog_root = findViewById(Resources.id.sdialog_root);
        dialog_img = findViewById(Resources.id.dialog_img);
        dialog_title = findViewById(Resources.id.dialog_title);
        dialog_msg = findViewById(Resources.id.dialog_msg);
        custom_view = findViewById(Resources.id.custom_view);
        layout_button = findViewById(Resources.id.layout_button);
        dialog_btn_no = findViewById(Resources.id.dialog_btn_no);
        dialog_btn_yes = findViewById(Resources.id.dialog_btn_yes);
        dialog_btn_neutral = findViewById(Resources.id.common_dialog_default_button);

        dialog_img_right = new ImageView(mContext);
        dialog_img_right.setLayoutParams(dialog_img.getLayoutParams());
        dialog_img_right.setScaleType(dialog_img.getScaleType());

        mDialogHeader = (LinearLayout) dialog_img.getParent();
        mDialogHeader.addView(dialog_img_right, 2);
    }

    private void initializeLogic() {
        //OnClickListeners
        dialog_btn_no.setOnClickListener(mNegativeClick);
        dialog_btn_yes.setOnClickListener(mPositiveClick);
        dialog_btn_neutral.setOnClickListener(mNeutralClick);
        dialog_img_right.setOnClickListener(mRightIconClick);
        //Initialization
        if (mIcon != -1) {
            dialog_img.setImageResource(mIcon);
        } else {
            dialog_img.setVisibility(View.GONE);
        }
        if (mRightIcon != -1) {
            dialog_img_right.setImageResource(mRightIcon);
        } else {
            dialog_img_right.setVisibility(View.GONE);
        }
        dialog_title.setText(mTitle);
        dialog_title.setVisibility(mTitle.length() > 0 ? View.VISIBLE : View.GONE);

        dialog_msg.setText(mMessage);
        dialog_msg.setVisibility(mMessage.length() == 0 ? View.GONE : View.VISIBLE);

        dialog_btn_no.setText(mNegativeStr);
        dialog_btn_yes.setText(mPostiveStr);

        //applyRippleEffect(dialog_btn_no, 0xffffffff);
        //applyRippleEffect(dialog_btn_yes, 0xffffffff);

        if (mCustomView != null) {
            custom_view.addView(mCustomView);
        } else {
            custom_view.setVisibility(View.GONE);
        }
    }

    public void setIcon(int resDrawable) {
        mIcon = resDrawable;
    }

    public void setRightIcon(int resDrawable, View.OnClickListener listener) {
        mRightIcon = resDrawable;
        mRightIconClick = listener;
    }

    public void setView(View view) {
        mCustomView = view;
    }

    public void setTitle(String str) {
        mTitle = str;
    }

    public void setMessage(String str) {
        mMessage = str;
    }

    public void setMessage(String str, int color) {
        mMessage = str;
        dialog_msg.setTextColor(color);
    }

    public void setPositiveButton(String str, View.OnClickListener listener) {
        if (str.length() > 0) {
            mPostiveStr = str;
        }
        mPositiveClick = listener == null ? Helper.getDialogDismissListener(this) : listener;
    }

    public void setNeutralButton(String str, View.OnClickListener listener) {
        if (!isEmpty(str)) {
            mNeutralStr = str;
        }
        mNeutralClick = listener == null ? Helper.getDialogDismissListener(this) : listener;
    }

    public void setNegativeButton(String str, View.OnClickListener listener) {
        if (str.length() > 0) {
            mNegativeStr = str;
        }
        mNegativeClick = listener == null ? Helper.getDialogDismissListener(this) : listener;
    }

    private void applyRippleEffect(final View view, final int color) {
        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[]{}
        }, new int[]{
                color
        });
        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, null);
        if (!view.isClickable()) {
            view.setClickable(true);
        }
        view.setBackground(rippleDrawable);
    }
}