package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class PropertySwitchSingleLineItem extends LinearLayout implements View.OnClickListener {
    public String a = "";
    public boolean b = false;
    public TextView c;
    public Switch d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public Kw i;

    public PropertySwitchSingleLineItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context, boolean z) {
        wB.a(context, this, 2131427651);
        this.c = (TextView) findViewById(2131232055);
        this.d = (Switch) findViewById(2131231777);
        this.e = (ImageView) findViewById(2131231155);
        this.g = findViewById(2131231626);
        this.h = findViewById(2131231628);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    public String getKey() {
        return this.a;
    }

    public boolean getValue() {
        return this.b;
    }

    public void onClick(View view) {
        setValue(!this.b);
        Kw kw = this.i;
        if (kw != null) {
            kw.a(this.a, Boolean.valueOf(this.b));
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        mB.a(this);
        this.a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(xB.b().a(getResources(), identifier));
            String str2 = this.a;
            char c2 = 65535;
            switch (str2.hashCode()) {
                case -782258371:
                    if (str2.equals("property_checked")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -56658399:
                    if (str2.equals("property_single_line")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1160800983:
                    if (str2.equals("property_enabled")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case 1800186104:
                    if (str2.equals("property_clickable")) {
                        c2 = 2;
                        break;
                    }
                    break;
            }
            if (c2 == 0) {
                this.f = 2131165672;
            } else if (c2 == 1) {
                this.f = 2131165952;
            } else if (c2 == 2) {
                this.f = 2131165979;
            } else if (c2 == 3) {
                this.f = 2131166002;
            }
            if (this.h.getVisibility() == 0) {
                ((ImageView) findViewById(2131231151)).setImageResource(this.f);
                ((TextView) findViewById(2131232195)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            this.e.setImageResource(this.f);
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        this.i = kw;
    }

    @SuppressLint("WrongConstant")
    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.g.setVisibility(8);
            this.h.setVisibility(0);
            return;
        }
        this.g.setVisibility(0);
        this.h.setVisibility(8);
    }

    public void setValue(boolean z) {
        this.b = z;
        this.d.setChecked(z);
    }
}
