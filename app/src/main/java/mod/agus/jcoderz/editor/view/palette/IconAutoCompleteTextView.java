package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconAutoCompleteTextView extends IconBase {

    public IconAutoCompleteTextView(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_edittext);
        setWidgetName("AutoCompleteTextView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.hint = getName();
        viewBean.convert = getName();
        return viewBean;
    }
}
