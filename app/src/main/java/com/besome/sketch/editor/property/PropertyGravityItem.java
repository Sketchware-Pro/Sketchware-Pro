package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.Ww;
import a.a.a.Xw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PropertyGravityItem extends RelativeLayout implements View.OnClickListener {
    public String a = "";
    public int b = -1;
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public Kw i;

    public PropertyGravityItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return this.a;
    }

    public int getValue() {
        return this.b;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            String str = this.a;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1474767389) {
                if (hashCode == -1244048924 && str.equals("property_gravity")) {
                    c2 = 0;
                }
            } else if (str.equals("property_layout_gravity")) {
                c2 = 1;
            }
            if (c2 == 0 || c2 == 1) {
                a();
            }
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(xB.b().a(getResources(), identifier));
            this.f = 2131165660;
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

    public void setValue(int i2) {
        this.b = i2;
        this.d.setText(sq.a(i2));
    }

    @SuppressLint("ResourceType")
    public final void a(Context context, boolean z) {
        wB.a(context, this, 2131427648);
        this.c = (TextView) findViewById(2131232055);
        this.d = (TextView) findViewById(2131232270);
        this.e = (ImageView) findViewById(2131231155);
        this.g = findViewById(2131231626);
        this.h = findViewById(2131231628);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    @SuppressLint("ResourceType")
    public final void a() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.c.getText().toString());
        aBVar.a(this.f);
        View a2 = wB.a(getContext(), 2131427642);
        CheckBox checkBox = (CheckBox) a2.findViewById(2131230889);
        CheckBox checkBox2 = (CheckBox) a2.findViewById(2131230891);
        CheckBox checkBox3 = (CheckBox) a2.findViewById(2131230888);
        CheckBox checkBox4 = (CheckBox) a2.findViewById(2131230894);
        CheckBox checkBox5 = (CheckBox) a2.findViewById(2131230886);
        CheckBox checkBox6 = (CheckBox) a2.findViewById(2131230895);
        int i2 = this.b;
        int i3 = i2 & 112;
        int i4 = i2 & 7;
        if (i4 == 1) {
            checkBox3.setChecked(true);
        } else {
            if ((i4 & 3) == 3) {
                checkBox.setChecked(true);
            }
            if ((i4 & 5) == 5) {
                checkBox2.setChecked(true);
            }
        }
        if (i3 == 16) {
            checkBox6.setChecked(true);
        } else {
            if ((i3 & 48) == 48) {
                checkBox4.setChecked(true);
            }
            if ((i3 & 80) == 80) {
                checkBox5.setChecked(true);
            }
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625035), new Ww(this, checkBox, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new Xw(this, aBVar));
        aBVar.show();
    }
}
