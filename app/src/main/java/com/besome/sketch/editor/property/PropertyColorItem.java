package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.Mw;
import a.a.a.Zx;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PropertyColorItem extends RelativeLayout implements View.OnClickListener {
    public Context a;
    public String b;
    public int c;
    public TextView d;
    public TextView e;
    public View f;
    public ImageView g;
    public int h;
    public View i;
    public View j;
    public Kw k;

    public PropertyColorItem(Context context, boolean z) {
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
            a();
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.d.setText(xB.b().a(getResources(), identifier));
            this.h = 2131165472;
            if (this.j.getVisibility() == 0) {
                ((ImageView) findViewById(2131231151)).setImageResource(this.h);
                ((TextView) findViewById(2131232195)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            this.g.setImageResource(this.h);
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        this.k = kw;
    }

    @SuppressLint("WrongConstant")
    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.i.setVisibility(8);
            this.j.setVisibility(0);
            return;
        }
        this.i.setVisibility(0);
        this.j.setVisibility(8);
    }

    public void setValue(int i2) {
        this.c = i2;
        if (i2 == 0) {
            this.e.setText("TRANSPARENT");
            this.f.setBackgroundColor(i2);
        } else if (i2 == 16777215) {
            this.e.setText("NONE");
            this.f.setBackgroundColor(i2);
        } else {
            this.e.setText(String.format("#%08X", Integer.valueOf(i2 & -1)));
            this.f.setBackgroundColor(i2);
        }
    }

    @SuppressLint("ResourceType")
    public final void a(Context context, boolean z) {
        this.a = context;
        wB.a(context, this, 2131427628);
        this.d = (TextView) findViewById(2131232055);
        this.e = (TextView) findViewById(2131232270);
        this.f = findViewById(2131232318);
        this.g = (ImageView) findViewById(2131231155);
        this.i = findViewById(2131231626);
        this.j = findViewById(2131231628);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    @SuppressLint("ResourceType")
    public final void a() {
        boolean z;
        boolean z2;
        View a2 = wB.a(this.a, 2131427373);
        a2.setAnimation(AnimationUtils.loadAnimation(this.a, 2130771968));
        if (this.b == "property_background_color") {
            z2 = true;
            z = true;
        } else {
            z2 = false;
            z = false;
        }
        Zx zx = new Zx(a2, (Activity) this.a, this.c, z2, z);
        zx.a(new Mw(this));
        zx.setAnimationStyle(2130771968);
        zx.showAtLocation(a2, 17, 0, 0);
    }
}
