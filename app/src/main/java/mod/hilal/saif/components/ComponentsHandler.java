package mod.hilal.saif.components;

import com.besome.sketch.beans.ComponentBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
//responsible code :
//ComponentBean == sketchware / beans √
//Manage components == agus /component √
//Manage events components == agus/editor/event √
//TypeVarComponent == agus / lib √
//TypeClassComponent == agus/lib √
//importClass== dev.aldi.sayuti.editor.manage

public class ComponentsHandler {

    //√ give typeName and return id
    public static int id(final String name) {
        if (name.equals("AsyncTask")) {
            return 36;
        }

        final String path = getPath();
        ArrayList<HashMap<String, Object>> data;

        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                final int len = data.size();
                for (int i = 0; i < len; i++) {
                    final String c = (String) data.get(i).get("typeName");
                    if (name.equals(c)) {
                        return Integer.parseInt((String) data.get(i).get("id"));
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }

    }

    // √ give id and return typeName
    public static String typeName(int id) {
        if (id == 36) {
            return "AsyncTask";
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return (String) data.get(i).get("typeName");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    //√ give id and return name
    public static String name(int id) {
        if (id == 36) {
            return "AsyncTask";
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return (String) data.get(i).get("name");
                    }
                }
            }
            return "component";
        } catch (Exception e) {
            return "component";
        }
    }

    //√ give id and return icon
    public static int icon(int id) {
        if (id == 36) {
            return 2131165726;
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return Integer.parseInt((String) data.get(i).get("icon"));
                    }
                }
            }
            return 2131165469;
        } catch (Exception e) {
            return 2131165469;
        }
    }

    // give id and return description
    //goto ComponentAddActivity
    //remove lines: 2303 to 2307
    //call this method using v0 as id and move result to v0
    public static String description(int id) {
        switch (id) {
            case 1:
                return "Intent is used to start a new Activity";

            case 2:
                return "File is used to save data locally";

            case 3:
                return "Calendar is used to calculate date and time";

            case 4:
                return "Vibrator is used to vibrate the device";

            case 5:
                return "Timer is used to delay a certain actions";

            case 6:
                return "Firebase Realtime Database is Google's cloud-based NoSQL database, where you can save and sync data realtime";

            case 7:
                return "Dialog is used to create a pop-up";

            case 8:
                return "MediaPlayer is used to play big sound files";

            case 9:
                return "SoundPool is used to play short sound effects";

            case 10:
                return "ObjectAnimator is used to animate certain properties of a View";

            case 11:
                return "The gyroscope measures the rate of rotation in rad/s around a device's x, y and z axis";

            case 12:
                return "Firebase Auth allows online user authentication";

            case 13:
                return "Interstitial Ad lets you show fullscreen advertisements";

            case 14:
                return "Firebase Storage is built for app developers who need to store and serve user-generated content";

            case 15:
                return "Camera is used to take a picture";

            case 16:
                return "FilePicker is used to select raw and media files, such as images, sounds and text";

            case 17:
                return "RequestNetwork is used to make Web API calls";

            case 18:
                return "TextToSpeech is used to convert text to speech";

            case 19:
                return "SpeechToText is used to convert speech to text";

            case 20:
                return "BluetoothConnect is used to connect to another device via Bluetooth";

            case 21:
                return "LocationManager is used to get data from the current location";

            case 22:
                return "RewardedVideoAd is used to show AdMob video advertisements";

            case 23:
                return "ProgressDialog is a pop-up dialog with progress style";

            case 24:
                return "DatePickerDialog is used to pick a date";

            case 25:
                return "TimePickerDialog is used to pick a time";

            case 26:
                return "Notification is used to create a notification";

            case 27:
                return "FragmentAdapter is used with TabLayout and ViewPager to manage each page";

            case 28:
                return "Firebase Phone is used with Firebase Auth to sign into your app with a phone number";

            case 29:
                return "Dynamic Links are smart URLs that allow you to send existing and potential users to any location within your app";

            case 30:
                return "Firebase Cloud Messaging is used to send and receive push notifications";

            case 31:
                return "Firebase Google Sign is used with Firebase Auth to sign into your app with a Google account";

            case 32:
                return "OneSignal is the market leader in customer engagement, powering mobile push, web push, email, and in-app messages";

            case 33:
                return "You can monetize your App with Facebook Ads Banner.";

            case 34:
                return "You can monetize your App with Facebook Ads.";

            case 36:
                return "AsyncTask is used to perform heavy tasks in the background while keeping the UI thread and application more responsive";

            default:
                return description2(id);
        }
    }

    public static String description2(int id) {
        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return (String) data.get(i).get("description");
                    }
                }
            }
            return "new component";
        } catch (Exception e) {
            return "new component";
        }
    }

    // √give id and return docs url
    public static String docs(int id) {
        if (id == 36) {
            return "";
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return (String) data.get(i).get("url");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    //√ give id and return buildclass
    public static String c(int id) {
        if (id == 36) {
            return "AsyncTask";
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return (String) data.get(i).get("buildClass");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    // mod •••••••••••••••••••••••••••••••

    // √ add components to sk
    //structure : list.add(new ComponentBean(27));
    public static void add(ArrayList<ComponentBean> list) {
        list.add(new ComponentBean(36));
        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    list.add(new ComponentBean(c));
                }
            }
        } catch (Exception ignored) {
        }
    }

    //√√√ give id and return variable name
    public static String var(int id) {
        if (id == 36) {
            return "#";
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    int c = Integer.parseInt((String) data.get(i).get("id"));
                    if (id == c) {
                        return (String) data.get(i).get("varName");
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    //√√ give typeName and return class
    public static String c(String name) {
        if (name.equals("AsyncTask")) {
            return "Component.AsyncTask";
        }

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    String c = (String) data.get(i).get("typeName");
                    if (name.equals(c)) {
                        return (String) data.get(i).get("class");
                    }
                }
            }
            return "Component";
        } catch (Exception e) {
            return "Component";
        }
    }

    //√√
    public static String extraVar(String name, String code, String varName) {

        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    String c = (String) data.get(i).get("name");
                    if (name.equals(c)) {
                        if (!data.get(i).get("additionalVar").equals("")) {
                            return code + "\r\n" + ((String) data.get(i).get("additionalVar")).replace("###", varName);
                        } else {
                            return code;
                        }
                    }
                }
            }
            return code;
        } catch (Exception e) {
            return code;
        }
    }

    //√√
    public static String defineExtraVar(String name, String varName) {
        String path = getPath();
        ArrayList<HashMap<String, Object>> data;
        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    String c = (String) data.get(i).get("name");
                    if (name.equals(c)) {
                        if (!data.get(i).get("defineAdditionalVar").equals("")) {
                            return ((String) data.get(i).get("defineAdditionalVar")).replace("###", varName);
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

    public static void getImports(String name, ArrayList<String> arrayList) {
        String path = getPath();
        ArrayList<HashMap<String, Object>> data;

        try {
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
                for (int i = 0; i < data.size(); i++) {
                    String c = (String) data.get(i).get("varName");
                    if (name.equals(c)) {
                        if (!data.get(i).get("imports").equals("")) {
                            ArrayList<String> temp = new ArrayList<>(Arrays.asList(((String) data.get(i).get("imports")).split("\n")));
                            arrayList.addAll(temp);
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }

    public static String getPath() {
        return FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/component.json");
    }
}
