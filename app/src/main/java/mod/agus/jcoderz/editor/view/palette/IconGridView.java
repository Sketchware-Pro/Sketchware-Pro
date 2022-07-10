package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconGridView extends IconBase {
    public IconGridView(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        IconGridView.super.a(context);
        setWidgetImage(2131165662);
        setWidgetName("GridView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 25;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 0;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = getName();
        viewBean.inject = "android:numColumns=\"3\"\nandroid:stretchMode=\"columnWidth\"";
        return viewBean;
    }
}
