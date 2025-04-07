package mod.hilal.saif.android_manifest;

import static pro.sketchware.utility.GsonUtils.getGson;

import android.os.Environment;

import com.google.gson.JsonParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import mod.hey.studios.util.Helper;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.xml.XmlBuilder;

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
                    attributes = getGson().fromJson(FileUtil.readFile(injections.getAbsolutePath()), Helper.TYPE_MAP_LIST);

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

    public static void getP(XmlBuilder nx, String id) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(id);

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                Object value = attribute.get("value");

                if (value instanceof String) {
                    if ("_application_permissions".equals(name)) {
                        XmlBuilder usesPermissionTag = new XmlBuilder("uses-permission");
                        usesPermissionTag.addAttributeValue((String) value);
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

    public static void getAppAttrs(XmlBuilder nx, String projectId) {
        addToApp(nx, projectId);
    }

    public static boolean getActivityAttrs(XmlBuilder nx, String projectId, String actName) {
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

    public static boolean isActivityThemeUsed(XmlBuilder nx, String projectId, String actName) {
        return isActivityAttributeUsed("android:theme", projectId, actName);
    }

    public static boolean isActivityOrientationUsed(XmlBuilder nx, String projectId, String actName) {
        return isActivityAttributeUsed("android:screenOrientation", projectId, actName);
    }

    public static boolean isActivityKeyboardUsed(XmlBuilder nx, String projectId, String actName) {
        return isActivityAttributeUsed("android:windowSoftInputMode", projectId, actName);
    }

    public static boolean isActivityExportedUsed(String sc_id, String activityName) {
        return isActivityAttributeUsed("android:exported", sc_id, activityName);
    }

    public static boolean isActivityAttributeUsed(String attribute, String sc_id, String activityName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(sc_id);
        String className = activityName.substring(0, activityName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attributeMap = attributes.get(i);
            Object name = attributeMap.get("name");

            if (name instanceof String) {
                if (className.equals(name) || "_apply_for_all_activities".equals(name)) {
                    Object value = attributeMap.get("value");

                    if (value instanceof String) {
                        if (((String) value).contains(attribute)) {
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
        ArrayList<String> manifestLines = new ArrayList<>(Arrays.asList(m.split("\n")));

        String path = getPathAndroidManifestActivitiesComponents(projectId).getAbsolutePath();
        if (FileUtil.isExistFile(path)) {
            ArrayList<HashMap<String, Object>> data = getGson().fromJson(FileUtil.readFile(path), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < data.size(); i++) {
                HashMap<String, Object> activityComponents = data.get(i);

                Object name = activityComponents.get("name");

                if (name instanceof String) {
                    Object value = activityComponents.get("value");

                    if (value instanceof String) {
                        if (!((String) value).trim().isEmpty()) {
                            for (int k = 3; k < manifestLines.size(); k++) {
                                String line = manifestLines.get(k);
                                String _line = manifestLines.get(k - 1);
                                if (line.contains("android:name=\"") && line.contains((String) name) && _line.contains("<activity")) {
                                    for (int q = k; q < manifestLines.size(); q++) {
                                        String v = manifestLines.get(q);
                                        String v2 = manifestLines.get(q - 1);
                                        if (v.matches("^		<[a-zA-Z_-]+[^>]")) {
                                            boolean hasShortClosing = false, spaceBeforeClosing = false;

                                            if (v2.contains("\"/>")) {
                                                hasShortClosing = true;
                                            } else if (v2.contains("\" />")) {
                                                hasShortClosing = true;
                                                spaceBeforeClosing = true;
                                            }

                                            if (hasShortClosing) {
                                                manifestLines.set(q - 1, manifestLines.get(q - 1).replace(spaceBeforeClosing ? "\" />" : "\"/>", "\">"));
                                                manifestLines.set(q - 1, manifestLines.get(q - 1) + "\r\n" + value + "\r\n" + "</activity>");
                                            } else {
                                                manifestLines.set(q - 2, manifestLines.get(q - 2) + "\r\n" + value);
                                            }
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

        File appComponents = getPathAndroidManifestAppComponents(projectId);
        if (appComponents.exists()) {
            String appComponentsContent;
            if (!(appComponentsContent = FileUtil.readFile(appComponents.getAbsolutePath())).trim().isEmpty()) {
                String str2 = manifestLines.get(manifestLines.size() - 3);
                String str3 = str2 + "\r\n" + appComponentsContent;
                manifestLines.set(manifestLines.size() - 3, str3);
            }
        }

        //assemble
        StringBuilder returnValue = new StringBuilder();
        for (String manifestLine : manifestLines) {
            returnValue.append("\n").append(manifestLine);
        }
        return returnValue.toString();
    }

    public static void addToApp(XmlBuilder nx, String projectId) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);

        boolean themeInjected = false;
        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if ("_application_attrs".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        nx.addAttributeValue((String) value);

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
            nx.addAttributeValue("android:theme=\"@style/AppTheme\"");
        }
    }

    public static void addToAct(XmlBuilder nx, String projectId, String actName) {
        ArrayList<HashMap<String, Object>> attributes = readAndroidManifestAttributeInjections(projectId);
        String className = actName.substring(0, actName.indexOf(".java"));

        for (int i = 0; i < attributes.size(); i++) {
            HashMap<String, Object> attribute = attributes.get(i);
            Object name = attribute.get("name");

            if (name instanceof String) {
                if (className.equals(name) || "_apply_for_all_activities".equals(name)) {
                    Object value = attribute.get("value");

                    if (value instanceof String) {
                        nx.addAttributeValue((String) value);
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
