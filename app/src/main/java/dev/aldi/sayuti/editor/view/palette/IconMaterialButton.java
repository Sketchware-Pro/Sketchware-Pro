package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconMaterialButton extends IconBase {

    public IconMaterialButton(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        super.a(context);
        setWidgetImage(R.drawable.widget_material_button);
        setWidgetName("MaterialButton");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 41;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.orientation = VERTICAL;
        layoutBean.width = ViewGroup.LayoutParams.WRAP_CONTENT;
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
