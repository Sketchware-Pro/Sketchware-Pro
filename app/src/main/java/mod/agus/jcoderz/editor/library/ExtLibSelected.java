package mod.agus.jcoderz.editor.library;

import a.a.a.Kp;
import mod.agus.jcoderz.handle.component.ConstVarComponent;

public class ExtLibSelected {

    /**
     * Empty, private constructor to avoid instantiation.
     */
    private ExtLibSelected() {
    }

    public static void addUsedDependencies(ConstVarComponent component, Kp kp) {
        if (component.isDynamicLinkUsed) {
            kp.a("firebase-dynamic-links-19.0.0");
        }
        if (component.isFCMUsed) {
            kp.a("firebase-messaging-19.0.0");
        }
        if (component.isOneSignalUsed) {
            kp.a("OneSignal-3.14.0");
            kp.a("play-services-gcm-17.0.0");
        }
        if (component.isFBAdsUsed) {
            kp.a("audience-network-sdk-5.9.0");
        }
        if (component.isLottieUsed) {
            kp.a("Lottie-3.4.0");
        }
        if (component.isYoutubePlayerUsed) {
            kp.a("android-youtube-player-10.0.5");
        }
        if (component.isCircleImageViewUsed) {
            kp.a("circle-imageview-v3.1.0");
        }
        if (component.isFBGoogleUsed) {
            kp.a("play-services-auth-17.0.0");
        }
        if (component.isOTPViewUsed) {
            kp.a("OTPView-0.1.0");
        }
        if (component.isCodeViewUsed) {
            kp.a("code-view");
        }
        if (component.isPatternLockViewUsed) {
            kp.a("pattern-lock-view");
        }
        if (component.isWaveSideBarUsed) {
            kp.a("wave-side-bar");
        }
    }
}
