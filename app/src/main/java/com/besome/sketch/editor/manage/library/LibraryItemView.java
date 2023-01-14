package com.besome.sketch.editor.manage.library;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.sketchware.remod.R;

import a.a.a.wB;
import a.a.a.xB;

public class LibraryItemView extends CardView {
    protected final Context context;
    protected ImageView icon;
    protected TextView title;
    protected TextView description;
    protected TextView enabled;

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
        layoutParams.leftMargin = (int) wB.a(context, 8f);
        layoutParams.topMargin = (int) wB.a(context, 4f);
        layoutParams.bottomMargin = (int) wB.a(context, 4f);
        layoutParams.rightMargin = (int) wB.a(context, 8f);
        setLayoutParams(layoutParams);
    }

    public void setData(ProjectLibraryBean projectLibraryBean) {
        boolean enabledChecked = ProjectLibraryBean.LIB_USE_Y.equals(projectLibraryBean.useYn);
        CharSequence enabledLabel = enabledChecked ? "ON" : "OFF";

        icon.setImageResource(ProjectLibraryBean.getLibraryIcon(projectLibraryBean.libType));
        title.setText(xB.b().a(getContext(), ProjectLibraryBean.getLibraryResName(projectLibraryBean.libType)));
        description.setText(xB.b().a(getContext(), ProjectLibraryBean.getLibraryResDesc(projectLibraryBean.libType)));
        enabled.setText(enabledLabel);
        enabled.setSelected(enabledChecked);
    }
}
