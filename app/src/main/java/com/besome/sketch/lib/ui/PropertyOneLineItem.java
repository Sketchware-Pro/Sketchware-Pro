package com.besome.sketch.lib.ui;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.wB;
import pro.sketchware.R;

public class PropertyOneLineItem extends RelativeLayout {
    private int key = -1;
    private TextView tvName;
    private View divider;

    public PropertyOneLineItem(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.program_info_item);
        tvName = findViewById(R.id.tv_name);
        divider = findViewById(R.id.layout_divider);
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

    public void setHideDivider(boolean hideDivider) {
        divider.setVisibility(hideDivider ? View.GONE : View.VISIBLE);
    }
}
