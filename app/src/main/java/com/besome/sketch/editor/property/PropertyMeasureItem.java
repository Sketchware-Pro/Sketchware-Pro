package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.Kw;
import a.a.a.mB;
import a.a.a.sq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyPopupMeasurementBinding;
import pro.sketchware.databinding.PropertySelectorItemBinding;
import pro.sketchware.lib.validator.MinMaxInputValidator;

@SuppressLint("ViewConstructor")
public class PropertyMeasureItem extends RelativeLayout implements View.OnClickListener {

    public PropertySelectorItemBinding binding;
    private String key = "";
    private int measureValue = -1;
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;
    private boolean isWrapContent = true;
    private boolean isCustomValue = true;
    private int imgLeftIconDrawableResId;

    public PropertyMeasureItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void setIcon(ImageView imageView) {
        if (key.equals("property_layout_width")) {
            imgLeftIconDrawableResId = R.drawable.ic_mtrl_width;
        } else if (key.equals("property_layout_height")) {
            imgLeftIconDrawableResId = R.drawable.ic_mtrl_height;
        }
        imageView.setImageResource(imgLeftIconDrawableResId);
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
                setIcon(binding.propertyMenuItem.imgIcon);
                binding.propertyMenuItem.tvTitle.setText(Helper.getResString(identifier));
                return;
            }
            setIcon(imgLeftIcon);
        }
    }

    public int getValue() {
        return measureValue;
    }

    public void setValue(int value) {
        measureValue = value;
        if (!isWrapContent && value == LayoutParams.WRAP_CONTENT) {
            tvValue.setText(sq.a(key, LayoutParams.MATCH_PARENT));
        } else if (isCustomValue || value < 0) {
            tvValue.setText(sq.a(key, value));
        } else {
            tvValue.setText(sq.a(key, LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            showDialog();
        }
    }

    public void setItemEnabled(int itemEnabled) {
        boolean isMatchParent = (itemEnabled & 1) == 1;
        isWrapContent = (itemEnabled & 2) == 2;
        isCustomValue = (itemEnabled & 4) == 4;
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
        binding = PropertySelectorItemBinding.inflate(LayoutInflater.from(context), this, true);
        tvName = binding.tvName;
        tvValue = binding.tvValue;
        imgLeftIcon = binding.imgLeftIcon;
        propertyItem = binding.propertyItem;
        propertyMenuItem = binding.propertyMenuItem.getRoot();
//        if (z) {
//            propertyMenuItem.setOnClickListener(this);
//            propertyMenuItem.setSoundEffectsEnabled(true);
//        }
    }

    private void showDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(Helper.getText(tvName));
        dialog.setIcon(imgLeftIconDrawableResId);

        PropertyPopupMeasurementBinding popupBinding = PropertyPopupMeasurementBinding.inflate(LayoutInflater.from(getContext()));
        popupBinding.tiInput.setHint(String.format(Helper.getResString(R.string.property_enter_value), Helper.getText(tvName)));

        MinMaxInputValidator minMaxInputValidator = new MinMaxInputValidator(getContext(), popupBinding.tiInput, 0, 999);

        popupBinding.rgWidthHeight.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_directinput) {
                popupBinding.directInput.setVisibility(VISIBLE);
                minMaxInputValidator.a(Helper.getText(popupBinding.edInput));
            } else {
                popupBinding.directInput.setVisibility(GONE);
            }
        });
        popupBinding.rgWidthHeight.clearCheck();
        if (measureValue >= 0) {
            if (isCustomValue) {
                popupBinding.rgWidthHeight.check(R.id.rb_directinput);
                minMaxInputValidator.a(String.valueOf(measureValue));
                popupBinding.directInput.setVisibility(VISIBLE);
            } else {
                popupBinding.rgWidthHeight.check(R.id.rb_wrapcontent);
            }
        } else if (measureValue == LayoutParams.MATCH_PARENT) {
            popupBinding.rgWidthHeight.check(R.id.rb_matchparent);
        } else if (isWrapContent) {
            popupBinding.rgWidthHeight.check(R.id.rb_wrapcontent);
        } else {
            popupBinding.rgWidthHeight.check(R.id.rb_matchparent);
        }

        popupBinding.tvInputDp.setVisibility(View.GONE);
        popupBinding.tiInput.setSuffixText("dp");

        dialog.setView(popupBinding.getRoot());
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_select), (v, which) -> {
            int checkedRadioButtonId = popupBinding.rgWidthHeight.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.rb_matchparent) {
                setValue(LayoutParams.MATCH_PARENT);
            } else if (checkedRadioButtonId == R.id.rb_wrapcontent) {
                setValue(LayoutParams.WRAP_CONTENT);
            } else if (minMaxInputValidator.b()) {
                setValue(Integer.parseInt(Helper.getText(popupBinding.edInput)));
            } else {
                return;
            }
            if (valueChangeListener != null) {
                valueChangeListener.a(key, measureValue);
            }
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }
}
