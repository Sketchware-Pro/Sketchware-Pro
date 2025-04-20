package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.lib.validator.MinMaxInputValidator;

@SuppressLint("ViewConstructor")
public class PropertySizeItem extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private String key = "";
    private int value = 1;
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private int icon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;

    public PropertySizeItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String str) {
        key = str;
        int identifier = getResources().getIdentifier(str, "string", getContext().getPackageName());
        if (identifier > 0) {
            tvName.setText(Helper.getResString(identifier));
            icon = R.drawable.ic_mtrl_expand;
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
            } else {
                imgLeftIcon.setImageResource(icon);
            }
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        TextView textView = tvValue;
        textView.setText(this.value + " dp");
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (key.equals("property_divider_height")) {
                showDialog();
            }
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
        wB.a(context, this, R.layout.property_input_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setSoundEffectsEnabled(true);
            setOnClickListener(this);
        }
    }

    private void showDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getText(tvName));
        dialog.setIcon(icon);
        View view = wB.a(getContext(), R.layout.property_popup_input_size);
        EditText input = view.findViewById(R.id.et_input);
        MinMaxInputValidator validator = new MinMaxInputValidator(context, view.findViewById(R.id.ti_input), 0, 999);
        validator.a(String.valueOf(value));
        dialog.setView(view);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (v, which) -> {
            if (validator.b()) {
                setValue(Integer.parseInt(Helper.getText(input)));
                if (valueChangeListener != null) {
                    valueChangeListener.a(key, value);
                }
                v.dismiss();
            }
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }
}
