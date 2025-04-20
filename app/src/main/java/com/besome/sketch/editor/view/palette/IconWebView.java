package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconWebView extends IconBase {
    public IconWebView(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_web);
        setWidgetName("WebView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 7;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 0;
        layoutBean.paddingTop = 0;
        layoutBean.paddingRight = 0;
        layoutBean.paddingBottom = 0;
        layoutBean.width = -1;
        layoutBean.height = -1;
        viewBean.convert = "WebView";
        return viewBean;
    }
}
