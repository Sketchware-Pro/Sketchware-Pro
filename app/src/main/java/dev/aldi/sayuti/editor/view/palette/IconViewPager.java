package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconViewPager extends IconBase {

    public IconViewPager(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_viewpager);
        setWidgetName("ViewPager");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_LAYOUT_VIEWPAGER;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.height = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.text.text = getName();
        viewBean.convert = "androidx.viewpager.widget.ViewPager";
        return viewBean;
    }
}
