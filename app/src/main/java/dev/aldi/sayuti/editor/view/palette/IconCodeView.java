package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconCodeView extends IconBase {

    public IconCodeView(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_code_view);
        setWidgetName("CodeView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 47;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = VERTICAL;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = "br.tiagohm.codeview.CodeView";
        return viewBean;
    }
}
