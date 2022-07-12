package com.besome.sketch.editor.logic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.sketchware.remod.R;

import java.util.HashMap;

import a.a.a.Vs;
import a.a.a.Ws;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PaletteSelector extends LinearLayout implements View.OnClickListener {

    private Context context;
    private Vs onBlockCategorySelectListener;

    public PaletteSelector(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteSelector(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setPadding(
                (int) wB.a(context, 8f),
                (int) wB.a(context, 4f),
                (int) wB.a(context, 8f),
                (int) wB.a(context, 4f)
        );
        initializePalettes();
    }

    private void unselectAllPalettes() {
        for (int i = 0; i < getChildCount(); i++) {
            Ws childAt = (Ws) getChildAt(i);
            if (childAt != null) {
                childAt.setSelected(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Ws) {
            unselectAllPalettes();
            Ws paletteView = (Ws) v;
            paletteView.setSelected(true);
            onBlockCategorySelectListener.a(paletteView.getId(), paletteView.getColor());
        }
    }

    public void setOnBlockCategorySelectListener(Vs listener) {
        onBlockCategorySelectListener = listener;
    }

    private void initializePalettes() {
        addPalette(0, Helper.getResString(R.string.block_category_var),
                0xffee7d16);
        addPalette(1, Helper.getResString(R.string.block_category_list),
                0xffcc5b22);
        addPalette(2, Helper.getResString(R.string.block_category_control),
                0xffe1a92a);
        addPalette(3, Helper.getResString(R.string.block_category_operator),
                0xff5cb722);
        addPalette(4, Helper.getResString(R.string.block_category_math),
                0xff23b9a9);
        addPalette(5, Helper.getResString(R.string.block_category_file),
                0xffa1887f);
        addPalette(6, Helper.getResString(R.string.block_category_view_func),
                0xff4a6cd4);
        addPalette(7, Helper.getResString(R.string.block_category_component_func),
                0xff2ca5e2);
        addPalette(8, Helper.getResString(R.string.block_category_moreblock),
                0xff8a55d7);

        for (HashMap<String, Object> palette : new mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector().getPaletteSelector()) {
            addPalette(
                    Integer.parseInt(palette.get("index").toString()),
                    palette.get("text").toString(),
                    Integer.parseInt(palette.get("color").toString())
            );
        }
    }

    private void addPalette(int id, String title, int color) {
        Ws paletteView = new Ws(context, id, title, color);
        paletteView.setTag(String.valueOf(id));
        paletteView.setOnClickListener(this);
        addView(paletteView);
        if (id == 0) {
            paletteView.setSelected(true);
        }
    }
}
