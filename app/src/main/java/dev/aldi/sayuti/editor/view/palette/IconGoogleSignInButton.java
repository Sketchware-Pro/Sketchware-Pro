package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class IconGoogleSignInButton extends IconBase {

    public IconGoogleSignInButton(Context context) {
        super(context);
        setWidgetImage(R.drawable.ic_mtrl_login);
        setWidgetName("SignInButton");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = ViewBeans.VIEW_TYPE_WIDGET_SIGNINBUTTON;
        viewBean.layout.orientation = VERTICAL;
        viewBean.layout.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewBean.layout.paddingLeft = 0;
        viewBean.layout.paddingTop = 0;
        viewBean.layout.paddingRight = 0;
        viewBean.layout.paddingBottom = 0;
        viewBean.convert = "com.google.android.gms.common.SignInButton";
        return viewBean;
    }
}
