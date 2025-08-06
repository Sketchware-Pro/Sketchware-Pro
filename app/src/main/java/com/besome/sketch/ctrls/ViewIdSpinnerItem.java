package com.besome.sketch.ctrls;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.wB;
import pro.sketchware.R;

public class ViewIdSpinnerItem extends LinearLayout {

    private ImageView icon;
    private TextView name;
    private ImageView selected;
    private boolean isDropDown;

    public ViewIdSpinnerItem(Context context) {
        super(context);
        initialize(context);
    }

    public void a(int iconResId, String text, boolean isSelected) {
        if (isSelected) {
            selected.setVisibility(View.VISIBLE);
        } else {
            selected.setVisibility(View.GONE);
        }

        if (text.charAt(0) == '_') {
            name.setText(text.substring(1));
            a(false, 0xffff5555, 0xfff8f820);
        } else {
            name.setText(text);
            a(true, getResources().getColor(R.color.view_property_spinner_filter), getResources().getColor(R.color.view_property_spinner_filter));
        }

        icon.setImageResource(iconResId);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.var_id_spinner_item);
        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        selected = findViewById(R.id.imgv_selected);
    }

    public void a(boolean notSelected, int color, int var3) {
        if (notSelected) {
            if (!isDropDown) color = var3;
            name.setTextColor(color);
            name.setTypeface(null, Typeface.NORMAL);
        } else {
            if (!isDropDown) color = var3;
            name.setTextColor(color);
            name.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    public void setDropDown(boolean isDropDown) {
        this.isDropDown = isDropDown;
    }

    public void setTextSize(int textSize) {
    }
}