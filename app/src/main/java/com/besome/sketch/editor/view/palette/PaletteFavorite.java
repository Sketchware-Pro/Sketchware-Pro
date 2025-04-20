package com.besome.sketch.editor.view.palette;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.ui.CustomScrollView;

import java.util.ArrayList;

import a.a.a.uy;
import a.a.a.wB;
import pro.sketchware.R;

public class PaletteFavorite extends LinearLayout {
    private LinearLayout collectionWidgets;
    private CustomScrollView scrollView;

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
        collectionWidgets.addView(favoriteWidget);
        return favoriteWidget;
    }

    public void a() {
        collectionWidgets.removeAllViews();
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.palette_favorite);
        collectionWidgets = findViewById(R.id.widget);
        scrollView = findViewById(R.id.scv);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        if (scrollEnabled) {
            scrollView.b();
        } else {
            scrollView.a();
        }
    }
}
