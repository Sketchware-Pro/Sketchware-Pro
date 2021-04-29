package com.besome.sketch.editor.event;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

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
        wB.a(context, this, Resources.layout.fr_logic_list_item_button);
        c = findViewById(Resources.id.img_button);
        d = findViewById(Resources.id.icon);
        e = findViewById(Resources.id.name);
    }
}
