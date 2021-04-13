package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

class ViewBean$1 implements Parcelable.Creator<ViewBean> {
    ViewBean$1() {
    }

    @Override // android.os.Parcelable.Creator
    public ViewBean createFromParcel(Parcel parcel) {
        return new ViewBean(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public ViewBean[] newArray(int i) {
        return new ViewBean[i];
    }
}
