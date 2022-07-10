package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconAutoCompleteTextView extends IconBase {
    public IconAutoCompleteTextView(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        IconAutoCompleteTextView.super.a(context);
        setWidgetImage(2131166242);
        setWidgetName("AutoCompleteTextView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 23;
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
