package com.besome.sketch.editor.manage.library;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.divider.MaterialDivider;

import a.a.a.wB;
import pro.sketchware.R;
import pro.sketchware.utility.ThemeUtils;

public class LibraryItemView extends FrameLayout {
    public final ViewGroup container;
    public final ImageView icon;
    public final TextView title;
    public final TextView description;
    public final TextView enabled;
    public final MaterialDivider divider;
    protected final Context context;

    public LibraryItemView(Context context) {
        super(context);
        this.context = context;

        wB.a(context, this, R.layout.manage_library_base_item);
        container = findViewById(R.id.container);
        icon = findViewById(R.id.lib_icon);
        title = findViewById(R.id.lib_title);
        description = findViewById(R.id.lib_desc);
        enabled = findViewById(R.id.tv_enable);
        divider = findViewById(R.id.divider);

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        int dividerColor = ThemeUtils.isDarkThemeEnabled(context) ? R.attr.colorSurfaceContainerHighest : R.attr.colorOutlineVariant;
        divider.setDividerColor(ThemeUtils.getColor(context, dividerColor));
    }

    public void setData(ProjectLibraryBean projectLibraryBean) {
        boolean enabledChecked = ProjectLibraryBean.LIB_USE_Y.equals(projectLibraryBean.useYn);
        CharSequence enabledLabel = enabledChecked ? "ON" : "OFF";

        icon.setImageResource(ProjectLibraryBean.getLibraryIcon(projectLibraryBean.libType));
        title.setText(ProjectLibraryBean.getLibraryResName(projectLibraryBean.libType));
        description.setText(ProjectLibraryBean.getLibraryResDesc(projectLibraryBean.libType));
        enabled.setText(enabledLabel);
        enabled.setSelected(enabledChecked);
    }

    public void setHideEnabled() {
        enabled.setVisibility(View.GONE);
    }

    public void showDivider(boolean showDivider) {
        divider.setVisibility(showDivider ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        container.setOnClickListener(listener);
    }

    @Override
    public Object getTag() {
        return container.getTag();
    }

    @Override
    public void setTag(Object tag) {
        container.setTag(tag);
    }
}
