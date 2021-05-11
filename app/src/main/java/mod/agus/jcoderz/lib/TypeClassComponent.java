package mod.agus.jcoderz.lib;

import mod.hilal.saif.components.ComponentsHandler;

public class TypeClassComponent {

    public static String a(String component) {
        switch (component) {
            case "FBAdsInterstitial":
                return "Component.FBAdsInterstitial";

            case "Videos":
                return "Component.Videos";

            case "FirebaseAdmin":
                return "Component.OneSignal";

            case "FirebaseCloudMessage":
                return "Component.FirebaseCloudMessage";

            case "TimePickerDialog":
                return "Component.TimePickerDialog";

            case "FBAdsBanner":
                return "Component.FBAdsBanner";

            case "Notification":
                return "Component.Notification";

            case "ProgressDialog":
                return "Component.ProgressDialog";

            case "FirebaseGoogleLogin":
                return "Component.FirebaseGoogleLogin";

            case "FirebaseDynamicLink":
                return "Component.FirebaseDynamicLink";

            case "PopupMenu":
                return "Component.PopupMenu";

            case "DatePickerDialog":
                return "Component.DatePickerDialog";

            case "FirebasePhoneAuth":
                return "Component.FirebasePhoneAuth";

            case "MediaController":
                return "Component.MediaController";
        }
        return ComponentsHandler.c(component);
    }
}
