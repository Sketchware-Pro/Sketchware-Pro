package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconAdView extends IconBase {
    public IconAdView(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_ad);
        setWidgetName("AdView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 17;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 0;
        layoutBean.paddingTop = 0;
        layoutBean.paddingRight = 0;
        layoutBean.paddingBottom = 0;
        layoutBean.width = -1;
        layoutBean.height = -2;
        viewBean.adSize = "SMART_BANNER";
        viewBean.adUnitId = "debug : ca-app-pub-3940256099942544/6300978111";
        viewBean.convert = "com.google.android.gms.ads.AdView";
        return viewBean;
    }
}
