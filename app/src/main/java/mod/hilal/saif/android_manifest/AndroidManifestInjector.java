package mod.hilal.saif.android_manifest;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import a.a.a.Nx;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class AndroidManifestInjector {

    public static File getPathAndroidManifestAttributeInjection(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware" + File.separator + "data" + File.separator + sc_id + File.separator +
                        "Injection" + File.separator + "androidmanifest" + File.separator + "attributes.json");
    }

    public static File getPathAndroidManifestLauncherActivity(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware" + File.separator + "data" + File.separator + sc_id + File.separator +
                        "Injection" + File.separator + "androidmanifest" + File.separator + "activity_launcher.txt");
    }

    public static File getPathAndroidManifestActivitiesComponents(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware" + File.separator + "data" + File.separator + sc_id + File.separator +
                        "Injection" + File.separator + "androidmanifest" + File.separator + "activities_components.json");
    }

    public static File getPathAndroidManifestAppComponents(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(),
                ".sketchware" + File.separator + "data" + File.separator + sc_id + File.separator +
                        "Injection" + File.separator + "androidmanifest" + File.separator + "app_components.txt");
    }

    private static ArrayList<HashMap<String, Object>> readAndroidManifestAttributeInjections(String sc_id) {
        ArrayList<HashMap<String, Object>> attributes;

        parseAttributes:
        {
            String errorMessage;

            try {
                File injections = getPathAndroidManifestAttributeInjection(sc_id);

                if (injections.exists()) {
                    attributes = new Gson().fromJson(FileUtil.readFile(injections.getAbsolutePath()), Helper.TYPE_MAP_LIST);

                    if (attributes == null) {
                        errorMessage = "result == null";
                        // fall-through for shared error handling
                    } else {
                        break parseAttributes;
                    }
                } else {
                    return new ArrayList<>();
                }
            } catch (JsonParseException e) {
                errorMessage = e.toString();
                // fall-through for shared error handling
            }

            attributes = new ArrayList<>();
            SketchwareUtil.toastError("Failed to parse AndroidManifest attribute injections; Reason: " + errorMessage);
        }

        return attributes;
    }

    public static void getP(Nx nx, String id) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(id);

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                Object value = attribute.get("value");

                if (value instanceof String) {
                    if ("_application_permissions".equals(name)) {
                        Nx usesPermissionTag = new Nx("uses-permission");
                        usesPermissionTag.b((String) value);
                        nx.a(usesPermissionTag);
                    }
                } else {
                    SketchwareUtil.toastError("Invalid AndroidManifest attribute injection value in attribute #" + (i + 1));
                }
            } else {
                SketchwareUtil.toastError("Invalid AndroidManifest attribute injection name in attribute #" + (i + 1));
            }
        }
    }

    public static void getAppAttrs(Nx nx, String projectId) {
        addToApp(nx, projectId);
    }

    public static boolean getActivityAttrs(Nx nx, String projectId, String actName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = actName.substring(0, actName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name)) {
                    addToAct(nx, projectId, actName);
                    return true;
                }
            } else {
                SketchwareUtil.toastError("Invalid AndroidManifest attribute injection name in attribute #" + (i + 1));
            }
        }

        return false;
    }

    public static boolean isActivityThemeUsed(Nx nx, String projectId, String actName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = actName.substring(0, actName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name) || "_apply_for_all_activities".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        if (((String) value).contains("android:theme")) {
                            return true;
                        }
                    } else {
                        SketchwareUtil.toastError("Invalid AndroidManifest attribute injection value in attribute #" + (i + 1));
                    }
                }
            }
        }

        return false;
    }

    public static boolean isActivityOrientationUsed(Nx nx, String projectId, String actName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = actName.substring(0, actName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name) || "_apply_for_all_activities".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        if (((String) value).contains("android:screenOrientation")) {
                            return true;
                        }
                    } else {
                        SketchwareUtil.toastError("Invalid AndroidManifest attribute injection value in attribute #" + (i + 1));
                    }
                }
            } else {
                SketchwareUtil.toastError("Invalid AndroidManifest attribute injection name in attribute #" + (i + 1));
            }
        }

        return false;
    }

    public static boolean isActivityKeyboardUsed(Nx nx, String projectId, String actName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = actName.substring(0, actName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name) || "_apply_for_all_activities".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        if (((String) value).contains("android:windowSoftInputMode")) {
                            return true;
                        }
                    } else {
                        SketchwareUtil.toastError("Invalid AndroidManifest attribute injection value in attribute #" + (i + 1));
                    }
                }
            } else {
                SketchwareUtil.toastError("Invalid AndroidManifest attribute injection name in attribute #" + (i + 1));
            }
        }

        return false;
    }

    public static String getLauncherActivity(String projectId) {
        File launcherActivityFile = getPathAndroidManifestLauncherActivity(projectId);

        if (launcherActivityFile.exists()) {
            String launcherActivity = FileUtil.readFile(launcherActivityFile.getAbsolutePath());

            if (!launcherActivity.contains(" ") && !launcherActivity.contains(".")) {
                return launcherActivity;
            }
        }

        return "main";
    }

    public static void setLauncherActivity(String projectId, String a) {
        FileUtil.writeFile(getPathAndroidManifestLauncherActivity(projectId).getAbsolutePath(),
                a);
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
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);

        boolean themeInjected = false;
        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if ("_application_attrs".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        nx.b((String) value);

                        if (!themeInjected && ((String) value).contains("android:theme")) {
                            themeInjected = true;
                        }
                    } else {
                        SketchwareUtil.toastError("Invalid AndroidManifest attribute injection value in attribute #" + (i + 1));
                    }
                }
            } else {
                SketchwareUtil.toastError("Invalid AndroidManifest attribute injection name in attribute #" + (i + 1));
            }
        }

        if (!themeInjected) {
            nx.b("android:theme=\"@style/AppTheme\"");
        }
    }

    public static void addToAct(Nx nx, String projectId, String actName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = actName.substring(0, actName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name) || "_apply_for_all_activities".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        nx.b((String) value);
                    } else {
                        SketchwareUtil.toastError("Invalid AndroidManifest attribute injection value in attribute #" + (i + 1));
                    }
                }
            } else {
                SketchwareUtil.toastError("Invalid AndroidManifest attribute injection name in attribute #" + (i + 1));
            }
        }
    }
}
