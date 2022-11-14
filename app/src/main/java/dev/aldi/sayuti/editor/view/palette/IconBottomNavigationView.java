package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class IconBottomNavigationView extends IconBase {

    public IconBottomNavigationView(Context context) {
        super(context);
        setWidgetImage(R.drawable.widget_bottom_view);
        setWidgetName("BottomNavigationView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.paddingLeft = 0;
        viewBean.layout.paddingTop = 0;
        viewBean.layout.paddingRight = 0;
        viewBean.layout.paddingBottom = 0;
        viewBean.text.text = getName();
        viewBean.convert = "com.google.android.material.bottomnavigation.BottomNavigationView";
        return viewBean;
    }
}
