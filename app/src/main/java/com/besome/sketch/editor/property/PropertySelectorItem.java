package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.ux;
import a.a.a.vx;
import a.a.a.wB;
import a.a.a.xB;

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

public class PropertySelectorItem extends RelativeLayout implements View.OnClickListener {
    public String a = "";
    public int b = -1;
    public TextView c;
    public TextView d;
    public ImageView e;
    public int f;
    public View g;
    public View h;
    public ViewGroup i;
    public Kw j;

    public PropertySelectorItem(Context context, boolean z) {
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
            switch (str.hashCode()) {
                case -1567696407:
                    if (str.equals("property_text_size")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1353621303:
                    if (str.equals("property_text_style")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -522792099:
                    if (str.equals("property_ime_option")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -512158157:
                    if (str.equals("property_spinner_mode")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 235805286:
                    if (str.equals("property_orientation")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1096920256:
                    if (str.equals("property_first_day_of_week")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 1106908695:
                    if (str.equals("property_choice_mode")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 2133471033:
                    if (str.equals("property_input_type")) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    a();
                    return;
                default:
                    return;
            }
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.a = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.c.setText(xB.b().a(getResources(), identifier));
            String str2 = this.a;
            char c2 = 65535;
            switch (str2.hashCode()) {
                case -1567696407:
                    if (str2.equals("property_text_size")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1353621303:
                    if (str2.equals("property_text_style")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -522792099:
                    if (str2.equals("property_ime_option")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case -512158157:
                    if (str2.equals("property_spinner_mode")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case 235805286:
                    if (str2.equals("property_orientation")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 1096920256:
                    if (str2.equals("property_first_day_of_week")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 1106908695:
                    if (str2.equals("property_choice_mode")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case 2133471033:
                    if (str2.equals("property_input_type")) {
                        c2 = 4;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.f = 2131165662;
                    break;
                case 1:
                    this.f = 2131165191;
                    break;
                case 2:
                    this.f = 2131166191;
                    break;
                case 3:
                    this.f = 2131165929;
                    break;
                case 4:
                    this.f = 2131165929;
                    break;
                case 5:
                    this.f = 2131166037;
                    break;
                case 6:
                    this.f = 2131165973;
                    break;
                case 7:
                    this.f = 2131165967;
                    break;
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

    public void setValue(int i2) {
        this.b = i2;
        this.d.setText(sq.a(this.a, i2));
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
        TextView textView = (TextView) a2.findViewById(2131230963);
        int i2 = 0;
        if (this.a == "property_ime_option") {
            textView.setText(xB.b().a(getContext(), 2131625819));
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        for (Pair<Integer, String> pair : sq.a(this.a)) {
            this.i.addView(a(pair));
        }
        int childCount = this.i.getChildCount();
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) this.i.getChildAt(i2);
            if (Integer.valueOf(radioButton.getTag().toString()).intValue() == this.b) {
                radioButton.setChecked(true);
                break;
            }
            i2++;
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625035), new ux(this, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new vx(this, aBVar));
        aBVar.show();
    }

    public final RadioButton a(Pair<Integer, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText((CharSequence) pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (wB.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
