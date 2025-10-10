package mod.agus.jcoderz.editor.library;

import mod.agus.jcoderz.handle.component.ConstVarComponent;
import mod.jbk.build.BuiltInLibraries;
import pro.sketchware.util.library.BuiltInLibraryManager;

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
        if (component.isLottieUsed) {
            kp.addLibrary(BuiltInLibraries.LOTTIE);
        }
        if (component.isYoutubePlayerUsed) {
            kp.addLibrary(BuiltInLibraries.ANDROID_YOUTUBE_PLAYER);
        }
        if (component.isCircleImageViewUsed) {
            kp.addLibrary(BuiltInLibraries.CIRCLEIMAGEVIEW);
        }
        if (component.isFBGoogleUsed) {
            kp.addLibrary(BuiltInLibraries.PLAY_SERVICES_AUTH);
        }
        if (component.isOTPViewUsed) {
            kp.addLibrary(BuiltInLibraries.OTPVIEW);
        }
        if (component.isCodeViewUsed) {
            kp.addLibrary(BuiltInLibraries.CODEVIEW);
        }
        if (component.isPatternLockViewUsed) {
            kp.addLibrary(BuiltInLibraries.PATTERN_LOCK_VIEW);
        }
        if (component.isWaveSideBarUsed) {
            kp.addLibrary(BuiltInLibraries.WAVE_SIDE_BAR);
        }
        if (component.isSwipeRefreshLayoutUsed) {
            kp.addLibrary(BuiltInLibraries.ANDROIDX_SWIPEREFRESHLAYOUT);
        }
    }
}
