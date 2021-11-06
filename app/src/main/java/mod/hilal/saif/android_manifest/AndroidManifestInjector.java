package mod.hilal.saif.android_manifest;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import a.a.a.Nx;
import mod.agus.jcoderz.lib.FileUtil;

public class AndroidManifestInjector {

    public static void getP(Nx nx, String id) {
        ArrayList<String> listPermission = new ArrayList<>();
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        try {
            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(id).concat("/Injection/androidmanifest/attributes.json");
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        String str = (String) data.get(i).get("name");
                        String str2 = (String) data.get(i).get("value");
                        if (str.equals("_application_permissions")) {
                            Nx nx2 = new Nx("uses-permission");
                            //nx2.a("android", "name", str2);
                            nx2.b(str2);
                            nx.a(nx2);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static void getAppAttrs(Nx nx, String projectId) {
        addToApp(nx, projectId);
    }

    public static boolean getActivityAttrs(Nx nx, String projectId, String actName) {
        try {
            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/attributes.json");
            ArrayList<HashMap<String, Object>> data = new ArrayList<>();
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    String temp = actName.substring(0, actName.indexOf(".java"));
                    if (data.get(i).get("name").equals(temp)) {
                        //nx.b((String)data.get(i).get("value"));
                        addToAct(nx, projectId, actName);
                        return true;

                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isActivityThemeUsed(Nx nx, String projectId, String actName) {
        try {
            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/attributes.json");
            ArrayList<HashMap<String, Object>> data = new ArrayList<>();
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    String temp = actName.substring(0, actName.indexOf(".java"));
                    if (data.get(i).get("name").equals(temp) || data.get(i).get("name").equals("_apply_for_all_activities")) {
                        //nx.b((String)data.get(i).get("value"));
                        if (((String) data.get(i).get("value")).contains("android:theme")) {
                            return true;
                        }
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isActivityOrientationUsed(Nx nx, String projectId, String actName) {
        try {
            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/attributes.json");
            ArrayList<HashMap<String, Object>> data = new ArrayList<>();
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    String temp = actName.substring(0, actName.indexOf(".java"));
                    if (data.get(i).get("name").equals(temp) || data.get(i).get("name").equals("_apply_for_all_activities")) {
                        //nx.b((String)data.get(i).get("value"));
                        if (((String) data.get(i).get("value")).contains("android:screenOrientation")) {
                            return true;
                        }
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isActivityKeyboardUsed(Nx nx, String projectId, String actName) {
        try {
            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/attributes.json");
            ArrayList<HashMap<String, Object>> data = new ArrayList<>();
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    String temp = actName.substring(0, actName.indexOf(".java"));
                    if (data.get(i).get("name").equals(temp) || data.get(i).get("name").equals("_apply_for_all_activities")) {
                        //nx.b((String)data.get(i).get("value"));
                        if (((String) data.get(i).get("value")).contains("android:windowSoftInputMode")) {
                            return true;
                        }
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getLauncherActivity(String projectId) {

        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/activity_launcher.txt");
        if (FileUtil.isExistFile(path)) {
            String str = FileUtil.readFile(path);
            if (str.contains(" ") || str.contains(".")) {
                //return "main";
                return "main";
            } else {
                //return "main";
                return str;
            }
        }
        //return "main";
        return "main";
    }

    public static void setLauncherActivity(String projectId, String a) {
        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/activity_launcher.txt");
        FileUtil.writeFile(path, a);
    }


    public static String mHolder(String m, String projectId) {
        ArrayList<String> m_list = new ArrayList<>();
        m_list = new ArrayList<String>(Arrays.asList(m.split("\n")));

        String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/activities_components.json");
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < data.size(); i++) {
                String name = (String) data.get(i).get("name");
                String value = (String) data.get(i).get("value");
                if (!value.trim().equals("")) {
                    for (int k = 3; k < m_list.size(); k++) {
                        String line = m_list.get(k);
                        String _line = m_list.get(k - 1);
                        if (line.contains("android:name=\"") && line.contains(name) && _line.contains("<activity")) {
                            for (int q = k; q < m_list.size(); q++) {
                                String v = m_list.get(q);
                                String v2 = m_list.get(q - 1);
                                if (v.matches("^		<[a-zA-Z_-]+[^>]")) {
                                    if (v2.contains("\"/>")) {
                                        if (!value.trim().equals("")) {
                                            m_list.set(q - 1, m_list.get(q - 1).replace("\"/>", "\">"));
                                            m_list.set(q - 1, m_list.get(q - 1).concat("\r\n").concat((String) data.get(i).get("value")).concat("\r\n").concat("</activity>"));
                                            break;
                                        }
                                    } else {
                                        if (!value.trim().equals("")) {
                                            m_list.set(q - 2, m_list.get(q - 2).concat("\r\n").concat((String) data.get(i).get("value")));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String path2 = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/app_components.txt");
        if (FileUtil.isExistFile(path2)) {
            if (!FileUtil.readFile(path2).trim().equals("")) {
                String str = FileUtil.readFile(path2);
                String str2 = m_list.get(m_list.size() - 3);
                String str3 = str2.concat("\r\n").concat(str);
                m_list.set(m_list.size() - 3, str3);
            }
        }
        //assemble
        String return_value = "";
        for (int k = 0; k < m_list.size(); k++) {
            return_value = return_value.concat("\n").concat(m_list.get(k));
        }
        return return_value;
    }


    public static void addToApp(Nx nx, String projectId) {
        try {
            boolean isthemeused = false;
            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/attributes.json");
            ArrayList<HashMap<String, Object>> data = new ArrayList<>();
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    String str = (String) data.get(i).get("name");
                    String str2 = (String) data.get(i).get("value");
                    if (str.equals("_application_attrs")) {
                        nx.b((String) data.get(i).get("value"));
                        if (str2.contains("android:theme")) {
                            isthemeused = true;
                        }
                    }
                }
                if (!isthemeused) {
                    nx.b("android:theme=\"@style/AppTheme\"");
                }
            } else {
                nx.b("android:theme=\"@style/AppTheme\"");
            }
        } catch (Exception e) {
        }
    }

    public static void addToAct(Nx nx, String projectId, String actName) {
        try {

            String path = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(projectId).concat("/Injection/androidmanifest/attributes.json");
            ArrayList<HashMap<String, Object>> data = new ArrayList<>();
            if (FileUtil.isExistFile(path)) {
                data = new Gson().fromJson(FileUtil.readFile(path), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType());
                for (int i = 0; i < data.size(); i++) {
                    String temp = actName.substring(0, actName.indexOf(".java"));
                    if (data.get(i).get("name").equals(temp) || data.get(i).get("name").equals("_apply_for_all_activities")) {
                        nx.b((String) data.get(i).get("value"));
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
