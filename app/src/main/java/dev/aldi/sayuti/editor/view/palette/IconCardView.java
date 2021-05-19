package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconCardView extends IconBase {
    public IconCardView(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconCardView.super.a(context);
        setWidgetImage(2131166299);
        setWidgetName("CardView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 36;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "androidx.cardview.widget.CardView";
        viewBean.inject = "app:cardElevation=\"10dp\"\napp:cardCornerRadius=\"20dp\"";
        return viewBean;
    }
}
