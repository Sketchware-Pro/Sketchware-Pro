package dev.aldi.sayuti.block;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import a.a.a.Gx;

public class ExtraBlockClassInfo {
    private static JSONArray jSONArray;

    static {
        loadEBCI();
    }

    public static void loadEBCI() {
        try {
            jSONArray = new JSONArray(ExtraBlockFile.getMenuBlockFile());
        } catch (JSONException e) {
            jSONArray = new JSONArray();
        }
    }

    private static void loadIfNull() {
        if (jSONArray == null) {
            loadEBCI();
        }
    }

    public static Gx getTypeVar(String str, String str2) {
        loadIfNull();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (str.equals(jSONObject.getString("type")) && str.equals(jSONObject.getString("name"))) {
                    return new Gx(jSONObject.getString("typeVar"));
                }
            } catch (JSONException e) {
            }
        }
        return null;
    }

    public static String getName(String str) {
        loadIfNull();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (str.equals(jSONObject.getString("id")) && jSONObject.has("name")) {
                    return jSONObject.getString("name");
                }
            } catch (JSONException e) {
            }
        }
        return getDefaultName(str);
    }

    public static String getType(String str) {
        loadIfNull();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.has("name") && str.equals(jSONObject.getString("name"))) {
                    return jSONObject.getString("type");
                }
            } catch (JSONException e) {
            }
        }
        return getDefaultType(str);
    }

    private static String getDefaultName(String str) {
        switch (str) {
            case "circleimageview":
                return "CircleImageView";

            case "onesignal":
                return "OneSignal";

            case "customViews":
                return "CustomView";

            case "asynctask":
                return "AsyncTask";

            case "activity":
                return "Context";

            case "otpview":
                return "OTPView";

            case "lottie":
                return "LottieAnimation";

            case "phoneauth":
                return "FirebasePhoneAuth";

            case "fbadbanner":
                return "FBAdsBanner";

            case "codeview":
                return "CodeView";

            case "recyclerview":
                return "RecyclerView";

            case "resource":
                return "Image";

            case "googlelogin":
                return "FirebaseGoogleSignIn";

            case "dynamiclink":
                return "FirebaseDynamicLink";

            case "youtubeview":
                return "YoutubePlayer";

            case "cardview":
                return "CardView";

            case "radiogroup":
                return "RadioGroup";

            case "color":
                return "Color";

            case "fbadinterstitial":
                return "FBAdsInterstitial";

            case "textinputlayout":
                return "TextInputLayout";

            case "collapsingtoolbar":
                return "CollapsingToolbarLayout";

            case "cloudmessage":
                return "FirebaseCloudMessage";

            case "resource_bg":
                return "BackgroundImage";

            case "datepicker":
                return "DatePicker";

            case "timepicker":
                return "TimePicker";

            case "swiperefreshlayout":
                return "SwipeRefreshLayout";

            case "signinbutton":
                return "SignInButton";

            case "materialButton":
                return "MaterialButton";

            case "fragmentAdapter":
                return "FragmentAdapter";

            default:
                return str;
        }
    }

    private static String getDefaultType(String str) {
        switch (str) {
            case "onesignal":
            case "phoneauth":
            case "fbadbanner":
            case "googlelogin":
            case "dynamiclink":
            case "fbadinterstitial":
            case "cloudmessage":
                return "p";
            default:
                return "v";
        }
    }
}
