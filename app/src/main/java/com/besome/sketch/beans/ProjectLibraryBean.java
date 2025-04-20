package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

import pro.sketchware.R;

public class ProjectLibraryBean implements Parcelable {
    public static final Parcelable.Creator<ProjectLibraryBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public ProjectLibraryBean createFromParcel(Parcel source) {
            return new ProjectLibraryBean(source);
        }

        @Override
        public ProjectLibraryBean[] newArray(int size) {
            return new ProjectLibraryBean[size];
        }
    };

    public static final String LIB_USE_N = "N";
    public static final String LIB_USE_Y = "Y";
    public static final int PROJECT_LIB_TYPE_ADMOB = 2;
    public static final int PROJECT_LIB_TYPE_COMPAT = 1;
    public static final int PROJECT_LIB_TYPE_FIREBASE = 0;
    public static final int PROJECT_LIB_TYPE_GOOGLE_MAP = 3;

    public static final int PROJECT_LIB_TYPE_LOCAL_LIB = 4;
    public static final int PROJECT_LIB_TYPE_NATIVE_LIB = 5;

    @Expose
    public String appId;
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
        appId = "";
        libType = i;
        useYn = LIB_USE_N;
        data = "";
        reserved1 = "";
        reserved2 = "";
        reserved3 = "";
        adUnits = new ArrayList<>();
        testDevices = new ArrayList<>();
    }

    public ProjectLibraryBean(Parcel parcel) {
        appId = parcel.readString();
        libType = parcel.readInt();
        useYn = parcel.readString();
        data = parcel.readString();
        reserved1 = parcel.readString();
        reserved2 = parcel.readString();
        reserved3 = parcel.readString();
        adUnits = new ArrayList<>();
        parcel.readTypedList(adUnits, AdUnitBean.getCreator());
        testDevices = new ArrayList<>();
        parcel.readTypedList(testDevices, AdTestDeviceBean.getCreator());
    }

    public static Parcelable.Creator<ProjectLibraryBean> getCreator() {
        return CREATOR;
    }

    public static int getLibraryIcon(int i) {
        return switch (i) {
            case 0 -> R.drawable.ic_mtrl_firebase;
            case 1 -> R.drawable.ic_mtrl_design;
            case 2 -> R.drawable.ic_mtrl_admob;
            case 3 -> R.drawable.ic_mtrl_map;
            case 4 -> R.drawable.ic_mtrl_box;
            case 5 -> R.drawable.ic_mtrl_settings_input;
            default -> 0;
        };
    }

    public static int getLibraryResDesc(int i) {
        return switch (i) {
            case 0 -> R.string.design_library_description_firebase;
            case 1 -> R.string.design_library_description_appcompat_and_design;
            case 2 -> R.string.design_library_description_admob;
            case 3 -> R.string.design_library_description_google_map;
            case 4 -> R.string.text_subtitle_menu_local_library;
            case 5 -> R.string.design_drawer_menu_nativelibs_subtitle;
            default -> 0;
        };
    }

    public static int getLibraryResName(int i) {
        return switch (i) {
            case 0 -> R.string.design_library_firebase_title_firebase;
            case 1 -> R.string.design_library_title_appcompat_and_design;
            case 2 -> R.string.design_library_admob_title_admob;
            case 3 -> R.string.design_library_google_map_title;
            case 4 -> R.string.text_title_menu_local_library;
            case 5 -> R.string.design_drawer_menu_nativelibs;
            default -> 0;
        };
    }

    public void copy(ProjectLibraryBean projectLibraryBean) {
        appId = projectLibraryBean.appId;
        libType = projectLibraryBean.libType;
        useYn = projectLibraryBean.useYn;
        data = projectLibraryBean.data;
        reserved1 = projectLibraryBean.reserved1;
        reserved2 = projectLibraryBean.reserved2;
        reserved3 = projectLibraryBean.reserved3;
        adUnits = new ArrayList<>();
        for (AdUnitBean adUnitBean : projectLibraryBean.adUnits) {
            adUnits.add(adUnitBean.clone());
        }
        testDevices = new ArrayList<>();
        if (projectLibraryBean.testDevices != null) {
            for (AdTestDeviceBean adTestDeviceBean : projectLibraryBean.testDevices) {
                testDevices.add(adTestDeviceBean.clone());
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isEnabled() {
        return useYn != null && !useYn.isEmpty() && useYn.equals(LIB_USE_Y);
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(appId);
        parcel.writeInt(libType);
        parcel.writeString(useYn);
        parcel.writeString(data);
        parcel.writeString(reserved1);
        parcel.writeString(reserved2);
        parcel.writeString(reserved3);
        parcel.writeTypedList(adUnits);
        parcel.writeTypedList(testDevices);
    }

    @Override
    @NonNull
    public ProjectLibraryBean clone() {
        ProjectLibraryBean projectLibraryBean = new ProjectLibraryBean(libType);
        projectLibraryBean.copy(this);
        return projectLibraryBean;
    }
}
