package mod.agus.jcoderz.editor.library;

import a.a.a.Kp;
import mod.agus.jcoderz.handle.component.ConstVarComponent;
import mod.jbk.build.BuiltInLibraries;

public class ExtLibSelected {

    /**
     * Empty, private constructor to avoid instantiation.
     */
    private ExtLibSelected() {
    }

    public static void addUsedDependencies(ConstVarComponent component, Kp kp) {
        if (component.isFCMUsed) {
            kp.a(BuiltInLibraries.FIREBASE_MESSAGING);
        }
        if (component.isOneSignalUsed) {
            kp.a(BuiltInLibraries.ONESIGNAL);
            kp.a(BuiltInLibraries.PLAY_SERVICES_GCM);
        }
        if (component.isFBAdsUsed) {
            kp.a(BuiltInLibraries.FACEBOOK_ADS_AUDIENCE_NETWORK_SDK);
        }
        if (component.isLottieUsed) {
            kp.a(BuiltInLibraries.LOTTIE);
        }
        if (component.isYoutubePlayerUsed) {
            kp.a(BuiltInLibraries.YOUTUBE_PLAYER);
        }
        if (component.isCircleImageViewUsed) {
            kp.a(BuiltInLibraries.CIRCLE_IMAGEVIEW);
        }
        if (component.isFBGoogleUsed) {
            kp.a(BuiltInLibraries.PLAY_SERVICES_AUTH);
        }
        if (component.isOTPViewUsed) {
            kp.a(BuiltInLibraries.OTPVIEW);
        }
        if (component.isCodeViewUsed) {
            kp.a(BuiltInLibraries.CODE_VIEW);
        }
        if (component.isPatternLockViewUsed) {
            kp.a(BuiltInLibraries.PATTERN_LOCK_VIEW);
        }
        if (component.isWaveSideBarUsed) {
            kp.a(BuiltInLibraries.WAVE_SIDE_BAR);
        }
    }
}
