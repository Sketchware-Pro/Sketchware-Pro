package a.a.a;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.ImageBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.TextBean;
import com.besome.sketch.beans.ViewBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import dev.aldi.sayuti.editor.injection.AppCompatInjection;
import mod.agus.jcoderz.beans.ViewBeans;
import mod.jbk.util.LogUtil;

public class Ox {

    private final jq buildConfig;
    private final AppCompatInjection aci;
    private final ProjectFileBean projectFile;
    private ViewBean fab;
    private ArrayList<ViewBean> views;
    private Nx rootLayout = null;
    private Nx collapsingToolbarLayout = null;

    public Ox(jq jq, ProjectFileBean projectFileBean) {
        buildConfig = jq;
        projectFile = projectFileBean;
        aci = new AppCompatInjection(jq, projectFileBean);
    }

    private static String x(String str) {
        if (!str.contains(".")) {
            return str;
        }
        String[] split = str.split("\\.");
        return split[split.length - 1];
    }

    /**
     * @return The parameter String escaped properly for XML strings
     */
    private String escapeXML(String str) {
        CharBuffer buffer = CharBuffer.wrap(str);
        StringBuilder result = new StringBuilder(str.length());
        while (buffer.hasRemaining()) {
            char got = buffer.get();
            switch (got) {
                case '?':
                    result.append("\\?");
                    break;

                case '@':
                    result.append("\\@");
                    break;

                case '\"':
                    result.append("&quot;");
                    break;

                case '&':
                    result.append("&amp;");
                    break;

                case '<':
                    result.append("&lt;");
                    break;

                case '>':
                    result.append("&gt;");
                    break;

                case '\n':
                    result.append("\\n");
                    break;

                default:
                    result.append(got);
                    break;
            }
        }
        return result.toString();
    }

