package a.a.a;

import android.content.Context;
import android.util.TypedValue;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import java.util.ArrayList;

public class uy extends IconBase {
    private final String widgetName;
    private final ArrayList<ViewBean> viewBeans;

    public uy(Context context, String widgetName, ArrayList<ViewBean> viewBeans) {
        super(context);
        this.widgetName = widgetName;
        this.viewBeans = viewBeans;
        initialize();
    }

    private void initialize() {
        tv_widget.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        setWidgetName(widgetName);
        setWidgetImage(ViewBean.getViewTypeResId(viewBeans.get(0).type));
    }

    @Override
    public ViewBean getBean() {
        return viewBeans.get(0);
    }

    public ArrayList<ViewBean> getData() {
        return viewBeans;
    }

    @Override
    public String getName() {
        return widgetName;
    }
}
