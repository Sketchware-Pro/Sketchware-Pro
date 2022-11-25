package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class IconTimePicker extends IconBase {

    public IconTimePicker(Context context) {
        super(context);
        setWidgetImage(R.drawable.widget_timer);
        setWidgetName("TimePicker");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_TIMEPICKER;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.convert = getName();
        return viewBean;
    }
}
