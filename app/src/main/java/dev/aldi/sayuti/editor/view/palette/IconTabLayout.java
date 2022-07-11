package dev.aldi.sayuti.editor.view.palette;

import android.content.Context;
import android.view.ViewGroup;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.sketchware.remod.R;

public class IconTabLayout extends IconBase {

    public IconTabLayout(Context context) {
        super(context);
    }

    @Override
    public void a(Context context) {
        IconTabLayout.super.a(context);
        setWidgetImage(R.drawable.widget_tab_layout);
        setWidgetName("TabLayout");
    }

    @Override
    public ViewBean getBean() {
        ViewBean viewBean = new ViewBean();
        viewBean.type = 30;
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutBean.backgroundColor = 0xff008dcd;
        viewBean.convert = "com.google.android.material.tabs.TabLayout";
        viewBean.inject = "app:tabGravity=\"fill\"\napp:tabMode=\"fixed\"\napp:tabIndicatorHeight=\"3dp\"\napp:tabIndicatorColor=\"@android:color/white\"\napp:tabSelectedTextColor=\"@android:color/white\"\napp:tabTextColor=\"@android:color/white\"\napp:tabTextAppearance=\"@android:style/TextAppearance.Widget.TabWidget\"";
        return viewBean;
    }
}
