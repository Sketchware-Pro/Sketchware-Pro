package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import a.a.a.yx;
import a.a.a.zx;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PropertyStringPairSelectorItem extends RelativeLayout implements View.OnClickListener {
    public String a = "";
    public String b = "";
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public ViewGroup i;
    public Kw j;

    public PropertyStringPairSelectorItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            a();
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(xB.b().a(getResources(), identifier));
            char c2 = 65535;
            if (str.hashCode() == -78143730 && str.equals("property_progressbar_style")) {
                c2 = 0;
            }
            if (c2 == 0) {
                this.f = 2131166179;
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
        this.j = kw;
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

    public void setValue(String str) {
        this.b = str;
        this.d.setText(str);
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
        View a2 = wB.a(getContext(), 2131427643);
        this.i = (ViewGroup) a2.findViewById(2131231668);
        int i2 = 0;
        for (Pair<String, String> pair : sq.b(this.a)) {
            this.i.addView(a(pair));
        }
        int childCount = this.i.getChildCount();
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.i.getChildAt(i2);
            if (radioButton.getTag().toString().equals(this.b)) {
                radioButton.setChecked(true);
                break;
            }
            i2++;
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625035), new yx(this, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new zx(this, aBVar));
        aBVar.show();
    }

    public final RadioButton a(Pair<String, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText((CharSequence) pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (wB.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
