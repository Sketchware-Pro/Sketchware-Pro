package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import a.a.a.nA;

public class SelectableItemBean extends nA implements Parcelable {
    public static final Parcelable.Creator<SelectableItemBean> CREATOR = new Parcelable.Creator<SelectableItemBean>() {

        @Override
        public SelectableItemBean createFromParcel(Parcel parcel) {
            return new SelectableItemBean(parcel);
        }

        @Override
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
        this.type = SRC_TYPE_NONE;
        this.name = "";
        this.desc = "";
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(String name) {
        this.isSelected = false;
        this.type = SRC_TYPE_NONE;
        this.name = name;
        this.desc = "";
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, int reserved1, int reserved2, int reserved3) {
        this.isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = "";
        this.isNew = false;
        this.reserved1 = reserved1;
        this.reserved2 = reserved2;
        this.reserved3 = reserved3;
    }

    public SelectableItemBean(int type, String name) {
        this.isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = "";
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc) {
        this.isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.isNew = false;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc, boolean isNew) {
        this.isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.isNew = isNew;
        this.reserved1 = 0;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc, boolean isNew, int reserved1) {
        this.isSelected = false;
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.isNew = isNew;
        this.reserved1 = reserved1;
        this.reserved2 = 0;
        this.reserved3 = 0;
    }

    public SelectableItemBean(int type, String name, String desc, boolean isNew, int reserved1, int reserved2, int reserved3) {
        this.isSelected = false;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public void print() {
    }

    @Override
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
