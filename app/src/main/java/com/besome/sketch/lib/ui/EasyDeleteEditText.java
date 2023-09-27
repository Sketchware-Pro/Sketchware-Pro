package com.besome.sketch.lib.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import a.a.a.wB;

public class EasyDeleteEditText extends RelativeLayout {
    public Context a;
    public ImageView b;
    public EditText c;
    public TextInputLayout d;
    
    public EasyDeleteEditText(final Context context) {
        super(context);
        a(context);
    }
    
    public EasyDeleteEditText(final Context context, final AttributeSet set) {
        super(context, set);
        a(context);
    }
    
    public EasyDeleteEditText(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        a(context);
    }
    
    public final void a() {
        if (c.isEnabled() && c.hasFocus() && c.length() > 0) {
            b.setVisibility(View.VISIBLE);
        }
        else {
            b.setVisibility(View.GONE);
        }
    }
    
    public final void a(final Context a) {
        wB.a(this.a = a, this, R.layout.easy_delete_edittext);
        b = findViewById(R.id.img_delete);
        c = findViewById(R.id.easy_ed_input);
        d = findViewById(R.id.easy_ti_input);
        b.setVisibility(View.GONE);
        b.setOnClickListener((view) -> {
            c.setText("");
        });
        c.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(final Editable editable) {}
            
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {}
            
            @Override
            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                a();
            }
        });
        c.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View view, final boolean b) {
                a();
            }
        });
        a();
    }
    
    public EditText getEditText() {
        return c;
    }
    
    public TextInputLayout getTextInputLayout() {
        return d;
    }
    
    public void setHint(final String hint) {
        d.setHint(hint);
    }
}
