package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ComponentBean;

import pro.sketchware.R;

public class IconBottomSheet extends IconBase {
    public IconBottomSheet(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        setWidgetImage(R.drawable.ic_mtrl_bottom_navigation);
        setWidgetName("Bottom Sheet");
    }

    @Override
    public Object getBean() {
        ComponentBean componentBean = new ComponentBean(ComponentBean.COMPONENT_TYPE_BOTTOM_SHEET);
        return componentBean;
    }
}
