package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconGoogleSignInButton extends IconBase {
    public IconGoogleSignInButton(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconGoogleSignInButton.super.a(context);
        setWidgetImage(2131165650);
        setWidgetName("SignInButton");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 42;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -2;
        layoutBean.paddingLeft = 0;
        layoutBean.paddingTop = 0;
        layoutBean.paddingRight = 0;
        layoutBean.paddingBottom = 0;
        viewBean.convert = "com.google.android.gms.common.SignInButton";
        return viewBean;
    }
}
