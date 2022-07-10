package com.besome.sketch.ctrls;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

public class ViewIdSpinnerItem extends LinearLayout {

    public ImageView a;
    public TextView b;
    public ImageView c;
    public Context d;
    public boolean e;

    public ViewIdSpinnerItem(Context context) {
        super(context);
        initialize(context);
    }

    public void a(int icon, String text, boolean selected) {
        if (selected) {
            c.setVisibility(View.VISIBLE);
        } else {
            c.setVisibility(View.GONE);
        }

        if (text.charAt(0) == '_') {
            b.setText(text.substring(1));
            a(false, 0xffff5555, 0xfff8f820);
        } else {
            b.setText(text);
            a(true, 0xff757575, 0xffffffff);
        }

        a.setImageResource(icon);
    }

    private void initialize(Context context) {
        d = context;
        wB.a(context, this, R.layout.var_id_spinner_item);
        a = findViewById(R.id.icon);
        b = findViewById(R.id.name);
        c = findViewById(R.id.imgv_selected);
    }

    public void a(boolean notSelected, int color, int var3) {
        if (notSelected) {
            if (!e) color = var3;
            b.setTextColor(color);
            b.setTypeface(null, Typeface.NORMAL);
        } else {
            if (!e) color = var3;
            b.setTextColor(color);
            b.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    public void setDropDown(boolean isDropDown) {
        e = isDropDown;
    }

    public void setTextSize(int textSize) {
    }
}
