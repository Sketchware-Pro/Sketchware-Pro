package com.besome.sketch.beans;

import a.a.a.Gx;
import a.a.a.nA;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.flexbox.FlexItem;
import com.google.gson.annotations.Expose;
import mod.agus.jcoderz.beans.ViewBeans;

public class ViewBean extends nA implements Parcelable {
    public static final int CHOICE_MODE_MULTI = 2;
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    public static final Parcelable.Creator<ViewBean> CREATOR = new Parcelable.Creator<ViewBean>() {
        /* class com.besome.sketch.beans.ViewBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public ViewBean createFromParcel(Parcel parcel) {
            return new ViewBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ViewBean[] newArray(int i) {
            return new ViewBean[i];
        }
    };
    public static final int DEFAULT_MAX = 100;
    public static final int DEFAULT_PROGRESS = 0;
    public static final String PROGRESSBAR_STYLE_CIRCLE = "?android:progressBarStyle";
    public static final String PROGRESSBAR_STYLE_HORIZONTAL = "?android:progressBarStyleHorizontal";
    public static final int SPINNER_MODE_DIALOG = 0;
    public static final int SPINNER_MODE_DROPDOWN = 1;
    public static final int VIEW_TYPE_COUNT = 99;
    public static final int VIEW_TYPE_LAYOUT_HSCROLLVIEW = 2;
    public static final int VIEW_TYPE_LAYOUT_LINEAR = 0;
    public static final int VIEW_TYPE_LAYOUT_RELATIVE = 1;
    public static final int VIEW_TYPE_LAYOUT_VSCROLLVIEW = 12;
    public static final int VIEW_TYPE_WIDGET_ADVIEW = 17;
    public static final int VIEW_TYPE_WIDGET_BUTTON = 3;
    public static final int VIEW_TYPE_WIDGET_CALENDARVIEW = 15;
    public static final int VIEW_TYPE_WIDGET_CHECKBOX = 11;
    public static final int VIEW_TYPE_WIDGET_EDITTEXT = 5;
    public static final int VIEW_TYPE_WIDGET_FAB = 16;
    public static final int VIEW_TYPE_WIDGET_IMAGEVIEW = 6;
    public static final int VIEW_TYPE_WIDGET_LISTVIEW = 9;
    public static final int VIEW_TYPE_WIDGET_MAPVIEW = 18;
    public static final int VIEW_TYPE_WIDGET_PROGRESSBAR = 8;
    public static final int VIEW_TYPE_WIDGET_RADIOBUTTON = 19;
    public static final int VIEW_TYPE_WIDGET_SEEKBAR = 14;
    public static final int VIEW_TYPE_WIDGET_SPINNER = 10;
    public static final int VIEW_TYPE_WIDGET_SWITCH = 13;
    public static final int VIEW_TYPE_WIDGET_TEXTVIEW = 4;
    public static final int VIEW_TYPE_WIDGET_WEBVIEW = 7;
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
        this.parent = null;
        this.parentType = -1;
        this.enabled = 1;
        this.clickable = 1;
        this.spinnerMode = 1;
        this.dividerHeight = 1;
        this.choiceMode = 0;
        this.customView = "";
        this.checked = 0;
        this.alpha = 1.0f;
        this.translationX = FlexItem.FLEX_GROW_DEFAULT;
        this.translationY = FlexItem.FLEX_GROW_DEFAULT;
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.max = 100;
        this.progress = 0;
        this.firstDayOfWeek = 1;
        this.adSize = "";
        this.adUnitId = "";
        this.layout = new LayoutBean();
        this.text = new TextBean();
        this.image = new ImageBean();
        this.indeterminate = "false";
        this.inject = "";
        this.convert = "";
        this.progressStyle = PROGRESSBAR_STYLE_CIRCLE;
    }

    public ViewBean(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.type = parcel.readInt();
        this.parent = parcel.readString();
        this.parentType = parcel.readInt();
        this.index = parcel.readInt();
        this.enabled = parcel.readInt();
        this.clickable = parcel.readInt();
        this.spinnerMode = parcel.readInt();
        this.dividerHeight = parcel.readInt();
        this.choiceMode = parcel.readInt();
        this.customView = parcel.readString();
        this.checked = parcel.readInt();
        this.alpha = parcel.readFloat();
        this.translationX = parcel.readFloat();
        this.translationY = parcel.readFloat();
        this.scaleX = parcel.readFloat();
        this.scaleY = parcel.readFloat();
        this.max = parcel.readInt();
        this.progress = parcel.readInt();
        this.firstDayOfWeek = parcel.readInt();
        this.adSize = parcel.readString();
        this.adUnitId = parcel.readString();
        this.preParent = parcel.readString();
        this.preParentType = parcel.readInt();
        this.preIndex = parcel.readInt();
        this.preId = parcel.readString();
        this.layout = (LayoutBean) parcel.readParcelable(LayoutBean.class.getClassLoader());
        this.text = (TextBean) parcel.readParcelable(TextBean.class.getClassLoader());
        this.image = (ImageBean) parcel.readParcelable(ImageBean.class.getClassLoader());
        this.indeterminate = parcel.readString();
        this.inject = parcel.readString();
        this.convert = parcel.readString();
        this.progressStyle = parcel.readString();
    }

    public ViewBean(String str, int i) {
        this();
        this.id = str;
        this.name = str;
        this.type = i;
        this.parent = null;
    }

    public static Parcelable.Creator getCreator() {
        return CREATOR;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getViewTypeByTypeName(String str) {
        char c;
        int viewTypeByTypeName = ViewBeans.getViewTypeByTypeName(str);
        switch (str.hashCode()) {
            case -1805606060:
                if (str.equals("Switch")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1793532415:
                if (str.equals("MapView")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1495589242:
                if (str.equals("ProgressBar")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1406842887:
                if (str.equals("WebView")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -938935918:
                if (str.equals("TextView")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -658531749:
                if (str.equals("SeekBar")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -339785223:
                if (str.equals("Spinner")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -188272861:
                if (str.equals("CalendarView")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1125864064:
                if (str.equals("ImageView")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1127291599:
                if (str.equals("LinearLayout")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1410352259:
                if (str.equals("ListView")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1528334714:
                if (str.equals("HScrollView")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 1601505219:
                if (str.equals("CheckBox")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1666676343:
                if (str.equals("EditText")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1955913096:
                if (str.equals("AdView")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 2001146706:
                if (str.equals("Button")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2059813682:
                if (str.equals("ScrollView")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 3;
            case 1:
                return 4;
            case 2:
                return 11;
            case 3:
                return 5;
            case 4:
                return 6;
            case 5:
                return 9;
            case 6:
                return 8;
            case 7:
                return 14;
            case '\b':
                return 10;
            case '\t':
                return 13;
            case '\n':
                return 7;
            case 11:
                return 0;
            case '\f':
                return 12;
            case '\r':
                return 2;
            case 14:
                return 15;
            case 15:
                return 17;
            case 16:
                return 18;
            case 17:
                return 19;
            default:
                return viewTypeByTypeName;
        }
    }

    public static String getViewTypeName(int i) {
        switch (i) {
            case 0:
                return "LinearLayout";
            case 1:
            case 16:
            default:
                return ViewBeans.getViewTypeName(i);
            case 2:
                return "HScrollView";
            case 3:
                return "Button";
            case 4:
                return "TextView";
            case 5:
                return "EditText";
            case 6:
                return "ImageView";
            case 7:
                return "WebView";
            case 8:
                return "ProgressBar";
            case 9:
                return "ListView";
            case 10:
                return "Spinner";
            case 11:
                return "CheckBox";
            case 12:
                return "ScrollView";
            case 13:
                return "Switch";
            case 14:
                return "SeekBar";
            case 15:
                return "CalendarView";
            case 17:
                return "AdView";
            case 18:
                return "MapView";
        }
    }

    public static int getViewTypeResId(int i) {
        switch (i) {
            case 0:
                return 2131166255;
            case 1:
                return 2131166265;
            case 2:
                return 2131166249;
            case 3:
                return 2131166237;
            case 4:
                return 2131166275;
            case 5:
                return 2131166242;
            case 6:
                return 2131166253;
            case 7:
                return 2131166278;
            case 8:
                return 2131166263;
            case 9:
                return 2131166257;
            case 10:
                return 2131166272;
            case 11:
                return 2131166241;
            case 12:
                return 2131166266;
            case 13:
                return 2131166273;
            case 14:
                return 2131166267;
            case 15:
                return 2131166239;
            case 16:
                return 2131166243;
            case 17:
                return 2131166234;
            case 18:
                return 2131166247;
            default:
                return ViewBeans.getViewTypeResId(i);
        }
    }

    public Gx buildClassInfo(int i) {
        String str;
        if (i != 0) {
            switch (i) {
                case 2:
                    str = "HorizontalScrollView";
                    break;
                case 3:
                    str = "Button";
                    break;
                case 4:
                    str = "TextView";
                    break;
                case 5:
                    str = "EditText";
                    break;
                case 6:
                    str = "ImageView";
                    break;
                case 7:
                    str = "WebView";
                    break;
                case 8:
                    str = "ProgressBar";
                    break;
                case 9:
                    str = "ListView";
                    break;
                case 10:
                    str = "Spinner";
                    break;
                case 11:
                    str = "CheckBox";
                    break;
                case 12:
                    str = "ScrollView";
                    break;
                case 13:
                    str = "Switch";
                    break;
                case 14:
                    str = "SeekBar";
                    break;
                case 15:
                    str = "CalendarView";
                    break;
                case 16:
                    str = "FloatingActionButton";
                    break;
                case 17:
                    str = "AdView";
                    break;
                case 18:
                    str = "MapView";
                    break;
                default:
                    str = ViewBeans.buildClassInfo(i);
                    break;
            }
        } else {
            str = "LinearLayout";
        }
        return new Gx(str);
    }

    public void clearClassInfo() {
        this.classInfo = null;
    }

    @Override // java.lang.Object
    public ViewBean clone() {
        ViewBean viewBean = new ViewBean();
        viewBean.copy(this);
        return viewBean;
    }

    public void copy(ViewBean viewBean) {
        viewBean.print();
        this.id = viewBean.id;
        this.name = viewBean.name;
        this.type = viewBean.type;
        this.parent = viewBean.parent;
        this.parentType = viewBean.parentType;
        this.index = viewBean.index;
        this.enabled = viewBean.enabled;
        this.clickable = viewBean.clickable;
        this.spinnerMode = viewBean.spinnerMode;
        this.dividerHeight = viewBean.dividerHeight;
        this.choiceMode = viewBean.choiceMode;
        this.customView = viewBean.customView;
        this.checked = viewBean.checked;
        this.alpha = viewBean.alpha;
        this.translationX = viewBean.translationX;
        this.translationY = viewBean.translationY;
        this.scaleX = viewBean.scaleX;
        this.scaleY = viewBean.scaleY;
        this.max = viewBean.max;
        this.progress = viewBean.progress;
        this.firstDayOfWeek = viewBean.firstDayOfWeek;
        this.adSize = viewBean.adSize;
        this.adUnitId = viewBean.adUnitId;
        this.preParent = viewBean.preParent;
        this.preParentType = viewBean.preParentType;
        this.preIndex = viewBean.preIndex;
        this.preId = viewBean.preId;
        this.layout.copy(viewBean.layout);
        this.text.copy(viewBean.text);
        this.image.copy(viewBean.image);
        this.indeterminate = viewBean.indeterminate;
        this.inject = viewBean.inject;
        this.convert = viewBean.convert;
        this.progressStyle = viewBean.progressStyle;
    }

    public int describeContents() {
        return 0;
    }

    public Gx getClassInfo() {
        if (this.classInfo == null) {
            this.classInfo = buildClassInfo(this.type);
        }
        return this.classInfo;
    }

    public Gx getParentClassInfo() {
        int i = this.parentType;
        if (i == -1) {
            return null;
        }
        if (this.parentClassInfo == null) {
            this.parentClassInfo = buildClassInfo(i);
        }
        return this.parentClassInfo;
    }

    public boolean isEqual(ViewBean viewBean) {
        if (this.type != viewBean.type || this.parentType != viewBean.parentType || this.index != viewBean.index || this.enabled != viewBean.enabled || this.clickable != viewBean.clickable || this.alpha != viewBean.alpha || this.translationX != viewBean.translationX || this.translationY != viewBean.translationY || this.scaleX != viewBean.scaleX || this.scaleY != viewBean.scaleY || this.spinnerMode != viewBean.spinnerMode || this.dividerHeight != viewBean.dividerHeight || this.choiceMode != viewBean.choiceMode || this.checked != viewBean.checked || this.max != viewBean.max || this.progress != viewBean.progress || this.firstDayOfWeek != viewBean.firstDayOfWeek || !this.adSize.equals(viewBean.adSize) || !this.adUnitId.equals(viewBean.adUnitId) || !this.text.isEqual(viewBean.text) || !this.layout.isEqual(viewBean.layout) || !this.image.isEqual(viewBean.image) || !this.indeterminate.equals(viewBean.indeterminate) || !this.inject.equals(viewBean.inject) || !this.convert.equals(viewBean.convert) || !this.progressStyle.equals(viewBean.progressStyle)) {
            return false;
        }
        String str = this.id;
        if (str != null) {
            String str2 = viewBean.id;
            if (str2 == null || !str.equals(str2)) {
                return false;
            }
        } else if (viewBean.id != null) {
            return false;
        }
        String str3 = this.parent;
        if (str3 != null) {
            String str4 = viewBean.parent;
            if (str4 == null || !str3.equals(str4)) {
                return false;
            }
        } else if (viewBean.parent != null) {
            return false;
        }
        String str5 = this.customView;
        if (str5 != null) {
            String str6 = viewBean.customView;
            if (str6 == null || !str5.equals(str6)) {
                return false;
            }
        } else if (viewBean.customView != null) {
            return false;
        }
        return true;
    }

    public void print() {
        this.layout.print();
        this.text.print();
        this.image.print();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.type);
        parcel.writeString(this.parent);
        parcel.writeInt(this.parentType);
        parcel.writeInt(this.index);
        parcel.writeInt(this.enabled);
        parcel.writeInt(this.clickable);
        parcel.writeInt(this.spinnerMode);
        parcel.writeInt(this.dividerHeight);
        parcel.writeInt(this.choiceMode);
        parcel.writeString(this.customView);
        parcel.writeInt(this.checked);
        parcel.writeFloat(this.alpha);
        parcel.writeFloat(this.translationX);
        parcel.writeFloat(this.translationY);
        parcel.writeFloat(this.scaleX);
        parcel.writeFloat(this.scaleY);
        parcel.writeInt(this.max);
        parcel.writeInt(this.progress);
        parcel.writeInt(this.firstDayOfWeek);
        parcel.writeString(this.adSize);
        parcel.writeString(this.adUnitId);
        parcel.writeString(this.preParent);
        parcel.writeInt(this.preParentType);
        parcel.writeInt(this.preIndex);
        parcel.writeString(this.preId);
        parcel.writeParcelable(this.layout, i);
        parcel.writeParcelable(this.text, i);
        parcel.writeParcelable(this.image, i);
        parcel.writeString(this.indeterminate);
        parcel.writeString(this.inject);
        parcel.writeString(this.convert);
        parcel.writeString(this.progressStyle);
    }
}
