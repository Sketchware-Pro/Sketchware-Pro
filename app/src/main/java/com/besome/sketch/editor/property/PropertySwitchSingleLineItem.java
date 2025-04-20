package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.materialswitch.MaterialSwitch;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

@SuppressLint("ViewConstructor")
public class PropertySwitchSingleLineItem extends LinearLayout implements View.OnClickListener {

    private String key = "";
    private boolean value = false;
    private TextView tvName;
    private MaterialSwitch switchValue;
    private ImageView imgLeftIcon;
    private int icon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;

    public PropertySwitchSingleLineItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void initialize(Context context, boolean z) {
        wB.a(context, this, R.layout.property_switch_item_singleline);
        tvName = findViewById(R.id.tv_name);
        switchValue = findViewById(R.id.switch_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        mB.a(this);
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            switch (this.key) {
                case "property_checked":
                    icon = R.drawable.ic_mtrl_check;
                    break;

                case "property_single_line":
                    icon = R.drawable.horizontal_line_48;
                    break;

                case "property_enabled":
                    icon = R.drawable.ic_mtrl_bulb;
                    break;

                case "property_clickable":
                    icon = R.drawable.ic_mtrl_touch;
            }
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            imgLeftIcon.setImageResource(icon);
        }
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
        switchValue.setChecked(value);
    }

    @Override
    public void onClick(View v) {
        setValue(!value);
        if (valueChangeListener != null) {
            valueChangeListener.a(key, value);
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
}