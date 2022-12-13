package mod.agus.jcoderz.editor.manifest;

import java.util.ArrayList;
import java.util.HashMap;

import com.sketchware.remod.xml.XmlBuilder;

/**
 * A helper class to add various elements to AndroidManifest.xml if components have been added,
 * for example the OneSignal component.
 */
public class EditorManifest {

    public static void writeDefFCM(XmlBuilder applicationTag) {
        XmlBuilder firebaseMessagingServiceTag = new XmlBuilder("service");
        firebaseMessagingServiceTag.addAttribute("android", "name", "com.google.firebase.messaging.FirebaseMessagingService");
        firebaseMessagingServiceTag.addAttribute("android", "exported", "false");
        XmlBuilder firebaseMessagingServiceIntentFilterTag = new XmlBuilder("intent-filter");
        firebaseMessagingServiceIntentFilterTag.addAttribute("android", "priority", "-500");
        XmlBuilder messagingEventActionTag = new XmlBuilder("action");
        messagingEventActionTag.addAttribute("android", "name", "com.google.firebase.MESSAGING_EVENT");
        firebaseMessagingServiceIntentFilterTag.a(messagingEventActionTag);
        firebaseMessagingServiceTag.a(firebaseMessagingServiceIntentFilterTag);
        XmlBuilder firebaseInstanceIdReceiverTag = new XmlBuilder("receiver");
        firebaseInstanceIdReceiverTag.addAttribute("android", "name", "com.google.firebase.iid.FirebaseInstanceIdReceiver");
        firebaseInstanceIdReceiverTag.addAttribute("android", "exported", "true");
        firebaseInstanceIdReceiverTag.addAttribute("android", "permission", "com.google.android.c2dm.permission.SEND");
        XmlBuilder firebaseInstanceIdReceiverIntentFilterTag = new XmlBuilder("intent-filter");
        XmlBuilder receiveActionTag = new XmlBuilder("action");
        receiveActionTag.addAttribute("android", "name", "com.google.android.c2dm.intent.RECEIVE");
        firebaseInstanceIdReceiverIntentFilterTag.a(receiveActionTag);
        firebaseInstanceIdReceiverTag.a(firebaseInstanceIdReceiverIntentFilterTag);
        applicationTag.a(firebaseMessagingServiceTag);
        applicationTag.a(firebaseInstanceIdReceiverTag);
    }

