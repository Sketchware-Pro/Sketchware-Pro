package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;

import pro.sketchware.R;

public class IconCalendarView extends IconBase {
    public IconCalendarView(Context context) {
        super(context);
    }

    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.ic_mtrl_calendar);
        setWidgetName("CalendarView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 15;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        layoutBean.width = -1;
        layoutBean.height = -1;
        viewBean.convert = "CalendarView";
        return viewBean;
    }
}
