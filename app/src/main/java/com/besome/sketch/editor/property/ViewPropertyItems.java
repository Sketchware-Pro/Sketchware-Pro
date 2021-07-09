package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import a.a.a.Cx;
import a.a.a.Gx;
import a.a.a.Kw;
import a.a.a.Lw;
import a.a.a.Pw;
import a.a.a.Vw;
import a.a.a.cC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oq;
import a.a.a.tx;
import a.a.a.xB;

public class ViewPropertyItems extends LinearLayout implements Kw {

    /**
     * The project's sc_id
     */
    public String a;
    public boolean b = false;
    public ViewBean c;
    public Lw d;
    public ProjectFileBean e;
    public HashMap<String, View> f = new HashMap<>();

    public ViewPropertyItems(Context context) {
        super(context);
        a(context);
    }

    public void b() {
        Cx.a().b();
    }

    public void c() {
        Intent intent = new Intent(getContext(), ManageImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", a);
        ((Activity) getContext()).startActivityForResult(intent, 209);
    }

    public void d(ViewBean viewBean) {
        if (getOrientation() == VERTICAL) {
            a(xB.b().a(getResources(), 0x7f0e0764));
        }
        Gx classInfo = viewBean.getClassInfo();
        Gx parentClassInfo = viewBean.getParentClassInfo();
        a(viewBean, "property_layout_width");
        a(viewBean, "property_layout_height");
        a(viewBean, "property_padding");
        a(viewBean, "property_margin");
        if (classInfo.a("LinearLayout")) {
            a(viewBean, "property_orientation");
            a(viewBean, "property_weight_sum");
            a(viewBean, "property_gravity");
        }
        if (classInfo.a("TextView")) {
            a(viewBean, "property_gravity");
        }
        if (parentClassInfo != null) {
            if (parentClassInfo.a("LinearLayout")) {
                a(viewBean, "property_layout_gravity");
                a(viewBean, "property_weight");
            }
            if (parentClassInfo.a("ScrollView") || parentClassInfo.a("HorizontalScrollView")) {
                a(viewBean, "property_layout_gravity");
            }
        }
    }

    public void e(ViewBean viewBean) {
        c = viewBean;
        removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = 3;
        setLayoutParams(layoutParams);
        setGravity(3);
        ArrayList<String> a = Cx.a().a(viewBean.getClassInfo().a());
        if (a == null) {
            a();
            return;
        }
        for (String s : a) {
            a(viewBean, s);
        }
    }

    public void f(ViewBean viewBean) {
        if (!viewBean.id.equals("_fab")) {
            Gx classInfo = viewBean.getClassInfo();
            viewBean.getParentClassInfo();
            a(viewBean, "property_inject");
            a(viewBean, "property_convert");
            if (classInfo.b("Spinner")) {
                a(viewBean, "property_spinner_mode");
            }
            if (classInfo.b("ListView")) {
                a(viewBean, "property_divider_height");
                a(viewBean, "property_custom_view_listview");
            }
            if (classInfo.b("GridView")) {
                a(viewBean, "property_custom_view_listview");
            }
            if (classInfo.b("RecyclerView")) {
                a(viewBean, "property_custom_view_listview");
            }
            if (classInfo.b("ViewPager")) {
                a(viewBean, "property_custom_view_listview");
            }
            if (classInfo.b("Spinner")) {
                a(viewBean, "property_custom_view_listview");
            }
            if (classInfo.b("AutoCompleteTextView")) {
                a(viewBean, "property_hint");
                a(viewBean, "property_hint_color");
            }
            if (classInfo.b("MultiAutoCompleteTextView")) {
                a(viewBean, "property_hint");
                a(viewBean, "property_hint_color");
            }
            if (classInfo.b("WaveSideBar")) {
                a(viewBean, "property_text_size");
                a(viewBean, "property_text_color");
            }
            if (classInfo.a("CompoundButton") && getOrientation() == VERTICAL) {
                a(viewBean, "property_checked");
            }
            if (classInfo.b("SeekBar")) {
                a(viewBean, "property_max");
                a(viewBean, "property_progress");
            }
            if (classInfo.b("CalendarView")) {
                a(viewBean, "property_first_day_of_week");
            }
            if (classInfo.b("AdView")) {
                a(viewBean, "property_ad_size");
            }
            if (classInfo.b("ProgressBar")) {
                a(viewBean, "property_max");
                a(viewBean, "property_progress");
                a(viewBean, "property_progressbar_style");
                a(viewBean, "property_indeterminate");
            }
        }
    }

    public void g(ViewBean viewBean) {
        Gx classInfo = viewBean.getClassInfo();
        if (classInfo.a("TextView")) {
            if (getOrientation() == VERTICAL) {
                a(xB.b().a(getResources(), 0x7f0e0766));
            }
            a(viewBean, "property_text");
            a(viewBean, "property_text_size");
            a(viewBean, "property_text_style");
            a(viewBean, "property_text_color");
            if (classInfo.b("EditText")) {
                a(viewBean, "property_hint");
                a(viewBean, "property_hint_color");
                if (getOrientation() == VERTICAL) {
                    a(viewBean, "property_single_line");
                }
                a(viewBean, "property_lines");
                a(viewBean, "property_input_type");
                a(viewBean, "property_ime_option");
            }
            if (classInfo.b("TextView")) {
                if (getOrientation() == VERTICAL) {
                    a(viewBean, "property_single_line");
                }
                a(viewBean, "property_lines");
            }
        }
    }

    public void h(ViewBean viewBean) {
        Gx classInfo = viewBean.getClassInfo();
        viewBean.getParentClassInfo();
        if (getOrientation() == VERTICAL) {
            if (classInfo.a("ImageView")) {
                a(xB.b().a(getResources(), 0x7f0e0763), new OnClickListener());
                a(viewBean, "property_image");
                a(viewBean, "property_scale_type");
            } else {
                a(xB.b().a(getResources(), 0x7f0e0763));
            }
        } else if (classInfo.a("ImageView")) {
            a(viewBean, "property_image");
            a(viewBean, "property_scale_type");
        }
        if (viewBean.type != 18) {
            a(viewBean, "property_background_resource");
            a(viewBean, "property_background_color");
        }
        if (getOrientation() == VERTICAL && !classInfo.b("LinearLayout")
                && !classInfo.b("ScrollView") && !classInfo.b("HorizontalScrollView")
                && !classInfo.b("ListView") && !classInfo.b("FloatingActionButton")) {
            a(viewBean, "property_enabled");
        }
        a(viewBean, "property_rotate");
        a(viewBean, "property_alpha");
        a(viewBean, "property_translation_x");
        a(viewBean, "property_translation_y");
        a(viewBean, "property_scale_x");
        a(viewBean, "property_scale_y");
    }

    public void i(ViewBean viewBean) {
        String str;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof PropertyInputItem) {
                PropertyInputItem propertyInputItem = (PropertyInputItem) childAt;
                switch (propertyInputItem.getKey()) {
                    case "property_id":
                        String str2 = viewBean.id;
                        viewBean.preId = str2;
                        if (str2.charAt(0) != '_') {
                            viewBean.id = propertyInputItem.getValue();
                        }
                        break;

                    case "property_convert":
                        viewBean.convert = propertyInputItem.getValue();
                        break;

                    case "property_inject":
                        viewBean.inject = propertyInputItem.getValue();
                        break;

                    case "property_text":
                        viewBean.text.text = propertyInputItem.getValue();
                        break;

                    case "property_hint":
                        viewBean.text.hint = propertyInputItem.getValue();
                        break;

                    case "property_weight":
                        viewBean.layout.weight = Integer.parseInt(propertyInputItem.getValue());
                        break;

                    case "property_weight_sum":
                        viewBean.layout.weightSum = Integer.parseInt(propertyInputItem.getValue());
                        break;

                    case "property_rotate":
                        viewBean.image.rotate = Integer.parseInt(propertyInputItem.getValue());
                        break;

                    case "property_alpha":
                        viewBean.alpha = Float.parseFloat(propertyInputItem.getValue());
                        break;

                    case "property_translation_x":
                        viewBean.translationX = Float.parseFloat(propertyInputItem.getValue());
                        break;

                    case "property_translation_y":
                        viewBean.translationY = Float.parseFloat(propertyInputItem.getValue());
                        break;

                    case "property_scale_x":
                        viewBean.scaleX = Float.parseFloat(propertyInputItem.getValue());
                        break;

                    case "property_scale_y":
                        viewBean.scaleY = Float.parseFloat(propertyInputItem.getValue());
                        break;

                    case "property_lines":
                        viewBean.text.line = Integer.parseInt(propertyInputItem.getValue());
                        break;

                    case "property_max":
                        viewBean.max = Integer.parseInt(propertyInputItem.getValue());
                        break;

                    case "property_progress":
                        viewBean.progress = Integer.parseInt(propertyInputItem.getValue());
                        break;
                }
            } else if (childAt instanceof PropertyMeasureItem) {
                PropertyMeasureItem propertyMeasureItem = (PropertyMeasureItem) childAt;
                if (propertyMeasureItem.getKey().equals("property_layout_width")) {
                    viewBean.layout.width = propertyMeasureItem.getValue();
                } else if (propertyMeasureItem.getKey().equals("property_layout_height")) {
                    viewBean.layout.height = propertyMeasureItem.getValue();
                }
            } else if (childAt instanceof PropertySelectorItem) {
                PropertySelectorItem propertySelectorItem = (PropertySelectorItem) childAt;
                switch (propertySelectorItem.getKey()) {
                    case "property_orientation":
                        viewBean.layout.orientation = propertySelectorItem.getValue();
                        break;

                    case "property_text_style":
                        viewBean.text.textType = propertySelectorItem.getValue();
                        break;

                    case "property_text_size":
                        viewBean.text.textSize = propertySelectorItem.getValue();
                        break;

                    case "property_input_type":
                        viewBean.text.inputType = propertySelectorItem.getValue();
                        break;

                    case "property_ime_option":
                        viewBean.text.imeOption = propertySelectorItem.getValue();
                        break;

                    case "property_spinner_mode":
                        viewBean.spinnerMode = propertySelectorItem.getValue();
                        break;

                    case "property_first_day_of_week":
                        viewBean.firstDayOfWeek = propertySelectorItem.getValue();
                        break;
                }
            } else if (childAt instanceof PropertyStringSelectorItem) {
                PropertyStringSelectorItem propertyStringSelectorItem = (PropertyStringSelectorItem) childAt;
                switch (propertyStringSelectorItem.getKey()) {
                    case "property_scale_type":
                        viewBean.image.scaleType = propertyStringSelectorItem.getValue();
                        break;

                    case "property_ad_size":
                        viewBean.adSize = propertyStringSelectorItem.getValue();
                        break;

                    case "property_indeterminate":
                        viewBean.indeterminate = propertyStringSelectorItem.getValue();
                        break;
                }
            } else if (childAt instanceof PropertyStringPairSelectorItem) {
                PropertyStringPairSelectorItem propertyStringPairSelectorItem = (PropertyStringPairSelectorItem) childAt;
                if (propertyStringPairSelectorItem.getKey().equals("property_progressbar_style")) {
                    viewBean.progressStyle = propertyStringPairSelectorItem.getValue();
                }
            } else if (childAt instanceof Pw) {
                Pw pw = (Pw) childAt;
                if (pw.getKey().equals("property_custom_view_listview")) {
                    viewBean.customView = pw.getValue();
                }
            } else if (childAt instanceof PropertySwitchSingleLineItem) {
                PropertySwitchSingleLineItem propertySwitchSingleLineItem = (PropertySwitchSingleLineItem) childAt;
                switch (propertySwitchSingleLineItem.getKey()) {
                    case "property_single_line":
                        viewBean.text.singleLine = propertySwitchSingleLineItem.getValue() ? 1 : 0;
                        break;

                    case "property_enabled":
                        viewBean.enabled = propertySwitchSingleLineItem.getValue() ? 1 : 0;
                        break;

                    case "property_clickable":
                        viewBean.clickable = propertySwitchSingleLineItem.getValue() ? 1 : 0;
                        break;

                    case "property_checked":
                        viewBean.checked = propertySwitchSingleLineItem.getValue() ? 1 : 0;
                        break;
                }
            } else if (childAt instanceof PropertyColorItem) {
                PropertyColorItem propertyColorItem = (PropertyColorItem) childAt;
                switch (propertyColorItem.getKey()) {
                    case "property_text_color":
                        viewBean.text.textColor = propertyColorItem.getValue();
                        break;

                    case "property_hint_color":
                        viewBean.text.hintColor = propertyColorItem.getValue();
                        break;

                    case "property_background_color":
                        viewBean.layout.backgroundColor = propertyColorItem.getValue();
                        break;
                }
            } else if (childAt instanceof PropertyIndentItem) {
                PropertyIndentItem propertyIndentItem = (PropertyIndentItem) childAt;
                if (propertyIndentItem.getKey().equals("property_margin")) {
                    LayoutBean layoutBean = viewBean.layout;
                    layoutBean.marginLeft = propertyIndentItem.j;
                    layoutBean.marginTop = propertyIndentItem.k;
                    layoutBean.marginRight = propertyIndentItem.l;
                    layoutBean.marginBottom = propertyIndentItem.m;
                } else if (propertyIndentItem.getKey().equals("property_padding")) {
                    LayoutBean layoutBean2 = viewBean.layout;
                    layoutBean2.paddingLeft = propertyIndentItem.j;
                    layoutBean2.paddingTop = propertyIndentItem.k;
                    layoutBean2.paddingRight = propertyIndentItem.l;
                    layoutBean2.paddingBottom = propertyIndentItem.m;
                }
            } else if (childAt instanceof PropertyGravityItem) {
                PropertyGravityItem propertyGravityItem = (PropertyGravityItem) childAt;
                if (propertyGravityItem.getKey().equals("property_gravity")) {
                    viewBean.layout.gravity = propertyGravityItem.getValue();
                } else if (propertyGravityItem.getKey().equals("property_layout_gravity")) {
                    viewBean.layout.layoutGravity = propertyGravityItem.getValue();
                }
            } else if (childAt instanceof tx) {
                tx txVar = (tx) childAt;
                if (txVar.getKey().equals("property_image")) {
                    viewBean.image.resName = txVar.getValue();
                } else if (txVar.getKey().equals("property_background_resource")) {
                    viewBean.layout.backgroundResource = txVar.getValue();
                }
            } else if (childAt instanceof PropertySizeItem) {
                PropertySizeItem propertySizeItem = (PropertySizeItem) childAt;
                if (propertySizeItem.getKey().equals("property_divider_height")) {
                    viewBean.dividerHeight = propertySizeItem.getValue();
                }
            } else if (childAt instanceof Vw) {
                Vw vw = (Vw) childAt;
                if (vw.getKey().equals("property_text_font")) {
                    viewBean.text.textFont = vw.getValue();
                }
            }
        }
        if (!viewBean.id.equals(viewBean.preId)) {
            for (ViewBean next : jC.a(this.a).d(this.e.getXmlName())) {
                if (next.parent.equals(viewBean.preId)) {
                    next.parent = viewBean.id;
                }
            }
            if (this.e.fileType == 0) {
                for (EventBean next2 : jC.a(this.a).g(this.e.getJavaName())) {
                    if (next2.targetId.equals(viewBean.preId)) {
                        next2.targetId = viewBean.id;
                    }
                }
                HashMap<String, ArrayList<BlockBean>> b2 = jC.a(this.a).b(this.e.getJavaName());
                String[] c2 = oq.c(viewBean.getClassInfo());
                for (String str3 : c2) {
                    String str4 = viewBean.preId + "_" + str3;
                    if (b2.containsKey(str4)) {
                        b2.put(viewBean.id + "_" + str3, b2.get(str4));
                        b2.remove(str4);
                    }
                }
                ArrayList<String> arrayList = new ArrayList<>();
                if (b2.containsKey("onCreate_initializeLogic")) {
                    arrayList.add("onCreate_initializeLogic");
                }
                for (Pair<String, String> stringStringPair : jC.a(this.a).i(this.e.getJavaName())) {
                    String str5 = ((String) stringStringPair.first) + "_" + "moreBlock";
                    if (b2.containsKey(str5)) {
                        arrayList.add(str5);
                    }
                }
                for (EventBean next3 : jC.a(this.a).g(this.e.getJavaName())) {
                    if (!next3.eventName.equals("onBindCustomView")) {
                        String eventKey = next3.getEventKey();
                        if (b2.containsKey(eventKey)) {
                            arrayList.add(eventKey);
                        }
                    }
                }
                for (String s : arrayList) {
                    ArrayList<BlockBean> arrayList2 = b2.get(s);
                    if (arrayList2 != null) {
                        for (BlockBean next4 : arrayList2) {
                            Gx classInfo = next4.getClassInfo();
                            if (classInfo == null || !classInfo.d() || !next4.spec.equals(viewBean.preId)) {
                                ArrayList<Gx> paramClassInfo = next4.getParamClassInfo();
                                if (paramClassInfo != null && paramClassInfo.size() > 0) {
                                    for (int i2 = 0; i2 < paramClassInfo.size(); i2++) {
                                        if (paramClassInfo.get(i2).d() && next4.parameters.get(i2).equals(viewBean.preId)) {
                                            next4.parameters.set(i2, viewBean.id);
                                        }
                                    }
                                }
                            } else {
                                next4.spec = viewBean.id;
                            }
                        }
                    }
                }
            } else {
                ArrayList<Pair<String, String>> arrayList3 = new ArrayList<>();
                for (ProjectFileBean next5 : jC.b(this.a).b()) {
                    for (ViewBean next6 : jC.a(this.a).f(next5.getXmlName())) {
                        if (next6.customView.equals(this.e.fileName)) {
                            arrayList3.add(new Pair<>(next5.getJavaName(), next6.id + "_" + "onBindCustomView"));
                        }
                    }
                }
                for (Pair<String, String> stringStringPair : arrayList3) {
                    ArrayList<BlockBean> arrayList4 = jC.a(this.a).b((String) ((Pair<String, String>) stringStringPair).first).get(((Pair<String, String>) stringStringPair).second);
                    if (arrayList4 != null) {
                        for (BlockBean next7 : arrayList4) {
                            Gx classInfo2 = next7.getClassInfo();
                            if (classInfo2 == null || !classInfo2.d() || !next7.spec.equals(viewBean.preId)) {
                                ArrayList<Gx> paramClassInfo2 = next7.getParamClassInfo();
                                if (paramClassInfo2 != null && paramClassInfo2.size() > 0) {
                                    for (int i3 = 0; i3 < paramClassInfo2.size(); i3++) {
                                        if (paramClassInfo2.get(i3).d() && next7.parameters.get(i3).equals(viewBean.preId)) {
                                            next7.parameters.set(i3, viewBean.id);
                                        }
                                    }
                                }
                            } else {
                                next7.spec = viewBean.id;
                            }
                        }
                    }
                }
            }
        }
        if (viewBean.type == 9) {
            ViewBean c3 = jC.a(this.a).c(this.e.getXmlName(), viewBean.preId);
            String str6 = viewBean.customView;
            if (!(str6 == null || c3 == null || (str = c3.customView) == null || str.equals(str6))) {
                ArrayList<EventBean> g = jC.a(this.a).g(this.e.getJavaName());
                int size = g.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    EventBean eventBean = g.get(size);
                    if (eventBean.targetId.equals(viewBean.id) && eventBean.eventName.equals("onBindCustomView")) {
                        g.remove(eventBean);
                        HashMap<String, ArrayList<BlockBean>> b3 = jC.a(this.a).b(this.e.getJavaName());
                        if (b3.containsKey(eventBean.getEventKey())) {
                            b3.remove(eventBean.getEventKey());
                        }
                    }
                }
                if (viewBean.customView.equals("") || viewBean.customView.equals("none")) {
                    for (Map.Entry<String, ArrayList<BlockBean>> entry : jC.a(this.a).b(this.e.getJavaName()).entrySet()) {
                        for (BlockBean next8 : entry.getValue()) {
                            if ("listSetCustomViewData".equals(next8.opCode) && viewBean.id.equals(next8.parameters.get(0))) {
                                next8.parameters.set(0, "");
                            }
                        }
                    }
                }
            }
        }
    }

    public void setOnPropertyValueChangedListener(Lw lw) {
        this.d = lw;
    }

    public void setProjectFileBean(ProjectFileBean projectFileBean) {
        this.e = projectFileBean;
    }

    public final void a(Context context) {
        Cx.a().a(context);
    }

    public void b(ViewBean viewBean) {
        if (getOrientation() == VERTICAL) {
            a(viewBean, "property_id");
        }
        a(viewBean);
        c(viewBean);
        if (getOrientation() == HORIZONTAL) {
            a(viewBean, "property_id");
        }
    }

    public final void a(ViewBean viewBean, String str) {
        boolean z = !viewBean.getClassInfo().a("AdView");
        boolean z2 = viewBean.id.charAt(0) == '_';
        char type = 65535;
        switch (str) {
            case "property_background_resource":
                type = 22;
                break;

            case "property_divider_height":
                type = ' ';
                break;

            case "property_layout_width":
                type = 1;
                break;

            case "property_scale_type":
                type = 21;
                break;

            case "property_text_size":
                type = 11;
                break;

            case "property_alpha":
                type = 26;
                break;

            case "property_image":
                type = 20;
                break;

            case "property_lines":
                type = 17;
                break;

            case "property_layout_gravity":
                type = '\b';
                break;

            case "property_text_color":
                type = '\r';
                break;

            case "property_text_style":
                type = '\f';
                break;

            case "property_background_color":
                type = 23;
                break;

            case "property_gravity":
                type = 7;
                break;

            case "property_hint":
                type = 14;
                break;

            case "property_text":
                type = '\n';
                break;

            case "property_progress":
                type = '$';
                break;

            case "property_max":
                type = '#';
                break;

            case "property_checked":
                type = '\"';
                break;

            case "property_weight_sum":
                type = 6;
                break;

            case "property_inject":
                type = ')';
                break;

            case "property_layout_height":
                type = 2;
                break;

            case "property_margin":
                type = 3;
                break;

            case "property_convert":
                type = '*';
                break;

            case "property_ime_option":
                type = 19;
                break;

            case "property_spinner_mode":
                type = 31;
                break;

            case "property_rotate":
                type = 25;
                break;

            case "property_weight":
                type = '\t';
                break;

            case "property_progressbar_style":
                type = '\'';
                break;

            case "property_single_line":
                type = 16;
                break;

            case "property_indeterminate":
                type = '(';
                break;

            case "property_translation_x":
                type = 27;
                break;

            case "property_translation_y":
                type = 28;
                break;

            case "property_orientation":
                type = 5;
                break;

            case "property_scale_x":
                type = 29;
                break;

            case "property_scale_y":
                type = 30;
                break;

            case "property_first_day_of_week":
                type = '%';
                break;

            case "property_custom_view_listview":
                type = '!';
                break;

            case "property_enabled":
                type = 24;
                break;

            case "property_id":
                type = 0;
                break;

            case "property_ad_size":
                type = '&';
                break;

            case "property_padding":
                type = 4;
                break;

            case "property_hint_color":
                type = 15;
                break;

            case "property_input_type":
                type = 18;
                break;
        }
        /*
        switch (str.hashCode()) {
            case -1353621303:
                if (str.equals("property_text_style")) {
                    type = '\f';
                    break;
                }
                break;
            case -1244048924:
                if (str.equals("property_gravity")) {
                    type = 7;
                    break;
                }
                break;
            case -1019380393:
                if (str.equals("property_text")) {
                    type = '\n';
                    break;
                }
                break;
            case -782258371:
                if (str.equals("property_checked")) {
                    type = '\"';
                    break;
                }
                break;
            case -710204242:
                if (str.equals("property_weight_sum")) {
                    type = 6;
                    break;
                }
                break;
            case -576300200:
                if (str.equals("property_margin")) {
                    type = 3;
                    break;
                }
                break;
            case -572981943:
                if (str.equals("property_convert")) {
                    type = '*';
                    break;
                }
                break;
            case -522792099:
                if (str.equals("property_ime_option")) {
                    type = 19;
                    break;
                }
                break;
            case -512158157:
                if (str.equals("property_spinner_mode")) {
                    type = 31;
                    break;
                }
                break;
            case -420171003:
                if (str.equals("property_rotate")) {
                    type = 25;
                    break;
                }
                break;
            case -286582750:
                if (str.equals("property_weight")) {
                    type = '\t';
                    break;
                }
                break;
            case -78143730:
                if (str.equals("property_progressbar_style")) {
                    type = '\'';
                    break;
                }
                break;
            case -56658399:
                if (str.equals("property_single_line")) {
                    type = 16;
                    break;
                }
                break;
            case -10402863:
                if (str.equals("property_indeterminate")) {
                    type = '(';
                    break;
                }
                break;
            case 20737408:
                if (str.equals("property_translation_x")) {
                    type = 27;
                    break;
                }
                break;
            case 20737409:
                if (str.equals("property_translation_y")) {
                    type = 28;
                    break;
                }
                break;
            case 235805286:
                if (str.equals("property_orientation")) {
                    type = 5;
                    break;
                }
                break;
            case 386320985:
                if (str.equals("property_scale_x")) {
                    type = 29;
                    break;
                }
                break;
            case 386320986:
                if (str.equals("property_scale_y")) {
                    type = 30;
                    break;
                }
                break;
            case 1096920256:
                if (str.equals("property_first_day_of_week")) {
                    type = '%';
                    break;
                }
                break;
            case 1118712953:
                if (str.equals("property_custom_view_listview")) {
                    type = '!';
                    break;
                }
                break;
            case 1160800983:
                if (str.equals("property_enabled")) {
                    type = 24;
                    break;
                }
                break;
            case 1357596613:
                if (str.equals("property_id")) {
                    type = 0;
                    break;
                }
                break;
            case 1618119219:
                if (str.equals("property_ad_size")) {
                    type = '&';
                    break;
                }
                break;
            case 1964055463:
                if (str.equals("property_padding")) {
                    type = 4;
                    break;
                }
                break;
            case 2101485653:
                if (str.equals("property_hint_color")) {
                    type = 15;
                    break;
                }
                break;
            case 2133471033:
                if (str.equals("property_input_type")) {
                    type = 18;
                    break;
                }
                break;
        }
        */
        switch (type) {
            case 0:
                a(str, z2 ? viewBean.id.substring(1) : viewBean.id, z2);
                return;

            case 1:
                a(str, viewBean.layout.width, z);
                return;

            case 2:
                a(str, viewBean.layout.height, z);
                return;

            case 3:
                LayoutBean layoutBean = viewBean.layout;
                a(str, layoutBean.marginLeft, layoutBean.marginTop, layoutBean.marginRight, layoutBean.marginBottom);
                return;

            case 4:
                LayoutBean layoutBean2 = viewBean.layout;
                a("property_padding", layoutBean2.paddingLeft, layoutBean2.paddingTop, layoutBean2.paddingRight, layoutBean2.paddingBottom);
                return;

            case 5:
                c(str, viewBean.layout.orientation);
                return;

            case 6:
                b(str, String.valueOf(viewBean.layout.weightSum));
                return;

            case 7:
                b(str, viewBean.layout.gravity);
                return;

            case '\b':
                b(str, viewBean.layout.layoutGravity);
                return;

            case '\t':
                b(str, String.valueOf(viewBean.layout.weight));
                return;

            case '\n':
                b(str, viewBean.text.text);
                return;

            case 11:
                c(str, viewBean.text.textSize);
                return;

            case '\f':
                c(str, viewBean.text.textType);
                return;

            case '\r':
                a(str, viewBean.text.textColor);
                return;

            case 14:
                b(str, viewBean.text.hint);
                return;

            case 15:
                a(str, viewBean.text.hintColor);
                return;

            case 16:
                e(str, viewBean.text.singleLine);
                return;

            case 17:
                b(str, String.valueOf(viewBean.text.line));
                return;

            case 18:
                c(str, viewBean.text.inputType);
                return;

            case 19:
                c(str, viewBean.text.imeOption);
                return;

            case 20:
                b(str, viewBean.image.resName, true);
                return;

            case 21:
                d(str, viewBean.image.scaleType);
                return;

            case 22:
                b(str, viewBean.layout.backgroundResource, false);
                return;

            case 23:
                a(str, viewBean.layout.backgroundColor);
                return;

            case 24:
                e(str, viewBean.enabled);
                return;

            case 25:
                b(str, String.valueOf(viewBean.image.rotate));
                return;

            case 26:
                b(str, String.valueOf(viewBean.alpha));
                return;

            case 27:
                b(str, String.valueOf(viewBean.translationX));
                return;

            case 28:
                b(str, String.valueOf(viewBean.translationY));
                return;

            case 29:
                b(str, String.valueOf(viewBean.scaleX));
                return;

            case 30:
                b(str, String.valueOf(viewBean.scaleY));
                return;

            case 31:
                c(str, viewBean.spinnerMode);
                return;

            case ' ':
                d(str, viewBean.dividerHeight);
                return;

            case '!':
                a(str, viewBean.customView);
                return;

            case '\"':
                e(str, viewBean.checked);
                return;

            case '#':
                b(str, String.valueOf(viewBean.max));
                return;

            case '$':
                b(str, String.valueOf(viewBean.progress));
                return;

            case '%':
                c(str, viewBean.firstDayOfWeek);
                return;

            case '&':
                d(str, viewBean.adSize);
                return;

            case '\'':
                c(str, viewBean.progressStyle);
                return;

            case '(':
                d(str, viewBean.indeterminate);
                return;

            case ')':
                b(str, viewBean.inject);
                return;

            case '*':
                b(str, viewBean.convert);
        }
    }

    public final void c(ViewBean viewBean) {
        viewBean.getClassInfo();
        viewBean.getParentClassInfo();
        if (getOrientation() == VERTICAL) {
            a(xB.b().a(getResources(), 0x7f0e0763), new OnClickListener());
        }
        a(viewBean, "property_image");
        a(viewBean, "property_rotate");
        a(viewBean, "property_alpha");
        a(viewBean, "property_translation_x");
        a(viewBean, "property_translation_y");
        a(viewBean, "property_scale_x");
        a(viewBean, "property_scale_y");
    }

    public void b(String str, String str2) {
        a(str, str2, this.b);
    }

    public void b(String str, String str2, boolean z) {
        tx txVar = (tx) this.f.get(str);
        if (txVar == null) {
            txVar = new tx(getContext(), !this.b, this.a, z);
            txVar.setOrientationItem(getOrientation());
            txVar.setKey(str);
            txVar.setValue(str2);
            txVar.setTag(str);
            txVar.setOnPropertyValueChangeListener(this);
            this.f.put(str, txVar);
        } else {
            txVar.setValue(str2);
        }
        addView(txVar);
    }

    public void e(String str, int i) {
        PropertySwitchSingleLineItem propertySwitchSingleLineItem = (PropertySwitchSingleLineItem) this.f.get(str);
        boolean z = false;
        if (propertySwitchSingleLineItem == null) {
            propertySwitchSingleLineItem = new PropertySwitchSingleLineItem(getContext(), !this.b);
            propertySwitchSingleLineItem.setOrientationItem(getOrientation());
            propertySwitchSingleLineItem.setKey(str);
            if (i == 1) {
                z = true;
            }
            propertySwitchSingleLineItem.setValue(z);
            propertySwitchSingleLineItem.setTag(str);
            propertySwitchSingleLineItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertySwitchSingleLineItem);
        } else {
            if (i == 1) {
                z = true;
            }
            propertySwitchSingleLineItem.setValue(z);
        }
        addView(propertySwitchSingleLineItem);
    }

    public void c(String str, int i) {
        PropertySelectorItem propertySelectorItem = (PropertySelectorItem) this.f.get(str);
        if (propertySelectorItem == null) {
            propertySelectorItem = new PropertySelectorItem(getContext(), !this.b);
            propertySelectorItem.setOrientationItem(getOrientation());
            propertySelectorItem.setKey(str);
            propertySelectorItem.setValue(i);
            propertySelectorItem.setTag(str);
            propertySelectorItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertySelectorItem);
        } else {
            propertySelectorItem.setValue(i);
        }
        addView(propertySelectorItem);
    }

    public void b(String str, int i) {
        PropertyGravityItem propertyGravityItem = (PropertyGravityItem) this.f.get(str);
        if (propertyGravityItem == null) {
            propertyGravityItem = new PropertyGravityItem(getContext(), !this.b);
            propertyGravityItem.setOrientationItem(getOrientation());
            propertyGravityItem.setKey(str);
            propertyGravityItem.setValue(i);
            propertyGravityItem.setTag(str);
            propertyGravityItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyGravityItem);
        } else {
            propertyGravityItem.setValue(i);
        }
        addView(propertyGravityItem);
    }

    public void d(String str, int i) {
        PropertySizeItem propertySizeItem = (PropertySizeItem) this.f.get(str);
        if (propertySizeItem == null) {
            propertySizeItem = new PropertySizeItem(getContext(), !this.b);
            propertySizeItem.setOrientationItem(getOrientation());
            propertySizeItem.setKey(str);
            propertySizeItem.setValue(i);
            propertySizeItem.setTag(str);
            propertySizeItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertySizeItem);
        } else {
            propertySizeItem.setValue(i);
        }
        addView(propertySizeItem);
    }

    public void c(String str, String str2) {
        PropertyStringPairSelectorItem propertyStringPairSelectorItem = (PropertyStringPairSelectorItem) this.f.get(str);
        if (propertyStringPairSelectorItem == null) {
            propertyStringPairSelectorItem = new PropertyStringPairSelectorItem(getContext(), !this.b);
            propertyStringPairSelectorItem.setOrientationItem(getOrientation());
            propertyStringPairSelectorItem.setKey(str);
            propertyStringPairSelectorItem.setValue(str2);
            propertyStringPairSelectorItem.setTag(str);
            propertyStringPairSelectorItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyStringPairSelectorItem);
        } else {
            propertyStringPairSelectorItem.setValue(str2);
        }
        addView(propertyStringPairSelectorItem);
    }

    public void d(String str, String str2) {
        PropertyStringSelectorItem propertyStringSelectorItem = (PropertyStringSelectorItem) this.f.get(str);
        if (propertyStringSelectorItem == null) {
            propertyStringSelectorItem = new PropertyStringSelectorItem(getContext(), !this.b);
            propertyStringSelectorItem.setOrientationItem(getOrientation());
            propertyStringSelectorItem.setKey(str);
            propertyStringSelectorItem.setValue(str2);
            propertyStringSelectorItem.setTag(str);
            propertyStringSelectorItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyStringSelectorItem);
        } else {
            propertyStringSelectorItem.setValue(str2);
        }
        addView(propertyStringSelectorItem);
    }

    public void a(String str, ViewBean viewBean) {
        this.a = str;
        this.c = viewBean;
        Cx.a().b(this.c.getClassInfo().a());
        removeAllViews();
        if (viewBean.id.equals("_fab")) {
            b(viewBean);
            return;
        }
        if (getOrientation() == VERTICAL) {
            b("property_id", viewBean.id);
        }
        f(viewBean);
        d(viewBean);
        g(viewBean);
        h(viewBean);
        if (getOrientation() == HORIZONTAL) {
            b("property_id", viewBean.id);
        }
    }

    public final void a() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.gravity = 17;
        setLayoutParams(layoutParams);
        setGravity(17);
        TextView textView = new TextView(getContext());
        textView.setTextColor(getResources().getColor(Resources.color.grey));
        textView.setGravity(17);
        textView.setPadding(8, 8, 8, 8);
        textView.setTextSize(2, 12.0f);
        textView.setText(xB.b().a(getContext(), 0x7f0e055c));
        addView(textView);
    }

    public final void a(ViewBean viewBean) {
        viewBean.getClassInfo();
        viewBean.getParentClassInfo();
        if (getOrientation() == VERTICAL) {
            a(xB.b().a(getResources(), 0x7f0e0764));
        }
        a(viewBean, "property_margin");
        a(viewBean, "property_layout_gravity");
    }

    public void a(String str) {
        PropertySubheader propertySubheader = new PropertySubheader(getContext());
        propertySubheader.setHeaderName(str);
        addView(propertySubheader);
    }

    public void a(String str, View.OnClickListener onClickListener) {
        PropertySubheader propertySubheader = new PropertySubheader(getContext());
        propertySubheader.setHeaderName(str);
        propertySubheader.setOnClickListener(onClickListener);
        addView(propertySubheader);
    }

    public void a(String str, int i, boolean z) {
        PropertyMeasureItem propertyMeasureItem = (PropertyMeasureItem) this.f.get(str);
        int i2 = z ? 7 : 3;
        if (propertyMeasureItem == null) {
            propertyMeasureItem = new PropertyMeasureItem(getContext(), !this.b);
            propertyMeasureItem.setOrientationItem(getOrientation());
            propertyMeasureItem.setItemEnabled(i2);
            propertyMeasureItem.setKey(str);
            propertyMeasureItem.setValue(i);
            propertyMeasureItem.setTag(str);
            propertyMeasureItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyMeasureItem);
        } else {
            propertyMeasureItem.setItemEnabled(i2);
            propertyMeasureItem.setValue(i);
        }
        addView(propertyMeasureItem);
    }

    public void a(String str, String str2, boolean z) {
        PropertyInputItem propertyInputItem = (PropertyInputItem) this.f.get(str);
        if (propertyInputItem == null) {
            propertyInputItem = new PropertyInputItem(getContext(), !z);
            propertyInputItem.setOrientationItem(getOrientation());
            propertyInputItem.a(this.a, this.e);
            propertyInputItem.setKey(str);
            propertyInputItem.setValue(str2);
            propertyInputItem.setTag(str);
            propertyInputItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyInputItem);
        } else {
            propertyInputItem.a(this.a, this.e);
            propertyInputItem.setValue(str2);
        }
        addView(propertyInputItem);
    }

    public void a(String str, int i, int i2, int i3, int i4) {
        PropertyIndentItem propertyIndentItem = (PropertyIndentItem) this.f.get(str);
        if (propertyIndentItem == null) {
            propertyIndentItem = new PropertyIndentItem(getContext(), !this.b);
            propertyIndentItem.setOrientationItem(getOrientation());
            propertyIndentItem.setKey(str);
            propertyIndentItem.a(i, i2, i3, i4);
            propertyIndentItem.setTag(str);
            propertyIndentItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyIndentItem);
        } else {
            propertyIndentItem.a(i, i2, i3, i4);
        }
        addView(propertyIndentItem);
    }

    public void a(String str, String str2) {
        Pw pw = (Pw) this.f.get(str);
        if (pw == null) {
            pw = new Pw(getContext(), !this.b);
            pw.setOrientationItem(getOrientation());
            pw.setKey(str);
            pw.setTag(str);
            pw.setOnPropertyValueChangeListener(this);
            this.f.put(str, pw);
        }
        pw.setCustomView(jC.b(this.a).c());
        pw.setValue(str2);
        addView(pw);
    }

    public void a(String str, int i) {
        PropertyColorItem propertyColorItem = (PropertyColorItem) this.f.get(str);
        if (propertyColorItem == null) {
            propertyColorItem = new PropertyColorItem(getContext(), !this.b);
            propertyColorItem.setOrientationItem(getOrientation());
            propertyColorItem.setKey(str);
            propertyColorItem.setValue(i);
            propertyColorItem.setTag(str);
            propertyColorItem.setOnPropertyValueChangeListener(this);
            this.f.put(str, propertyColorItem);
        } else {
            propertyColorItem.setValue(i);
        }
        addView(propertyColorItem);
    }

    @Override // a.a.a.Kw
    public void a(String str, Object obj) {
        Cx.a().a(this.c.getClassInfo().a(), str);
        if (this.d != null) {
            ViewBean clone = this.c.clone();
            i(this.c);
            if (!this.b) {
                cC.c(this.a).a(this.e.getXmlName(), clone, this.c.clone());
                this.d.a(this.c);
            }
        }
    }

    private class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mB.a() && b) {
                c();
            }
        }
    }
}