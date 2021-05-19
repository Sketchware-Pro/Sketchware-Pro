package com.besome.sketch.editor.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Iterator;

import a.a.a.Vs;
import a.a.a.Ws;
import a.a.a.wB;
import a.a.a.xB;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {
    public Context a;
    public Vs b;

    public PaletteSelector(Context context) {
        super(context);
        a(context);
    }

    public PaletteSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    @SuppressLint("WrongConstant")
    public final void a(Context context) {
        this.a = context;
        setOrientation(1);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        int a2 = (int) wB.a(context, 8.0f);
        int a3 = (int) wB.a(context, 4.0f);
        setPadding(a2, a3, a2, a3);
        a();
    }

    public final void b() {
        for (int i = 0; i < getChildCount(); i++) {
            Ws childAt = (Ws) getChildAt(i);
            if (childAt instanceof Ws) {
                childAt.setSelected(false);
            }
        }
    }

    public void onClick(View view) {
        if (view instanceof Ws) {
            b();
            Ws ws = (Ws) view;
            ws.setSelected(true);
            this.b.a(ws.getId(), ws.getColor());
        }
    }

    public void setOnBlockCategorySelectListener(Vs vs) {
        this.b = vs;
    }

    public final void a() {
        a(0, xB.b().a(getResources(), 2131624105), -1147626);
        a(1, xB.b().a(getResources(), 2131624101), -3384542);
        a(2, xB.b().a(getResources(), 2131624099), -1988310);
        a(3, xB.b().a(getResources(), 2131624104), -10701022);
        a(4, xB.b().a(getResources(), 2131624102), -14435927);
        a(5, xB.b().a(getResources(), 2131624100), -6190977);
        a(6, xB.b().a(getResources(), 2131624106), -11899692);
        a(7, xB.b().a(getResources(), 2131624098), -13851166);
        a(8, xB.b().a(getResources(), 2131624103), -7711273);
        Iterator it = new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector().getPaletteSelector().iterator();
        while (it.hasNext()) {
            HashMap hashMap = (HashMap) it.next();
            a(((Integer) hashMap.get("index")).intValue(), (String) hashMap.get("text"), ((Integer) hashMap.get("color")).intValue());
        }
    }

    public final void a(int i, String str, int i2) {
        Ws ws = new Ws(this.a, i, str, i2);
        ws.setTag(String.valueOf(i));
        ws.setOnClickListener(this);
        addView(ws);
        if (i == 0) {
            ws.setSelected(true);
        }
    }
}
