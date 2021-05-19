package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconWaveSideBar extends IconBase {
    public IconWaveSideBar(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconWaveSideBar.super.a(context);
        setWidgetImage(2131166312);
        setWidgetName("WaveSideBar");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 35;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -2;
        layoutBean.height = -1;
        viewBean.text.text = getName();
        viewBean.text.textSize = 16;
        viewBean.text.textColor = -16740915;
        viewBean.convert = "com.sayuti.lib.WaveSideBar";
        viewBean.inject = "app:sidebar_text_alignment=\"center\"\napp:sidebar_position=\"right\"\napp:sidebar_max_offset=\"80dp\"";
        return viewBean;
    }
}
