package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.HashMap;

import a.a.a.Gx;
import a.a.a.nA;
import mod.agus.jcoderz.beans.ViewBeans;
import pro.sketchware.R;

public class ViewBean extends nA implements Parcelable {
    public static final Parcelable.Creator<ViewBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public ViewBean createFromParcel(Parcel source) {
            return new ViewBean(source);
        }

        @Override
        public ViewBean[] newArray(int size) {
            return new ViewBean[size];
        }
    };

    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    public static final int CHOICE_MODE_MULTI = 2;


    public static final int DEFAULT_PROGRESS = 0;
    public static final int DEFAULT_MAX = 100;

    public static final String PROGRESSBAR_STYLE_CIRCLE = "?android:progressBarStyle";
    public static final String PROGRESSBAR_STYLE_HORIZONTAL = "?android:progressBarStyleHorizontal";

    public static final int SPINNER_MODE_DIALOG = 0;
    public static final int SPINNER_MODE_DROPDOWN = 1;

    public static final int VIEW_TYPE_LAYOUT_LINEAR = 0;
    public static final int VIEW_TYPE_LAYOUT_RELATIVE = 1;
    public static final int VIEW_TYPE_LAYOUT_HSCROLLVIEW = 2;
    public static final int VIEW_TYPE_WIDGET_BUTTON = 3;
    public static final int VIEW_TYPE_WIDGET_TEXTVIEW = 4;
    public static final int VIEW_TYPE_WIDGET_EDITTEXT = 5;
    public static final int VIEW_TYPE_WIDGET_IMAGEVIEW = 6;
    public static final int VIEW_TYPE_WIDGET_WEBVIEW = 7;
    public static final int VIEW_TYPE_WIDGET_PROGRESSBAR = 8;
    public static final int VIEW_TYPE_WIDGET_LISTVIEW = 9;
    public static final int VIEW_TYPE_WIDGET_SPINNER = 10;
    public static final int VIEW_TYPE_WIDGET_CHECKBOX = 11;
    public static final int VIEW_TYPE_LAYOUT_VSCROLLVIEW = 12;
    public static final int VIEW_TYPE_WIDGET_SWITCH = 13;
    public static final int VIEW_TYPE_WIDGET_SEEKBAR = 14;
    public static final int VIEW_TYPE_WIDGET_CALENDARVIEW = 15;
    public static final int VIEW_TYPE_WIDGET_FAB = 16;
    public static final int VIEW_TYPE_WIDGET_ADVIEW = 17;
    public static final int VIEW_TYPE_WIDGET_MAPVIEW = 18;
    public static final int VIEW_TYPE_COUNT = 19;

    @Expose
    public String adSize;
    @Expose
    public String adUnitId;
    @Expose
    public float alpha;
    @Expose
    public int checked;
    @Expose
    public int choiceMode;
    public Gx classInfo;
    @Expose
    public int clickable;
    @Expose
    public String convert;
    @Expose
    public String customView;
    @Expose
    public int dividerHeight;
    @Expose
    public int enabled;
    @Expose
    public int firstDayOfWeek;
    @Expose
    public String id;
    @Expose
    public ImageBean image;
    @Expose
    public String indeterminate;
    @Expose
    public int index;
    @Expose
    public String inject;
    @Expose
    public LayoutBean layout;
    @Expose
    public int max;
    public String name;
    @Expose
    public String parent;
    public Gx parentClassInfo;
    @Expose
    public int parentType;
    @Expose
    public String preId;
    @Expose
    public int preIndex;
    @Expose
    public String preParent;
    @Expose
    public int preParentType;
    @Expose
    public int progress;
    @Expose
    public String progressStyle;
    @Expose
    public float scaleX;
    @Expose
    public float scaleY;
    @Expose
    public int spinnerMode;
    @Expose
    public TextBean text;
    @Expose
    public float translationX;
    @Expose
    public float translationY;
    @Expose
    public int type;
    @Expose
    public HashMap<String, String> parentAttributes;
    public boolean isCustomWidget;

    public ViewBean() {
        parent = null;
        parentType = -1;
        enabled = 1;
        clickable = 1;
        spinnerMode = 1;
        dividerHeight = 1;
        choiceMode = 0;
        customView = "";
        checked = 0;
        alpha = 1.0f;
        translationX = 0;
        translationY = 0;
        scaleX = 1.0f;
        scaleY = 1.0f;
        max = 100;
        progress = 0;
        firstDayOfWeek = 1;
        adSize = "";
        adUnitId = "";
        layout = new LayoutBean();
        text = new TextBean();
        image = new ImageBean();
        indeterminate = "false";
        inject = "";
        convert = "";
        progressStyle = PROGRESSBAR_STYLE_CIRCLE;
        parentAttributes = new HashMap<>();
        isCustomWidget = false;
    }

    public ViewBean(Parcel parcel) {
        id = parcel.readString();
        name = parcel.readString();
        type = parcel.readInt();
        parent = parcel.readString();
        parentType = parcel.readInt();
        index = parcel.readInt();
        enabled = parcel.readInt();
        clickable = parcel.readInt();
        spinnerMode = parcel.readInt();
        dividerHeight = parcel.readInt();
        choiceMode = parcel.readInt();
        customView = parcel.readString();
        checked = parcel.readInt();
        alpha = parcel.readFloat();
        translationX = parcel.readFloat();
        translationY = parcel.readFloat();
        scaleX = parcel.readFloat();
        scaleY = parcel.readFloat();
        max = parcel.readInt();
        progress = parcel.readInt();
        firstDayOfWeek = parcel.readInt();
        adSize = parcel.readString();
        adUnitId = parcel.readString();
        preParent = parcel.readString();
        preParentType = parcel.readInt();
        preIndex = parcel.readInt();
        preId = parcel.readString();
        layout = parcel.readParcelable(LayoutBean.class.getClassLoader());
        text = parcel.readParcelable(TextBean.class.getClassLoader());
        image = parcel.readParcelable(ImageBean.class.getClassLoader());
        indeterminate = parcel.readString();
        inject = parcel.readString();
        convert = parcel.readString();
        progressStyle = parcel.readString();
        int size = parcel.readInt();
        parentAttributes = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            String key = parcel.readString();
            String value = parcel.readString();
            parentAttributes.put(key, value);
        }
        isCustomWidget = parcel.readInt() != 0;
    }

    public ViewBean(String id, int type) {
        this();
        this.id = id;
        name = id;
        this.type = type;
        parent = null;
    }

    public static Parcelable.Creator<ViewBean> getCreator() {
        return CREATOR;
    }

    public static int getViewTypeByTypeName(String typeName) {
        return switch (typeName) {
            case "RelativeLayout" -> VIEW_TYPE_LAYOUT_RELATIVE;
            case "Switch" -> VIEW_TYPE_WIDGET_SWITCH;
            case "MapView" -> VIEW_TYPE_WIDGET_MAPVIEW;
            case "ProgressBar" -> VIEW_TYPE_WIDGET_PROGRESSBAR;
            case "WebView" -> VIEW_TYPE_WIDGET_WEBVIEW;
            case "TextView" -> VIEW_TYPE_WIDGET_TEXTVIEW;
            case "SeekBar" -> VIEW_TYPE_WIDGET_SEEKBAR;
            case "Spinner" -> VIEW_TYPE_WIDGET_SPINNER;
            case "CalendarView" -> VIEW_TYPE_WIDGET_CALENDARVIEW;
            case "ImageView" -> VIEW_TYPE_WIDGET_IMAGEVIEW;
            case "LinearLayout" -> VIEW_TYPE_LAYOUT_LINEAR;
            case "ListView" -> VIEW_TYPE_WIDGET_LISTVIEW;
            case "HScrollView" -> VIEW_TYPE_LAYOUT_HSCROLLVIEW;
            case "CheckBox" -> VIEW_TYPE_WIDGET_CHECKBOX;
            case "EditText" -> VIEW_TYPE_WIDGET_EDITTEXT;
            case "AdView" -> VIEW_TYPE_WIDGET_ADVIEW;
            case "Button" -> VIEW_TYPE_WIDGET_BUTTON;
            case "ScrollView" -> VIEW_TYPE_LAYOUT_VSCROLLVIEW;
            default -> ViewBeans.getViewTypeByTypeName(typeName);
        };
    }

    public static String getViewTypeName(int type) {
        return switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR -> "LinearLayout";
            case VIEW_TYPE_LAYOUT_RELATIVE -> "RelativeLayout";
            case VIEW_TYPE_LAYOUT_HSCROLLVIEW -> "HScrollView";
            case VIEW_TYPE_WIDGET_BUTTON -> "Button";
            case VIEW_TYPE_WIDGET_TEXTVIEW -> "TextView";
            case VIEW_TYPE_WIDGET_EDITTEXT -> "EditText";
            case VIEW_TYPE_WIDGET_IMAGEVIEW -> "ImageView";
            case VIEW_TYPE_WIDGET_WEBVIEW -> "WebView";
            case VIEW_TYPE_WIDGET_PROGRESSBAR -> "ProgressBar";
            case VIEW_TYPE_WIDGET_LISTVIEW -> "ListView";
            case VIEW_TYPE_WIDGET_SPINNER -> "Spinner";
            case VIEW_TYPE_WIDGET_CHECKBOX -> "CheckBox";
            case VIEW_TYPE_LAYOUT_VSCROLLVIEW -> "ScrollView";
            case VIEW_TYPE_WIDGET_SWITCH -> "Switch";
            case VIEW_TYPE_WIDGET_SEEKBAR -> "SeekBar";
            case VIEW_TYPE_WIDGET_CALENDARVIEW -> "CalendarView";
            case VIEW_TYPE_WIDGET_ADVIEW -> "AdView";
            case VIEW_TYPE_WIDGET_MAPVIEW -> "MapView";
            default -> ViewBeans.getViewTypeName(type);
        };
    }

    public static int getViewTypeResId(int type) {
        return switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR -> R.drawable.ic_mtrl_view_horizontal;
            case VIEW_TYPE_LAYOUT_RELATIVE -> R.drawable.ic_mtrl_view_relative;
            case VIEW_TYPE_LAYOUT_HSCROLLVIEW -> R.drawable.ic_mtrl_swipe_horizontal;
            case VIEW_TYPE_WIDGET_BUTTON -> R.drawable.ic_mtrl_button_click;
            case VIEW_TYPE_WIDGET_TEXTVIEW -> R.drawable.ic_mtrl_formattext;
            case VIEW_TYPE_WIDGET_EDITTEXT -> R.drawable.ic_mtrl_edittext;
            case VIEW_TYPE_WIDGET_IMAGEVIEW -> R.drawable.ic_mtrl_image;
            case VIEW_TYPE_WIDGET_WEBVIEW -> R.drawable.ic_mtrl_web;
            case VIEW_TYPE_WIDGET_PROGRESSBAR -> R.drawable.ic_mtrl_progress_bar;
            case VIEW_TYPE_WIDGET_LISTVIEW -> R.drawable.ic_mtrl_list;
            case VIEW_TYPE_WIDGET_SPINNER -> R.drawable.ic_mtrl_spinner;
            case VIEW_TYPE_WIDGET_CHECKBOX -> R.drawable.ic_mtrl_checkbox;
            case VIEW_TYPE_LAYOUT_VSCROLLVIEW -> R.drawable.ic_mtrl_swap_vertical;
            case VIEW_TYPE_WIDGET_SWITCH -> R.drawable.ic_mtrl_toggle;
            case VIEW_TYPE_WIDGET_SEEKBAR -> R.drawable.ic_mtrl_seekbar;
            case VIEW_TYPE_WIDGET_CALENDARVIEW -> R.drawable.ic_mtrl_calendar;
            case VIEW_TYPE_WIDGET_FAB -> R.drawable.ic_mtrl_fab;
            case VIEW_TYPE_WIDGET_ADVIEW -> R.drawable.ic_mtrl_ad;
            case VIEW_TYPE_WIDGET_MAPVIEW -> R.drawable.ic_mtrl_map;
            default -> ViewBeans.getViewTypeResId(type);
        };
    }

    public Gx buildClassInfo(int type) {
        String name = switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR -> "LinearLayout";
            case VIEW_TYPE_LAYOUT_RELATIVE -> "RelativeLayout";
            case VIEW_TYPE_LAYOUT_HSCROLLVIEW -> "HorizontalScrollView";
            case VIEW_TYPE_WIDGET_BUTTON -> "Button";
            case VIEW_TYPE_WIDGET_TEXTVIEW -> "TextView";
            case VIEW_TYPE_WIDGET_EDITTEXT -> "EditText";
            case VIEW_TYPE_WIDGET_IMAGEVIEW -> "ImageView";
            case VIEW_TYPE_WIDGET_WEBVIEW -> "WebView";
            case VIEW_TYPE_WIDGET_PROGRESSBAR -> "ProgressBar";
            case VIEW_TYPE_WIDGET_LISTVIEW -> "ListView";
            case VIEW_TYPE_WIDGET_SPINNER -> "Spinner";
            case VIEW_TYPE_WIDGET_CHECKBOX -> "CheckBox";
            case VIEW_TYPE_LAYOUT_VSCROLLVIEW -> "ScrollView";
            case VIEW_TYPE_WIDGET_SWITCH -> "Switch";
            case VIEW_TYPE_WIDGET_SEEKBAR -> "SeekBar";
            case VIEW_TYPE_WIDGET_CALENDARVIEW -> "CalendarView";
            case VIEW_TYPE_WIDGET_FAB -> "FloatingActionButton";
            case VIEW_TYPE_WIDGET_ADVIEW -> "AdView";
            case VIEW_TYPE_WIDGET_MAPVIEW -> "MapView";
            default -> ViewBeans.buildClassInfo(type);
        };
        return new Gx(name);
    }

    public void clearClassInfo() {
        classInfo = null;
    }

    @Override
    @NonNull
    public ViewBean clone() {
        ViewBean viewBean = new ViewBean();
        viewBean.copy(this);
        return viewBean;
    }

    public void copy(ViewBean other) {
        other.print();
        id = other.id;
        name = other.name;
        type = other.type;
        parent = other.parent;
        parentType = other.parentType;
        index = other.index;
        enabled = other.enabled;
        clickable = other.clickable;
        spinnerMode = other.spinnerMode;
        dividerHeight = other.dividerHeight;
        choiceMode = other.choiceMode;
        customView = other.customView;
        checked = other.checked;
        alpha = other.alpha;
        translationX = other.translationX;
        translationY = other.translationY;
        scaleX = other.scaleX;
        scaleY = other.scaleY;
        max = other.max;
        progress = other.progress;
        firstDayOfWeek = other.firstDayOfWeek;
        adSize = other.adSize;
        adUnitId = other.adUnitId;
        preParent = other.preParent;
        preParentType = other.preParentType;
        preIndex = other.preIndex;
        preId = other.preId;
        layout.copy(other.layout);
        text.copy(other.text);
        image.copy(other.image);
        indeterminate = other.indeterminate;
        inject = other.inject;
        convert = other.convert;
        progressStyle = other.progressStyle;
        parentAttributes = other.parentAttributes;
        isCustomWidget = other.isCustomWidget;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Gx getClassInfo() {
        if (classInfo == null) {
            classInfo = buildClassInfo(type);
        }
        return classInfo;
    }

    public Gx getParentClassInfo() {
        if (parentType == -1) {
            return null;
        }
        if (parentClassInfo == null) {
            parentClassInfo = buildClassInfo(parentType);
        }
        return parentClassInfo;
    }

    public boolean isEqual(ViewBean viewBean) {
        if (type != viewBean.type || parentType != viewBean.parentType || index != viewBean.index ||
                enabled != viewBean.enabled || clickable != viewBean.clickable || alpha != viewBean.alpha ||
                translationX != viewBean.translationX || translationY != viewBean.translationY ||
                scaleX != viewBean.scaleX || scaleY != viewBean.scaleY || spinnerMode != viewBean.spinnerMode ||
                dividerHeight != viewBean.dividerHeight || choiceMode != viewBean.choiceMode ||
                checked != viewBean.checked || max != viewBean.max || progress != viewBean.progress ||
                firstDayOfWeek != viewBean.firstDayOfWeek || !adSize.equals(viewBean.adSize) ||
                !adUnitId.equals(viewBean.adUnitId) || !text.isEqual(viewBean.text) || !layout.isEqual(viewBean.layout) ||
                !image.isEqual(viewBean.image) || !indeterminate.equals(viewBean.indeterminate) ||
                !inject.equals(viewBean.inject) || !convert.equals(viewBean.convert) ||
                !progressStyle.equals(viewBean.progressStyle) || !parentAttributes.equals(viewBean.parentAttributes)) {
            return false;
        }

        String id = this.id;
        if (id != null) {
            if (!id.equals(viewBean.id)) {
                return false;
            }
        } else if (viewBean.id != null) {
            return false;
        }

        String parent = this.parent;
        if (parent != null) {
            if (!parent.equals(viewBean.parent)) {
                return false;
            }
        } else if (viewBean.parent != null) {
            return false;
        }

        String customView = this.customView;
        if (customView != null) {
            return customView.equals(viewBean.customView);
        } else return viewBean.customView == null;
    }

    public void print() {
        layout.print();
        text.print();
        image.print();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(type);
        dest.writeString(parent);
        dest.writeInt(parentType);
        dest.writeInt(index);
        dest.writeInt(enabled);
        dest.writeInt(clickable);
        dest.writeInt(spinnerMode);
        dest.writeInt(dividerHeight);
        dest.writeInt(choiceMode);
        dest.writeString(customView);
        dest.writeInt(checked);
        dest.writeFloat(alpha);
        dest.writeFloat(translationX);
        dest.writeFloat(translationY);
        dest.writeFloat(scaleX);
        dest.writeFloat(scaleY);
        dest.writeInt(max);
        dest.writeInt(progress);
        dest.writeInt(firstDayOfWeek);
        dest.writeString(adSize);
        dest.writeString(adUnitId);
        dest.writeString(preParent);
        dest.writeInt(preParentType);
        dest.writeInt(preIndex);
        dest.writeString(preId);
        dest.writeParcelable(layout, flags);
        dest.writeParcelable(text, flags);
        dest.writeParcelable(image, flags);
        dest.writeString(indeterminate);
        dest.writeString(inject);
        dest.writeString(convert);
        dest.writeString(progressStyle);
        dest.writeInt(parentAttributes.size());
        for (HashMap.Entry<String, String> entry : parentAttributes.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(isCustomWidget ? 1 : 0);
    }
}