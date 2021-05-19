package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconCodeView extends IconBase {
    public IconCodeView(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconCodeView.super.a(context);
        setWidgetImage(2131166357);
        setWidgetName("CodeView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 47;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -1;
        layoutBean.height = -2;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "br.tiagohm.codeview.CodeView";
        return viewBean;
    }
}
