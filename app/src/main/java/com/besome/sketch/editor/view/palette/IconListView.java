package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconListView extends IconBase {
    public IconListView(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_list);
        setWidgetName("ListView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 9;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.text = getName();
        viewBean.convert = "ListView";
        return viewBean;
    }
}
