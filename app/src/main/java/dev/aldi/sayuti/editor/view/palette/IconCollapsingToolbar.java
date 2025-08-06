package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconCollapsingToolbar extends IconBase {

    public IconCollapsingToolbar(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_collapsing_toolbar);
        setWidgetName("CollapsingToolbar");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT;
        viewBean.layout.orientation = VERTICAL;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.convert = "com.google.android.material.appbar.CollapsingToolbarLayout";
        viewBean.inject = "android:fitsSystemWindows=\"true\"\napp:layout_scrollFlags=\"scroll|exitUntilCollapsed\"";
        return viewBean;
    }
}
