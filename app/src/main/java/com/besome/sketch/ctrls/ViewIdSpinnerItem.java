package com.besome.sketch.ctrls;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import pro.sketchware.R;
import pro.sketchware.databinding.VarIdSpinnerItemBinding;

public class ViewIdSpinnerItem extends LinearLayout {

    private boolean isDropDown;

    private final VarIdSpinnerItemBinding binding;

    public ViewIdSpinnerItem(Context context) {
        super(context);
        binding = VarIdSpinnerItemBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void a(int iconResId, String text, boolean isSelected) {
        if (isSelected) {
            binding.imgvSelected.setVisibility(View.VISIBLE);
        } else {
            binding.imgvSelected.setVisibility(View.GONE);
        }

        if (text.charAt(0) == '_') {
            binding.name.setText(text.substring(1));
            a(false, 0xffff5555, 0xfff8f820);
        } else {
            binding.name.setText(text);
            a(true, getResources().getColor(R.color.view_property_spinner_filter), getResources().getColor(R.color.view_property_spinner_filter));
        }

        binding.icon.setImageResource(iconResId);
    }

    public void a(boolean notSelected, int color, int var3) {
        if (notSelected) {
            if (!isDropDown) color = var3;
            binding.name.setTextColor(color);
            binding.name.setTypeface(null, Typeface.NORMAL);
        } else {
            if (!isDropDown) color = var3;
            binding.name.setTextColor(color);
            binding.name.setTypeface(null, Typeface.BOLD_ITALIC);
        }
    }

    public void setDropDown(boolean isDropDown) {
        this.isDropDown = isDropDown;
    }

    public void setTextSize(int textSize) {
    }
}