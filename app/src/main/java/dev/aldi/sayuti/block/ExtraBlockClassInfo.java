package dev.aldi.sayuti.block;

import a.a.a.Gx;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        return a(str);
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
        return b(str);
    }

    public static String a(String str) {
        switch (str.hashCode()) {
            case -1861588048:
                if (str.equals("circleimageview")) {
                    return "CircleImageView";
                }
                return str;
            case -1787283314:
                if (str.equals("onesignal")) {
                    return "OneSignal";
                }
                return str;
            case -1786176131:
                if (str.equals("customViews")) {
                    return "CustomView";
                }
                return str;
            case -1706714623:
                if (str.equals("asynctask")) {
                    return "AsyncTask";
                }
                return str;
            case -1655966961:
                if (str.equals("activity")) {
                    return "Class";
                }
                return str;
            case -1138271152:
                if (str.equals("otpview")) {
                    return "OTPView";
                }
                return str;
            case -1096937569:
                if (str.equals("lottie")) {
                    return "LottieAnimation";
                }
                return str;
            case -1028606954:
                if (str.equals("phoneauth")) {
                    return "FirebasePhoneAuth";
                }
                return str;
            case -897829429:
                if (str.equals("fbadbanner")) {
                    return "FBAdsBanner";
                }
                return str;
            case -866964974:
                if (str.equals("codeview")) {
                    return "CodeView";
                }
                return str;
            case -400432284:
                if (str.equals("recyclerview")) {
                    return "RecyclerView";
                }
                return str;
            case -341064690:
                if (str.equals("resource")) {
                    return "Image";
                }
                return str;
            case -322859824:
                if (str.equals("googlelogin")) {
                    return "FirebaseGoogleSignIn";
                }
                return str;
            case -257959751:
                if (str.equals("dynamiclink")) {
                    return "FirebaseDynamicLink";
                }
                return str;
            case -75883448:
                if (str.equals("youtubeview")) {
                    return "YoutubePlayer";
                }
                return str;
            case -7230027:
                if (str.equals("cardview")) {
                    return "CardView";
                }
                return str;
            case 5318500:
                if (str.equals("radiogroup")) {
                    return "RadioGroup";
                }
                return str;
            case 94842723:
                if (str.equals("color")) {
                    return "Color";
                }
                return str;
            case 710961803:
                if (str.equals("fbadinterstitial")) {
                    return "FBAdsInterstitial";
                }
                return str;
            case 893026343:
                if (str.equals("textinputlayout")) {
                    return "TextInputLayout";
                }
                return str;
            case 1096269585:
                if (str.equals("collapsingtoolbar")) {
                    return "CollapsingToolbarLayout";
                }
                return str;
            case 1177398322:
                if (str.equals("cloudmessage")) {
                    return "FirebaseCloudMessage";
                }
                return str;
            case 1234536982:
                if (str.equals("resource_bg")) {
                    return "BackgroundImage";
                }
                return str;
            case 1351679420:
                if (str.equals("datepicker")) {
                    return "DatePicker";
                }
                return str;
            case 1612926363:
                if (str.equals("timepicker")) {
                    return "TimePicker";
                }
                return str;
            case 2107542731:
                if (str.equals("swiperefreshlayout")) {
                    return "SwipeRefreshLayout";
                }
                return str;
            default:
                return str;
        }
    }

    public static String b(String str) {
        switch (str.hashCode()) {
            case -1787283314:
                if (str.equals("onesignal")) {
                    return "p";
                }
                break;
            case -1028606954:
                if (str.equals("phoneauth")) {
                    return "p";
                }
                break;
            case -897829429:
                if (str.equals("fbadbanner")) {
                    return "p";
                }
                break;
            case -322859824:
                if (str.equals("googlelogin")) {
                    return "p";
                }
                break;
            case -257959751:
                if (str.equals("dynamiclink")) {
                    return "p";
                }
                break;
            case 710961803:
                if (str.equals("fbadinterstitial")) {
                    return "p";
                }
                break;
            case 1177398322:
                if (str.equals("cloudmessage")) {
                    return "p";
                }
                break;
        }
        return "v";
    }
}
