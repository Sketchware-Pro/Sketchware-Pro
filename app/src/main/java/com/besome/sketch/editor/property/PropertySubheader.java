package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.wB;
import pro.sketchware.R;

public class PropertySubheader extends RelativeLayout {

    private ImageView imgAdd;
    private TextView tvName;

    public PropertySubheader(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.property_subheader);
        tvName = findViewById(R.id.tv_name);
        imgAdd = findViewById(R.id.img_add);
    }

    public void setHeaderName(String str) {
        tvName.setText(str);
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        imgAdd.setVisibility(VISIBLE);
        imgAdd.setOnClickListener(onClickListener);
    }
}
