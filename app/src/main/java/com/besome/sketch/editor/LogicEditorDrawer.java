package com.besome.sketch.editor;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import a.a.a.Us;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.AppSettings;
import pro.sketchware.R;

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

        MaterialCardView tools = findViewById(R.id.new_button);
        tools.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AppSettings.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getContext().startActivity(intent);
        });

        TextInputEditText searchInput = findViewById(R.id.et_search);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    public void a() {
        favorite.removeAllViews();
    }

    public View a(String str, ArrayList<BlockBean> arrayList) {
        Us collectionBlock = null;
        if (!arrayList.isEmpty()) {
            BlockBean blockBean = arrayList.get(0);
            collectionBlock = new Us(getContext(), blockBean.type, blockBean.typeName, blockBean.opCode, str, arrayList);
            favorite.addView(collectionBlock);
            View view = new View(getContext());
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    1,
                    (int) getDip(8)));
            favorite.addView(view);
        }

        return collectionBlock;
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

    private void filter(String query) {
        String lowerQuery = query.toLowerCase().trim();
        for (int i = 0; i < favorite.getChildCount(); i++) {
            View child = favorite.getChildAt(i);
            if (child instanceof Us) {
                Us item = (Us) child;
                boolean match = item.T != null && item.T.toLowerCase().contains(lowerQuery);
                item.setVisibility(match ? View.VISIBLE : View.GONE);
                
                if (i + 1 < favorite.getChildCount()) {
                     View divider = favorite.getChildAt(i + 1);
                     if (!(divider instanceof Us)) {
                         divider.setVisibility(match ? View.VISIBLE : View.GONE);
                     }
                }
            }
        }
    }
}
