package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class IconWaveSideBar extends IconBase {

    public IconWaveSideBar(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_wave_side_bar);
        setWidgetName("WaveSideBar");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_WAVESIDEBAR;
        viewBean.layout.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.layout.height = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.text.text = getName();
        viewBean.text.textSize = 16;
        viewBean.text.textColor = 0xff008dcd;
        viewBean.convert = "com.sayuti.lib.WaveSideBar";
        viewBean.inject = "app:sidebar_text_alignment=\"center\"\napp:sidebar_position=\"right\"\napp:sidebar_max_offset=\"80dp\"";
        return viewBean;
    }
}
