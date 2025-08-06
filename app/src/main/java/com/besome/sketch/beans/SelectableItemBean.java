package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import a.a.a.nA;

public class SelectableItemBean extends nA implements Parcelable {
    public static final Parcelable.Creator<SelectableItemBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public SelectableItemBean createFromParcel(Parcel source) {
            return new SelectableItemBean(source);
        }

        @Override
        public SelectableItemBean[] newArray(int size) {
            return new SelectableItemBean[size];
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
        isSelected = false;
        type = SRC_TYPE_NONE;
        name = "";
        desc = "";
        isNew = false;
        reserved1 = 0;
        reserved2 = 0;
        reserved3 = 0;
    }

    public SelectableItemBean(String name) {
        isSelected = false;
        type = SRC_TYPE_NONE;
        this.name = name;
        desc = "";
        isNew = false;
        reserved1 = 0;
        reserved2 = 0;
        reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, int reserved1, int reserved2, int reserved3) {
        isSelected = false;
        this.type = type;
        this.name = name;
        desc = "";
        isNew = false;
        this.reserved1 = reserved1;
        this.reserved2 = reserved2;
        this.reserved3 = reserved3;
    }

    public SelectableItemBean(int type, String name) {
        isSelected = false;
        this.type = type;
        this.name = name;
        desc = "";
        isNew = false;
        reserved1 = 0;
        reserved2 = 0;
        reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc) {
        isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        isNew = false;
        reserved1 = 0;
        reserved2 = 0;
        reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc, boolean isNew) {
        isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.isNew = isNew;
        reserved1 = 0;
        reserved2 = 0;
        reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc, boolean isNew, int reserved1) {
        isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.isNew = isNew;
        this.reserved1 = reserved1;
        reserved2 = 0;
        reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc, boolean isNew, int reserved1, int reserved2, int reserved3) {
        isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.isNew = isNew;
        this.reserved1 = reserved1;
        this.reserved2 = reserved2;
        this.reserved3 = reserved3;
    }

    public SelectableItemBean(Parcel parcel) {
        boolean z = false;
        isSelected = parcel.readInt() != 0;
        type = parcel.readInt();
        name = parcel.readString();
        desc = parcel.readString();
        isNew = parcel.readInt() != 0 || z;
        reserved1 = parcel.readInt();
        reserved2 = parcel.readInt();
        reserved3 = parcel.readInt();
    }

    public static Parcelable.Creator<SelectableItemBean> getCreator() {
        return CREATOR;
    }

    public void copy(SelectableItemBean selectableItemBean) {
        isSelected = selectableItemBean.isSelected;
        type = selectableItemBean.type;
        name = selectableItemBean.name;
        desc = selectableItemBean.desc;
        isNew = selectableItemBean.isNew;
        reserved1 = selectableItemBean.reserved1;
        reserved2 = selectableItemBean.reserved2;
        reserved3 = selectableItemBean.reserved3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(isSelected ? 1 : 0);
        parcel.writeInt(type);
        parcel.writeString(name);
        parcel.writeString(desc);
        parcel.writeInt(isNew ? 1 : 0);
        parcel.writeInt(reserved1);
        parcel.writeInt(reserved2);
        parcel.writeInt(reserved3);
    }
}
