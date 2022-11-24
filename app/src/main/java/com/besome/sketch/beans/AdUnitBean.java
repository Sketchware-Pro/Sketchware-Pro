package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class AdUnitBean implements Parcelable {
    public static final Parcelable.Creator<AdUnitBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public AdUnitBean createFromParcel(Parcel source) {
            return new AdUnitBean(source);
        }

        @Override
        public AdUnitBean[] newArray(int size) {
            return new AdUnitBean[size];
        }
    };

    @Expose
    public String id;
    @Expose
    public String name;

    public AdUnitBean() {
        this("", "");
    }

    public AdUnitBean(String str, String str2) {
        id = str;
        name = str2;
    }

    public AdUnitBean(Parcel parcel) {
        id = parcel.readString();
        name = parcel.readString();
    }

    public static Parcelable.Creator<AdUnitBean> getCreator() {
        return CREATOR;
    }

    public void copy(AdUnitBean adUnitBean) {
        id = adUnitBean.id;
        name = adUnitBean.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }

    @Override
    public AdUnitBean clone() {
        AdUnitBean adUnitBean = new AdUnitBean();
        adUnitBean.copy(this);
        return adUnitBean;
    }
}
