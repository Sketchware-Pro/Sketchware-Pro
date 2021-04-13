package mod.agus.jcoderz.lib;

import mod.hilal.saif.components.ComponentsHandler;

public class TypeClassComponent {
    public static String a(String str) {
        switch (str.hashCode()) {
            case -1810281568:
                if (str.equals("FBAdsInterstitial")) {
                    return "Component.FBAdsInterstitial";
                }
                break;
            case -1732810888:
                if (str.equals("Videos")) {
                    return "Component.Videos";
                }
                break;
            case -1601208984:
                if (str.equals("FirebaseAdmin")) {
                    return "Component.OneSignal";
                }
                break;
            case -1231229991:
                if (str.equals("FirebaseCloudMessage")) {
                    return "Component.FirebaseCloudMessage";
                }
                break;
            case 313126659:
                if (str.equals("TimePickerDialog")) {
                    return "Component.TimePickerDialog";
                }
                break;
            case 610585248:
                if (str.equals("FBAdsBanner")) {
                    return "Component.FBAdsBanner";
                }
                break;
            case 759553291:
                if (str.equals("Notification")) {
                    return "Component.Notification";
                }
                break;
            case 955867637:
                if (str.equals("ProgressDialog")) {
                    return "Component.ProgressDialog";
                }
                break;
            case 1040211977:
                if (str.equals("FirebaseGoogleLogin")) {
                    return "Component.FirebaseGoogleLogin";
                }
                break;
            case 1133711410:
                if (str.equals("FirebaseDynamicLink")) {
                    return "Component.FirebaseDynamicLink";
                }
                break;
            case 1387586571:
                if (str.equals("PopupMenu")) {
                    return "Component.PopupMenu";
                }
                break;
            case 1472283236:
                if (str.equals("DatePickerDialog")) {
                    return "Component.DatePickerDialog";
                }
                break;
            case 1774120399:
                if (str.equals("FirebasePhoneAuth")) {
                    return "Component.FirebasePhoneAuth";
                }
                break;
            case 2114116224:
                if (str.equals("MediaController")) {
                    return "Component.MediaController";
                }
                break;
        }
        return ComponentsHandler.c(str);
    }
}
