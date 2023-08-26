package a.a.a;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.besome.sketch.lib.base.BaseWidget;

import java.util.ArrayList;

public class uy extends IconBase {
    public String f;
    public ArrayList<ViewBean> g;

    public uy(Context context, String widgetName, ArrayList<ViewBean> viewBeans) {
        super(context);
        f = widgetName;
        g = viewBeans;
        a();
    }

    public final void a() {
        tv_widget.setTextSize(2, 11.0f);
        setWidgetName(f);
        setWidgetImage(ViewBean.getViewTypeResId(g.get(0).type));
    }

    public ViewBean getBean() {
        return g.get(0);
    }

    public ArrayList<ViewBean> getData() {
        return g;
    }

    public String getName() {
        return f;
    }
}

