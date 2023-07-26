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
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private String sc_id;
    public boolean b = false;
    public ViewBean c;
    public Lw d;
    public ProjectFileBean e;
    public HashMap<String, View> f = new HashMap<>();

    public ViewPropertyItems(Context var1) {
        super(var1);
        Cx.a().a(var1);
    }

    private void setupViews() {
        LayoutParams params = new LayoutParams(-1, -1);
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        setGravity(Gravity.CENTER);
        TextView label = new TextView(getContext());
        label.setTextColor(getResources().getColor(2131034217));
        label.setGravity(Gravity.CENTER);
        label.setPadding(8, 8, 8, 8);
        label.setTextSize(2, 12.0F);
        label.setText(xB.b().a(getContext(), 2131625308));
        addView(label);
    }

    private void a(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            a(xB.b().a(getResources(), 2131625828));
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

    private void a(String header) {
        var sub = new PropertySubheader(getContext());
        sub.setHeaderName(header);
        addView(sub);
    }

    private void a(String name, int value) {
        PropertyColorItem colorItem = (PropertyColorItem)f.get(name);
        if (colorItem == null) {
            colorItem = new PropertyColorItem(getContext(), !b);
            colorItem.setOrientationItem(getOrientation());
            colorItem.setKey(name);
            colorItem.setValue(value);
            colorItem.setTag(name);
            colorItem.setOnPropertyValueChangeListener(this);
            f.put(name, colorItem);
        } else {
            colorItem.setValue(value);
        }

        addView(colorItem);
    }

    private void a(String key, int left, int top, int right, int bottom) {
        PropertyIndentItem indentItem = (PropertyIndentItem)f.get(key);
        if (indentItem == null) {
            indentItem = new PropertyIndentItem(getContext(), !b);
            indentItem.setOrientationItem(getOrientation());
            indentItem.setKey(key);
            indentItem.a(left, top, right, bottom);
            indentItem.setTag(key);
            indentItem.setOnPropertyValueChangeListener(this);
            f.put(key, indentItem);
        } else {
            indentItem.a(left, top, right, bottom);
        }

        addView(indentItem);
    }

    private void a(String key, int value, boolean isEnable) {
        PropertyMeasureItem measureItem = (PropertyMeasureItem)f.get(key);
        int isEnabled;
        if (isEnable) {
            isEnabled = 7;
        } else {
            isEnabled = 3;
        }

        if (measureItem == null) {
            measureItem = new PropertyMeasureItem(getContext(), !b);
            measureItem.setOrientationItem(getOrientation());
            measureItem.setItemEnabled(isEnabled);
            measureItem.setKey(key);
            measureItem.setValue(value);
            measureItem.setTag(key);
            measureItem.setOnPropertyValueChangeListener(this);
            f.put(key, measureItem);
        } else {
            measureItem.setItemEnabled(isEnabled);
            measureItem.setValue(value);
        }

        addView(measureItem);
    }

    private void a(String header, OnClickListener listener) {
        PropertySubheader propertySubheader = new PropertySubheader(getContext());
        propertySubheader.setHeaderName(header);
        propertySubheader.setOnClickListener(listener);
        addView(propertySubheader);
    }

    public void a(String scId, ViewBean bean) {
        sc_id = scId;
        c = bean;
        Cx.a().b(c.getClassInfo().a());
        removeAllViews();
        if (bean.id.equals("_fab")) {
            b(bean);
        } else {
            if (getOrientation() == LinearLayout.VERTICAL) {
                b("property_id", bean.id);
            }

            f(bean);
            d(bean);
            g(bean);
            h(bean);
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                b("property_id", bean.id);
            }
        }
    }

    public void a(String var1, Object var2) {
        Cx.a().a(c.getClassInfo().a(), var1);
        if (d != null) {
            ViewBean cloned = c.clone();
            i(c);
            if (!b) {
                cC.c(sc_id).a(e.getXmlName(), cloned, c.clone());
                d.a(c);
            }
        }
    }

    public void a(String key, String value) {
        Pw pw = (Pw)f.get(key);
        if (pw == null) {
            pw = new Pw(getContext(), !b);
            pw.setOrientationItem(getOrientation());
            pw.setKey(key);
            pw.setTag(key);
            pw.setOnPropertyValueChangeListener(this);
            f.put(key, pw);
        }

        pw.setCustomView(jC.b(sc_id).c());
        pw.setValue(value);
        addView(pw);
    }

    public void a(String key, String value, boolean z) {
        PropertyInputItem inputItem = (PropertyInputItem)f.get(key);
        if (inputItem == null) {
            inputItem = new PropertyInputItem(getContext(), !z);
            inputItem.setOrientationItem(getOrientation());
            inputItem.a(sc_id, e);
            inputItem.setKey(key);
            inputItem.setValue(value);
            inputItem.setTag(key);
            inputItem.setOnPropertyValueChangeListener(this);
            f.put(key, inputItem);
        } else {
            inputItem.a(sc_id, e);
            inputItem.setValue(value);
        }

        addView(inputItem);
    }

    public void save() {
        Cx.a().b();
    }

    private void b(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            a(bean, "property_id");
        }

        a(bean);
        c(bean);
        if (getOrientation() == LinearLayout.HORIZONTAL) {
            a(bean, "property_id");
        }
    }

    private void b(String key, int value) {
        PropertyGravityItem gravityItem = (PropertyGravityItem)f.get(key);
        if (gravityItem == null) {
            gravityItem = new PropertyGravityItem(getContext(), !b);
            gravityItem.setOrientationItem(getOrientation());
            gravityItem.setKey(key);
            gravityItem.setValue(value);
            gravityItem.setTag(key);
            gravityItem.setOnPropertyValueChangeListener(this);
            f.put(key, gravityItem);
        } else {
            gravityItem.setValue(value);
        }

        addView(gravityItem);
    }

    private void b(String key, String value) {
        a(key, value, b);
    }

    private void b(String key, String value, boolean z) {
        tx drawableItem = (tx)f.get(key);
        if (drawableItem == null) {
            drawableItem = new tx(getContext(), !b, sc_id, z);
            drawableItem.setOrientationItem(getOrientation());
            drawableItem.setKey(key);
            drawableItem.setValue(value);
            drawableItem.setTag(key);
            drawableItem.setOnPropertyValueChangeListener(this);
            f.put(key, drawableItem);
        } else {
            drawableItem.setValue(value);
        }
        addView(drawableItem);
    }

    private void c() {
        Intent intent = new Intent(getContext(), ManageImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        ((Activity)getContext()).startActivityForResult(intent, 209);
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

    public void d(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            a(xB.b().a(getResources(), 2131625828));
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
            for (String s : items) {
                a(bean, s);
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

    public void i(ViewBean bean) {
        int childCount = getChildCount();

        for(int i = 0; i < childCount; ++i) {
            View view = getChildAt(i);
            if (view instanceof PropertyInputItem inputItem) {
                switch (inputItem.getKey()) {
                    case "property_id" -> {
                        String var5 = bean.id;
                        bean.preId = var5;
                        if (var5.charAt(0) != '_') {
                            bean.id = inputItem.getValue();
                        }
                    }
                    case "property_convert" -> bean.convert = inputItem.getValue();
                    case "property_inject" -> bean.inject = inputItem.getValue();
                    case "property_text" -> bean.text.text = inputItem.getValue();
                    case "property_hint" -> bean.text.hint = inputItem.getValue();
                    case "property_weight" ->
                            bean.layout.weight = Integer.parseInt(inputItem.getValue());
                    case "property_weight_sum" ->
                            bean.layout.weightSum = Integer.parseInt(inputItem.getValue());
                    case "property_rotate" ->
                            bean.image.rotate = Integer.parseInt(inputItem.getValue());
                    case "property_alpha" -> bean.alpha = Float.parseFloat(inputItem.getValue());
                    case "property_translation_x" ->
                            bean.translationX = Float.parseFloat(inputItem.getValue());
                    case "property_translation_y" ->
                            bean.translationY = Float.parseFloat(inputItem.getValue());
                    case "property_scale_x" -> bean.scaleX = Float.parseFloat(inputItem.getValue());
                    case "property_scale_y" -> bean.scaleY = Float.parseFloat(inputItem.getValue());
                    case "property_lines" -> bean.text.line = Integer.parseInt(inputItem.getValue());
                    case "property_max" -> bean.max = Integer.parseInt(inputItem.getValue());
                    case "property_progress" ->
                            bean.progress = Integer.parseInt(inputItem.getValue());
                }
            } else if (view instanceof PropertyMeasureItem measureItem) {
                if (measureItem.getKey().equals("property_layout_width")) {
                    bean.layout.width = measureItem.getValue();
                } else if (measureItem.getKey().equals("property_layout_height")) {
                    bean.layout.height = measureItem.getValue();
                }
            } else if (view instanceof PropertySelectorItem selectorItem) {
                switch (selectorItem.getKey()) {
                    case "property_orientation" -> bean.layout.orientation = selectorItem.getValue();
                    case "property_text_style" -> bean.text.textType = selectorItem.getValue();
                    case "property_text_size" -> bean.text.textSize = selectorItem.getValue();
                    case "property_input_type" -> bean.text.inputType = selectorItem.getValue();
                    case "property_ime_option" -> bean.text.imeOption = selectorItem.getValue();
                    case "property_spinner_mode" -> bean.spinnerMode = selectorItem.getValue();
                    case "property_first_day_of_week" -> bean.firstDayOfWeek = selectorItem.getValue();
                }
            } else if (view instanceof PropertyStringSelectorItem stringSelectorItem) {
                switch (stringSelectorItem.getKey()) {
                    case "property_scale_type" -> bean.image.scaleType = stringSelectorItem.getValue();
                    case "property_ad_size" -> bean.adSize = stringSelectorItem.getValue();
                    case "property_indeterminate" -> bean.indeterminate = stringSelectorItem.getValue();
                }
            } else if (view instanceof PropertyStringPairSelectorItem stringPairSelectorItem) {
                if (stringPairSelectorItem.getKey().equals("property_progressbar_style")) {
                    bean.progressStyle = stringPairSelectorItem.getValue();
                }
            } else if (view instanceof Pw listview_item) {
                if (listview_item.getKey().equals("property_custom_view_listview")) {
                    bean.customView = listview_item.getValue();
                }
            } else if (view instanceof PropertySwitchSingleLineItem switchSingleLineItem) {
                switch (switchSingleLineItem.getKey()) {
                    case "property_single_line" ->
                            bean.text.singleLine = switchSingleLineItem.getValue() ? 1 : 0;
                    case "property_enabled" -> bean.enabled = switchSingleLineItem.getValue() ? 1 : 0;
                    case "property_clickable" -> bean.clickable = switchSingleLineItem.getValue() ? 1 : 0;
                    case "property_checked" -> bean.checked = switchSingleLineItem.getValue() ? 1 : 0;
                }
            } else if (view instanceof PropertyColorItem colorItem) {
                switch (colorItem.getKey()) {
                    case "property_text_color" -> bean.text.textColor = colorItem.getValue();
                    case "property_hint_color" -> bean.text.hintColor = colorItem.getValue();
                    case "property_background_color" ->
                            bean.layout.backgroundColor = colorItem.getValue();
                }
            } else if (view instanceof PropertyIndentItem indentItem) {
                if (indentItem.getKey().equals("property_margin")) {
                    LayoutBean layout = bean.layout;
                    layout.marginLeft = indentItem.j;
                    layout.marginTop = indentItem.k;
                    layout.marginRight = indentItem.l;
                    layout.marginBottom = indentItem.m;
                } else if (indentItem.getKey().equals("property_padding")) {
                    LayoutBean layout = bean.layout;
                    layout.paddingLeft = indentItem.j;
                    layout.paddingTop = indentItem.k;
                    layout.paddingRight = indentItem.l;
                    layout.paddingBottom = indentItem.m;
                }
            } else if (view instanceof PropertyGravityItem gravityItem) {
                if (gravityItem.getKey().equals("property_gravity")) {
                    bean.layout.gravity = gravityItem.getValue();
                } else if (gravityItem.getKey().equals("property_layout_gravity")) {
                    bean.layout.layoutGravity = gravityItem.getValue();
                }
            } else if (view instanceof tx image_item) {
                if (image_item.getKey().equals("property_image")) {
                    bean.image.resName = image_item.getValue();
                } else if (image_item.getKey().equals("property_background_resource")) {
                    bean.layout.backgroundResource = image_item.getValue();
                }
            } else if (view instanceof PropertySizeItem sizeItem) {
                if (sizeItem.getKey().equals("property_divider_height")) {
                    bean.dividerHeight = sizeItem.getValue();
                }
            } else if (view instanceof Vw vw) {
                if (vw.getKey().equals("property_text_font")) {
                    bean.text.textFont = vw.getValue();
                }
            }
        }

        if (!bean.id.equals(bean.preId)) {
            for(ViewBean viewBean : jC.a(sc_id).d(e.getXmlName())) {
                if (viewBean.parent.equals(bean.preId)) {
                    viewBean.parent = bean.id;
                }
            }

            if (e.fileType == 0) {
                for(EventBean eventBean : jC.a(sc_id).g(e.getJavaName())) {
                    if (eventBean.targetId.equals(bean.preId)) {
                        eventBean.targetId = bean.id;
                    }
                }

                HashMap<String, ArrayList<BlockBean>> beanMap = jC.a(sc_id).b(e.getJavaName());

                for(String events : oq.c(bean.getClassInfo())) {
                    StringBuilder eventBodyBuilder = new StringBuilder();
                    eventBodyBuilder.append(bean.preId);
                    eventBodyBuilder.append("_");
                    eventBodyBuilder.append(events);
                    String body = eventBodyBuilder.toString();
                    if (beanMap.containsKey(body)) {
                        eventBodyBuilder = new StringBuilder();
                        eventBodyBuilder.append(bean.id);
                        eventBodyBuilder.append("_");
                        eventBodyBuilder.append(events);
                        beanMap.put(eventBodyBuilder.toString(), beanMap.get(body));
                        beanMap.remove(body);
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
                ArrayList<Pair<String, String>> viewBeanItems = new ArrayList<>();

                for (ProjectFileBean projectFileBean : jC.b(sc_id).b()) {
                    for(ViewBean viewBean : jC.a(sc_id).f(projectFileBean.getXmlName())) {
                        if (viewBean.customView.equals(e.fileName)) {
                            String javaName = projectFileBean.getJavaName();
                            String key = viewBean.id +
                                    "_" +
                                    "onBindCustomView";
                            viewBeanItems.add(new Pair<>(javaName, key));
                        }
                    }
                }

                for(Pair<String, String> viewBean : viewBeanItems) {
                    ArrayList<BlockBean> blockBeans = jC.a(sc_id).b(viewBean.first).get(viewBean.second);
                    if (blockBeans != null) {
                        for( BlockBean blockBean : blockBeans) {
                            Gx classInfo = blockBean.getClassInfo();
                            if (classInfo != null && classInfo.d() && blockBean.spec.equals(bean.preId)) {
                                blockBean.spec = bean.id;
                            } else {
                                ArrayList<Gx> paramClassInfo = blockBean.getParamClassInfo();
                                if (paramClassInfo != null && paramClassInfo.size() > 0) {
                                    for(int i = 0; i < paramClassInfo.size(); ++i) {
                                        if (paramClassInfo.get(i).d() && blockBean.parameters.get(i).equals(bean.preId)) {
                                            blockBean.parameters.set(i, bean.id);
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
