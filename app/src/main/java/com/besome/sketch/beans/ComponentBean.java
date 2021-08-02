package com.besome.sketch.beans;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.sketchware.remod.Resources;

import a.a.a.Gx;
import mod.hilal.saif.components.ComponentsHandler;

public class ComponentBean extends CollapsibleBean implements Parcelable {

    public static final int COMPONENT_TYPE_INTENT = 1;
    public static final int COMPONENT_TYPE_SHAREDPREF = 2;
    public static final int COMPONENT_TYPE_CALENDAR = 3;
    public static final int COMPONENT_TYPE_VIBRATOR = 4;
    public static final int COMPONENT_TYPE_TIMERTASK = 5;
    public static final int COMPONENT_TYPE_FIREBASE = 6;
    public static final int COMPONENT_TYPE_DIALOG = 7;
    public static final int COMPONENT_TYPE_MEDIAPLAYER = 8;
    public static final int COMPONENT_TYPE_SOUNDPOOL = 9;
    public static final int COMPONENT_TYPE_OBJECTANIMATOR = 10;
    public static final int COMPONENT_TYPE_GYROSCOPE = 11;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH = 12;
    public static final int COMPONENT_TYPE_INTERSTITIAL_AD = 13;
    public static final int COMPONENT_TYPE_FIREBASE_STORAGE = 14;
    public static final int COMPONENT_TYPE_CAMERA = 15;
    public static final int COMPONENT_TYPE_FILE_PICKER = 16;
    public static final int COMPONENT_TYPE_REQUEST_NETWORK = 17;
    public static final int COMPONENT_TYPE_TEXT_TO_SPEECH = 18;
    public static final int COMPONENT_TYPE_SPEECH_TO_TEXT = 19;
    public static final int COMPONENT_TYPE_BLUETOOTH_CONNECT = 20;
    public static final int COMPONENT_TYPE_LOCATION_MANAGER = 21;

    public static final Parcelable.Creator<ComponentBean> CREATOR = new Parcelable.Creator<ComponentBean>() {
        @Override
        public ComponentBean createFromParcel(Parcel source) {
            return new ComponentBean(source);
        }

        @Override
        public ComponentBean[] newArray(int size) {
            return new ComponentBean[size];
        }
    };

    public Gx classInfo;
    @Expose
    public String componentId;
    @Expose
    public String param1;
    @Expose
    public String param2;
    @Expose
    public String param3;
    @Expose
    public int type;

    /**
     * Constructs a Component without <code>componentId</code>, <code>param1</code>,
     * <code>param2</code>, <code>param3</code>
     */
    public ComponentBean(int type) {
        this(type, "");
    }

    /**
     * Constructs a Component without <code>param1</code>, <code>param2</code>, <code>param3</code>.
     */
    public ComponentBean(int type, String componentId) {
        this(type, componentId, "", "");
    }

    /**
     * Constructs a Component without <code>param2</code> and <code>param3</code>.
     */
    public ComponentBean(int type, String componentId, String param1) {
        this(type, componentId, param1, "", "");
    }

    /**
     * Constructs a Component without <code>param3</code>.
     */
    public ComponentBean(int type, String componentId, String param1, String param2) {
        this(type, componentId, param1, param2, "");
    }

