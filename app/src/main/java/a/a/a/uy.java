package a.a.a;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.besome.sketch.lib.base.BaseWidget;

import java.util.ArrayList;

public class uy extends IconBase {
    public String widgetName;
    public ArrayList<ViewBean> viewBeans;

    public uy(Context context, String widgetName, ArrayList<ViewBean> viewBeans) {
        super(context);
        this.widgetName = widgetName;
        this.viewBeans = viewBeans;
        a();
    }

    public final void a() {
        tv_widget.setTextSize(2, 11.0f);
        setWidgetName(widgetName);
        setWidgetImage(ViewBean.getViewTypeResId(viewBeans.get(0).type));
    }

    public ViewBean getBean() {
        return viewBeans.get(0);
    }

    public ArrayList<ViewBean> getData() {
        return viewBeans;
    }

    public String getName() {
        return widgetName;
    }
}

