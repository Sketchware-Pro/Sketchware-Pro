package mod.agus.jcoderz.handle.component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A registry for blocks added by Agus.
 */
public class ConstVarComponent {

    public boolean isCircleImageViewUsed = false;
    public boolean isCodeViewUsed = false;
    public boolean isDynamicLinkUsed = false;
    public boolean isFBAdsUsed = false;
    public boolean isFBGoogleUsed = false;
    public boolean isFCMUsed = false;
    public boolean isLottieUsed = false;
    public boolean isOTPViewUsed = false;
    public boolean isOneSignalUsed = false;
    public boolean isPatternLockViewUsed = false;
    public boolean isWaveSideBarUsed = false;
    public boolean isYoutubePlayerUsed = false;
    public HashMap<String, ArrayList<String>> param = new HashMap<>();
    public String pkgName = "";

    public void handleComponent(int componentId) {
        switch (componentId) {
            case 29:
                isDynamicLinkUsed = true;
                return;

            case 30:
                isFCMUsed = true;
                return;

            case 31:
                isFBGoogleUsed = true;
                return;

            case 32:
                isOneSignalUsed = true;
                return;

            case 33:
            case 34:
                isFBAdsUsed = true;

            default:
        }
    }

    public void handleDeleteComponent(String componentNameId) {
        switch (componentNameId) {
            case "FBAdsBanner":
            case "FBAdsInterstitial":
                isFBAdsUsed = false;
                break;

            case "FirebaseCloudMessage":
                isFCMUsed = false;
                break;

            case "FirebaseDynamicLink":
                isDynamicLinkUsed = false;
                break;

            case "FirebaseGoogleLogin":
                isFBGoogleUsed = false;
                break;

            case "OneSignal":
                isOneSignalUsed = false;
                break;

            default:
        }
    }

    public void handleWidget(String widgetId) {
        switch (widgetId) {
            case "CircleImageView":
                isCircleImageViewUsed = true;
                break;

            case "CodeView":
                isCodeViewUsed = true;
                break;

            case "LottieAnimationView":
                isLottieUsed = true;
                break;

            case "OTPView":
                isOTPViewUsed = true;
                break;

            case "PatternLockView":
                isPatternLockViewUsed = true;
                break;

            case "WaveSideBar":
                isWaveSideBarUsed = true;
                break;

            case "YouTubePlayerView":
                isYoutubePlayerUsed = true;
                break;
        }
    }

    public void setParams(ArrayList<String> arrayList, String packageName, String blockId) {
        if (!packageName.isEmpty()) {
            pkgName = packageName;
        }
        switch (blockId) {
            case "OneSignal setAppId":
            case "OnResultBillingResponse":
            case "Youtube useWebUI":
            case "FirebaseDynamicLink setDataHost":
            case "FacebookAds setProvider":
                if (arrayList != null && arrayList.size() > 0) {
                    if (param == null) param = new HashMap<>();
                    param.clear();
                    param.put(blockId, arrayList);
                }
                break;

            default:
        }
    }
}
