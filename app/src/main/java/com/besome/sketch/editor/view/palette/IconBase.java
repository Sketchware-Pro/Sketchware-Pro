package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseWidget;
import com.sketchware.remod.R;

public class IconBase extends BaseWidget {
    public String e;

    public IconBase(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.icon_bg);
    }

    public ViewBean getBean() {
        return null;
    }

    public String getName() {
        return this.e;
    }

    public void setName(String string) {
        this.e = string;
    }

    public void setText(String string) {
        this.b.setTextSize(2, 11.0f);
        this.setWidgetName(string);
    }
}

