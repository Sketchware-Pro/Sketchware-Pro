package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class InAppBean implements Parcelable {
    public static final Parcelable.Creator<InAppBean> CREATOR = new Parcelable.Creator<InAppBean>() {
        /* class com.besome.sketch.beans.InAppBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public InAppBean createFromParcel(Parcel parcel) {
            return new InAppBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public InAppBean[] newArray(int i) {
            return new InAppBean[i];
        }
    };
    public String choose = ProjectLibraryBean.LIB_USE_N;
    public String currency;
    public String desc;
    public String price;
    public long priceL;
    public String sku;
    public String title;

    public InAppBean() {
    }

    public InAppBean(Parcel parcel) {
        this.sku = parcel.readString();
        this.title = parcel.readString();
        this.price = parcel.readString();
        this.desc = parcel.readString();
        this.choose = parcel.readString();
        this.currency = parcel.readString();
        this.priceL = parcel.readLong();
    }

    public static Parcelable.Creator<InAppBean> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sku);
        parcel.writeString(this.title);
        parcel.writeString(this.price);
        parcel.writeString(this.desc);
        parcel.writeString(this.choose);
        parcel.writeString(this.currency);
        parcel.writeLong(this.priceL);
    }
}
