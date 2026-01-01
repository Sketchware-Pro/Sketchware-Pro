package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ComponentBean;

import pro.sketchware.R;

public class IconCustomDialog extends IconBase {
    public IconCustomDialog(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        setWidgetImage(R.drawable.widget_alertdialog);
        setWidgetName("Custom Dialog");
    }

    @Override
    public ComponentBean getBean() {
        ComponentBean componentBean = new ComponentBean(ComponentBean.COMPONENT_TYPE_CUSTOM_DIALOG);
        return componentBean;
    }
}
