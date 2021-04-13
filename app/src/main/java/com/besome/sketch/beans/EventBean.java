package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class EventBean extends CollapsibleBean implements Parcelable {
    public static final Parcelable.Creator<EventBean> CREATOR = new Parcelable.Creator<EventBean>() {
        /* class com.besome.sketch.beans.EventBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public EventBean createFromParcel(Parcel parcel) {
            return new EventBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public EventBean[] newArray(int i) {
            return new EventBean[i];
        }
    };
    public static final int EVENT_TYPE_ACTIVITY = 3;
    public static final int EVENT_TYPE_COMPONENT = 2;
    public static final int EVENT_TYPE_DRAWER_VIEW = 4;
    public static final int EVENT_TYPE_ETC = 5;
    public static final int EVENT_TYPE_VIEW = 1;
    public static final String SEPARATOR = "_";
    @Expose
    public String eventName;
    @Expose
    public int eventType;
    @Expose
    public String targetId;
    @Expose
    public int targetType;

    public EventBean(int i, int i2, String str, String str2) {
        this.eventType = i;
        this.targetType = i2;
        this.targetId = str;
        this.eventName = str2;
    }

    public static Parcelable.Creator<EventBean> getCreator() {
        return CREATOR;
    }

    public static int getEventIconResource(int i, int i2) {
        if (i == 3) {
            return 2131166270;
        }
        if (i == 1 || i == 4) {
            return ViewBean.getViewTypeResId(i2);
        }
        if (i == 2) {
            return ComponentBean.getIconResource(i2);
        }
        return 2131166260;
    }

    public static int getEventTypeBgRes(int i) {
        if (i == 1) {
            return 2131165337;
        }
        if (i == 2) {
            return 2131165334;
        }
        if (i != 3) {
            return i != 4 ? 0 : 2131165335;
        }
        return 2131165333;
    }

    public static String getEventTypeName(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "drawer view event" : "activity event" : "component event" : "view event";
    }

    public void copy(EventBean eventBean) {
        this.eventType = eventBean.eventType;
        this.targetType = eventBean.targetType;
        this.targetId = eventBean.targetId;
        this.eventName = eventBean.eventName;
    }

    public int describeContents() {
        return 0;
    }

    public String getEventKey() {
        return this.targetId + SEPARATOR + this.eventName;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.eventType);
        parcel.writeInt(this.targetType);
        parcel.writeString(this.targetId);
        parcel.writeString(this.eventName);
    }

    public EventBean(Parcel parcel) {
        this.eventType = parcel.readInt();
        this.targetType = parcel.readInt();
        this.targetId = parcel.readString();
        this.eventName = parcel.readString();
    }
}
