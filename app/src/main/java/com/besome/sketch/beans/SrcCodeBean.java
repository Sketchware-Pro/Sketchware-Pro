package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class SrcCodeBean implements Parcelable {
    public static final Parcelable.Creator<SrcCodeBean> CREATOR = new Parcelable.Creator<SrcCodeBean>() {
        /* class com.besome.sketch.beans.SrcCodeBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public SrcCodeBean createFromParcel(Parcel parcel) {
            return new SrcCodeBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SrcCodeBean[] newArray(int i) {
            return new SrcCodeBean[i];
        }
    };
    public String pkgName;
    public String source;
    public String srcFileName;

    public SrcCodeBean() {
    }

    public SrcCodeBean(String str, String str2) {
        this.srcFileName = str;
        this.source = str2;
    }

    public SrcCodeBean(Parcel parcel) {
        this.pkgName = parcel.readString();
        this.srcFileName = parcel.readString();
        this.source = parcel.readString();
    }

    public static Parcelable.Creator<SrcCodeBean> getCreator() {
        return CREATOR;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pkgName);
        parcel.writeString(this.srcFileName);
        parcel.writeString(this.source);
    }
}
