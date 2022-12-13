package a.a.a;

import static android.text.TextUtils.isEmpty;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Pair;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.gson.Gson;
import com.sketchware.remod.xml.XmlBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mod.agus.jcoderz.handle.component.ConstVarManifest;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileResConfig;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;

public class Ix {

    public XmlBuilder a = new XmlBuilder("manifest");
    public ArrayList<ProjectFileBean> b;
    public BuildSettings buildSettings;
    public jq c;
    public FilePathUtil fpu = new FilePathUtil();
    public FileResConfig frc;
    public ProjectSettings settings;
    private boolean targetsSdkVersion31OrHigher = false;
    private String packageName;

    public Ix(jq jq, ArrayList<ProjectFileBean> projectFileBeans) {
        c = jq;
        b = projectFileBeans;
        buildSettings = new BuildSettings(jq.sc_id);
        frc = new FileResConfig(c.sc_id);
        a.addAttribute("xmlns", "android", "http://schemas.android.com/apk/res/android");
    }

    /**
     * Adds FileProvider metadata to AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link XmlBuilder} object
     */
    private void writeFileProvider(XmlBuilder applicationTag) {
        XmlBuilder providerTag = new XmlBuilder("provider");
        providerTag.addAttribute("android", "authorities", c.packageName + ".provider");
        providerTag.addAttribute("android", "name", "androidx.core.content.FileProvider");
        providerTag.addAttribute("android", "exported", "false");
        providerTag.addAttribute("android", "grantUriPermissions", "true");
        XmlBuilder metadataTag = new XmlBuilder("meta-data");
        metadataTag.addAttribute("android", "name", "android.support.FILE_PROVIDER_PATHS");
        metadataTag.addAttribute("android", "resource", "@xml/provider_paths");
        providerTag.a(metadataTag);
        applicationTag.a(providerTag);
    }

    /**
     * Adds a permission to AndroidManifest.
     *
     * @param manifestTag    AndroidManifest {@link XmlBuilder} object
     * @param permissionName The {@code uses-permission} {@link XmlBuilder} tag
     */
    private void writePermission(XmlBuilder manifestTag, String permissionName) {
        XmlBuilder usesPermissionTag = new XmlBuilder("uses-permission");
        usesPermissionTag.addAttribute("android", "name", permissionName);
        manifestTag.a(usesPermissionTag);
    }

