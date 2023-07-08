package com.besome.sketch.editor.component;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;

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
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(1);
        icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        container.setOnClickListener(onClickListener);
    }

    public void onEventAvailableToAdd() {
        addEvent.setVisibility(VISIBLE);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        icon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public ImageView getIcon() {
        return icon;
    }

    public TextView getName() {
        return name;
    }
}
