package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconBadgeView extends IconBase {
    public IconBadgeView(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconBadgeView.super.a(context);
        setWidgetImage(2131166317);
        setWidgetName("BadgeView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 33;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -2;
        layoutBean.height = -2;
        viewBean.text.text = getName();
        viewBean.convert = "com.allenliu.badgeview.BadgeView";
        return viewBean;
    }
}
