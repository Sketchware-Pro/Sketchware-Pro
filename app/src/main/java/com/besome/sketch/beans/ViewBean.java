package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.sketchware.remod.R;

import a.a.a.Gx;
import a.a.a.nA;
import mod.agus.jcoderz.beans.ViewBeans;

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
        switch (typeName) {
            case "Switch":
                return VIEW_TYPE_WIDGET_SWITCH;

            case "MapView":
                return VIEW_TYPE_WIDGET_MAPVIEW;

            case "ProgressBar":
                return VIEW_TYPE_WIDGET_PROGRESSBAR;

            case "WebView":
                return VIEW_TYPE_WIDGET_WEBVIEW;

            case "TextView":
                return VIEW_TYPE_WIDGET_TEXTVIEW;

            case "SeekBar":
                return VIEW_TYPE_WIDGET_SEEKBAR;

            case "Spinner":
                return VIEW_TYPE_WIDGET_SPINNER;

            case "CalendarView":
                return VIEW_TYPE_WIDGET_CALENDARVIEW;

            case "ImageView":
                return VIEW_TYPE_WIDGET_IMAGEVIEW;

            case "LinearLayout":
                return VIEW_TYPE_LAYOUT_LINEAR;

            case "ListView":
                return VIEW_TYPE_WIDGET_LISTVIEW;

            case "HScrollView":
                return VIEW_TYPE_LAYOUT_HSCROLLVIEW;

            case "CheckBox":
                return VIEW_TYPE_WIDGET_CHECKBOX;

            case "EditText":
                return VIEW_TYPE_WIDGET_EDITTEXT;

            case "AdView":
                return VIEW_TYPE_WIDGET_ADVIEW;

            case "Button":
                return VIEW_TYPE_WIDGET_BUTTON;

            case "ScrollView":
                return VIEW_TYPE_LAYOUT_VSCROLLVIEW;

            default:
                return ViewBeans.getViewTypeByTypeName(typeName);
        }
    }

    public static String getViewTypeName(int type) {
        switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR:
                return "LinearLayout";

            case VIEW_TYPE_LAYOUT_HSCROLLVIEW:
                return "HScrollView";

            case VIEW_TYPE_WIDGET_BUTTON:
                return "Button";

            case VIEW_TYPE_WIDGET_TEXTVIEW:
                return "TextView";

            case VIEW_TYPE_WIDGET_EDITTEXT:
                return "EditText";

            case VIEW_TYPE_WIDGET_IMAGEVIEW:
                return "ImageView";

            case VIEW_TYPE_WIDGET_WEBVIEW:
                return "WebView";

            case VIEW_TYPE_WIDGET_PROGRESSBAR:
                return "ProgressBar";

            case VIEW_TYPE_WIDGET_LISTVIEW:
                return "ListView";

            case VIEW_TYPE_WIDGET_SPINNER:
                return "Spinner";

            case VIEW_TYPE_WIDGET_CHECKBOX:
                return "CheckBox";

            case VIEW_TYPE_LAYOUT_VSCROLLVIEW:
                return "ScrollView";

            case VIEW_TYPE_WIDGET_SWITCH:
                return "Switch";

            case VIEW_TYPE_WIDGET_SEEKBAR:
                return "SeekBar";

            case VIEW_TYPE_WIDGET_CALENDARVIEW:
                return "CalendarView";

            case VIEW_TYPE_WIDGET_ADVIEW:
                return "AdView";

            case VIEW_TYPE_WIDGET_MAPVIEW:
                return "MapView";

            default:
                return ViewBeans.getViewTypeName(type);
        }
    }

    public static int getViewTypeResId(int type) {
        switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR:
                return R.drawable.widget_linear_horizontal;

            case VIEW_TYPE_LAYOUT_RELATIVE:
                return R.drawable.widget_relative_layout;

            case VIEW_TYPE_LAYOUT_HSCROLLVIEW:
                return R.drawable.widget_horizon_scrollview;

            case VIEW_TYPE_WIDGET_BUTTON:
                return R.drawable.widget_button;

            case VIEW_TYPE_WIDGET_TEXTVIEW:
                return R.drawable.widget_text_view;

            case VIEW_TYPE_WIDGET_EDITTEXT:
                return R.drawable.widget_edit_text;

            case VIEW_TYPE_WIDGET_IMAGEVIEW:
                return R.drawable.widget_image_view;

            case VIEW_TYPE_WIDGET_WEBVIEW:
                return R.drawable.widget_web_view;

            case VIEW_TYPE_WIDGET_PROGRESSBAR:
                return R.drawable.widget_progress_bar;

            case VIEW_TYPE_WIDGET_LISTVIEW:
                return R.drawable.widget_list_view;

            case VIEW_TYPE_WIDGET_SPINNER:
                return R.drawable.widget_spinner;

            case VIEW_TYPE_WIDGET_CHECKBOX:
                return R.drawable.widget_check_box;

            case VIEW_TYPE_LAYOUT_VSCROLLVIEW:
                return R.drawable.widget_scrollview;

            case VIEW_TYPE_WIDGET_SWITCH:
                return R.drawable.widget_switch;

            case VIEW_TYPE_WIDGET_SEEKBAR:
                return R.drawable.widget_seek_bar;

            case VIEW_TYPE_WIDGET_CALENDARVIEW:
                return R.drawable.widget_calendarview;

            case VIEW_TYPE_WIDGET_FAB:
                return R.drawable.widget_fab;

            case VIEW_TYPE_WIDGET_ADVIEW:
                return R.drawable.widget_admob;

            case VIEW_TYPE_WIDGET_MAPVIEW:
                return R.drawable.widget_google_map;

            default:
                return ViewBeans.getViewTypeResId(type);
        }
    }

    public Gx buildClassInfo(int type) {
        String name;
        switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR:
                name = "LinearLayout";
                break;

            // RIP RelativeLayout
         /* case VIEW_TYPE_LAYOUT_RELATIVE:
                name = "RelativeLayout";
                break;
         */

            case VIEW_TYPE_LAYOUT_HSCROLLVIEW:
                name = "HorizontalScrollView";
                break;

            case VIEW_TYPE_WIDGET_BUTTON:
                name = "Button";
                break;

            case VIEW_TYPE_WIDGET_TEXTVIEW:
                name = "TextView";
                break;

            case VIEW_TYPE_WIDGET_EDITTEXT:
                name = "EditText";
                break;

            case VIEW_TYPE_WIDGET_IMAGEVIEW:
                name = "ImageView";
                break;

            case VIEW_TYPE_WIDGET_WEBVIEW:
                name = "WebView";
                break;

            case VIEW_TYPE_WIDGET_PROGRESSBAR:
                name = "ProgressBar";
                break;

            case VIEW_TYPE_WIDGET_LISTVIEW:
                name = "ListView";
                break;

            case VIEW_TYPE_WIDGET_SPINNER:
                name = "Spinner";
                break;

            case VIEW_TYPE_WIDGET_CHECKBOX:
                name = "CheckBox";
                break;

            case VIEW_TYPE_LAYOUT_VSCROLLVIEW:
                name = "ScrollView";
                break;

            case VIEW_TYPE_WIDGET_SWITCH:
                name = "Switch";
                break;

            case VIEW_TYPE_WIDGET_SEEKBAR:
                name = "SeekBar";
                break;

            case VIEW_TYPE_WIDGET_CALENDARVIEW:
                name = "CalendarView";
                break;

            case VIEW_TYPE_WIDGET_FAB:
                name = "FloatingActionButton";
                break;

            case VIEW_TYPE_WIDGET_ADVIEW:
                name = "AdView";
                break;

            case VIEW_TYPE_WIDGET_MAPVIEW:
                name = "MapView";
                break;

            default:
                name = ViewBeans.buildClassInfo(type);
        }
        return new Gx(name);
    }

    public void clearClassInfo() {
        classInfo = null;
    }

    @Override
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
                !progressStyle.equals(viewBean.progressStyle)) {
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
    }
}
