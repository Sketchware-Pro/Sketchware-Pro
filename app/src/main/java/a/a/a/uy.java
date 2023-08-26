package a.a.a;

import android.content.Context;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.palette.IconBase;
import com.besome.sketch.lib.base.BaseWidget;

import java.util.ArrayList;

public class uy extends IconBase {
    public String f;
    public ArrayList<ViewBean> g;

    public uy(Context context, String string, ArrayList<ViewBean> arrayList) {
        super(context);
        this.f = string;
        this.g = arrayList;
        this.a();
    }

    public final void a() {
        ((BaseWidget)this).b.setTextSize(2, 11.0f);
        this.setWidgetName(this.f);
        this.setWidgetImage(ViewBean.getViewTypeResId((int)this.g.get((int)0).type));
    }

    public ViewBean getBean() {
        return this.g.get(0);
    }

    public ArrayList<ViewBean> getData() {
        return this.g;
    }

    public String getName() {
        return this.f;
    }
}

