package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.materialswitch.MaterialSwitch;

import pro.sketchware.databinding.PropertySwitchItemBinding;

public class PropertySwitchItem extends RelativeLayout implements View.OnClickListener {

    public PropertySwitchItemBinding binding;
    private int key = -1;
    private boolean value = false;
    private TextView tvName;
    private TextView tvDesc;
    private MaterialSwitch switchValue;

    public PropertySwitchItem(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        binding = PropertySwitchItemBinding.inflate(LayoutInflater.from(context), this, true);
        tvName = binding.tvName;
        tvDesc = binding.tvDesc;
        switchValue = binding.switchValue;
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

    @Override
    public void onClick(View v) {
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