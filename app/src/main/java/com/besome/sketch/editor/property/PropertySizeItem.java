package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.Kw;
import a.a.a.mB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyInputItemBinding;
import pro.sketchware.databinding.PropertyPopupInputSizeBinding;
import pro.sketchware.lib.validator.MinMaxInputValidator;

@SuppressLint("ViewConstructor")
public class PropertySizeItem extends RelativeLayout implements View.OnClickListener {

    public PropertyInputItemBinding binding;
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
                binding.propertyMenuItem.imgIcon.setImageResource(icon);
                binding.propertyMenuItem.tvTitle.setText(Helper.getResString(identifier));
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
            propertyItem.setOnClickListener(null);
            propertyMenuItem.setOnClickListener(this);
        } else {
            propertyItem.setVisibility(VISIBLE);
            propertyMenuItem.setVisibility(GONE);
            propertyItem.setOnClickListener(this);
            propertyMenuItem.setOnClickListener(null);
        }
    }

    private void initialize(Context context, boolean z) {
        this.context = context;
        binding = PropertyInputItemBinding.inflate(LayoutInflater.from(context), this, true);
        tvName = binding.tvName;
        tvValue = binding.tvValue;
        imgLeftIcon = binding.imgLeftIcon;
        propertyItem = binding.propertyItem;
        propertyMenuItem = binding.propertyMenuItem.getRoot();
//        if (z) {
//            propertyMenuItem.setSoundEffectsEnabled(true);
//            propertyMenuItem.setOnClickListener(this);
//        }
    }

    private void showDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getText(tvName));
        dialog.setIcon(icon);
        PropertyPopupInputSizeBinding popupBinding = PropertyPopupInputSizeBinding.inflate(LayoutInflater.from(getContext()));
        EditText input = popupBinding.etInput;
        MinMaxInputValidator validator = new MinMaxInputValidator(context, popupBinding.tiInput, 0, 999);
        validator.a(String.valueOf(value));
        dialog.setView(popupBinding.getRoot());
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
