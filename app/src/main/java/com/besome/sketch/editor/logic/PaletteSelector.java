package com.besome.sketch.editor.logic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;

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
        a = context;
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        setPadding(
            (int) wB.a(context, 8.0f),
            (int) wB.a(context, 4.0f),
            (int) wB.a(context, 8.0f),
            (int) wB.a(context, 4.0f)
        );
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
            Ws paletteView = (Ws) view;
            paletteView.setSelected(true);
            b.a(paletteView.getId(), paletteView.getColor());
        }
    }

    public void setOnBlockCategorySelectListener(Vs vs) {
        b = vs;
    }

    public final void a() {
        addPalette(0, xB.b().a(getResources(), 2131624105), 0xffee7d16);
        addPalette(1, xB.b().a(getResources(), 2131624101), 0xffcc5b22);
        addPalette(2, xB.b().a(getResources(), 2131624099), 0xffe1a92a);
        addPalette(3, xB.b().a(getResources(), 2131624104), 0xff5cb722);
        addPalette(4, xB.b().a(getResources(), 2131624102), 0xff23b9a9);
        addPalette(5, xB.b().a(getResources(), 2131624100), 0xffa1887f);
        addPalette(6, xB.b().a(getResources(), 2131624106), 0xff4a6cd4);
        addPalette(7, xB.b().a(getResources(), 2131624098), 0xff2ca5e2);
        addPalette(8, xB.b().a(getResources(), 2131624103), 0xff8a55d7);
        for (HashMap<String, Object> palette : new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector().getPaletteSelector()) {
            addPalette(
                Integer.parseInt(palette.get("index").toString()),
                palette.get("text").toString(),
                Integer.parseInt(palette.get("color").toString())
            );
        }
    }

    private void addPalette(int id, String title, int color) {
        Ws paletteView = new Ws(a, id, title, color);
        paletteView.setTag(String.valueOf(id));
        paletteView.setOnClickListener(this);
        addView(paletteView);
        if (id == 0) {
            paletteView.setSelected(true);
        }
    }
}
