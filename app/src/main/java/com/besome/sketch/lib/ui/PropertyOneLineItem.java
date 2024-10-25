package com.besome.sketch.lib.ui;

import a.a.a.wB;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

public class PropertyOneLineItem extends RelativeLayout {
    private int key = -1;
    private TextView tvName;

    public PropertyOneLineItem(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.program_info_item);
        tvName = findViewById(R.id.tv_name);
        setClickable(true);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setName(String txt) {
        tvName.setText(txt);
    }
}
