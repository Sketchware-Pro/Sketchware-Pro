package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProjectBean implements Parcelable {
    public static final Parcelable.Creator<ProjectBean> CREATOR = new Parcelable.Creator<ProjectBean>() {
        /* class com.besome.sketch.beans.ProjectBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public ProjectBean createFromParcel(Parcel parcel) {
            return new ProjectBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ProjectBean[] newArray(int i) {
            return new ProjectBean[i];
        }
    };
    public String apkName;
    public ArrayList<String> images;
    public String pkgName;
    public ArrayList<String> screens;

    public ProjectBean() {
        this.pkgName = "";
        this.apkName = "";
        this.pkgName = "";
        this.apkName = "";
        this.screens = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public ProjectBean(Parcel parcel) {
        this.pkgName = "";
        this.apkName = "";
        this.pkgName = parcel.readString();
        this.apkName = parcel.readString();
        this.screens = (ArrayList) parcel.readSerializable();
        this.images = (ArrayList) parcel.readSerializable();
    }

    public static Parcelable.Creator<ProjectBean> getCreator() {
        return CREATOR;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pkgName);
        parcel.writeString(this.apkName);
        parcel.writeSerializable(this.screens);
        parcel.writeSerializable(this.images);
    }
}
