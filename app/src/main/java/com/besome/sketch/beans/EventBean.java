package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import a.a.a.eC;
import a.a.a.jC;
import pro.sketchware.R;

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
        return switch (eventType) {
            case EVENT_TYPE_ACTIVITY -> R.drawable.ic_mtrl_code;
            case EVENT_TYPE_VIEW, EVENT_TYPE_DRAWER_VIEW -> ViewBean.getViewTypeResId(targetType);
            case EVENT_TYPE_COMPONENT -> ComponentBean.getIconResource(targetType);
            default -> R.drawable.widget_module;
        };
    }

    public static void deleteEvent(String sc_id, EventBean event, ProjectFileBean projectFileBean) {
        jC.a(sc_id).d(projectFileBean.getJavaName(), event.targetId, event.eventName);
        eC a2 = jC.a(sc_id);
        String javaName = projectFileBean.getJavaName();
        a2.k(javaName, event.targetId + "_" + event.eventName);
    }

    public static int getEventTypeBgRes(int eventType) {
        return switch (eventType) {
            case EVENT_TYPE_VIEW -> R.drawable.bg_event_type_view;
            case EVENT_TYPE_COMPONENT -> R.drawable.bg_event_type_component;
            case EVENT_TYPE_ACTIVITY -> R.drawable.bg_event_type_activity;
            case EVENT_TYPE_DRAWER_VIEW -> R.drawable.bg_event_type_drawer_view;
            default -> 0;
        };
    }

    public static String getEventTypeName(int eventType) {
        return switch (eventType) {
            case EVENT_TYPE_VIEW -> "view event";
            case EVENT_TYPE_COMPONENT -> "component event";
            case EVENT_TYPE_ACTIVITY -> "activity event";
            case EVENT_TYPE_DRAWER_VIEW -> "drawer view event";
            default -> "";
        };
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
