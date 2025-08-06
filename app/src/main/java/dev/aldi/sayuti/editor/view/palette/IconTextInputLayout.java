package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconTextInputLayout extends IconBase {

    public IconTextInputLayout(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_edittext);
        setWidgetName("TextInputLayout");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT;
        viewBean.layout.orientation = VERTICAL;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.paddingLeft = 0;
        viewBean.layout.paddingTop = 0;
        viewBean.layout.paddingRight = 0;
        viewBean.layout.paddingBottom = 0;
        viewBean.convert = "com.google.android.material.textfield.TextInputLayout";
        viewBean.inject = "style=\"@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox\"";
        return viewBean;
    }
}
