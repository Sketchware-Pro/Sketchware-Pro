package a.a.a;

import com.besome.sketch.beans.ImageBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.TextBean;
import com.besome.sketch.beans.ViewBean;

import java.util.regex.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import dev.aldi.sayuti.editor.injection.AppCompatInjection;

public class Ox {

    public jq a;
    public AppCompatInjection aci;
    public ProjectFileBean b;
    public ViewBean c;
    public ArrayList d;
    public Nx e = null;
    public Nx f = null;

    public Ox(jq jqVar, ProjectFileBean projectFileBean) {
        this.a = jqVar;
        this.b = projectFileBean;
        this.aci = new AppCompatInjection(this.a, this.b);
    }

    public static String x(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split[split.length - 1];
    }

    public final String a(String str) {
        CharBuffer wrap = CharBuffer.wrap(str);
        String str2 = "";
        while (wrap.hasRemaining()) {
            char c2 = wrap.get();
            if (c2 == '?') {
                str2 = str2 + "\\?";
            } else if (c2 == '@') {
                str2 = str2 + "\\@";
            } else if (c2 == '\"') {
                str2 = str2 + "&quot;";
            } else if (c2 == '&') {
                str2 = str2 + "&amp;";
            } else if (c2 == '<') {
                str2 = str2 + "&lt;";
            } else if (c2 == '>') {
                str2 = str2 + "&gt;";
            } else if (c2 == '\n') {
                str2 = str2 + "\\n";
            } else {
                str2 = str2 + c2;
            }
        }
        return str2;
    }

