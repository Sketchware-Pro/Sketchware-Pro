package mod.agus.jcoderz.manage.library;

import java.util.ArrayList;

/**
 * {@link a.a.a.qq} but for "external" built-in libraries, "Lottie-3.4.0" for example.
 */
public class ExtLibSelection {

    /**
     * Empty, private constructor to avoid instantiation.
     */
    private ExtLibSelection() {
    }

    /**
     * @see a.a.a.qq#a(String)
     */
    public static ArrayList<String> a(String name) {
        ArrayList<String> knownDependencies = new ArrayList<>();
        switch (name) {
            case "annotations-13.0":
            case "Okio-1.17.4":
            case "constraintlayout-solver-1.1.3":
            case "annotations-4.11.0":
            case "core-ktx-1.0.1":
            case "disklrucache-4.11.0":
                // ???
                break;

            case "Lottie-3.4.0":
                knownDependencies.add("appcompat-1.0.0");
                knownDependencies.add("Okio-1.17.4");
                break;

            case "firebase-iid-interop-17.0.0":
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                break;

            case "circle-imageview-v3.1.0":
            case "gifdecoder-4.11.0":
            case "exifinterface-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                break;

            case "firebase-dynamic-links-19.0.0":
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                knownDependencies.add("firebase-measurement-connector-18.0.0");
                break;

            case "glide-4.11.0":
                knownDependencies.add("exifinterface-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("vectordrawable-animated-1.0.0");
                knownDependencies.add("gifdecoder-4.11.0");
                knownDependencies.add("disklrucache-4.11.0");
                knownDependencies.add("annotations-4.11.0");
                break;

            case "play-services-location-17.0.0":
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-places-placereport-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                break;

            case "kotlin-stdlib-jdk7-1.3.50":
                knownDependencies.add("kotlin-stdlib-1.3.50");
                break;

            case "play-services-auth-17.0.0":
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("loader-1.0.0");
                knownDependencies.add("play-services-auth-api-phone-17.0.0");
                knownDependencies.add("play-services-auth-base-17.0.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                break;

            case "kotlin-stdlib-1.3.50":
                knownDependencies.add("annotations-13.0");
                break;

            case "constraintlayout-1.1.3":
                knownDependencies.add("constraintlayout-solver-1.1.3");
                break;

            case "pattern-lock-view":
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("annotations-13.0");
                break;

            case "firebase-iid-19.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("legacy-support-core-utils-1.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-stats-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                knownDependencies.add("firebase-iid-interop-17.0.0");
                break;

            case "play-services-places-placereport-17.0.0":
            case "firebase-measurement-connector-18.0.0":
                knownDependencies.add("play-services-basement-17.0.0");
                break;

            case "firebase-messaging-19.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                knownDependencies.add("firebase-iid-19.0.0");
                knownDependencies.add("firebase-measurement-connector-18.0.0");
                break;

            case "play-services-iid-17.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-stats-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                break;

            case "android-youtube-player-10.0.5":
                knownDependencies.add("appcompat-1.0.0");
                knownDependencies.add("kotlin-stdlib-jdk7-1.3.50");
                break;

            case "OneSignal-3.14.0":
                knownDependencies.add("cardview-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("browser-1.0.0");
                knownDependencies.add("media-1.0.0");
                knownDependencies.add("play-services-location-17.0.0");
                knownDependencies.add("play-services-ads-identifier-17.0.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("firebase-messaging-19.0.0");
                break;

            case "OTPView-0.1.0":
                knownDependencies.add("kotlin-stdlib-jdk7-1.3.50");
                knownDependencies.add("appcompat-1.0.0");
                knownDependencies.add("core-ktx-1.0.1");
                knownDependencies.add("constraintlayout-1.1.3");
                break;

            case "play-services-gcm-17.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("legacy-support-core-utils-1.0.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-iid-17.0.0");
                knownDependencies.add("play-services-stats-17.0.0");
                break;
        }

        return knownDependencies;
    }

    /**
     * @see a.a.a.qq#b(String)
     */
    public static String b(String name) {
        switch (name) {
            case "android-youtube-player-10.0.5":
                return "com.pierfrancescosoffritti.androidyoutubeplayer";

            case "circle-imageview-v3.1.0":
                return "de.hdodenhof.circleimageview";

            case "code-view":
                return "br.tiagohm.codeview";

            case "constraintlayout-1.1.3":
                return "androidx.constraintlayout.widget";

            case "firebase-messaging-19.0.0":
                return "com.google.firebase.messaging";

            case "glide-4.11.0":
                return "com.bumptech.glide";

            case "Lottie-3.4.0":
                return "com.airbnb.lottie";

            case "OneSignal-3.14.0":
                return "com.onesignal";

            case "OTPView-0.1.0":
                return "affan.ahmad.otp";

            case "pattern-lock-view":
                return "com.andrognito.patternlockview";

            case "play-services-auth-17.0.0":
                return "com.google.android.gms.auth.api";

            case "play-services-gcm-17.0.0":
                return "com.google.android.gms.gcm";

            case "wave-side-bar":
                return "com.sayuti.lib";

            default:
                return "";
        }
    }

    /**
     * @see a.a.a.qq#c(String)
     */
    public static boolean c(String name) {
        switch (name) {
            case "android-youtube-player-10.0.5":
            case "circle-imageview-v3.1.0":
            case "code-view":
            case "constraintlayout-1.1.3":
            case "firebase-messaging-19.0.0":
            case "glide-4.11.0":
            case "Lottie-3.4.0":
            case "OneSignal-3.14.0":
            case "OTPView-0.1.0":
            case "pattern-lock-view":
            case "play-services-auth-17.0.0":
            case "play-services-gcm-17.0.0":
            case "wave-side-bar":
                return true;

            default:
                return false;
        }
    }

    public static boolean d(String str) {
        return str.equals("code-view");
    }
}
