package com.besome.sketch.lib.ui;

import a.a.a.VA;
import a.a.a.WA;
import a.a.a.XA;
import a.a.a.wB;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.material.textfield.TextInputLayout;

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
        wB.a(context, this, 2131427412);
        b = findViewById(2131231132);
        c = findViewById(2131230983);
        d = findViewById(2131230984);
        b.setVisibility(View.GONE);
        b.setOnClickListener(new VA(this));
        c.addTextChangedListener(new WA(this));
        c.setOnFocusChangeListener(new XA(this));
        a();
    }

    public EditText getEditText() {
        return c;
    }

    public TextInputLayout getTextInputLayout() {
        return d;
    }

    public void setHint(String var1) {
        d.setHint(var1);
    }
}
