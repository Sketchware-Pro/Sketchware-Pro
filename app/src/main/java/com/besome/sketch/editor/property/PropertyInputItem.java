package com.besome.sketch.editor.property;

import a.a.a.Kw;
import a.a.a.OB;
import a.a.a.SB;
import a.a.a.TB;
import a.a.a._B;
import a.a.a.aB;
import a.a.a.bx;
import a.a.a.cx;
import a.a.a.dx;
import a.a.a.ex;
import a.a.a.fx;
import a.a.a.gx;
import a.a.a.hx;
import a.a.a.ix;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectFileBean;
import com.google.android.gms.common.api.Api;

public class PropertyInputItem extends RelativeLayout implements View.OnClickListener {
    public Context a;
    public String b = "";
    public String c = "";
    public ImageView d;
    public int e;
    public TextView f;
    public TextView g;
    public View h;
    public View i;
    public String j;
    public ProjectFileBean k;
    public Kw l;

    public PropertyInputItem(Context context, boolean z) {
        super(context);
        a(context, z);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void setIcon(ImageView imageView) {
        char c2;
        String str = this.b;
        switch (str.hashCode()) {
            case -1553367436:
                if (str.equals("property_alpha")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case -1543300075:
                if (str.equals("property_lines")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case -1019734351:
                if (str.equals("property_hint")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case -1019380393:
                if (str.equals("property_text")) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case -1018178217:
                if (str.equals("property_progress")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case -864174086:
                if (str.equals("property_max")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case -710204242:
                if (str.equals("property_weight_sum")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case -679051461:
                if (str.equals("property_inject")) {
                    c2 = 14;
                    break;
                }
                c2 = 65535;
                break;
            case -572981943:
                if (str.equals("property_convert")) {
                    c2 = 15;
                    break;
                }
                c2 = 65535;
                break;
            case -420171003:
                if (str.equals("property_rotate")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case -286582750:
                if (str.equals("property_weight")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 20737408:
                if (str.equals("property_translation_x")) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 20737409:
                if (str.equals("property_translation_y")) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case 386320985:
                if (str.equals("property_scale_x")) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case 386320986:
                if (str.equals("property_scale_y")) {
                    c2 = '\r';
                    break;
                }
                c2 = 65535;
                break;
            case 1357596613:
                if (str.equals("property_id")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                this.e = 2131166047;
                break;
            case 1:
                this.e = 2131165190;
                break;
            case 2:
                this.e = 2131165668;
                break;
            case 3:
                this.e = 2131166003;
                break;
            case 4:
                this.e = 2131166003;
                break;
            case 5:
                this.e = 2131165834;
                break;
            case 6:
                this.e = 2131166000;
                break;
            case 7:
                this.e = 2131166000;
                break;
            case '\b':
                this.e = 2131166000;
                break;
            case '\t':
                this.e = 2131166004;
                break;
            case '\n':
                this.e = 2131166181;
                break;
            case 11:
                this.e = 2131166180;
                break;
            case '\f':
                this.e = 2131166048;
                break;
            case '\r':
                this.e = 2131166048;
                break;
            case 14:
                this.e = 2131166315;
                break;
            case 15:
                this.e = 2131166316;
                break;
        }
        imageView.setImageResource(this.e);
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
            switch (str.hashCode()) {
                case -1553367436:
                    if (str.equals("property_alpha")) {
                        c2 = '\t';
                        break;
                    }
                    break;
                case -1543300075:
                    if (str.equals("property_lines")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case -1019734351:
                    if (str.equals("property_hint")) {
                        c2 = 2;
                        break;
                    }
                    break;
                case -1019380393:
                    if (str.equals("property_text")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -1018178217:
                    if (str.equals("property_progress")) {
                        c2 = '\b';
                        break;
                    }
                    break;
                case -864174086:
                    if (str.equals("property_max")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case -710204242:
                    if (str.equals("property_weight_sum")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case -679051461:
                    if (str.equals("property_inject")) {
                        c2 = 14;
                        break;
                    }
                    break;
                case -572981943:
                    if (str.equals("property_convert")) {
                        c2 = 15;
                        break;
                    }
                    break;
                case -420171003:
                    if (str.equals("property_rotate")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case -286582750:
                    if (str.equals("property_weight")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 20737408:
                    if (str.equals("property_translation_x")) {
                        c2 = '\n';
                        break;
                    }
                    break;
                case 20737409:
                    if (str.equals("property_translation_y")) {
                        c2 = 11;
                        break;
                    }
                    break;
                case 386320985:
                    if (str.equals("property_scale_x")) {
                        c2 = '\f';
                        break;
                    }
                    break;
                case 386320986:
                    if (str.equals("property_scale_y")) {
                        c2 = '\r';
                        break;
                    }
                    break;
                case 1357596613:
                    if (str.equals("property_id")) {
                        c2 = 0;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    b();
                    return;
                case 1:
                case 2:
                    b(0, 9999);
                    return;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                    a();
                    return;
                case '\t':
                    a(0, 1);
                    return;
                case '\n':
                case 11:
                    a(-9999, 9999);
                    return;
                case '\f':
                case '\r':
                    a(0, 99);
                    return;
                case 14:
                    b(0, 9999);
                    return;
                case 15:
                    b(0, 99);
                    return;
                default:
                    return;
            }
        }
    }

    @SuppressLint("ResourceType")
    public void setKey(String str) {
        this.b = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            this.f.setText(xB.b().a(getResources(), identifier));
            if (this.i.getVisibility() == 0) {
                setIcon((ImageView) findViewById(2131231151));
                ((TextView) findViewById(2131232195)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            setIcon(this.d);
        }
    }

    public void setOnPropertyValueChangeListener(Kw kw) {
        this.l = kw;
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

    public void setValue(String str) {
        this.c = str;
        this.g.setText(str);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context, boolean z) {
        this.a = context;
        wB.a(context, this, 2131427633);
        this.f = (TextView) findViewById(2131232055);
        this.g = (TextView) findViewById(2131232270);
        this.d = (ImageView) findViewById(2131231155);
        this.h = findViewById(2131231626);
        this.i = findViewById(2131231628);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }
    @SuppressLint("ResourceType")
    public final void b() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.f.getText().toString());
        aBVar.a(this.e);
        View a2 = wB.a(getContext(), 2131427638);
        EditText editText = (EditText) a2.findViewById(2131230990);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(524289);
        editText.setImeOptions(6);
        @SuppressLint("ResourceType") _B _b = new _B(this.a, a2.findViewById(2131231816), uq.b, uq.a(), jC.a(this.j).a(this.k), this.c);
        _b.a(this.c);
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new bx(this, _b, editText, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new cx(this, aBVar));
        aBVar.show();
    }

    public void a(String str, ProjectFileBean projectFileBean) {
        this.j = str;
        this.k = projectFileBean;
    }
    @SuppressLint("ResourceType")
    public final void a() {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.f.getText().toString());
        aBVar.a(this.e);
        View a2 = wB.a(getContext(), 2131427638);
        EditText editText = (EditText) a2.findViewById(2131230990);
        editText.setInputType(4098);
        editText.setText(this.c);
        String str = this.b;
        TB tb = new TB(this.a, a2.findViewById(2131231816), 0, (str == "property_max" || str == "property_progress") ? Api.BaseClientBuilder.API_PRIORITY_OTHER : 999);
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new fx(this, tb, editText, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new gx(this, aBVar));
        aBVar.show();
    }

    @SuppressLint("ResourceType")
    public final void b(int i2, int i3) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.f.getText().toString());
        aBVar.a(this.e);
        View a2 = wB.a(getContext(), 2131427638);
        SB sb = new SB(this.a, a2.findViewById(2131231816), i2, i3);
        sb.a(this.c);
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new dx(this, sb, (EditText) a2.findViewById(2131230990), aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new ex(this, aBVar));
        aBVar.show();
    }
    @SuppressLint("ResourceType")
    public final void a(int i2, int i3) {
        aB aBVar = new aB((Activity) getContext());
        aBVar.b(this.f.getText().toString());
        aBVar.a(this.e);
        View a2 = wB.a(getContext(), 2131427638);
        EditText editText = (EditText) a2.findViewById(2131230990);
        editText.setInputType(i2 < 0 ? 12290 : 8194);
        editText.setText(this.c);
        OB ob = new OB(this.a, a2.findViewById(2131231816), i2, i3);
        aBVar.a(a2);
        aBVar.b(xB.b().a(getContext(), 2131625031), new hx(this, ob, editText, aBVar));
        aBVar.a(xB.b().a(getContext(), 2131624974), new ix(this, aBVar));
        aBVar.show();
    }
}
