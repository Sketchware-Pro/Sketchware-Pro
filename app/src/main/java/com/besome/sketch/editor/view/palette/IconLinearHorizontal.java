package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconLinearHorizontal extends IconBase {
    public IconLinearHorizontal(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_view_horizontal);
        setWidgetName("Linear(H)");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 0;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 0;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "LinearLayout";
        return viewBean;
    }
}
