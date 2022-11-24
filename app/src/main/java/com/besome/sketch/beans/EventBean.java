package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.sketchware.remod.R;

public class EventBean extends CollapsibleBean implements Parcelable {
    public static final Parcelable.Creator<EventBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public EventBean createFromParcel(Parcel source) {
            return new EventBean(source);
        }

        @Override
        public EventBean[] newArray(int size) {
            return new EventBean[size];
        }
    };

    public static final int EVENT_TYPE_VIEW = 1;
    public static final int EVENT_TYPE_COMPONENT = 2;
    public static final int EVENT_TYPE_ACTIVITY = 3;
    public static final int EVENT_TYPE_DRAWER_VIEW = 4;
    public static final int EVENT_TYPE_ETC = 5;

    public static final String SEPARATOR = "_";

    @Expose
    public String eventName;
    @Expose
    public int eventType;
    @Expose
    public String targetId;
    @Expose
    public int targetType;

    public EventBean(int eventType, int targetType, String targetId, String eventName) {
        this.eventType = eventType;
        this.targetType = targetType;
        this.targetId = targetId;
        this.eventName = eventName;
    }

    public EventBean(Parcel other) {
        eventType = other.readInt();
        targetType = other.readInt();
        targetId = other.readString();
        eventName = other.readString();
    }

    public static Parcelable.Creator<EventBean> getCreator() {
        return CREATOR;
    }

    public static int getEventIconResource(int eventType, int targetType) {
        switch (eventType) {
            case EVENT_TYPE_ACTIVITY:
                return R.drawable.widget_source;

            case EVENT_TYPE_VIEW:
            case EVENT_TYPE_DRAWER_VIEW:
                return ViewBean.getViewTypeResId(targetType);

            case EVENT_TYPE_COMPONENT:
                return ComponentBean.getIconResource(targetType);

            default:
                return R.drawable.widget_module;
        }
    }

    public static int getEventTypeBgRes(int eventType) {
        switch (eventType) {
            case EVENT_TYPE_VIEW:
                return R.drawable.bg_event_type_view;

            case EVENT_TYPE_COMPONENT:
                return R.drawable.bg_event_type_component;

            case EVENT_TYPE_ACTIVITY:
                return R.drawable.bg_event_type_activity;

            case EVENT_TYPE_DRAWER_VIEW:
                return R.drawable.bg_event_type_drawer_view;

            default:
                return 0;
        }
    }

    public static String getEventTypeName(int eventType) {
        switch (eventType) {
            case EVENT_TYPE_VIEW:
                return "view event";

            case EVENT_TYPE_COMPONENT:
                return "component event";

            case EVENT_TYPE_ACTIVITY:
                return "activity event";

            case EVENT_TYPE_DRAWER_VIEW:
                return "drawer view event";

            default:
                return "";
        }
    }

    public void copy(EventBean other) {
        eventType = other.eventType;
        targetType = other.targetType;
        targetId = other.targetId;
        eventName = other.eventName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getEventKey() {
        return targetId + SEPARATOR + eventName;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventType);
        dest.writeInt(targetType);
        dest.writeString(targetId);
        dest.writeString(eventName);
    }
}
