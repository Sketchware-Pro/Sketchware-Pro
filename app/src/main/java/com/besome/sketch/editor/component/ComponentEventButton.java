package com.besome.sketch.editor.component;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

public class ComponentEventButton extends LinearLayout {
    public LinearLayout a;
    public LinearLayout b;
    public ImageView c;
    public ImageView d;
    public TextView e;

    public ComponentEventButton(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, R.layout.fr_logic_list_item_component_event);
        this.a = (LinearLayout) findViewById(R.id.container);
        this.d = (ImageView) findViewById(R.id.add_event);
        this.b = (LinearLayout) findViewById(R.id.icon_bg);
        this.c = (ImageView) findViewById(R.id.icon);
        this.e = (TextView) findViewById(R.id.name);
    }

    public void b() {
        this.d.setVisibility(GONE);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(1);
        this.c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        this.a.setOnClickListener(onClickListener);
    }

    public void a() {
        this.d.setVisibility(VISIBLE);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        this.c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }
}
