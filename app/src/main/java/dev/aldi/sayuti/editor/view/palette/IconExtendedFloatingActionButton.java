package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import pro.sketchware.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class IconExtendedFloatingActionButton extends IconBase {

    public IconExtendedFloatingActionButton(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_fab);
        setWidgetName("ExtendedFloatingActionButton");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_EXTENDEDFLOATINGACTIONBUTTON;
        viewBean.layout.orientation = VERTICAL;
        viewBean.layout.width = ViewGroup.LayoutParams.MATCH_PARENT;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.text.text = "ExtendedFloatingActionButton";
        viewBean.convert = "com.google.android.material.floatingactionbutton.FloatingActionButton";
        viewBean.inject = "app:icon=\"@drawable/default_image\"\napp:iconTint=\"#FFFFFF\"\napp:iconSize=\"24dp\"";
        return viewBean;
    }
}

