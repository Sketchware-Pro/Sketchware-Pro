package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconCollapsingToolbar extends IconBase {
    public IconCollapsingToolbar(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconCollapsingToolbar.super.a(context);
        setWidgetImage(2131166351);
        setWidgetName("CollapsingToolbar");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 37;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "com.google.android.material.appbar.CollapsingToolbarLayout";
        viewBean.inject = "android:fitsSystemWindows=\"true\"\napp:layout_scrollFlags=\"scroll|exitUntilCollapsed\"";
        return viewBean;
    }
}
