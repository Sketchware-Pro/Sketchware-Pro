package mod.hilal.saif.components;

import com.besome.sketch.beans.ComponentBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mod.agus.jcoderz.lib.FileUtil;

public class ComponentsHandler {
    public static int id(String str) {
        if (str.equals("AsyncTask")) {
            return 36;
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (str.equals((String) ((HashMap) arrayList.get(i)).get("typeName"))) {
                        return Integer.valueOf((String) ((HashMap) arrayList.get(i)).get("id")).intValue();
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static String typeName(int i) {
        if (i == 36) {
            return "AsyncTask";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return (String) ((HashMap) arrayList.get(i2)).get("typeName");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String name(int i) {
        if (i == 36) {
            return "AsyncTask";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    /* class mod.hilal.saif.components.ComponentsHandler.AnonymousClass3 */
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return (String) ((HashMap) arrayList.get(i2)).get("name");
                    }
                }
            }
            return "component";
        } catch (Exception e) {
            return "component";
        }
    }

    public static int icon(int i) {
        if (i == 36) {
            return 2131165726;
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("icon")).intValue();
                    }
                }
            }
            return 2131165469;
        } catch (Exception e) {
            return 2131165469;
        }
    }

    public static String description(int i) {
        switch (i) {
            case 1:
                return "Intent is used to start new activity";
            case 2:
                return "File is used to save data locally";
            case 3:
                return "Calendar is used to calculate date and time";
            case 4:
                return "Vibrator is used to vibrate the device";
            case 5:
                return "Timer to delay a certain action";
            case 6:
                return "Firebase Realtime Database is Google's cloud-based NoSQL database, where you can save and sync data realtime";
            case 7:
                return "Dialog is used to create popup";
            case 8:
                return "MediaPlayer is used to play larg music file";
            case 9:
                return "SoundPool is used to play FX sounds";
            case 10:
                return "ObjectAnimator is used to animate widgets";
            case 11:
                return "Gyroscope is used to get x , y and z axis of the phone";
            case 12:
                return "Firebase Auth allows online user authentication";
            case 13:
                return "Interstitial Ad is used to show admob ads";
            case 14:
                return "Firebase Storage is built for app developers who need to store and serve user-generated content";
            case 15:
                return "Camera is used to take a picture";
            case 16:
                return "FilePicker is used to pick a file from Device storage such as text, images and sounds";
            case 17:
                return "RequestNetwork is used to make web api calls";
            case 18:
                return "TextToSpeech is used to convert text to speech";
            case 19:
                return "SpeechToText is used to convert speech to text";
            case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return "BluetoothConnect is used to connect your device to another via Bluetooth";
            case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER:
                return "LocationManager is used to load data from current location";
            case 22:
                return "Video Ad is used to show admob video ads";
            case 23:
                return "ProgressDialog is a popup with progress style";
            case 24:
                return "DatePickerDialog is used to pick a date";
            case 25:
                return "TimePickerDialog is used to pick a time";
            case 26:
                return "Notification is used to create notification";
            case 27:
                return "FragmentStatePagerAdapter used with TabLayout and ViewPager to manage each page";
            case 28:
                return "Firebase Phone Auth lets you add login with phone number like WhatsApp";
            case 29:
                return "Dynamic Links are smart URLs that allow you to send existing and potential users to any location within your app.";
            case 30:
                return "Firebase FCM : Send Notification With Firebase Cloud Messaging";
            case 31:
                return "Firebase Google Sign : Firebase Auth With Google Sign in";
            case 32:
                return "OneSignal is the market leader in customer engagement, powering mobile push, web push, email, and in-app messages.";
            case 33:
                return "You can monetize your App with Facebook Ads Banner.";
            case 34:
                return "You can monetize your App with Facebook Ads.";
            default:
                return description2(i);
        }
    }

    public static String description2(int i) {
        if (i == 36) {
            return "Android AsyncTask is an abstract class provided by Android which gives us the liberty to perform heavy tasks in the background and keep the UI thread light thus making the application more responsive.";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return (String) ((HashMap) arrayList.get(i2)).get("description");
                    }
                }
            }
            return "new component";
        } catch (Exception e) {
            return "new component";
        }
    }

    public static String docs(int i) {
        if (i == 36) {
            return "";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return (String) ((HashMap) arrayList.get(i2)).get("url");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String c(int i) {
        if (i == 36) {
            return "AsyncTask";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return (String) ((HashMap) arrayList.get(i2)).get("buildClass");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static void add(ArrayList arrayList) {
        arrayList.add(new ComponentBean(36));
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList2 = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < arrayList2.size(); i++) {
                    arrayList.add(new ComponentBean(Integer.valueOf((String) ((HashMap) arrayList2.get(i)).get("id")).intValue()));
                }
            }
        } catch (Exception e) {
        }
    }

    public static String var(int i) {
        if (i == 36) {
            return "#";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (i == Integer.valueOf((String) ((HashMap) arrayList.get(i2)).get("id")).intValue()) {
                        return (String) ((HashMap) arrayList.get(i2)).get("varName");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String c(String str) {
        if (str.equals("AsyncTask")) {
            return "Component.AsyncTask";
        }
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < arrayList.size(); i++) {
                    if (str.equals((String) ((HashMap) arrayList.get(i)).get("typeName"))) {
                        return (String) ((HashMap) arrayList.get(i)).get("class");
                    }
                }
            }
            return "Component";
        } catch (Exception e) {
            return "Component";
        }
    }

    public static String extraVar(String str, String str2, String str3) {
        String path = getPath();
        new ArrayList();
        try {
            if (!FileUtil.isExistFile(path)) {
                return str2;
            }
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList.size(); i++) {
                if (str.equals((String) ((HashMap) arrayList.get(i)).get("name"))) {
                    if (!((String) ((HashMap) arrayList.get(i)).get("additionalVar")).equals("")) {
                        return str2 + "\r\n" + ((String) ((HashMap) arrayList.get(i)).get("additionalVar")).replace("###", str3);
                    } else {
                        return str2;
                    }
                }
            }
            return str2;
        } catch (Exception e) {
            return str2;
        }
    }

    public static String defineExtraVar(String str, String str2) {
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < arrayList.size(); i++) {
                    if (str.equals((String) ((HashMap) arrayList.get(i)).get("name"))) {
                        if (!((String) ((HashMap) arrayList.get(i)).get("defineAdditionalVar")).equals("")) {
                            return ((String) ((HashMap) arrayList.get(i)).get("defineAdditionalVar")).replace("###", str2);
                        } else {
                            return "";
                        }
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static void getImports(String str, ArrayList<String> arrayList) {
        String path = getPath();
        new ArrayList();
        try {
            if (FileUtil.isExistFile(path)) {
                ArrayList arrayList2 = (ArrayList) new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < arrayList2.size(); i++) {
                    if (str.equals((String) ((HashMap) arrayList2.get(i)).get("varName")) && !((String) ((HashMap) arrayList2.get(i)).get("imports")).equals("")) {
                        ArrayList arrayList3 = new ArrayList(Arrays.asList(((String) ((HashMap) arrayList2.get(i)).get("imports")).split("\n")));
                        for (int i2 = 0; i2 < arrayList3.size(); i2++) {
                            arrayList.add((String) arrayList3.get(i2));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static String getPath() {
        return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/component.json");
    }
}
