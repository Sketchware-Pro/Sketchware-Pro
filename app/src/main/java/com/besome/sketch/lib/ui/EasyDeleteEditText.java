package com.besome.sketch.lib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import a.a.a.wB;
import mod.hasrat.lib.BaseTextWatcher;

public class EasyDeleteEditText extends RelativeLayout {

    public Context a;
    public ImageView b;
    public EditText c;
    public TextInputLayout d;

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
        if (c.isEnabled() && c.hasFocus() && c.length() > 0) {
            b.setVisibility(View.VISIBLE);
        } else {
            b.setVisibility(View.GONE);
        }

    }

    private void initialize(Context context) {
        a = context;
        wB.a(context, this, R.layout.easy_delete_edittext);
        b = findViewById(R.id.img_delete);
        c = findViewById(R.id.easy_ed_input);
        d = findViewById(R.id.easy_ti_input);
        b.setVisibility(View.GONE);
        b.setOnClickListener(view -> c.setText(""));
        c.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a();
            }
        });
        c.setOnFocusChangeListener((v, hasFocus) -> a());
        a();
    }

    public EditText getEditText() {
        return c;
    }

    public TextInputLayout getTextInputLayout() {
        return d;
    }

    public void setHint(String txt) {
        d.setHint(txt);
    }
}
