package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconBadgeView extends IconBase {

    public IconBadgeView(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_badge);
        setWidgetName("BadgeView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_BADGEVIEW;
        viewBean.layout.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.layout.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.text.text = getName();
        viewBean.convert = "com.allenliu.badgeview.BadgeView";
        return viewBean;
    }
}
