package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class SrcCodeBean implements Parcelable {
    public static final Parcelable.Creator<SrcCodeBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public SrcCodeBean createFromParcel(Parcel source) {
            return new SrcCodeBean(source);
        }

        @Override
        public SrcCodeBean[] newArray(int size) {
            return new SrcCodeBean[size];
        }
    };

    public String pkgName;
    public String source;
    public String srcFileName;

    public SrcCodeBean() {
    }

    public SrcCodeBean(String sourceFilename, String content) {
        srcFileName = sourceFilename;
        source = content;
    }

    public SrcCodeBean(Parcel other) {
        pkgName = other.readString();
        srcFileName = other.readString();
        source = other.readString();
    }

    public static Parcelable.Creator<SrcCodeBean> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pkgName);
        dest.writeString(srcFileName);
        dest.writeString(source);
    }
}