    public ComponentBean(int type, String componentId, String param1, String param2, String param3) {
        this.type = type;
        this.componentId = componentId;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public ComponentBean(Parcel other) {
        this.type = other.readInt();
        this.componentId = other.readString();
        this.param1 = other.readString();
        this.param2 = other.readString();
        this.param3 = other.readString();
    }

    public static String getComponentDocsUrlByTypeName(int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return "https://docs.sketchware.io/docs/component-intent.html";

            case COMPONENT_TYPE_SHAREDPREF:
                return "https://docs.sketchware.io/docs/component-shared-preference.html";

            case COMPONENT_TYPE_CALENDAR:
                return "https://docs.sketchware.io/docs/component-calendar.html";

            case COMPONENT_TYPE_VIBRATOR:
                return "https://docs.sketchware.io/docs/component-vibrator.html";

            case COMPONENT_TYPE_TIMERTASK:
                return "https://docs.sketchware.io/docs/component-timer.html";

            case COMPONENT_TYPE_FIREBASE:
                return "https://docs.sketchware.io/docs/component-firebase-database.html";

            case COMPONENT_TYPE_DIALOG:
                return "https://docs.sketchware.io/docs/component-dialog.html";

            case COMPONENT_TYPE_MEDIAPLAYER:
                return "https://docs.sketchware.io/docs/component-mediaplayer.html";

            case COMPONENT_TYPE_SOUNDPOOL:
                return "https://docs.sketchware.io/docs/component-soundpool.html";

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return "https://docs.sketchware.io/docs/component-object-animator.html";

            case COMPONENT_TYPE_GYROSCOPE:
                return "https://docs.sketchware.io/docs/component-gyroscope.html";

            case COMPONENT_TYPE_FIREBASE_AUTH:
                return "https://docs.sketchware.io/docs/component-firebase-auth.html";

            case COMPONENT_TYPE_INTERSTITIAL_AD:
            case COMPONENT_TYPE_REQUEST_NETWORK:
            case COMPONENT_TYPE_TEXT_TO_SPEECH:
            case COMPONENT_TYPE_SPEECH_TO_TEXT:
            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
            case COMPONENT_TYPE_LOCATION_MANAGER:
                // sad :c
                return "";

            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return "https://docs.sketchware.io/docs/component-firebase-storage.html";

            case COMPONENT_TYPE_CAMERA:
                return "https://docs.sketchware.io/docs/component-camera.html";

            case COMPONENT_TYPE_FILE_PICKER:
                return "https://docs.sketchware.io/docs/component-filepicker.html";

            default:
                return ComponentsHandler.docs(type);
        }
    }

