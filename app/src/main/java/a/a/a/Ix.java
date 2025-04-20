package a.a.a;

import static android.text.TextUtils.isEmpty;
import static com.besome.sketch.Config.VAR_DEFAULT_TARGET_SDK_VERSION;

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
import java.util.Set;

import mod.agus.jcoderz.editor.manifest.EditorManifest;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.android_manifest.AndroidManifestInjector;
import mod.jbk.build.BuiltInLibraries;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileResConfig;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.xml.XmlBuilder;

public class Ix {
    private final BuiltInLibraryManager builtInLibraryManager;
    public XmlBuilder a = new XmlBuilder("manifest");
    public ArrayList<ProjectFileBean> b;
    public BuildSettings buildSettings;
    public jq c;
    public FilePathUtil fpu = new FilePathUtil();
    public FileResConfig frc;
    public ProjectSettings settings;
    private boolean targetsSdkVersion31OrHigher = false;
    private String packageName;

    public Ix(jq jq, ArrayList<ProjectFileBean> projectFileBeans, BuiltInLibraryManager builtInLibraryManager) {
        c = jq;
        b = projectFileBeans;
        this.builtInLibraryManager = builtInLibraryManager;
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
                if (!c.dlDataList.isEmpty()) {
                    intentFilterTag.a(intentFilterDataTag);
                }
            }
        }
        activityTag.a(intentFilterTag);
    }

    private void writeAndroidxRoomService(XmlBuilder application) {
        XmlBuilder invalidationService = new XmlBuilder("service");
        invalidationService.addAttribute("android", "name", "androidx.room.MultiInstanceInvalidationService");
        invalidationService.addAttribute("android", "directBootAware", "true");
        invalidationService.addAttribute("android", "exported", "false");
        application.a(invalidationService);
    }

    private void writeAndroidxStartupInitializationProvider(XmlBuilder application) {
        var initializers = Set.of(
                new Pair<>(builtInLibraryManager.containsLibrary(BuiltInLibraries.ANDROIDX_EMOJI2), "androidx.emoji2.text.EmojiCompatInitializer"),
                new Pair<>(builtInLibraryManager.containsLibrary(BuiltInLibraries.ANDROIDX_LIFECYCLE_PROCESS), "androidx.lifecycle.ProcessLifecycleInitializer"),
                new Pair<>(builtInLibraryManager.containsLibrary(BuiltInLibraries.ANDROIDX_WORK_RUNTIME), "androidx.work.WorkManagerInitializer")
        );

        if (initializers.stream().anyMatch(initializer -> initializer.first)) {
            XmlBuilder initializationProvider = new XmlBuilder("provider");
            initializationProvider.addAttribute("android", "name", "androidx.startup.InitializationProvider");
            initializationProvider.addAttribute("android", "authorities", c.packageName + ".androidx-startup");
            initializationProvider.addAttribute("android", "exported", "false");
            for (var pair : initializers) {
                if (pair.first) {
                    XmlBuilder metadata = new XmlBuilder("meta-data");
                    metadata.addAttribute("android", "name", pair.second);
                    metadata.addAttribute("android", "value", "androidx.startup");
                    initializationProvider.a(metadata);
                }
            }
            application.a(initializationProvider);
        }
    }

    private void writeAndroidxWorkRuntimeTags(XmlBuilder application) {
        XmlBuilder alarmService = new XmlBuilder("service");
        alarmService.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.SystemAlarmService");
        alarmService.addAttribute("android", "directBootAware", "false");
        alarmService.addAttribute("android", "enabled", "@bool/enable_system_alarm_service_default");
        alarmService.addAttribute("android", "exported", "false");
        application.a(alarmService);

        XmlBuilder jobService = new XmlBuilder("service");
        jobService.addAttribute("android", "name", "androidx.work.impl.background.systemjob.SystemJobService");
        jobService.addAttribute("android", "directBootAware", "false");
        jobService.addAttribute("android", "enabled", "@bool/enable_system_job_service_default");
        jobService.addAttribute("android", "exported", "true");
        jobService.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        application.a(jobService);

        XmlBuilder foregroundService = new XmlBuilder("service");
        foregroundService.addAttribute("android", "name", "androidx.work.impl.foreground.SystemForegroundService");
        foregroundService.addAttribute("android", "directBootAware", "false");
        foregroundService.addAttribute("android", "enabled", "@bool/enable_system_foreground_service_default");
        foregroundService.addAttribute("android", "exported", "false");
        application.a(foregroundService);

        XmlBuilder forceStopRunnableReceiver = new XmlBuilder("receiver");
        forceStopRunnableReceiver.addAttribute("android", "name", "androidx.work.impl.utils.ForceStopRunnable$BroadcastReceiver");
        forceStopRunnableReceiver.addAttribute("android", "directBootAware", "false");
        forceStopRunnableReceiver.addAttribute("android", "enabled", "true");
        forceStopRunnableReceiver.addAttribute("android", "exported", "false");
        application.a(forceStopRunnableReceiver);

        XmlBuilder batteryChargingReceiver = new XmlBuilder("receiver");
        batteryChargingReceiver.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.ConstraintProxy$BatteryChargingProxy");
        batteryChargingReceiver.addAttribute("android", "directBootAware", "false");
        batteryChargingReceiver.addAttribute("android", "enabled", "false");
        batteryChargingReceiver.addAttribute("android", "exported", "false");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder connectedAction = new XmlBuilder("action");
            connectedAction.addAttribute("android", "name", "android.intent.action.ACTION_POWER_CONNECTED");
            intentFilter.a(connectedAction);
            XmlBuilder disconnectedAction = new XmlBuilder("action");
            disconnectedAction.addAttribute("android", "name", "android.intent.action.ACTION_POWER_DISCONNECTED");
            intentFilter.a(disconnectedAction);
            batteryChargingReceiver.a(intentFilter);
        }
        application.a(batteryChargingReceiver);

        XmlBuilder batteryNotLowReceiver = new XmlBuilder("receiver");
        batteryNotLowReceiver.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.ConstraintProxy$BatteryNotLowProxy");
        batteryNotLowReceiver.addAttribute("android", "directBootAware", "false");
        batteryNotLowReceiver.addAttribute("android", "enabled", "false");
        batteryNotLowReceiver.addAttribute("android", "exported", "false");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder okayAction = new XmlBuilder("action");
            okayAction.addAttribute("android", "name", "android.intent.action.BATTERY_OKAY");
            intentFilter.a(okayAction);
            XmlBuilder lowAction = new XmlBuilder("action");
            lowAction.addAttribute("android", "name", "android.intent.action.BATTERY_LOW");
            intentFilter.a(lowAction);
            batteryNotLowReceiver.a(intentFilter);
        }
        application.a(batteryNotLowReceiver);

        XmlBuilder storageNotLowReceiver = new XmlBuilder("receiver");
        storageNotLowReceiver.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.ConstraintProxy$StorageNotLowProxy");
        storageNotLowReceiver.addAttribute("android", "directBootAware", "false");
        storageNotLowReceiver.addAttribute("android", "enabled", "false");
        storageNotLowReceiver.addAttribute("android", "exported", "false");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder lowAction = new XmlBuilder("action");
            lowAction.addAttribute("android", "name", "android.intent.action.DEVICE_STORAGE_LOW");
            intentFilter.a(lowAction);
            XmlBuilder okAction = new XmlBuilder("action");
            okAction.addAttribute("android", "name", "android.intent.action.DEVICE_STORAGE_OK");
            intentFilter.a(okAction);
            storageNotLowReceiver.a(intentFilter);
        }
        application.a(storageNotLowReceiver);

        XmlBuilder networkStateReceiver = new XmlBuilder("receiver");
        networkStateReceiver.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.ConstraintProxy$NetworkStateProxy");
        networkStateReceiver.addAttribute("android", "directBootAware", "false");
        networkStateReceiver.addAttribute("android", "enabled", "false");
        networkStateReceiver.addAttribute("android", "exported", "false");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder action = new XmlBuilder("action");
            action.addAttribute("android", "name", "android.net.conn.CONNECTIVITY_CHANGE");
            intentFilter.a(action);
            networkStateReceiver.a(intentFilter);
        }
        application.a(networkStateReceiver);

        XmlBuilder rescheduleReceiver = new XmlBuilder("receiver");
        rescheduleReceiver.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.RescheduleReceiver");
        rescheduleReceiver.addAttribute("android", "directBootAware", "false");
        rescheduleReceiver.addAttribute("android", "enabled", "false");
        rescheduleReceiver.addAttribute("android", "exported", "false");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder bootCompletedAction = new XmlBuilder("action");
            bootCompletedAction.addAttribute("android", "name", "android.intent.action.BOOT_COMPLETED");
            intentFilter.a(bootCompletedAction);
            XmlBuilder timeSetAction = new XmlBuilder("action");
            timeSetAction.addAttribute("android", "name", "android.intent.action.TIME_SET");
            intentFilter.a(timeSetAction);
            XmlBuilder timezoneChangedAction = new XmlBuilder("action");
            timezoneChangedAction.addAttribute("android", "name", "android.intent.action.TIMEZONE_CHANGED");
            intentFilter.a(timezoneChangedAction);
            rescheduleReceiver.a(intentFilter);
        }
        application.a(rescheduleReceiver);

        XmlBuilder proxyUpdateReceiver = new XmlBuilder("receiver");
        proxyUpdateReceiver.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.ConstraintProxyUpdateReceiver");
        proxyUpdateReceiver.addAttribute("android", "directBootAware", "false");
        proxyUpdateReceiver.addAttribute("android", "enabled", "@bool/enable_system_alarm_service_default");
        proxyUpdateReceiver.addAttribute("android", "exported", "false");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder action = new XmlBuilder("action");
            action.addAttribute("android", "name", "androidx.work.impl.background.systemalarm.UpdateProxies");
            intentFilter.a(action);
            proxyUpdateReceiver.a(intentFilter);
        }
        application.a(proxyUpdateReceiver);

        XmlBuilder diagnosticsReceiver = new XmlBuilder("receiver");
        diagnosticsReceiver.addAttribute("android", "name", "androidx.work.impl.diagnostics.DiagnosticsReceiver");
        diagnosticsReceiver.addAttribute("android", "directBootAware", "false");
        diagnosticsReceiver.addAttribute("android", "enabled", "true");
        diagnosticsReceiver.addAttribute("android", "exported", "true");
        diagnosticsReceiver.addAttribute("android", "permission", "android.permission.DUMP");
        {
            XmlBuilder intentFilter = new XmlBuilder("intent-filter");
            XmlBuilder action = new XmlBuilder("action");
            action.addAttribute("android", "name", "androidx.work.diagnostics.REQUEST_DIAGNOSTICS");
            intentFilter.a(action);
            diagnosticsReceiver.a(intentFilter);
        }
        application.a(diagnosticsReceiver);
    }

    public void setYq(yq yqVar) {
        settings = new ProjectSettings(yqVar.sc_id);
        targetsSdkVersion31OrHigher = Integer.parseInt(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, String.valueOf(VAR_DEFAULT_TARGET_SDK_VERSION))) >= 31;
        packageName = yqVar.packageName;
    }

    /**
     * Builds an AndroidManifest.
     *
     * @return The AndroidManifest as {@link String}
     */
    public String a() {
        int targetSdkVersion;
        try {
            targetSdkVersion = Integer.parseInt(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, String.valueOf(VAR_DEFAULT_TARGET_SDK_VERSION)));
        } catch (NumberFormatException ignored) {
            targetSdkVersion = VAR_DEFAULT_TARGET_SDK_VERSION;
        }
        boolean addRequestLegacyExternalStorage = targetSdkVersion >= 28;

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
        if (c.isAdMobEnabled) {
            writePermission(a, "com.google.android.gms.permission.AD_ID");
        }
        if (builtInLibraryManager.containsLibrary(BuiltInLibraries.ANDROIDX_WORK_RUNTIME)) {
            writePermission(a, "android.permission.WAKE_LOCK");
            writePermission(a, "android.permission.ACCESS_NETWORK_STATE");
            writePermission(a, "android.permission.RECEIVE_BOOT_COMPLETED");
            writePermission(a, "android.permission.FOREGROUND_SERVICE");
        }
        if (c.x.isFCMUsed) {
            writePermission(a, Manifest.permission.WAKE_LOCK);
            writePermission(a, "com.google.android.c2dm.permission.RECEIVE");
        }
        if (c.x.isOneSignalUsed) {
            XmlBuilder permission = new XmlBuilder("permission");
            permission.addAttribute("android", "name", packageName + ".permission.C2D_MESSAGE");
            permission.addAttribute("android", "protectionLevel", "signature");
            a.a(permission);
            writePermission(a, packageName + ".permission.C2D_MESSAGE");
            writePermission(a, Manifest.permission.WAKE_LOCK);
            writePermission(a, Manifest.permission.VIBRATE);
            writePermission(a, Manifest.permission.RECEIVE_BOOT_COMPLETED);
            writePermission(a, "com.sec.android.provider.badge.permission.READ");
            writePermission(a, "com.sec.android.provider.badge.permission.WRITE");
            writePermission(a, "com.htc.launcher.permission.READ_SETTINGS");
            writePermission(a, "com.htc.launcher.permission.UPDATE_SHORTCUT");
            writePermission(a, "com.sonyericsson.home.permission.BROADCAST_BADGE");
            writePermission(a, "com.sonymobile.home.permission.PROVIDER_INSERT_BADGE");
            writePermission(a, "com.anddoes.launcher.permission.UPDATE_COUNT");
            writePermission(a, "com.majeur.launcher.permission.UPDATE_BADGE");
            writePermission(a, "com.huawei.android.launcher.permission.CHANGE_BADGE");
            writePermission(a, "com.huawei.android.launcher.permission.READ_SETTINGS");
            writePermission(a, "com.huawei.android.launcher.permission.WRITE_SETTINGS");
            writePermission(a, "android.permission.READ_APP_BADGE");
            writePermission(a, "com.oppo.launcher.permission.READ_SETTINGS");
            writePermission(a, "com.oppo.launcher.permission.WRITE_SETTINGS");
            writePermission(a, "me.everything.badger.permission.BADGE_COUNT_READ");
            writePermission(a, "me.everything.badger.permission.BADGE_COUNT_WRITE");
        }
        AndroidManifestInjector.getP(a, c.sc_id);

        if (c.isAdMobEnabled || c.isTextToSpeechUsed || c.isSpeechToTextUsed) {
            XmlBuilder queries = new XmlBuilder("queries");
            if (c.isAdMobEnabled) {
                XmlBuilder forBrowserContent = new XmlBuilder("intent");
                {
                    XmlBuilder action = new XmlBuilder("action");
                    action.addAttribute("android", "name", "android.intent.action.VIEW");
                    forBrowserContent.a(action);
                    XmlBuilder category = new XmlBuilder("category");
                    category.addAttribute("android", "name", "android.intent.category.BROWSABLE");
                    forBrowserContent.a(category);
                    XmlBuilder data = new XmlBuilder("data");
                    data.addAttribute("android", "scheme", "https");
                    forBrowserContent.a(data);
                }
                queries.a(forBrowserContent);
                XmlBuilder forCustomTabsService = new XmlBuilder("intent");
                {
                    XmlBuilder action = new XmlBuilder("action");
                    action.addAttribute("android", "name", "android.support.customtabs.action.CustomTabsService");
                    forCustomTabsService.a(action);
                }
                queries.a(forCustomTabsService);
            }
            if (c.isTextToSpeechUsed && targetSdkVersion >= 30) {
                XmlBuilder intent = new XmlBuilder("intent");
                XmlBuilder action = new XmlBuilder("action");
                action.addAttribute("android", "name", "android.intent.action.TTS_SERVICE");
                intent.a(action);
                queries.a(intent);
            }
            if (c.isSpeechToTextUsed && targetSdkVersion >= 30) {
                XmlBuilder intent = new XmlBuilder("intent");
                XmlBuilder action = new XmlBuilder("action");
                action.addAttribute("android", "name", "android.speech.RecognitionService");
                intent.a(action);
                queries.a(intent);
            }
            a.a(queries);
        }

        XmlBuilder applicationTag = new XmlBuilder("application");
        applicationTag.addAttribute("android", "allowBackup", "true");
        applicationTag.addAttribute("android", "icon", "@mipmap/ic_launcher");
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

        boolean hasDebugActivity = false;
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
                    } else if (projectFileBean.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_FULLSCREEN)) {
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
                    if (!keyboardSetting.isEmpty()) {
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
            if (projectFileBean.fileName.equals("debug")) {
                hasDebugActivity = true;
            }
        }

        if (!hasDebugActivity) {
            XmlBuilder activityTag = new XmlBuilder("activity");
            activityTag.addAttribute("android", "name", ".DebugActivity");
            activityTag.addAttribute("android", "screenOrientation", "portrait");
            activityTag.addAttribute("android", "theme", "@style/AppTheme.DebugActivity");
            applicationTag.a(activityTag);
        }
        if (c.isAdMobEnabled) {
            XmlBuilder activityTag = new XmlBuilder("activity");
            activityTag.addAttribute("android", "name", "com.google.android.gms.ads.AdActivity");
            activityTag.addAttribute("android", "configChanges", "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize");
            activityTag.addAttribute("android", "exported", "false");
            activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent");
            applicationTag.a(activityTag);

            XmlBuilder initProvider = new XmlBuilder("provider");
            initProvider.addAttribute("android", "name", "com.google.android.gms.ads.MobileAdsInitProvider");
            initProvider.addAttribute("android", "authorities", c.packageName + ".mobileadsinitprovider");
            initProvider.addAttribute("android", "exported", "false");
            initProvider.addAttribute("android", "initOrder", "100");
            applicationTag.a(initProvider);

            XmlBuilder adService = new XmlBuilder("service");
            adService.addAttribute("android", "name", "com.google.android.gms.ads.AdService");
            adService.addAttribute("android", "enabled", "true");
            adService.addAttribute("android", "exported", "false");
            applicationTag.a(adService);

            XmlBuilder testingActivity = new XmlBuilder("activity");
            testingActivity.addAttribute("android", "name", "com.google.android.gms.ads.OutOfContextTestingActivity");
            testingActivity.addAttribute("android", "configChanges", "keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize");
            testingActivity.addAttribute("android", "exported", "false");
            applicationTag.a(testingActivity);
        }
        if (builtInLibraryManager.containsLibrary(BuiltInLibraries.ANDROIDX_ROOM_RUNTIME)) {
            writeAndroidxRoomService(applicationTag);
        }
        writeAndroidxStartupInitializationProvider(applicationTag);
        if (builtInLibraryManager.containsLibrary(BuiltInLibraries.ANDROIDX_WORK_RUNTIME)) {
            writeAndroidxWorkRuntimeTags(applicationTag);
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
        if (c.x.isFCMUsed) {
            EditorManifest.writeDefFCM(applicationTag);
        }
        if (c.x.isOneSignalUsed) {
            EditorManifest.manifestOneSignal(applicationTag, packageName, c.x.param);
        }
        if (c.x.isFBAdsUsed) {
            EditorManifest.manifestFBAds(applicationTag, packageName);
        }
        if (c.x.isFBGoogleUsed) {
            EditorManifest.manifestFBGoogleLogin(applicationTag);
        }
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
