package com.besome.sketch.beans;

import a.a.a.Gx;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import mod.hilal.saif.components.ComponentsHandler;

public class ComponentBean extends CollapsibleBean implements Parcelable {
    public static final int COMPONENT_TYPE_BLUETOOTH_CONNECT = 20;
    public static final int COMPONENT_TYPE_CALENDAR = 3;
    public static final int COMPONENT_TYPE_CAMERA = 15;
    public static final int COMPONENT_TYPE_DIALOG = 7;
    public static final int COMPONENT_TYPE_FILE_PICKER = 16;
    public static final int COMPONENT_TYPE_FIREBASE = 6;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH = 12;
    public static final int COMPONENT_TYPE_FIREBASE_STORAGE = 14;
    public static final int COMPONENT_TYPE_GYROSCOPE = 11;
    public static final int COMPONENT_TYPE_INTENT = 1;
    public static final int COMPONENT_TYPE_INTERSTITIAL_AD = 13;
    public static final int COMPONENT_TYPE_LOCATION_MANAGER = 21;
    public static final int COMPONENT_TYPE_MEDIAPLAYER = 8;
    public static final int COMPONENT_TYPE_OBJECTANIMATOR = 10;
    public static final int COMPONENT_TYPE_REQUEST_NETWORK = 17;
    public static final int COMPONENT_TYPE_SHAREDPREF = 2;
    public static final int COMPONENT_TYPE_SOUNDPOOL = 9;
    public static final int COMPONENT_TYPE_SPEECH_TO_TEXT = 19;
    public static final int COMPONENT_TYPE_TEXT_TO_SPEECH = 18;
    public static final int COMPONENT_TYPE_TIMERTASK = 5;
    public static final int COMPONENT_TYPE_VIBRATOR = 4;
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        /* class com.besome.sketch.beans.ComponentBean.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public Object createFromParcel(Parcel parcel) {
            return new ComponentBean(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Object[] newArray(int i) {
            return new ComponentBean[i];
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

    public ComponentBean(int i) {
        this(i, "");
    }

    public ComponentBean(int i, String str) {
        this(i, str, "", "");
    }

    public ComponentBean(int i, String str, String str2) {
        this(i, str, str2, "", "");
    }

    public ComponentBean(int i, String str, String str2, String str3) {
        this(i, str, str2, str3, "");
    }

    public ComponentBean(int i, String str, String str2, String str3, String str4) {
        this.param1 = "";
        this.param2 = "";
        this.param3 = "";
        this.type = i;
        this.componentId = str;
        this.param1 = str2;
        this.param2 = str3;
        this.param3 = str4;
    }

    public ComponentBean(Parcel parcel) {
        this.param1 = "";
        this.param2 = "";
        this.param3 = "";
        this.type = parcel.readInt();
        this.componentId = parcel.readString();
        this.param1 = parcel.readString();
        this.param2 = parcel.readString();
        this.param3 = parcel.readString();
    }

    public static String getComponentDocsUrlByTypeName(int i) {
        switch (i) {
            case 1:
                return "https://docs.sketchware.io/docs/component-intent.html";
            case 2:
                return "https://docs.sketchware.io/docs/component-shared-preference.html";
            case 3:
                return "https://docs.sketchware.io/docs/component-calendar.html";
            case 4:
                return "https://docs.sketchware.io/docs/component-vibrator.html";
            case 5:
                return "https://docs.sketchware.io/docs/component-timer.html";
            case 6:
                return "https://docs.sketchware.io/docs/component-firebase-database.html";
            case 7:
                return "https://docs.sketchware.io/docs/component-dialog.html";
            case 8:
                return "https://docs.sketchware.io/docs/component-mediaplayer.html";
            case 9:
                return "https://docs.sketchware.io/docs/component-soundpool.html";
            case 10:
                return "https://docs.sketchware.io/docs/component-object-animator.html";
            case 11:
                return "https://docs.sketchware.io/docs/component-gyroscope.html";
            case 12:
                return "https://docs.sketchware.io/docs/component-firebase-auth.html";
            case 13:
                return "";
            case 14:
                return "https://docs.sketchware.io/docs/component-firebase-storage.html";
            case 15:
                return "https://docs.sketchware.io/docs/component-camera.html";
            case 16:
                return "https://docs.sketchware.io/docs/component-filepicker.html";
            default:
                return ComponentsHandler.docs(i);
        }
    }

    public static String getComponentName(Context context, int i) {
        switch (i) {
            case 1:
                return "Intent";
            case 2:
                return "SharedPreferences";
            case 3:
                return "Calendar";
            case 4:
                return "Vibrator";
            case 5:
                return "Timer";
            case 6:
                return "Firebase DB";
            case 7:
                return "Dialog";
            case 8:
                return "MediaPlayer";
            case 9:
                return "SoundPool";
            case 10:
                return "ObjectAnimator";
            case 11:
                return "Gyroscope";
            case 12:
                return "Firebase Auth";
            case 13:
                return "Interstitial Ad";
            case 14:
                return "Firebase Storage";
            case 15:
                return "Camera";
            case 16:
                return "FilePicker";
            case 17:
                return "RequestNetwork";
            case 18:
                return "TextToSpeech";
            case 19:
                return "SpeechToText";
            case 20:
                return "BluetoothConnect";
            case 21:
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
                return ComponentsHandler.name(i);
        }
    }

    public static int getComponentTypeByTypeName(String str) {
        switch (str.hashCode()) {
            case -2099895620:
                if (str.equals("Intent")) {
                    return 1;
                }
                break;
            case -1965257499:
                if (str.equals("Gyroscope")) {
                    return 11;
                }
                break;
            case -1908172204:
                if (str.equals("FirebaseStorage")) {
                    return 14;
                }
                break;
            case -1884914774:
                if (str.equals("TextToSpeech")) {
                    return 18;
                }
                break;
            case -1810281568:
                if (str.equals("FBAdsInterstitial")) {
                    return 34;
                }
                break;
            case -1231229991:
                if (str.equals("FirebaseCloudMessage")) {
                    return 30;
                }
                break;
            case -1042830870:
                if (str.equals("SpeechToText")) {
                    return 19;
                }
                break;
            case -1014653761:
                if (str.equals("RequestNetwork")) {
                    return 17;
                }
                break;
            case -699448178:
                if (str.equals("OneSignal")) {
                    return 32;
                }
                break;
            case -596330166:
                if (str.equals("FilePicker")) {
                    return 16;
                }
                break;
            case -294086120:
                if (str.equals("LocationManager")) {
                    return 21;
                }
                break;
            case -113680546:
                if (str.equals("Calendar")) {
                    return 3;
                }
                break;
            case -105717264:
                if (str.equals("RewardedVideoAd")) {
                    return 22;
                }
                break;
            case 2189724:
                if (str.equals("File")) {
                    return 2;
                }
                break;
            case 80811813:
                if (str.equals("Timer")) {
                    return 5;
                }
                break;
            case 191354283:
                if (str.equals("SoundPool")) {
                    return 9;
                }
                break;
            case 225459311:
                if (str.equals("FirebaseAuth")) {
                    return 12;
                }
                break;
            case 313126659:
                if (str.equals("TimePickerDialog")) {
                    return 25;
                }
                break;
            case 320151695:
                if (str.equals("InterstitialAd")) {
                    return 13;
                }
                break;
            case 610585248:
                if (str.equals("FBAdsBanner")) {
                    return 33;
                }
                break;
            case 759553291:
                if (str.equals("Notification")) {
                    return 26;
                }
                break;
            case 955867637:
                if (str.equals("ProgressDialog")) {
                    return 23;
                }
                break;
            case 1040211977:
                if (str.equals("FirebaseGoogleLogin")) {
                    return 31;
                }
                break;
            case 1133711410:
                if (str.equals("FirebaseDynamicLink")) {
                    return 29;
                }
                break;
            case 1170382393:
                if (str.equals("Vibrator")) {
                    return 4;
                }
                break;
            case 1236935621:
                if (str.equals("MediaPlayer")) {
                    return 8;
                }
                break;
            case 1263589055:
                if (str.equals("FragmentAdapter")) {
                    return 27;
                }
                break;
            case 1472283236:
                if (str.equals("DatePickerDialog")) {
                    return 24;
                }
                break;
            case 1512362620:
                if (str.equals("BluetoothConnect")) {
                    return 20;
                }
                break;
            case 1774120399:
                if (str.equals("FirebasePhoneAuth")) {
                    return 28;
                }
                break;
            case 1779003621:
                if (str.equals("FirebaseDB")) {
                    return 6;
                }
                break;
            case 1799376742:
                if (str.equals("ObjectAnimator")) {
                    return 10;
                }
                break;
            case 2011082565:
                if (str.equals("Camera")) {
                    return 15;
                }
                break;
            case 2046749032:
                if (str.equals("Dialog")) {
                    return 7;
                }
                break;
        }
        return ComponentsHandler.id(str);
    }

    public static String getComponentTypeName(int i) {
        switch (i) {
            case 1:
                return "Intent";
            case 2:
                return "File";
            case 3:
                return "Calendar";
            case 4:
                return "Vibrator";
            case 5:
                return "Timer";
            case 6:
                return "FirebaseDB";
            case 7:
                return "Dialog";
            case 8:
                return "MediaPlayer";
            case 9:
                return "SoundPool";
            case 10:
                return "ObjectAnimator";
            case 11:
                return "Gyroscope";
            case 12:
                return "FirebaseAuth";
            case 13:
                return "InterstitialAd";
            case 14:
                return "FirebaseStorage";
            case 15:
                return "Camera";
            case 16:
                return "FilePicker";
            case 17:
                return "RequestNetwork";
            case 18:
                return "TextToSpeech";
            case 19:
                return "SpeechToText";
            case 20:
                return "BluetoothConnect";
            case 21:
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
                return ComponentsHandler.typeName(i);
        }
    }

    public static Parcelable.Creator getCreator() {
        return CREATOR;
    }

    public static int getDescStrResource(int i) {
        switch (i) {
            case 1:
                return 2131625105;
            case 2:
                return 2131625099;
            case 3:
                return 2131625096;
            case 4:
                return 2131625115;
            case 5:
                return 2131625114;
            case 6:
                return 2131625211;
            case 7:
                return 2131625098;
            case 8:
                return 2131625108;
            case 9:
                return 2131625111;
            case 10:
                return 2131625109;
            case 11:
                return 2131625104;
            case 12:
                return 2131625102;
            case 13:
                return 2131625106;
            case 14:
                return 2131625103;
            case 15:
                return 2131625097;
            case 16:
                return 2131625100;
            case 17:
                return 2131625110;
            case 18:
                return 2131625113;
            case 19:
                return 2131625112;
            case 20:
                return 2131625095;
            case 21:
                return 2131625107;
            case 22:
                return 2131626494;
            case 23:
                return 2131626495;
            case 24:
                return 2131626496;
            case 25:
                return 2131626497;
            case 26:
                return 2131626498;
            case 27:
                return 2131626503;
            case 28:
                return 2131626506;
            case 29:
                return 2131626507;
            case 30:
                return 2131626508;
            case 31:
                return 2131626509;
            case 32:
                return 2131626510;
            case 33:
                return 2131626511;
            case 34:
                return 2131626512;
            default:
                return 0;
        }
    }

    public static int getIconResource(int i) {
        switch (i) {
            case 1:
                return 2131166254;
            case 2:
                return 2131166268;
            case 3:
                return 2131166238;
            case 4:
                return 2131166277;
            case 5:
                return 2131166276;
            case 6:
            case 12:
            case 14:
                return 2131166245;
            case 7:
                return 2131166235;
            case 8:
                return 2131166259;
            case 9:
                return 2131166269;
            case 10:
                return 2131166262;
            case 11:
                return 2131166248;
            case 13:
                return 2131166234;
            case 15:
                return 2131166240;
            case 16:
                return 2131166244;
            case 17:
                return 2131166261;
            case 18:
                return 2131166274;
            case 19:
                return 2131166271;
            case 20:
                return 2131166236;
            case 21:
                return 2131166258;
            case 22:
                return 2131166295;
            case 23:
                return 2131166296;
            case 24:
                return 2131166294;
            case 25:
                return 2131166297;
            case 26:
                return 2131166298;
            case 27:
                return 2131166304;
            case 28:
                return 2131166324;
            case 29:
                return 2131166325;
            case 30:
                return 2131166326;
            case 31:
                return 2131166327;
            case 32:
                return 2131166328;
            case 33:
                return 2131166329;
            case 34:
                return 2131166330;
            default:
                return ComponentsHandler.icon(i);
        }
    }

    public void buildClassInfo() {
        String str;
        switch (this.type) {
            case 1:
                str = "Intent";
                break;
            case 2:
                str = "SharedPreferences";
                break;
            case 3:
                str = "Calendar";
                break;
            case 4:
                str = "Vibrator";
                break;
            case 5:
                str = "Timer";
                break;
            case 6:
                str = "FirebaseDB";
                break;
            case 7:
                str = "Dialog";
                break;
            case 8:
                str = "MediaPlayer";
                break;
            case 9:
                str = "SoundPool";
                break;
            case 10:
                str = "ObjectAnimator";
                break;
            case 11:
                str = "Gyroscope";
                break;
            case 12:
                str = "FirebaseAuth";
                break;
            case 13:
                str = "InterstitialAd";
                break;
            case 14:
                str = "FirebaseStorage";
                break;
            case 15:
                str = "Camera";
                break;
            case 16:
                str = "FilePicker";
                break;
            case 17:
                str = "RequestNetwork";
                break;
            case 18:
                str = "TextToSpeech";
                break;
            case 19:
                str = "SpeechToText";
                break;
            case 20:
                str = "BluetoothConnect";
                break;
            case 21:
                str = "LocationManager";
                break;
            case 22:
                str = "RewardedVideoAd";
                break;
            case 23:
                str = "ProgressDialog";
                break;
            case 24:
                str = "DatePickerDialog";
                break;
            case 25:
                str = "TimePickerDialog";
                break;
            case 26:
                str = "Notification";
                break;
            case 27:
                str = "FragmentAdapter";
                break;
            case 28:
                str = "FirebasePhoneAuth";
                break;
            case 29:
                str = "FirebaseDynamicLink";
                break;
            case 30:
                str = "FirebaseCloudMessage";
                break;
            case 31:
                str = "FirebaseGoogleLogin";
                break;
            case 32:
                str = "OneSignal";
                break;
            case 33:
                str = "FBAdsBanner";
                break;
            case 34:
                str = "FBAdsInterstitial";
                break;
            default:
                str = ComponentsHandler.c(this.type);
                break;
        }
        this.classInfo = new Gx(str);
    }

    public void clearClassInfo() {
        this.classInfo = null;
    }

    public void copy(ComponentBean componentBean) {
        this.type = componentBean.type;
        this.componentId = componentBean.componentId;
        this.param1 = componentBean.param1;
        this.param2 = componentBean.param2;
        this.param3 = componentBean.param3;
    }

    public int describeContents() {
        return 0;
    }

    public Gx getClassInfo() {
        if (this.classInfo == null) {
            buildClassInfo();
        }
        return this.classInfo;
    }

    public void print() {
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeString(this.componentId);
        parcel.writeString(this.param1);
        parcel.writeString(this.param2);
        parcel.writeString(this.param3);
    }
}
