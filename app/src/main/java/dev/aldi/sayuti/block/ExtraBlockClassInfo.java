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
        return switch (str) {
            case "circleimageview" -> "CircleImageView";
            case "onesignal" -> "OneSignal";
            case "customViews" -> "CustomView";
            case "asynctask" -> "AsyncTask";
            case "activity" -> "Context";
            case "otpview" -> "OTPView";
            case "lottie" -> "LottieAnimation";
            case "phoneauth" -> "FirebasePhoneAuth";
            case "fbadbanner" -> "FBAdsBanner";
            case "codeview" -> "CodeView";
            case "recyclerview" -> "RecyclerView";
            case "resource" -> "Image";
            case "googlelogin" -> "FirebaseGoogleSignIn";
            case "dynamiclink" -> "FirebaseDynamicLink";
            case "youtubeview" -> "YoutubePlayer";
            case "cardview" -> "CardView";
            case "radiogroup" -> "RadioGroup";
            case "color" -> "Color";
            case "fbadinterstitial" -> "FBAdsInterstitial";
            case "textinputlayout" -> "TextInputLayout";
            case "collapsingtoolbar" -> "CollapsingToolbarLayout";
            case "cloudmessage" -> "FirebaseCloudMessage";
            case "resource_bg" -> "BackgroundImage";
            case "datepicker" -> "DatePicker";
            case "timepicker" -> "TimePicker";
            case "swiperefreshlayout" -> "SwipeRefreshLayout";
            case "signinbutton" -> "SignInButton";
            case "materialButton" -> "MaterialButton";
            case "fragmentAdapter" -> "FragmentAdapter";
            default -> str;
        };
    }

    private static String getDefaultType(String str) {
        return switch (str) {
            case "onesignal", "phoneauth", "fbadbanner", "googlelogin", "dynamiclink", "fbadinterstitial", "cloudmessage" ->
                    "p";
            default -> "v";
        };
    }
}
