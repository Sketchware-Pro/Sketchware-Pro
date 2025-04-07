package com.besome.sketch.lib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;

import pro.sketchware.databinding.EasyDeleteEdittextBinding;
import pro.sketchware.lib.base.BaseTextWatcher;

public class EasyDeleteEditText extends RelativeLayout {

    private EasyDeleteEdittextBinding binding;

    public EasyDeleteEditText(Context context) {
        super(context);
        initialize(context);
    }

    public EasyDeleteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public EasyDeleteEditText(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        initialize(context);
    }

    private void a() {
        if (binding.easyEdInput.isEnabled() && binding.easyEdInput.hasFocus() && binding.easyEdInput.length() > 0) {
            binding.imgDelete.setVisibility(View.VISIBLE);
        } else {
            binding.imgDelete.setVisibility(View.GONE);
        }

    }

    private void initialize(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = EasyDeleteEdittextBinding.inflate(inflater, this, true);
        binding.imgDelete.setVisibility(View.GONE);
        binding.imgDelete.setOnClickListener(view -> binding.easyEdInput.setText(""));
        binding.easyEdInput.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a();
            }
        });
        binding.easyEdInput.setOnFocusChangeListener((v, hasFocus) -> a());
        a();
    }

    public EditText getEditText() {
        return binding.easyEdInput;
    }

    public TextInputLayout getTextInputLayout() {
        return binding.easyTiInput;
    }

    public void setHint(String txt) {
        binding.easyTiInput.setHint(txt);
    }
}
