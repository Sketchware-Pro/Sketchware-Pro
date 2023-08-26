package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseWidget;
import com.sketchware.remod.R;

public class IconBase extends BaseWidget {
    public String e;

    public IconBase(Context context) {
        super(context);
        setBackgroundResource(R.drawable.icon_bg);
    }

    public ViewBean getBean() {
        return null;
    }

    public String getName() {
        return e;
    }

    public void setName(String string) {
        e = string;
    }

    public void setText(String string) {
        b.setTextSize(2, 11.0f);
        setWidgetName(string);
    }
}

