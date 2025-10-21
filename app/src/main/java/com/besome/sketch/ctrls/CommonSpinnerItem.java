package com.besome.sketch.ctrls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import pro.sketchware.databinding.CommonSpinnerItemBinding;

public class CommonSpinnerItem extends LinearLayout {

    private final CommonSpinnerItemBinding binding;

    public CommonSpinnerItem(Context context) {
        super(context);
        binding = CommonSpinnerItemBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void a(String name, boolean isVisible) {
        binding.tvSpnName.setText(name);
        if (isVisible) {
            binding.imgvSelected.setVisibility(View.VISIBLE);
        } else {
            binding.imgvSelected.setVisibility(View.GONE);
        }

    }

    public void setTextSize(int textSize) {
    }
}
