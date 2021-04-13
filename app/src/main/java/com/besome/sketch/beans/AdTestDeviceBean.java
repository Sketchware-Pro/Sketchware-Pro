package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class AdTestDeviceBean implements Parcelable {
    public static final Parcelable.Creator<AdTestDeviceBean> CREATOR = new Parcelable.Creator<AdTestDeviceBean>() {
        /* class com.besome.sketch.beans.AdTestDeviceBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public AdTestDeviceBean createFromParcel(Parcel parcel) {
            return new AdTestDeviceBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public AdTestDeviceBean[] newArray(int i) {
            return new AdTestDeviceBean[i];
        }
    };
    @Expose
    public String deviceId;

    public AdTestDeviceBean() {
        this("");
    }

    public static Parcelable.Creator<AdTestDeviceBean> getCreator() {
        return CREATOR;
    }

    public void copy(AdTestDeviceBean adTestDeviceBean) {
        this.deviceId = adTestDeviceBean.deviceId;
    }

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.deviceId);
    }

    public AdTestDeviceBean(String str) {
        this.deviceId = str;
    }

    @Override // java.lang.Object
    public AdTestDeviceBean clone() {
        AdTestDeviceBean adTestDeviceBean = new AdTestDeviceBean();
        adTestDeviceBean.copy(this);
        return adTestDeviceBean;
    }

    public AdTestDeviceBean(Parcel parcel) {
        this.deviceId = parcel.readString();
    }
}
