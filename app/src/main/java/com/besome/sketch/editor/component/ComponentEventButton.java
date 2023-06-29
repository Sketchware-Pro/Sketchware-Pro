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
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.fr_logic_list_item_component_event);
        a = findViewById(R.id.container);
        d = findViewById(R.id.add_event);
        b = findViewById(R.id.icon_bg);
        c = findViewById(R.id.icon);
        e = findViewById(R.id.name);
    }

    public void b() {
        d.setVisibility(GONE);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(1);
        c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        a.setOnClickListener(onClickListener);
    }

    public void a() {
        d.setVisibility(VISIBLE);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        c.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }
}
