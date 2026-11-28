package mod.hilal.saif.android_manifest;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.lC;
import a.a.a.wq;
import a.a.a.yB;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileResConfig;
import pro.sketchware.utility.FileUtil;

public class ManifestPermissionSync {
    private static final String TAG = "ManifestPermissionSync";
    private static final Pattern PERMISSION_PATTERN = Pattern.compile(
            "<uses-permission(?:-sdk-23)?\\s+[^>]*?android:name\\s*=\\s*[\"']([^\"']+)[\"'][^>]*?>",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Extracts all permission names declared in the given manifest XML.
     */
    public static List<String> extractPermissionsFromXml(String xml) {
        List<String> list = new ArrayList<>();
        if (xml == null || xml.isEmpty()) return list;
        Matcher matcher = PERMISSION_PATTERN.matcher(xml);
        while (matcher.find()) {
            String perm = matcher.group(1);
            if (perm != null) {
                perm = perm.trim();
                if (!perm.isEmpty() && !list.contains(perm)) {
                    list.add(perm);
                }
            }
        }
        return list;
    }

    /**
     * Reads available manifest files for this project, extracts all permissions,
     * merges them with the existing permission list in .sketchware/data/{sc_id}/permission,
     * and saves the updated list.
     */
    public static List<String> syncManifestToPermissionList(String scId) {
        if (scId == null || scId.isEmpty()) return new ArrayList<>();

        Set<String> allPermissions = new LinkedHashSet<>();

        // 1. Existing permissions from FileResConfig / permission file
        try {
            FileResConfig frc = new FileResConfig(scId);
            if (frc.getPermissionList() != null) {
                allPermissions.addAll(frc.getPermissionList());
            }
        } catch (Exception ignored) {}

        // 2. Read from manual_manifest.xml
        File manualManifest = AndroidManifestInjector.getPathManualManifest(scId);
        if (manualManifest.exists() && manualManifest.length() > 0) {
            String xml = FileUtil.readFile(manualManifest.getAbsolutePath());
            allPermissions.addAll(extractPermissionsFromXml(xml));
        }

        // 3. Read from dataPath/AndroidManifest.xml
        String dataPath = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + scId;
        File dataManifest = new File(dataPath, "AndroidManifest.xml");
        if (dataManifest.exists() && dataManifest.length() > 0) {
            String xml = FileUtil.readFile(dataManifest.getAbsolutePath());
            allPermissions.addAll(extractPermissionsFromXml(xml));
        }

        // 4. Read from mysc Manifest
        try {
            File myscManifest = new File(wq.d(scId), "app/src/main/AndroidManifest.xml");
            if (myscManifest.exists() && myscManifest.length() > 0) {
                String xml = FileUtil.readFile(myscManifest.getAbsolutePath());
                allPermissions.addAll(extractPermissionsFromXml(xml));
            }
        } catch (Exception ignored) {}

        List<String> result = new ArrayList<>(allPermissions);
        String permissionPath = new FilePathUtil().getPathPermission(scId);
        try {
            File permFile = new File(permissionPath);
            if (permFile.getParentFile() != null && !permFile.getParentFile().exists()) {
                permFile.getParentFile().mkdirs();
            }
            FileUtil.writeFile(permissionPath, new Gson().toJson(result));
        } catch (Exception e) {
            Log.e(TAG, "Failed to write permissions list", e);
        }

        return result;
    }

    /**
     * Synchronizes a list of permissions into the project's manual_manifest.xml (and mirrors if present).
     */
    public static void syncPermissionListToManifest(String scId, List<String> permissions) {
        if (scId == null || scId.isEmpty()) return;
        if (permissions == null) permissions = new ArrayList<>();

        File manualManifest = AndroidManifestInjector.getPathManualManifest(scId);
        String dataPath = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + scId;
        File dataManifest = new File(dataPath, "AndroidManifest.xml");
        File myscManifest = null;
        try {
            myscManifest = new File(wq.d(scId), "app/src/main/AndroidManifest.xml");
        } catch (Exception ignored) {}

        String manifestContent = null;
        if (manualManifest.exists() && manualManifest.length() > 0) {
            manifestContent = FileUtil.readFile(manualManifest.getAbsolutePath());
        } else if (dataManifest.exists() && dataManifest.length() > 0) {
            manifestContent = FileUtil.readFile(dataManifest.getAbsolutePath());
        }

        if (manifestContent == null || manifestContent.trim().isEmpty() || !manifestContent.contains("<manifest")) {
            String pkg = null;
            try {
                pkg = yB.c(lC.b(scId), "my_sc_pkg_name");
            } catch (Exception ignored) {}
            if (pkg == null || pkg.isEmpty()) pkg = "com.my.project";

            manifestContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                    "    package=\"" + pkg + "\">\n\n" +
                    "    <application\n" +
                    "        android:allowBackup=\"true\"\n" +
                    "        android:icon=\"@mipmap/ic_launcher\"\n" +
                    "        android:label=\"@string/app_name\"\n" +
                    "        android:theme=\"@style/AppTheme\">\n" +
                    "        <activity\n" +
                    "            android:name=\".MainActivity\"\n" +
                    "            android:exported=\"true\">\n" +
                    "            <intent-filter>\n" +
                    "                <action android:name=\"android.intent.action.MAIN\" />\n" +
                    "                <category android:name=\"android.intent.category.LAUNCHER\" />\n" +
                    "            </intent-filter>\n" +
                    "        </activity>\n" +
                    "    </application>\n" +
                    "</manifest>\n";
        }

        String updatedManifest = updatePermissionsInManifest(manifestContent, permissions);

        try {
            if (manualManifest.getParentFile() != null && !manualManifest.getParentFile().exists()) {
                manualManifest.getParentFile().mkdirs();
            }
            FileUtil.writeFile(manualManifest.getAbsolutePath(), updatedManifest);
        } catch (Exception e) {
            Log.e(TAG, "Failed to write manual manifest", e);
        }

        try {
            if (dataManifest.exists() && dataManifest.getParentFile() != null) {
                FileUtil.writeFile(dataManifest.getAbsolutePath(), updatedManifest);
            }
        } catch (Exception ignored) {}

        try {
            if (myscManifest != null && myscManifest.exists() && myscManifest.getParentFile() != null) {
                FileUtil.writeFile(myscManifest.getAbsolutePath(), updatedManifest);
            }
        } catch (Exception ignored) {}
    }

    /**
     * Updates permission tags inside the given manifest XML to match targetPermissions exactly.
     */
    public static String updatePermissionsInManifest(String manifestXml, List<String> targetPermissions) {
        if (manifestXml == null) return "";
        if (targetPermissions == null) targetPermissions = new ArrayList<>();

        List<String> currentPermissions = extractPermissionsFromXml(manifestXml);
        String result = manifestXml;

        for (String cur : currentPermissions) {
            if (!targetPermissions.contains(cur)) {
                String patternStr = "(?m)^[ \\t]*<uses-permission(?:-sdk-23)?\\s+[^>]*?android:name\\s*=\\s*[\"']"
                        + Pattern.quote(cur) + "[\"'][^>]*?>[ \\t]*(?:\\r?\\n)?";
                result = result.replaceAll(patternStr, "");
            }
        }

        List<String> remaining = extractPermissionsFromXml(result);
        StringBuilder newTags = new StringBuilder();
        for (String target : targetPermissions) {
            if (!remaining.contains(target)) {
                newTags.append("    <uses-permission android:name=\"").append(target).append("\" />\n");
            }
        }

        if (newTags.length() > 0) {
            Matcher appMatcher = Pattern.compile("<application[\\s>]", Pattern.CASE_INSENSITIVE).matcher(result);
            if (appMatcher.find()) {
                int index = appMatcher.start();
                result = result.substring(0, index) + newTags + "\n    " + result.substring(index);
            } else {
                int endManifest = result.lastIndexOf("</manifest>");
                if (endManifest != -1) {
                    result = result.substring(0, endManifest) + newTags + result.substring(endManifest);
                } else {
                    result = result + "\n" + newTags;
                }
            }
        }

        return result;
    }

    /**
     * Injects missing permissions from FileResConfig into the given manifest string.
     */
    public static String injectMissingPermissions(String scId, String manifestXml) {
        if (manifestXml == null || manifestXml.isEmpty()) return manifestXml;
        try {
            FileResConfig frc = new FileResConfig(scId);
            List<String> list = frc.getPermissionList();
            if (list == null || list.isEmpty()) return manifestXml;

            List<String> existing = extractPermissionsFromXml(manifestXml);
            StringBuilder sb = new StringBuilder();
            for (String perm : list) {
                if (!existing.contains(perm)) {
                    sb.append("    <uses-permission android:name=\"").append(perm).append("\" />\n");
                }
            }
            if (sb.length() > 0) {
                Matcher appMatcher = Pattern.compile("<application[\\s>]", Pattern.CASE_INSENSITIVE).matcher(manifestXml);
                if (appMatcher.find()) {
                    int index = appMatcher.start();
                    return manifestXml.substring(0, index) + sb + "\n    " + manifestXml.substring(index);
                } else {
                    int endManifest = manifestXml.lastIndexOf("</manifest>");
                    if (endManifest != -1) {
                        return manifestXml.substring(0, endManifest) + sb + manifestXml.substring(endManifest);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error injecting missing permissions", e);
        }
        return manifestXml;
    }
}
