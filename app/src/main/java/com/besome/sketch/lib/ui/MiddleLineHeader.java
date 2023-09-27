package com.besome.sketch.lib.ui;

import android.view.ViewGroup;
import a.a.a.wB;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MiddleLineHeader extends LinearLayout
{
    public ImageView a;
    public TextView b;
    public View c;
    
    public MiddleLineHeader(final Context context) {
        super(context);
        a(context);
    }
    
    public MiddleLineHeader(final Context context, final AttributeSet set) {
        super(context, set);
        a(context);
    }
    
    public void a() {
        a(1, 16, getContext().getResources().getColor(2131034284));
    }
    
    public void a(final int n, final int n2, final int textColor) {
        b.setTypeface((Typeface)null, n);
        b.setTextSize(2, (float)n2);
        b.setTextColor(textColor);
    }
    
    public final void a(final Context context) {
        wB.a(context, this, 2131427572);
        a = findViewById(2131231079);
        b = findViewById(2131231081);
        c = findViewById(2131231080);
        a();
    }
}
