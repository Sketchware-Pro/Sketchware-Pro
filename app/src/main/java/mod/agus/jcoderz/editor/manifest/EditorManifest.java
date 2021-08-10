package mod.agus.jcoderz.editor.manifest;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Nx;

/**
 * A helper class to add various elements to AndroidManifest.xml if components have been added,
 * for example the OneSignal component.
 */
public class EditorManifest {

    public static void writeAttrIntentFilter(Nx activityTag, HashMap<String, ArrayList<String>> arguments) {
        Nx intentFilterTag = new Nx("intent-filter");
        Nx intentFilterActionTag = new Nx("action");
        intentFilterActionTag.a("android", "name", "android.intent.action.VIEW");
        Nx intentFilterCategoryDefaultTag = new Nx("category");
        intentFilterCategoryDefaultTag.a("android", "name", "android.intent.category.DEFAULT");
        Nx intentFilterCategoryBrowsableTag = new Nx("category");
        intentFilterCategoryBrowsableTag.a("android", "name", "android.intent.category.BROWSABLE");
        Nx intentFilterDataTag = new Nx("data");
        if (arguments.size() > 0) {
            intentFilterDataTag.a("android", "host", arguments.get("FirebaseDynamicLink setDataHost").get(0));
        }
        if (arguments.size() > 1) {
            intentFilterDataTag.a("android", "scheme", arguments.get("FirebaseDynamicLink setDataHost").get(1));
        }
        intentFilterTag.a(intentFilterActionTag);
        intentFilterTag.a(intentFilterCategoryDefaultTag);
        intentFilterTag.a(intentFilterCategoryBrowsableTag);
        if (arguments.size() != 0) {
            intentFilterTag.a(intentFilterDataTag);
        }
        activityTag.a(intentFilterTag);
    }

    public static void writeDefFCM(Nx applicationTag) {
        Nx firebaseMessagingServiceTag = new Nx("service");
        firebaseMessagingServiceTag.a("android", "name", "com.google.firebase.messaging.FirebaseMessagingService");
        firebaseMessagingServiceTag.a("android", "exported", "false");
        Nx firebaseMessagingServiceIntentFilterTag = new Nx("intent-filter");
        firebaseMessagingServiceIntentFilterTag.a("android", "priority", "-500");
        Nx messagingEventActionTag = new Nx("action");
        messagingEventActionTag.a("android", "name", "com.google.firebase.MESSAGING_EVENT");
        firebaseMessagingServiceIntentFilterTag.a(messagingEventActionTag);
        firebaseMessagingServiceTag.a(firebaseMessagingServiceIntentFilterTag);
        Nx firebaseInstanceIdReceiverTag = new Nx("receiver");
        firebaseInstanceIdReceiverTag.a("android", "name", "com.google.firebase.iid.FirebaseInstanceIdReceiver");
        firebaseInstanceIdReceiverTag.a("android", "exported", "true");
        firebaseInstanceIdReceiverTag.a("android", "permission", "com.google.android.c2dm.permission.SEND");
        Nx firebaseInstanceIdReceiverIntentFilterTag = new Nx("intent-filter");
        Nx receiveActionTag = new Nx("action");
        receiveActionTag.a("android", "name", "com.google.android.c2dm.intent.RECEIVE");
        firebaseInstanceIdReceiverIntentFilterTag.a(receiveActionTag);
        firebaseInstanceIdReceiverTag.a(firebaseInstanceIdReceiverIntentFilterTag);
        applicationTag.a(firebaseMessagingServiceTag);
        applicationTag.a(firebaseInstanceIdReceiverTag);
    }

    public static void writeMetadataComponentFirebase(Nx applicationTag, String componentName) {
        Nx metadataTag = new Nx("meta-data");

        switch (componentName) {
            case "Firebase Dynamic Link":
                metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.dynamiclinks.internal.FirebaseDynamicLinkRegistrar");
                break;

            case "Firebase Cloud Message":
                metadataTag.a("android", "name", "com.google.firebase.components:com.google.firebase.iid.Registrar");
                break;
        }
        metadataTag.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
        applicationTag.a(metadataTag);
    }

