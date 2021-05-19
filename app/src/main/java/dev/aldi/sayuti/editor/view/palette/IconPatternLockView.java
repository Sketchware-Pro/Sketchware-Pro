package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;

public class IconPatternLockView extends IconBase {
    public IconPatternLockView(Context context) {
        super(context);
    }

    public void a(Context context) {
        IconPatternLockView.super.a(context);
        setWidgetImage(2131166308);
        setWidgetName("PatternLockView");
    }

    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 34;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = -1;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingTop = 8;
        layoutBean.paddingRight = 8;
        layoutBean.paddingBottom = 8;
        viewBean.text.text = getName();
        viewBean.convert = "com.andrognito.patternlockview.PatternLockView";
        return viewBean;
    }
}
