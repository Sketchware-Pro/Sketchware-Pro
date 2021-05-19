package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;

public class IconImageView extends IconBase {
    public String f = "";

    public IconImageView(Context context) {
        super(context);
    }

    @Override // com.besome.sketch.lib.base.BaseWidget
    public void a(Context context) {
        super.a(context);
        setWidgetImage(2131166253);
        setWidgetName("ImageView");
    }

    @Override // com.besome.sketch.editor.view.palette.IconBase
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 6;
        viewBean.convert = "ImageView";
        viewBean.image.resName = this.f;
        return viewBean;
    }

    public String getResourceName() {
        return this.f;
    }

    public void setResourceName(String str) {
        this.f = str;
    }
}
