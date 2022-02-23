package com.besome.sketch.editor.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.wB;

public class ColorGroupItem extends RelativeLayout {

    public Context context;
    public TextView tvColorName;
    public ImageView imgSelector;

    public ColorGroupItem(Context context) {
        super(context);
        wB.a(context, this, Resources.layout.color_picker_grid_item);
        tvColorName = findViewById(Resources.id.tv_color_name);
        imgSelector = findViewById(Resources.id.img_selector);
        setPadding(0, 0, 4, 0);
    }
}
