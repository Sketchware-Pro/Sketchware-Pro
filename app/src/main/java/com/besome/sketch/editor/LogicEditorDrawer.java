package com.besome.sketch.editor;

import static mod.SketchwareUtil.getDip;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.Us;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.Tools;

public class LogicEditorDrawer extends LinearLayout {

    private LinearLayout favorite;
    private CustomScrollView scrollView;

    public LogicEditorDrawer(Context context) {
        super(context);
        initialize(context);
    }

    public LogicEditorDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public void setDragEnabled(boolean dragEnabled) {
        if (dragEnabled) {
            scrollView.b();
        } else {
            scrollView.a();
        }
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.logic_editor_drawer);
        ((TextView) findViewById(R.id.tv_block_collection)).setText(Helper.getResString(R.string.logic_editor_title_block_collection));
        favorite = findViewById(R.id.layout_favorite);
        scrollView = findViewById(R.id.scv);

        CardView tools = findViewById(R.id.new_button);
        tools.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Tools.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ((LogicEditorActivity) getContext()).startActivityForResult(intent, 463);
        });
    }

    public void a() {
        favorite.removeAllViews();
    }

    public View a(String str, ArrayList<BlockBean> arrayList) {
        if (arrayList.size() <= 0) {
            return null;
        }

        BlockBean blockBean = arrayList.get(0);
        Us us = new Us(getContext(), blockBean.type, blockBean.typeName, blockBean.opCode, str, arrayList);
        favorite.addView(us);
        View view = new View(getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(
                1,
                (int) getDip(8)));
        favorite.addView(view);
        return us;
    }

    public void a(String str) {
        for (int i = 0; i < favorite.getChildCount(); i++) {
            View childAt = favorite.getChildAt(i);
            if ((childAt instanceof Us) && ((Us) childAt).T.equals(str)) {
                favorite.removeViewAt(i + 1);
                favorite.removeViewAt(i);
            }
        }
    }
}
