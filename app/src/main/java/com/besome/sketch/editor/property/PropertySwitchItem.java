package com.besome.sketch.editor.property;

import a.a.a.wB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

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

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        wB.a(context, this, 2131427650);
        this.c = (TextView) findViewById(2131232055);
        this.d = (TextView) findViewById(2131231944);
        this.e = (Switch) findViewById(2131231777);
        setOnClickListener(this);
        setSoundEffectsEnabled(true);
    }

    public int getKey() {
        return this.a;
    }

    public boolean getValue() {
        return this.b;
    }

    public void onClick(View view) {
        setValue(!this.b);
    }

    public void setDesc(String str) {
        this.d.setText(str);
    }

    public void setKey(int i) {
        this.a = i;
    }

    public void setName(String str) {
        this.c.setText(str);
    }

    public void setSwitchChangedListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.e.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setTextColor(int i) {
        this.c.setTextColor(i);
        this.d.setTextColor(i);
    }

    public void setValue(boolean z) {
        this.b = z;
        this.e.setChecked(z);
    }
}
