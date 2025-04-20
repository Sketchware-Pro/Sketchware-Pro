package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconImageView extends IconBase {
    public String f = "";

    public IconImageView(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_image);
        setWidgetName("ImageView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 6;
        viewBean.convert = "ImageView";
        viewBean.image.resName = f;
        return viewBean;
    }

    public String getResourceName() {
        return f;
    }

    public void setResourceName(String str) {
        f = str;
    }
}
