package com.besome.sketch.editor.view.palette;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.ui.CustomScrollView;

import java.util.ArrayList;

import a.a.a.uy;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PaletteFavorite extends LinearLayout {
    public LinearLayout a;
    public CustomScrollView b;
    public CardView c;

    public PaletteFavorite(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteFavorite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    public View a(String name, ArrayList<ViewBean> viewBeans) {
        uy favoriteWidget = new uy(getContext(), name, viewBeans);
        a.addView(favoriteWidget);
        return favoriteWidget;
    }

    public void a() {
        a.removeAllViews();
    }

    private void initialize(Context context) {
        wB.a(context, this, 2131427607);
        a = findViewById(2131232333);
        b = findViewById(2131231695);
        ((TextView) findViewById(2131232162)).setText(Helper.getResString(2131626360));
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        if (scrollEnabled) {
            b.b();
        } else {
            b.a();
        }
    }
}