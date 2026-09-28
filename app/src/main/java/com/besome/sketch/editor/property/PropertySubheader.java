package com.besome.sketch.editor.property;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pro.sketchware.databinding.PropertySubheaderBinding;

public class PropertySubheader extends RelativeLayout {

    public PropertySubheaderBinding binding;
    private ImageView imgAdd;
    private TextView tvName;

    public PropertySubheader(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        binding = PropertySubheaderBinding.inflate(LayoutInflater.from(context), this, true);
        tvName = binding.tvName;
        imgAdd = binding.imgAdd;
    }

    public void setHeaderName(String str) {
        tvName.setText(str);
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        imgAdd.setVisibility(VISIBLE);
        imgAdd.setOnClickListener(onClickListener);
    }
}
