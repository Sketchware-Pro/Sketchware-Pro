package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertyGravityItem extends RelativeLayout implements View.OnClickListener {

    public String key = "";
    public int gravityValue = -1;
    public TextView tvName;
    public TextView tvValue;
    public ImageView imgLeftIcon;
    public int icon;
    public View propertyItem;
    public View propertyMenuItem;
    public Kw valueChangeListener;

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
            tvName.setText(xB.b().a(getResources(), identifier));
            icon = Resources.drawable.gravity_96;
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
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
                    a();
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
        wB.a(context, this, Resources.layout.property_selector_item);
        tvName = findViewById(Resources.id.tv_name);
        tvValue = findViewById(Resources.id.tv_value);
        imgLeftIcon = findViewById(Resources.id.img_left_icon);
        propertyItem = findViewById(Resources.id.property_item);
        propertyMenuItem = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public final void a() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), Resources.layout.property_popup_selector_gravity);
        CheckBox chk_left = view.findViewById(Resources.id.chk_left);
        CheckBox chk_right = view.findViewById(Resources.id.chk_right);
        CheckBox chk_hcenter = view.findViewById(Resources.id.chk_hcenter);
        CheckBox chk_top = view.findViewById(Resources.id.chk_top);
        CheckBox chk_bottom = view.findViewById(Resources.id.chk_bottom);
        CheckBox chk_vcenter = view.findViewById(Resources.id.chk_vcenter);
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
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), v -> {
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
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
