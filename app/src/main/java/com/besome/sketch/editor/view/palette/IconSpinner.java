package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconSpinner extends IconBase {
    public IconSpinner(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_spinner);
        setWidgetName("Spinner");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.type = 10;
        layoutBean.width = -1;
        layoutBean.height = -2;
        viewBean.convert = "Spinner";
        return viewBean;
    }
}
