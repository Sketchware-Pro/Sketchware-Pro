package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconDatePicker extends IconBase {
    public IconDatePicker(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        IconDatePicker.super.a(context);
        setWidgetImage(2131165519);
        setWidgetName("DatePicker");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 27;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        layoutBean.width = -1;
        viewBean.convert = getName();
        return viewBean;
    }
}
