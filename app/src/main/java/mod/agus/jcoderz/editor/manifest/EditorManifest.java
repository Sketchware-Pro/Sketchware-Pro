package mod.agus.jcoderz.editor.manifest;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Nx;

/**
 * A helper class to add various elements to AndroidManifest.xml if components have been added,
 * for example the OneSignal component.
 */
public class EditorManifest {

    public static void writeDefFCM(Nx applicationTag) {
        Nx firebaseMessagingServiceTag = new Nx("service");
        firebaseMessagingServiceTag.addAttribute("android", "name", "com.google.firebase.messaging.FirebaseMessagingService");
        firebaseMessagingServiceTag.addAttribute("android", "exported", "false");
        Nx firebaseMessagingServiceIntentFilterTag = new Nx("intent-filter");
        firebaseMessagingServiceIntentFilterTag.addAttribute("android", "priority", "-500");
        Nx messagingEventActionTag = new Nx("action");
        messagingEventActionTag.addAttribute("android", "name", "com.google.firebase.MESSAGING_EVENT");
        firebaseMessagingServiceIntentFilterTag.a(messagingEventActionTag);
        firebaseMessagingServiceTag.a(firebaseMessagingServiceIntentFilterTag);
        Nx firebaseInstanceIdReceiverTag = new Nx("receiver");
        firebaseInstanceIdReceiverTag.addAttribute("android", "name", "com.google.firebase.iid.FirebaseInstanceIdReceiver");
        firebaseInstanceIdReceiverTag.addAttribute("android", "exported", "true");
        firebaseInstanceIdReceiverTag.addAttribute("android", "permission", "com.google.android.c2dm.permission.SEND");
        Nx firebaseInstanceIdReceiverIntentFilterTag = new Nx("intent-filter");
        Nx receiveActionTag = new Nx("action");
        receiveActionTag.addAttribute("android", "name", "com.google.android.c2dm.intent.RECEIVE");
        firebaseInstanceIdReceiverIntentFilterTag.a(receiveActionTag);
        firebaseInstanceIdReceiverTag.a(firebaseInstanceIdReceiverIntentFilterTag);
        applicationTag.a(firebaseMessagingServiceTag);
        applicationTag.a(firebaseInstanceIdReceiverTag);
    }

