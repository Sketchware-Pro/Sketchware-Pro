package com.besome.sketch.editor.event;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.wB;

public class CollapsibleButton extends LinearLayout {

    public Context a;
    public int b;
    public LinearLayout c;
    public ImageView d;
    public TextView e;

    public CollapsibleButton(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        a = context;
        setLayoutParams(new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        wB.a(context, this, com.sketchware.remod.R.layout.fr_logic_list_item_button);
        c = findViewById(com.sketchware.remod.R.id.img_button);
        d = findViewById(com.sketchware.remod.R.id.icon);
        e = findViewById(com.sketchware.remod.R.id.name);
    }
}
