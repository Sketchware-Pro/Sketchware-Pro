package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconSwipeRefreshLayout extends IconBase {
    public IconSwipeRefreshLayout(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconSwipeRefreshLayout.super.a(context);
        setWidgetImage(2131166320);
        setWidgetName("SwipeRefreshLayout");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 39;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -1;
        layoutBean.height = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "androidx.swiperefreshlayout.widget.SwipeRefreshLayout";
        return viewBean;
    }
}
