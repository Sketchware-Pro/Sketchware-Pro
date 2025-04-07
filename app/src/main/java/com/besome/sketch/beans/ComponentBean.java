package com.besome.sketch.beans;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import a.a.a.Gx;
import mod.hilal.saif.components.ComponentsHandler;
import pro.sketchware.R;

public class ComponentBean extends CollapsibleBean implements Parcelable {
    public static final Parcelable.Creator<ComponentBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public ComponentBean createFromParcel(Parcel source) {
            return new ComponentBean(source);
        }

        @Override
        public ComponentBean[] newArray(int size) {
            return new ComponentBean[size];
        }
    };

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
        type = other.readInt();
        componentId = other.readString();
        param1 = other.readString();
        param2 = other.readString();
        param3 = other.readString();
    }

    public static String getComponentDocsUrlByTypeName(int type) {
        return switch (type) {
            case COMPONENT_TYPE_INTENT -> "https://docs.sketchware.io/docs/component-intent.html";
            case COMPONENT_TYPE_SHAREDPREF ->
                    "https://docs.sketchware.io/docs/component-shared-preference.html";
            case COMPONENT_TYPE_CALENDAR ->
                    "https://docs.sketchware.io/docs/component-calendar.html";
            case COMPONENT_TYPE_VIBRATOR ->
                    "https://docs.sketchware.io/docs/component-vibrator.html";
            case COMPONENT_TYPE_TIMERTASK -> "https://docs.sketchware.io/docs/component-timer.html";
            case COMPONENT_TYPE_FIREBASE ->
                    "https://docs.sketchware.io/docs/component-firebase-database.html";
            case COMPONENT_TYPE_DIALOG -> "https://docs.sketchware.io/docs/component-dialog.html";
            case COMPONENT_TYPE_MEDIAPLAYER ->
                    "https://docs.sketchware.io/docs/component-mediaplayer.html";
            case COMPONENT_TYPE_SOUNDPOOL ->
                    "https://docs.sketchware.io/docs/component-soundpool.html";
            case COMPONENT_TYPE_OBJECTANIMATOR ->
                    "https://docs.sketchware.io/docs/component-object-animator.html";
            case COMPONENT_TYPE_GYROSCOPE ->
                    "https://docs.sketchware.io/docs/component-gyroscope.html";
            case COMPONENT_TYPE_FIREBASE_AUTH ->
                    "https://docs.sketchware.io/docs/component-firebase-auth.html";
            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE ->
                    "https://sketchware-pro.vercel.app/docs/components/Firebase/cloud-messaging";
            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS ->
                    "https://sketchware-pro.vercel.app/docs/components/Firebase/dynamic-links";
            case COMPONENT_TYPE_INTERSTITIAL_AD, COMPONENT_TYPE_REQUEST_NETWORK,
                 COMPONENT_TYPE_TEXT_TO_SPEECH, COMPONENT_TYPE_SPEECH_TO_TEXT,
                 COMPONENT_TYPE_BLUETOOTH_CONNECT, COMPONENT_TYPE_LOCATION_MANAGER ->
                // sad :c
                    "";
            case COMPONENT_TYPE_FIREBASE_STORAGE ->
                    "https://docs.sketchware.io/docs/component-firebase-storage.html";
            case COMPONENT_TYPE_CAMERA -> "https://docs.sketchware.io/docs/component-camera.html";
            case COMPONENT_TYPE_FILE_PICKER ->
                    "https://docs.sketchware.io/docs/component-filepicker.html";
            default -> ComponentsHandler.docs(type);
        };
    }

    /**
     * @param context Can be null, as it's not used
     */
    public static String getComponentName(Context context, int type) {
        return switch (type) {
            case COMPONENT_TYPE_INTENT -> "Intent";
            case COMPONENT_TYPE_SHAREDPREF -> "SharedPreferences";
            case COMPONENT_TYPE_CALENDAR -> "Calendar";
            case COMPONENT_TYPE_VIBRATOR -> "Vibrator";
            case COMPONENT_TYPE_TIMERTASK -> "Timer";
            case COMPONENT_TYPE_FIREBASE -> "Firebase DB";
            case COMPONENT_TYPE_DIALOG -> "Dialog";
            case COMPONENT_TYPE_MEDIAPLAYER -> "MediaPlayer";
            case COMPONENT_TYPE_SOUNDPOOL -> "SoundPool";
            case COMPONENT_TYPE_OBJECTANIMATOR -> "ObjectAnimator";
            case COMPONENT_TYPE_GYROSCOPE -> "Gyroscope";
            case COMPONENT_TYPE_FIREBASE_AUTH -> "Firebase Auth";
            case COMPONENT_TYPE_INTERSTITIAL_AD -> "Interstitial Ad";
            case COMPONENT_TYPE_FIREBASE_STORAGE -> "Firebase Storage";
            case COMPONENT_TYPE_CAMERA -> "Camera";
            case COMPONENT_TYPE_FILE_PICKER -> "FilePicker";
            case COMPONENT_TYPE_REQUEST_NETWORK -> "RequestNetwork";
            case COMPONENT_TYPE_TEXT_TO_SPEECH -> "TextToSpeech";
            case COMPONENT_TYPE_SPEECH_TO_TEXT -> "SpeechToText";
            case COMPONENT_TYPE_BLUETOOTH_CONNECT -> "BluetoothConnect";
            case COMPONENT_TYPE_LOCATION_MANAGER -> "LocationManager";
            case COMPONENT_TYPE_REWARDED_VIDEO_AD -> "RewardedVideoAd";
            case COMPONENT_TYPE_PROGRESS_DIALOG -> "ProgressDialog";
            case COMPONENT_TYPE_DATE_PICKER_DIALOG -> "DatePickerDialog";
            case COMPONENT_TYPE_TIME_PICKER_DIALOG -> "TimePickerDialog";
            case COMPONENT_TYPE_NOTIFICATION -> "Notification";
            case COMPONENT_TYPE_FRAGMENT_ADAPTER -> "FragmentAdapter";
            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE -> "PhoneAuth";
            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS -> "Dynamic Link";
            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE -> "Cloud Message";
            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN -> "Google Login";
            case COMPONENT_TYPE_ONESIGNAL -> "OneSignal";
            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER -> "Facebook Ads Banner";
            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL -> "Facebook Ads Interstitial";
            default -> ComponentsHandler.name(type);
        };
    }

    public static int getComponentTypeByTypeName(String typeName) {
        return switch (typeName) {
            case "Intent" -> COMPONENT_TYPE_INTENT;
            case "File" -> COMPONENT_TYPE_SHAREDPREF;
            case "Calendar" -> COMPONENT_TYPE_CALENDAR;
            case "Vibrator" -> COMPONENT_TYPE_VIBRATOR;
            case "Timer" -> COMPONENT_TYPE_TIMERTASK;
            case "FirebaseDB" -> COMPONENT_TYPE_FIREBASE;
            case "Dialog" -> COMPONENT_TYPE_DIALOG;
            case "MediaPlayer" -> COMPONENT_TYPE_MEDIAPLAYER;
            case "SoundPool" -> COMPONENT_TYPE_SOUNDPOOL;
            case "ObjectAnimator" -> COMPONENT_TYPE_OBJECTANIMATOR;
            case "Gyroscope" -> COMPONENT_TYPE_GYROSCOPE;
            case "FirebaseAuth" -> COMPONENT_TYPE_FIREBASE_AUTH;
            case "InterstitialAd" -> COMPONENT_TYPE_INTERSTITIAL_AD;
            case "FirebaseStorage" -> COMPONENT_TYPE_FIREBASE_STORAGE;
            case "Camera" -> COMPONENT_TYPE_CAMERA;
            case "FilePicker" -> COMPONENT_TYPE_FILE_PICKER;
            case "RequestNetwork" -> COMPONENT_TYPE_REQUEST_NETWORK;
            case "TextToSpeech" -> COMPONENT_TYPE_TEXT_TO_SPEECH;
            case "SpeechToText" -> COMPONENT_TYPE_SPEECH_TO_TEXT;
            case "BluetoothConnect" -> COMPONENT_TYPE_BLUETOOTH_CONNECT;
            case "LocationManager" -> COMPONENT_TYPE_LOCATION_MANAGER;
            case "RewardedVideoAd" -> COMPONENT_TYPE_REWARDED_VIDEO_AD;
            case "ProgressDialog" -> COMPONENT_TYPE_PROGRESS_DIALOG;
            case "DatePickerDialog" -> COMPONENT_TYPE_DATE_PICKER_DIALOG;
            case "TimePickerDialog" -> COMPONENT_TYPE_TIME_PICKER_DIALOG;
            case "Notification" -> COMPONENT_TYPE_NOTIFICATION;
            case "FragmentAdapter" -> COMPONENT_TYPE_FRAGMENT_ADAPTER;
            case "FirebasePhoneAuth" -> COMPONENT_TYPE_FIREBASE_AUTH_PHONE;
            case "FirebaseDynamicLink" -> COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS;
            case "FirebaseCloudMessage" -> COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE;
            case "FirebaseGoogleLogin" -> COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN;
            case "OneSignal" -> COMPONENT_TYPE_ONESIGNAL;
            case "FBAdsBanner" -> COMPONENT_TYPE_FACEBOOK_ADS_BANNER;
            case "FBAdsInterstitial" -> COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL;
            default -> ComponentsHandler.id(typeName);
        };
    }

    public static String getComponentTypeName(int type) {
        return switch (type) {
            case COMPONENT_TYPE_INTENT -> "Intent";
            case COMPONENT_TYPE_SHAREDPREF -> "File";
            case COMPONENT_TYPE_CALENDAR -> "Calendar";
            case COMPONENT_TYPE_VIBRATOR -> "Vibrator";
            case COMPONENT_TYPE_TIMERTASK -> "Timer";
            case COMPONENT_TYPE_FIREBASE -> "FirebaseDB";
            case COMPONENT_TYPE_DIALOG -> "Dialog";
            case COMPONENT_TYPE_MEDIAPLAYER -> "MediaPlayer";
            case COMPONENT_TYPE_SOUNDPOOL -> "SoundPool";
            case COMPONENT_TYPE_OBJECTANIMATOR -> "ObjectAnimator";
            case COMPONENT_TYPE_GYROSCOPE -> "Gyroscope";
            case COMPONENT_TYPE_FIREBASE_AUTH -> "FirebaseAuth";
            case COMPONENT_TYPE_INTERSTITIAL_AD -> "InterstitialAd";
            case COMPONENT_TYPE_FIREBASE_STORAGE -> "FirebaseStorage";
            case COMPONENT_TYPE_CAMERA -> "Camera";
            case COMPONENT_TYPE_FILE_PICKER -> "FilePicker";
            case COMPONENT_TYPE_REQUEST_NETWORK -> "RequestNetwork";
            case COMPONENT_TYPE_TEXT_TO_SPEECH -> "TextToSpeech";
            case COMPONENT_TYPE_SPEECH_TO_TEXT -> "SpeechToText";
            case COMPONENT_TYPE_BLUETOOTH_CONNECT -> "BluetoothConnect";
            case COMPONENT_TYPE_LOCATION_MANAGER -> "LocationManager";
            case COMPONENT_TYPE_REWARDED_VIDEO_AD -> "RewardedVideoAd";
            case COMPONENT_TYPE_PROGRESS_DIALOG -> "ProgressDialog";
            case COMPONENT_TYPE_DATE_PICKER_DIALOG -> "DatePickerDialog";
            case COMPONENT_TYPE_TIME_PICKER_DIALOG -> "TimePickerDialog";
            case COMPONENT_TYPE_NOTIFICATION -> "Notification";
            case COMPONENT_TYPE_FRAGMENT_ADAPTER -> "FragmentAdapter";
            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE -> "FirebasePhoneAuth";
            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS -> "FirebaseDynamicLink";
            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE -> "FirebaseCloudMessage";
            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN -> "FirebaseGoogleLogin";
            case COMPONENT_TYPE_ONESIGNAL -> "OneSignal";
            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER -> "FBAdsBanner";
            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL -> "FBAdsInterstitial";
            default -> ComponentsHandler.typeName(type);
        };
    }

    public static Parcelable.Creator<ComponentBean> getCreator() {
        return CREATOR;
    }

    public static int getDescStrResource(int type) {
        return switch (type) {
            case COMPONENT_TYPE_INTENT -> R.string.component_description_intent;
            case COMPONENT_TYPE_SHAREDPREF -> R.string.component_description_file;
            case COMPONENT_TYPE_CALENDAR -> R.string.component_description_calendar;
            case COMPONENT_TYPE_VIBRATOR -> R.string.component_description_vibrator;
            case COMPONENT_TYPE_TIMERTASK -> R.string.component_description_timer;
            case COMPONENT_TYPE_FIREBASE ->
                    R.string.design_library_firebase_description_about_firebase;
            case COMPONENT_TYPE_DIALOG -> R.string.component_description_dialog;
            case COMPONENT_TYPE_MEDIAPLAYER -> R.string.component_description_mediaplayer;
            case COMPONENT_TYPE_SOUNDPOOL -> R.string.component_description_soundpool;
            case COMPONENT_TYPE_OBJECTANIMATOR -> R.string.component_description_objectanimator;
            case COMPONENT_TYPE_GYROSCOPE -> R.string.component_description_gyrosope;
            case COMPONENT_TYPE_FIREBASE_AUTH -> R.string.component_description_firebase_auth;
            case COMPONENT_TYPE_INTERSTITIAL_AD -> R.string.component_description_interstitial_ad;
            case COMPONENT_TYPE_FIREBASE_STORAGE -> R.string.component_description_firebase_storage;
            case COMPONENT_TYPE_CAMERA -> R.string.component_description_camera;
            case COMPONENT_TYPE_FILE_PICKER -> R.string.component_description_file_picker;
            case COMPONENT_TYPE_REQUEST_NETWORK -> R.string.component_description_request_network;
            case COMPONENT_TYPE_TEXT_TO_SPEECH -> R.string.component_description_text_to_speech;
            case COMPONENT_TYPE_SPEECH_TO_TEXT -> R.string.component_description_speech_to_text;
            case COMPONENT_TYPE_BLUETOOTH_CONNECT ->
                    R.string.component_description_bluetooth_connect;
            case COMPONENT_TYPE_LOCATION_MANAGER -> R.string.component_description_location_manager;
            case COMPONENT_TYPE_REWARDED_VIDEO_AD -> R.string.component_description_video_ad;
            case COMPONENT_TYPE_PROGRESS_DIALOG -> R.string.component_description_progress_dialog;
            case COMPONENT_TYPE_DATE_PICKER_DIALOG ->
                    R.string.component_description_date_picker_dialog;
            case COMPONENT_TYPE_TIME_PICKER_DIALOG ->
                    R.string.component_description_time_picker_dialog;
            case COMPONENT_TYPE_NOTIFICATION -> R.string.component_description_notification;
            case COMPONENT_TYPE_FRAGMENT_ADAPTER -> R.string.component_description_fragment_adapter;
            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE -> R.string.component_description_fb_phone_auth;
            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS -> R.string.component_description_fb_dynamic;
            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE -> R.string.component_description_fb_fcm;
            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN ->
                    R.string.component_description_fb_google;
            case COMPONENT_TYPE_ONESIGNAL -> R.string.component_description_fb_admin;
            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER -> R.string.component_description_fb_ads_banner;
            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL ->
                    R.string.component_description_fb_ads_interstitial;
            default -> 0;
        };
    }

    public static int getIconResource(int type) {
        return switch (type) {
            case COMPONENT_TYPE_INTENT -> R.drawable.ic_mtrl_version_control;
            case COMPONENT_TYPE_SHAREDPREF -> R.drawable.ic_mtrl_save;
            case COMPONENT_TYPE_CALENDAR -> R.drawable.ic_mtrl_calendar;
            case COMPONENT_TYPE_VIBRATOR -> R.drawable.ic_mtrl_vibration;
            case COMPONENT_TYPE_TIMERTASK -> R.drawable.ic_mtrl_timer;
            case COMPONENT_TYPE_FIREBASE_AUTH -> R.drawable.ic_mtrl_firebase_auth;
            case COMPONENT_TYPE_FIREBASE_STORAGE -> R.drawable.ic_mtrl_firebase_storage;
            case COMPONENT_TYPE_FIREBASE -> R.drawable.ic_mtrl_firebase_rtdb;
            case COMPONENT_TYPE_DIALOG -> R.drawable.ic_mtrl_dialog;
            case COMPONENT_TYPE_MEDIAPLAYER -> R.drawable.ic_mtrl_video;
            case COMPONENT_TYPE_SOUNDPOOL -> R.drawable.ic_mtrl_volume;
            case COMPONENT_TYPE_OBJECTANIMATOR -> R.drawable.ic_mtrl_animation;
            case COMPONENT_TYPE_GYROSCOPE -> R.drawable.ic_mtrl_sensors;
            case COMPONENT_TYPE_INTERSTITIAL_AD -> R.drawable.ic_mtrl_admob;
            case COMPONENT_TYPE_CAMERA -> R.drawable.ic_mtrl_camera;
            case COMPONENT_TYPE_FILE_PICKER -> R.drawable.ic_mtrl_file;
            case COMPONENT_TYPE_REQUEST_NETWORK -> R.drawable.ic_mtrl_wifi;
            case COMPONENT_TYPE_TEXT_TO_SPEECH -> R.drawable.ic_mtrl_tts;
            case COMPONENT_TYPE_SPEECH_TO_TEXT -> R.drawable.ic_mtrl_stt;
            case COMPONENT_TYPE_BLUETOOTH_CONNECT -> R.drawable.ic_mtrl_bluetooth;
            case COMPONENT_TYPE_LOCATION_MANAGER -> R.drawable.ic_mtrl_location;
            case COMPONENT_TYPE_REWARDED_VIDEO_AD -> R.drawable.ic_mtrl_screen_play;
            case COMPONENT_TYPE_PROGRESS_DIALOG -> R.drawable.ic_mtrl_progress;
            case COMPONENT_TYPE_DATE_PICKER_DIALOG -> R.drawable.ic_mtrl_calendar_add;
            case COMPONENT_TYPE_TIME_PICKER_DIALOG -> R.drawable.ic_mtrl_time;
            case COMPONENT_TYPE_NOTIFICATION -> R.drawable.ic_mtrl_notifications;
            case COMPONENT_TYPE_FRAGMENT_ADAPTER -> R.drawable.ic_mtrl_viewpager;
            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE -> R.drawable.ic_mtrl_firebase_sms;
            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS -> R.drawable.ic_mtrl_firebase_dl;
            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE -> R.drawable.ic_mtrl_firebase_cloud;
            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN -> R.drawable.ic_mtrl_firebase_google;
            case COMPONENT_TYPE_ONESIGNAL -> R.drawable.ic_mtrl_firebase_onesignal;
            case 36 -> R.drawable.ic_mtrl_sync;
            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER -> R.drawable.ic_mtrl_fbads_banner;
            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL -> R.drawable.ic_mtrl_fbads_banner;

            default -> ComponentsHandler.icon(type);
        };
    }

    public void buildClassInfo() {
        String typeName = switch (type) {
            case COMPONENT_TYPE_INTENT -> "Intent";
            case COMPONENT_TYPE_SHAREDPREF -> "SharedPreferences";
            case COMPONENT_TYPE_CALENDAR -> "Calendar";
            case COMPONENT_TYPE_VIBRATOR -> "Vibrator";
            case COMPONENT_TYPE_TIMERTASK -> "Timer";
            case COMPONENT_TYPE_FIREBASE -> "FirebaseDB";
            case COMPONENT_TYPE_DIALOG -> "Dialog";
            case COMPONENT_TYPE_MEDIAPLAYER -> "MediaPlayer";
            case COMPONENT_TYPE_SOUNDPOOL -> "SoundPool";
            case COMPONENT_TYPE_OBJECTANIMATOR -> "ObjectAnimator";
            case COMPONENT_TYPE_GYROSCOPE -> "Gyroscope";
            case COMPONENT_TYPE_FIREBASE_AUTH -> "FirebaseAuth";
            case COMPONENT_TYPE_INTERSTITIAL_AD -> "InterstitialAd";
            case COMPONENT_TYPE_FIREBASE_STORAGE -> "FirebaseStorage";
            case COMPONENT_TYPE_CAMERA -> "Camera";
            case COMPONENT_TYPE_FILE_PICKER -> "FilePicker";
            case COMPONENT_TYPE_REQUEST_NETWORK -> "RequestNetwork";
            case COMPONENT_TYPE_TEXT_TO_SPEECH -> "TextToSpeech";
            case COMPONENT_TYPE_SPEECH_TO_TEXT -> "SpeechToText";
            case COMPONENT_TYPE_BLUETOOTH_CONNECT -> "BluetoothConnect";
            case COMPONENT_TYPE_LOCATION_MANAGER -> "LocationManager";
            case COMPONENT_TYPE_REWARDED_VIDEO_AD -> "RewardedVideoAd";
            case COMPONENT_TYPE_PROGRESS_DIALOG -> "ProgressDialog";
            case COMPONENT_TYPE_DATE_PICKER_DIALOG -> "DatePickerDialog";
            case COMPONENT_TYPE_TIME_PICKER_DIALOG -> "TimePickerDialog";
            case COMPONENT_TYPE_NOTIFICATION -> "Notification";
            case COMPONENT_TYPE_FRAGMENT_ADAPTER -> "FragmentAdapter";
            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE -> "FirebasePhoneAuth";
            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS -> "FirebaseDynamicLink";
            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE -> "FirebaseCloudMessage";
            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN -> "FirebaseGoogleLogin";
            case COMPONENT_TYPE_ONESIGNAL -> "OneSignal";
            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER -> "FBAdsBanner";
            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL -> "FBAdsInterstitial";
            default -> ComponentsHandler.c(type);
        };
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
