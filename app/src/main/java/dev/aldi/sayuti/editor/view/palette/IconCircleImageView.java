package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconCircleImageView extends IconBase {
    public String f = "";

    public IconCircleImageView(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconCircleImageView.super.a(context);
        setWidgetImage(2131166354);
        setWidgetName("CircleImageView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 43;
        viewBean.convert = "de.hdodenhof.circleimageview.CircleImageView";
        viewBean.inject = "app:civ_border_width=\"3dp\"\napp:civ_border_color=\"#008DCD\"\napp:civ_circle_background_color=\"#FFFFFF\"\napp:civ_border_overlay=\"true\"";
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