    public static void manifestOneSignal(Nx applicationTag, String packageName, HashMap<String, ArrayList<String>> hashMap) {
        if (hashMap.size() != 0) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.a("android", "name", "onesignal_app_id");
            metadataTag.a("android", "value", hashMap.get("OneSignal setAppId").get(0));
            applicationTag.a(metadataTag);
        }
        Nx metadataTag = new Nx("meta-data");
        metadataTag.a("android", "name", "onesignal_google_project_number");
        metadataTag.a("android", "value", "str:REMOTE");
        applicationTag.a(metadataTag);
        if (!packageName.isEmpty()) {
            Nx receiverTag = new Nx("receiver");
            receiverTag.a("android", "name", "com.onesignal.GcmBroadcastReceiver");
            receiverTag.a("android", "permission", "com.google.android.c2dm.permission.SEND");
            Nx intentFilterTag = new Nx("intent-filter");
            intentFilterTag.a("android", "priority", "999");
            Nx actionTag = new Nx("action");
            actionTag.a("android", "name", "com.google.android.c2dm.intent.RECEIVE");
            Nx categoryTag = new Nx("category");
            categoryTag.a("android", "name", packageName);
            intentFilterTag.a(categoryTag);
            intentFilterTag.a(actionTag);
            receiverTag.a(intentFilterTag);
            applicationTag.a(receiverTag);
        }
        Nx notificationOpenedReceiverTag = new Nx("receiver");
        notificationOpenedReceiverTag.a("android", "name", "com.onesignal.NotificationOpenedReceiver");
        applicationTag.a(notificationOpenedReceiverTag);
        Nx gcmIntentServiceTag = new Nx("service");
        gcmIntentServiceTag.a("android", "name", "com.onesignal.GcmIntentService");
        applicationTag.a(gcmIntentServiceTag);
        Nx gcmIntentJobServiceTag = new Nx("service");
        gcmIntentJobServiceTag.a("android", "name", "com.onesignal.GcmIntentJobService");
        gcmIntentJobServiceTag.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(gcmIntentJobServiceTag);
        Nx restoreJobServiceTag = new Nx("service");
        restoreJobServiceTag.a("android", "name", "com.onesignal.RestoreJobService");
        restoreJobServiceTag.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(restoreJobServiceTag);
        Nx restoreKickoffJobServiceTag = new Nx("service");
        restoreKickoffJobServiceTag.a("android", "name", "com.onesignal.RestoreKickoffJobService");
        restoreKickoffJobServiceTag.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(restoreKickoffJobServiceTag);
        Nx syncServiceTag = new Nx("service");
        syncServiceTag.a("android", "name", "com.onesignal.SyncService");
        syncServiceTag.a("android", "stopWithTask", "true");
        applicationTag.a(syncServiceTag);
        Nx syncJobServiceTag = new Nx("service");
        syncJobServiceTag.a("android", "name", "com.onesignal.SyncJobService");
        syncJobServiceTag.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(syncJobServiceTag);
        Nx permissionsActivityTag = new Nx("activity");
        permissionsActivityTag.a("android", "name", "com.onesignal.PermissionsActivity");
        permissionsActivityTag.a("android", "theme", "@style/AppTheme.FullScreen");
        applicationTag.a(permissionsActivityTag);
        Nx notificationRestoreServiceTag = new Nx("service");
        notificationRestoreServiceTag.a("android", "name", "com.onesignal.NotificationRestoreService");
        applicationTag.a(notificationRestoreServiceTag);
        Nx bootUpReceiverTag = new Nx("receiver");
        bootUpReceiverTag.a("android", "name", "com.onesignal.BootUpReceiver");
        Nx bootUpReceiverIntentFilterTag = new Nx("intent-filter");
        Nx bootUpReceiverBootCompleteActionTag = new Nx("action");
        bootUpReceiverBootCompleteActionTag.a("android", "name", "android.intent.action.BOOT_COMPLETED");
        Nx bootUpReceiverQuickBootPowerOnActionTag = new Nx("action");
        bootUpReceiverQuickBootPowerOnActionTag.a("android", "name", "android.intent.action.QUICKBOOT_POWERON");
        bootUpReceiverIntentFilterTag.a(bootUpReceiverBootCompleteActionTag);
        bootUpReceiverIntentFilterTag.a(bootUpReceiverQuickBootPowerOnActionTag);
        bootUpReceiverTag.a(bootUpReceiverIntentFilterTag);
        applicationTag.a(bootUpReceiverTag);
        Nx upgradeReceiverTag = new Nx("receiver");
        upgradeReceiverTag.a("android", "name", "com.onesignal.UpgradeReceiver");
        Nx upgradeReceiverIntentFilterTag = new Nx("intent-filter");
        Nx upgradeReceiverIntentFilterActionTag = new Nx("action");
        upgradeReceiverIntentFilterActionTag.a("android", "name", "android.intent.action.MY_PACKAGE_REPLACED");
        upgradeReceiverIntentFilterTag.a(upgradeReceiverIntentFilterActionTag);
        upgradeReceiverTag.a(upgradeReceiverIntentFilterTag);
        applicationTag.a(upgradeReceiverTag);
    }

    public static void manifestFBAds(Nx applicationTag, String packageName) {
        Nx activityTag = new Nx("activity");
        activityTag.a("android", "name", "com.facebook.ads.AudienceNetworkActivity");
        activityTag.a("android", "configChanges", "keyboardHidden|orientation|screenSize");
        activityTag.a("android", "exported", "false");
        activityTag.a("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.a(activityTag);
        if (!packageName.isEmpty()) {
            Nx nx3 = new Nx("provider");
            nx3.a("android", "name", "com.facebook.ads.AudienceNetworkContentProvider");
            nx3.a("android", "authorities", packageName + ".AudienceNetworkContentProvider");
            nx3.a("android", "exported", "false");
            applicationTag.a(nx3);
        }
    }

    public static void manifestFBGoogleLogin(Nx applicationTag) {
        Nx activityTag = new Nx("activity");
        activityTag.a("android", "name", "com.google.android.gms.auth.api.signin.internal.SignInHubActivity");
        activityTag.a("android", "excludeFromRecents", "true");
        activityTag.a("android", "exported", "false");
        activityTag.a("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.a(activityTag);
        Nx serviceTag = new Nx("service");
        serviceTag.a("android", "name", "com.google.android.gms.auth.api.signin.RevocationBoundService");
        serviceTag.a("android", "exported", "true");
        serviceTag.a("android", "permission", "com.google.android.gms.auth.api.signin.permission.REVOCATION_NOTIFICATION");
        applicationTag.a(serviceTag);
    }
}
