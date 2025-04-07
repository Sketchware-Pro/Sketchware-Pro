package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;

import a.a.a.nA;

public class ImageBean extends nA implements Parcelable {
    public static final Parcelable.Creator<ImageBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
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
        scaleType = ImageView.ScaleType.CENTER.name();
        rotate = 0;
    }

    public ImageBean(Parcel parcel) {
        resName = parcel.readString();
        scaleType = parcel.readString();
        rotate = parcel.readInt();
    }

    public static Parcelable.Creator<ImageBean> getCreator() {
        return CREATOR;
    }

    public void copy(ImageBean imageBean) {
        resName = imageBean.resName;
        scaleType = imageBean.scaleType;
        rotate = imageBean.rotate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isEqual(ImageBean imageBean) {
        String str = resName;
        if (str != null) {
            String str2 = imageBean.resName;
            if (!str.equals(str2)) {
                return false;
            }
        } else if (imageBean.resName != null) {
            return false;
        }
        String str3 = scaleType;
        if (str3 != null) {
            String str4 = imageBean.scaleType;
            if (!str3.equals(str4)) {
                return false;
            }
        } else if (imageBean.scaleType != null) {
            return false;
        }
        return rotate == imageBean.rotate;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(resName);
        parcel.writeString(scaleType);
        parcel.writeInt(rotate);
    }
}
