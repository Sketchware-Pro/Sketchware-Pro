package com.besome.sketch.editor.event;

import a.a.a.wB;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

public class CollapsibleButton extends LinearLayout {
    public int b;
    public ImageView d;
    public TextView e;

    public CollapsibleButton(Context context) {
        this(context, null);
    }

    public CollapsibleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        setLayoutParams(layoutParams);
        wB.a(context, this, R.layout.fr_logic_list_item_button);
        d = findViewById(R.id.icon);
        e = findViewById(R.id.name);
    }
}
