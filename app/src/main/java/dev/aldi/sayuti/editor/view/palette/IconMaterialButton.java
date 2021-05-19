package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconMaterialButton extends IconBase {
    public IconMaterialButton(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconMaterialButton.super.a(context);
        setWidgetImage(2131166353);
        setWidgetName("MaterialButton");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 41;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = 1;
        layoutBean.width = -2;
        layoutBean.gravity = 17;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.textColor = -1;
        viewBean.text.text = getName();
        viewBean.convert = "com.google.android.material.button.MaterialButton";
        viewBean.inject = "android:textAppearance=\"@style/TextAppearance.MaterialComponents.Button\"\napp:backgroundTint=\"@color/colorPrimary\"\napp:cornerRadius=\"8dp\"\nstyle=\"@style/Widget.MaterialComponents.Button\"";
        return viewBean;
    }
}
