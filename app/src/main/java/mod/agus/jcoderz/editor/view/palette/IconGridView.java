package mod.agus.jcoderz.editor.view.palette;

import android.content.Context;
import android.widget.LinearLayout;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconGridView extends IconBase {

    public IconGridView(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_grid);
        setWidgetName("GridView");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_GRIDVIEW;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = LinearLayout.HORIZONTAL;
        layoutBean.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.convert = getName();
        viewBean.inject = "android:numColumns=\"3\"\n" +
                "android:stretchMode=\"columnWidth\"";
        return viewBean;
    }
}
