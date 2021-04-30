package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.wB;

public class PropertySubheader extends RelativeLayout {

    public ImageView a;
    public TextView b;

    public PropertySubheader(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, Resources.layout.property_subheader);
        b = findViewById(Resources.id.tv_name);
        a = findViewById(Resources.id.img_add);
    }

    public void setHeaderName(String str) {
        b.setText(str);
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        a.setVisibility(VISIBLE);
        a.setOnClickListener(onClickListener);
    }
}
