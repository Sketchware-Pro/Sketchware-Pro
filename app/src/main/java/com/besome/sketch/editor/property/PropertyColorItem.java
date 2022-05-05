package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.Kw;
import a.a.a.Zx;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

public class PropertyColorItem extends RelativeLayout implements View.OnClickListener {

    public Context context;
    public String key;
    public int value;
    public TextView tvName;
    public TextView tvValue;
    public View viewColor;
    public ImageView imgLeftIcon;
    public int icon;
    public View propertyItem;
    public View propertyMenuItem;
    public Kw valueChangeListener;

    public PropertyColorItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int identifier = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(xB.b().a(getResources(), identifier));
            icon = R.drawable.color_palette_48;
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(xB.b().a(getContext(), identifier));
                return;
            }
            imgLeftIcon.setImageResource(icon);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        if (value == 0) {
            tvValue.setText("TRANSPARENT");
            viewColor.setBackgroundColor(value);
        } else if (value == 0xffffff) {
            tvValue.setText("NONE");
            viewColor.setBackgroundColor(value);
        } else {
            tvValue.setText(String.format("#%08X", value));
            viewColor.setBackgroundColor(value);
        }
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            showColorPicker();
        }
    }

    public void setOnPropertyValueChangeListener(Kw onPropertyValueChangeListener) {
        valueChangeListener = onPropertyValueChangeListener;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(GONE);
            propertyMenuItem.setVisibility(VISIBLE);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
        }
    }

    private void initialize(Context context, boolean z) {
        this.context = context;
        wB.a(context, this, R.layout.property_color_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        viewColor = findViewById(R.id.view_color);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    private void showColorPicker() {
        boolean z;
        boolean z2;
        View view = wB.a(context, R.layout.color_picker);
        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        if (key.equals("property_background_color")) {
            z2 = true;
            z = true;
        } else {
            z2 = false;
            z = false;
        }
        Zx colorPicker = new Zx(view, (Activity) context, value, z2, z);
        colorPicker.a(i -> {
            setValue(i);
            if (valueChangeListener != null) {
                valueChangeListener.a(key, value);
            }
        });
        colorPicker.setAnimationStyle(R.anim.abc_fade_in);
        colorPicker.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
