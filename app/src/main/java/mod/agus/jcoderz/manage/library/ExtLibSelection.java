package mod.agus.jcoderz.manage.library;

import java.util.ArrayList;

public class ExtLibSelection {

    public static ArrayList<String> a(String str) {
        ArrayList<String> libraries = new ArrayList<>();
        switch (str) {
            case "annotations-13.0":
            case "Okio-1.17.4":
            case "constraintlayout-solver-1.1.3":
            case "annotations-4.11.0":
            case "core-ktx-1.0.1":
            case "disklrucache-4.11.0":
                // ???
                break;

            case "Lottie-3.4.0":
                libraries.add("appcompat-1.0.0");
                libraries.add("Okio-1.17.4");
                break;

            case "firebase-iid-interop-17.0.0":
                libraries.add("play-services-base-17.1.0");
                libraries.add("play-services-basement-17.0.0");
                break;

            case "circle-imageview-v3.1.0":

            case "gifdecoder-4.11.0":

            case "exifinterface-1.0.0":
                libraries.add("annotation-1.1.0");
                break;

            case "firebase-dynamic-links-19.0.0":
                libraries.add("play-services-base-17.1.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-tasks-17.0.0");
                libraries.add("firebase-common-19.0.0");
                libraries.add("firebase-measurement-connector-18.0.0");
                break;

            case "glide-4.11.0":
                libraries.add("exifinterface-1.0.0");
                libraries.add("fragment-1.0.0");
                libraries.add("vectordrawable-animated-1.0.0");
                libraries.add("gifdecoder-4.11.0");
                libraries.add("disklrucache-4.11.0");
                libraries.add("annotations-4.11.0");
                break;

            case "play-services-location-17.0.0":
                libraries.add("play-services-base-17.1.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-places-placereport-17.0.0");
                libraries.add("play-services-tasks-17.0.0");
                break;

            case "kotlin-stdlib-jdk7-1.3.50":
                libraries.add("kotlin-stdlib-1.3.50");
                break;

            case "play-services-auth-17.0.0":
                libraries.add("fragment-1.0.0");
                libraries.add("loader-1.0.0");
                libraries.add("play-services-auth-api-phone-17.0.0");
                libraries.add("play-services-auth-base-17.0.0");
                libraries.add("play-services-base-17.1.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-tasks-17.0.0");
                break;

            case "kotlin-stdlib-1.3.50":
                libraries.add("annotations-13.0");
                break;

            case "constraintlayout-1.1.3":
                libraries.add("constraintlayout-solver-1.1.3");
                break;

            case "pattern-lock-view":
                libraries.add("core-1.0.0");
                libraries.add("annotations-13.0");
                break;

            case "firebase-iid-19.0.0":
                libraries.add("collection-1.0.0");
                libraries.add("core-1.0.0");
                libraries.add("legacy-support-core-utils-1.0.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-stats-17.0.0");
                libraries.add("play-services-tasks-17.0.0");
                libraries.add("firebase-common-19.0.0");
                libraries.add("firebase-iid-interop-17.0.0");
                break;

            case "play-services-places-placereport-17.0.0":

            case "firebase-measurement-connector-18.0.0":
                libraries.add("play-services-basement-17.0.0");
                break;

            case "firebase-messaging-19.0.0":
                libraries.add("collection-1.0.0");
                libraries.add("core-1.0.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-tasks-17.0.0");
                libraries.add("firebase-common-19.0.0");
                libraries.add("firebase-iid-19.0.0");
                libraries.add("firebase-measurement-connector-18.0.0");
                break;

            case "play-services-iid-17.0.0":
                libraries.add("collection-1.0.0");
                libraries.add("core-1.0.0");
                libraries.add("play-services-base-17.1.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-stats-17.0.0");
                libraries.add("play-services-tasks-17.0.0");
                break;

            case "android-youtube-player-10.0.5":
                libraries.add("appcompat-1.0.0");
                libraries.add("kotlin-stdlib-jdk7-1.3.50");
                break;

            case "OneSignal-3.14.0":
                libraries.add("cardview-1.0.0");
                libraries.add("fragment-1.0.0");
                libraries.add("browser-1.0.0");
                libraries.add("media-1.0.0");
                libraries.add("play-services-location-17.0.0");
                libraries.add("play-services-ads-identifier-17.0.0");
                libraries.add("play-services-base-17.1.0");
                libraries.add("firebase-messaging-19.0.0");
                break;

            case "OTPView-0.1.0":
                libraries.add("kotlin-stdlib-jdk7-1.3.50");
                libraries.add("appcompat-1.0.0");
                libraries.add("core-ktx-1.0.1");
                libraries.add("constraintlayout-1.1.3");
                break;

            case "play-services-gcm-17.0.0":
                libraries.add("collection-1.0.0");
                libraries.add("core-1.0.0");
                libraries.add("legacy-support-core-utils-1.0.0");
                libraries.add("play-services-base-17.1.0");
                libraries.add("play-services-basement-17.0.0");
                libraries.add("play-services-iid-17.0.0");
                libraries.add("play-services-stats-17.0.0");
                break;
        }

        return libraries;
    }

    public static String b(String str) {
        switch (str) {
            case "Lottie-3.4.0":
                return "com.airbnb.lottie";

            case "circle-imageview-v3.1.0":
                return "de.hdodenhof.circleimageview";

            case "glide-4.11.0":
                return "com.bumptech.glide";

            case "code-view":
                return "br.tiagohm.codeview";

            case "wave-side-bar":
                return "com.sayuti.lib";

            case "play-services-auth-17.0.0":
                return "com.google.android.gms.auth.api";

            case "constraintlayout-1.1.3":
                return "androidx.constraintlayout.widget";

            case "pattern-lock-view":
                return "com.andrognito.patternlockview";

            case "firebase-messaging-19.0.0":
                return "com.google.firebase.messaging";

            case "android-youtube-player-10.0.5":
                return "com.pierfrancescosoffritti.androidyoutubeplayer";

            case "OneSignal-3.14.0":
                return "com.onesignal";

            case "OTPView-0.1.0":
                return "affan.ahmad.otp";

            default:
                return "";
        }
    }

    public static boolean c(String str) {
        switch (str) {
            case "Lottie-3.4.0":
            case "circle-imageview-v3.1.0":
            case "glide-4.11.0":
            case "code-view":
            case "wave-side-bar":
            case "play-services-auth-17.0.0":
            case "constraintlayout-1.1.3":
            case "pattern-lock-view":
            case "firebase-messaging-19.0.0":
            case "android-youtube-player-10.0.5":
            case "OneSignal-3.14.0":
            case "OTPView-0.1.0":
                return true;

            default:
                return false;
        }
    }

    public static boolean d(String str) {
        return str.equals("code-view");
    }
}
