package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import a.a.a.nA;

public class LayoutBean extends nA implements Parcelable {
    public static final Parcelable.Creator<LayoutBean> CREATOR = new Parcelable.Creator<LayoutBean>() {

        @Override
        public LayoutBean createFromParcel(Parcel parcel) {
            return new LayoutBean(parcel);
        }

        @Override
        public LayoutBean[] newArray(int i) {
            return new LayoutBean[i];
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
        this.width = LAYOUT_WRAP_CONTENT;
        this.height = LAYOUT_WRAP_CONTENT;
        this.gravity = GRAVITY_NONE;
        this.layoutGravity = GRAVITY_NONE;
        this.orientation = ORIENTATION_NONE;
        this.backgroundColor = 0xffffff;
        this.borderColor = 0xff008dcd;
    }

    public LayoutBean(Parcel parcel) {
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.orientation = parcel.readInt();
        this.gravity = parcel.readInt();
        this.paddingLeft = parcel.readInt();
        this.paddingTop = parcel.readInt();
        this.paddingRight = parcel.readInt();
        this.paddingBottom = parcel.readInt();
        this.marginLeft = parcel.readInt();
        this.marginTop = parcel.readInt();
        this.marginRight = parcel.readInt();
        this.marginBottom = parcel.readInt();
        this.weight = parcel.readInt();
        this.weightSum = parcel.readInt();
        this.layoutGravity = parcel.readInt();
        this.backgroundColor = parcel.readInt();
        this.borderColor = parcel.readInt();
        this.backgroundResource = parcel.readString();
    }

    public static Parcelable.Creator getCreator() {
        return CREATOR;
    }

    public void copy(LayoutBean layoutBean) {
        this.width = layoutBean.width;
        this.height = layoutBean.height;
        this.orientation = layoutBean.orientation;
        this.gravity = layoutBean.gravity;
        this.paddingLeft = layoutBean.paddingLeft;
        this.paddingTop = layoutBean.paddingTop;
        this.paddingRight = layoutBean.paddingRight;
        this.paddingBottom = layoutBean.paddingBottom;
        this.marginLeft = layoutBean.marginLeft;
        this.marginTop = layoutBean.marginTop;
        this.marginRight = layoutBean.marginRight;
        this.marginBottom = layoutBean.marginBottom;
        this.weight = layoutBean.weight;
        this.weightSum = layoutBean.weightSum;
        this.layoutGravity = layoutBean.layoutGravity;
        this.backgroundColor = layoutBean.backgroundColor;
        this.borderColor = layoutBean.borderColor;
        this.backgroundResource = layoutBean.backgroundResource;
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
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.orientation);
        parcel.writeInt(this.gravity);
        parcel.writeInt(this.paddingLeft);
        parcel.writeInt(this.paddingTop);
        parcel.writeInt(this.paddingRight);
        parcel.writeInt(this.paddingBottom);
        parcel.writeInt(this.marginLeft);
        parcel.writeInt(this.marginTop);
        parcel.writeInt(this.marginRight);
        parcel.writeInt(this.marginBottom);
        parcel.writeInt(this.weight);
        parcel.writeInt(this.weightSum);
        parcel.writeInt(this.layoutGravity);
        parcel.writeInt(this.backgroundColor);
        parcel.writeInt(this.borderColor);
        parcel.writeString(this.backgroundResource);
    }
}
