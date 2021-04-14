package mod.agus.jcoderz.lib;

import mod.hilal.saif.components.ComponentsHandler;

public class TypeVarComponent {
    public static String a(int i) {
        switch (i) {
            case 28:
                return "PhoneAuthProvider.OnVerificationStateChangedCallbacks";
            case 29:
                return "DynamicLink";
            case 30:
                return "FirebaseCloudMessage";
            case 31:
                return "GoogleSignInClient";
            case 32:
                return "OSPermissionSubscriptionState";
            case 33:
                return "com.facebook.ads.AdView";
            case 34:
                return "com.facebook.ads.InterstitialAd";
            default:
                return ComponentsHandler.var(i);
        }
    }

    public static String b(String str) {
        return "";
    }
}
