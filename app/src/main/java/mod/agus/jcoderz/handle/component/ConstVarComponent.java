package mod.agus.jcoderz.handle.component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A registry for blocks added by Agus.
 */
public class ConstVarComponent {

    public boolean isCircleImageViewUsed = false;
    public boolean isCodeViewUsed = false;
    public boolean isFBGoogleUsed = false;
    public boolean isFCMUsed = false;
    public boolean isLottieUsed = false;
    public boolean isOTPViewUsed = false;
    public boolean isPatternLockViewUsed = false;
    public boolean isWaveSideBarUsed = false;
    public boolean isYoutubePlayerUsed = false;
    public boolean isSwipeRefreshLayoutUsed = false;
    public HashMap<String, ArrayList<String>> param = new HashMap<>();

    public void handleDeleteComponent(String componentNameId) {
        switch (componentNameId) {
            case "FirebaseCloudMessage":
                isFCMUsed = false;
                break;

            case "FirebaseGoogleLogin":
                isFBGoogleUsed = false;
                break;

            default:
        }
    }
}
