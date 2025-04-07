package com.besome.sketch.editor.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.color.MaterialColors;

import a.a.a.wB;
import pro.sketchware.R;

public class ComponentEventButton extends LinearLayout {
    private LinearLayout container;
    private ImageView icon;
    private ImageView addEvent;
    private TextView name;

    public ComponentEventButton(Context context) {
        this(context, null);
    }

    public ComponentEventButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.fr_logic_list_item_component_event);
        container = findViewById(R.id.container);
        addEvent = findViewById(R.id.add_event);
        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
    }

    public void onEventAdded() {
        addEvent.setVisibility(GONE);
        icon.setColorFilter(MaterialColors.getColor(name, com.google.android.material.R.attr.colorSecondary));

    }

    public void setClickListener(View.OnClickListener onClickListener) {
        container.setOnClickListener(onClickListener);
    }

    public void onEventAvailableToAdd() {
        addEvent.setVisibility(VISIBLE);
        icon.setColorFilter(MaterialColors.getColor(name, com.google.android.material.R.attr.colorOutlineVariant));

    }

    public ImageView getIcon() {
        return icon;
    }

    public TextView getName() {
        return name;
    }
}
