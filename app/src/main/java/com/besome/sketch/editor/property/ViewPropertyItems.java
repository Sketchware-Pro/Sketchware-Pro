package com.besome.sketch.editor.property;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import a.a.a.Cx;
import a.a.a.Gx;
import a.a.a.Kw;
import a.a.a.Lw;
import a.a.a.cC;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.oq;
import mod.hey.studios.project.ProjectSettings;
import mod.pranav.viewbinding.ViewBindingBuilder;
import pro.sketchware.R;

public class ViewPropertyItems extends LinearLayout implements Kw, View.OnClickListener {
    private final boolean b = false;
    private final HashMap<String, View> f = new HashMap<>();
    private String sc_id;
    private ViewBean c;
    private Lw d;
    private ProjectFileBean e;
    private ProjectSettings settings;

    public ViewPropertyItems(Context var1) {
        super(var1);
        Cx.a().a(var1);
    }

    private void setupViews() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        setLayoutParams(params);
        setGravity(Gravity.CENTER);
        TextView label = new TextView(getContext());
        label.setGravity(Gravity.CENTER);
        label.setPadding(8, 8, 8, 8);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        label.setText(R.string.design_property_recent_message_nodata);
        if (label.getParent() == null) {
            addView(label);
        }
    }

    private void setupMarginAndGravityProperties(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            addSubheader(getContext().getString(R.string.property_header_layout));
        }

        setupPropertyByKey(bean, "property_margin");
        setupPropertyByKey(bean, "property_layout_gravity");
    }

    private void setupPropertyByKey(ViewBean bean, String property) {
        boolean isNotAdview = !bean.getClassInfo().a("AdView");
        boolean isGeneratedId = bean.id.charAt(0) == '_';

        switch (property) {
            case "property_id" -> {
                String id = isGeneratedId ? bean.id.substring(1) : bean.id;
                setupInputProperty(property, id, isGeneratedId, null);
            }
            case "property_layout_width" -> setupMeasureProperty(property, bean.layout.width, isNotAdview);
            case "property_layout_height" -> setupMeasureProperty(property, bean.layout.height, isNotAdview);
            case "property_parent_attr" -> setupAttributes(property, bean.parentAttributes);
            case "property_margin" -> {
                LayoutBean layoutBean = bean.layout;
                setupPaddingProperty(property, layoutBean.marginLeft, layoutBean.marginTop, layoutBean.marginRight, layoutBean.marginBottom);
            }
            case "property_padding" -> {
                LayoutBean layoutBean = bean.layout;
                setupPaddingProperty("property_padding", layoutBean.paddingLeft, layoutBean.paddingTop, layoutBean.paddingRight, layoutBean.paddingBottom);
            }
            case "property_orientation" -> setupSelectorProperty(property, bean.layout.orientation);
            case "property_weight_sum" -> setupInputProperty(property, String.valueOf(bean.layout.weightSum));
            case "property_gravity" -> setupGravityProperty(property, bean.layout.gravity);
            case "property_layout_gravity" -> setupGravityProperty(property, bean.layout.layoutGravity);
            case "property_weight" -> setupInputProperty(property, String.valueOf(bean.layout.weight));
            case "property_text" -> setupInputProperty(property, bean.text.text);
            case "property_text_size" -> setupSelectorProperty(property, bean.text.textSize);
            case "property_text_style" -> setupSelectorProperty(property, bean.text.textType);
            case "property_text_color" -> setupColorPropertyWithRes(property, bean.text.resTextColor, bean.text.textColor);
            case "property_hint" -> setupInputProperty(property, bean.text.hint);
            case "property_hint_color" -> setupColorPropertyWithRes(property, bean.text.resHintColor, bean.text.hintColor);
            case "property_single_line" -> setupSwitchProperty(property, bean.text.singleLine);
            case "property_lines" -> setupInputProperty(property, String.valueOf(bean.text.line));
            case "property_input_type" -> setupSelectorProperty(property, bean.text.inputType);
            case "property_ime_option" -> setupSelectorProperty(property, bean.text.imeOption);
            case "property_image" -> setupResourceProperty(property, bean.image.resName, true);
            case "property_scale_type" -> setupStringSelectorProperty(property, bean.image.scaleType);
            case "property_background_resource" ->
                    setupResourceProperty(property, bean.layout.backgroundResource, false);
            case "property_background_color" ->
                    setupColorPropertyWithRes(property, bean.layout.backgroundResColor, bean.layout.backgroundColor);
            case "property_enabled" -> setupSwitchProperty(property, bean.enabled);
            case "property_rotate" -> setupInputProperty(property, String.valueOf(bean.image.rotate));
            case "property_alpha" -> setupInputProperty(property, String.valueOf(bean.alpha));
            case "property_translation_x" -> setupInputProperty(property, String.valueOf(bean.translationX));
            case "property_translation_y" -> setupInputProperty(property, String.valueOf(bean.translationY));
            case "property_scale_x" -> setupInputProperty(property, String.valueOf(bean.scaleX));
            case "property_scale_y" -> setupInputProperty(property, String.valueOf(bean.scaleY));
            case "property_spinner_mode" -> setupSelectorProperty(property, bean.spinnerMode);
            case "property_divider_height" -> setupSizeProperty(property, bean.dividerHeight);
            case "property_custom_view_listview" -> setupCustomViewProperty(property, bean.customView);
            case "property_checked" -> setupSwitchProperty(property, bean.checked);
            case "property_max" -> setupInputProperty(property, String.valueOf(bean.max));
            case "property_progress" -> setupInputProperty(property, String.valueOf(bean.progress));
            case "property_first_day_of_week" -> setupSelectorProperty(property, bean.firstDayOfWeek);
            case "property_ad_size" -> setupStringSelectorProperty(property, bean.adSize);
            case "property_progressbar_style" -> setupStringPairSelectorProperty(property, bean.progressStyle);
            case "property_indeterminate" -> setupStringSelectorProperty(property, bean.indeterminate);
            case "property_inject" -> setupInputProperty(property, bean.inject);
            case "property_convert" -> setupInputProperty(property, bean.convert, String.valueOf(bean.type));
        }
    }

    private void addSubheader(String header) {
        var sub = new PropertySubheader(getContext());
        sub.setHeaderName(header);
        if (sub.getParent() == null) {
            addView(sub);
        }
    }

    private void setupColorProperty(String name, int value) {
        View cachedItem = f.get(name);
        PropertyColorItem colorItem = cachedItem instanceof PropertyColorItem ? (PropertyColorItem) cachedItem : null;
        if (colorItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (colorItem.getParent() == null) {
            addView(colorItem);
        }
    }

    private void setupPaddingProperty(String key, int left, int top, int right, int bottom) {
        View cachedItem = f.get(key);
        PropertyIndentItem indentItem = cachedItem instanceof PropertyIndentItem ? (PropertyIndentItem) cachedItem : null;
        if (indentItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (indentItem.getParent() == null) {
            addView(indentItem);
        }
    }

    private void setupColorPropertyWithRes(String name, String value, int value2) {
        View cachedItem = f.get(name);
        PropertyColorItem colorItem = cachedItem instanceof PropertyColorItem ? (PropertyColorItem) cachedItem : null;
        if (colorItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
            colorItem = new PropertyColorItem(getContext(), !b, sc_id);
            colorItem.setOrientationItem(getOrientation());
            colorItem.setKey(name);
            colorItem.setValue(value2, value);
            colorItem.setTag(name);
            colorItem.setOnPropertyValueChangeListener(this);
            f.put(name, colorItem);
        } else {
            colorItem.setValue(value2, value);
        }

        if (colorItem.getParent() == null) {
            addView(colorItem);
        }
    }

    private void setupMeasureProperty(String key, int value, boolean isEnable) {
        View cachedItem = f.get(key);
        PropertyMeasureItem measureItem = cachedItem instanceof PropertyMeasureItem ? (PropertyMeasureItem) cachedItem : null;
        int isEnabled;
        if (isEnable) {
            isEnabled = 7;
        } else {
            isEnabled = 3;
        }

        if (measureItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (measureItem.getParent() == null) {
            addView(measureItem);
        }
    }

    private void addSubheaderWithListener(String header, OnClickListener listener) {
        PropertySubheader propertySubheader = new PropertySubheader(getContext());
        propertySubheader.setHeaderName(header);
        propertySubheader.setOnClickListener(listener);
        if (propertySubheader.getParent() == null) {
            addView(propertySubheader);
        }
    }

    public void a(String scId, ViewBean bean) {
        sc_id = scId;
        boolean isSameView = c != null && c.id.equals(bean.id);
        c = bean;
        Cx.a().b(c.getClassInfo().getClassName());
        
        if (!isSameView) {
            removeAllViews();
            f.clear();
        }
        
        if (bean.id.equals("_fab")) {
            setupBasicAndLayoutProperties(bean);
        } else {
            if (getOrientation() == LinearLayout.VERTICAL) {
                setupInputProperty("property_id", bean.id);
            }

            setupSpecialWidgetProperties(bean);
            setupLayoutAndWeightProperties(bean);
            setupTextProperties(bean);
            setupVisualProperties(bean);
            if (getOrientation() == LinearLayout.HORIZONTAL) {
                setupInputProperty("property_id", bean.id);
            }
        }
    }

    @Override
    public void a(String var1, Object var2) {
        Cx.a().a(c.getClassInfo().getClassName(), var1);
        if (d != null) {
            ViewBean cloned = c.clone();
            updateBeanFromUI(c);
            if (!b) {
                cC.c(sc_id).a(e.getXmlName(), cloned, c.clone());
                d.a(c);
            }
        }
    }

    private void setupCustomViewProperty(String key, String value) {
        PropertyCustomViewItem propertyCustomViewItem = (PropertyCustomViewItem) f.get(key);
        if (propertyCustomViewItem == null) {
            propertyCustomViewItem = new PropertyCustomViewItem(getContext(), !b);
            propertyCustomViewItem.setOrientationItem(getOrientation());
            propertyCustomViewItem.setKey(key);
            propertyCustomViewItem.setTag(key);
            propertyCustomViewItem.setOnPropertyValueChangeListener(this);
            f.put(key, propertyCustomViewItem);
        }

        propertyCustomViewItem.setCustomView(jC.b(sc_id).c());
        propertyCustomViewItem.setValue(value);

        if (propertyCustomViewItem.getParent() == null) {
            addView(propertyCustomViewItem);
        }
    }

    private void setupInputProperty(String key, String value, boolean z, String typeView) {
        View cachedItem = f.get(key);
        PropertyInputItem inputItem = cachedItem instanceof PropertyInputItem ? (PropertyInputItem) cachedItem : null;
        if (inputItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
            inputItem = new PropertyInputItem(getContext(), !z);
            inputItem.setOrientationItem(getOrientation());
            inputItem.setTypeView(typeView);
            inputItem.a(sc_id, e);
            inputItem.setKey(key);
            inputItem.setValue(value);
            inputItem.setTag(key);
            inputItem.setOnPropertyValueChangeListener(this);
            f.put(key, inputItem);
        } else {
            inputItem.setTypeView(typeView);
            inputItem.a(sc_id, e);
            inputItem.setValue(value);
        }
        inputItem.setBean(c);

        if (inputItem.getParent() == null) {
            addView(inputItem);
        }
    }

    public void save() {
        Cx.a().b();
    }

    private void setupBasicAndLayoutProperties(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            setupPropertyByKey(bean, "property_id");
        }

        setupMarginAndGravityProperties(bean);
        setupImageAndTransformProperties(bean);
        if (getOrientation() == LinearLayout.HORIZONTAL) {
            setupPropertyByKey(bean, "property_id");
        }
    }

    private void setupGravityProperty(String key, int value) {
        View cachedItem = f.get(key);
        PropertyGravityItem gravityItem = cachedItem instanceof PropertyGravityItem ? (PropertyGravityItem) cachedItem : null;
        if (gravityItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (gravityItem.getParent() == null) {
            addView(gravityItem);
        }
    }

    private void setupInputProperty(String key, String value) {
        setupInputProperty(key, value, b, null);
    }

    private void setupInputProperty(String key, String value, String viewType) {
        setupInputProperty(key, value, b, viewType);
    }

    private void setupResourceProperty(String key, String value, boolean z) {
        View cachedItem = f.get(key);
        PropertyResourceItem drawableItem = cachedItem instanceof PropertyResourceItem ? (PropertyResourceItem) cachedItem : null;
        if (drawableItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
            drawableItem = new PropertyResourceItem(getContext(), !b, sc_id, z);
            drawableItem.setOrientationItem(getOrientation());
            drawableItem.setKey(key);
            drawableItem.setValue(value);
            drawableItem.setTag(key);
            drawableItem.setOnPropertyValueChangeListener(this);
            f.put(key, drawableItem);
        } else {
            drawableItem.setValue(value);
        }

        if (drawableItem.getParent() == null) {
            addView(drawableItem);
        }
    }

    private void setupImageAndTransformProperties(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            addSubheaderWithListener(getContext().getString(R.string.property_header_image), this);
        }

        setupPropertyByKey(bean, "property_image");
        setupPropertyByKey(bean, "property_rotate");
        setupPropertyByKey(bean, "property_alpha");
        setupPropertyByKey(bean, "property_translation_x");
        setupPropertyByKey(bean, "property_translation_y");
        setupPropertyByKey(bean, "property_scale_x");
        setupPropertyByKey(bean, "property_scale_y");
    }

    private void setupSelectorProperty(String key, int value) {
        View cachedItem = f.get(key);
        PropertySelectorItem selectorItem = cachedItem instanceof PropertySelectorItem ? (PropertySelectorItem) cachedItem : null;
        if (selectorItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (selectorItem.getParent() == null) {
            addView(selectorItem);
        }
    }

    private void setupStringPairSelectorProperty(String key, String value) {
        View cachedItem = f.get(key);
        PropertyStringPairSelectorItem pairSelectorItem = cachedItem instanceof PropertyStringPairSelectorItem ? (PropertyStringPairSelectorItem) cachedItem : null;
        if (pairSelectorItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (pairSelectorItem.getParent() == null) {
            addView(pairSelectorItem);
        }
    }

    public void setupLayoutAndWeightProperties(ViewBean bean) {
        if (getOrientation() == LinearLayout.VERTICAL) {
            addSubheader(getContext().getString(R.string.property_header_layout));
        }

        Gx classInfo = bean.getClassInfo();
        Gx parentClassInfo = bean.getParentClassInfo();
        setupPropertyByKey(bean, "property_layout_width");
        setupPropertyByKey(bean, "property_layout_height");
        if (bean.parentType == ViewBean.VIEW_TYPE_LAYOUT_RELATIVE) {
            setupPropertyByKey(bean, "property_parent_attr");
        }
        setupPropertyByKey(bean, "property_padding");
        setupPropertyByKey(bean, "property_margin");
        if (classInfo.a("LinearLayout")) {
            setupPropertyByKey(bean, "property_orientation");
            setupPropertyByKey(bean, "property_weight_sum");
            setupPropertyByKey(bean, "property_gravity");
        }

        if (classInfo.a("TextView")) {
            setupPropertyByKey(bean, "property_gravity");
        }

        if (parentClassInfo != null) {
            if (parentClassInfo.a("LinearLayout")) {
                setupPropertyByKey(bean, "property_layout_gravity");
                setupPropertyByKey(bean, "property_weight");
            }

            if (parentClassInfo.a("ScrollView") || parentClassInfo.a("HorizontalScrollView")) {
                setupPropertyByKey(bean, "property_layout_gravity");
            }
        }
    }

    private void setupSizeProperty(String key, int value) {
        View cachedItem = f.get(key);
        PropertySizeItem propertySizeItem = cachedItem instanceof PropertySizeItem ? (PropertySizeItem) cachedItem : null;
        if (propertySizeItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (propertySizeItem.getParent() == null) {
            addView(propertySizeItem);
        }
    }

    private void setupStringSelectorProperty(String key, String value) {
        View cachedItem = f.get(key);
        PropertyStringSelectorItem stringSelectorItem = cachedItem instanceof PropertyStringSelectorItem ? (PropertyStringSelectorItem) cachedItem : null;
        if (stringSelectorItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (stringSelectorItem.getParent() == null) {
            addView(stringSelectorItem);
        }
    }

    public void setupAllPropertiesFromConfig(ViewBean bean) {
        c = bean;
        removeAllViews();
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.LEFT;
        setLayoutParams(params);
        setGravity(Gravity.LEFT);
        ArrayList<String> items = Cx.a().a(bean.getClassInfo().getClassName());
        if (items == null) {
            setupViews();
        } else {
            for (String s : items) {
                setupPropertyByKey(bean, s);
            }
        }
    }

    private void setupSwitchProperty(String key, int value) {
        View cachedItem = f.get(key);
        PropertySwitchSingleLineItem switchSingleLineItem = cachedItem instanceof PropertySwitchSingleLineItem ? (PropertySwitchSingleLineItem) cachedItem : null;
        boolean isEnabled = false;
        if (switchSingleLineItem == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
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

        if (switchSingleLineItem.getParent() == null) {
            addView(switchSingleLineItem);
        }
    }

    private void setupAttributes(String key, HashMap<String, String> value) {
        ArrayList<ViewBean> viewBeans = jC.a(sc_id).d(e.getXmlName());
        List<String> ids = new ArrayList<>();
        for (ViewBean bean : viewBeans) {
            if (!bean.id.equals(c.id) && bean.parent.equals(c.parent)) {
                ids.add(bean.id);
            }
        }
        View cachedItem = f.get(key);
        PropertyAttributesItem item = cachedItem instanceof PropertyAttributesItem ? (PropertyAttributesItem) cachedItem : null;
        if (item == null) {
            if (cachedItem != null && cachedItem.getParent() != null) {
                ((ViewGroup) cachedItem.getParent()).removeView(cachedItem);
            }
            item = new PropertyAttributesItem(getContext(), !b);
            item.setOrientationItem(getOrientation());
            item.setKey(key);
            item.setValue(value);
            item.setTag(key);
            item.setOnPropertyValueChangeListener(this);
            f.put(key, item);
        } else {
            item.setValue(value);
        }
        item.setBean(c);
        item.setBeans(viewBeans);
        item.setAvailableIds(ids);

        if (item.getParent() == null) {
            addView(item);
        }
    }

    public void setupSpecialWidgetProperties(ViewBean bean) {
        if (!bean.id.equals("_fab")) {
            Gx classInfo = bean.getClassInfo();
            setupPropertyByKey(bean, "property_inject");
            setupPropertyByKey(bean, "property_convert");
            if (classInfo.b("Spinner")) {
                setupPropertyByKey(bean, "property_spinner_mode");
            }

            if (classInfo.b("ListView")) {
                setupPropertyByKey(bean, "property_divider_height");
                setupPropertyByKey(bean, "property_custom_view_listview");
            }

            if (classInfo.b("GridView")) {
                setupPropertyByKey(bean, "property_custom_view_listview");
            }

            if (classInfo.b("RecyclerView")) {
                setupPropertyByKey(bean, "property_custom_view_listview");
            }

            if (classInfo.b("ViewPager")) {
                setupPropertyByKey(bean, "property_custom_view_listview");
            }

            if (classInfo.b("Spinner")) {
                setupPropertyByKey(bean, "property_custom_view_listview");
            }

            if (classInfo.b("AutoCompleteTextView")) {
                setupPropertyByKey(bean, "property_hint");
                setupPropertyByKey(bean, "property_hint_color");
            }

            if (classInfo.b("MultiAutoCompleteTextView")) {
                setupPropertyByKey(bean, "property_hint");
                setupPropertyByKey(bean, "property_hint_color");
            }

            if (classInfo.b("WaveSideBar")) {
                setupPropertyByKey(bean, "property_text_size");
                setupPropertyByKey(bean, "property_text_color");
            }

            if (classInfo.a("CompoundButton") && getOrientation() == LinearLayout.VERTICAL) {
                setupPropertyByKey(bean, "property_checked");
            }

            if (classInfo.b("SeekBar")) {
                setupPropertyByKey(bean, "property_max");
                setupPropertyByKey(bean, "property_progress");
            }

            if (classInfo.b("CalendarView")) {
                setupPropertyByKey(bean, "property_first_day_of_week");
            }

            if (classInfo.b("AdView")) {
                setupPropertyByKey(bean, "property_ad_size");
            }

            if (classInfo.b("ProgressBar")) {
                setupPropertyByKey(bean, "property_max");
                setupPropertyByKey(bean, "property_progress");
                setupPropertyByKey(bean, "property_progressbar_style");
                setupPropertyByKey(bean, "property_indeterminate");
            }
        }
    }

    public void setupTextProperties(ViewBean bean) {
        Gx classInfo = bean.getClassInfo();
        if (classInfo.a("TextView")) {
            if (getOrientation() == LinearLayout.VERTICAL) {
                addSubheader(getContext().getString(R.string.property_header_text));
            }

            setupPropertyByKey(bean, "property_text");
            setupPropertyByKey(bean, "property_text_size");
            setupPropertyByKey(bean, "property_text_style");
            setupPropertyByKey(bean, "property_text_color");
            if (classInfo.b("EditText")) {
                setupPropertyByKey(bean, "property_hint");
                setupPropertyByKey(bean, "property_hint_color");
                if (getOrientation() == LinearLayout.VERTICAL) {
                    setupPropertyByKey(bean, "property_single_line");
                }

                setupPropertyByKey(bean, "property_lines");
                setupPropertyByKey(bean, "property_input_type");
                setupPropertyByKey(bean, "property_ime_option");
            }

            if (classInfo.b("TextView")) {
                if (getOrientation() == LinearLayout.VERTICAL) {
                    setupPropertyByKey(bean, "property_single_line");
                }

                setupPropertyByKey(bean, "property_lines");
            }
        }
    }

    public void setupVisualProperties(ViewBean bean) {
        Gx classInfo = bean.getClassInfo();
        if (getOrientation() == LinearLayout.VERTICAL) {
            if (classInfo.a("ImageView")) {
                addSubheaderWithListener(getContext().getString(R.string.property_header_image), this);
                setupPropertyByKey(bean, "property_image");
                setupPropertyByKey(bean, "property_scale_type");
            } else {
                addSubheader(getContext().getString(R.string.property_header_image));
            }
        } else if (classInfo.a("ImageView")) {
            setupPropertyByKey(bean, "property_image");
            setupPropertyByKey(bean, "property_scale_type");
        }

        if (bean.type != ViewBean.VIEW_TYPE_WIDGET_MAPVIEW) {
            setupPropertyByKey(bean, "property_background_resource");
            setupPropertyByKey(bean, "property_background_color");
        }

        if (getOrientation() == LinearLayout.VERTICAL
                && !classInfo.b("LinearLayout")
                && !classInfo.b("ScrollView")
                && !classInfo.b("HorizontalScrollView")
                && !classInfo.b("ListView")
                && !classInfo.b("FloatingActionButton")) {
            setupPropertyByKey(bean, "property_enabled");
        }

        setupPropertyByKey(bean, "property_rotate");
        setupPropertyByKey(bean, "property_alpha");
        setupPropertyByKey(bean, "property_translation_x");
        setupPropertyByKey(bean, "property_translation_y");
        setupPropertyByKey(bean, "property_scale_x");
        setupPropertyByKey(bean, "property_scale_y");
    }

    public void updateBeanFromUI(ViewBean bean) {
        int childCount = getChildCount();

        for (int i = 0; i < childCount; ++i) {
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
                    case "property_text_size" -> bean.text.textSize = Integer.parseInt(inputItem.getValue());
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
                    case "property_lines" ->
                            bean.text.line = Integer.parseInt(inputItem.getValue());
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
                    case "property_orientation" ->
                            bean.layout.orientation = selectorItem.getValue();
                    case "property_text_style" -> bean.text.textType = selectorItem.getValue();
                    case "property_input_type" -> bean.text.inputType = selectorItem.getValue();
                    case "property_ime_option" -> bean.text.imeOption = selectorItem.getValue();
                    case "property_spinner_mode" -> bean.spinnerMode = selectorItem.getValue();
                    case "property_first_day_of_week" ->
                            bean.firstDayOfWeek = selectorItem.getValue();
                }
            } else if (view instanceof PropertyStringSelectorItem stringSelectorItem) {
                switch (stringSelectorItem.getKey()) {
                    case "property_scale_type" ->
                            bean.image.scaleType = stringSelectorItem.getValue();
                    case "property_ad_size" -> bean.adSize = stringSelectorItem.getValue();
                    case "property_indeterminate" ->
                            bean.indeterminate = stringSelectorItem.getValue();
                }
            } else if (view instanceof PropertyStringPairSelectorItem stringPairSelectorItem) {
                if (stringPairSelectorItem.getKey().equals("property_progressbar_style")) {
                    bean.progressStyle = stringPairSelectorItem.getValue();
                }
            } else if (view instanceof PropertyCustomViewItem listview_item) {
                if (listview_item.getKey().equals("property_custom_view_listview")) {
                    bean.customView = listview_item.getValue();
                }
            } else if (view instanceof PropertySwitchSingleLineItem switchSingleLineItem) {
                switch (switchSingleLineItem.getKey()) {
                    case "property_single_line" ->
                            bean.text.singleLine = switchSingleLineItem.getValue() ? 1 : 0;
                    case "property_enabled" ->
                            bean.enabled = switchSingleLineItem.getValue() ? 1 : 0;
                    case "property_clickable" ->
                            bean.clickable = switchSingleLineItem.getValue() ? 1 : 0;
                    case "property_checked" ->
                            bean.checked = switchSingleLineItem.getValue() ? 1 : 0;
                }
            } else if (view instanceof PropertyColorItem colorItem) {
                switch (colorItem.getKey()) {
                    case "property_text_color" -> {
                        bean.text.textColor = colorItem.getValue();
                        bean.text.resTextColor = colorItem.getResValue();
                    }
                    case "property_hint_color" -> {
                        bean.text.hintColor = colorItem.getValue();
                        bean.text.resHintColor = colorItem.getResValue();
                    }
                    case "property_background_color" -> {
                        bean.layout.backgroundResColor = colorItem.getResValue();
                        bean.layout.backgroundColor = colorItem.getValue();
                    }
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
            } else if (view instanceof PropertyResourceItem image_item) {
                if (image_item.getKey().equals("property_image")) {
                    bean.image.resName = image_item.getValue();
                } else if (image_item.getKey().equals("property_background_resource")) {
                    bean.layout.backgroundResource = image_item.getValue();
                }
            } else if (view instanceof PropertySizeItem sizeItem) {
                if (sizeItem.getKey().equals("property_divider_height")) {
                    bean.dividerHeight = sizeItem.getValue();
                }
            } else if (view instanceof PropertyAttributesItem item) {
                if (item.getKey().equals("property_parent_attr")) {
                    bean.parentAttributes = item.getValue();
                }
            }
        }

        if (!bean.id.equals(bean.preId)) {
            boolean viewBinding = settings.getValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, "false").equals("true");
            for (ViewBean viewBean : jC.a(sc_id).d(e.getXmlName())) {
                if (viewBean.parent.equals(bean.preId)) {
                    viewBean.parent = bean.id;
                }
            }

            if (e.fileType == ProjectFileBean.PROJECT_FILE_TYPE_ACTIVITY) {
                for (EventBean eventBean : jC.a(sc_id).g(e.getJavaName())) {
                    if (eventBean.targetId.equals(bean.preId)) {
                        eventBean.targetId = bean.id;
                    }
                }

                HashMap<String, ArrayList<BlockBean>> beanMap = jC.a(sc_id).b(e.getJavaName());

                for (String events : oq.getEventsForClass(bean.getClassInfo())) {
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

                            if (classInfo != null && classInfo.d() && blockBean.spec.equals(viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.preId) : bean.preId)) {
                                blockBean.spec = viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.id) : bean.id;
                            } else {
                                ArrayList<Gx> paramClassInfo = blockBean.getParamClassInfo();
                                if (paramClassInfo != null && !paramClassInfo.isEmpty()) {
                                    for (int i = 0; i < paramClassInfo.size(); ++i) {
                                        if (paramClassInfo.get(i).d() && blockBean.parameters.get(i).equals(viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.preId) : bean.preId)) {
                                            blockBean.parameters.set(i, viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.id) : bean.id);
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
                    for (ViewBean viewBean : jC.a(sc_id).f(projectFileBean.getXmlName())) {
                        if (viewBean.customView.equals(e.fileName)) {
                            String javaName = projectFileBean.getJavaName();
                            String key = viewBean.id +
                                    "_" +
                                    "onBindCustomView";
                            viewBeanItems.add(new Pair<>(javaName, key));
                        }
                    }
                }

                for (Pair<String, String> viewBean : viewBeanItems) {
                    ArrayList<BlockBean> blockBeans = jC.a(sc_id).b(viewBean.first).get(viewBean.second);
                    if (blockBeans != null) {
                        for (BlockBean blockBean : blockBeans) {
                            Gx classInfo = blockBean.getClassInfo();

                            if (classInfo != null && classInfo.d() && blockBean.spec.equals(viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.preId) : bean.preId)) {
                                blockBean.spec = viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.id) : bean.id;
                            } else {
                                ArrayList<Gx> paramClassInfo = blockBean.getParamClassInfo();
                                if (paramClassInfo != null && !paramClassInfo.isEmpty()) {
                                    for (int i = 0; i < paramClassInfo.size(); ++i) {
                                        if (paramClassInfo.get(i).d() && blockBean.parameters.get(i).equals(viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.preId) : bean.preId)) {
                                            blockBean.parameters.set(i, viewBinding ? "binding." + ViewBindingBuilder.generateParameterFromId(bean.id) : bean.id);
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

                    while (true) {
                        childCount = size - 1;
                        if (childCount < 0) {
                            if (bean.customView.isEmpty() || bean.customView.equals("none")) {
                                Iterator<Entry<String, ArrayList<BlockBean>>> blocks = jC.a(sc_id).b(e.getJavaName()).entrySet().iterator();

                                while (blocks.hasNext()) {
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


    public void setProjectSettings(ProjectSettings settings) {
        this.settings = settings;
    }

    public void setOnPropertyValueChangedListener(Lw listener) {
        d = listener;
    }

    public void setProjectFileBean(ProjectFileBean fileBean) {
        e = fileBean;
    }

    @Override
    public void onClick(View v) {
        if (!mB.a() && !b) {
            Intent intent = new Intent(getContext(), ManageImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("sc_id", sc_id);
            ((Activity) getContext()).startActivityForResult(intent, 209);
        }
    }
}
