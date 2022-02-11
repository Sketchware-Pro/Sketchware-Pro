package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import a.a.a.nA;
import a.a.a.yB;

public class TutorialBean extends nA implements Parcelable {
    public static final Parcelable.Creator<TutorialBean> CREATOR = new Parcelable.Creator<TutorialBean>() {

        @Override
        public TutorialBean createFromParcel(Parcel parcel) {
            return new TutorialBean(parcel);
        }

        @Override
        public TutorialBean[] newArray(int i) {
            return new TutorialBean[i];
        }
    };
    public String groupDesc;
    public int groupIcon;
    public int groupId;
    public String groupTitle;
    public ArrayList<HashMap<String, Object>> listAdvancedHash = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> listDocHash = new ArrayList<>();
    public ArrayList<HashMap<String, Object>> listHash = new ArrayList<>();
    public int progress;
    public int startedCnt;

    public TutorialBean() {
    }

    public TutorialBean(Parcel parcel) {
        this.groupId = parcel.readInt();
        this.groupTitle = parcel.readString();
        this.groupDesc = parcel.readString();
        this.progress = parcel.readInt();
        this.startedCnt = parcel.readInt();
        this.listHash = (ArrayList) parcel.readSerializable();
        this.listAdvancedHash = (ArrayList) parcel.readSerializable();
        this.listDocHash = (ArrayList) parcel.readSerializable();
    }

    public static Parcelable.Creator<TutorialBean> getCreator() {
        return CREATOR;
    }

    public void addAdvancedList(HashMap<String, Object> hashMap) {
        this.listAdvancedHash.add(hashMap);
    }

    public void addBasicList(HashMap<String, Object> hashMap) {
        this.listHash.add(hashMap);
    }

    public void addDocList(HashMap<String, Object> hashMap) {
        this.listDocHash.add(hashMap);
    }

    public void copy(TutorialBean tutorialBean) {
        this.groupId = tutorialBean.groupId;
        this.groupTitle = tutorialBean.groupTitle;
        this.groupDesc = tutorialBean.groupDesc;
        this.progress = tutorialBean.progress;
        this.startedCnt = tutorialBean.startedCnt;
        this.listHash = tutorialBean.listHash;
        this.listAdvancedHash = tutorialBean.listAdvancedHash;
        this.listDocHash = tutorialBean.listDocHash;
    }

    public int describeContents() {
        return 0;
    }

    public int getGroupAdvancedListSize() {
        return this.listAdvancedHash.size();
    }

    public int getGroupDocSize() {
        return this.listDocHash.size();
    }

    public int getGroupListAllSize() {
        return getGroupListSize() + getGroupAdvancedListSize() + getGroupDocSize();
    }

    public int getGroupListSize() {
        return this.listHash.size();
    }

    public String getProgressText() {
        int groupListAllSize = getGroupListAllSize();
        if (groupListAllSize == 0) {
            return "";
        }
        if (this.progress >= groupListAllSize) {
            return "Completed";
        }
        return this.progress + "/" + groupListAllSize;
    }

    public void resetTutorial() {
        this.progress = 0;
        Iterator<HashMap<String, Object>> it = this.listHash.iterator();
        while (it.hasNext()) {
            it.next().put("completed", ProjectLibraryBean.LIB_USE_N);
        }
        Iterator<HashMap<String, Object>> it2 = this.listAdvancedHash.iterator();
        while (it2.hasNext()) {
            it2.next().put("completed", ProjectLibraryBean.LIB_USE_N);
        }
        Iterator<HashMap<String, Object>> it3 = this.listDocHash.iterator();
        while (it3.hasNext()) {
            it3.next().put("completed", ProjectLibraryBean.LIB_USE_N);
        }
    }

    public void updateCompleteStatus(String str) {
        this.progress = 0;
        if (!str.isEmpty()) {
            String[] split = str.split(",");
            for (String str2 : split) {
                Iterator<HashMap<String, Object>> it = this.listHash.iterator();
                while (it.hasNext()) {
                    HashMap<String, Object> next = it.next();
                    if (yB.c(next, "sc_id").equals(str2)) {
                        next.put("completed", ProjectLibraryBean.LIB_USE_Y);
                        this.progress++;
                    }
                }
                Iterator<HashMap<String, Object>> it2 = this.listAdvancedHash.iterator();
                while (it2.hasNext()) {
                    HashMap<String, Object> next2 = it2.next();
                    if (yB.c(next2, "sc_id").equals(str2)) {
                        next2.put("completed", ProjectLibraryBean.LIB_USE_Y);
                        this.progress++;
                    }
                }
                Iterator<HashMap<String, Object>> it3 = this.listDocHash.iterator();
                while (it3.hasNext()) {
                    HashMap<String, Object> next3 = it3.next();
                    if (yB.c(next3, "sc_id").equals(str2)) {
                        next3.put("completed", ProjectLibraryBean.LIB_USE_Y);
                        this.progress++;
                    }
                }
            }
            return;
        }
        Iterator<HashMap<String, Object>> it4 = this.listHash.iterator();
        while (it4.hasNext()) {
            it4.next().put("completed", ProjectLibraryBean.LIB_USE_N);
        }
        Iterator<HashMap<String, Object>> it5 = this.listAdvancedHash.iterator();
        while (it5.hasNext()) {
            it5.next().put("completed", ProjectLibraryBean.LIB_USE_N);
        }
        Iterator<HashMap<String, Object>> it6 = this.listDocHash.iterator();
        while (it6.hasNext()) {
            it6.next().put("completed", ProjectLibraryBean.LIB_USE_N);
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.groupId);
        parcel.writeString(this.groupTitle);
        parcel.writeString(this.groupDesc);
        parcel.writeInt(this.progress);
        parcel.writeInt(this.startedCnt);
        parcel.writeSerializable(this.listHash);
        parcel.writeSerializable(this.listAdvancedHash);
        parcel.writeSerializable(this.listDocHash);
    }
}
