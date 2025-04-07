package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyPopupSelectorSingleBinding;

@SuppressLint("ViewConstructor")
public class PropertyCustomViewItem extends RelativeLayout implements View.OnClickListener {

    private String key = "";
    private String value = "";
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private int f;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw propertyValueChangeListener;
    private ArrayList<ProjectFileBean> customViews;

    public PropertyCustomViewItem(Context context, boolean idk) {
        super(context);
        a(idk);
    }

    private RadioButton a(String fileName) {
        RadioButton radioButton = new RadioButton(getContext());
        radioButton.setText(fileName);
        radioButton.setTag(fileName);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (wB.a(getContext(), 1.0F) * 40.0F));
        radioButton.setGravity(19);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    private void a() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getText(tvName));
        dialog.setIcon(f);
        PropertyPopupSelectorSingleBinding propertyBinding = PropertyPopupSelectorSingleBinding.inflate(((Activity) getContext()).getLayoutInflater());
        RadioGroup rgContent = propertyBinding.rgContent;
        rgContent.addView(a("none"));

        for (ProjectFileBean projectFileBean : customViews) {
            RadioButton var4 = a(projectFileBean.fileName);
            propertyBinding.rgContent.addView(var4);
        }

        ((RadioButton) rgContent.getChildAt(0)).setChecked(true);

        for (int i = 0, childCount = rgContent.getChildCount(); i < childCount; i++) {
            RadioButton radioButton = (RadioButton) rgContent.getChildAt(i);
            if (radioButton.getTag().toString().equals(value)) {
                radioButton.setChecked(true);
            }
        }

        dialog.setView(propertyBinding.getRoot());
        dialog.setPositiveButton(xB.b().a(getContext(), R.string.common_word_select), (v, which) -> {
            for (int i = 0, childCount = rgContent.getChildCount(); i < childCount; i++) {
                RadioButton radioButton = (RadioButton) rgContent.getChildAt(i);

                if (radioButton.isChecked()) {
                    setValue(radioButton.getTag().toString());
                }
            }
            if (propertyValueChangeListener != null) {
                propertyValueChangeListener.a(key, value);
            }
            v.dismiss();
        });
        dialog.setNegativeButton(xB.b().a(getContext(), R.string.common_word_cancel), null);
        dialog.show();
    }

    private void a(boolean var2) {
        wB.a(getContext(), this, R.layout.property_selector_item);
        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        propertyItem = findViewById(R.id.property_item);
        propertyMenuItem = findViewById(R.id.property_menu_item);
        imgLeftIcon = findViewById(R.id.img_left_icon);
        if (var2) {
            setOnClickListener(this);
            setSoundEffectsEnabled(true);
        }

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        int var2 = getResources().getIdentifier(key, "string", getContext().getPackageName());
        if (var2 > 0) {
            tvName.setText(xB.b().a(getResources(), var2));
            f = R.drawable.ic_mtrl_interface;
            if (propertyMenuItem.getVisibility() == View.VISIBLE) {
                ImageView var3 = findViewById(R.id.img_icon);
                TextView var4 = findViewById(R.id.tv_title);
                var3.setImageResource(f);
                var4.setText(xB.b().a(getContext(), var2));
            } else {
                imgLeftIcon.setImageResource(f);
            }
        }

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (TextUtils.isEmpty(value)) {
            value = "none";
        }
        this.value = value;
        tvValue.setText(value);
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            if ("property_custom_view_listview".equals(key)) {
                a();
            }
        }
    }

    public void setCustomView(ArrayList<ProjectFileBean> customView) {
        customViews = customView;
    }

    public void setOnPropertyValueChangeListener(Kw var1) {
        propertyValueChangeListener = var1;
    }

    public void setOrientationItem(int orientationItem) {
        if (orientationItem == 0) {
            propertyItem.setVisibility(View.GONE);
            propertyMenuItem.setVisibility(View.VISIBLE);
        } else {
            propertyItem.setVisibility(View.VISIBLE);
            propertyMenuItem.setVisibility(View.GONE);
        }

    }
}
