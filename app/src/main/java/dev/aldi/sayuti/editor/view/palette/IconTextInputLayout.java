package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconTextInputLayout extends IconBase {

    public IconTextInputLayout(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_edit_text);
        setWidgetName("TextInputLayout");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 38;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = VERTICAL;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.paddingLeft = 0;
        layoutBean.paddingTop = 0;
        layoutBean.paddingRight = 0;
        layoutBean.paddingBottom = 0;
        viewBean.convert = "com.google.android.material.textfield.TextInputLayout";
        viewBean.inject = "style=\"@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox\"";
        return viewBean;
    }
}
