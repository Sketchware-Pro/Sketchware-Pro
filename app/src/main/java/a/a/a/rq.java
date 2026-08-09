package a.a.a;

import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;

import java.util.ArrayList;

import pro.sketchware.R;

public class rq {

    public static int a(String name) {
        if ("Basic List Item".equals(name)) {
            return R.drawable.activity_preset_1;
        }
        return -1;
    }

    public static ViewBean a() {
        ViewBean viewBean = new ViewBean("textview1", 4);
        viewBean.parent = "root";
        viewBean.index = 0;
        viewBean.preParentType = 0;
        viewBean.name = "textview1";
        viewBean.text.text = "TextView";
        LayoutBean layoutBean = viewBean.layout;
        layoutBean.paddingTop = 8;
        layoutBean.paddingBottom = 8;
        layoutBean.paddingLeft = 8;
        layoutBean.paddingRight = 8;
        return viewBean;
    }

    public static ArrayList<ProjectFileBean> b() {
        ArrayList<ProjectFileBean> arrayList = new ArrayList<>();
        arrayList.add(i());
        return arrayList;
    }

    public static ArrayList<ViewBean> b(String name) {
        if ("Basic List Item".equals(name)) {
            return j();
        }
        return new ArrayList<>();
    }

    public static int c(String name) {
        if ("Basic Drawer".equals(name)) {
            return R.drawable.activity_preset_1;
        }
        return -1;
    }

    public static ArrayList<ProjectFileBean> c() {
        ArrayList<ProjectFileBean> arrayList = new ArrayList<>();
        arrayList.add(g());
        return arrayList;
    }

    public static ArrayList<ProjectFileBean> d() {
        ArrayList<ProjectFileBean> arrayList = new ArrayList<>();
        arrayList.add(k());
        arrayList.add(e());
        arrayList.add(m());
        return arrayList;
    }

    public static ArrayList<ViewBean> d(String name) {
        if ("Basic Drawer".equals(name)) {
            return h();
        }
        return new ArrayList<>();
    }

    public static int e(String name) {
        if ("Empty Activity".equals(name)) {
            return R.drawable.activity_preset_4;
        }
        if ("Basic Activity".equals(name) || "Text Activity".equals(name)) {
            return R.drawable.activity_preset_1;
        }
        return -1;
    }

    public static ProjectFileBean e() {
        return new ProjectFileBean(0, null, "Basic Activity", 0, 0, true, false, false, false);
    }

    public static ArrayList<ViewBean> f() {
        return new ArrayList<>();
    }

    public static ArrayList<ViewBean> f(String name) {
        if ("Empty Activity".equals(name)) {
            return l();
        }
        if ("Basic Activity".equals(name)) {
            return f();
        }
        if ("Text Activity".equals(name)) {
            return n();
        }
        return new ArrayList<>();
    }

    public static ProjectFileBean g() {
        return new ProjectFileBean(2, null, "Basic Drawer");
    }

    public static ArrayList<ViewBean> h() {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        for (int index = 0; index < 7; index++) {
            arrayList.add(a());
        }
        return arrayList;
    }

    public static ProjectFileBean i() {
        return new ProjectFileBean(1, null, "Basic List Item");
    }

    public static ArrayList<ViewBean> j() {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        for (int index = 0; index < 7; index++) {
            arrayList.add(a());
        }
        return arrayList;
    }

    public static ProjectFileBean k() {
        return new ProjectFileBean(0, null, "Empty Activity", 0, 0, false, true, false, false);
    }

    public static ArrayList<ViewBean> l() {
        return new ArrayList<>();
    }

    public static ProjectFileBean m() {
        return new ProjectFileBean(0, null, "Text Activity", 0, 0, true, false, false, false);
    }

    public static ArrayList<ViewBean> n() {
        ArrayList<ViewBean> arrayList = new ArrayList<>();
        for (int index = 0; index < 7; index++) {
            arrayList.add(a());
        }
        return arrayList;
    }
}
