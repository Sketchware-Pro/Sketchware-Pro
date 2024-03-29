package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.sketchware.remod.R;

public class IconLinearVertical extends IconBase {
    public IconLinearVertical(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_linear_vertical);
        setWidgetName("Linear(V)");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 0;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.height = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "LinearLayout";
        return viewBean;
    }
}
