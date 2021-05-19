package com.besome.sketch.editor.view.palette;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.design.DesignActivity;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.besome.sketch.shared.views.SharedViewsListActivity;

import java.util.ArrayList;

import a.a.a.uy;
import a.a.a.wB;
import a.a.a.xB;

public class PaletteFavorite extends LinearLayout implements View.OnClickListener {
    public LinearLayout a;
    public CustomScrollView b;
    public CardView c;

    public PaletteFavorite(Context context) {
        super(context);
        a(context);
    }

    public PaletteFavorite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final void a(Context context) {
        wB.a(context, this, 2131427607);
        this.a = (LinearLayout) findViewById(2131232333);
        this.b = (CustomScrollView) findViewById(2131231695);
        this.c = findViewById(2131230952);
        this.c.setOnClickListener(this);
        ((TextView) findViewById(2131232162)).setText(xB.b().a(getContext(), 2131626360));
    }

    public final void b() {
        Intent intent = new Intent(getContext(), SharedViewsListActivity.class);
        intent.setFlags(536870912);
        ((DesignActivity) getContext()).startActivityForResult(intent, 462);
    }

    public void onClick(View view) {
        if (view.getId() == 2131230952) {
            b();
        }
    }

    public void setScrollEnabled(boolean z) {
        if (z) {
            this.b.b();
        } else {
            this.b.a();
        }
    }

    public void a() {
        this.a.removeAllViews();
    }

    public View a(String str, ArrayList<ViewBean> arrayList) {
        View uyVar = new uy(getContext(), str, arrayList);
        this.a.addView(uyVar);
        return uyVar;
    }
}
