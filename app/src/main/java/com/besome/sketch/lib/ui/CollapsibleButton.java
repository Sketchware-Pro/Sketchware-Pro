package com.besome.sketch.lib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class CollapsibleButton extends LinearLayout {

    private int id;
    private ImageView icon;
    private TextView label;

    public CollapsibleButton(Context context) {
        this(context, null);
    }

    public CollapsibleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public static CollapsibleButton create(Context context, int id, @DrawableRes int icon, @StringRes int label) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(context);
        collapsibleButton.id = id;
        collapsibleButton.icon.setImageResource(icon);
        collapsibleButton.label.setText(Helper.getResString(collapsibleButton, label));
        return collapsibleButton;
    }

    private void initialize(Context context) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        setLayoutParams(layoutParams);
        wB.a(context, this, R.layout.fr_logic_list_item_button);
        icon = findViewById(R.id.icon);
        label = findViewById(R.id.name);
    }

    public int getButtonId() {
        return id;
    }

    public TextView getLabel() {
        return label;
    }

    public ImageView getIcon() {
        return icon;
    }
}
