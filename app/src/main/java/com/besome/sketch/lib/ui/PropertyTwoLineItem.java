package com.besome.sketch.lib.ui;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.wB;
import pro.sketchware.R;

public class PropertyTwoLineItem extends RelativeLayout {
    private int key = -1;
    private TextView tvName, tvDesc;
    private View divider;

    public PropertyTwoLineItem(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, R.layout.program_info_two_line_item);
        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_desc);
        divider = findViewById(R.id.layout_divider);
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setDesc(String txt) {
        tvDesc.setText(txt);
    }

    public void setName(String var1) {
        tvName.setText(var1);
    }

    public void setHideDivider(boolean hideDivider) {
        divider.setVisibility(hideDivider ? View.GONE : View.VISIBLE);
    }
}
