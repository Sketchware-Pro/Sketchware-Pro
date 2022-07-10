package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconTimePicker extends IconBase {
    public IconTimePicker(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        IconTimePicker.super.a(context);
        setWidgetImage(2131166276);
        setWidgetName("TimePicker");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 28;
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
