package com.besome.sketch.editor.property;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import a.a.a.Kw;
import a.a.a.TB;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.sq;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.PropertyPopupMeasurementBinding;

@SuppressLint("ViewConstructor")
public class PropertyMeasureItem extends RelativeLayout implements View.OnClickListener {

    private String key = "";
    private int measureValue = -1;
    private TextView tvName;
    private TextView tvValue;
    private ImageView imgLeftIcon;
    private View propertyItem;
    private View propertyMenuItem;
    private Kw valueChangeListener;
    private boolean isMatchParent = true;
    private boolean isWrapContent = true;
    private boolean isCustomValue = true;
    private int imgLeftIconDrawableResId;

    public PropertyMeasureItem(Context context, boolean z) {
        super(context);
        initialize(context, z);
    }

    private void setIcon(ImageView imageView) {
        if (key.equals("property_layout_width")) {
            imgLeftIconDrawableResId = R.drawable.width_96;
        } else if (key.equals("property_layout_height")) {
            imgLeftIconDrawableResId = R.drawable.height_96;
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
                setIcon(findViewById(R.id.img_icon));
                ((TextView) findViewById(R.id.tv_title)).setText(Helper.getResString(identifier));
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
        isMatchParent = (itemEnabled & 1) == 1;
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
        aB dialog = new aB((Activity) getContext());
        dialog.b(tvName.getText().toString());
        dialog.a(imgLeftIconDrawableResId);

        PropertyPopupMeasurementBinding binding = PropertyPopupMeasurementBinding.inflate(LayoutInflater.from(getContext()));
        binding.tiInput.setHint(String.format(Helper.getResString(R.string.property_enter_value), tvName.getText().toString()));

        TB tb = new TB(getContext(), binding.tiInput, 0, 999);

        binding.rgWidthHeight.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_directinput) {
                binding.directInput.setVisibility(VISIBLE);
                tb.a(binding.edInput.getText().toString());
            } else {
                binding.directInput.setVisibility(GONE);
            }
        });
        binding.rgWidthHeight.clearCheck();
        if (measureValue >= 0) {
            if (isCustomValue) {
                binding.rgWidthHeight.check(R.id.rb_directinput);
                tb.a(String.valueOf(measureValue));
                binding.directInput.setVisibility(VISIBLE);
            } else {
                binding.rgWidthHeight.check(R.id.rb_wrapcontent);
            }
        } else if (measureValue == LayoutParams.MATCH_PARENT) {
            binding.rgWidthHeight.check(R.id.rb_matchparent);
        } else if (isWrapContent) {
            binding.rgWidthHeight.check(R.id.rb_wrapcontent);
        } else {
            binding.rgWidthHeight.check(R.id.rb_matchparent);
        }
        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_select), v -> {
            int checkedRadioButtonId = binding.rgWidthHeight.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.rb_matchparent) {
                setValue(LayoutParams.MATCH_PARENT);
            } else if (checkedRadioButtonId == R.id.rb_wrapcontent) {
                setValue(LayoutParams.WRAP_CONTENT);
            } else if (tb.b()) {
                setValue(Integer.parseInt(binding.edInput.getText().toString()));
            } else {
                return;
            }
            if (valueChangeListener != null) {
                valueChangeListener.a(key, measureValue);
            }
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }
}
