package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseWidget;

import pro.sketchware.R;

public class IconBase extends BaseWidget {
    private String name;

    public IconBase(Context context) {
        super(context);
        setBackgroundResource(R.drawable.icon_bg);
    }

    public ViewBean getBean() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String widgetName) {
        setWidgetNameTextSize(11);
        setWidgetName(widgetName);
    }
}
