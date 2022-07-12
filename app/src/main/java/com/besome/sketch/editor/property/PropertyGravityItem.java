package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyGravityItem extends RelativeLayout implements View.OnClickListener {

    private String key = "";
    private int gravityValue = -1;
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private int icon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;

    public PropertyGravityItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String str) {
        key = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            icon = R.drawable.gravity_96;
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            imgLeftIcon.setImageResource(icon);
        }
    }

    public int getValue() {
        return gravityValue;
    }

    public void setValue(int value) {
        gravityValue = value;
        tvValue.setText(sq.a(value));
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            switch (key) {
                case "property_gravity":
                case "property_layout_gravity":
                    showDialog();
                    break;
            }
        }
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
        dialog.a(icon);
        View view = wB.a(getContext(), R.layout.property_popup_selector_gravity);
        CheckBox chk_left = view.findViewById(R.id.chk_left);
        CheckBox chk_right = view.findViewById(R.id.chk_right);
        CheckBox chk_hcenter = view.findViewById(R.id.chk_hcenter);
        CheckBox chk_top = view.findViewById(R.id.chk_top);
        CheckBox chk_bottom = view.findViewById(R.id.chk_bottom);
        CheckBox chk_vcenter = view.findViewById(R.id.chk_vcenter);
        int verticalGravity = gravityValue & Gravity.FILL_VERTICAL;
        int horizontalGravity = gravityValue & Gravity.FILL_HORIZONTAL;
        if (horizontalGravity == Gravity.CENTER_HORIZONTAL) {
            chk_hcenter.setChecked(true);
        } else {
            if ((horizontalGravity & Gravity.LEFT) == Gravity.LEFT) {
                chk_left.setChecked(true);
            }
            if ((horizontalGravity & Gravity.RIGHT) == Gravity.RIGHT) {
                chk_right.setChecked(true);
            }
        }
        if (verticalGravity == Gravity.CENTER_VERTICAL) {
            chk_vcenter.setChecked(true);
        } else {
            if ((verticalGravity & Gravity.TOP) == Gravity.TOP) {
                chk_top.setChecked(true);
            }
            if ((verticalGravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                chk_bottom.setChecked(true);
            }
        }
        dialog.a(view);
        dialog.b(Helper.getResString(R.string.common_word_select), v -> {
            int value = chk_left.isChecked() ? Gravity.LEFT : Gravity.NO_GRAVITY;
            if (chk_right.isChecked()) {
                value |= Gravity.RIGHT;
            }
            if (chk_hcenter.isChecked()) {
                value |= Gravity.CENTER_HORIZONTAL;
            }
            if (chk_top.isChecked()) {
                value |= Gravity.TOP;
            }
            if (chk_bottom.isChecked()) {
                value |= Gravity.BOTTOM;
            }
            if (chk_vcenter.isChecked()) {
                value |= Gravity.CENTER_VERTICAL;
            }
            setValue(value);
            if (valueChangeListener != null) {
                valueChangeListener.a(key, gravityValue);
            }
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
