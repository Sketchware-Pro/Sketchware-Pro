package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.wB;

public class PropertySwitchItem extends RelativeLayout implements View.OnClickListener {

    public int key = -1;
    public boolean value = false;
    public TextView tvName;
    public TextView tvDesc;
    public Switch switchValue;

    public PropertySwitchItem(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, Resources.layout.property_switch_item);
        tvName = findViewById(Resources.id.tv_name);
        tvDesc = findViewById(Resources.id.tv_desc);
        switchValue = findViewById(Resources.id.switch_value);
        setOnClickListener(this);
        setSoundEffectsEnabled(true);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
        switchValue.setChecked(value);
    }

    public void onClick(View view) {
        setValue(!value);
    }

    public void setDesc(String str) {
        tvDesc.setText(str);
    }

    public void setName(String str) {
        tvName.setText(str);
    }

    public void setSwitchChangedListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        switchValue.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setTextColor(int textColor) {
        tvName.setTextColor(textColor);
        tvDesc.setTextColor(textColor);
    }
}