    /**
     * @param context Can be null, as it's not used
     */
    public static String getComponentName(Context context, int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return "Intent";

            case COMPONENT_TYPE_SHAREDPREF:
                return "SharedPreferences";

            case COMPONENT_TYPE_CALENDAR:
                return "Calendar";

            case COMPONENT_TYPE_VIBRATOR:
                return "Vibrator";

            case COMPONENT_TYPE_TIMERTASK:
                return "Timer";

            case COMPONENT_TYPE_FIREBASE:
                return "Firebase DB";

            case COMPONENT_TYPE_DIALOG:
                return "Dialog";

            case COMPONENT_TYPE_MEDIAPLAYER:
                return "MediaPlayer";

            case COMPONENT_TYPE_SOUNDPOOL:
                return "SoundPool";

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return "ObjectAnimator";

            case COMPONENT_TYPE_GYROSCOPE:
                return "Gyroscope";

            case COMPONENT_TYPE_FIREBASE_AUTH:
                return "Firebase Auth";

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return "Interstitial Ad";

            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return "Firebase Storage";

            case COMPONENT_TYPE_CAMERA:
                return "Camera";

            case COMPONENT_TYPE_FILE_PICKER:
                return "FilePicker";

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return "RequestNetwork";

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return "TextToSpeech";

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return "SpeechToText";

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return "BluetoothConnect";

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return "LocationManager";

            case 22:
                return "RewardedVideoAd";

            case 23:
                return "ProgressDialog";

            case 24:
                return "DatePickerDialog";

            case 25:
                return "TimePickerDialog";

            case 26:
                return "Notification";

            case 27:
                return "FragmentAdapter";

            case 28:
                return "PhoneAuth";

            case 29:
                return "Dynamic Link";

            case 30:
                return "Cloud Message";

            case 31:
                return "Google Login";

            case 32:
                return "OneSignal";

            case 33:
                return "Facebook Ads Banner";

            case 34:
                return "Facebook Ads Interstitial";

            default:
                return ComponentsHandler.name(type);
        }
    }

    public static int getComponentTypeByTypeName(String typeName) {
        switch (typeName) {
            case "Intent":
                return COMPONENT_TYPE_INTENT;

            case "File":
                return COMPONENT_TYPE_SHAREDPREF;

            case "Calendar":
                return COMPONENT_TYPE_CALENDAR;

            case "Vibrator":
                return COMPONENT_TYPE_VIBRATOR;

            case "Timer":
                return COMPONENT_TYPE_TIMERTASK;

            case "FirebaseDB":
                return COMPONENT_TYPE_FIREBASE;

            case "Dialog":
                return COMPONENT_TYPE_DIALOG;

            case "MediaPlayer":
                return COMPONENT_TYPE_MEDIAPLAYER;

            case "SoundPool":
                return COMPONENT_TYPE_SOUNDPOOL;

            case "ObjectAnimator":
                return COMPONENT_TYPE_OBJECTANIMATOR;

            case "Gyroscope":
                return COMPONENT_TYPE_GYROSCOPE;

            case "FirebaseAuth":
                return COMPONENT_TYPE_FIREBASE_AUTH;

            case "InterstitialAd":
                return COMPONENT_TYPE_INTERSTITIAL_AD;

            case "FirebaseStorage":
                return COMPONENT_TYPE_FIREBASE_STORAGE;

            case "Camera":
                return COMPONENT_TYPE_CAMERA;

            case "FilePicker":
                return COMPONENT_TYPE_FILE_PICKER;

            case "RequestNetwork":
                return COMPONENT_TYPE_REQUEST_NETWORK;

            case "TextToSpeech":
                return COMPONENT_TYPE_TEXT_TO_SPEECH;

            case "SpeechToText":
                return COMPONENT_TYPE_SPEECH_TO_TEXT;

            case "BluetoothConnect":
                return COMPONENT_TYPE_BLUETOOTH_CONNECT;

            case "LocationManager":
                return COMPONENT_TYPE_LOCATION_MANAGER;

            case "RewardedVideoAd":
                return 22;

            case "ProgressDialog":
                return 23;

            case "DatePickerDialog":
                return 24;

            case "TimePickerDialog":
                return 25;

            case "Notification":
                return 26;

            case "FragmentAdapter":
                return 27;

            case "FirebasePhoneAuth":
                return 28;

            case "FirebaseDynamicLink":
                return 29;

            case "FirebaseCloudMessage":
                return 30;

            case "FirebaseGoogleLogin":
                return 31;

            case "OneSignal":
                return 32;

            case "FBAdsBanner":
                return 33;

            case "FBAdsInterstitial":
                return 34;

            default:
                return ComponentsHandler.id(typeName);
        }
    }

    public static String getComponentTypeName(int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return "Intent";

            case COMPONENT_TYPE_SHAREDPREF:
                return "File";

            case COMPONENT_TYPE_CALENDAR:
                return "Calendar";

            case COMPONENT_TYPE_VIBRATOR:
                return "Vibrator";

            case COMPONENT_TYPE_TIMERTASK:
                return "Timer";

            case COMPONENT_TYPE_FIREBASE:
                return "FirebaseDB";

            case COMPONENT_TYPE_DIALOG:
                return "Dialog";

            case COMPONENT_TYPE_MEDIAPLAYER:
                return "MediaPlayer";

            case COMPONENT_TYPE_SOUNDPOOL:
                return "SoundPool";

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return "ObjectAnimator";

            case COMPONENT_TYPE_GYROSCOPE:
                return "Gyroscope";

            case COMPONENT_TYPE_FIREBASE_AUTH:
                return "FirebaseAuth";

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return "InterstitialAd";

            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return "FirebaseStorage";

            case COMPONENT_TYPE_CAMERA:
                return "Camera";

            case COMPONENT_TYPE_FILE_PICKER:
                return "FilePicker";

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return "RequestNetwork";

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return "TextToSpeech";

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return "SpeechToText";

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return "BluetoothConnect";

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return "LocationManager";

            case 22:
                return "RewardedVideoAd";

            case 23:
                return "ProgressDialog";

            case 24:
                return "DatePickerDialog";

            case 25:
                return "TimePickerDialog";

            case 26:
                return "Notification";

            case 27:
                return "FragmentAdapter";

            case 28:
                return "FirebasePhoneAuth";

            case 29:
                return "FirebaseDynamicLink";

            case 30:
                return "FirebaseCloudMessage";

            case 31:
                return "FirebaseGoogleLogin";

            case 32:
                return "OneSignal";

            case 33:
                return "FBAdsBanner";

            case 34:
                return "FBAdsInterstitial";

            default:
                return ComponentsHandler.typeName(type);
        }
    }

    public static Parcelable.Creator<ComponentBean> getCreator() {
        return CREATOR;
    }

    public static int getDescStrResource(int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return Resources.string.component_description_intent;

            case COMPONENT_TYPE_SHAREDPREF:
                return Resources.string.component_description_file;

            case COMPONENT_TYPE_CALENDAR:
                return Resources.string.component_description_calendar;

            case COMPONENT_TYPE_VIBRATOR:
                return Resources.string.component_description_vibrator;

            case COMPONENT_TYPE_TIMERTASK:
                return Resources.string.component_description_timer;

            case COMPONENT_TYPE_FIREBASE:
                return Resources.string.design_library_firebase_description_about_firebase;

            case COMPONENT_TYPE_DIALOG:
                return Resources.string.component_description_dialog;

            case COMPONENT_TYPE_MEDIAPLAYER:
                return Resources.string.component_description_mediaplayer;

            case COMPONENT_TYPE_SOUNDPOOL:
                return Resources.string.component_description_soundpool;

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return Resources.string.component_description_objectanimator;

            case COMPONENT_TYPE_GYROSCOPE:
                return Resources.string.component_description_gyrosope;

            case COMPONENT_TYPE_FIREBASE_AUTH:
                return Resources.string.component_description_firebase_auth;

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return Resources.string.component_description_interstitial_ad;

            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return Resources.string.component_description_firebase_storage;

            case COMPONENT_TYPE_CAMERA:
                return Resources.string.component_description_camera;

            case COMPONENT_TYPE_FILE_PICKER:
                return Resources.string.component_description_file_picker;

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return Resources.string.component_description_request_network;

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return Resources.string.component_description_text_to_speech;

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return Resources.string.component_description_speech_to_text;

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return Resources.string.component_description_bluetooth_connect;

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return Resources.string.component_description_location_manager;

            case 22:
                return Resources.string.component_description_video_ad;

            case 23:
                return Resources.string.component_description_progress_dialog;

            case 24:
                return Resources.string.component_description_date_picker_dialog;

            case 25:
                return Resources.string.component_description_time_picker_dialog;

            case 26:
                return Resources.string.component_description_notification;

            case 27:
                return Resources.string.component_description_fragment_adapter;

            case 28:
                return Resources.string.component_description_fb_phone_auth;

            case 29:
                return Resources.string.component_description_fb_dynamic;

            case 30:
                return Resources.string.component_description_fb_fcm;

            case 31:
                return Resources.string.component_description_fb_google;

            case 32:
                return Resources.string.component_description_fb_admin;

            case 33:
                return Resources.string.component_description_fb_ads_banner;

            case 34:
                return Resources.string.component_description_fb_ads_interstitial;

            default:
                return 0;
        }
    }

    public static int getIconResource(int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return Resources.drawable.widget_intent;

            case COMPONENT_TYPE_SHAREDPREF:
                return Resources.drawable.widget_shared_preference;

            case COMPONENT_TYPE_CALENDAR:
                return Resources.drawable.widget_calendar;

            case COMPONENT_TYPE_VIBRATOR:
                return Resources.drawable.widget_vibrator;

            case COMPONENT_TYPE_TIMERTASK:
                return Resources.drawable.widget_timer;

            case COMPONENT_TYPE_FIREBASE:
            case COMPONENT_TYPE_FIREBASE_AUTH:
            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return Resources.drawable.widget_firebase;

            case COMPONENT_TYPE_DIALOG:
                return Resources.drawable.widget_alertdialog;

            case COMPONENT_TYPE_MEDIAPLAYER:
                return Resources.drawable.widget_mediaplayer;

            case COMPONENT_TYPE_SOUNDPOOL:
                return Resources.drawable.widget_soundpool;

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return Resources.drawable.widget_objectanimator;

            case COMPONENT_TYPE_GYROSCOPE:
                return Resources.drawable.widget_gyroscope;

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return Resources.drawable.widget_admob;

            case COMPONENT_TYPE_CAMERA:
                return Resources.drawable.widget_camera;

            case COMPONENT_TYPE_FILE_PICKER:
                return Resources.drawable.widget_file;

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return Resources.drawable.widget_network_request;

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return Resources.drawable.widget_text_to_speech;

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return Resources.drawable.widget_speech_to_text;

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return Resources.drawable.widget_bluetooth;

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return Resources.drawable.widget_location;

            case 22:
                return Resources.drawable.widget_media_controller;

            case 23:
                return Resources.drawable.widget_progress_dialog;

            case 24:
                return Resources.drawable.widget_date_picker_dialog;

            case 25:
                return Resources.drawable.widget_time_picker_dialog;

            case 26:
                return Resources.drawable.widget_notification;

            case 27:
                return Resources.drawable.widget_fragment;

            case 28:
                return Resources.drawable.widget_phone_auth;

            case 29:
                return Resources.drawable.component_dynamic_link;

            case 30:
                return Resources.drawable.component_fcm;

            case 31:
                return Resources.drawable.component_firebase_google;

            case 32:
                return Resources.drawable.component_firebase_admin;

            case 33:
                return Resources.drawable.component_fbads_banner;

            case 34:
                return Resources.drawable.component_fbads_interstitial;

            default:
                return ComponentsHandler.icon(type);
        }
    }

    public void buildClassInfo() {
        String typeName;
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                typeName = "Intent";
                break;

            case COMPONENT_TYPE_SHAREDPREF:
                typeName = "SharedPreferences";
                break;

            case COMPONENT_TYPE_CALENDAR:
                typeName = "Calendar";
                break;

            case COMPONENT_TYPE_VIBRATOR:
                typeName = "Vibrator";
                break;

            case COMPONENT_TYPE_TIMERTASK:
                typeName = "Timer";
                break;

            case COMPONENT_TYPE_FIREBASE:
                typeName = "FirebaseDB";
                break;

            case COMPONENT_TYPE_DIALOG:
                typeName = "Dialog";
                break;

            case COMPONENT_TYPE_MEDIAPLAYER:
                typeName = "MediaPlayer";
                break;

            case COMPONENT_TYPE_SOUNDPOOL:
                typeName = "SoundPool";
                break;

            case COMPONENT_TYPE_OBJECTANIMATOR:
                typeName = "ObjectAnimator";
                break;

            case COMPONENT_TYPE_GYROSCOPE:
                typeName = "Gyroscope";
                break;

            case COMPONENT_TYPE_FIREBASE_AUTH:
                typeName = "FirebaseAuth";
                break;

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                typeName = "InterstitialAd";
                break;

            case COMPONENT_TYPE_FIREBASE_STORAGE:
                typeName = "FirebaseStorage";
                break;

            case COMPONENT_TYPE_CAMERA:
                typeName = "Camera";
                break;

            case COMPONENT_TYPE_FILE_PICKER:
                typeName = "FilePicker";
                break;

            case COMPONENT_TYPE_REQUEST_NETWORK:
                typeName = "RequestNetwork";
                break;

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                typeName = "TextToSpeech";
                break;

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                typeName = "SpeechToText";
                break;

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                typeName = "BluetoothConnect";
                break;

            case COMPONENT_TYPE_LOCATION_MANAGER:
                typeName = "LocationManager";
                break;

            case 22:
                typeName = "RewardedVideoAd";
                break;

            case 23:
                typeName = "ProgressDialog";
                break;

            case 24:
                typeName = "DatePickerDialog";
                break;

            case 25:
                typeName = "TimePickerDialog";
                break;

            case 26:
                typeName = "Notification";
                break;

            case 27:
                typeName = "FragmentAdapter";
                break;

            case 28:
                typeName = "FirebasePhoneAuth";
                break;

            case 29:
                typeName = "FirebaseDynamicLink";
                break;

            case 30:
                typeName = "FirebaseCloudMessage";
                break;

            case 31:
                typeName = "FirebaseGoogleLogin";
                break;

            case 32:
                typeName = "OneSignal";
                break;

            case 33:
                typeName = "FBAdsBanner";
                break;

            case 34:
                typeName = "FBAdsInterstitial";
                break;

            default:
                typeName = ComponentsHandler.c(type);
                break;
        }
        classInfo = new Gx(typeName);
    }

    public void clearClassInfo() {
        classInfo = null;
    }

    public void copy(ComponentBean other) {
        type = other.type;
        componentId = other.componentId;
        param1 = other.param1;
        param2 = other.param2;
        param3 = other.param3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Gx getClassInfo() {
        if (classInfo == null) {
            buildClassInfo();
        }
        return classInfo;
    }

    public void print() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(componentId);
        dest.writeString(param1);
        dest.writeString(param2);
        dest.writeString(param3);
    }
}