    public static void manifestOneSignal(XmlBuilder applicationTag, String packageName, HashMap<String, ArrayList<String>> hashMap) {
        if (hashMap.size() != 0) {
            XmlBuilder metadataTag = new XmlBuilder("meta-data");
            metadataTag.addAttribute("android", "name", "onesignal_app_id");
            metadataTag.addAttribute("android", "value", hashMap.get("OneSignal setAppId").get(0));
            applicationTag.a(metadataTag);
        }
        XmlBuilder metadataTag = new XmlBuilder("meta-data");
        metadataTag.addAttribute("android", "name", "onesignal_google_project_number");
        metadataTag.addAttribute("android", "value", "str:REMOTE");
        applicationTag.a(metadataTag);
        if (!packageName.isEmpty()) {
            XmlBuilder receiverTag = new XmlBuilder("receiver");
            receiverTag.addAttribute("android", "name", "com.onesignal.GcmBroadcastReceiver");
            receiverTag.addAttribute("android", "exported", "true");
            receiverTag.addAttribute("android", "permission", "com.google.android.c2dm.permission.SEND");
            XmlBuilder intentFilterTag = new XmlBuilder("intent-filter");
            intentFilterTag.addAttribute("android", "priority", "999");
            XmlBuilder actionTag = new XmlBuilder("action");
            actionTag.addAttribute("android", "name", "com.google.android.c2dm.intent.RECEIVE");
            XmlBuilder categoryTag = new XmlBuilder("category");
            categoryTag.addAttribute("android", "name", packageName);
            intentFilterTag.a(categoryTag);
            intentFilterTag.a(actionTag);
            receiverTag.a(intentFilterTag);
            applicationTag.a(receiverTag);
        }
        XmlBuilder notificationOpenedReceiverTag = new XmlBuilder("receiver");
        notificationOpenedReceiverTag.addAttribute("android", "name", "com.onesignal.NotificationOpenedReceiver");
        applicationTag.a(notificationOpenedReceiverTag);
        XmlBuilder gcmIntentServiceTag = new XmlBuilder("service");
        gcmIntentServiceTag.addAttribute("android", "name", "com.onesignal.GcmIntentService");
        applicationTag.a(gcmIntentServiceTag);
        XmlBuilder gcmIntentJobServiceTag = new XmlBuilder("service");
        gcmIntentJobServiceTag.addAttribute("android", "name", "com.onesignal.GcmIntentJobService");
        gcmIntentJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(gcmIntentJobServiceTag);
        XmlBuilder restoreJobServiceTag = new XmlBuilder("service");
        restoreJobServiceTag.addAttribute("android", "name", "com.onesignal.RestoreJobService");
        restoreJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(restoreJobServiceTag);
        XmlBuilder restoreKickoffJobServiceTag = new XmlBuilder("service");
        restoreKickoffJobServiceTag.addAttribute("android", "name", "com.onesignal.RestoreKickoffJobService");
        restoreKickoffJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(restoreKickoffJobServiceTag);
        XmlBuilder syncServiceTag = new XmlBuilder("service");
        syncServiceTag.addAttribute("android", "name", "com.onesignal.SyncService");
        syncServiceTag.addAttribute("android", "stopWithTask", "true");
        applicationTag.a(syncServiceTag);
        XmlBuilder syncJobServiceTag = new XmlBuilder("service");
        syncJobServiceTag.addAttribute("android", "name", "com.onesignal.SyncJobService");
        syncJobServiceTag.addAttribute("android", "permission", "android.permission.BIND_JOB_SERVICE");
        applicationTag.a(syncJobServiceTag);
        XmlBuilder permissionsActivityTag = new XmlBuilder("activity");
        permissionsActivityTag.addAttribute("android", "name", "com.onesignal.PermissionsActivity");
        permissionsActivityTag.addAttribute("android", "theme", "@style/AppTheme.FullScreen");
        applicationTag.a(permissionsActivityTag);
        XmlBuilder notificationRestoreServiceTag = new XmlBuilder("service");
        notificationRestoreServiceTag.addAttribute("android", "name", "com.onesignal.NotificationRestoreService");
        applicationTag.a(notificationRestoreServiceTag);
        XmlBuilder bootUpReceiverTag = new XmlBuilder("receiver");
        bootUpReceiverTag.addAttribute("android", "name", "com.onesignal.BootUpReceiver");
        bootUpReceiverTag.addAttribute("android", "exported", "false");
        XmlBuilder bootUpReceiverIntentFilterTag = new XmlBuilder("intent-filter");
        XmlBuilder bootUpReceiverBootCompleteActionTag = new XmlBuilder("action");
        bootUpReceiverBootCompleteActionTag.addAttribute("android", "name", "android.intent.action.BOOT_COMPLETED");
        XmlBuilder bootUpReceiverQuickBootPowerOnActionTag = new XmlBuilder("action");
        bootUpReceiverQuickBootPowerOnActionTag.addAttribute("android", "name", "android.intent.action.QUICKBOOT_POWERON");
        bootUpReceiverIntentFilterTag.a(bootUpReceiverBootCompleteActionTag);
        bootUpReceiverIntentFilterTag.a(bootUpReceiverQuickBootPowerOnActionTag);
        bootUpReceiverTag.a(bootUpReceiverIntentFilterTag);
        applicationTag.a(bootUpReceiverTag);
        XmlBuilder upgradeReceiverTag = new XmlBuilder("receiver");
        upgradeReceiverTag.addAttribute("android", "name", "com.onesignal.UpgradeReceiver");
        upgradeReceiverTag.addAttribute("android", "exported", "false");
        XmlBuilder upgradeReceiverIntentFilterTag = new XmlBuilder("intent-filter");
        XmlBuilder upgradeReceiverIntentFilterActionTag = new XmlBuilder("action");
        upgradeReceiverIntentFilterActionTag.addAttribute("android", "name", "android.intent.action.MY_PACKAGE_REPLACED");
        upgradeReceiverIntentFilterTag.a(upgradeReceiverIntentFilterActionTag);
        upgradeReceiverTag.a(upgradeReceiverIntentFilterTag);
        applicationTag.a(upgradeReceiverTag);
    }

    public static void manifestFBAds(XmlBuilder applicationTag, String packageName) {
        XmlBuilder activityTag = new XmlBuilder("activity");
        activityTag.addAttribute("android", "name", "com.facebook.ads.AudienceNetworkActivity");
        activityTag.addAttribute("android", "configChanges", "keyboardHidden|orientation|screenSize");
        activityTag.addAttribute("android", "exported", "false");
        activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.a(activityTag);
        if (!packageName.isEmpty()) {
            XmlBuilder nx3 = new XmlBuilder("provider");
            nx3.addAttribute("android", "name", "com.facebook.ads.AudienceNetworkContentProvider");
            nx3.addAttribute("android", "authorities", packageName + ".AudienceNetworkContentProvider");
            nx3.addAttribute("android", "exported", "false");
            applicationTag.a(nx3);
        }
    }

    public static void manifestFBGoogleLogin(XmlBuilder applicationTag) {
        XmlBuilder activityTag = new XmlBuilder("activity");
        activityTag.addAttribute("android", "name", "com.google.android.gms.auth.api.signin.internal.SignInHubActivity");
        activityTag.addAttribute("android", "excludeFromRecents", "true");
        activityTag.addAttribute("android", "exported", "false");
        activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.a(activityTag);
        XmlBuilder serviceTag = new XmlBuilder("service");
        serviceTag.addAttribute("android", "name", "com.google.android.gms.auth.api.signin.RevocationBoundService");
        serviceTag.addAttribute("android", "exported", "true");
        serviceTag.addAttribute("android", "permission", "com.google.android.gms.auth.api.signin.permission.REVOCATION_NOTIFICATION");
        applicationTag.a(serviceTag);
    }
}
