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
        initialize(context);
    }
    
    public MiddleLineHeader(final Context context, final AttributeSet set) {
        super(context, set);
        initialize(context);
    }
    
    public void updateTextViewAppearance(final int typeface, final int fontSize, final int textColor) {
        b.setTypeface((Typeface)null, typeface);
        b.setTextSize(2, (float)fontSize);
        b.setTextColor(textColor);
    }
    
    public final void initialize(final Context context) {
        wB.a(context, this, R.layout.middle_line_header);
        a = findViewById(R.id.header_icon);
        b = findViewById(R.id.header_name);
        c = findViewById(R.id.header_line);
        updateTextViewAppearance(1, 16, getContext().getResources().getColor(R.color.scolor_blue_01));
    }
}
