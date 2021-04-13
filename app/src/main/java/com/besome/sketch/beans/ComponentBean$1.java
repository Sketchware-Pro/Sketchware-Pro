package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

class ComponentBean$1 implements Parcelable.Creator {
    ComponentBean$1() {
    }

    @Override // android.os.Parcelable.Creator
    public Object createFromParcel(Parcel parcel) {
        return new ComponentBean(parcel);
    }

    @Override // android.os.Parcelable.Creator
    public Object[] newArray(int i) {
        return new ComponentBean[i];
    }
}
