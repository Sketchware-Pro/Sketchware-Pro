package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconBottomNavigationView extends IconBase {

    public IconBottomNavigationView(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_bottom_view);
        setWidgetName("BottomNavigationView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 32;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.paddingLeft = 0;
        layoutBean.paddingTop = 0;
        layoutBean.paddingRight = 0;
        layoutBean.paddingBottom = 0;
        viewBean.text.text = getName();
        viewBean.convert = "com.google.android.material.bottomnavigation.BottomNavigationView";
        return viewBean;
    }
}