    public final void a() {
        Nx nx = new Nx("LinearLayout");
        nx.a("android", "layout_width", "match_parent");
        nx.a("android", "layout_height", "match_parent");
        nx.a("android", "orientation", "vertical");
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            ViewBean viewBean = (ViewBean) it.next();
            String str = viewBean.parent;
            if (str == null || str.length() <= 0 || viewBean.parent.equals("root")) {
                b(nx, viewBean);
            }
        }
        if (this.a.g) {
            ProjectFileBean projectFileBean = this.b;
            if (projectFileBean.fileType == 0) {
                if (projectFileBean.hasActivityOption(1)) {
                    nx.a("app", "layout_behavior", "@string/appbar_scrolling_view_behavior");
                }
                if (this.b.hasActivityOption(1) || this.b.hasActivityOption(8)) {
                    Nx nx2 = new Nx("androidx.coordinatorlayout.widget.CoordinatorLayout");
                    nx2.a("android", "id", "@+id/_coordinator");
                    this.aci.inject(nx2, "CoordinatorLayout");
                    this.e = nx2;
                }
                if (this.b.hasActivityOption(1)) {
                    Nx nx3 = new Nx("androidx.appcompat.widget.Toolbar");
                    nx3.a("android", "id", "@+id/_toolbar");
                    this.aci.inject(nx3, "Toolbar");
                    Nx nx4 = new Nx("com.google.android.material.appbar.AppBarLayout");
                    nx4.a("android", "id", "@+id/_app_bar");
                    this.aci.inject(nx4, "AppBarLayout");
                    if (this.f != null) {
                        this.f.a(nx3);
                        nx4.a(this.f);
                    } else {
                        nx4.a(nx3);
                    }
                    this.e.a(nx4);
                    this.e.a(nx);
                } else {
                    Nx nx5 = this.e;
                    if (nx5 == null) {
                        this.e = nx;
                    } else {
                        nx5.a(nx);
                    }
                }
                if (this.b.hasActivityOption(8)) {
                    c(this.e, this.c);
                }
                if (this.c.type == 32) {
                    b(this.e, this.c);
                }
                if (this.b.hasActivityOption(4)) {
                    Nx nx6 = new Nx("androidx.drawerlayout.widget.DrawerLayout");
                    nx6.a("android", "id", "@+id/_drawer");
                    this.aci.inject(nx6, "DrawerLayout");
                    nx6.a(this.e);
                    Nx nx7 = new Nx("LinearLayout");
                    nx7.a("android", "id", "@+id/_nav_view");
                    this.aci.inject(nx7, "NavigationDrawer");
                    Nx nx8 = new Nx("include", true);
                    nx8.a("", "layout", "@layout/_drawer_" + this.b.fileName);
                    nx7.a(nx8);
                    nx6.a(nx7);
                    this.e = nx6;
                }
            } else {
                this.e = nx;
            }
        } else {
            this.e = nx;
        }
        this.e.a(0, "xmlns", "tools", "http://schemas.android.com/tools");
        this.e.a(0, "xmlns", "app", "http://schemas.android.com/apk/res-auto");
        this.e.a(0, "xmlns", "android", "http://schemas.android.com/apk/res/android");
    }

    public void a(Nx nx, ViewBean viewBean) {
        if (viewBean.layout.backgroundResource == null || "NONE".toLowerCase().equals(viewBean.layout.backgroundResource.toLowerCase())) {
            int i = viewBean.layout.backgroundColor;
            if (i != 16777215) {
                if (i != 0) {
                    Object[] objArr = {Integer.valueOf(i & 16777215)};
                    if (nx.c().equals("BottomAppBar")) {
                        nx.a("app", "backgroundTint", String.format("#%06X", objArr));
                    } else if (nx.c().equals("CollapsingToolbarLayout")) {
                        nx.a("app", "contentScrim", String.format("#%06X", objArr));
                    } else {
                        if(!hasAttr("background", viewBean)) nx.a("android", "background", String.format("#%06X", objArr));
                    }
                } else if (nx.c().equals("BottomAppBar")) {
                    nx.a("android", "backgroundTint", "@android:color/transparent");
                } else if (nx.c().equals("CollapsingToolbarLayout")) {
                    nx.a("app", "contentScrim", "?attr/colorPrimary");
                } else {
                    nx.a("android", "background", "@android:color/transparent");
                }
            }
        } else {
            if(!hasAttr("background", viewBean)) nx.a("android", "background", String.format("@drawable/%s", viewBean.layout.backgroundResource.endsWith(".9") ? viewBean.layout.backgroundResource.replaceAll("\\.9", "") : viewBean.layout.backgroundResource));
        }
    }

    public void a(ArrayList arrayList) {
        a(arrayList, (ViewBean) null);
    }

    public void a(ArrayList arrayList, ViewBean viewBean) {
        this.c = viewBean;
        this.d = arrayList;
        a();
    }

    public String b() {
        return this.e.b();
    }

    public void b(Nx nx, ViewBean viewBean) {
        int i;
        viewBean.getClassInfo().a();
        Nx nx2 = viewBean.convert.equals("") ? new Nx(viewBean.getClassInfo().a()) : new Nx(viewBean.convert.replaceAll(" ", ""));
        if (nx2 != null) {
            StringBuilder sb = new StringBuilder();
            if (viewBean.convert.equals("include")) {
                sb.append("@layout/");
                sb.append(viewBean.id);
                nx2.a("", "layout", sb.toString());
            } else {
                sb.append("@+id/");
                sb.append(viewBean.id);
                nx2.a("android", "id", sb.toString());
                if (this.b.fileType == 1 && ((i = viewBean.type) == 4 || i == 3 || i == 6 || i == 11 || i == 13 || i == 14 || i == 15 || i == 8 || i == 22 || i == 23 || i == 19 || i == 24 || i == 32)) {
                    if(!hasAttr("focusable", viewBean)) nx2.a("android", "focusable", "false");
                }
            }
            if (!viewBean.convert.equals("include")) {
                int i2 = viewBean.layout.width;
                if (i2 == -1) {
                    nx2.a("android", "layout_width", "match_parent");
                } else if (i2 == -2) {
                    nx2.a("android", "layout_width", "wrap_content");
                } else {
                    nx2.a("android", "layout_width", viewBean.layout.width + "dp");
                }
                int i3 = viewBean.layout.height;
                if (i3 == -1) {
                    nx2.a("android", "layout_height", "match_parent");
                } else if (i3 == -2) {
                    nx2.a("android", "layout_height", "wrap_content");
                } else {
                    nx2.a("android", "layout_height", viewBean.layout.height + "dp");
                }
                g(nx2, viewBean);
                i(nx2, viewBean);
                a(nx2, viewBean);
                if (viewBean.getClassInfo().a("ViewGroup")) {
                    d(nx2, viewBean);
                }
            }
            this.a.x.handleWidget(x(viewBean.convert));
            if (viewBean.getClassInfo().b("LinearLayout") && !nx2.c().matches("(BottomAppBar|NavigationView|Coordinator|Floating|Collaps|include)\\w*")) {
                h(nx2, viewBean);
                m(nx2, viewBean);
            }
            if (viewBean.getClassInfo().a("TextView")) {
                d(nx2, viewBean);
                j(nx2, viewBean);
            }
            if (viewBean.getClassInfo().a("ImageView")) {
                e(nx2, viewBean);
                if (!nx2.b().contains(".")) {
                    ea(nx2, viewBean);
                }
            }
            if (viewBean.getClassInfo().b("SeekBar")) {
                d(nx2, viewBean);
            }
            if (viewBean.getClassInfo().b("ProgressBar")) {
                d(nx2, viewBean);
            }
            if (viewBean.getClassInfo().b("WaveSideBar")) {
                o(nx2, viewBean);
            }
            k(nx2, viewBean);
            int i4 = viewBean.parentType;
            if (!viewBean.convert.equals("include")) {
                if (i4 == 0) {
                    f(nx2, viewBean);
                    l(nx2, viewBean);
                } else if (i4 == 2 || i4 == 12) {
                    f(nx2, viewBean);
                }
            }
            if (viewBean.getClassInfo().a("ViewGroup")) {
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    ViewBean viewBean2 = (ViewBean) it.next();
                    String str = viewBean2.parent;
                    if (str != null && str.equals(viewBean.id)) {
                        b(nx2, viewBean2);
                    }
                }
            }
            if (!viewBean.inject.equals("")) {
                nx2.b(viewBean.inject.replaceAll(" ", ""));
            }
            if (nx2.c().equals("CollapsingToolbarLayout")) {
                this.f = nx2;
            } else {
                nx.a(nx2);
            }
        }
    }

    public void c(Nx nx, ViewBean viewBean) {
        Nx nx2 = new Nx("com.google.android.material.floatingactionbutton.FloatingActionButton");
        nx2.a("android", "id", "@+id/" + viewBean.id);
        nx2.a("android", "layout_width", "wrap_content");
        nx2.a("android", "layout_height", "wrap_content");
        g(nx2, viewBean);
        f(nx2, viewBean);
        String str = viewBean.image.resName;
        if (str != null && str.length() > 0 && !viewBean.image.resName.equals("NONE")) {
            nx2.a("app", "srcCompat", "@drawable/" + viewBean.image.resName.toLowerCase());
        }
        if (viewBean.id.equals("_fab")) {
            this.aci.inject(nx2, "FloatingActionButton");
        }
        k(nx2, viewBean);
        nx.a(nx2);
    }

    public void d(Nx nx, ViewBean viewBean) {
        int i = viewBean.layout.gravity;
        if (i != 0) {
            String str = "";
            int i2 = i & 112;
            int i3 = i & 7;
            if (i3 == 1) {
                str = "" + "center_horizontal";
            } else {
                if ((i3 & 3) == 3) {
                    str = "" + "left";
                }
                if ((i3 & 5) == 5) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "right";
                }
            }
            if (i2 == 16) {
                if (str.length() > 0) {
                    str = str + "|";
                }
                str = str + "center_vertical";
            } else {
                if ((i2 & 48) == 48) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "top";
                }
                if ((i2 & 80) == 80) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "bottom";
                }
            }
            nx.a("android", "gravity", str);
        }
    }

    public void e(Nx nx, ViewBean viewBean) {
        if (viewBean.image.resName.length() > 0 && !"NONE".equals(viewBean.image.resName)) {
            StringBuilder sb = new StringBuilder();
            sb.append("@drawable/");
            sb.append(viewBean.image.resName.toLowerCase());
            if (nx.c().equals("FloatingActionButton")) {
                nx.a("app", "srcCompat", sb.toString());
            } else {
                nx.a("android", "src", sb.toString());
            }
        }
    }

    public void ea(Nx nx, ViewBean viewBean) {
        if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER)) {
            nx.a("android", "scaleType", "center");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_XY)) {
            nx.a("android", "scaleType", "fitXY");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_START)) {
            nx.a("android", "scaleType", "fitStart");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_END)) {
            nx.a("android", "scaleType", "fitEnd");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_CENTER)) {
            nx.a("android", "scaleType", "fitCenter");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_CROP)) {
            nx.a("android", "scaleType", "centerCrop");
        } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_INSIDE)) {
            nx.a("android", "scaleType", "centerInside");
        }
    }

    public void f(Nx nx, ViewBean viewBean) {
        int i = viewBean.layout.layoutGravity;
        if (i != 0) {
            String str = "";
            int i2 = i & 112;
            int i3 = i & 7;
            if (i3 == 1) {
                str = "" + "center_horizontal";
            } else {
                if ((i3 & 3) == 3) {
                    str = "" + "left";
                }
                if ((i3 & 5) == 5) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "right";
                }
            }
            if (i2 == 16) {
                if (str.length() > 0) {
                    str = str + "|";
                }
                str = str + "center_vertical";
            } else {
                if ((i2 & 48) == 48) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "top";
                }
                if ((i2 & 80) == 80) {
                    if (str.length() > 0) {
                        str = str + "|";
                    }
                    str = str + "bottom";
                }
            }
            nx.a("android", "layout_gravity", str);
        }
    }

    public void g(Nx nx, ViewBean viewBean) {
        int i;
        LayoutBean layoutBean = viewBean.layout;
        int i2 = layoutBean.marginLeft;
        if (i2 == layoutBean.marginRight && (i = layoutBean.marginTop) == layoutBean.marginBottom && i2 == i && i2 > 0) {
            nx.a("android", "layout_margin", viewBean.layout.marginLeft + "dp");
            return;
        }
        if (viewBean.layout.marginLeft > 0) {
            nx.a("android", "layout_marginLeft", viewBean.layout.marginLeft + "dp");
        }
        if (viewBean.layout.marginTop > 0) {
            nx.a("android", "layout_marginTop", viewBean.layout.marginTop + "dp");
        }
        if (viewBean.layout.marginRight > 0) {
            nx.a("android", "layout_marginRight", viewBean.layout.marginRight + "dp");
        }
        if (viewBean.layout.marginBottom > 0) {
            nx.a("android", "layout_marginBottom", viewBean.layout.marginBottom + "dp");
        }
    }

    public void h(Nx nx, ViewBean viewBean) {
        int i = viewBean.layout.orientation;
        if (i == 0) {
            nx.a("android", "orientation", "horizontal");
        } else if (i == 1) {
            nx.a("android", "orientation", "vertical");
        }
    }

    public void i(Nx nx, ViewBean viewBean) {
        int i;
        LayoutBean layoutBean = viewBean.layout;
        int i2 = layoutBean.paddingLeft;
        if (i2 == layoutBean.paddingRight && (i = layoutBean.paddingTop) == layoutBean.paddingBottom && i2 == i && i2 > 0) {
            nx.a("android", "padding", viewBean.layout.paddingLeft + "dp");
            return;
        }
        if (viewBean.layout.paddingLeft > 0) {
            nx.a("android", "paddingLeft", viewBean.layout.paddingLeft + "dp");
        }
        if (viewBean.layout.paddingTop > 0) {
            nx.a("android", "paddingTop", viewBean.layout.paddingTop + "dp");
        }
        if (viewBean.layout.paddingRight > 0) {
            nx.a("android", "paddingRight", viewBean.layout.paddingRight + "dp");
        }
        if (viewBean.layout.paddingBottom > 0) {
            nx.a("android", "paddingBottom", viewBean.layout.paddingBottom + "dp");
        }
    }

    public void j(Nx nx, ViewBean viewBean) {
        String str = viewBean.text.text;
        if (str != null && str.length() > 0) {
            nx.a("android", "text", a(viewBean.text.text));
        }
        if (viewBean.text.textSize > 0) {
            nx.a("android", "textSize", viewBean.text.textSize + "sp");
        }
        int i = viewBean.text.textType;
        if (i == TextBean.TEXT_TYPE_BOLD) {
            nx.a("android", "textStyle", "bold");
        } else if (i == TextBean.TEXT_TYPE_ITALIC) {
            nx.a("android", "textStyle", "italic");
        } else if (i == TextBean.TEXT_TYPE_BOLDITALIC) {
            nx.a("android", "textStyle", "bold|italic");
        }
        if (viewBean.text.textColor != 0) {
            if(!hasAttr("textColor", viewBean)) nx.a("android", "textColor", String.format("#%06X", Integer.valueOf(viewBean.text.textColor & 0xffffff)));
        }
        if (viewBean.type == 5 || viewBean.type == 23 || viewBean.type == 24) {
            String str2 = viewBean.text.hint;
            if (str2 != null && str2.length() > 0) {
                nx.a("android", "hint", a(viewBean.text.hint));
            }
            if (viewBean.text.hintColor != 0) {
                if(!hasAttr("textColorHint", viewBean)) nx.a("android", "textColorHint", String.format("#%06X", Integer.valueOf(viewBean.text.hintColor & 0xffffff)));
            }
            if (viewBean.text.singleLine != 0) {
                nx.a("android", "singleLine", "true");
            }
            int i2 = viewBean.text.line;
            if (i2 > 0) {
                nx.a("android", "lines", String.valueOf(i2));
            }
            int i3 = viewBean.text.inputType;
            if (i3 != TextBean.INPUT_TYPE_TEXT) {
                nx.a("android", "inputType", sq.a("property_input_type", i3));
            }
            int i4 = viewBean.text.imeOption;
            if (i4 != TextBean.IME_OPTION_NORMAL) {
                if (i4 == TextBean.IME_OPTION_NONE) {
                    nx.a("android", "imeOptions", "actionNone");
                } else if (i4 == TextBean.IME_OPTION_GO) {
                    nx.a("android", "imeOptions", "actionGo");
                } else if (i4 == TextBean.IME_OPTION_SEARCH) {
                    nx.a("android", "imeOptions", "actionSearch");
                } else if (i4 == TextBean.IME_OPTION_SEND) {
                    nx.a("android", "imeOptions", "actionSend");
                } else if (i4 == TextBean.IME_OPTION_NEXT) {
                    nx.a("android", "imeOptions", "actionNext");
                } else if (i4 == TextBean.IME_OPTION_DONE) {
                    nx.a("android", "imeOptions", "actionDone");
                }
            }
        }
        if (viewBean.type == 4) {
            if (viewBean.text.singleLine != 0) {
                nx.a("android", "singleLine", "true");
            }
            int i5 = viewBean.text.line;
            if (i5 > 0) {
                nx.a("android", "lines", String.valueOf(i5));
            }
        }
    }

    public void k(Nx nx, ViewBean viewBean) {
        int i;
        if (viewBean.enabled == 0) {
            nx.a("android", "enabled", "false");
        }
        if (viewBean.clickable == 0) {
            nx.a("android", "clickable", "false");
        }
        int i2 = viewBean.image.rotate;
        if (i2 != 0) {
            nx.a("android", "rotation", String.valueOf(i2));
        }
        float f2 = viewBean.alpha;
        if (1.0f != f2) {
            nx.a("android", "alpha", String.valueOf(f2));
        }
        if (0.0f != viewBean.translationX) {
            nx.a("android", "translationX", viewBean.translationX + "dp");
        }
        if (0.0f != viewBean.translationY) {
            nx.a("android", "translationY", viewBean.translationY + "dp");
        }
        float f3 = viewBean.scaleX;
        if (1.0f != f3) {
            nx.a("android", "scaleX", String.valueOf(f3));
        }
        float f4 = viewBean.scaleY;
        if (1.0f != f4) {
            nx.a("android", "scaleY", String.valueOf(f4));
        }
        int i3 = viewBean.type;
        if ((i3 == 11 || i3 == 13) && 1 == viewBean.checked) {
            nx.a("android", "checked", "true");
        }
        if (viewBean.type == 14) {
            int i4 = viewBean.progress;
            if (i4 > 0) {
                nx.a("android", "progress", String.valueOf(i4));
            }
            int i5 = viewBean.max;
            if (100 != i5) {
                nx.a("android", "max", String.valueOf(i5));
            }
        }
        if (viewBean.type == 15 && (i = viewBean.firstDayOfWeek) != 1) {
            nx.a("android", "firstDayOfWeek", String.valueOf(i));
        }
        if (viewBean.type == 10) {
            int i6 = viewBean.spinnerMode;
            if (i6 == 1) {
                nx.a("android", "spinnerMode", "dropdown");
            } else if (i6 == 0) {
                nx.a("android", "spinnerMode", "dialog");
            }
        }
        if (viewBean.type == 9) {
            if (1 != viewBean.dividerHeight) {
                nx.a("android", "dividerHeight", viewBean.dividerHeight + "dp");
            }
            if (viewBean.dividerHeight == 0) {
                nx.a("android", "divider", "@null");
            }
            int i7 = viewBean.choiceMode;
            if (i7 == 0) {
                nx.a("android", "choiceMode", "none");
            } else if (i7 == 1) {
                nx.a("android", "choiceMode", "singleChoice");
            } else if (i7 == 2) {
                nx.a("android", "choiceMode", "multipleChoice");
            }
        }
        if (viewBean.type == 17) {
            String str = viewBean.adSize;
            if (str == null || str.length() <= 0) {
                nx.a("app", "adSize", "SMART_BANNER");
            } else {
                nx.a("app", "adSize", viewBean.adSize);
            }
            jq jqVar = a;
            if (jqVar.f) {
                nx.a("app", "adUnitId", jqVar.r);
            } else {
                nx.a("app", "adUnitId", "ca-app-pub-3940256099942544/6300978111");
            }
        }
        if (viewBean.type == 8) {
            int i8 = viewBean.progress;
            if (i8 > 0) {
                nx.a("android", "progress", String.valueOf(i8));
            }
            int i9 = viewBean.max;
            if (100 != i9) {
                nx.a("android", "max", String.valueOf(i9));
            }
            String str2 = viewBean.indeterminate;
            if (str2 != null && str2.length() > 0) {
                nx.a("android", "indeterminate", viewBean.indeterminate);
            }
            String str3 = viewBean.progressStyle;
            if (str3 != null && str3.length() > 0) {
                nx.a((String) null, "style", viewBean.progressStyle);
            }
        }
    }

    public void l(Nx nx, ViewBean viewBean) {
        int i = viewBean.layout.weight;
        if (i > 0) {
            nx.a("android", "layout_weight", String.valueOf(i));
        }
    }

    public void m(Nx nx, ViewBean viewBean) {
        int i = viewBean.layout.weightSum;
        if (i > 0) {
            nx.a("android", "weightSum", String.valueOf(i));
        }
    }

    public void n(Nx nx, ViewBean viewBean) {
        nx.a("app", "tabGravity", "fill");
        nx.a("app", "tabMode", "fixed");
        nx.a("app", "tabIndicatorHeight", "3dp");
        nx.a("app", "tabIndicatorColor", "@android:color/white");
        nx.a("app", "tabSelectedTextColor", "@android:color/white");
        nx.a("app", "tabTextColor", "@android:color/white");
        nx.a("app", "tabTextAppearance", "@android:style/TextAppearance.Widget.TabWidget");
    }
    
    /**
     * check whether the atrribute (attrName) is injected to the ViewBean or not.
     */
    public boolean hasAttr(String attrName, ViewBean bean){
        final String inject = bean.inject;
        if(inject.equals("") || inject == null) return false;
        if(Pattern.compile("(android|app) *?: *?" + attrName).matcher(inject).find()) return true;
        return false;
    }

    public void o(Nx nx, ViewBean viewBean) {
        if (viewBean.text.textSize > 0) {
            nx.a("app", "sidebar_text_size", viewBean.text.textSize + "sp");
        }
        if (viewBean.text.textColor != 0) {
            nx.a("app", "sidebar_text_color", String.format("#%06X", Integer.valueOf(viewBean.text.textColor & 0xffffff)));
        }
    }
}
