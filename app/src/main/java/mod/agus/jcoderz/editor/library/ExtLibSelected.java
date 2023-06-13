package mod.agus.jcoderz.editor.library;

import a.a.a.BuiltInLibraryManager;
import mod.agus.jcoderz.handle.component.ConstVarComponent;
import mod.jbk.build.BuiltInLibraries;

public class ExtLibSelected {

    /**
     * Empty, private constructor to avoid instantiation.
     */
    private ExtLibSelected() {
    }

    public static void addUsedDependencies(ConstVarComponent component, BuiltInLibraryManager kp) {
        if (component.isFCMUsed) {
            kp.addLibrary(BuiltInLibraries.FIREBASE_MESSAGING);
        }
        if (component.isOneSignalUsed) {
            kp.addLibrary(BuiltInLibraries.ONESIGNAL);
            kp.addLibrary(BuiltInLibraries.PLAY_SERVICES_GCM);
        }
        if (component.isFBAdsUsed) {
            kp.addLibrary(BuiltInLibraries.FACEBOOK_ADS_AUDIENCE_NETWORK_SDK);
        }
        if (component.isLottieUsed) {
            kp.addLibrary(BuiltInLibraries.LOTTIE);
        }
        if (component.isYoutubePlayerUsed) {
            kp.addLibrary(BuiltInLibraries.YOUTUBE_PLAYER);
        }
        if (component.isCircleImageViewUsed) {
            kp.addLibrary(BuiltInLibraries.CIRCLE_IMAGEVIEW);
        }
        if (component.isFBGoogleUsed) {
            kp.addLibrary(BuiltInLibraries.PLAY_SERVICES_AUTH);
        }
        if (component.isOTPViewUsed) {
            kp.addLibrary(BuiltInLibraries.OTPVIEW);
        }
        if (component.isCodeViewUsed) {
            kp.addLibrary(BuiltInLibraries.CODE_VIEW);
        }
        if (component.isPatternLockViewUsed) {
            kp.addLibrary(BuiltInLibraries.PATTERN_LOCK_VIEW);
        }
        if (component.isWaveSideBarUsed) {
            kp.addLibrary(BuiltInLibraries.WAVE_SIDE_BAR);
        }
    }
}
