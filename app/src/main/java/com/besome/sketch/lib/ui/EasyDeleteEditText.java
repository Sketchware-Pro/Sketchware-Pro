package com.besome.sketch.lib.ui;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.View$OnClickListener;
import android.view.View$OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;

import a.a.a.VA;
import a.a.a.WA;
import a.a.a.wB;
import a.a.a.XA;

public class EasyDeleteEditText extends RelativeLayout {
    public Context a;
    public ImageView b;
    public EditText c;
    public TextInputLayout d;
    
    public EasyDeleteEditText(final Context context) {
        super(context);
        this.a(context);
    }
    
    public EasyDeleteEditText(final Context context, final AttributeSet set) {
        super(context, set);
        this.a(context);
    }
    
    public EasyDeleteEditText(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.a(context);
    }
    
    public final void a() {
        if (this.c.isEnabled() && this.c.hasFocus() && this.c.length() > 0) {
            this.b.setVisibility(0);
        }
        else {
            this.b.setVisibility(8);
        }
    }
    
    public final void a(final Context a) {
        wB.a(this.a = a, (ViewGroup)this, 2131427412);
        this.b = (ImageView)this.findViewById(2131231132);
        this.c = (EditText)this.findViewById(2131230983);
        this.d = (TextInputLayout)this.findViewById(2131230984);
        this.b.setVisibility(8);
        this.b.setOnClickListener((View$OnClickListener)new VA(this));
        this.c.addTextChangedListener((TextWatcher)new WA(this));
        this.c.setOnFocusChangeListener((View$OnFocusChangeListener)new XA(this));
        this.a();
    }
    
    public EditText getEditText() {
        return this.c;
    }
    
    public TextInputLayout getTextInputLayout() {
        return this.d;
    }
    
    public void setHint(final String hint) {
        this.d.setHint((CharSequence)hint);
    }
}
