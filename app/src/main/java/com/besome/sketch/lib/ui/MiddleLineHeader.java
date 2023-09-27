package com.besome.sketch.lib.ui;

import android.graphics.Typeface;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import a.a.a.wB;

import com.sketchware.remod.R;

public class MiddleLineHeader extends LinearLayout {
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
        a(1, 16, getContext().getResources().getColor(R.color.scolor_blue_01));
    }
    
    public void a(final int n, final int n2, final int textColor) {
        b.setTypeface((Typeface)null, n);
        b.setTextSize(2, (float)n2);
        b.setTextColor(textColor);
    }
    
    public final void a(final Context context) {
        wB.a(context, this, R.layout.middle_line_header);
        a = findViewById(R.id.header_icon);
        b = findViewById(R.id.header_name);
        c = findViewById(R.id.header_line);
        a();
    }
}
