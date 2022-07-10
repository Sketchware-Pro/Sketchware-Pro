package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class ProjectResourceBean extends SelectableBean implements Parcelable {
    public static final Parcelable.Creator<ProjectResourceBean> CREATOR = new Parcelable.Creator<ProjectResourceBean>() {

        @Override
        public ProjectResourceBean createFromParcel(Parcel parcel) {
            return new ProjectResourceBean(parcel);
        }

        @Override
        public ProjectResourceBean[] newArray(int i) {
            return new ProjectResourceBean[i];
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
        this.isEdited = false;
        this.isDuplicateCollection = false;
        this.curSoundPosition = 0;
        this.totalSoundDuration = 0;
        this.rotate = 0;
        this.flipVertical = 1;
        this.flipHorizontal = 1;
    }

    public ProjectResourceBean(Parcel parcel) {
        this.resType = parcel.readInt();
        this.resName = parcel.readString();
        this.resFullName = parcel.readString();
        boolean z = true;
        this.isEdited = parcel.readInt() != 0;
        this.isDuplicateCollection = parcel.readInt() != 0;
        this.curSoundPosition = parcel.readInt();
        this.totalSoundDuration = parcel.readInt();
        this.rotate = parcel.readInt();
        this.flipVertical = parcel.readInt();
        this.flipHorizontal = parcel.readInt();
        this.savedPos = parcel.readInt();
        this.isNew = parcel.readInt() != 0 && z;
    }

    public static Parcelable.Creator<ProjectResourceBean> getCreator() {
        return CREATOR;
    }

    public static boolean isNinePatch(String resFullName) {
        return resFullName.endsWith(".9.png");
    }

    public void copy(ProjectResourceBean projectResourceBean) {
        this.resType = projectResourceBean.resType;
        this.resName = projectResourceBean.resName;
        this.resFullName = projectResourceBean.resFullName;
        this.isEdited = projectResourceBean.isEdited;
        this.isDuplicateCollection = projectResourceBean.isDuplicateCollection;
        this.curSoundPosition = projectResourceBean.curSoundPosition;
        this.totalSoundDuration = projectResourceBean.totalSoundDuration;
        this.rotate = projectResourceBean.rotate;
        this.flipVertical = projectResourceBean.flipVertical;
        this.flipHorizontal = projectResourceBean.flipHorizontal;
        this.savedPos = projectResourceBean.savedPos;
        this.isNew = projectResourceBean.isNew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isNinePatch() {
        return isNinePatch(this.resFullName);
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.resType);
        parcel.writeString(this.resName);
        parcel.writeString(this.resFullName);
        parcel.writeInt(this.isEdited ? 1 : 0);
        parcel.writeInt(this.isDuplicateCollection ? 1 : 0);
        parcel.writeInt(this.curSoundPosition);
        parcel.writeInt(this.totalSoundDuration);
        parcel.writeInt(this.rotate);
        parcel.writeInt(this.flipVertical);
        parcel.writeInt(this.flipHorizontal);
        parcel.writeInt(this.savedPos);
        parcel.writeInt(this.isNew ? 1 : 0);
    }

    @Override
    public ProjectResourceBean clone() {
        ProjectResourceBean projectResourceBean = new ProjectResourceBean(this.resType, this.resName, this.resFullName);
        projectResourceBean.copy(this);
        return projectResourceBean;
    }
}
