package com.besome.sketch.beans;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.sketchware.remod.R;

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
    public static final int COMPONENT_TYPE_REWARDED_VIDEO_AD = 22;
    public static final int COMPONENT_TYPE_PROGRESS_DIALOG = 23;
    public static final int COMPONENT_TYPE_DATE_PICKER_DIALOG = 24;
    public static final int COMPONENT_TYPE_TIME_PICKER_DIALOG = 25;
    public static final int COMPONENT_TYPE_NOTIFICATION = 26;
    public static final int COMPONENT_TYPE_FRAGMENT_ADAPTER = 27;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH_PHONE = 28;
    public static final int COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS = 29;
    public static final int COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE = 30;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN = 31;
    public static final int COMPONENT_TYPE_ONESIGNAL = 32;
    public static final int COMPONENT_TYPE_FACEBOOK_ADS_BANNER = 33;
    public static final int COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL = 34;

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

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                return "https://sketchware-pro.vercel.app/docs/components/Firebase/cloud-messaging";

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                return "https://sketchware-pro.vercel.app/docs/components/Firebase/dynamic-links";

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

            case COMPONENT_TYPE_REWARDED_VIDEO_AD:
                return "RewardedVideoAd";

            case COMPONENT_TYPE_PROGRESS_DIALOG:
                return "ProgressDialog";

            case COMPONENT_TYPE_DATE_PICKER_DIALOG:
                return "DatePickerDialog";

            case COMPONENT_TYPE_TIME_PICKER_DIALOG:
                return "TimePickerDialog";

            case COMPONENT_TYPE_NOTIFICATION:
                return "Notification";

            case COMPONENT_TYPE_FRAGMENT_ADAPTER:
                return "FragmentAdapter";

            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE:
                return "PhoneAuth";

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                return "Dynamic Link";

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                return "Cloud Message";

            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                return "Google Login";

            case COMPONENT_TYPE_ONESIGNAL:
                return "OneSignal";

            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER:
                return "Facebook Ads Banner";

            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL:
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
                return COMPONENT_TYPE_REWARDED_VIDEO_AD;

            case "ProgressDialog":
                return COMPONENT_TYPE_PROGRESS_DIALOG;

            case "DatePickerDialog":
                return COMPONENT_TYPE_DATE_PICKER_DIALOG;

            case "TimePickerDialog":
                return COMPONENT_TYPE_TIME_PICKER_DIALOG;

            case "Notification":
                return COMPONENT_TYPE_NOTIFICATION;

            case "FragmentAdapter":
                return COMPONENT_TYPE_FRAGMENT_ADAPTER;

            case "FirebasePhoneAuth":
                return COMPONENT_TYPE_FIREBASE_AUTH_PHONE;

            case "FirebaseDynamicLink":
                return COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS;

            case "FirebaseCloudMessage":
                return COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE;

            case "FirebaseGoogleLogin":
                return COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN;

            case "OneSignal":
                return COMPONENT_TYPE_ONESIGNAL;

            case "FBAdsBanner":
                return COMPONENT_TYPE_FACEBOOK_ADS_BANNER;

            case "FBAdsInterstitial":
                return COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL;

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

            case COMPONENT_TYPE_REWARDED_VIDEO_AD:
                return "RewardedVideoAd";

            case COMPONENT_TYPE_PROGRESS_DIALOG:
                return "ProgressDialog";

            case COMPONENT_TYPE_DATE_PICKER_DIALOG:
                return "DatePickerDialog";

            case COMPONENT_TYPE_TIME_PICKER_DIALOG:
                return "TimePickerDialog";

            case COMPONENT_TYPE_NOTIFICATION:
                return "Notification";

            case COMPONENT_TYPE_FRAGMENT_ADAPTER:
                return "FragmentAdapter";

            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE:
                return "FirebasePhoneAuth";

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                return "FirebaseDynamicLink";

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                return "FirebaseCloudMessage";

            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                return "FirebaseGoogleLogin";

            case COMPONENT_TYPE_ONESIGNAL:
                return "OneSignal";

            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER:
                return "FBAdsBanner";

            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL:
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
                return R.string.component_description_intent;

            case COMPONENT_TYPE_SHAREDPREF:
                return R.string.component_description_file;

            case COMPONENT_TYPE_CALENDAR:
                return R.string.component_description_calendar;

            case COMPONENT_TYPE_VIBRATOR:
                return R.string.component_description_vibrator;

            case COMPONENT_TYPE_TIMERTASK:
                return R.string.component_description_timer;

            case COMPONENT_TYPE_FIREBASE:
                return R.string.design_library_firebase_description_about_firebase;

            case COMPONENT_TYPE_DIALOG:
                return R.string.component_description_dialog;

            case COMPONENT_TYPE_MEDIAPLAYER:
                return R.string.component_description_mediaplayer;

            case COMPONENT_TYPE_SOUNDPOOL:
                return R.string.component_description_soundpool;

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return R.string.component_description_objectanimator;

            case COMPONENT_TYPE_GYROSCOPE:
                return R.string.component_description_gyrosope;

            case COMPONENT_TYPE_FIREBASE_AUTH:
                return R.string.component_description_firebase_auth;

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return R.string.component_description_interstitial_ad;

            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return R.string.component_description_firebase_storage;

            case COMPONENT_TYPE_CAMERA:
                return R.string.component_description_camera;

            case COMPONENT_TYPE_FILE_PICKER:
                return R.string.component_description_file_picker;

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return R.string.component_description_request_network;

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return R.string.component_description_text_to_speech;

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return R.string.component_description_speech_to_text;

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return R.string.component_description_bluetooth_connect;

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return R.string.component_description_location_manager;

            case COMPONENT_TYPE_REWARDED_VIDEO_AD:
                return R.string.component_description_video_ad;

            case COMPONENT_TYPE_PROGRESS_DIALOG:
                return R.string.component_description_progress_dialog;

            case COMPONENT_TYPE_DATE_PICKER_DIALOG:
                return R.string.component_description_date_picker_dialog;

            case COMPONENT_TYPE_TIME_PICKER_DIALOG:
                return R.string.component_description_time_picker_dialog;

            case COMPONENT_TYPE_NOTIFICATION:
                return R.string.component_description_notification;

            case COMPONENT_TYPE_FRAGMENT_ADAPTER:
                return R.string.component_description_fragment_adapter;

            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE:
                return R.string.component_description_fb_phone_auth;

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                return R.string.component_description_fb_dynamic;

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                return R.string.component_description_fb_fcm;

            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                return R.string.component_description_fb_google;

            case COMPONENT_TYPE_ONESIGNAL:
                return R.string.component_description_fb_admin;

            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER:
                return R.string.component_description_fb_ads_banner;

            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL:
                return R.string.component_description_fb_ads_interstitial;

            default:
                return 0;
        }
    }

    public static int getIconResource(int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return R.drawable.widget_intent;

            case COMPONENT_TYPE_SHAREDPREF:
                return R.drawable.widget_shared_preference;

            case COMPONENT_TYPE_CALENDAR:
                return R.drawable.widget_calendar;

            case COMPONENT_TYPE_VIBRATOR:
                return R.drawable.widget_vibrator;

            case COMPONENT_TYPE_TIMERTASK:
                return R.drawable.widget_timer;

            case COMPONENT_TYPE_FIREBASE:
            case COMPONENT_TYPE_FIREBASE_AUTH:
            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return R.drawable.widget_firebase;

            case COMPONENT_TYPE_DIALOG:
                return R.drawable.widget_alertdialog;

            case COMPONENT_TYPE_MEDIAPLAYER:
                return R.drawable.widget_mediaplayer;

            case COMPONENT_TYPE_SOUNDPOOL:
                return R.drawable.widget_soundpool;

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return R.drawable.widget_objectanimator;

            case COMPONENT_TYPE_GYROSCOPE:
                return R.drawable.widget_gyroscope;

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return R.drawable.widget_admob;

            case COMPONENT_TYPE_CAMERA:
                return R.drawable.widget_camera;

            case COMPONENT_TYPE_FILE_PICKER:
                return R.drawable.widget_file;

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return R.drawable.widget_network_request;

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return R.drawable.widget_text_to_speech;

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return R.drawable.widget_speech_to_text;

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return R.drawable.widget_bluetooth;

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return R.drawable.widget_location;

            case COMPONENT_TYPE_REWARDED_VIDEO_AD:
                return R.drawable.widget_media_controller;

            case COMPONENT_TYPE_PROGRESS_DIALOG:
                return R.drawable.widget_progress_dialog;

            case COMPONENT_TYPE_DATE_PICKER_DIALOG:
                return R.drawable.widget_date_picker_dialog;

            case COMPONENT_TYPE_TIME_PICKER_DIALOG:
                return R.drawable.widget_time_picker_dialog;

            case COMPONENT_TYPE_NOTIFICATION:
                return R.drawable.widget_notification;

            case COMPONENT_TYPE_FRAGMENT_ADAPTER:
                return R.drawable.widget_fragment;

            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE:
                return R.drawable.widget_phone_auth;

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                return R.drawable.component_dynamic_link;

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                return R.drawable.component_fcm;

            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                return R.drawable.component_firebase_google;

            case COMPONENT_TYPE_ONESIGNAL:
                return R.drawable.component_firebase_admin;

            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER:
                return R.drawable.component_fbads_banner;

            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL:
                return R.drawable.component_fbads_interstitial;

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

            case COMPONENT_TYPE_REWARDED_VIDEO_AD:
                typeName = "RewardedVideoAd";
                break;

            case COMPONENT_TYPE_PROGRESS_DIALOG:
                typeName = "ProgressDialog";
                break;

            case COMPONENT_TYPE_DATE_PICKER_DIALOG:
                typeName = "DatePickerDialog";
                break;

            case COMPONENT_TYPE_TIME_PICKER_DIALOG:
                typeName = "TimePickerDialog";
                break;

            case COMPONENT_TYPE_NOTIFICATION:
                typeName = "Notification";
                break;

            case COMPONENT_TYPE_FRAGMENT_ADAPTER:
                typeName = "FragmentAdapter";
                break;

            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE:
                typeName = "FirebasePhoneAuth";
                break;

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                typeName = "FirebaseDynamicLink";
                break;

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                typeName = "FirebaseCloudMessage";
                break;

            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                typeName = "FirebaseGoogleLogin";
                break;

            case COMPONENT_TYPE_ONESIGNAL:
                typeName = "OneSignal";
                break;

            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER:
                typeName = "FBAdsBanner";
                break;

            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL:
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
