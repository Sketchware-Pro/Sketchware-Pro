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

    public int a = -1;
    public boolean b = false;
    public TextView c;
    public TextView d;
    public Switch e;

    public PropertySwitchItem(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, Resources.layout.property_switch_item);
        c = findViewById(Resources.id.tv_name);
        d = findViewById(Resources.id.tv_desc);
        e = findViewById(Resources.id.switch_value);
        setOnClickListener(this);
        setSoundEffectsEnabled(true);
    }

    public int getKey() {
        return a;
    }

    public void setKey(int i) {
        a = i;
    }

    public boolean getValue() {
        return b;
    }

    public void setValue(boolean value) {
        b = value;
        e.setChecked(value);
    }

    public void onClick(View view) {
        setValue(!b);
    }

    public void setDesc(String str) {
        d.setText(str);
    }

    public void setName(String str) {
        c.setText(str);
    }

    public void setSwitchChangedListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        e.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setTextColor(int textColor) {
        c.setTextColor(textColor);
        d.setTextColor(textColor);
    }
}
