package com.besome.sketch.editor.property;

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
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.EventBean;
import com.besome.sketch.beans.LayoutBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class ViewPropertyItems extends LinearLayout implements Kw {
    public String a;
    public boolean b = false;
    public ViewBean c;
    public Lw d;
    public ProjectFileBean e;
    public HashMap<String, View> f = new HashMap();

    public ViewPropertyItems(Context var1) {
        super(var1);
        this.a(var1);
    }

    public final void a() {
        LayoutParams var1 = new LayoutParams(-1, -1);
        var1.gravity = 17;
        this.setLayoutParams(var1);
        this.setGravity(17);
        TextView var2 = new TextView(this.getContext());
        var2.setTextColor(this.getResources().getColor(2131034217));
        var2.setGravity(17);
        var2.setPadding(8, 8, 8, 8);
        var2.setTextSize(2, 12.0F);
        var2.setText(xB.b().a(this.getContext(), 2131625308));
        this.addView(var2);
    }

    public final void a(Context var1) {
        Cx.a().a(var1);
    }

    public final void a(ViewBean var1) {
        var1.getClassInfo();
        var1.getParentClassInfo();
        if (this.getOrientation() == 1) {
            this.a(xB.b().a(this.getResources(), 2131625828));
        }

        this.a(var1, "property_margin");
        this.a(var1, "property_layout_gravity");
    }

    public final void a(ViewBean var1, String var2) {
        boolean var3 = var1.getClassInfo().a("AdView") ^ true;
        boolean var4;
        if (var1.id.charAt(0) == '_') {
            var4 = true;
        } else {
            var4 = false;
        }

        switch(var2) {
            case "property_id":
                String var8;
                if (var4) {
                    var8 = var1.id.substring(1);
                } else {
                    var8 = var1.id;
                }

                this.a(var2, var8, var4);
                break;
            case "property_layout_width":
                this.a(var2, var1.layout.width, var3);
                break;
            case "property_layout_height":
                this.a(var2, var1.layout.height, var3);
                break;
            case "property_margin":
                LayoutBean var7 = var1.layout;
                this.a(var2, var7.marginLeft, var7.marginTop, var7.marginRight, var7.marginBottom);
                break;
            case "property_padding":
                LayoutBean var6 = var1.layout;
                this.a("property_padding", var6.paddingLeft, var6.paddingTop, var6.paddingRight, var6.paddingBottom);
                break;
            case "property_orientation":
                this.c(var2, var1.layout.orientation);
                break;
            case "property_weight_sum":
                this.b(var2, String.valueOf(var1.layout.weightSum));
                break;
            case "property_gravity":
                this.b(var2, var1.layout.gravity);
                break;
            case "property_layout_gravity":
                this.b(var2, var1.layout.layoutGravity);
                break;
            case "property_weight":
                this.b(var2, String.valueOf(var1.layout.weight));
                break;
            case "property_text":
                this.b(var2, var1.text.text);
                break;
            case "property_text_size":
                this.c(var2, var1.text.textSize);
                break;
            case "property_text_style":
                this.c(var2, var1.text.textType);
                break;
            case "property_text_color":
                this.a(var2, var1.text.textColor);
                break;
            case "property_hint":
                this.b(var2, var1.text.hint);
                break;
            case "property_hint_color":
                this.a(var2, var1.text.hintColor);
                break;
            case "property_single_line":
                this.e(var2, var1.text.singleLine);
                break;
            case "property_lines":
                this.b(var2, String.valueOf(var1.text.line));
                break;
            case "property_input_type":
                this.c(var2, var1.text.inputType);
                break;
            case "property_ime_option":
                this.c(var2, var1.text.imeOption);
                break;
            case "property_image":
                this.b(var2, var1.image.resName, true);
                break;
            case "property_scale_type":
                this.d(var2, var1.image.scaleType);
                break;
            case "property_background_resource":
                this.b(var2, var1.layout.backgroundResource, false);
                break;
            case "property_background_color":
                this.a(var2, var1.layout.backgroundColor);
                break;
            case "property_enabled":
                this.e(var2, var1.enabled);
                break;
            case "property_rotate":
                this.b(var2, String.valueOf(var1.image.rotate));
                break;
            case "property_alpha":
                this.b(var2, String.valueOf(var1.alpha));
                break;
            case "property_translation_x":
                this.b(var2, String.valueOf(var1.translationX));
                break;
            case "property_translation_y":
                this.b(var2, String.valueOf(var1.translationY));
                break;
            case "property_scale_x":
                this.b(var2, String.valueOf(var1.scaleX));
                break;
            case "property_scale_y":
                this.b(var2, String.valueOf(var1.scaleY));
                break;
            case "property_spinner_mode":
                this.c(var2, var1.spinnerMode);
                break;
            case "property_divider_height":
                this.d(var2, var1.dividerHeight);
                break;
            case "property_custom_view_listview":
                this.a(var2, var1.customView);
                break;
            case "property_checked":
                this.e(var2, var1.checked);
                break;
            case "property_max":
                this.b(var2, String.valueOf(var1.max));
                break;
            case "property_progress":
                this.b(var2, String.valueOf(var1.progress));
                break;
            case "property_first_day_of_week":
                this.c(var2, var1.firstDayOfWeek);
                break;
            case "property_ad_size":
                this.d(var2, var1.adSize);
                break;
            case "property_progressbar_style":
                this.c(var2, var1.progressStyle);
                break;
            case "property_indeterminate":
                this.d(var2, var1.indeterminate);
                break;
            case "property_inject":
                this.b(var2, var1.inject);
                break;
            case "property_convert":
                this.b(var2, var1.convert);
        }
    }

    public void a(String var1) {
        PropertySubheader var2 = new PropertySubheader(this.getContext());
        var2.setHeaderName(var1);
        this.addView(var2);
    }

    public void a(String var1, int var2) {
        PropertyColorItem var3 = (PropertyColorItem)this.f.get(var1);
        PropertyColorItem var4;
        if (var3 == null) {
            var3 = new PropertyColorItem(this.getContext(), this.b ^ true);
            var3.setOrientationItem(this.getOrientation());
            var3.setKey(var1);
            var3.setValue(var2);
            var3.setTag(var1);
            var3.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var3);
            var4 = var3;
        } else {
            var3.setValue(var2);
            var4 = var3;
        }

        this.addView(var4);
    }

    public void a(String var1, int var2, int var3, int var4, int var5) {
        PropertyIndentItem var6 = (PropertyIndentItem)this.f.get(var1);
        PropertyIndentItem var7;
        if (var6 == null) {
            var6 = new PropertyIndentItem(this.getContext(), this.b ^ true);
            var6.setOrientationItem(this.getOrientation());
            var6.setKey(var1);
            var6.a(var2, var3, var4, var5);
            var6.setTag(var1);
            var6.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var6);
            var7 = var6;
        } else {
            var6.a(var2, var3, var4, var5);
            var7 = var6;
        }

        this.addView(var7);
    }

    public void a(String var1, int var2, boolean var3) {
        PropertyMeasureItem var4 = (PropertyMeasureItem)this.f.get(var1);
        byte var5;
        if (var3) {
            var5 = 7;
        } else {
            var5 = 3;
        }

        PropertyMeasureItem var6;
        if (var4 == null) {
            var4 = new PropertyMeasureItem(this.getContext(), this.b ^ true);
            var4.setOrientationItem(this.getOrientation());
            var4.setItemEnabled(var5);
            var4.setKey(var1);
            var4.setValue(var2);
            var4.setTag(var1);
            var4.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var4);
            var6 = var4;
        } else {
            var4.setItemEnabled(var5);
            var4.setValue(var2);
            var6 = var4;
        }

        this.addView(var6);
    }

    public void a(String var1, OnClickListener var2) {
        PropertySubheader var3 = new PropertySubheader(this.getContext());
        var3.setHeaderName(var1);
        var3.setOnClickListener(var2);
        this.addView(var3);
    }

    public void a(String var1, ViewBean var2) {
        this.a = var1;
        this.c = var2;
        Cx.a().b(this.c.getClassInfo().a());
        this.removeAllViews();
        if (var2.id.equals("_fab")) {
            this.b(var2);
        } else {
            if (this.getOrientation() == 1) {
                this.b("property_id", var2.id);
            }

            this.f(var2);
            this.d(var2);
            this.g(var2);
            this.h(var2);
            if (this.getOrientation() == 0) {
                this.b("property_id", var2.id);
            }
        }
    }

    public void a(String var1, Object var2) {
        Cx.a().a(this.c.getClassInfo().a(), var1);
        if (this.d != null) {
            ViewBean var3 = this.c.clone();
            this.i(this.c);
            if (!this.b) {
                cC.c(this.a).a(this.e.getXmlName(), var3, this.c.clone());
                this.d.a(this.c);
            }
        }
    }

    public void a(String var1, String var2) {
        Pw var3 = (Pw)this.f.get(var1);
        Pw var4 = var3;
        if (var3 == null) {
            var4 = new Pw(this.getContext(), this.b ^ true);
            var4.setOrientationItem(this.getOrientation());
            var4.setKey(var1);
            var4.setTag(var1);
            var4.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var4);
        }

        var4.setCustomView(jC.b(this.a).c());
        var4.setValue(var2);
        this.addView(var4);
    }

    public void a(String var1, String var2, boolean var3) {
        PropertyInputItem var4 = (PropertyInputItem)this.f.get(var1);
        PropertyInputItem var5;
        if (var4 == null) {
            var4 = new PropertyInputItem(this.getContext(), var3 ^ true);
            var4.setOrientationItem(this.getOrientation());
            var4.a(this.a, this.e);
            var4.setKey(var1);
            var4.setValue(var2);
            var4.setTag(var1);
            var4.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var4);
            var5 = var4;
        } else {
            var4.a(this.a, this.e);
            var4.setValue(var2);
            var5 = var4;
        }

        this.addView(var5);
    }

    public void save() {
        Cx.a().b();
    }

    public void b(ViewBean var1) {
        if (this.getOrientation() == 1) {
            this.a(var1, "property_id");
        }

        this.a(var1);
        this.c(var1);
        if (this.getOrientation() == 0) {
            this.a(var1, "property_id");
        }
    }

    public void b(String var1, int var2) {
        PropertyGravityItem var3 = (PropertyGravityItem)this.f.get(var1);
        PropertyGravityItem var4;
        if (var3 == null) {
            var3 = new PropertyGravityItem(this.getContext(), this.b ^ true);
            var3.setOrientationItem(this.getOrientation());
            var3.setKey(var1);
            var3.setValue(var2);
            var3.setTag(var1);
            var3.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var3);
            var4 = var3;
        } else {
            var3.setValue(var2);
            var4 = var3;
        }

        this.addView(var4);
    }

    public void b(String var1, String var2) {
        this.a(var1, var2, this.b);
    }

    public void b(String var1, String var2, boolean var3) {
        tx var4 = (tx)this.f.get(var1);
        tx var5;
        if (var4 == null) {
            var4 = new tx(this.getContext(), this.b ^ true, this.a, var3);
            var4.setOrientationItem(this.getOrientation());
            var4.setKey(var1);
            var4.setValue(var2);
            var4.setTag(var1);
            var4.setOnPropertyValueChangeListener(this);
            this.f.put(var1, var4);
            var5 = var4;
        } else {
            var4.setValue(var2);
            var5 = var4;
        }

        this.addView(var5);
    }

    public void c() {
        Intent var1 = new Intent(this.getContext(), ManageImageActivity.class);
        var1.setFlags(536870912);
        var1.putExtra("sc_id", this.a);
        ((Activity)this.getContext()).startActivityForResult(var1, 209);
    }

    public void c(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            a(xB.b().a(getResources(), 2131625827), view -> openManageImageActivityIfNeeded());
        }

        a(bean, "property_image");
        a(bean, "property_rotate");
        a(bean, "property_alpha");
        a(bean, "property_translation_x");
        a(bean, "property_translation_y");
        a(bean, "property_scale_x");
        a(bean, "property_scale_y");
    }

    private void c(String key, int value) {
        PropertySelectorItem selectorItem = (PropertySelectorItem)f.get(key);
        if (selectorItem == null) {
            selectorItem = new PropertySelectorItem(getContext(), !b);
            selectorItem.setOrientationItem(getOrientation());
            selectorItem.setKey(key);
            selectorItem.setValue(value);
            selectorItem.setTag(key);
            selectorItem.setOnPropertyValueChangeListener(this);
            f.put(key, selectorItem);
        } else {
            selectorItem.setValue(value);
        }

        addView(selectorItem);
    }

    private void c(String key, String value) {
        PropertyStringPairSelectorItem pairSelectorItem = (PropertyStringPairSelectorItem)f.get(key);
        if (pairSelectorItem == null) {
            pairSelectorItem = new PropertyStringPairSelectorItem(getContext(), !b);
            pairSelectorItem.setOrientationItem(getOrientation());
            pairSelectorItem.setKey(key);
            pairSelectorItem.setValue(value);
            pairSelectorItem.setTag(key);
            pairSelectorItem.setOnPropertyValueChangeListener(this);
            f.put(key, pairSelectorItem);
        } else {
            pairSelectorItem.setValue(value);
        }

        addView(pairSelectorItem);
    }

    public void d(ViewBean var1) {
        if (this.getOrientation() == 1) {
            this.a(xB.b().a(this.getResources(), 2131625828));
        }

        Gx classInfo = bean.getClassInfo();
        Gx parentClassInfo = bean.getParentClassInfo();
        a(bean, "property_layout_width");
        a(bean, "property_layout_height");
        a(bean, "property_padding");
        a(bean, "property_margin");
        if (classInfo.a("LinearLayout")) {
            a(bean, "property_orientation");
            a(bean, "property_weight_sum");
            a(bean, "property_gravity");
        }

        if (classInfo.a("TextView")) {
            a(bean, "property_gravity");
        }

        if (parentClassInfo != null) {
            if (parentClassInfo.a("LinearLayout")) {
                a(bean, "property_layout_gravity");
                a(bean, "property_weight");
            }

            if (parentClassInfo.a("ScrollView") || parentClassInfo.a("HorizontalScrollView")) {
                a(bean, "property_layout_gravity");
            }
        }
    }

    private void d(String key, int value) {
        PropertySizeItem propertySizeItem = (PropertySizeItem)f.get(key);
        if (propertySizeItem == null) {
            propertySizeItem = new PropertySizeItem(getContext(), !b);
            propertySizeItem.setOrientationItem(getOrientation());
            propertySizeItem.setKey(key);
            propertySizeItem.setValue(value);
            propertySizeItem.setTag(key);
            propertySizeItem.setOnPropertyValueChangeListener(this);
            f.put(key, propertySizeItem);
        } else {
            propertySizeItem.setValue(value);
        }

        addView(propertySizeItem);
    }

    private void d(String key, String value) {
        PropertyStringSelectorItem stringSelectorItem = (PropertyStringSelectorItem)f.get(key);
        if (stringSelectorItem == null) {
            stringSelectorItem = new PropertyStringSelectorItem(getContext(), !b);
            stringSelectorItem.setOrientationItem(getOrientation());
            stringSelectorItem.setKey(key);
            stringSelectorItem.setValue(value);
            stringSelectorItem.setTag(key);
            stringSelectorItem.setOnPropertyValueChangeListener(this);
            f.put(key, stringSelectorItem);
        } else {
            stringSelectorItem.setValue(value);
        }

        addView(stringSelectorItem);
    }

    public void e(ViewBean bean) {
        c = bean;
        removeAllViews();
        LayoutParams params = new LayoutParams(-1, -1);
        params.gravity = Gravity.LEFT;
        setLayoutParams(params);
        setGravity(Gravity.LEFT);
        ArrayList<String> items = Cx.a().a(bean.getClassInfo().a());
        if (items == null) {
            setupViews();
        } else {
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
                this.a(var1, (String)var4.next());
            }
        }
    }

    public void e(String key, int value) {
        PropertySwitchSingleLineItem switchSingleLineItem = (PropertySwitchSingleLineItem)f.get(key);
        boolean isEnabled = false;
        if (switchSingleLineItem == null) {
            switchSingleLineItem = new PropertySwitchSingleLineItem(getContext(), !b);
            switchSingleLineItem.setOrientationItem(getOrientation());
            switchSingleLineItem.setKey(key);
            if (value == 1) {
                isEnabled = true;
            }

            switchSingleLineItem.setValue(isEnabled);
            switchSingleLineItem.setTag(key);
            switchSingleLineItem.setOnPropertyValueChangeListener(this);
            f.put(key, switchSingleLineItem);
        } else {
            if (value == 1) {
                isEnabled = true;
            }

            switchSingleLineItem.setValue(isEnabled);
        }

        addView(switchSingleLineItem);
    }

    public void f(ViewBean bean) {
        if (!bean.id.equals("_fab")) {
            Gx classInfo = bean.getClassInfo();
            a(bean, "property_inject");
            a(bean, "property_convert");
            if (classInfo.b("Spinner")) {
                a(bean, "property_spinner_mode");
            }

            if (classInfo.b("ListView")) {
                a(bean, "property_divider_height");
                a(bean, "property_custom_view_listview");
            }

            if (classInfo.b("GridView")) {
                a(bean, "property_custom_view_listview");
            }

            if (classInfo.b("RecyclerView")) {
                a(bean, "property_custom_view_listview");
            }

            if (classInfo.b("ViewPager")) {
                a(bean, "property_custom_view_listview");
            }

            if (classInfo.b("Spinner")) {
                a(bean, "property_custom_view_listview");
            }

            if (classInfo.b("AutoCompleteTextView")) {
                a(bean, "property_hint");
                a(bean, "property_hint_color");
            }

            if (classInfo.b("MultiAutoCompleteTextView")) {
                a(bean, "property_hint");
                a(bean, "property_hint_color");
            }

            if (classInfo.b("WaveSideBar")) {
                a(bean, "property_text_size");
                a(bean, "property_text_color");
            }

            if (classInfo.a("CompoundButton") && getOrientation() == LinearLayout.VERTICAL) {
                a(bean, "property_checked");
            }

            if (classInfo.b("SeekBar")) {
                a(bean, "property_max");
                a(bean, "property_progress");
            }

            if (classInfo.b("CalendarView")) {
                a(bean, "property_first_day_of_week");
            }

            if (classInfo.b("AdView")) {
                a(bean, "property_ad_size");
            }

            if (classInfo.b("ProgressBar")) {
                a(bean, "property_max");
                a(bean, "property_progress");
                a(bean, "property_progressbar_style");
                a(bean, "property_indeterminate");
            }
        }
    }

    public void g(ViewBean bean) {
        Gx classInfo = bean.getClassInfo();
        if (classInfo.a("TextView")) {
            if (getOrientation() == LinearLayout.VERTICAL) {
                a(xB.b().a(getResources(), 2131625830));
            }

            a(bean, "property_text");
            a(bean, "property_text_size");
            a(bean, "property_text_style");
            a(bean, "property_text_color");
            if (classInfo.b("EditText")) {
                a(bean, "property_hint");
                a(bean, "property_hint_color");
                if (getOrientation() == LinearLayout.VERTICAL) {
                    a(bean, "property_single_line");
                }

                a(bean, "property_lines");
                a(bean, "property_input_type");
                a(bean, "property_ime_option");
            }

            if (classInfo.b("TextView")) {
                if (getOrientation() == LinearLayout.VERTICAL) {
                    a(bean, "property_single_line");
                }

                a(bean, "property_lines");
            }
        }
    }

    public void h(ViewBean bean) {
        Gx classInfo = bean.getClassInfo();
        if (getOrientation() == LinearLayout.VERTICAL) {
            if (classInfo.a("ImageView")) {
                a(xB.b().a(getResources(), 2131625827), v -> openManageImageActivityIfNeeded());
                a(bean, "property_image");
                a(bean, "property_scale_type");
            } else {
                a(xB.b().a(getResources(), 2131625827));
            }
        } else if (classInfo.a("ImageView")) {
            a(bean, "property_image");
            a(bean, "property_scale_type");
        }

        if (bean.type != 18) {
            a(bean, "property_background_resource");
            a(bean, "property_background_color");
        }

        if (getOrientation() == LinearLayout.VERTICAL
                && !classInfo.b("LinearLayout")
                && !classInfo.b("ScrollView")
                && !classInfo.b("HorizontalScrollView")
                && !classInfo.b("ListView")
                && !classInfo.b("FloatingActionButton")) {
            a(bean, "property_enabled");
        }

        a(bean, "property_rotate");
        a(bean, "property_alpha");
        a(bean, "property_translation_x");
        a(bean, "property_translation_y");
        a(bean, "property_scale_x");
        a(bean, "property_scale_y");
    }

    public void i(ViewBean var1) {
        int var2 = this.getChildCount();

        for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = this.getChildAt(var3);
            if (var4 instanceof PropertyInputItem) {
                PropertyInputItem var17 = (PropertyInputItem)var4;
                if (var17.getKey().equals("property_id")) {
                    String var5 = var1.id;
                    var1.preId = var5;
                    if (var5.charAt(0) != '_') {
                        var1.id = var17.getValue();
                    }
                } else if (var17.getKey().equals("property_convert")) {
                    var1.convert = var17.getValue();
                } else if (var17.getKey().equals("property_inject")) {
                    var1.inject = var17.getValue();
                } else if (var17.getKey().equals("property_text")) {
                    var1.text.text = var17.getValue();
                } else if (var17.getKey().equals("property_hint")) {
                    var1.text.hint = var17.getValue();
                } else if (var17.getKey().equals("property_weight")) {
                    var1.layout.weight = Integer.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_weight_sum")) {
                    var1.layout.weightSum = Integer.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_rotate")) {
                    var1.image.rotate = Integer.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_alpha")) {
                    var1.alpha = Float.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_translation_x")) {
                    var1.translationX = Float.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_translation_y")) {
                    var1.translationY = Float.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_scale_x")) {
                    var1.scaleX = Float.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_scale_y")) {
                    var1.scaleY = Float.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_lines")) {
                    var1.text.line = Integer.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_max")) {
                    var1.max = Integer.valueOf(var17.getValue());
                } else if (var17.getKey().equals("property_progress")) {
                    var1.progress = Integer.valueOf(var17.getValue());
                }
            } else if (var4 instanceof PropertyMeasureItem) {
                PropertyMeasureItem var18 = (PropertyMeasureItem)var4;
                if (var18.getKey().equals("property_layout_width")) {
                    var1.layout.width = var18.getValue();
                } else if (var18.getKey().equals("property_layout_height")) {
                    var1.layout.height = var18.getValue();
                }
            } else if (var4 instanceof PropertySelectorItem) {
                PropertySelectorItem var19 = (PropertySelectorItem)var4;
                if (var19.getKey().equals("property_orientation")) {
                    var1.layout.orientation = var19.getValue();
                } else if (var19.getKey().equals("property_text_style")) {
                    var1.text.textType = var19.getValue();
                } else if (var19.getKey().equals("property_text_size")) {
                    var1.text.textSize = var19.getValue();
                } else if (var19.getKey().equals("property_input_type")) {
                    var1.text.inputType = var19.getValue();
                } else if (var19.getKey().equals("property_ime_option")) {
                    var1.text.imeOption = var19.getValue();
                } else if (var19.getKey().equals("property_spinner_mode")) {
                    var1.spinnerMode = var19.getValue();
                } else if (var19.getKey().equals("property_first_day_of_week")) {
                    var1.firstDayOfWeek = var19.getValue();
                }
            } else if (var4 instanceof PropertyStringSelectorItem) {
                PropertyStringSelectorItem var20 = (PropertyStringSelectorItem)var4;
                if (var20.getKey().equals("property_scale_type")) {
                    var1.image.scaleType = var20.getValue();
                } else if (var20.getKey().equals("property_ad_size")) {
                    var1.adSize = var20.getValue();
                } else if (var20.getKey().equals("property_indeterminate")) {
                    var1.indeterminate = var20.getValue();
                }
            } else if (var4 instanceof PropertyStringPairSelectorItem) {
                PropertyStringPairSelectorItem var21 = (PropertyStringPairSelectorItem)var4;
                if (var21.getKey().equals("property_progressbar_style")) {
                    var1.progressStyle = var21.getValue();
                }
            } else if (var4 instanceof Pw) {
                Pw var22 = (Pw)var4;
                if (var22.getKey().equals("property_custom_view_listview")) {
                    var1.customView = var22.getValue();
                }
            } else if (var4 instanceof PropertySwitchSingleLineItem) {
                PropertySwitchSingleLineItem var23 = (PropertySwitchSingleLineItem)var4;
                if (var23.getKey().equals("property_single_line")) {
                    var1.text.singleLine = var23.getValue();
                } else if (var23.getKey().equals("property_enabled")) {
                    var1.enabled = var23.getValue();
                } else if (var23.getKey().equals("property_clickable")) {
                    var1.clickable = var23.getValue();
                } else if (var23.getKey().equals("property_checked")) {
                    var1.checked = var23.getValue();
                }
            } else if (var4 instanceof PropertyColorItem) {
                PropertyColorItem var24 = (PropertyColorItem)var4;
                if (var24.getKey().equals("property_text_color")) {
                    var1.text.textColor = var24.getValue();
                } else if (var24.getKey().equals("property_hint_color")) {
                    var1.text.hintColor = var24.getValue();
                } else if (var24.getKey().equals("property_background_color")) {
                    var1.layout.backgroundColor = var24.getValue();
                }
            } else if (var4 instanceof PropertyIndentItem) {
                PropertyIndentItem var25 = (PropertyIndentItem)var4;
                if (var25.getKey().equals("property_margin")) {
                    LayoutBean var38 = var1.layout;
                    var38.marginLeft = var25.j;
                    var38.marginTop = var25.k;
                    var38.marginRight = var25.l;
                    var38.marginBottom = var25.m;
                } else if (var25.getKey().equals("property_padding")) {
                    LayoutBean var39 = var1.layout;
                    var39.paddingLeft = var25.j;
                    var39.paddingTop = var25.k;
                    var39.paddingRight = var25.l;
                    var39.paddingBottom = var25.m;
                }
            } else if (var4 instanceof PropertyGravityItem) {
                PropertyGravityItem var26 = (PropertyGravityItem)var4;
                if (var26.getKey().equals("property_gravity")) {
                    var1.layout.gravity = var26.getValue();
                } else if (var26.getKey().equals("property_layout_gravity")) {
                    var1.layout.layoutGravity = var26.getValue();
                }
            } else if (var4 instanceof tx) {
                tx var27 = (tx)var4;
                if (var27.getKey().equals("property_image")) {
                    var1.image.resName = var27.getValue();
                } else if (var27.getKey().equals("property_background_resource")) {
                    var1.layout.backgroundResource = var27.getValue();
                }
            } else if (var4 instanceof PropertySizeItem) {
                PropertySizeItem var28 = (PropertySizeItem)var4;
                if (var28.getKey().equals("property_divider_height")) {
                    var1.dividerHeight = var28.getValue();
                }
            } else if (var4 instanceof Vw) {
                Vw var29 = (Vw)var4;
                if (var29.getKey().equals("property_text_font")) {
                    var1.text.textFont = var29.getValue();
                }
            }
        }

        if (!var1.id.equals(var1.preId)) {
            for(ViewBean var30 : jC.a(this.a).d(this.e.getXmlName())) {
                if (var30.parent.equals(var1.preId)) {
                    var30.parent = var1.id;
                }
            }

            if (this.e.fileType == 0) {
                for(EventBean var31 : jC.a(this.a).g(this.e.getJavaName())) {
                    if (var31.targetId.equals(var1.preId)) {
                        var31.targetId = var1.id;
                    }
                }

                HashMap var32 = jC.a(this.a).b(this.e.getJavaName());

                for(String var6 : oq.c(var1.getClassInfo())) {
                    StringBuilder var7 = new StringBuilder();
                    var7.append(var1.preId);
                    var7.append("_");
                    var7.append(var6);
                    String var8 = var7.toString();
                    if (var32.containsKey(var8)) {
                        var7 = new StringBuilder();
                        var7.append(var1.id);
                        var7.append("_");
                        var7.append(var6);
                        var32.put(var7.toString(), var32.get(var8));
                        var32.remove(var8);
                    }
                }

                ArrayList<String> events = new ArrayList<>();
                if (beanMap.containsKey("onCreate_initializeLogic")) {
                    events.add("onCreate_initializeLogic");
                }

                for (Pair<String, String> moreblocks : jC.a(sc_id).i(e.getJavaName())) {
                    String key = moreblocks.first +
                            "_" +
                            "moreBlock";
                    if (beanMap.containsKey(key)) {
                        events.add(key);
                    }
                }

                for (EventBean eventBean : jC.a(sc_id).g(e.getJavaName())) {
                    if (!eventBean.eventName.equals("onBindCustomView")) {
                        String eventKey = eventBean.getEventKey();
                        if (beanMap.containsKey(eventKey)) {
                            events.add(eventKey);
                        }
                    }
                }

                for (String event : events) {
                    ArrayList<BlockBean> blockBeans = beanMap.get(event);
                    if (blockBeans != null) {
                        for (BlockBean blockBean : blockBeans) {
                            Gx classInfo = blockBean.getClassInfo();
                            if (classInfo != null && classInfo.d() && blockBean.spec.equals(bean.preId)) {
                                blockBean.spec = bean.id;
                            } else {
                                ArrayList<Gx> paramClassInfo = blockBean.getParamClassInfo();
                                if (paramClassInfo != null && paramClassInfo.size() > 0) {
                                    for (int i = 0; i < paramClassInfo.size(); ++i) {
                                        if (paramClassInfo.get(i).d() && blockBean.parameters.get(i).equals(bean.preId)) {
                                            blockBean.parameters.set(i, bean.id);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                ArrayList var73 = new ArrayList();

                for(ProjectFileBean var33 : jC.b(this.a).b()) {
                    for(ViewBean var45 : jC.a(this.a).f(var33.getXmlName())) {
                        if (var45.customView.equals(this.e.fileName)) {
                            String var67 = var33.getJavaName();
                            StringBuilder var10 = new StringBuilder();
                            var10.append(var45.id);
                            var10.append("_");
                            var10.append("onBindCustomView");
                            var73.add(new Pair(var67, var10.toString()));
                        }
                    }
                }

                for(Pair var46 : var73) {
                    ArrayList var47 = (ArrayList)jC.a(this.a).b((String)var46.first).get(var46.second);
                    if (var47 != null) {
                        for(BlockBean var48 : var47) {
                            Gx var68 = var48.getClassInfo();
                            if (var68 != null && var68.d() && var48.spec.equals(var1.preId)) {
                                var48.spec = var1.id;
                            } else {
                                ArrayList var69 = var48.getParamClassInfo();
                                if (var69 != null && var69.size() > 0) {
                                    for(int var15 = 0; var15 < var69.size(); ++var15) {
                                        if (((Gx)var69.get(var15)).d() && ((String)var48.parameters.get(var15)).equals(var1.preId)) {
                                            var48.parameters.set(var15, var1.id);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (bean.type == ViewBean.VIEW_TYPE_WIDGET_LISTVIEW) {
            ViewBean viewBean = jC.a(sc_id).c(e.getXmlName(), bean.preId);
            String custom = bean.customView;
            if (custom != null && viewBean != null) {
                String customView = viewBean.customView;
                if (customView != null && !customView.equals(custom)) {
                    ArrayList<EventBean> eventBeans = jC.a(sc_id).g(e.getJavaName());
                    int size = eventBeans.size();

                    while(true) {
                        childCount = size - 1;
                        if (childCount < 0) {
                            if (bean.customView.equals("") || bean.customView.equals("none")) {
                                Iterator<Entry<String, ArrayList<BlockBean>>> blocks = jC.a(sc_id).b(e.getJavaName()).entrySet().iterator();

                                while(blocks.hasNext()) {
                                    for (BlockBean blockBean : blocks.next().getValue()) {
                                        if ("listSetCustomViewData".equals(blockBean.opCode) && bean.id.equals(blockBean.parameters.get(0))) {
                                            blockBean.parameters.set(0, "");
                                        }
                                    }
                                }
                            }
                            break;
                        }

                        EventBean eventBean = eventBeans.get(childCount);
                        size = childCount;
                        if (eventBean.targetId.equals(bean.id)) {
                            if (eventBean.eventName.equals("onBindCustomView")) {
                                eventBeans.remove(eventBean);
                                HashMap<String, ArrayList<BlockBean>> blocks = jC.a(sc_id).b(e.getJavaName());
                                blocks.remove(eventBean.getEventKey());
                            }
                        }
                    }
                }
            }
        }
    }

    public void setOnPropertyValueChangedListener(Lw listener) {
        d = listener;
    }

    public void setProjectFileBean(ProjectFileBean fileBean) {
        e = fileBean;
    }

    private void openManageImageActivityIfNeeded() {
        if (mB.a() || b) return;
        c();
    }
}
