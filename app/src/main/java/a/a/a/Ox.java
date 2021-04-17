package a.a.a;

import com.besome.sketch.beans.ImageBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.TextBean;
import com.besome.sketch.beans.ViewBean;
import dev.aldi.sayuti.editor.injection.AppCompatInjection;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class Ox {
    public jq a;
    public AppCompatInjection aci;
    public ProjectFileBean b;
    public ViewBean c;
    public ArrayList d;
    public Nx e = null;
    public Nx f = null;

    public Ox(jq var1, ProjectFileBean var2) {
        this.a = var1;
        this.b = var2;
        this.aci = new AppCompatInjection(this.a, this.b);
    }

    public static String x(String var0) {
        if (var0.contains(".")) {
            String[] var1 = var0.split("\\.");
            var0 = var1[var1.length - 1];
        }

        return var0;
    }

    public final String a(String var1) {
        CharBuffer var2 = CharBuffer.wrap(var1);
        var1 = "";

        while(var2.hasRemaining()) {
            char var3 = var2.get();
            StringBuilder var4;
            if (var3 == '?') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("\\?");
                var1 = var4.toString();
            } else if (var3 == '@') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("\\@");
                var1 = var4.toString();
            } else if (var3 == '"') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("&quot;");
                var1 = var4.toString();
            } else if (var3 == '&') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("&amp;");
                var1 = var4.toString();
            } else if (var3 == '<') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("&lt;");
                var1 = var4.toString();
            } else if (var3 == '>') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("&gt;");
                var1 = var4.toString();
            } else if (var3 == '\n') {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append("\\n");
                var1 = var4.toString();
            } else {
                var4 = new StringBuilder();
                var4.append(var1);
                var4.append(var3);
                var1 = var4.toString();
            }
        }

        return var1;
    }

    public final void a() {
        Nx var1 = new Nx("LinearLayout");
        var1.a("android", "layout_width", "match_parent");
        var1.a("android", "layout_height", "match_parent");
        var1.a("android", "orientation", "vertical");
        Iterator var2 = this.d.iterator();

        while(true) {
            ViewBean var3;
            String var4;
            do {
                if (!var2.hasNext()) {
                    if (this.a.g) {
                        ProjectFileBean var7 = this.b;
                        if (var7.fileType == 0) {
                            if (var7.hasActivityOption(1)) {
                                var1.a("app", "layout_behavior", "@string/appbar_scrolling_view_behavior");
                            }

                            Nx var8;
                            if (this.b.hasActivityOption(1) || this.b.hasActivityOption(8)) {
                                var8 = new Nx("androidx.coordinatorlayout.widget.CoordinatorLayout");
                                var8.a("android", "id", "@+id/_coordinator");
                                this.aci.inject(var8, "CoordinatorLayout");
                                this.e = var8;
                            }

                            Nx var9;
                            if (this.b.hasActivityOption(1)) {
                                var9 = new Nx("androidx.appcompat.widget.Toolbar");
                                var9.a("android", "id", "@+id/_toolbar");
                                this.aci.inject(var9, "Toolbar");
                                var8 = new Nx("com.google.android.material.appbar.AppBarLayout");
                                var8.a("android", "id", "@+id/_app_bar");
                                this.aci.inject(var8, "AppBarLayout");
                                if (this.f != null) {
                                    this.f.a(var9);
                                    var8.a(this.f);
                                } else {
                                    var8.a(var9);
                                }

                                this.e.a(var8);
                                this.e.a(var1);
                            } else {
                                var8 = this.e;
                                if (var8 == null) {
                                    this.e = var1;
                                } else {
                                    var8.a(var1);
                                }
                            }

                            if (this.b.hasActivityOption(8)) {
                                this.c(this.e, this.c);
                            }

                            if (this.c.type == 32) {
                                this.b(this.e, this.c);
                            }

                            if (this.b.hasActivityOption(4)) {
                                var8 = new Nx("androidx.drawerlayout.widget.DrawerLayout");
                                var8.a("android", "id", "@+id/_drawer");
                                this.aci.inject(var8, "DrawerLayout");
                                var8.a(this.e);
                                var9 = new Nx("LinearLayout");
                                var9.a("android", "id", "@+id/_nav_view");
                                this.aci.inject(var9, "NavigationDrawer");
                                Nx var6 = new Nx("include", true);
                                StringBuilder var5 = new StringBuilder();
                                var5.append("@layout/_drawer_");
                                var5.append(this.b.fileName);
                                var6.a("", "layout", var5.toString());
                                var9.a(var6);
                                var8.a(var9);
                                this.e = var8;
                            }
                        } else {
                            this.e = var1;
                        }
                    } else {
                        this.e = var1;
                    }

                    this.e.a(0, "xmlns", "tools", "http://schemas.android.com/tools");
                    this.e.a(0, "xmlns", "app", "http://schemas.android.com/apk/res-auto");
                    this.e.a(0, "xmlns", "android", "http://schemas.android.com/apk/res/android");
                    return;
                }

                var3 = (ViewBean)var2.next();
                var4 = var3.parent;
            } while(var4 != null && var4.length() > 0 && !var3.parent.equals("root"));

            this.b(var1, var3);
        }
    }

    public void a(Nx var1, ViewBean var2) {
        if (var2.layout.backgroundResource != null && !"NONE".toLowerCase().equals(var2.layout.backgroundResource.toLowerCase())) {
            String var5;
            if (var2.layout.backgroundResource.endsWith(".9")) {
                var5 = var2.layout.backgroundResource.replaceAll("\\.9", "");
            } else {
                var5 = var2.layout.backgroundResource;
            }

            var1.a("android", "background", String.format("@drawable/%s", var5));
        } else {
            int var3 = var2.layout.backgroundColor;
            if (var3 != 16777215) {
                if (var3 != 0) {
                    Object[] var4 = new Object[]{var3 & 16777215};
                    if (var1.c().equals("BottomAppBar")) {
                        var1.a("app", "backgroundTint", String.format("#%06X", var4));
                    } else if (var1.c().equals("CollapsingToolbarLayout")) {
                        var1.a("app", "contentScrim", String.format("#%06X", var4));
                    } else {
                        var1.a("android", "background", String.format("#%06X", var4));
                    }
                } else if (var1.c().equals("BottomAppBar")) {
                    var1.a("android", "backgroundTint", "@android:color/transparent");
                } else if (var1.c().equals("CollapsingToolbarLayout")) {
                    var1.a("app", "contentScrim", "?attr/colorPrimary");
                } else {
                    var1.a("android", "background", "@android:color/transparent");
                }
            }
        }

    }

    public void a(ArrayList var1) {
        this.a((ArrayList)var1, (ViewBean)null);
    }

    public void a(ArrayList var1, ViewBean var2) {
        this.c = var2;
        this.d = var1;
        this.a();
    }

    public String b() {
        return this.e.b();
    }

    public void b(Nx var1, ViewBean var2) {
        var2.getClassInfo().a();
        Nx var3;
        if (var2.convert.equals("")) {
            var3 = new Nx(var2.getClassInfo().a());
        } else {
            var3 = new Nx(var2.convert.replaceAll(" ", ""));
        }

        if (var3 != null) {
            StringBuilder var4 = new StringBuilder();
            int var5;
            if (var2.convert.equals("include")) {
                var4.append("@layout/");
                var4.append(var2.id);
                var3.a("", "layout", var4.toString());
            } else {
                var4.append("@+id/");
                var4.append(var2.id);
                var3.a("android", "id", var4.toString());
                if (this.b.fileType == 1) {
                    var5 = var2.type;
                    if (var5 == 4 || var5 == 3 || var5 == 6 || var5 == 11 || var5 == 13 || var5 == 14 || var5 == 15 || var5 == 8 || var5 == 22 || var5 == 23 || var5 == 19 || var5 == 24 || var5 == 32) {
                        var3.a("android", "focusable", "false");
                    }
                }
            }

            if (!var2.convert.equals("include")) {
                var5 = var2.layout.width;
                if (var5 == -1) {
                    var3.a("android", "layout_width", "match_parent");
                } else if (var5 == -2) {
                    var3.a("android", "layout_width", "wrap_content");
                } else {
                    var4 = new StringBuilder();
                    var4.append(var2.layout.width);
                    var4.append("dp");
                    var3.a("android", "layout_width", var4.toString());
                }

                var5 = var2.layout.height;
                if (var5 == -1) {
                    var3.a("android", "layout_height", "match_parent");
                } else if (var5 == -2) {
                    var3.a("android", "layout_height", "wrap_content");
                } else {
                    var4 = new StringBuilder();
                    var4.append(var2.layout.height);
                    var4.append("dp");
                    var3.a("android", "layout_height", var4.toString());
                }

                this.g(var3, var2);
                this.i(var3, var2);
                this.a(var3, var2);
                if (var2.getClassInfo().a("ViewGroup")) {
                    this.d(var3, var2);
                }
            }

            this.a.x.handleWidget(x(var2.convert));
            if (var2.getClassInfo().b("LinearLayout") && !var3.c().matches("(BottomAppBar|NavigationView|Coordinator|Floating|Collaps|include)\\w*")) {
                this.h(var3, var2);
                this.m(var3, var2);
            }

            if (var2.getClassInfo().a("TextView")) {
                this.d(var3, var2);
                this.j(var3, var2);
            }

            if (var2.getClassInfo().a("ImageView")) {
                this.e(var3, var2);
                if (!var3.b().contains(".")) {
                    this.ea(var3, var2);
                }
            }

            if (var2.getClassInfo().b("SeekBar")) {
                this.d(var3, var2);
            }

            if (var2.getClassInfo().b("ProgressBar")) {
                this.d(var3, var2);
            }

            if (var2.getClassInfo().b("WaveSideBar")) {
                this.o(var3, var2);
            }

            this.k(var3, var2);
            var5 = var2.parentType;
            if (!var2.convert.equals("include")) {
                if (var5 == 0) {
                    this.f(var3, var2);
                    this.l(var3, var2);
                } else if (var5 == 2 || var5 == 12) {
                    this.f(var3, var2);
                }
            }

            if (var2.getClassInfo().a("ViewGroup")) {
                Iterator var6 = this.d.iterator();

                while(var6.hasNext()) {
                    ViewBean var8 = (ViewBean)var6.next();
                    String var7 = var8.parent;
                    if (var7 != null && var7.equals(var2.id)) {
                        this.b(var3, var8);
                    }
                }
            }

            if (!var2.inject.equals("")) {
                var3.b(var2.inject.replaceAll(" ", ""));
            }

            if (var3.c().equals("CollapsingToolbarLayout")) {
                this.f = var3;
            } else {
                var1.a(var3);
            }
        }

    }

    public void c(Nx var1, ViewBean var2) {
        Nx var3 = new Nx("com.google.android.material.floatingactionbutton.FloatingActionButton");
        StringBuilder var4 = new StringBuilder();
        var4.append("@+id/");
        var4.append(var2.id);
        var3.a("android", "id", var4.toString());
        var3.a("android", "layout_width", "wrap_content");
        var3.a("android", "layout_height", "wrap_content");
        this.g(var3, var2);
        this.f(var3, var2);
        String var5 = var2.image.resName;
        if (var5 != null && var5.length() > 0 && !var2.image.resName.equals("NONE")) {
            var4 = new StringBuilder();
            var4.append("@drawable/");
            var4.append(var2.image.resName.toLowerCase());
            var3.a("app", "srcCompat", var4.toString());
        }

        if (var2.id.equals("_fab")) {
            this.aci.inject(var3, "FloatingActionButton");
        }

        this.k(var3, var2);
        var1.a(var3);
    }

    public void d(Nx var1, ViewBean var2) {
        int var3 = var2.layout.gravity;
        if (var3 != 0) {
            String var4 = "";
            int var5 = var3 & 112;
            var3 &= 7;
            StringBuilder var6;
            String var7;
            StringBuilder var8;
            if (var3 == 1) {
                var6 = new StringBuilder();
                var6.append("");
                var6.append("center_horizontal");
                var7 = var6.toString();
            } else {
                if ((var3 & 3) == 3) {
                    var6 = new StringBuilder();
                    var6.append("");
                    var6.append("left");
                    var4 = var6.toString();
                }

                var7 = var4;
                if ((var3 & 5) == 5) {
                    var7 = var4;
                    if (var4.length() > 0) {
                        var6 = new StringBuilder();
                        var6.append(var4);
                        var6.append("|");
                        var7 = var6.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append("right");
                    var7 = var8.toString();
                }
            }

            if (var5 == 16) {
                var4 = var7;
                if (var7.length() > 0) {
                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append("|");
                    var4 = var8.toString();
                }

                var6 = new StringBuilder();
                var6.append(var4);
                var6.append("center_vertical");
                var7 = var6.toString();
            } else {
                var4 = var7;
                if ((var5 & 48) == 48) {
                    var4 = var7;
                    if (var7.length() > 0) {
                        var8 = new StringBuilder();
                        var8.append(var7);
                        var8.append("|");
                        var4 = var8.toString();
                    }

                    var6 = new StringBuilder();
                    var6.append(var4);
                    var6.append("top");
                    var4 = var6.toString();
                }

                var7 = var4;
                if ((var5 & 80) == 80) {
                    var7 = var4;
                    if (var4.length() > 0) {
                        var6 = new StringBuilder();
                        var6.append(var4);
                        var6.append("|");
                        var7 = var6.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append("bottom");
                    var7 = var8.toString();
                }
            }

            var1.a("android", "gravity", var7);
        }

    }

    public void e(Nx var1, ViewBean var2) {
        if (var2.image.resName.length() > 0 && !"NONE".equals(var2.image.resName)) {
            StringBuilder var3 = new StringBuilder();
            var3.append("@drawable/");
            var3.append(var2.image.resName.toLowerCase());
            if (var1.c().equals("FloatingActionButton")) {
                var1.a("app", "srcCompat", var3.toString());
            } else {
                var1.a("android", "src", var3.toString());
            }
        }

    }

    public void ea(Nx var1, ViewBean var2) {
        if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER)) {
            var1.a("android", "scaleType", "center");
        } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_XY)) {
            var1.a("android", "scaleType", "fitXY");
        } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_START)) {
            var1.a("android", "scaleType", "fitStart");
        } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_END)) {
            var1.a("android", "scaleType", "fitEnd");
        } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_CENTER)) {
            var1.a("android", "scaleType", "fitCenter");
        } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_CROP)) {
            var1.a("android", "scaleType", "centerCrop");
        } else if (var2.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_INSIDE)) {
            var1.a("android", "scaleType", "centerInside");
        }

    }

    public void f(Nx var1, ViewBean var2) {
        int var3 = var2.layout.layoutGravity;
        if (var3 != 0) {
            String var4 = "";
            int var5 = var3 & 112;
            var3 &= 7;
            StringBuilder var6;
            String var7;
            StringBuilder var8;
            if (var3 == 1) {
                var6 = new StringBuilder();
                var6.append("");
                var6.append("center_horizontal");
                var7 = var6.toString();
            } else {
                if ((var3 & 3) == 3) {
                    var6 = new StringBuilder();
                    var6.append("");
                    var6.append("left");
                    var4 = var6.toString();
                }

                var7 = var4;
                if ((var3 & 5) == 5) {
                    var7 = var4;
                    if (var4.length() > 0) {
                        var6 = new StringBuilder();
                        var6.append(var4);
                        var6.append("|");
                        var7 = var6.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append("right");
                    var7 = var8.toString();
                }
            }

            if (var5 == 16) {
                var4 = var7;
                if (var7.length() > 0) {
                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append("|");
                    var4 = var8.toString();
                }

                var6 = new StringBuilder();
                var6.append(var4);
                var6.append("center_vertical");
                var7 = var6.toString();
            } else {
                var4 = var7;
                if ((var5 & 48) == 48) {
                    var4 = var7;
                    if (var7.length() > 0) {
                        var8 = new StringBuilder();
                        var8.append(var7);
                        var8.append("|");
                        var4 = var8.toString();
                    }

                    var6 = new StringBuilder();
                    var6.append(var4);
                    var6.append("top");
                    var4 = var6.toString();
                }

                var7 = var4;
                if ((var5 & 80) == 80) {
                    var7 = var4;
                    if (var4.length() > 0) {
                        var6 = new StringBuilder();
                        var6.append(var4);
                        var6.append("|");
                        var7 = var6.toString();
                    }

                    var8 = new StringBuilder();
                    var8.append(var7);
                    var8.append("bottom");
                    var7 = var8.toString();
                }
            }

            var1.a("android", "layout_gravity", var7);
        }

    }

    public void g(Nx var1, ViewBean var2) {
        LayoutBean var3 = var2.layout;
        int var4 = var3.marginLeft;
        StringBuilder var6;
        if (var4 == var3.marginRight) {
            int var5 = var3.marginTop;
            if (var5 == var3.marginBottom && var4 == var5 && var4 > 0) {
                var6 = new StringBuilder();
                var6.append(String.valueOf(var2.layout.marginLeft));
                var6.append("dp");
                var1.a("android", "layout_margin", var6.toString());
                return;
            }
        }

        if (var2.layout.marginLeft > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.marginLeft));
            var6.append("dp");
            var1.a("android", "layout_marginLeft", var6.toString());
        }

        if (var2.layout.marginTop > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.marginTop));
            var6.append("dp");
            var1.a("android", "layout_marginTop", var6.toString());
        }

        if (var2.layout.marginRight > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.marginRight));
            var6.append("dp");
            var1.a("android", "layout_marginRight", var6.toString());
        }

        if (var2.layout.marginBottom > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.marginBottom));
            var6.append("dp");
            var1.a("android", "layout_marginBottom", var6.toString());
        }

    }

    public void h(Nx var1, ViewBean var2) {
        int var3 = var2.layout.orientation;
        if (var3 == 0) {
            var1.a("android", "orientation", "horizontal");
        } else if (var3 == 1) {
            var1.a("android", "orientation", "vertical");
        }

    }

    public void i(Nx var1, ViewBean var2) {
        LayoutBean var3 = var2.layout;
        int var4 = var3.paddingLeft;
        StringBuilder var6;
        if (var4 == var3.paddingRight) {
            int var5 = var3.paddingTop;
            if (var5 == var3.paddingBottom && var4 == var5 && var4 > 0) {
                var6 = new StringBuilder();
                var6.append(String.valueOf(var2.layout.paddingLeft));
                var6.append("dp");
                var1.a("android", "padding", var6.toString());
                return;
            }
        }

        if (var2.layout.paddingLeft > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.paddingLeft));
            var6.append("dp");
            var1.a("android", "paddingLeft", var6.toString());
        }

        if (var2.layout.paddingTop > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.paddingTop));
            var6.append("dp");
            var1.a("android", "paddingTop", var6.toString());
        }

        if (var2.layout.paddingRight > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.paddingRight));
            var6.append("dp");
            var1.a("android", "paddingRight", var6.toString());
        }

        if (var2.layout.paddingBottom > 0) {
            var6 = new StringBuilder();
            var6.append(String.valueOf(var2.layout.paddingBottom));
            var6.append("dp");
            var1.a("android", "paddingBottom", var6.toString());
        }

    }

    public void j(Nx var1, ViewBean var2) {
        String var3 = var2.text.text;
        if (var3 != null && var3.length() > 0) {
            var1.a("android", "text", this.a(var2.text.text));
        }

        if (var2.text.textSize > 0) {
            StringBuilder var5 = new StringBuilder();
            var5.append(String.valueOf(var2.text.textSize));
            var5.append("sp");
            var1.a("android", "textSize", var5.toString());
        }

        int var4 = var2.text.textType;
        if (var4 == TextBean.TEXT_TYPE_BOLD) {
            var1.a("android", "textStyle", "bold");
        } else if (var4 == TextBean.TEXT_TYPE_ITALIC) {
            var1.a("android", "textStyle", "italic");
        } else if (var4 == TextBean.TEXT_TYPE_BOLDITALIC) {
            var1.a("android", "textStyle", "bold|italic");
        }

        if (var2.text.textColor != 0) {
            var1.a("android", "textColor", String.format("#%06X", var2.text.textColor & 16777215));
        }

        if (var2.type == 5 || var2.type == 23 || var2.type == 24) {
            var3 = var2.text.hint;
            if (var3 != null && var3.length() > 0) {
                var1.a("android", "hint", this.a(var2.text.hint));
            }

            if (var2.text.hintColor != 0) {
                var1.a("android", "textColorHint", String.format("#%06X", var2.text.hintColor & 16777215));
            }

            if (var2.text.singleLine != 0) {
                var1.a("android", "singleLine", "true");
            }

            var4 = var2.text.line;
            if (var4 > 0) {
                var1.a("android", "lines", String.valueOf(var4));
            }

            var4 = var2.text.inputType;
            if (var4 != TextBean.INPUT_TYPE_TEXT) {
                var1.a("android", "inputType", sq.a("property_input_type", var4));
            }

            var4 = var2.text.imeOption;
            if (var4 != TextBean.IME_OPTION_NORMAL) {
                if (var4 == TextBean.IME_OPTION_NONE) {
                    var1.a("android", "imeOptions", "actionNone");
                } else if (var4 == TextBean.IME_OPTION_GO) {
                    var1.a("android", "imeOptions", "actionGo");
                } else if (var4 == TextBean.IME_OPTION_SEARCH) {
                    var1.a("android", "imeOptions", "actionSearch");
                } else if (var4 == TextBean.IME_OPTION_SEND) {
                    var1.a("android", "imeOptions", "actionSend");
                } else if (var4 == TextBean.IME_OPTION_NEXT) {
                    var1.a("android", "imeOptions", "actionNext");
                } else if (var4 == TextBean.IME_OPTION_DONE) {
                    var1.a("android", "imeOptions", "actionDone");
                }
            }
        }

        if (var2.type == 4) {
            if (var2.text.singleLine != 0) {
                var1.a("android", "singleLine", "true");
            }

            var4 = var2.text.line;
            if (var4 > 0) {
                var1.a("android", "lines", String.valueOf(var4));
            }
        }

    }

    public void k(Nx var1, ViewBean var2) {
        if (var2.enabled == 0) {
            var1.a("android", "enabled", "false");
        }

        if (var2.clickable == 0) {
            var1.a("android", "clickable", "false");
        }

        int var3 = var2.image.rotate;
        if (var3 != 0) {
            var1.a("android", "rotation", String.valueOf(var3));
        }

        float var4 = var2.alpha;
        if (1.0F != var4) {
            var1.a("android", "alpha", String.valueOf(var4));
        }

        StringBuilder var5;
        if (0.0F != var2.translationX) {
            var5 = new StringBuilder();
            var5.append(String.valueOf(var2.translationX));
            var5.append("dp");
            var1.a("android", "translationX", var5.toString());
        }

        if (0.0F != var2.translationY) {
            var5 = new StringBuilder();
            var5.append(String.valueOf(var2.translationY));
            var5.append("dp");
            var1.a("android", "translationY", var5.toString());
        }

        var4 = var2.scaleX;
        if (1.0F != var4) {
            var1.a("android", "scaleX", String.valueOf(var4));
        }

        var4 = var2.scaleY;
        if (1.0F != var4) {
            var1.a("android", "scaleY", String.valueOf(var4));
        }

        var3 = var2.type;
        if ((var3 == 11 || var3 == 13) && 1 == var2.checked) {
            var1.a("android", "checked", "true");
        }

        if (var2.type == 14) {
            var3 = var2.progress;
            if (var3 > 0) {
                var1.a("android", "progress", String.valueOf(var3));
            }

            var3 = var2.max;
            if (100 != var3) {
                var1.a("android", "max", String.valueOf(var3));
            }
        }

        if (var2.type == 15) {
            var3 = var2.firstDayOfWeek;
            if (var3 != 1) {
                var1.a("android", "firstDayOfWeek", String.valueOf(var3));
            }
        }

        if (var2.type == 10) {
            var3 = var2.spinnerMode;
            if (var3 == 1) {
                var1.a("android", "spinnerMode", "dropdown");
            } else if (var3 == 0) {
                var1.a("android", "spinnerMode", "dialog");
            }
        }

        if (var2.type == 9) {
            if (1 != var2.dividerHeight) {
                var5 = new StringBuilder();
                var5.append(var2.dividerHeight);
                var5.append("dp");
                var1.a("android", "dividerHeight", var5.toString());
            }

            if (var2.dividerHeight == 0) {
                var1.a("android", "divider", "@null");
            }

            var3 = var2.choiceMode;
            if (var3 == 0) {
                var1.a("android", "choiceMode", "none");
            } else if (var3 == 1) {
                var1.a("android", "choiceMode", "singleChoice");
            } else if (var3 == 2) {
                var1.a("android", "choiceMode", "multipleChoice");
            }
        }

        String var6;
        if (var2.type == 17) {
            var6 = var2.adSize;
            if (var6 != null && var6.length() > 0) {
                var1.a("app", "adSize", var2.adSize);
            } else {
                var1.a("app", "adSize", "SMART_BANNER");
            }

            jq var7 = this.a;
            if (var7.f) {
                var1.a("app", "adUnitId", var7.r);
            } else {
                var1.a("app", "adUnitId", "ca-app-pub-3940256099942544/6300978111");
            }
        }

        if (var2.type == 8) {
            var3 = var2.progress;
            if (var3 > 0) {
                var1.a("android", "progress", String.valueOf(var3));
            }

            var3 = var2.max;
            if (100 != var3) {
                var1.a("android", "max", String.valueOf(var3));
            }

            var6 = var2.indeterminate;
            if (var6 != null && var6.length() > 0) {
                var1.a("android", "indeterminate", var2.indeterminate);
            }

            var6 = var2.progressStyle;
            if (var6 != null && var6.length() > 0) {
                var1.a((String)null, "style", var2.progressStyle);
            }
        }

    }

    public void l(Nx var1, ViewBean var2) {
        int var3 = var2.layout.weight;
        if (var3 > 0) {
            var1.a("android", "layout_weight", String.valueOf(var3));
        }

    }

    public void m(Nx var1, ViewBean var2) {
        int var3 = var2.layout.weightSum;
        if (var3 > 0) {
            var1.a("android", "weightSum", String.valueOf(var3));
        }

    }

    public void n(Nx var1, ViewBean var2) {
        var1.a("app", "tabGravity", "fill");
        var1.a("app", "tabMode", "fixed");
        var1.a("app", "tabIndicatorHeight", "3dp");
        var1.a("app", "tabIndicatorColor", "@android:color/white");
        var1.a("app", "tabSelectedTextColor", "@android:color/white");
        var1.a("app", "tabTextColor", "@android:color/white");
        var1.a("app", "tabTextAppearance", "@android:style/TextAppearance.Widget.TabWidget");
    }

    public void o(Nx var1, ViewBean var2) {
        if (var2.text.textSize > 0) {
            StringBuilder var3 = new StringBuilder();
            var3.append(String.valueOf(var2.text.textSize));
            var3.append("sp");
            var1.a("app", "sidebar_text_size", var3.toString());
        }

        if (var2.text.textColor != 0) {
            var1.a("app", "sidebar_text_color", String.format("#%06X", var2.text.textColor & 16777215));
        }

    }
}
