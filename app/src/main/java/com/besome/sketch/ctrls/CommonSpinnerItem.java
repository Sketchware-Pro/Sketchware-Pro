package com.besome.sketch.ctrls;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.wB;
import pro.sketchware.R;

public class CommonSpinnerItem extends LinearLayout {

    public ImageView a;
    public TextView b;
    public Context c;

    public CommonSpinnerItem(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        c = context;
        wB.a(context, this, R.layout.common_spinner_item);
        b = findViewById(R.id.tv_spn_name);
        a = findViewById(R.id.imgv_selected);
    }

    public void a(String name, boolean isVisible) {
        b.setText(name);
        if (isVisible) {
            a.setVisibility(View.VISIBLE);
        } else {
            a.setVisibility(View.GONE);
        }

    }

    public void setTextSize(int textSize) {
    }
}
