package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconViewPager extends IconBase {
    public IconViewPager(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconViewPager.super.a(context);
        setWidgetImage(2131166352);
        setWidgetName("ViewPager");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 31;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.height = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.text = getName();
        viewBean.convert = "androidx.viewpager.widget.ViewPager";
        return viewBean;
    }
}
