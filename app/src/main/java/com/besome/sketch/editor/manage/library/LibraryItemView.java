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

    public void setData(ProjectLibraryBean projectLibraryBean) {
        icon.setImageResource(ProjectLibraryBean.getLibraryIcon(projectLibraryBean.libType));
        title.setText(xB.b().a(getContext(), ProjectLibraryBean.getLibraryResName(projectLibraryBean.libType)));
        description.setText(xB.b().a(getContext(), ProjectLibraryBean.getLibraryResDesc(projectLibraryBean.libType)));
        enabled.setText("Y".equals(projectLibraryBean.useYn) ? "ON" : "OFF");
        enabled.setSelected("Y".equals(projectLibraryBean.useYn));
    }
}
