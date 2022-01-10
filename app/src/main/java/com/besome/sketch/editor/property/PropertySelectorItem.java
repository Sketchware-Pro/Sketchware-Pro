package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.Kw;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PropertySelectorItem extends RelativeLayout implements View.OnClickListener {

    public String key = "";
    public int value = -1;
    public TextView tvName;
    public TextView tvValue;
    public ImageView imgLeftIcon;
    public int icon;
    public View propertyItem;
    public View propertyMenuItem;
    public ViewGroup radioGroupContent;
    public Kw valueChangeListener;

    public PropertySelectorItem(Context context, boolean z) {
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
            switch (this.key) {
                case "property_orientation":
                    icon = Resources.drawable.grid_3_48;
                    break;

                case "property_text_style":
                    icon = Resources.drawable.abc_96_color;
                    break;

                case "property_text_size":
                    icon = Resources.drawable.text_width_96;
                    break;

                case "property_ime_option":
                case "property_input_type":
                    icon = Resources.drawable.keyboard_48;
                    break;

                case "property_spinner_mode":
                    icon = Resources.drawable.pull_down_48;
                    break;

                case "property_choice_mode":
                    icon = Resources.drawable.multiple_choice_48;
                    break;

                case "property_first_day_of_week":
                    icon = Resources.drawable.monday_48;
                    break;
            }
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(Resources.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(Resources.id.tv_title)).setText(xB.b().a(getContext(), identifier));
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
        tvValue.setText(sq.a(key, value));
    }

    public void onClick(View v) {
        if (!mB.a()) {
            switch (key) {
                case "property_orientation":
                case "property_text_style":
                case "property_text_size":
                case "property_ime_option":
                case "property_input_type":
                case "property_spinner_mode":
                case "property_choice_mode":
                case "property_first_day_of_week":
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
            return;
        }
        propertyItem.setVisibility(VISIBLE);
        propertyMenuItem.setVisibility(GONE);
    }

    private void initialize(Context context, boolean z) {
        wB.a(context, this, Resources.layout.property_selector_item);
        tvName = findViewById(Resources.id.tv_name);
        tvValue = findViewById(Resources.id.tv_value);
        imgLeftIcon = findViewById(Resources.id.img_left_icon);
        propertyItem = findViewById(Resources.id.property_item);
        propertyMenuItem = findViewById(Resources.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    private void showDialog() {
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(icon);
        View view = wB.a(getContext(), Resources.layout.property_popup_selector_single);
        radioGroupContent = view.findViewById(Resources.id.rg_content);
        TextView desc = view.findViewById(Resources.id.desc);
        if (key.equals("property_ime_option")) {
            desc.setText(xB.b().a(getContext(), Resources.string.property_description_edittext_ime_options));
            desc.setVisibility(VISIBLE);
        } else {
            desc.setVisibility(GONE);
        }
        for (Pair<Integer, String> pair : sq.a(key)) {
            radioGroupContent.addView(a(pair));
        }
        for (int i = 0; radioGroupContent.getChildCount() > i; i++) {
            RadioButton radioButton = (RadioButton) radioGroupContent.getChildAt(i);
            if (Integer.parseInt(radioButton.getTag().toString()) == value) {
                radioButton.setChecked(true);
            }
        }
        dialog.a(view);
        dialog.b(xB.b().a(getContext(), Resources.string.common_word_select), new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; radioGroupContent.getChildCount() > i; i++) {
                    RadioButton radioButton = (RadioButton) radioGroupContent.getChildAt(i);
                    if (radioButton.isChecked()) {
                        setValue(Integer.parseInt(radioButton.getTag().toString()));
                    }
                }
                if (valueChangeListener != null) {
                    valueChangeListener.a(key, value);
                }
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private RadioButton a(Pair<Integer, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (wB.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
