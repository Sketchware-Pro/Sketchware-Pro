package a.a.a;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.text.TextUtils;

import com.besome.sketch.beans.ProjectFileBean;
import com.google.gson.Gson;

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
     * @param nx AndroidManifest {@link Nx} object
     * @return The {@code provider} {@link Nx} tag
     */
    public final Nx a(Nx nx) {
        Nx providerTag = new Nx("provider");
        providerTag.a("android", "authorities", c.a + ".provider");
        providerTag.a("android", "name", "androidx.core.content.FileProvider");
        providerTag.a("android", "exported", "false");
        providerTag.a("android", "grantUriPermissions", "true");
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "android.support.FILE_PROVIDER_PATHS");
        metadataTag.a("android", "resource", "@xml/provider_paths");
        providerTag.a(metadataTag);
        nx.a(providerTag);
        return providerTag;
    }

    /**
     * Adds a permission to AndroidManifest.
     *
     * @param nx  AndroidManifest {@link Nx} object
     * @param str The {@code uses-permission} {@link Nx} tag
     */
    public final void a(Nx nx, String str) {
        Nx usesPermissionTag = new Nx("uses-permission");
        usesPermissionTag.a("android", "name", str);
        nx.a(usesPermissionTag);
    }

    /**
     * Adds Firebase metadata to AndroidManifest.
     *
     * @param nx AndroidManifest {@link Nx} object
     * @return The {@code provider} {@link Nx} tag
     */
    public final Nx b(Nx nx) {
        Nx providerTag = new Nx("provider");
        providerTag.a("android", "name", "com.google.firebase.provider.FirebaseInitProvider");
        providerTag.a("android", "authorities", c.a + ".firebaseinitprovider");
        providerTag.a("android", "exported", "false");
        providerTag.a("android", "initOrder", "100");
        nx.a(providerTag);
        Nx serviceTag = new Nx("service");
        serviceTag.a("android", "name", "com.google.firebase.components.ComponentDiscoveryService");
        serviceTag.a("android", "exported", "false");
        if (c.i) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.auth.FirebaseAuthRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.j) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.database.DatabaseRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        if (c.k) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.storage.StorageRegistrar");
            metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
            serviceTag.a(metadataTag);
        }
        ConstVarManifest.handleMetadata(serviceTag, c.x);
        nx.a(serviceTag);
        return providerTag;
    }

    /**
     * Adds the Google Maps SDK API key metadata to AndroidManifest.
     *
     * @param nx AndroidManifest {@link Nx} object
     * @return The {@code meta-data} {@link Nx} tag
     */
    public final Nx c(Nx nx) {
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "com.google.android.geo.API_KEY");
        metadataTag.a("android", "value", "@string/google_maps_key");
        nx.a(metadataTag);
        return metadataTag;
    }

    /**
     * Specifies in AndroidManifest that the app uses Apache HTTP legacy library.
     *
     * @param nx AndroidManifest {@link Nx} object
     * @return The {@code uses-library} {@link Nx} tag
     */
    public final Nx d(Nx nx) {
        Nx usesLibraryTag = new Nx("uses-library");
        usesLibraryTag.a("android", "name", "org.apache.http.legacy");
        usesLibraryTag.a("android", "required", "false");
        nx.a(usesLibraryTag);
        return usesLibraryTag;
    }

    /**
     * Adds metadata about the GMS library version (a resource integer).
     *
     * @param nx {@link Nx} object to add the {@code meta-data} tag to
     * @return The {@code meta-data} {@link Nx} object
     */
    public final Nx e(Nx nx) {
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "com.google.android.gms.version");
        metadataTag.a("android", "value", "@integer/google_play_services_version");
        nx.a(metadataTag);
        return metadataTag;
    }

    public void f(Nx nx) {
        ConstVarManifest.handleAttrComponent(nx, c.x);
    }

    /**
     * Registers a {@link BroadcastReceiver} in AndroidManifest.
     *
     * @param nx            AndroidManifest {@link Nx} object
     * @param componentName The component name of the broadcast
     * @see ComponentName
     */
    public final void writeBroadcast(Nx nx, String componentName) {
        Nx receiverTag = new Nx("receiver");
        receiverTag.a("android", "name", componentName);
        Nx intentFilterTag = new Nx("intent-filter");
        Nx actionTag = new Nx("action");
        actionTag.a("android", "name", componentName);
        intentFilterTag.a(actionTag);
        receiverTag.a(intentFilterTag);
        nx.a(receiverTag);
    }

    private void writeAdmobAppId(Nx nx) {
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "com.google.android.gms.ads.APPLICATION_ID");
        metadataTag.a("android", "value", c.appId);
        nx.a(metadataTag);
    }

    /**
     * Registers a {@link Service} in AndroidManifest.
     *
     * @param nx  AndroidManifest {@link Nx} object
     * @param str The component name of the service
     */
    public final void writeService(Nx nx, String str) {
        Nx serviceTag = new Nx("service");
        serviceTag.a("android", "name", str);
        serviceTag.a("android", "enabled", "true");
        nx.a(serviceTag);
    }

    public void setYq(yq yqVar) {
        settings = new ProjectSettings(yqVar.b);
    }

    /**
     * Builds an AndroidManifest.
     *
     * @return The AndroidManifest as {@link String}
     */
    public String a() {
        boolean addRequestLegacyExternalStorage = false;
        a.a("", "package", c.a);

        if (!c.a()) {
            if (c.b(jq.PERMISSION_CALL_PHONE)) {
                a(a, Manifest.permission.CALL_PHONE);
            }
            if (c.b(jq.PERMISSION_INTERNET)) {
                a(a, Manifest.permission.INTERNET);
            }
            if (c.b(jq.PERMISSION_VIBRATE)) {
                a(a, Manifest.permission.VIBRATE);
            }
            if (c.b(jq.PERMISSION_ACCESS_NETWORK_STATE)) {
                a(a, Manifest.permission.ACCESS_NETWORK_STATE);
            }
            if (c.b(jq.PERMISSION_CAMERA)) {
                a(a, Manifest.permission.CAMERA);
            }
            if (c.b(jq.PERMISSION_READ_EXTERNAL_STORAGE)) {
                try {
                    if (Integer.parseInt(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, "28")) >= 28) {
                        addRequestLegacyExternalStorage = true;
                    }
                } catch (NumberFormatException ignored) {
                }
                a(a, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (c.b(jq.PERMISSION_WRITE_EXTERNAL_STORAGE)) {
                a(a, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (c.b(jq.PERMISSION_RECORD_AUDIO)) {
                a(a, Manifest.permission.RECORD_AUDIO);
            }
            if (c.b(jq.PERMISSION_BLUETOOTH)) {
                a(a, Manifest.permission.BLUETOOTH);
            }
            if (c.b(jq.PERMISSION_BLUETOOTH_ADMIN)) {
                a(a, Manifest.permission.BLUETOOTH_ADMIN);
            }
            if (c.b(jq.PERMISSION_ACCESS_FINE_LOCATION)) {
                a(a, Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }
        if (FileUtil.isExistFile(fpu.getPathPermission(c.sc_id))) {
            for (String s : frc.getPermissionList()) {
                a(a, s);
            }
        }
        ConstVarManifest.handlePermissionComponent(a, c.x);
        AndroidManifestInjector.getP(a, c.sc_id);

        Nx applicationTag = new Nx("application");
        applicationTag.a("android", "allowBackup", "true");
        applicationTag.a("android", "icon", "@drawable/app_icon");
        applicationTag.a("android", "label", "@string/app_name");
        applicationTag.a("android", "name",
                settings.getValue(ProjectSettings.SETTING_APPLICATION_CLASS, ".SketchApplication"));
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
                f(activityTag);
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
                    activityTag.a(intentFilterTag);
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
        if (c.l) {
            Nx activityTag = new Nx("activity");
            activityTag.a("android", "name", "com.google.android.gms.ads.AdActivity");
            activityTag.a("android", "configChanges", "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize");
            activityTag.a("android", "theme", "@android:style/Theme.Translucent");
            applicationTag.a(activityTag);
        }
        if (c.h || c.l || c.m) {
            e(applicationTag);
        }
        if (c.h) {
            b(applicationTag);
        }
        if (c.u) {
            a(applicationTag);
        }
        if (c.l && !TextUtils.isEmpty(c.appId)) {
            writeAdmobAppId(applicationTag);
        }
        if (c.m) {
            c(applicationTag);
        }
        ConstVarManifest.handleBgTaskComponent(applicationTag, c.x);
        if (FileUtil.isExistFile(fpu.getManifestJava(c.sc_id))) {
            ArrayList<HashMap<String, Object>> activityAttrs = getActivityAttrs();
            for (String s : frc.getJavaManifestList()) {
                writeJava(applicationTag, s, activityAttrs);
            }
        }
        if (buildSettings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_FALSE)) {
            d(applicationTag);
        }
        if (FileUtil.isExistFile(fpu.getManifestService(c.sc_id))) {
            for (String s : frc.getServiceManifestList()) {
                writeService(applicationTag, s);
            }
        }
        if (FileUtil.isExistFile(fpu.getManifestBroadcast(c.sc_id))) {
            for (String s : frc.getBroadcastManifestList()) {
                writeBroadcast(applicationTag, s);
            }
        }
        a.a(applicationTag);
        return AndroidManifestInjector.mHolder(a.b(), c.sc_id);
    }

    public final void writeJava(Nx nx, String activityName, ArrayList<HashMap<String, Object>> arrayList) {
        Nx activityTag = new Nx("activity");
        boolean specifiedActivityName = false;
        boolean specifiedConfigChanges = false;
        for (HashMap<String, Object> hashMap : arrayList) {
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
        nx.a(activityTag);
    }

    public ArrayList<HashMap<String, Object>> getActivityAttrs() {
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
