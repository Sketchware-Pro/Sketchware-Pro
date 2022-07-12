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
import mod.hey.studios.util.Helper;

public class PropertyColorItem extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private String key;
    private int value;
    private TextView tvName;
    private TextView tvValue;
    private View viewColor;
    private ImageView imgLeftIcon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;

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
            tvName.setText(Helper.getResString(identifier));
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(R.drawable.color_palette_48);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            imgLeftIcon.setImageResource(R.drawable.color_palette_48);
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
    public void onClick(View v) {
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
        boolean colorNoneAvailable;
        boolean colorTransparentAvailable;
        View view = wB.a(context, R.layout.color_picker);
        view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.abc_fade_in));
        if (key.equals("property_background_color")) {
            colorTransparentAvailable = true;
            colorNoneAvailable = true;
        } else {
            colorTransparentAvailable = false;
            colorNoneAvailable = false;
        }
        Zx colorPicker = new Zx(view, (Activity) context, value, colorTransparentAvailable, colorNoneAvailable);
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
