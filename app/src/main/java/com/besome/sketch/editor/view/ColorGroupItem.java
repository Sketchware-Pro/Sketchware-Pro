package com.besome.sketch.editor.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.wB;

public class ColorGroupItem extends RelativeLayout {

    public Context context;
    public TextView tvColorName;
    public ImageView imgSelector;

    public ColorGroupItem(Context context) {
        super(context);
        wB.a(context, this, 2131427374);
        tvColorName = (TextView) findViewById(2131231916);
        imgSelector = (ImageView) findViewById(2131231182);
        setPadding(0, 0, 4, 0);
    }
}
