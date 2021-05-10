package com.besome.sketch.editor;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.besome.sketch.shared.blocks.SharedBlocksListActivity;

import java.util.ArrayList;

import a.a.a.Us;
import a.a.a.wB;
import a.a.a.xB;
import mod.hilal.saif.activities.tools.Tools;

public class LogicEditorDrawer extends LinearLayout {

    /* renamed from: a  reason: collision with root package name */
    public LinearLayout a;
    public CustomScrollView b;
    public CardView c;
    public CardView ccc;

    public LogicEditorDrawer(Context context) {
        super(context);
        a(context);
    }

    public LogicEditorDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final void b() {
        Intent intent = new Intent(getContext(), SharedBlocksListActivity.class);
        intent.setFlags(536870912);
        ((LogicEditorActivity) getContext()).startActivityForResult(intent, 463);
    }

    public final void bbb() {
        Intent intent = new Intent(getContext(), Tools.class);
        intent.setFlags(536870912);
        ((LogicEditorActivity) getContext()).startActivityForResult(intent, 463);
    }

    public void setDragEnabled(boolean z) {
        if (z) {
            this.b.b();
        } else {
            this.b.a();
        }
    }

    public final void a(Context context) {
        wB.a(context, this, 2131427492);
        ((TextView) findViewById(2131231892)).setText(xB.b().a(getContext(), 2131625519));
        this.a = (LinearLayout) findViewById(2131231347);
        this.b = (CustomScrollView) findViewById(2131231695);
        this.c = (CardView) findViewById(2131230951);
        this.c.setOnClickListener(v -> {
            b();
        });
        this.ccc = (CardView) findViewById(2131232616);
        this.ccc.setOnClickListener(v -> {
            bbb();
        });
        ((TextView) findViewById(2131232158)).setText(xB.b().a(getContext(), 2131626238));
    }

    public void a() {
        this.a.removeAllViews();
    }

    public View a(String str, ArrayList<BlockBean> arrayList) {
        if (arrayList.size() <= 0) {
            return null;
        }
        BlockBean blockBean = arrayList.get(0);
        Us us = new Us(getContext(), blockBean.type, blockBean.typeName, blockBean.opCode, str, arrayList);
        this.a.addView(us);
        View view = new View(getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(1, (int) wB.a(getContext(), 8.0f)));
        this.a.addView(view);
        return us;
    }

    public void a(String str) {
        for (int i = 0; i < this.a.getChildCount(); i++) {
            View childAt = this.a.getChildAt(i);
            if ((childAt instanceof Us) && ((Us) childAt).T.equals(str)) {
                this.a.removeViewAt(i + 1);
                this.a.removeViewAt(i);
            }
        }
    }
}