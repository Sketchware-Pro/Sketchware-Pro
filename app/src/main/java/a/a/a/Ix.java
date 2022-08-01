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

    public Nx a = new Nx("manifest");
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
        a.a("xmlns", "android", "http://schemas.android.com/apk/res/android");
    }

    /**
     * Adds FileProvider metadata to AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link Nx} object
     */
    private void writeFileProvider(Nx applicationTag) {
        Nx providerTag = new Nx("provider");
        providerTag.a("android", "authorities", c.packageName + ".provider");
        providerTag.a("android", "name", "androidx.core.content.FileProvider");
        providerTag.a("android", "exported", "false");
        providerTag.a("android", "grantUriPermissions", "true");
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "android.support.FILE_PROVIDER_PATHS");
        metadataTag.a("android", "resource", "@xml/provider_paths");
        providerTag.a(metadataTag);
        applicationTag.a(providerTag);
    }

    /**
     * Adds a permission to AndroidManifest.
     *
     * @param manifestTag    AndroidManifest {@link Nx} object
     * @param permissionName The {@code uses-permission} {@link Nx} tag
     */
    private void writePermission(Nx manifestTag, String permissionName) {
        Nx usesPermissionTag = new Nx("uses-permission");
        usesPermissionTag.a("android", "name", permissionName);
        manifestTag.a(usesPermissionTag);
    }

    /**
     * Adds Firebase metadata to AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link Nx} object
     */
    private void writeFirebaseMetaData(Nx applicationTag) {
        Nx providerTag = new Nx("provider");
        providerTag.a("android", "name", "com.google.firebase.provider.FirebaseInitProvider");
        providerTag.a("android", "authorities", c.packageName + ".firebaseinitprovider");
        providerTag.a("android", "exported", "false");
        providerTag.a("android", "initOrder", "100");
        applicationTag.a(providerTag);
        Nx serviceTag = new Nx("service");
        serviceTag.a("android", "name", "com.google.firebase.components.ComponentDiscoveryService");
        serviceTag.a("android", "exported", "false");
        if (c.isFirebaseAuthUsed) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.auth.FirebaseAuthRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.isFirebaseDatabaseUsed) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.database.DatabaseRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.isFirebaseStorageUsed) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.storage.StorageRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.isDynamicLinkUsed) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.dynamiclinks.internal.FirebaseDynamicLinkRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.x.isFCMUsed) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.iid.Registrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        applicationTag.a(serviceTag);
    }

    /**
     * Adds the Google Maps SDK API key metadata to AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link Nx} object
     */
    private void writeGoogleMapMetaData(Nx applicationTag) {
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "com.google.android.geo.API_KEY");
        metadataTag.a("android", "value", "@string/google_maps_key");
        applicationTag.a(metadataTag);
    }

    /**
     * Specifies in AndroidManifest that the app uses Apache HTTP legacy library.
     *
     * @param applicationTag AndroidManifest {@link Nx} object
     */
    private void writeLegacyLibrary(Nx applicationTag) {
        Nx usesLibraryTag = new Nx("uses-library");
        usesLibraryTag.a("android", "name", "org.apache.http.legacy");
        usesLibraryTag.a("android", "required", "false");
        applicationTag.a(usesLibraryTag);
    }

    /**
     * Adds metadata about the GMS library version (a resource integer).
     *
     * @param applicationTag {@link Nx} object to add the {@code meta-data} tag to
     */
    private void writeGMSVersion(Nx applicationTag) {
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "com.google.android.gms.version");
        metadataTag.a("android", "value", "@integer/google_play_services_version");
        applicationTag.a(metadataTag);
    }

    /**
     * Registers a {@link BroadcastReceiver} in AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link Nx} object
     * @param receiverName   The component name of the broadcast
     * @see ComponentName
     */
    private void writeBroadcast(Nx applicationTag, String receiverName) {
        Nx receiverTag = new Nx("receiver");
        receiverTag.a("android", "name", receiverName);
        Nx intentFilterTag = new Nx("intent-filter");
        Nx actionTag = new Nx("action");
        actionTag.a("android", "name", receiverName);
        intentFilterTag.a(actionTag);
        if (targetsSdkVersion31OrHigher) {
            receiverTag.a("android", "exported", "true");
        }
        receiverTag.a(intentFilterTag);
        applicationTag.a(receiverTag);
    }

    private void writeAdmobAppId(Nx applicationTag) {
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "com.google.android.gms.ads.APPLICATION_ID");
        metadataTag.a("android", "value", c.appId);
        applicationTag.a(metadataTag);
    }

    /**
     * Registers a {@link Service} in AndroidManifest.
     *
     * @param applicationTag AndroidManifest {@link Nx} object
     * @param serviceName    The component name of the service
     */
    private void writeService(Nx applicationTag, String serviceName) {
        Nx serviceTag = new Nx("service");
        serviceTag.a("android", "name", serviceName);
        serviceTag.a("android", "enabled", "true");
        applicationTag.a(serviceTag);
    }

    private void writeDLIntentFilter(Nx activityTag) {
        Nx intentFilterTag = new Nx("intent-filter");
        Nx intentFilterActionTag = new Nx("action");
        intentFilterActionTag.a("android", "name", "android.intent.action.VIEW");
        Nx intentFilterCategoryDefaultTag = new Nx("category");
        intentFilterCategoryDefaultTag.a("android", "name", "android.intent.category.DEFAULT");
        Nx intentFilterCategoryBrowsableTag = new Nx("category");
        intentFilterCategoryBrowsableTag.a("android", "name", "android.intent.category.BROWSABLE");
        intentFilterTag.a(intentFilterActionTag);
        intentFilterTag.a(intentFilterCategoryDefaultTag);
        intentFilterTag.a(intentFilterCategoryBrowsableTag);
        for (Pair<String, String> stringStringPair : c.dlDataList) {
            if (!isEmpty(stringStringPair.first) && !isEmpty(stringStringPair.second)) {
                Nx intentFilterDataTag = new Nx("data");
                intentFilterDataTag.a("android", "host", stringStringPair.first);
                intentFilterDataTag.a("android", "scheme", stringStringPair.second);
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
        a.a("", "package", c.packageName);

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

        Nx applicationTag = new Nx("application");
        applicationTag.a("android", "allowBackup", "true");
        applicationTag.a("android", "icon", "@drawable/app_icon");
        applicationTag.a("android", "label", "@string/app_name");

        String applicationClassName = settings.getValue(ProjectSettings.SETTING_APPLICATION_CLASS, ".SketchApplication");
        if (c.isDebugBuild || !applicationClassName.equals(".SketchApplication") ||
                new File(fpu.getPathJava(c.sc_id), "SketchApplication.java").exists()) {
            applicationTag.a("android", "name", applicationClassName);
        }
        if (addRequestLegacyExternalStorage) {
            applicationTag.a("android", "requestLegacyExternalStorage", "true");
        }
        if (!buildSettings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            applicationTag.a("android", "usesCleartextTraffic", "true");
        }
        AndroidManifestInjector.getAppAttrs(applicationTag, c.sc_id);

        for (ProjectFileBean projectFileBean : b) {
            if (!projectFileBean.fileName.contains("_fragment")) {
                Nx activityTag = new Nx("activity");

                String javaName = projectFileBean.getJavaName();
                activityTag.a("android", "name", "." + javaName.substring(0, javaName.indexOf(".java")));

                if (!AndroidManifestInjector.getActivityAttrs(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    activityTag.a("android", "configChanges", "orientation|screenSize|keyboardHidden|smallestScreenSize|screenLayout");
                    activityTag.a("android", "hardwareAccelerated", "true");
                    activityTag.a("android", "supportsPictureInPicture", "true");
                }
                if (!AndroidManifestInjector.isActivityThemeUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    if (c.g) {
                        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN)) {
                            activityTag.a("android", "theme", "@style/AppTheme.FullScreen");
                        }
                    } else if (projectFileBean.hasActivityOption(ProjectFileBean.PROJECT_FILE_TYPE_DRAWER)) {
                        if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                            activityTag.a("android", "theme", "@style/NoStatusBar");
                        } else {
                            activityTag.a("android", "theme", "@style/FullScreen");
                        }
                    } else if (!projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_TOOLBAR)) {
                        activityTag.a("android", "theme", "@style/NoActionBar");
                    }
                }
                if (!AndroidManifestInjector.isActivityOrientationUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    int orientation = projectFileBean.orientation;
                    if (orientation == ProjectFileBean.ORIENTATION_PORTRAIT) {
                        activityTag.a("android", "screenOrientation", "portrait");
                    } else if (orientation == ProjectFileBean.ORIENTATION_LANDSCAPE) {
                        activityTag.a("android", "screenOrientation", "landscape");
                    }
                }
                if (!AndroidManifestInjector.isActivityKeyboardUsed(activityTag, c.sc_id, projectFileBean.getJavaName())) {
                    String keyboardSetting = vq.a(projectFileBean.keyboardSetting);
                    if (keyboardSetting.length() > 0) {
                        activityTag.a("android", "windowSoftInputMode", keyboardSetting);
                    }
                }
                if (projectFileBean.fileName.equals(AndroidManifestInjector.getLauncherActivity(c.sc_id))) {
                    Nx intentFilterTag = new Nx("intent-filter");
                    Nx actionTag = new Nx("action");
                    actionTag.a("android", "name", Intent.ACTION_MAIN);
                    intentFilterTag.a(actionTag);
                    Nx categoryTag = new Nx("category");
                    categoryTag.a("android", "name", Intent.CATEGORY_LAUNCHER);
                    intentFilterTag.a(categoryTag);
                    if (targetsSdkVersion31OrHigher && !AndroidManifestInjector.isActivityExportedUsed(c.sc_id, javaName)) {
                        activityTag.a("android", "exported", "true");
                    }
                    activityTag.a(intentFilterTag);
                } else if (c.isDynamicLinkUsed) {
                    if (targetsSdkVersion31OrHigher && !AndroidManifestInjector.isActivityExportedUsed(c.sc_id, javaName)) {
                        activityTag.a("android", "exported", "false");
                    }
                    writeDLIntentFilter(activityTag);
                }
                applicationTag.a(activityTag);
            }
        }
        {
            Nx activityTag = new Nx("activity");
            activityTag.a("android", "name", ".DebugActivity");
            activityTag.a("android", "screenOrientation", "portrait");
            applicationTag.a(activityTag);
        }
        if (c.isAdMobEnabled) {
            Nx activityTag = new Nx("activity");
            activityTag.a("android", "name", "com.google.android.gms.ads.AdActivity");
            activityTag.a("android", "configChanges", "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize");
            activityTag.a("android", "theme", "@android:style/Theme.Translucent");
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

    private void writeJava(Nx applicationTag, String activityName, ArrayList<HashMap<String, Object>> activityAttrs) {
        Nx activityTag = new Nx("activity");
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
                        activityTag.b(value);
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
            activityTag.a("android", "name", activityName);
        }
        if (!specifiedConfigChanges) {
            activityTag.a("android", "configChanges", "orientation|screenSize");
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
