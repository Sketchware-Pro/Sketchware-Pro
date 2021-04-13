package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectLibraryBean implements Parcelable {
    public static final Parcelable.Creator<ProjectLibraryBean> CREATOR = new Parcelable.Creator<ProjectLibraryBean>() {
        /* class com.besome.sketch.beans.ProjectLibraryBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public ProjectLibraryBean createFromParcel(Parcel parcel) {
            return new ProjectLibraryBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public ProjectLibraryBean[] newArray(int i) {
            return new ProjectLibraryBean[i];
        }
    };
    public static final String LIB_USE_N = "N";
    public static final String LIB_USE_Y = "Y";
    public static final int PROJECT_LIB_TYPE_ADMOB = 2;
    public static final int PROJECT_LIB_TYPE_COMPAT = 1;
    public static final int PROJECT_LIB_TYPE_FIREBASE = 0;
    public static final int PROJECT_LIB_TYPE_GOOGLE_MAP = 3;
    @Expose
    public ArrayList<AdUnitBean> adUnits;
    @Expose
    public String data;
    @Expose
    public int libType;
    @Expose
    public String reserved1;
    @Expose
    public String reserved2;
    @Expose
    public String reserved3;
    @Expose
    public ArrayList<AdTestDeviceBean> testDevices;
    @Expose
    public String useYn;

    public ProjectLibraryBean(int i) {
        this.libType = i;
        this.useYn = LIB_USE_N;
        this.data = "";
        this.reserved1 = "";
        this.reserved2 = "";
        this.reserved3 = "";
        this.adUnits = new ArrayList<>();
        this.testDevices = new ArrayList<>();
    }

    public static Parcelable.Creator<ProjectLibraryBean> getCreator() {
        return CREATOR;
    }

    public static int getLibraryIcon(int i) {
        if (i == 0) {
            return 2131166245;
        }
        if (i == 1) {
            return 2131165505;
        }
        if (i != 2) {
            return i != 3 ? 0 : 2131166247;
        }
        return 2131166234;
    }

    public static int getLibraryResDesc(int i) {
        if (i == 0) {
            return 2131625204;
        }
        if (i == 1) {
            return 2131625203;
        }
        if (i != 2) {
            return i != 3 ? 0 : 2131625205;
        }
        return 2131625202;
    }

    public static int getLibraryResName(int i) {
        if (i == 0) {
            return 2131625234;
        }
        if (i == 1) {
            return 2131625251;
        }
        if (i != 2) {
            return i != 3 ? 0 : 2131625241;
        }
        return 2131625194;
    }

    public void copy(ProjectLibraryBean projectLibraryBean) {
        this.libType = projectLibraryBean.libType;
        this.useYn = projectLibraryBean.useYn;
        this.data = projectLibraryBean.data;
        this.reserved1 = projectLibraryBean.reserved1;
        this.reserved2 = projectLibraryBean.reserved2;
        this.reserved3 = projectLibraryBean.reserved3;
        this.adUnits = new ArrayList<>();
        Iterator<AdUnitBean> it = projectLibraryBean.adUnits.iterator();
        while (it.hasNext()) {
            this.adUnits.add(it.next().clone());
        }
        this.testDevices = new ArrayList<>();
        ArrayList<AdTestDeviceBean> arrayList = projectLibraryBean.testDevices;
        if (arrayList != null) {
            Iterator<AdTestDeviceBean> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.testDevices.add(it2.next().clone());
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public boolean isEnabled() {
        String str = this.useYn;
        return str != null && !str.isEmpty() && this.useYn.equals(LIB_USE_Y);
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.libType);
        parcel.writeString(this.useYn);
        parcel.writeString(this.data);
        parcel.writeString(this.reserved1);
        parcel.writeString(this.reserved2);
        parcel.writeString(this.reserved3);
        parcel.writeTypedList(this.adUnits);
        parcel.writeTypedList(this.testDevices);
    }

    @Override // java.lang.Object
    public ProjectLibraryBean clone() {
        ProjectLibraryBean projectLibraryBean = new ProjectLibraryBean(this.libType);
        projectLibraryBean.copy(this);
        return projectLibraryBean;
    }

    public ProjectLibraryBean(Parcel parcel) {
        this.libType = parcel.readInt();
        this.useYn = parcel.readString();
        this.data = parcel.readString();
        this.reserved1 = parcel.readString();
        this.reserved2 = parcel.readString();
        this.reserved3 = parcel.readString();
        this.adUnits = new ArrayList<>();
        parcel.readTypedList(this.adUnits, AdUnitBean.getCreator());
        this.testDevices = new ArrayList<>();
        parcel.readTypedList(this.testDevices, AdTestDeviceBean.getCreator());
    }
}
