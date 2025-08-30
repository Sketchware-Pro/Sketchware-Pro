package com.besome.sketch.editor.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.wB;
import pro.sketchware.R;

public class ColorGroupItem extends RelativeLayout {

    public final TextView tvColorName;
    public final ImageView imgColorSelector;

    public ColorGroupItem(Context context) {
        super(context);
        wB.a(context, this, R.layout.color_picker_grid_item);
        tvColorName = findViewById(R.id.tv_color_name);
        imgColorSelector = findViewById(R.id.img_selector);
        setPadding(0, 0, 4, 0);
    }
}
