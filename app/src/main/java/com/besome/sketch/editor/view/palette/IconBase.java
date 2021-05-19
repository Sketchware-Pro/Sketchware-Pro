package com.besome.sketch.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.lib.base.BaseWidget;

public class IconBase extends BaseWidget {
    public String e;

    public IconBase(Context context) {
        super(context);
        setBackgroundResource(2131165891);
    }

    public ViewBean getBean() {
        return null;
    }

    public String getName() {
        return this.e;
    }

    public void setName(String str) {
        this.e = str;
    }

    public void setText(String str) {
        this.b.setTextSize(2, 11.0f);
        setWidgetName(str);
    }
}
