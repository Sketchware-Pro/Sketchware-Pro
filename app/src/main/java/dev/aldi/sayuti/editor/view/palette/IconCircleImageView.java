package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconCircleImageView extends IconBase {

    private String resourceName = "";

    public IconCircleImageView(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_camera);
        setWidgetName("CircleImageView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW;
        viewBean.convert = "de.hdodenhof.circleimageview.CircleImageView";
        viewBean.inject = "app:civ_border_width=\"3dp\"\napp:civ_border_color=\"#008DCD\"\napp:civ_circle_background_color=\"#FFFFFF\"\napp:civ_border_overlay=\"true\"";
        viewBean.image.resName = resourceName;
        return viewBean;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
