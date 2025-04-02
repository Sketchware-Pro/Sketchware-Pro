package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

@SuppressLint("ViewConstructor")
public class PropertyStringPairSelectorItem extends RelativeLayout implements View.OnClickListener {

    private String key = "";
    private String value = "";
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private int icon;
    private View propertyItem;
    private View propertyMenuItem;
    private ViewGroup radioGroupContent;
    private Kw valueChangeListener;

    public PropertyStringPairSelectorItem(Context context, boolean z) {
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
            if (key.equals("property_progressbar_style")) {
                icon = R.drawable.ic_mtrl_style;
            }
            if (propertyMenuItem.getVisibility() == VISIBLE) {
                ((ImageView) findViewById(R.id.img_icon)).setImageResource(icon);
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
                return;
            }
            imgLeftIcon.setImageResource(icon);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        tvValue.setText(value);
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            showDialog();
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
        wB.a(context, this, R.layout.property_selector_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        if (z) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }
    }

    private void showDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getText(tvName));
        dialog.setIcon(icon);
        View view = wB.a(getContext(), R.layout.property_popup_selector_single);
        radioGroupContent = view.findViewById(R.id.rg_content);
        int counter = 0;
        for (Pair<String, String> pair : sq.b(key)) {
            radioGroupContent.addView(getOption(pair));
        }
        int childCount = radioGroupContent.getChildCount();
        while (true) {
            if (counter >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) radioGroupContent.getChildAt(counter);
            if (radioButton.getTag().toString().equals(value)) {
                radioButton.setChecked(true);
                break;
            }
            counter++;
        }
        dialog.setView(view);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_select), (v, which) -> {
            int childCount1 = radioGroupContent.getChildCount();
            int counter1 = 0;
            while (true) {
                if (counter1 >= childCount1) {
                    break;
                }
                RadioButton radioButton = (RadioButton) radioGroupContent.getChildAt(counter1);
                if (radioButton.isChecked()) {
                    setValue(radioButton.getTag().toString());
                    break;
                }
                counter1++;
            }
            if (valueChangeListener != null) {
                valueChangeListener.a(key, value);
            }
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private RadioButton getOption(Pair<String, String> pair) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(pair.second);
        radioButton.setTag(pair.first);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (wB.a(getContext(), 1.0f) * 40.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }
}
