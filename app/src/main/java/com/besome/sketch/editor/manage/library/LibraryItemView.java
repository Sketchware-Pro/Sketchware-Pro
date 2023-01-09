package com.besome.sketch.editor.manage.library;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.sketchware.remod.R;

import a.a.a.wB;
import a.a.a.xB;

public class LibraryItemView extends CardView {
    private final Context context;
    private ImageView icon;
    private TextView title;
    private TextView description;
    private TextView enabled;

    public LibraryItemView(Context context) {
        super(context);
        this.context = context;
    }

    public void a(int resLayout) {
        wB.a(context, this, resLayout);
        icon = findViewById(R.id.lib_icon);
        title = findViewById(R.id.lib_title);
        description = findViewById(R.id.lib_desc);
        enabled = findViewById(R.id.tv_enable);
        LayoutParams layoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(context, 4f);
        layoutParams.bottomMargin = (int) wB.a(context, 4f);
        setLayoutParams(layoutParams);
    }

    public void setData(@Nullable ProjectLibraryBean projectLibraryBean) {
        @DrawableRes
        int iconId;
        CharSequence titleContent;
        CharSequence descriptionContent;
        boolean enabledChecked;
        CharSequence enabledLabel;

        if (projectLibraryBean != null) {
            iconId = ProjectLibraryBean.getLibraryIcon(projectLibraryBean.libType);
            titleContent = xB.b().a(getContext(), ProjectLibraryBean.getLibraryResName(projectLibraryBean.libType));
            descriptionContent = xB.b().a(getContext(), ProjectLibraryBean.getLibraryResDesc(projectLibraryBean.libType));
            enabledChecked = ProjectLibraryBean.LIB_USE_Y.equals(projectLibraryBean.useYn);
        } else {
            iconId = R.drawable.ic_detail_setting_48dp;
            titleContent = "(Advanced) Exclude built-in libraries";
            descriptionContent = "Use custom Library versions";
            enabledChecked = false;
        }
        enabledLabel = enabledChecked ? "ON" : "OFF";

        icon.setImageResource(iconId);
        title.setText(titleContent);
        description.setText(descriptionContent);
        enabled.setText(enabledLabel);
        enabled.setSelected(enabledChecked);
    }
}