    private void writeRootLayout() {
        Nx nx = new Nx("LinearLayout");
        nx.addAttribute("android", "layout_width", "match_parent");
        nx.addAttribute("android", "layout_height", "match_parent");
        nx.addAttribute("android", "orientation", "vertical");
        for (ViewBean viewBean : views) {
            String parent = viewBean.parent;
            if (parent == null || parent.length() <= 0 || parent.equals("root")) {
                writeWidget(nx, viewBean);
            }
        }
        if (buildConfig.g) {
            if (projectFile.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                    nx.addAttribute("app", "layout_behavior", "@string/appbar_scrolling_view_behavior");
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)
                        || projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    Nx coordinatorLayoutTag = new Nx("androidx.coordinatorlayout.widget.CoordinatorLayout");
                    coordinatorLayoutTag.addAttribute("android", "id", "@+id/_coordinator");
                    aci.inject(coordinatorLayoutTag, "CoordinatorLayout");
                    rootLayout = coordinatorLayoutTag;
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                    Nx toolbarTag = new Nx("androidx.appcompat.widget.Toolbar");
                    toolbarTag.addAttribute("android", "id", "@+id/_toolbar");
                    aci.inject(toolbarTag, "Toolbar");
                    Nx appBarLayoutTag = new Nx("com.google.android.material.appbar.AppBarLayout");
                    appBarLayoutTag.addAttribute("android", "id", "@+id/_app_bar");
                    aci.inject(appBarLayoutTag, "AppBarLayout");
                    if (collapsingToolbarLayout != null) {
                        collapsingToolbarLayout.a(toolbarTag);
                        appBarLayoutTag.a(collapsingToolbarLayout);
                    } else {
                        appBarLayoutTag.a(toolbarTag);
                    }
                    rootLayout.a(appBarLayoutTag);
                    rootLayout.a(nx);
                } else {
                    if (rootLayout == null) {
                        rootLayout = nx;
                    } else {
                        rootLayout.a(nx);
                    }
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FAB)) {
                    writeFabView(rootLayout, fab);
                }
                if (fab.type == ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW) {
                    writeWidget(rootLayout, fab);
                }
                if (projectFile.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                    Nx drawerLayoutTag = new Nx("androidx.drawerlayout.widget.DrawerLayout");
                    drawerLayoutTag.addAttribute("android", "id", "@+id/_drawer");
                    aci.inject(drawerLayoutTag, "DrawerLayout");
                    drawerLayoutTag.a(rootLayout);
                    Nx linearLayoutTag = new Nx("LinearLayout");
                    linearLayoutTag.addAttribute("android", "id", "@+id/_nav_view");
                    aci.inject(linearLayoutTag, "NavigationDrawer");
                    Nx includeTag = new Nx("include", true);
                    includeTag.addAttribute("", "layout", "@layout/_drawer_" + projectFile.fileName);
                    linearLayoutTag.a(includeTag);
                    drawerLayoutTag.a(linearLayoutTag);
                    rootLayout = drawerLayoutTag;
                }
            } else {
                rootLayout = nx;
            }
        } else {
            rootLayout = nx;
        }
        rootLayout.addNamespaceDeclaration(0, "xmlns", "tools", "http://schemas.android.com/tools");
        rootLayout.addNamespaceDeclaration(0, "xmlns", "app", "http://schemas.android.com/apk/res-auto");
        rootLayout.addNamespaceDeclaration(0, "xmlns", "android", "http://schemas.android.com/apk/res/android");
    }

    private void writeBackgroundResource(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        String backgroundResource = viewBean.layout.backgroundResource;
        if (backgroundResource == null || "NONE".equalsIgnoreCase(backgroundResource)) {
            int backgroundColor = viewBean.layout.backgroundColor;
            if (backgroundColor != 0xffffff) {
                if (backgroundColor != 0) {
                    int color = backgroundColor & 0xffffff;
                    if (nx.c().equals("BottomAppBar")) {
                        if (!toNotAdd.contains("app:backgroundTint")) {
                            nx.addAttribute("app", "backgroundTint", String.format("#%06X", color));
                        }
                    } else if (nx.c().equals("CollapsingToolbarLayout")) {
                        if (!toNotAdd.contains("app:contentScrim")) {
                            nx.addAttribute("app", "contentScrim", String.format("#%06X", color));
                        }
                    } else {
                        if (!hasAttr("background", viewBean) && !toNotAdd.contains("android:background")) {
                            nx.addAttribute("android", "background", String.format("#%06X", color));
                        }
                    }
                } else if (nx.c().equals("BottomAppBar")) {
                    if (!toNotAdd.contains("android:backgroundTint")) {
                        nx.addAttribute("android", "backgroundTint", "@android:color/transparent");
                    }
                } else if (nx.c().equals("CollapsingToolbarLayout")) {
                    if (!toNotAdd.contains("app:contentScrim")) {
                        nx.addAttribute("app", "contentScrim", "?attr/colorPrimary");
                    }
                } else {
                    if (!toNotAdd.contains("android:background")) {
                        nx.addAttribute("android", "background", "@android:color/transparent");
                    }
                }
            }
        } else {
            if (!hasAttr("background", viewBean) && !toNotAdd.contains("android:background")) {
                boolean isNinePatchBackground = backgroundResource.endsWith(".9");
                nx.addAttribute("android", "background", "@drawable/" +
                        (isNinePatchBackground ? backgroundResource.replaceAll("\\.9", "") :
                                backgroundResource));
            }
        }
    }

    public void a(ArrayList<ViewBean> arrayList) {
        a(arrayList, null);
    }

    public void a(ArrayList<ViewBean> arrayList, ViewBean viewBean) {
        fab = viewBean;
        views = arrayList;
        writeRootLayout();
    }

    public String b() {
        return rootLayout.toCode();
    }

    private void writeWidget(Nx nx, ViewBean viewBean) {
        viewBean.getClassInfo().a();
        String convert = viewBean.convert;
        Set<String> toNotAdd = readAttributesToReplace(viewBean);

        Nx widgetTag = convert.equals("") ? new Nx(viewBean.getClassInfo().a()) :
                new Nx(convert.replaceAll(" ", ""));
        if (convert.equals("include")) {
            if (!toNotAdd.contains("layout")) {
                widgetTag.addAttribute("", "layout", "@layout/" + viewBean.id);
            }
        } else {
            if (!toNotAdd.contains("android:id")) {
                widgetTag.addAttribute("android", "id", "@+id/" + viewBean.id);
            }
            int type = viewBean.type;
            if (projectFile.fileType == ProjectFileBean.PROJECT_FILE_TYPE_CUSTOM_VIEW) {
                switch (type) {
                    case ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW:
                    case ViewBean.VIEW_TYPE_WIDGET_EDITTEXT:
                    case ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW:
                    case ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR:
                    case ViewBean.VIEW_TYPE_WIDGET_CHECKBOX:
                    case ViewBean.VIEW_TYPE_WIDGET_SWITCH:
                    case ViewBean.VIEW_TYPE_WIDGET_SEEKBAR:
                    case ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW:
                    case ViewBeans.VIEW_TYPE_WIDGET_RADIOBUTTON:
                    case ViewBeans.VIEW_TYPE_WIDGET_SEARCHVIEW:
                    case ViewBeans.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW:
                    case ViewBeans.VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW:
                    case ViewBeans.VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW:
                        if (!hasAttr("focusable", viewBean) && !toNotAdd.contains("android:focusable")) {
                            widgetTag.addAttribute("android", "focusable", "false");
                        }
                        break;

                    default:
                        break;
                }
            }

            if (!toNotAdd.contains("android:layout_width")) {
                int width = viewBean.layout.width;
                if (width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    widgetTag.addAttribute("android", "layout_width", "match_parent");
                } else if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    widgetTag.addAttribute("android", "layout_width", "wrap_content");
                } else {
                    widgetTag.addAttribute("android", "layout_width", width + "dp");
                }
            }

            if (!toNotAdd.contains("android:layout_height")) {
                int height = viewBean.layout.height;
                if (height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    widgetTag.addAttribute("android", "layout_height", "match_parent");
                } else if (height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                    widgetTag.addAttribute("android", "layout_height", "wrap_content");
                } else {
                    widgetTag.addAttribute("android", "layout_height", height + "dp");
                }
            }

            writeLayoutMargin(widgetTag, viewBean);
            writeViewPadding(widgetTag, viewBean);
            writeBackgroundResource(widgetTag, viewBean);
            if (viewBean.getClassInfo().a("ViewGroup")) {
                writeViewGravity(widgetTag, viewBean);
            }
        }
        buildConfig.x.handleWidget(x(viewBean.convert));
        if (viewBean.getClassInfo().a("LinearLayout") &&
                !widgetTag.c().matches("(BottomAppBar|NavigationView|Coordinator|Floating|Collaps|include)\\w*")) {
            if (!toNotAdd.contains("android:orientation")) {
                int orientation = viewBean.layout.orientation;
                if (orientation == LinearLayout.HORIZONTAL) {
                    widgetTag.addAttribute("android", "orientation", "horizontal");
                } else if (orientation == LinearLayout.VERTICAL) {
                    widgetTag.addAttribute("android", "orientation", "vertical");
                }
            }

            if (!toNotAdd.contains("android:weightSum")) {
                int weightSum = viewBean.layout.weightSum;
                if (weightSum > 0) {
                    widgetTag.addAttribute("android", "weightSum", String.valueOf(weightSum));
                }
            }
        }
        if (viewBean.getClassInfo().a("TextView")) {
            writeViewGravity(widgetTag, viewBean);
            writeTextAttributes(widgetTag, viewBean);
        }
        if (viewBean.getClassInfo().a("ImageView")) {
            writeImgSrcAttr(widgetTag, viewBean);
            if (!widgetTag.toCode().contains(".")) {
                writeImageScaleType(widgetTag, viewBean);
            }
        }
        if (viewBean.getClassInfo().b("SeekBar")) {
            writeViewGravity(widgetTag, viewBean);
        }
        if (viewBean.getClassInfo().b("ProgressBar")) {
            writeViewGravity(widgetTag, viewBean);
        }
        if (viewBean.getClassInfo().b("WaveSideBar")) {
            int textSize = viewBean.text.textSize;
            if (textSize > 0 && !toNotAdd.contains("app:sidebar_text_size")) {
                widgetTag.addAttribute("app", "sidebar_text_size", textSize + "sp");
            }

            int textColor = viewBean.text.textColor;
            if (textColor != 0 && !toNotAdd.contains("app:sidebar_text_color")) {
                widgetTag.addAttribute("app", "sidebar_text_color", String.format("#%06X", textColor & 0xffffff));
            }
        }
        k(widgetTag, viewBean);
        int parentViewType = viewBean.parentType;
        if (!viewBean.convert.equals("include")) {
            if (parentViewType == ViewBean.VIEW_TYPE_LAYOUT_LINEAR) {
                writeLayoutGravity(widgetTag, viewBean);
                int weight = viewBean.layout.weight;
                if (weight > 0 && !toNotAdd.contains("android:layout_weight")) {
                    widgetTag.addAttribute("android", "layout_weight", String.valueOf(weight));
                }
            } else if (parentViewType == ViewBean.VIEW_TYPE_LAYOUT_HSCROLLVIEW || parentViewType == ViewBean.VIEW_TYPE_LAYOUT_VSCROLLVIEW) {
                writeLayoutGravity(widgetTag, viewBean);
            }
        }
        if (viewBean.getClassInfo().a("ViewGroup")) {
            for (ViewBean bean : views) {
                if (bean.parent != null && bean.parent.equals(viewBean.id)) {
                    writeWidget(widgetTag, bean);
                }
            }
        }
        if (!viewBean.inject.equals("")) {
            widgetTag.addAttributeValue(viewBean.inject.replaceAll(" ", ""));
        }
        if (widgetTag.c().equals("CollapsingToolbarLayout")) {
            collapsingToolbarLayout = widgetTag;
        } else {
            nx.a(widgetTag);
        }
    }

    private void writeFabView(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        Nx floatingActionButtonTag = new Nx("com.google.android.material.floatingactionbutton.FloatingActionButton");
        if (!toNotAdd.contains("android:id")) {
            floatingActionButtonTag.addAttribute("android", "id", "@+id/" + viewBean.id);
        }
        if (!toNotAdd.contains("android:layout_width")) {
            floatingActionButtonTag.addAttribute("android", "layout_width", "wrap_content");
        }
        if (!toNotAdd.contains("android:layout_height")) {
            floatingActionButtonTag.addAttribute("android", "layout_height", "wrap_content");
        }
        writeLayoutMargin(floatingActionButtonTag, viewBean);
        writeLayoutGravity(floatingActionButtonTag, viewBean);

        String resName = viewBean.image.resName;
        if (resName != null && resName.length() > 0 && !resName.equals("NONE") &&
                !toNotAdd.contains("app:srcCompat")) {
            floatingActionButtonTag.addAttribute("app", "srcCompat", "@drawable/" + resName.toLowerCase());
        }
        if (viewBean.id.equals("_fab")) {
            aci.inject(floatingActionButtonTag, "FloatingActionButton");
        }
        k(floatingActionButtonTag, viewBean);
        nx.a(floatingActionButtonTag);
    }

    private void writeViewGravity(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        if (!toNotAdd.contains("android:gravity")) {
            int gravity = viewBean.layout.gravity;
            if (gravity != Gravity.NO_GRAVITY) {
                String attrValue = "";
                int verticalGravity = gravity & Gravity.FILL_VERTICAL;
                int horizontalGravity = gravity & Gravity.FILL_HORIZONTAL;
                if (horizontalGravity == Gravity.CENTER_HORIZONTAL) {
                    attrValue = "center_horizontal";
                } else {
                    if ((horizontalGravity & Gravity.LEFT) == Gravity.LEFT) {
                        attrValue = "left";
                    }
                    if ((horizontalGravity & Gravity.RIGHT) == Gravity.RIGHT) {
                        if (attrValue.length() > 0) {
                            attrValue += "|";
                        }
                        attrValue += "right";
                    }
                }
                if (verticalGravity == Gravity.CENTER_VERTICAL) {
                    if (attrValue.length() > 0) {
                        attrValue += "|";
                    }
                    attrValue += "center_vertical";
                } else {
                    if ((verticalGravity & Gravity.TOP) == Gravity.TOP) {
                        if (attrValue.length() > 0) {
                            attrValue += "|";
                        }
                        attrValue += "top";
                    }
                    if ((verticalGravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                        if (attrValue.length() > 0) {
                            attrValue += "|";
                        }
                        attrValue += "bottom";
                    }
                }
                nx.addAttribute("android", "gravity", attrValue);
            }
        }
    }

    private void writeImgSrcAttr(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        String resName = viewBean.image.resName;
        if (resName.length() > 0 && !"NONE".equals(resName)) {
            String value = "@drawable/" + resName.toLowerCase();
            if (nx.c().equals("FloatingActionButton")) {
                if (!toNotAdd.contains("app:srcCompat")) {
                    nx.addAttribute("app", "srcCompat", value);
                }
            } else {
                if (!toNotAdd.contains("android:src")) {
                    nx.addAttribute("android", "src", value);
                }
            }
        }
    }

    /**
     * @see ImageView.ScaleType
     */
    private void writeImageScaleType(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        if (!toNotAdd.contains("android:scaleType")) {
            if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER)) {
                nx.addAttribute("android", "scaleType", "center");
            } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_XY)) {
                nx.addAttribute("android", "scaleType", "fitXY");
            } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_START)) {
                nx.addAttribute("android", "scaleType", "fitStart");
            } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_END)) {
                nx.addAttribute("android", "scaleType", "fitEnd");
            } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_FIT_CENTER)) {
                nx.addAttribute("android", "scaleType", "fitCenter");
            } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_CROP)) {
                nx.addAttribute("android", "scaleType", "centerCrop");
            } else if (viewBean.image.scaleType.equals(ImageBean.SCALE_TYPE_CENTER_INSIDE)) {
                nx.addAttribute("android", "scaleType", "centerInside");
            }
        }
    }

    /**
     * @see Gravity
     */
    private void writeLayoutGravity(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        if (!toNotAdd.contains("android:layout_gravity")) {
            int gravity = viewBean.layout.layoutGravity;
            if (gravity != Gravity.NO_GRAVITY) {
                String attrValue = "";
                int verticalGravity = gravity & Gravity.FILL_VERTICAL;
                int horizontalGravity = gravity & Gravity.FILL_HORIZONTAL;
                if (horizontalGravity == Gravity.CENTER_HORIZONTAL) {
                    attrValue = "center_horizontal";
                } else {
                    if ((horizontalGravity & Gravity.LEFT) == Gravity.LEFT) {
                        attrValue = "left";
                    }
                    if ((horizontalGravity & Gravity.RIGHT) == Gravity.RIGHT) {
                        if (attrValue.length() > 0) {
                            attrValue += "|";
                        }
                        attrValue += "right";
                    }
                }
                if (verticalGravity == Gravity.CENTER_VERTICAL) {
                    if (attrValue.length() > 0) {
                        attrValue += "|";
                    }
                    attrValue += "center_vertical";
                } else {
                    if ((verticalGravity & Gravity.TOP) == Gravity.TOP) {
                        if (attrValue.length() > 0) {
                            attrValue += "|";
                        }
                        attrValue += "top";
                    }
                    if ((verticalGravity & Gravity.BOTTOM) == Gravity.BOTTOM) {
                        if (attrValue.length() > 0) {
                            attrValue += "|";
                        }
                        attrValue += "bottom";
                    }
                }
                nx.addAttribute("android", "layout_gravity", attrValue);
            }
        }
    }

    /**
     * @see ViewGroup.MarginLayoutParams
     */
    private void writeLayoutMargin(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        LayoutBean layoutBean = viewBean.layout;
        int marginLeft = layoutBean.marginLeft;
        int marginTop = layoutBean.marginTop;
        int marginRight = layoutBean.marginRight;
        int marginBottom = layoutBean.marginBottom;

        if (marginLeft == marginRight && marginTop == marginBottom
                && marginLeft == marginTop && marginLeft > 0) {
            if (!toNotAdd.contains("android:layout_margin")) {
                nx.addAttribute("android", "layout_margin", marginLeft + "dp");
            }
            return;
        }

        if (marginLeft > 0 && !toNotAdd.contains("android:layout_marginLeft")) {
            nx.addAttribute("android", "layout_marginLeft", marginLeft + "dp");
        }
        if (viewBean.layout.marginTop > 0 && !toNotAdd.contains("android:layout_marginTop")) {
            nx.addAttribute("android", "layout_marginTop", viewBean.layout.marginTop + "dp");
        }
        if (marginRight > 0 && !toNotAdd.contains("android:layout_marginRight")) {
            nx.addAttribute("android", "layout_marginRight", marginRight + "dp");
        }
        if (marginBottom > 0 && !toNotAdd.contains("android:layout_marginBottom")) {
            nx.addAttribute("android", "layout_marginBottom", marginBottom + "dp");
        }
    }

    /**
     * @see View#getPaddingLeft()
     * @see View#getPaddingTop()
     * @see View#getPaddingRight()
     * @see View#getPaddingBottom()
     */
    private void writeViewPadding(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        LayoutBean layoutBean = viewBean.layout;
        int paddingLeft = layoutBean.paddingLeft;
        int paddingTop = layoutBean.paddingTop;
        int paddingRight = layoutBean.paddingRight;
        int paddingBottom = layoutBean.paddingBottom;

        if (paddingLeft == paddingRight && paddingTop == paddingBottom
                && paddingLeft == paddingTop && paddingLeft > 0) {
            if (!toNotAdd.contains("android:padding")) {
                nx.addAttribute("android", "padding", paddingLeft + "dp");
            }
            return;
        }

        if (paddingLeft > 0 && !toNotAdd.contains("android:paddingLeft")) {
            nx.addAttribute("android", "paddingLeft", paddingLeft + "dp");
        }
        if (paddingTop > 0 && !toNotAdd.contains("android:paddingTop")) {
            nx.addAttribute("android", "paddingTop", paddingTop + "dp");
        }
        if (paddingRight > 0 && !toNotAdd.contains("android:paddingRight")) {
            nx.addAttribute("android", "paddingRight", paddingRight + "dp");
        }
        if (paddingBottom > 0 && !toNotAdd.contains("android:paddingBottom")) {
            nx.addAttribute("android", "paddingBottom", paddingBottom + "dp");
        }
    }

    private void writeTextAttributes(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        String text = viewBean.text.text;
        if (text != null && text.length() > 0 && !toNotAdd.contains("android:text")) {
            if (text.startsWith("@")) {
                nx.addAttribute("android", "text", text);
            } else {
                nx.addAttribute("android", "text", escapeXML(text));
            }
        }

        int textSize = viewBean.text.textSize;
        if (textSize > 0 && !toNotAdd.contains("android:textSize")) {
            nx.addAttribute("android", "textSize", textSize + "sp");
        }
        if (!toNotAdd.contains("android:textStyle")) {
            int textType = viewBean.text.textType;
            if (textType == TextBean.TEXT_TYPE_BOLD) {
                nx.addAttribute("android", "textStyle", "bold");
            } else if (textType == TextBean.TEXT_TYPE_ITALIC) {
                nx.addAttribute("android", "textStyle", "italic");
            } else if (textType == TextBean.TEXT_TYPE_BOLDITALIC) {
                nx.addAttribute("android", "textStyle", "bold|italic");
            }
        }
        if (viewBean.text.textColor != 0) {
            if (!hasAttr("textColor", viewBean) && !toNotAdd.contains("android:textColor")) {
                nx.addAttribute("android", "textColor", String.format("#%06X", viewBean.text.textColor & 0xffffff));
            }
        }
        switch (viewBean.type) {
            case ViewBean.VIEW_TYPE_WIDGET_EDITTEXT:
            case ViewBeans.VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW:
            case ViewBeans.VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW:
                String hint = viewBean.text.hint;
                if (hint != null && hint.length() > 0 && !toNotAdd.contains("android:hint")) {
                    if (hint.startsWith("@")) {
                        nx.addAttribute("android", "hint", hint);
                    } else {
                        nx.addAttribute("android", "hint", escapeXML(hint));
                    }
                }
                if (viewBean.text.hintColor != 0) {
                    if (!hasAttr("textColorHint", viewBean) && !toNotAdd.contains("android:textColorHint")) {
                        nx.addAttribute("android", "textColorHint", String.format("#%06X", viewBean.text.hintColor & 0xffffff));
                    }
                }
                if (viewBean.text.singleLine != 0 && !toNotAdd.contains("android:singleLine")) {
                    nx.addAttribute("android", "singleLine", "true");
                }

                int line = viewBean.text.line;
                if (line > 0 && !toNotAdd.contains("android:lines")) {
                    nx.addAttribute("android", "lines", String.valueOf(line));
                }

                int inputType = viewBean.text.inputType;
                if (inputType != TextBean.INPUT_TYPE_TEXT && !toNotAdd.contains("android:inputType")) {
                    nx.addAttribute("android", "inputType", sq.a("property_input_type", inputType));
                }

                int imeOption = viewBean.text.imeOption;
                if (imeOption != TextBean.IME_OPTION_NORMAL && !toNotAdd.contains("android:imeOptions")) {
                    if (imeOption == TextBean.IME_OPTION_NONE) {
                        nx.addAttribute("android", "imeOptions", "actionNone");
                    } else if (imeOption == TextBean.IME_OPTION_GO) {
                        nx.addAttribute("android", "imeOptions", "actionGo");
                    } else if (imeOption == TextBean.IME_OPTION_SEARCH) {
                        nx.addAttribute("android", "imeOptions", "actionSearch");
                    } else if (imeOption == TextBean.IME_OPTION_SEND) {
                        nx.addAttribute("android", "imeOptions", "actionSend");
                    } else if (imeOption == TextBean.IME_OPTION_NEXT) {
                        nx.addAttribute("android", "imeOptions", "actionNext");
                    } else if (imeOption == TextBean.IME_OPTION_DONE) {
                        nx.addAttribute("android", "imeOptions", "actionDone");
                    }
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_TEXTVIEW:
                if (viewBean.text.singleLine != 0 && !toNotAdd.contains("android:singleLine")) {
                    nx.addAttribute("android", "singleLine", "true");
                }
                line = viewBean.text.line;
                if (line > 0 && !toNotAdd.contains("android:lines")) {
                    nx.addAttribute("android", "lines", String.valueOf(line));
                }
                break;
        }
    }

    private void k(Nx nx, ViewBean viewBean) {
        Set<String> toNotAdd = readAttributesToReplace(viewBean);
        if (viewBean.enabled == 0 && !toNotAdd.contains("android:enabled")) {
            nx.addAttribute("android", "enabled", "false");
        }
        if (viewBean.clickable == 0 && !toNotAdd.contains("android:clickable")) {
            nx.addAttribute("android", "clickable", "false");
        }
        int rotate = viewBean.image.rotate;
        if (rotate != 0 && !toNotAdd.contains("android:rotation")) {
            nx.addAttribute("android", "rotation", String.valueOf(rotate));
        }
        float alpha = viewBean.alpha;
        if (1.0f != alpha && !toNotAdd.contains("android:alpha")) {
            nx.addAttribute("android", "alpha", String.valueOf(alpha));
        }
        if (0.0f != viewBean.translationX && !toNotAdd.contains("android:translationX")) {
            nx.addAttribute("android", "translationX", viewBean.translationX + "dp");
        }
        if (0.0f != viewBean.translationY && !toNotAdd.contains("android:translationY")) {
            nx.addAttribute("android", "translationY", viewBean.translationY + "dp");
        }
        float scaleX = viewBean.scaleX;
        if (1.0f != scaleX && !toNotAdd.contains("android:scaleX")) {
            nx.addAttribute("android", "scaleX", String.valueOf(scaleX));
        }
        float scaleY = viewBean.scaleY;
        if (1.0f != scaleY && !toNotAdd.contains("android:scaleY")) {
            nx.addAttribute("android", "scaleY", String.valueOf(scaleY));
        }

        switch (viewBean.type) {
            case ViewBean.VIEW_TYPE_WIDGET_CHECKBOX:
            case ViewBean.VIEW_TYPE_WIDGET_SWITCH:
            case ViewBeans.VIEW_TYPE_WIDGET_RADIOBUTTON:
                if (viewBean.checked == 1 && !toNotAdd.contains("android:checked")) {
                    nx.addAttribute("android", "checked", "true");
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_SEEKBAR:
                int progress = viewBean.progress;
                if (progress > ViewBean.DEFAULT_PROGRESS && !toNotAdd.contains("android:progress")) {
                    nx.addAttribute("android", "progress", String.valueOf(progress));
                }

                int max = viewBean.max;
                if (max != ViewBean.DEFAULT_MAX && !toNotAdd.contains("android:max")) {
                    nx.addAttribute("android", "max", String.valueOf(max));
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW:
                int firstDayOfWeek = viewBean.firstDayOfWeek;
                if (firstDayOfWeek != 1 && !toNotAdd.contains("android:firstDayOfWeek")) {
                    nx.addAttribute("android", "firstDayOfWeek", String.valueOf(firstDayOfWeek));
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_SPINNER:
                int spinnerMode = viewBean.spinnerMode;
                if (!toNotAdd.contains("android:spinnerMode")) {
                    if (spinnerMode == ViewBean.SPINNER_MODE_DROPDOWN) {
                        nx.addAttribute("android", "spinnerMode", "dropdown");
                    } else if (spinnerMode == ViewBean.SPINNER_MODE_DIALOG) {
                        nx.addAttribute("android", "spinnerMode", "dialog");
                    }
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_LISTVIEW:
                int dividerHeight = viewBean.dividerHeight;
                if (dividerHeight != 1 && !toNotAdd.contains("android:dividerHeight")) {
                    nx.addAttribute("android", "dividerHeight", dividerHeight + "dp");
                }
                if (dividerHeight == 0 && !toNotAdd.contains("android:divider")) {
                    nx.addAttribute("android", "divider", "@null");
                }

                if (!toNotAdd.contains("android:choiceMode")) {
                    switch (viewBean.choiceMode) {
                        case ViewBean.CHOICE_MODE_NONE:
                            nx.addAttribute("android", "choiceMode", "none");
                            break;

                        case ViewBean.CHOICE_MODE_SINGLE:
                            nx.addAttribute("android", "choiceMode", "singleChoice");
                            break;

                        case ViewBean.CHOICE_MODE_MULTI:
                            nx.addAttribute("android", "choiceMode", "multipleChoice");
                            break;
                    }
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_ADVIEW:
                String adSize = viewBean.adSize;
                if (!toNotAdd.contains("app:adSize")) {
                    if (adSize == null || adSize.length() <= 0) {
                        nx.addAttribute("app", "adSize", "SMART_BANNER");
                    } else {
                        nx.addAttribute("app", "adSize", adSize);
                    }
                }

                if (!toNotAdd.contains("app:adUnitId")) {
                    if (buildConfig.isDebugBuild) {
                        nx.addAttribute("app", "adUnitId", "ca-app-pub-3940256099942544/6300978111");
                    } else {
                        nx.addAttribute("app", "adUnitId", buildConfig.bannerAdUnitId);
                    }
                }
                break;

            case ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR:
                progress = viewBean.progress;
                if (progress > ViewBean.DEFAULT_PROGRESS && !toNotAdd.contains("android:progress")) {
                    nx.addAttribute("android", "progress", String.valueOf(progress));
                }

                max = viewBean.max;
                if (max != ViewBean.DEFAULT_MAX && !toNotAdd.contains("android:max")) {
                    nx.addAttribute("android", "max", String.valueOf(max));
                }

                String indeterminate = viewBean.indeterminate;
                if (indeterminate != null && indeterminate.length() > 0 && !toNotAdd.contains("android:indeterminate")) {
                    nx.addAttribute("android", "indeterminate", indeterminate);
                }
                String progressStyle = viewBean.progressStyle;
                if (progressStyle != null && progressStyle.length() > 0 && !toNotAdd.contains("style")) {
                    nx.addAttribute(null, "style", progressStyle);
                }
                break;
        }
    }

    /**
     * check whether the attribute (attrName) is injected to the ViewBean or not.
     */
    private boolean hasAttr(String attrName, ViewBean bean) {
        final String inject = bean.inject;
        if (inject == null || inject.equals("")) return false;
        return Pattern.compile("(android|app) *?: *?" + attrName).matcher(inject).find();
    }

    private Set<String> readAttributesToReplace(ViewBean viewBean) {
        Set<String> toReplace = new HashSet<>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader("<tag xmlns:android=\"http://schemas.android.com/apk/res/android\" " +
                    "xmlns:app=\"http://schemas.android.com/apk/res-auto\" " +
                    "xmlns:tools=\"http://schemas.android.com/tools\"" +
                    viewBean.inject + "></tag>"));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        if ("http://schemas.android.com/tools".equals(parser.getAttributeNamespace(i)) &&
                                "replace".equals(parser.getAttributeName(i))) {
                            toReplace.addAll(Arrays.asList(parser.getAttributeValue(i).split("\\s*,\\s*")));
                        }
                    }
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            LogUtil.e("a.a.a.Ox", "Failed to parse inject property of View " + viewBean, e);
        }

        return toReplace;
    }
}
