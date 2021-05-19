package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconBottomNavigationView extends IconBase {
    public IconBottomNavigationView(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconBottomNavigationView.super.a(context);
        setWidgetImage(2131166314);
        setWidgetName("BottomNavigationView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 32;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 0;
        layoutBean.paddingTop = 0;
        layoutBean.paddingRight = 0;
        layoutBean.paddingBottom = 0;
        viewBean.text.text = getName();
        viewBean.convert = "com.google.android.material.bottomnavigation.BottomNavigationView";
        return viewBean;
    }
}
