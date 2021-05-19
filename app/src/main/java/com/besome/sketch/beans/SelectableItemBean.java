package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import a.a.a.nA;

public class SelectableItemBean extends nA implements Parcelable {
    public static final Parcelable.Creator<SelectableItemBean> CREATOR = new Parcelable.Creator<SelectableItemBean>() {
        /* class com.besome.sketch.beans.SelectableItemBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public SelectableItemBean createFromParcel(Parcel parcel) {
            return new SelectableItemBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public SelectableItemBean[] newArray(int i) {
            return new SelectableItemBean[i];
        }
    };
    public static final int SRC_TYPE_NONE = -1;
    public static final int SRC_TYPE_SKETCHWARE_DATA = 1;
    public static final int SRC_TYPE_SKETCHWARE_ICONPACK = 2;
    public static final int SRC_TYPE_STORAGE = 0;
    public String desc;
    public boolean isNew;
    public boolean isNinePatch;
    public boolean isSelected;
    public String name;
    public int reserved1;
    public int reserved2;
    public int reserved3;
    public int type;

    public SelectableItemBean() {
        this.isSelected = false;
        this.type = -1;
        this.name = "";
        this.desc = "";
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(String str) {
        this.isSelected = false;
        this.type = -1;
        this.name = str;
        this.desc = "";
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int i, String str, int i2, int i3, int i4) {
        this.isSelected = false;
        this.type = i;
        this.name = str;
        this.desc = "";
        this.isNew = false;
        this.reserved1 = i2;
        this.reserved2 = i3;
        this.reserved3 = i4;
    }

    public SelectableItemBean(int i, String str) {
        this.isSelected = false;
        this.type = i;
        this.name = str;
        this.desc = "";
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int i, String str, String str2) {
        this.isSelected = false;
        this.type = i;
        this.name = str;
        this.desc = str2;
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int i, String str, String str2, boolean z) {
        this.isSelected = false;
        this.type = i;
        this.name = str;
        this.desc = str2;
        this.isNew = z;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int i, String str, String str2, boolean z, int i2) {
        this.isSelected = false;
        this.type = i;
        this.name = str;
        this.desc = str2;
        this.isNew = z;
        this.reserved1 = i2;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int i, String str, String str2, boolean z, int i2, int i3, int i4) {
        this.isSelected = false;
        this.type = i;
        this.name = str;
        this.desc = str2;
        this.isNew = z;
        this.reserved1 = i2;
        this.reserved2 = i3;
        this.reserved3 = i4;
    }

    public SelectableItemBean(Parcel parcel) {
        boolean z = false;
        this.isSelected = parcel.readInt() != 0;
        this.type = parcel.readInt();
        this.name = parcel.readString();
        this.desc = parcel.readString();
        this.isNew = parcel.readInt() != 0 || z;
        this.reserved1 = parcel.readInt();
        this.reserved2 = parcel.readInt();
        this.reserved3 = parcel.readInt();
    }

    public static Parcelable.Creator<SelectableItemBean> getCreator() {
        return CREATOR;
    }

    public void copy(SelectableItemBean selectableItemBean) {
        this.isSelected = selectableItemBean.isSelected;
        this.type = selectableItemBean.type;
        this.name = selectableItemBean.name;
        this.desc = selectableItemBean.desc;
        this.isNew = selectableItemBean.isNew;
        this.reserved1 = selectableItemBean.reserved1;
        this.reserved2 = selectableItemBean.reserved2;
        this.reserved3 = selectableItemBean.reserved3;
    }

    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.isSelected ? 1 : 0);
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeString(this.desc);
        parcel.writeInt(this.isNew ? 1 : 0);
        parcel.writeInt(this.reserved1);
        parcel.writeInt(this.reserved2);
        parcel.writeInt(this.reserved3);
    }
}
