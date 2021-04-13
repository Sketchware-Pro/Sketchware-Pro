package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.wx;
import a.a.a.xB;
import a.a.a.xx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PropertySizeItem extends RelativeLayout implements View.OnClickListener {
    public Context a;
    public String b = "";
    public int c = 1;
    public TextView d;
    public TextView e;
    public ImageView f;
    public int g;
    public View h;
    public View i;
    public Kw j;

    public PropertySizeItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return this.b;
    }

    public int getValue() {
        return this.c;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            String str = this.b;
            char c2 = 65535;
            if (str.hashCode() == -1919612745 && str.equals("property_divider_height")) {
                c2 = 0;
            }
            if (c2 == 0) {
                a();
            }
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.d.setText(xB.b().a(getResources(), identifier));
            this.g = 2131165605;
            if (this.i.getVisibility() == 0) {
                ((ImageView) findViewById(2131231151)).setImageResource(this.g);
                ((TextView) findViewById(2131232195)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            this.f.setImageResource(this.g);
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        this.j = kw;
    }

    @SuppressLint("WrongConstant")
    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.h.setVisibility(8);
            this.i.setVisibility(0);
            return;
        }
        this.h.setVisibility(0);
        this.i.setVisibility(8);
    }

    public void setValue(int i2) {
        this.c = i2;
        TextView textView = this.e;
        textView.setText(this.c + " dp");
    }

    @SuppressLint("ResourceType")
    public final void a(Context context, boolean z) {
        this.a = context;
        wB.a(context, this, 2131427633);
        this.d = (TextView) findViewById(2131232055);
        this.e = (TextView) findViewById(2131232270);
        this.f = (ImageView) findViewById(2131231155);
        this.h = findViewById(2131231626);
        this.i = findViewById(2131231628);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    @SuppressLint("ResourceType")
    public final void a() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.d.getText().toString());
        aBVar.a(this.g);
        View a2 = wB.a(getContext(), 2131427637);
        TB tb = new TB(this.a, a2.findViewById(2131231816), 0, 999);
        tb.a(String.valueOf(this.c));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new wx(this, tb, (EditText) a2.findViewById(2131231034), aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new xx(this, aBVar));
        aBVar.show();
    }
}
