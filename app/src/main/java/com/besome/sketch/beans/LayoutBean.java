package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import a.a.a.nA;

public class LayoutBean extends nA implements Parcelable {
    public static final Parcelable.Creator<LayoutBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public LayoutBean createFromParcel(Parcel source) {
            return new LayoutBean(source);
        }

        @Override
        public LayoutBean[] newArray(int size) {
            return new LayoutBean[size];
        }
    };

    public static final int GRAVITY_BOTTOM = 80;
    public static final int GRAVITY_CENTER = 17;
    public static final int GRAVITY_CENTER_HORIZONTAL = 1;
    public static final int GRAVITY_CENTER_VERTICAL = 16;
    public static final int GRAVITY_LEFT = 3;
    public static final int GRAVITY_NONE = 0;
    public static final int GRAVITY_RIGHT = 5;
    public static final int GRAVITY_TOP = 48;
    public static final int LAYOUT_MATCH_PARENT = -1;
    public static final int LAYOUT_NOTUSED = 0;
    public static final int LAYOUT_WRAP_CONTENT = -2;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_NONE = -1;
    public static final int ORIENTATION_VERTICAL = 1;
    public static final int VALUE_FALSE = 0;
    public static final int VALUE_TRUE = -1;
    @Expose
    public int backgroundColor;
    @Expose
    public String backgroundResource;
    @Expose
    public int borderColor;
    @Expose
    public int gravity;
    @Expose
    public int height;
    @Expose
    public int layoutGravity;
    @Expose
    public int marginBottom;
    @Expose
    public int marginLeft;
    @Expose
    public int marginRight;
    @Expose
    public int marginTop;
    @Expose
    public int orientation;
    @Expose
    public int paddingBottom;
    @Expose
    public int paddingLeft;
    @Expose
    public int paddingRight;
    @Expose
    public int paddingTop;
    @Expose
    public int weight;
    @Expose
    public int weightSum;
    @Expose
    public int width;

    public LayoutBean() {
        width = LAYOUT_WRAP_CONTENT;
        height = LAYOUT_WRAP_CONTENT;
        gravity = GRAVITY_NONE;
        layoutGravity = GRAVITY_NONE;
        orientation = ORIENTATION_NONE;
        backgroundColor = 0xffffff;
        borderColor = 0xff008dcd;
    }

    public LayoutBean(Parcel parcel) {
        width = parcel.readInt();
        height = parcel.readInt();
        orientation = parcel.readInt();
        gravity = parcel.readInt();
        paddingLeft = parcel.readInt();
        paddingTop = parcel.readInt();
        paddingRight = parcel.readInt();
        paddingBottom = parcel.readInt();
        marginLeft = parcel.readInt();
        marginTop = parcel.readInt();
        marginRight = parcel.readInt();
        marginBottom = parcel.readInt();
        weight = parcel.readInt();
        weightSum = parcel.readInt();
        layoutGravity = parcel.readInt();
        backgroundColor = parcel.readInt();
        borderColor = parcel.readInt();
        backgroundResource = parcel.readString();
    }

    public static Parcelable.Creator<LayoutBean> getCreator() {
        return CREATOR;
    }

    public void copy(LayoutBean layoutBean) {
        width = layoutBean.width;
        height = layoutBean.height;
        orientation = layoutBean.orientation;
        gravity = layoutBean.gravity;
        paddingLeft = layoutBean.paddingLeft;
        paddingTop = layoutBean.paddingTop;
        paddingRight = layoutBean.paddingRight;
        paddingBottom = layoutBean.paddingBottom;
        marginLeft = layoutBean.marginLeft;
        marginTop = layoutBean.marginTop;
        marginRight = layoutBean.marginRight;
        marginBottom = layoutBean.marginBottom;
        weight = layoutBean.weight;
        weightSum = layoutBean.weightSum;
        layoutGravity = layoutBean.layoutGravity;
        backgroundColor = layoutBean.backgroundColor;
        borderColor = layoutBean.borderColor;
        backgroundResource = layoutBean.backgroundResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isEqual(LayoutBean layoutBean) {
        if (width != layoutBean.width || height != layoutBean.height || orientation != layoutBean.orientation || gravity != layoutBean.gravity || paddingLeft != layoutBean.paddingLeft || paddingTop != layoutBean.paddingTop || paddingRight != layoutBean.paddingRight || paddingBottom != layoutBean.paddingBottom || marginLeft != layoutBean.marginLeft || marginTop != layoutBean.marginTop || marginRight != layoutBean.marginRight || marginBottom != layoutBean.marginBottom || weight != layoutBean.weight || weightSum != layoutBean.weightSum || layoutGravity != layoutBean.layoutGravity || backgroundColor != layoutBean.backgroundColor || borderColor != layoutBean.borderColor) {
            return false;
        }
        if (backgroundResource != null) {
            return backgroundResource.equals(layoutBean.backgroundResource);
        } else {
            return layoutBean.backgroundResource == null;
        }
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeInt(orientation);
        parcel.writeInt(gravity);
        parcel.writeInt(paddingLeft);
        parcel.writeInt(paddingTop);
        parcel.writeInt(paddingRight);
        parcel.writeInt(paddingBottom);
        parcel.writeInt(marginLeft);
        parcel.writeInt(marginTop);
        parcel.writeInt(marginRight);
        parcel.writeInt(marginBottom);
        parcel.writeInt(weight);
        parcel.writeInt(weightSum);
        parcel.writeInt(layoutGravity);
        parcel.writeInt(backgroundColor);
        parcel.writeInt(borderColor);
        parcel.writeString(backgroundResource);
    }
}
