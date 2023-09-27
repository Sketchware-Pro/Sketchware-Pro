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
        this.a(context);
    }
    
    public MiddleLineHeader(final Context context, final AttributeSet set) {
        super(context, set);
        this.a(context);
    }
    
    public void a() {
        this.a(1, 16, this.getContext().getResources().getColor(2131034284));
    }
    
    public void a(final int n, final int n2, final int textColor) {
        this.b.setTypeface((Typeface)null, n);
        this.b.setTextSize(2, (float)n2);
        this.b.setTextColor(textColor);
    }
    
    public final void a(final Context context) {
        wB.a(context, (ViewGroup)this, 2131427572);
        this.a = (ImageView)this.findViewById(2131231079);
        this.b = (TextView)this.findViewById(2131231081);
        this.c = this.findViewById(2131231080);
        this.a();
    }
}
