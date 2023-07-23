package com.besome.sketch.lib.ui;

import a.a.a.wB;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

public class PropertyTwoLineItem extends RelativeLayout {
    private int key = -1;
    private TextView tvName, tvDesc;

    public PropertyTwoLineItem(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, R.layout.program_info_two_line_item);
        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_desc);
    }

    public int getKey() {
        return key;
    }

    public void setDesc(String txt) {
        tvDesc.setText(txt);
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setName(String var1) {
        tvName.setText(var1);
    }
}
