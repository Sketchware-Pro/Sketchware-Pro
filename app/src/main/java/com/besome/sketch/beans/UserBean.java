package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBean implements Parcelable {
    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        /* class com.besome.sketch.beans.UserBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public UserBean createFromParcel(Parcel parcel) {
            return new UserBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public UserBean[] newArray(int i) {
            return new UserBean[i];
        }
    };
    public String alias;
    public boolean isAuto;
    public String isSnsUser;
    public String loginId;
    public String loginPwd;
    public String snsKind;
    public int userId;

    public UserBean() {
    }

    public UserBean(Parcel parcel) {
        this.userId = parcel.readInt();
        this.loginId = parcel.readString();
        this.loginPwd = parcel.readString();
        this.isSnsUser = parcel.readString();
        this.snsKind = parcel.readString();
        this.alias = parcel.readString();
    }

    public static Parcelable.Creator<UserBean> getCreator() {
        return CREATOR;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.userId);
        parcel.writeString(this.loginId);
        parcel.writeString(this.loginPwd);
        parcel.writeString(this.isSnsUser);
        parcel.writeString(this.snsKind);
        parcel.writeString(this.alias);
    }
}
