package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconBadgeView extends IconBase {

    public IconBadgeView(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_badge_view);
        setWidgetName("BadgeView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 33;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutBean.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.text.text = getName();
        viewBean.convert = "com.allenliu.badgeview.BadgeView";
        return viewBean;
    }
}
