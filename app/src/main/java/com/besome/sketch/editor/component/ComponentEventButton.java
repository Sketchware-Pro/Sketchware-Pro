package com.besome.sketch.editor.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.color.MaterialColors;

import pro.sketchware.databinding.FrLogicListItemComponentEventBinding;

public class ComponentEventButton extends LinearLayout {

    private final FrLogicListItemComponentEventBinding binding;

    public ComponentEventButton(Context context) {
        this(context, null);
    }

    public ComponentEventButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        binding = FrLogicListItemComponentEventBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void onEventAdded() {
        binding.addEvent.setVisibility(GONE);
        binding.icon.setColorFilter(MaterialColors.getColor(binding.name, com.google.android.material.R.attr.colorOnSurface));
        binding.name.setTextColor(MaterialColors.getColor(binding.name, com.google.android.material.R.attr.colorOnSurface));
    }

    public void setClickListener(View.OnClickListener onClickListener) {
        binding.container.setOnClickListener(onClickListener);
    }

    public void onEventAvailableToAdd() {
        binding.addEvent.setVisibility(VISIBLE);
        binding.icon.setColorFilter(MaterialColors.getColor(binding.name, com.google.android.material.R.attr.colorOutline));
        binding.name.setTextColor(MaterialColors.getColor(binding.name, com.google.android.material.R.attr.colorOutline));
    }

    public ImageView getIcon() {
        return binding.icon;
    }

    public TextView getName() {
        return binding.name;
    }
}
