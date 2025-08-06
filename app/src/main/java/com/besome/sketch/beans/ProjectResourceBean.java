package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

public class ProjectResourceBean extends SelectableBean implements Parcelable {
    public static final Parcelable.Creator<ProjectResourceBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public ProjectResourceBean createFromParcel(Parcel source) {
            return new ProjectResourceBean(source);
        }

        @Override
        public ProjectResourceBean[] newArray(int size) {
            return new ProjectResourceBean[size];
        }
    };

    public static int PROJECT_RES_TYPE_FILE = 1;
    public static int PROJECT_RES_TYPE_RESOURCE;
    public int curSoundPosition;
    public int flipHorizontal;
    public int flipVertical;
    public boolean isDuplicateCollection;
    public boolean isEdited;
    @Expose
    public String resFullName;
    @Expose
    public String resName;


    @Expose
    public int resType;
    public int rotate;
    public int totalSoundDuration;

    public ProjectResourceBean(int resType, String resName, String resFullName) {
        this.resType = resType;
        this.resName = resName;
        this.resFullName = resFullName;
        isEdited = false;
        isDuplicateCollection = false;
        curSoundPosition = 0;
        totalSoundDuration = 0;
        rotate = 0;
        flipVertical = 1;
        flipHorizontal = 1;
    }

    public ProjectResourceBean(Parcel parcel) {
        resType = parcel.readInt();
        resName = parcel.readString();
        resFullName = parcel.readString();
        boolean z = true;
        isEdited = parcel.readInt() != 0;
        isDuplicateCollection = parcel.readInt() != 0;
        curSoundPosition = parcel.readInt();
        totalSoundDuration = parcel.readInt();
        rotate = parcel.readInt();
        flipVertical = parcel.readInt();
        flipHorizontal = parcel.readInt();
        savedPos = parcel.readInt();
        isNew = parcel.readInt() != 0 && z;
    }

    public static Parcelable.Creator<ProjectResourceBean> getCreator() {
        return CREATOR;
    }

    public static boolean isNinePatch(String resFullName) {
        return resFullName.endsWith(".9.png");
    }


    public static boolean isSvg(String resFullName) {
        return resFullName.endsWith(".svg");
    }

    public void copy(ProjectResourceBean projectResourceBean) {
        resType = projectResourceBean.resType;
        resName = projectResourceBean.resName;
        resFullName = projectResourceBean.resFullName;
        isEdited = projectResourceBean.isEdited;
        isDuplicateCollection = projectResourceBean.isDuplicateCollection;
        curSoundPosition = projectResourceBean.curSoundPosition;
        totalSoundDuration = projectResourceBean.totalSoundDuration;
        rotate = projectResourceBean.rotate;
        flipVertical = projectResourceBean.flipVertical;
        flipHorizontal = projectResourceBean.flipHorizontal;
        savedPos = projectResourceBean.savedPos;
        isNew = projectResourceBean.isNew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isNinePatch() {
        return isNinePatch(resFullName);
    }

    public boolean isSvg() {
        return isSvg(resFullName);
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(resType);
        parcel.writeString(resName);
        parcel.writeString(resFullName);
        parcel.writeInt(isEdited ? 1 : 0);
        parcel.writeInt(isDuplicateCollection ? 1 : 0);
        parcel.writeInt(curSoundPosition);
        parcel.writeInt(totalSoundDuration);
        parcel.writeInt(rotate);
        parcel.writeInt(flipVertical);
        parcel.writeInt(flipHorizontal);
        parcel.writeInt(savedPos);
        parcel.writeInt(isNew ? 1 : 0);
    }

    @Override
    @NonNull
    public ProjectResourceBean clone() {
        ProjectResourceBean projectResourceBean = new ProjectResourceBean(resType, resName, resFullName);
        projectResourceBean.copy(this);
        return projectResourceBean;
    }
}
