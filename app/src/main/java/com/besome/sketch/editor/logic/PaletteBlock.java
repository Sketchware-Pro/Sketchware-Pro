package com.besome.sketch.editor.logic;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.lib.ui.CustomHorizontalScrollView;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.sketchware.remod.R;

import a.a.a.Rs;
import a.a.a.Ts;
import a.a.a.wB;
import mod.hey.studios.moreblock.MoreblockActionListener;

public class PaletteBlock extends LinearLayout {

    private Context a;
    private CustomScrollView customScrollView;
    private CustomHorizontalScrollView horizontalScrollView;
    private LinearLayout blockBuilder;
    public float f = 0.0F;
    private MoreblockActionListener moreblockActionListener;

    public PaletteBlock(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteBlock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void setMoreblockActionListener(MoreblockActionListener moreblockActionListener) {
        this.moreblockActionListener = moreblockActionListener;
    }

    public Ts a(String spec, String type, String opCode) {
        return a(spec, type, "", opCode);
    }

    public Ts a(String spec, String type, String typeName, String opCode) {
        View view = new View(a);
        view.setLayoutParams(new LayoutParams(-1, (int) (f * 8.0F)));
        blockBuilder.addView(view);

        Rs blockView = new Rs(a, -1, spec, type, typeName, opCode);
        blockView.setBlockType(1);

        if ("definedFunc".equals(opCode)) {
            LinearLayout container = new LinearLayout(a);
            container.setLayoutParams(new LayoutParams(-1, -2));
            container.setOrientation(LinearLayout.HORIZONTAL);
            container.setGravity(Gravity.CENTER_VERTICAL);
            blockBuilder.addView(container);

            ImageView igGoto = new ImageView(a);
            igGoto.setImageResource(R.drawable.ic_goto);
            LayoutParams igGotoLayoutParams = new LayoutParams(32, 32);
            igGotoLayoutParams.setMargins((int) f * 8, 0, 0, (int) f * 2);
            igGoto.setLayoutParams(igGotoLayoutParams);

            if (moreblockActionListener != null) {
                igGoto.setOnClickListener(v -> moreblockActionListener.onGotoMoreblock(spec, type, typeName));
            }

            container.addView(blockView);
            container.addView(igGoto);
        } else {
            blockBuilder.addView(blockView);
        }

        return blockView;
    }

    public TextView a(String title) {
        TextView textView = new TextView(a);
        textView.setLayoutParams(new LayoutParams(-1, (int) (f * 30.0F)));
        textView.setBackgroundResource(R.drawable.bg_feed);
        textView.setText(title);
        textView.setTextSize(10.0F);
        textView.setGravity(17);
        textView.setPadding((int) (f * 8.0F), 0, (int) (f * 8.0F), 0);
        blockBuilder.addView(textView);
        return textView;
    }

    public void a() {
        blockBuilder.removeAllViews();
    }

    private void initialize(Context context) {
        a = context;
        wB.a(context, this, R.layout.palette_block);
        PaletteSelector paletteSelector = findViewById(R.id.palette_selector);
        customScrollView = findViewById(R.id.scv);
        horizontalScrollView = findViewById(R.id.hscv);
        blockBuilder = findViewById(R.id.block_builder);
        f = wB.a(a, 1.0F);
    }

    public void a(String title, int color) {
        TextView textView = new TextView(a);
        LayoutParams layoutParams = new LayoutParams(-1, (int) (f * 16.0F));
        layoutParams.topMargin = (int) (f * 16.0F);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundColor(color);
        textView.setText(title);
        textView.setTextColor(-1);
        textView.setTextSize(9.0F);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(19);
        textView.setPadding((int) (f * 12.0F), 0, (int) (f * 12.0F), 0);
        blockBuilder.addView(textView);
    }

    public void setDragEnabled(boolean dragEnabled) {
        if (dragEnabled) {
            customScrollView.b();
            horizontalScrollView.b();
        } else {
            customScrollView.a();
            horizontalScrollView.a();
        }
    }

    public void setMinWidth(int minWidth) {
        customScrollView.setMinimumWidth(minWidth - (int) (f * 5.0F));
        horizontalScrollView.setMinimumWidth(minWidth - (int) (f * 5.0F));
        getLayoutParams().width = minWidth;
    }

    public void setUseScroll(boolean useScroll) {
        customScrollView.setUseScroll(useScroll);
        horizontalScrollView.setUseScroll(useScroll);
    }
}
