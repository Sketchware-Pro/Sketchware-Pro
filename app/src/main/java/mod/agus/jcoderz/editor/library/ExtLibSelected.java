package mod.agus.jcoderz.editor.library;

import a.a.a.Kp;
import mod.agus.jcoderz.handle.component.ConstVarComponent;

public class ExtLibSelected {
    public static void a(ConstVarComponent constVarComponent, Kp kp) {
        if (constVarComponent.isDynamicLinkUsed) {
            kp.a("firebase-dynamic-links-19.0.0");
        }
        if (constVarComponent.isFCMUsed) {
            kp.a("firebase-messaging-19.0.0");
        }
        if (constVarComponent.isOneSignalUsed) {
            kp.a("OneSignal-3.14.0");
            kp.a("play-services-gcm-17.0.0");
        }
        if (constVarComponent.isFBAdsUsed) {
            kp.a("audience-network-sdk-5.9.0");
        }
        if (constVarComponent.isLottieUsed) {
            kp.a("Lottie-3.4.0");
        }
        if (constVarComponent.isYoutubePlayerUsed) {
            kp.a("android-youtube-player-10.0.5");
        }
        if (constVarComponent.isCircleImageViewUsed) {
            kp.a("circle-imageview-v3.1.0");
        }
        if (constVarComponent.isFBGoogleUsed) {
            kp.a("play-services-auth-17.0.0");
        }
        if (constVarComponent.isOTPViewUsed) {
            kp.a("OTPView-0.1.0");
        }
        if (constVarComponent.isCodeViewUsed) {
            kp.a("code-view");
        }
        if (constVarComponent.isPatternLockViewUsed) {
            kp.a("pattern-lock-view");
        }
        if (constVarComponent.isWaveSideBarUsed) {
            kp.a("wave-side-bar");
        }
    }
}
