package mod.agus.jcoderz.editor.event;

import java.util.ArrayList;

import mod.hilal.saif.events.EventsHandler;

public class CustomImportEvent {

    public static void a(ArrayList<String> importList, String listener) {
        switch (listener) {
            case "OnFailureListener":
                importList.add("com.google.android.gms.tasks.OnFailureListener");
                break;

            case "OTPListener":
                importList.add("affan.ahmad.otp.OTPListener");
                break;

            case "OnSuccessListener":
                importList.add("com.google.android.gms.tasks.OnSuccessListener");
                break;

            case "OnCompleteListenerFCM":
                importList.add("com.google.android.gms.tasks.OnCompleteListener");
                importList.add("com.google.android.gms.tasks.Task");
                break;

            case "fullScreenContentCallback":
                importList.add("com.google.android.gms.ads.FullScreenContentCallback");
                importList.add("com.google.android.gms.ads.AdError");
                break;

            case "interstitialAdLoadCallback":
                importList.add("com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback");
                importList.add("com.google.android.gms.ads.LoadAdError");
                break;

            case "bannerAdViewListener":
                importList.add("com.google.android.gms.ads.AdListener");
                importList.add("com.google.android.gms.ads.LoadAdError");
                break;
        }
        EventsHandler.getImports(importList, listener);
    }
}
