package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconCardView extends IconBase {

    public IconCardView(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_rectangle);
        setWidgetName("CardView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_LAYOUT_CARDVIEW;
        viewBean.layout.orientation = VERTICAL;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.convert = "androidx.cardview.widget.CardView";
        viewBean.inject = "app:cardElevation=\"10dp\"\napp:cardCornerRadius=\"20dp\"";
        return viewBean;
    }
}
