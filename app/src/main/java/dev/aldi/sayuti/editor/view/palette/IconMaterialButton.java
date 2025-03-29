package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import pro.sketchware.R;

import mod.agus.jcoderz.beans.ViewBeans;

public class IconMaterialButton extends IconBase {

    public IconMaterialButton(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_button_click);
        setWidgetName("MaterialButton");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_MATERIALBUTTON;
        viewBean.layout.orientation = VERTICAL;
        viewBean.layout.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.layout.gravity = Gravity.CENTER;
        viewBean.layout.paddingLeft = 8;
        viewBean.layout.paddingTop = 8;
        viewBean.layout.paddingRight = 8;
        viewBean.layout.paddingBottom = 8;
        viewBean.text.textColor = Color.WHITE;
        viewBean.text.text = getName();
        viewBean.convert = "com.google.android.material.button.MaterialButton";
        viewBean.inject = "app:cornerRadius=\"8dp\"";
        return viewBean;
    }
}
