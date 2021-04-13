package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.Yw;
import a.a.a.Zw;
import a.a.a._w;
import a.a.a.aB;
import a.a.a.ax;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PropertyIndentItem extends RelativeLayout implements View.OnClickListener {
    public Context a;
    public String b = "";
    public String c = "";
    public View d;
    public View e;
    public ImageView f;
    public int g;
    public TextView h;
    public TextView i;
    public int j;
    public int k;
    public int l;
    public int m;
    public Kw n;

    public PropertyIndentItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    public String getKey() {
        return this.b;
    }

    public String getValue() {
        return this.c;
    }

    public void onClick(View view) {
        if (!mB.a()) {
            String str = this.b;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -576300200) {
                if (hashCode == 1964055463 && str.equals("property_padding")) {
                    c2 = 1;
                }
            } else if (str.equals("property_margin")) {
                c2 = 0;
            }
            if (c2 == 0 || c2 == 1) {
                a();
            }
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.h.setText(xB.b().a(getResources(), identifier));
            String str2 = this.b;
            char c2 = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != -576300200) {
                if (hashCode == 1964055463 && str2.equals("property_padding")) {
                    c2 = 1;
                }
            } else if (str2.equals("property_margin")) {
                c2 = 0;
            }
            if (c2 == 0) {
                this.g = 2131165924;
            } else if (c2 == 1) {
                this.g = 2131165451;
            }
            if (this.e.getVisibility() == 0) {
                ((ImageView) findViewById(2131231151)).setImageResource(this.g);
                ((TextView) findViewById(2131232195)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            this.f.setImageResource(this.g);
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        this.n = kw;
    }

    @SuppressLint("WrongConstant")
    public void setOrientationItem(int i2) {
        if (i2 == 0) {
            this.d.setVisibility(8);
            this.e.setVisibility(0);
            return;
        }
        this.d.setVisibility(0);
        this.e.setVisibility(8);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context, boolean z) {
        this.a = context;
        wB.a(context, this, 2131427633);
        this.h = (TextView) findViewById(2131232055);
        this.i = (TextView) findViewById(2131232270);
        this.f = (ImageView) findViewById(2131231155);
        this.d = findViewById(2131231626);
        this.e = findViewById(2131231628);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    public void a(int i2, int i3, int i4, int i5) {
        this.j = i2;
        this.k = i3;
        this.l = i4;
        this.m = i5;
        TextView textView = this.i;
        textView.setText("left: " + this.j + ", top: " + this.k + ", right: " + this.l + ", bottom: " + this.m);
    }

    public final void a() {
        int i2;
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.h.getText().toString());
        aBVar.a(this.g);
        View a2 = wB.a(getContext(), 2131427635);
        @SuppressLint("ResourceType") CheckBox checkBox = (CheckBox) a2.findViewById(2131230890);
        checkBox.setText(xB.b().a(getContext(), 2131624972));
        EditText editText = (EditText) a2.findViewById(2131231023);
        EditText editText2 = (EditText) a2.findViewById(2131231038);
        EditText editText3 = (EditText) a2.findViewById(2131231044);
        EditText editText4 = (EditText) a2.findViewById(2131231042);
        EditText editText5 = (EditText) a2.findViewById(2131231025);
        TB tb = new TB(this.a, a2.findViewById(2131231802), 0, 999);
        TB tb2 = new TB(this.a, a2.findViewById(2131231824), 0, 999);
        @SuppressLint("ResourceType") TB tb3 = new TB(this.a, a2.findViewById(2131231829), 0, 999);
        TB tb4 = new TB(this.a, a2.findViewById(2131231831), 0, 999);
        TB tb5 = new TB(this.a, a2.findViewById(2131231806), 0, 999);
        tb2.a(String.valueOf(this.j));
        tb4.a(String.valueOf(this.k));
        tb3.a(String.valueOf(this.l));
        tb5.a(String.valueOf(this.m));
        int i3 = this.j;
        int i4 = this.k;
        if (i3 == i4 && i4 == (i2 = this.l) && i2 == this.m) {
            tb.a(String.valueOf(i3));
            checkBox.setChecked(true);
            editText.setEnabled(true);
            editText2.clearFocus();
            editText3.clearFocus();
            editText4.clearFocus();
            editText5.clearFocus();
            editText2.setEnabled(false);
            editText3.setEnabled(false);
            editText4.setEnabled(false);
            editText5.setEnabled(false);
        } else {
            editText.clearFocus();
            editText.setEnabled(false);
            editText2.setEnabled(true);
            editText3.setEnabled(true);
            editText4.setEnabled(true);
            editText5.setEnabled(true);
        }
        checkBox.setOnClickListener(new Yw(this, checkBox, editText, editText2, editText3, editText4, editText5));
        editText.addTextChangedListener(new Zw(this, tb2, editText, tb4, tb3, tb5));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new _w(this, checkBox, tb, tb2, tb3, tb4, tb5, editText2, editText3, editText4, editText5, aBVar));
        aBVar.a(xB.b().a(this.a, 2131624974), new ax(this, aBVar));
        aBVar.show();
    }
}
