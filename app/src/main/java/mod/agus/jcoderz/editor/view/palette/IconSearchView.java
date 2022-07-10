package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconSearchView extends IconBase {
    public IconSearchView(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        IconSearchView.super.a(context);
        setWidgetImage(2131165849);
        setWidgetName("SearchView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 22;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.hint = getName();
        viewBean.text.hintColor = -10453621;
        viewBean.convert = getName();
        return viewBean;
    }
}