    public static void manifestOneSignal(Nx applicationTag, String packageName, HashMap<String, ArrayList<String>> hashMap) {
        if (hashMap.size() != 0) {
            Nx metadataTag = new Nx("meta-data");
            metadataTag.addAttribute("android", "name", "onesignal_app_id");
            metadataTag.addAttribute("android", "value", hashMap.get("OneSignal setAppId").get(0));
            applicationTag.a(metadataTag);
        }
        Nx metadataTag = new Nx("meta-data");
        metadataTag.addAttribute("android", "name", "onesignal_google_project_number");
        metadataTag.addAttribute("android", "value", "str:REMOTE");
        applicationTag.a(metadataTag);
        if (!packageName.isEmpty()) {
            Nx receiverTag = new Nx("receiver");
            receiverTag.addAttribute("android", "name", "com.onesignal.GcmBroadcastReceiver");
            receiverTag.addAttribute("android", "exported", "true");
            receiverTag.addAttribute("android", "permission", "com.google.android.c2dm.permission.SEND");
            Nx intentFilterTag = new Nx("intent-filter");
            intentFilterTag.addAttribute("android", "priority", "999");
            Nx actionTag = new Nx("action");
            actionTag.addAttribute("android", "name", "com.google.android.c2dm.intent.RECEIVE");
            Nx categoryTag = new Nx("category");
            categoryTag.addAttribute("android", "name", packageName);
            intentFilterTag.a(categoryTag);
            intentFilterTag.a(actionTag);
            receiverTag.a(intentFilterTag);
            applicationTag.a(receiverTag);
        }
        Nx notificationOpenedReceiverTag = new Nx("receiver");
        notificationOpenedReceiverTag.addAttribute("android", "name", "com.onesignal.NotificationOpenedReceiver");
        applicationTag.a(notificationOpenedReceiverTag);
        Nx gcmIntentServiceTag = new Nx("service");
        gcmIntentServiceTag.addAttribute("android", "name", "com.onesignal.GcmIntentService");
        applicationTag.a(gcmIntentServiceTag);
        Nx gcmIntentJobServiceTag = new Nx("service");
        gcmIntentJobServiceTag.addAttribute("android", "name", "com.onesignal.GcmIntentJobService");
        gcmIntentJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(gcmIntentJobServiceTag);
        Nx restoreJobServiceTag = new Nx("service");
        restoreJobServiceTag.addAttribute("android", "name", "com.onesignal.RestoreJobService");
        restoreJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(restoreJobServiceTag);
        Nx restoreKickoffJobServiceTag = new Nx("service");
        restoreKickoffJobServiceTag.addAttribute("android", "name", "com.onesignal.RestoreKickoffJobService");
        restoreKickoffJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(restoreKickoffJobServiceTag);
        Nx syncServiceTag = new Nx("service");
        syncServiceTag.addAttribute("android", "name", "com.onesignal.SyncService");
        syncServiceTag.addAttribute("android", "stopWithTask", "true");
        applicationTag.a(syncServiceTag);
        Nx syncJobServiceTag = new Nx("service");
        syncJobServiceTag.addAttribute("android", "name", "com.onesignal.SyncJobService");
        syncJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(syncJobServiceTag);
        Nx permissionsActivityTag = new Nx("activity");
        permissionsActivityTag.addAttribute("android", "name", "com.onesignal.PermissionsActivity");
        permissionsActivityTag.addAttribute("android", "theme", "@style/AppTheme.FullScreen");
        applicationTag.a(permissionsActivityTag);
        Nx notificationRestoreServiceTag = new Nx("service");
        notificationRestoreServiceTag.addAttribute("android", "name", "com.onesignal.NotificationRestoreService");
        applicationTag.a(notificationRestoreServiceTag);
        Nx bootUpReceiverTag = new Nx("receiver");
        bootUpReceiverTag.addAttribute("android", "name", "com.onesignal.BootUpReceiver");
        bootUpReceiverTag.addAttribute("android", "exported", "false");
        Nx bootUpReceiverIntentFilterTag = new Nx("intent-filter");
        Nx bootUpReceiverBootCompleteActionTag = new Nx("action");
        bootUpReceiverBootCompleteActionTag.addAttribute("android", "name", "android.intent.action.BOOT_COMPLETED");
        Nx bootUpReceiverQuickBootPowerOnActionTag = new Nx("action");
        bootUpReceiverQuickBootPowerOnActionTag.addAttribute("android", "name", "android.intent.action.QUICKBOOT_POWERON");
        bootUpReceiverIntentFilterTag.a(bootUpReceiverBootCompleteActionTag);
        bootUpReceiverIntentFilterTag.a(bootUpReceiverQuickBootPowerOnActionTag);
        bootUpReceiverTag.a(bootUpReceiverIntentFilterTag);
        applicationTag.a(bootUpReceiverTag);
        Nx upgradeReceiverTag = new Nx("receiver");
        upgradeReceiverTag.addAttribute("android", "name", "com.onesignal.UpgradeReceiver");
        upgradeReceiverTag.addAttribute("android", "exported", "false");
        Nx upgradeReceiverIntentFilterTag = new Nx("intent-filter");
        Nx upgradeReceiverIntentFilterActionTag = new Nx("action");
        upgradeReceiverIntentFilterActionTag.addAttribute("android", "name", "android.intent.action.MY_PACKAGE_REPLACED");
        upgradeReceiverIntentFilterTag.a(upgradeReceiverIntentFilterActionTag);
        upgradeReceiverTag.a(upgradeReceiverIntentFilterTag);
        applicationTag.a(upgradeReceiverTag);
    }

    public static void manifestFBAds(Nx applicationTag, String packageName) {
        Nx activityTag = new Nx("activity");
        activityTag.addAttribute("android", "name", "com.facebook.ads.AudienceNetworkActivity");
        activityTag.addAttribute("android", "configChanges", "keyboardHidden|orientation|screenSize");
        activityTag.addAttribute("android", "exported", "false");
        activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.a(activityTag);
        if (!packageName.isEmpty()) {
            Nx nx3 = new Nx("provider");
            nx3.addAttribute("android", "name", "com.facebook.ads.AudienceNetworkContentProvider");
            nx3.addAttribute("android", "authorities", packageName + ".AudienceNetworkContentProvider");
            nx3.addAttribute("android", "exported", "false");
            applicationTag.a(nx3);
        }
    }

    public static void manifestFBGoogleLogin(Nx applicationTag) {
        Nx activityTag = new Nx("activity");
        activityTag.addAttribute("android", "name", "com.google.android.gms.auth.api.signin.internal.SignInHubActivity");
        activityTag.addAttribute("android", "excludeFromRecents", "true");
        activityTag.addAttribute("android", "exported", "false");
        activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.a(activityTag);
        Nx serviceTag = new Nx("service");
        serviceTag.addAttribute("android", "name", "com.google.android.gms.auth.api.signin.RevocationBoundService");
        serviceTag.addAttribute("android", "exported", "true");
        serviceTag.addAttribute("android", "permission", "com.google.android.gms.auth.api.signin.permission.REVOCATION_NOTIFICATION");
        applicationTag.a(serviceTag);
    }
}
