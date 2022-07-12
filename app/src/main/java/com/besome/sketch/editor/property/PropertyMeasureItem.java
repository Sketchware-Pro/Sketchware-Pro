package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyMeasureItem extends RelativeLayout implements View.OnClickListener {

    private String key = "";
    private int measureValue = -1;
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;
    private boolean isMatchParent = true;
    private boolean isWrapContent = true;
    private boolean isCustomValue = true;
    private int imgLeftIconDrawableResId;

    public PropertyMeasureItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void setIcon(ImageView imageView) {
        if (key.equals("property_layout_width")) {
            imgLeftIconDrawableResId = R.drawable.width_96;
        } else if (key.equals("property_layout_height")) {
            imgLeftIconDrawableResId = R.drawable.height_96;
        }
        imageView.setImageResource(imgLeftIconDrawableResId);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            if (this.propertyMenuItem.getVisibility() == VISIBLE) {
                setIcon(findViewById(R.id.img_icon));
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            setIcon(imgLeftIcon);
        }
    }

    public int getValue() {
        return measureValue;
    }

    public void setValue(int value) {
        measureValue = value;
        if (!isWrapContent && value == LayoutParams.WRAP_CONTENT) {
            tvValue.setText(sq.a(key, LayoutParams.MATCH_PARENT));
        } else if (isCustomValue || value < 0) {
            tvValue.setText(sq.a(key, value));
        } else {
            tvValue.setText(sq.a(key, LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            showDialog();
        }
    }

    public void setItemEnabled(int itemEnabled) {
        isMatchParent = (itemEnabled & 1) == 1;
        isWrapContent = (itemEnabled & 2) == 2;
        isCustomValue = (itemEnabled & 4) == 4;
    }

    public void setOnPropertyValueChangeListener(Kw onPropertyValueChangeListener) {
        valueChangeListener = onPropertyValueChangeListener;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(GONE);
            propertyMenuItem.setVisibility(VISIBLE);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
        }
    }

    private void initialize(Context context, boolean z) {
        wB.a(context, this, R.layout.property_selector_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    private void showDialog() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(imgLeftIconDrawableResId);

        View view = wB.a(getContext(), R.layout.property_popup_measurement);
        EditText ed_input = view.findViewById(R.id.ed_input);
        RadioGroup rg_width_height = view.findViewById(R.id.rg_width_height);
        TB tb = new TB(getContext(), view.findViewById(R.id.ti_input), 0, 999);

        RadioButton rb_matchparent = view.findViewById(R.id.rb_matchparent);
        View tv_matchparent = view.findViewById(R.id.tv_matchparent);
        RadioButton rb_wrapcontent = view.findViewById(R.id.rb_wrapcontent);
        TextView tv_wrapcontent = view.findViewById(R.id.tv_wrapcontent);
        RadioButton rb_directinput = view.findViewById(R.id.rb_directinput);
        View direct_input = view.findViewById(R.id.direct_input);
        TextView tv_input_dp = view.findViewById(R.id.tv_input_dp);

        rg_width_height.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_directinput) {
                ed_input.setEnabled(true);
                tb.a(ed_input.getText().toString());
            } else {
                ed_input.setEnabled(false);
            }
        });
        ed_input.setEnabled(false);
        rg_width_height.clearCheck();
        if (measureValue >= 0) {
            if (isCustomValue) {
                rg_width_height.check(R.id.rb_directinput);
                ed_input.setEnabled(true);
                tb.a(String.valueOf(measureValue));
            } else {
                rg_width_height.check(R.id.rb_wrapcontent);
            }
        } else if (measureValue == LayoutParams.MATCH_PARENT) {
            rg_width_height.check(R.id.rb_matchparent);
        } else if (isWrapContent) {
            rg_width_height.check(R.id.rb_wrapcontent);
        } else {
            rg_width_height.check(R.id.rb_matchparent);
        }
        tv_matchparent.setOnClickListener(v -> rb_matchparent.setChecked(true));
        if (isWrapContent) {
            rb_matchparent.setEnabled(true);
            tv_wrapcontent.setClickable(true);
            tv_wrapcontent.setTextColor(0xff757575);
            tv_wrapcontent.setOnClickListener(v -> rb_wrapcontent.setChecked(true));
        } else {
            rb_matchparent.setEnabled(false);
            tv_wrapcontent.setClickable(false);
            tv_wrapcontent.setTextColor(0xffdddddd);
        }
        if (isCustomValue) {
            rb_directinput.setEnabled(true);
            direct_input.setClickable(true);
            tv_input_dp.setTextColor(0xff757575);
            direct_input.setOnClickListener(v -> rb_directinput.setChecked(true));
        } else {
            rb_directinput.setEnabled(false);
            direct_input.setClickable(false);
            tv_input_dp.setTextColor(0xffdddddd);
        }
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_select), v -> {
            int checkedRadioButtonId = rg_width_height.getCheckedRadioButtonId();

            if (checkedRadioButtonId == R.id.rb_matchparent) {
                setValue(LayoutParams.MATCH_PARENT);
            } else if (checkedRadioButtonId == R.id.rb_wrapcontent) {
                setValue(LayoutParams.WRAP_CONTENT);
            } else if (tb.b()) {
                setValue(Integer.parseInt(ed_input.getText().toString()));
            } else {
                return;
            }
            if (valueChangeListener != null) {
                valueChangeListener.a(key, measureValue);
            }
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
