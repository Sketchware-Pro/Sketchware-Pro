package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

public class PropertySubheader extends RelativeLayout {

    public ImageView imgAdd;
    public TextView tvName;

    public PropertySubheader(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
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
