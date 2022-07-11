package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconCollapsingToolbar extends IconBase {

    public IconCollapsingToolbar(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_collapsing_toolbar);
        setWidgetName("CollapsingToolbar");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 37;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = VERTICAL;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "com.google.android.material.appbar.CollapsingToolbarLayout";
        viewBean.inject = "android:fitsSystemWindows=\"true\"\napp:layout_scrollFlags=\"scroll|exitUntilCollapsed\"";
        return viewBean;
    }
}
