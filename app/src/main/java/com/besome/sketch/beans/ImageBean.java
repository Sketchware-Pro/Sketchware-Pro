package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;

import a.a.a.nA;

public class ImageBean extends nA implements Parcelable {
    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<ImageBean>() {

        @Override // android.os.Parcelable.Creator
        public ImageBean createFromParcel(Parcel parcel) {
            return new ImageBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ImageBean[] newArray(int i) {
            return new ImageBean[i];
        }
    };
    public static final String SCALE_TYPE_CENTER = ImageView.ScaleType.CENTER.name();
    public static final String SCALE_TYPE_CENTER_CROP = ImageView.ScaleType.CENTER_CROP.name();
    public static final String SCALE_TYPE_CENTER_INSIDE = ImageView.ScaleType.CENTER_INSIDE.name();
    public static final String SCALE_TYPE_FIT_CENTER = ImageView.ScaleType.FIT_CENTER.name();
    public static final String SCALE_TYPE_FIT_END = ImageView.ScaleType.FIT_END.name();
    public static final String SCALE_TYPE_FIT_START = ImageView.ScaleType.FIT_START.name();
    public static final String SCALE_TYPE_FIT_XY = ImageView.ScaleType.FIT_XY.name();
    @Expose
    public String resName;
    @Expose
    public int rotate;
    @Expose
    public String scaleType;

    public ImageBean() {
        this.scaleType = ImageView.ScaleType.CENTER.name();
        this.rotate = 0;
    }

    public ImageBean(Parcel parcel) {
        this.resName = parcel.readString();
        this.scaleType = parcel.readString();
        this.rotate = parcel.readInt();
    }

    public static Parcelable.Creator<ImageBean> getCreator() {
        return CREATOR;
    }

    public void copy(ImageBean imageBean) {
        this.resName = imageBean.resName;
        this.scaleType = imageBean.scaleType;
        this.rotate = imageBean.rotate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isEqual(ImageBean imageBean) {
        String str = this.resName;
        if (str != null) {
            String str2 = imageBean.resName;
            if (str2 == null || !str.equals(str2)) {
                return false;
            }
        } else if (imageBean.resName != null) {
            return false;
        }
        String str3 = this.scaleType;
        if (str3 != null) {
            String str4 = imageBean.scaleType;
            if (str4 == null || !str3.equals(str4)) {
                return false;
            }
        } else if (imageBean.scaleType != null) {
            return false;
        }
        return this.rotate == imageBean.rotate;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.resName);
        parcel.writeString(this.scaleType);
        parcel.writeInt(this.rotate);
    }
}
