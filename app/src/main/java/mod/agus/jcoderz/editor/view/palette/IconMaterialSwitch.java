package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import pro.sketchware.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class IconMaterialSwitch extends IconBase {

    public IconMaterialSwitch(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_toggle);
        setWidgetName("MaterialSwitch");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_MATERIALSWITCH;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        layoutBean.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.convert = getName();
        return viewBean;
    }
}