    /**
     * Adds Firebase metadata to AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link XmlBuilder} object
     */
    private void writeFirebaseMetaData(XmlBuilder applicationTag) {
        XmlBuilder providerTag = new XmlBuilder("provider");
        providerTag.addAttribute("android", "name", "com.google.firebase.provider.FirebaseInitProvider");
        providerTag.addAttribute("android", "authorities", c.packageName + ".firebaseinitprovider");
        providerTag.addAttribute("android", "exported", "false");
        providerTag.addAttribute("android", "initOrder", "100");
        applicationTag.a(providerTag);
        XmlBuilder serviceTag = new XmlBuilder("service");
        serviceTag.addAttribute("android", "name", "com.google.firebase.components.ComponentDiscoveryService");
        serviceTag.addAttribute("android", "exported", "false");
        if (c.isFirebaseAuthUsed) {
            XmlBuilder metadataTag = new XmlBuilder("meta-data");
            metadataTag.addAttribute("android", "name", "com.google.firebase.components:com.google.firebase.auth.FirebaseAuthRegistrar");
            metadataTag.addAttribute("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.isFirebaseDatabaseUsed) {
            XmlBuilder metadataTag = new XmlBuilder("meta-data");
            metadataTag.addAttribute("android", "name", "com.google.firebase.components:com.google.firebase.database.DatabaseRegistrar");
            metadataTag.addAttribute("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.isFirebaseStorageUsed) {
            XmlBuilder metadataTag = new XmlBuilder("meta-data");
            metadataTag.addAttribute("android", "name", "com.google.firebase.components:com.google.firebase.storage.StorageRegistrar");
            metadataTag.addAttribute("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.isDynamicLinkUsed) {
            XmlBuilder metadataTag = new XmlBuilder("meta-data");
            metadataTag.addAttribute("android", "name", "com.google.firebase.components:com.google.firebase.dynamiclinks.internal.FirebaseDynamicLinkRegistrar");
            metadataTag.addAttribute("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.x.isFCMUsed) {
            XmlBuilder metadataTag = new XmlBuilder("meta-data");
            metadataTag.addAttribute("android", "name", "com.google.firebase.components:com.google.firebase.iid.Registrar");
            metadataTag.addAttribute("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        applicationTag.a(serviceTag);
    }

    /**
     * Adds the Google Maps SDK API key metadata to AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link XmlBuilder} object
     */
    private void writeGoogleMapMetaData(XmlBuilder applicationTag) {
        XmlBuilder metadataTag = new XmlBuilder("meta-data");
        metadataTag.addAttribute("android", "name", "com.google.android.geo.API_KEY");
        metadataTag.addAttribute("android", "value", "@string/google_maps_key");
        applicationTag.a(metadataTag);
    }

    /**
     * Specifies in AndroidManifest that the app uses Apache HTTP legacy library.
     *
     * @param applicationTag AndroidManifest {@link XmlBuilder} object
     */
    private void writeLegacyLibrary(XmlBuilder applicationTag) {
        XmlBuilder usesLibraryTag = new XmlBuilder("uses-library");
        usesLibraryTag.addAttribute("android", "name", "org.apache.http.legacy");
        usesLibraryTag.addAttribute("android", "required", "false");
        applicationTag.a(usesLibraryTag);
    }

    /**
     * Adds metadata about the GMS library version (a resource integer).
     *
     * @param applicationTag {@link XmlBuilder} object to add the {@code meta-data} tag to
     */
    private void writeGMSVersion(XmlBuilder applicationTag) {
        XmlBuilder metadataTag = new XmlBuilder("meta-data");
        metadataTag.addAttribute("android", "name", "com.google.android.gms.version");
        metadataTag.addAttribute("android", "value", "@integer/google_play_services_version");
        applicationTag.a(metadataTag);
    }

    /**
     * Registers a {@link BroadcastReceiver} in AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link XmlBuilder} object
     * @param receiverName   The component name of the broadcast
     * @see ComponentName
     */
    private void writeBroadcast(XmlBuilder applicationTag, String receiverName) {
        XmlBuilder receiverTag = new XmlBuilder("receiver");
        receiverTag.addAttribute("android", "name", receiverName);
        XmlBuilder intentFilterTag = new XmlBuilder("intent-filter");
        XmlBuilder actionTag = new XmlBuilder("action");
        actionTag.addAttribute("android", "name", receiverName);
        intentFilterTag.a(actionTag);
        if (targetsSdkVersion31OrHigher) {
            receiverTag.addAttribute("android", "exported", "true");
        }
        receiverTag.a(intentFilterTag);
        applicationTag.a(receiverTag);
    }

    private void writeAdmobAppId(XmlBuilder applicationTag) {
        XmlBuilder metadataTag = new XmlBuilder("meta-data");
        metadataTag.addAttribute("android", "name", "com.google.android.gms.ads.APPLICATION_ID");
        metadataTag.addAttribute("android", "value", c.appId);
        applicationTag.a(metadataTag);
    }

    /**
     * Registers a {@link Service} in AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link XmlBuilder} object
     * @param serviceName    The component name of the service
     */
    private void writeService(XmlBuilder applicationTag, String serviceName) {
        XmlBuilder serviceTag = new XmlBuilder("service");
        serviceTag.addAttribute("android", "name", serviceName);
        serviceTag.addAttribute("android", "enabled", "true");
        applicationTag.a(serviceTag);
    }

    private void writeDLIntentFilter(XmlBuilder activityTag) {
        XmlBuilder intentFilterTag = new XmlBuilder("intent-filter");
        XmlBuilder intentFilterActionTag = new XmlBuilder("action");
        intentFilterActionTag.addAttribute("android", "name", "android.intent.action.VIEW");
        XmlBuilder intentFilterCategoryDefaultTag = new XmlBuilder("category");
        intentFilterCategoryDefaultTag.addAttribute("android", "name", "android.intent.category.DEFAULT");
        XmlBuilder intentFilterCategoryBrowsableTag = new XmlBuilder("category");
        intentFilterCategoryBrowsableTag.addAttribute("android", "name", "android.intent.category.BROWSABLE");
        intentFilterTag.a(intentFilterActionTag);
        intentFilterTag.a(intentFilterCategoryDefaultTag);
        intentFilterTag.a(intentFilterCategoryBrowsableTag);
        for (Pair<String, String> stringStringPair : c.dlDataList) {
            if (!isEmpty(stringStringPair.first) && !isEmpty(stringStringPair.second)) {
                XmlBuilder intentFilterDataTag = new XmlBuilder("data");
                intentFilterDataTag.addAttribute("android", "host", stringStringPair.first);
                intentFilterDataTag.addAttribute("android", "scheme", stringStringPair.second);
                if (c.dlDataList.size() != 0) {
                    intentFilterTag.a(intentFilterDataTag);
                }
            }
        }
        activityTag.a(intentFilterTag);
    }

    public void setYq(yq yqVar) {
        settings = new ProjectSettings(yqVar.sc_id);
        targetsSdkVersion31OrHigher = Integer.parseInt(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, "28")) >= 31;
        packageName = yqVar.packageName;
    }

    /**
     * Builds an AndroidManifest.
     *
     * @return The AndroidManifest as {@link String}
     */
    public String a() {
        boolean addRequestLegacyExternalStorage = false;
        a.addAttribute("", "package", c.packageName);

        if (!c.hasPermissions()) {
            if (c.hasPermission(jq.PERMISSION_CALL_PHONE)) {
                writePermission(a, Manifest.permission.CALL_PHONE);
            }
            if (c.hasPermission(jq.PERMISSION_INTERNET)) {
                writePermission(a, Manifest.permission.INTERNET);
            }
            if (c.hasPermission(jq.PERMISSION_VIBRATE)) {
                writePermission(a, Manifest.permission.VIBRATE);
            }
            if (c.hasPermission(jq.PERMISSION_ACCESS_NETWORK_STATE)) {
                writePermission(a, Manifest.permission.ACCESS_NETWORK_STATE);
            }
            if (c.hasPermission(jq.PERMISSION_CAMERA)) {
                writePermission(a, Manifest.permission.CAMERA);
            }
            if (c.hasPermission(jq.PERMISSION_READ_EXTERNAL_STORAGE)) {
                try {
                    if (Integer.parseInt(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, "28")) >= 28) {
                        addRequestLegacyExternalStorage = true;
                    }
                } catch (NumberFormatException ignored) {
                }
                writePermission(a, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (c.hasPermission(jq.PERMISSION_WRITE_EXTERNAL_STORAGE)) {
                writePermission(a, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (c.hasPermission(jq.PERMISSION_RECORD_AUDIO)) {
                writePermission(a, Manifest.permission.RECORD_AUDIO);
            }
            if (c.hasPermission(jq.PERMISSION_BLUETOOTH)) {
                writePermission(a, Manifest.permission.BLUETOOTH);
            }
            if (c.hasPermission(jq.PERMISSION_BLUETOOTH_ADMIN)) {
                writePermission(a, Manifest.permission.BLUETOOTH_ADMIN);
            }
            if (c.hasPermission(jq.PERMISSION_ACCESS_FINE_LOCATION)) {
                writePermission(a, Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
        if (FileUtil.isExistFile(fpu.getPathPermission(c.sc_id))) {
            for (String s : frc.getPermissionList()) {
                writePermission(a, s);
            }
        }
        ConstVarManifest.handlePermissionComponent(a, c.x);
        AndroidManifestInjector.getP(a, c.sc_id);

        XmlBuilder applicationTag = new XmlBuilder("application");
        applicationTag.addAttribute("android", "allowBackup", "true");
        applicationTag.addAttribute("android", "icon", "@drawable/app_icon");
        applicationTag.addAttribute("android", "label", "@string/app_name");

        String applicationClassName = settings.getValue(ProjectSettings.SETTING_APPLICATION_CLASS, ".SketchApplication");
        if (c.isDebugBuild || !applicationClassName.equals(".SketchApplication") ||
                new File(fpu.getPathJava(c.sc_id), "SketchApplication.java").exists()) {
            applicationTag.addAttribute("android", "name", applicationClassName);
        }
        if (addRequestLegacyExternalStorage) {
            applicationTag.addAttribute("android", "requestLegacyExternalStorage", "true");
        }
        if (!buildSettings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            applicationTag.addAttribute("android", "usesCleartextTraffic", "true");
        }
        AndroidManifestInjector.getAppAttrs(applicationTag, c.sc_id);

        for (ProjectFileBean projectFileBean : b) {
            if (!projectFileBean.fileName.contains("_fragment")) {
                XmlBuilder activityTag = new XmlBuilder("activity");

                String javaName = projectFileBean.getJavaName();
                activityTag.addAttribute("android", "name", "." + javaName.substring(0, javaName.indexOf(".java")));

                if (!AndroidManifestInjector.getActivityAttrs(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    activityTag.addAttribute("android", "configChanges", "orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout");
                    activityTag.addAttribute("android", "hardwareAccelerated", "true");
                    activityTag.addAttribute("android", "supportsPictureInPicture", "true");
                }
                if (!AndroidManifestInjector.isActivityThemeUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    if (c.g) {
                        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN)) {
                            activityTag.addAttribute("android", "theme", "@style/AppTheme.FullScreen");
                        }
                    } else if (projectFileBean.hasActivityOption(ProjectFileBean.PROJECT_FILE_TYPE_DRAWER)) {
                        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                            activityTag.addAttribute("android", "theme", "@style/NoStatusBar");
                        } else {
                            activityTag.addAttribute("android", "theme", "@style/FullScreen");
                        }
                    } else if (!projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                        activityTag.addAttribute("android", "theme", "@style/NoActionBar");
                    }
                }
                if (!AndroidManifestInjector.isActivityOrientationUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    int orientation = projectFileBean.orientation;
                    if (orientation == ProjectFileBean.ORIENTATION_PORTRAIT) {
                        activityTag.addAttribute("android", "screenOrientation", "portrait");
                    } else if (orientation == ProjectFileBean.ORIENTATION_LANDSCAPE) {
                        activityTag.addAttribute("android", "screenOrientation", "landscape");
                    }
                }
                if (!AndroidManifestInjector.isActivityKeyboardUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    String keyboardSetting = vq.a(projectFileBean.keyboardSetting);
                    if (keyboardSetting.length() > 0) {
                        activityTag.addAttribute("android", "windowSoftInputMode", keyboardSetting);
                    }
                }
                if (projectFileBean.fileName.equals(AndroidManifestInjector.getLauncherActivity(c.sc_id))) {
                    XmlBuilder intentFilterTag = new XmlBuilder("intent-filter");
                    XmlBuilder actionTag = new XmlBuilder("action");
                    actionTag.addAttribute("android", "name", Intent.ACTION_MAIN);
                    intentFilterTag.a(actionTag);
                    XmlBuilder categoryTag = new XmlBuilder("category");
                    categoryTag.addAttribute("android", "name", Intent.CATEGORY_LAUNCHER);
                    intentFilterTag.a(categoryTag);
                    if (targetsSdkVersion31OrHigher && !AndroidManifestInjector.isActivityExportedUsed(c.sc_id, javaName)) {
                        activityTag.addAttribute("android", "exported", "true");
                    }
                    activityTag.a(intentFilterTag);
                } else if (c.isDynamicLinkUsed) {
                    if (targetsSdkVersion31OrHigher && !AndroidManifestInjector.isActivityExportedUsed(c.sc_id, javaName)) {
                        activityTag.addAttribute("android", "exported", "false");
                    }
                    writeDLIntentFilter(activityTag);
                }
                applicationTag.a(activityTag);
            }
        }
        {
            XmlBuilder activityTag = new XmlBuilder("activity");
            activityTag.addAttribute("android", "name", ".DebugActivity");
            activityTag.addAttribute("android", "screenOrientation", "portrait");
            applicationTag.a(activityTag);
        }
        if (c.isAdMobEnabled) {
            XmlBuilder activityTag = new XmlBuilder("activity");
            activityTag.addAttribute("android", "name", "com.google.android.gms.ads.AdActivity");
            activityTag.addAttribute("android", "configChanges", "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize");
            activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent");
            applicationTag.a(activityTag);
        }
        if (c.isFirebaseEnabled || c.isAdMobEnabled || c.isMapUsed) {
            writeGMSVersion(applicationTag);
        }
        if (c.isFirebaseEnabled) {
            writeFirebaseMetaData(applicationTag);
        }
        if (c.u) {
            writeFileProvider(applicationTag);
        }
        if (c.isAdMobEnabled && !isEmpty(c.appId)) {
            writeAdmobAppId(applicationTag);
        }
        if (c.isMapUsed) {
            writeGoogleMapMetaData(applicationTag);
        }
        ConstVarManifest.handleBgTaskComponent(applicationTag, c.x);
        if (FileUtil.isExistFile(fpu.getManifestJava(c.sc_id))) {
            ArrayList<HashMap<String, Object>> activityAttrs = getActivityAttrs();
            for (String activityName : frc.getJavaManifestList()) {
                writeJava(applicationTag, activityName, activityAttrs);
            }
        }
        if (buildSettings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_FALSE)) {
            writeLegacyLibrary(applicationTag);
        }
        if (FileUtil.isExistFile(fpu.getManifestService(c.sc_id))) {
            for (String serviceName : frc.getServiceManifestList()) {
                writeService(applicationTag, serviceName);
            }
        }
        if (FileUtil.isExistFile(fpu.getManifestBroadcast(c.sc_id))) {
            for (String receiverName : frc.getBroadcastManifestList()) {
                writeBroadcast(applicationTag, receiverName);
            }
        }
        a.a(applicationTag);
        // Needed, as crashing on my SM-A526B with Android 12 / One UI 4.1 / firmware build A526BFXXS1CVD1 otherwise
        //noinspection RegExpRedundantEscape
        return AndroidManifestInjector.mHolder(a.toCode(), c.sc_id).replaceAll("\\$\\{applicationId\\}", packageName);
    }

    private void writeJava(XmlBuilder applicationTag, String activityName, ArrayList<HashMap<String, Object>> activityAttrs) {
        XmlBuilder activityTag = new XmlBuilder("activity");
        boolean specifiedActivityName = false;
        boolean specifiedConfigChanges = false;
        for (HashMap<String, Object> hashMap : activityAttrs) {
            if (hashMap.containsKey("name") && hashMap.containsKey("value")) {
                Object nameObject = hashMap.get("name");
                Object valueObject = hashMap.get("value");
                if (nameObject instanceof String && valueObject instanceof String) {
                    String name = nameObject.toString();
                    String value = valueObject.toString();
                    if (name.equals(activityName)) {
                        activityTag.addAttributeValue(value);
                        if (value.contains("android:name=")) {
                            specifiedActivityName = true;
                        } else if (value.contains("android:configChanges=")) {
                            specifiedConfigChanges = true;
                        }
                    }
                }
            }
        }
        if (!specifiedActivityName) {
            activityTag.addAttribute("android", "name", activityName);
        }
        if (!specifiedConfigChanges) {
            activityTag.addAttribute("android", "configChanges", "orientation|screenSize");
        }
        applicationTag.a(activityTag);
    }

    private ArrayList<HashMap<String, Object>> getActivityAttrs() {
        String activityAttributesPath = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(c.sc_id).concat("/Injection/androidmanifest/attributes.json");
        if (FileUtil.isExistFile(activityAttributesPath)) {
            try {
                return new Gson().fromJson(FileUtil.readFile(activityAttributesPath), Helper.TYPE_MAP_LIST);
            } catch (Exception ignored) {
            }
        }
        return new ArrayList<>();
    }
}
