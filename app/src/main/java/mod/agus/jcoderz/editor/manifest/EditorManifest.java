package mod.agus.jcoderz.editor.manifest;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Nx;

public class EditorManifest {
    public static void writeAttrIntentFilter(Nx nx, HashMap<String, ArrayList> hashMap) {
        Nx nx2 = new Nx("intent-filter");
        Nx nx3 = new Nx("action");
        nx3.a("android", "name", "android.intent.action.VIEW");
        Nx nx4 = new Nx("category");
        nx4.a("android", "name", "android.intent.category.DEFAULT");
        Nx nx5 = new Nx("category");
        nx5.a("android", "name", "android.intent.category.BROWSABLE");
        Nx nx6 = new Nx("data");
        if (hashMap.size() > 0) {
            nx6.a("android", "host", hashMap.get("FirebaseDynamicLink setDataHost").get(0).toString());
        }
        if (hashMap.size() > 1) {
            nx6.a("android", "scheme", hashMap.get("FirebaseDynamicLink setDataHost").get(1).toString());
        }
        nx2.a(nx3);
        nx2.a(nx4);
        nx2.a(nx5);
        if (hashMap.size() != 0) {
            nx2.a(nx6);
        }
        nx.a(nx2);
    }

    public static void writeDefFCM(Nx nx) {
        Nx nx2 = new Nx("service");
        nx2.a("android", "name", "com.google.firebase.messaging.FirebaseMessagingService");
        nx2.a("android", "exported", "false");
        Nx nx3 = new Nx("intent-filter");
        nx3.a("android", "priority", "-500");
        Nx nx4 = new Nx("action");
        nx4.a("android", "name", "com.google.firebase.MESSAGING_EVENT");
        nx3.a(nx4);
        nx2.a(nx3);
        Nx nx5 = new Nx("receiver");
        nx5.a("android", "name", "com.google.firebase.iid.FirebaseInstanceIdReceiver");
        nx5.a("android", "exported", "true");
        nx5.a("android", "permission", "com.google.android.c2dm.permission.SEND");
        Nx nx6 = new Nx("intent-filter");
        Nx nx7 = new Nx("action");
        nx7.a("android", "name", "com.google.android.c2dm.intent.RECEIVE");
        nx6.a(nx7);
        nx5.a(nx6);
        nx.a(nx2);
        nx.a(nx5);
    }

    public static void writeMetadataComponentFirebase(Nx nx, String str) {
        Nx nx2 = new Nx("meta-data");
        switch (str.hashCode()) {
            case 1665812692:
                if (str.equals("Firebase Dynamic Link")) {
                    nx2.a("android", "name", "com.google.firebase.components:com.google.firebase.dynamiclinks.internal.FirebaseDynamicLinkRegistrar");
                    nx2.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
                    nx.a(nx2);
                    return;
                }
                return;
            case 1956214435:
                if (str.equals("Firebase Cloud Message")) {
                    nx2.a("android", "name", "com.google.firebase.components:com.google.firebase.iid.Registrar");
                    nx2.a("android", "value", "com.google.firebase.components.ComponentRegistrar");
                    nx.a(nx2);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public static void manifestOneSignal(Nx nx, String str, HashMap<String, ArrayList> hashMap) {
        if (hashMap.size() != 0) {
            Nx nx2 = new Nx("meta-data");
            nx2.a("android", "name", "onesignal_app_id");
            nx2.a("android", "value", hashMap.get("OneSignal setAppId").get(0).toString());
            nx.a(nx2);
        }
        Nx nx3 = new Nx("meta-data");
        nx3.a("android", "name", "onesignal_google_project_number");
        nx3.a("android", "value", "str:REMOTE");
        nx.a(nx3);
        if (!str.isEmpty()) {
            Nx nx4 = new Nx("receiver");
            nx4.a("android", "name", "com.onesignal.GcmBroadcastReceiver");
            nx4.a("android", "permission", "com.google.android.c2dm.permission.SEND");
            Nx nx5 = new Nx("intent-filter");
            nx5.a("android", "priority", "999");
            Nx nx6 = new Nx("action");
            nx6.a("android", "name", "com.google.android.c2dm.intent.RECEIVE");
            Nx nx7 = new Nx("category");
            nx7.a("android", "name", str);
            nx6.a(nx7);
            nx5.a(nx6);
            nx4.a(nx5);
            nx.a(nx4);
        }
        Nx nx8 = new Nx("receiver");
        nx8.a("android", "name", "com.onesignal.NotificationOpenedReceiver");
        nx.a(nx8);
        Nx nx9 = new Nx("service");
        nx9.a("android", "name", "com.onesignal.GcmIntentService");
        nx.a(nx9);
        Nx nx10 = new Nx("service");
        nx10.a("android", "name", "com.onesignal.GcmIntentJobService");
        nx10.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        nx.a(nx10);
        Nx nx11 = new Nx("service");
        nx11.a("android", "name", "com.onesignal.RestoreJobService");
        nx11.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        nx.a(nx11);
        Nx nx12 = new Nx("service");
        nx12.a("android", "name", "com.onesignal.RestoreKickoffJobService");
        nx12.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        nx.a(nx12);
        Nx nx13 = new Nx("service");
        nx13.a("android", "name", "com.onesignal.SyncService");
        nx13.a("android", "stopWithTask", "true");
        nx.a(nx13);
        Nx nx14 = new Nx("service");
        nx14.a("android", "name", "com.onesignal.SyncJobService");
        nx14.a("android", "permission", "android.permission.BIND_JOB_SERVICE");
        nx.a(nx14);
        Nx nx15 = new Nx("activity");
        nx15.a("android", "name", "com.onesignal.PermissionsActivity");
        nx15.a("android", "theme", "@style/AppTheme.FullScreen");
        nx.a(nx15);
        Nx nx16 = new Nx("service");
        nx16.a("android", "name", "com.onesignal.NotificationRestoreService");
        nx.a(nx16);
        Nx nx17 = new Nx("receiver");
        nx17.a("android", "name", "com.onesignal.BootUpReceiver");
        Nx nx18 = new Nx("intent-filter");
        Nx nx19 = new Nx("action");
        nx19.a("android", "name", "android.intent.action.BOOT_COMPLETED");
        Nx nx20 = new Nx("action");
        nx20.a("android", "name", "android.intent.action.QUICKBOOT_POWERON");
        nx19.a(nx20);
        nx18.a(nx19);
        nx17.a(nx18);
        nx.a(nx17);
        Nx nx21 = new Nx("receiver");
        nx21.a("android", "name", "com.onesignal.UpgradeReceiver");
        Nx nx22 = new Nx("intent-filter");
        Nx nx23 = new Nx("action");
        nx23.a("android", "name", "android.intent.action.MY_PACKAGE_REPLACED");
        nx22.a(nx23);
        nx21.a(nx22);
        nx.a(nx21);
    }

    public static void manifestFBAds(Nx nx, String str) {
        Nx nx2 = new Nx("activity");
        nx2.a("android", "name", "com.facebook.ads.AudienceNetworkActivity");
        nx2.a("android", "configChanges", "keyboardHidden|orientation|screenSize");
        nx2.a("android", "exported", "false");
        nx2.a("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        nx.a(nx2);
        if (!str.isEmpty()) {
            Nx nx3 = new Nx("provider");
            nx3.a("android", "name", "com.facebook.ads.AudienceNetworkContentProvider");
            nx3.a("android", "authorities", str + ".AudienceNetworkContentProvider");
            nx3.a("android", "exported", "false");
            nx.a(nx3);
        }
    }

    public static void manifestFBGoogleLogin(Nx nx) {
        Nx nx2 = new Nx("activity");
        nx2.a("android", "name", "com.google.android.gms.auth.api.signin.internal.SignInHubActivity");
        nx2.a("android", "excludeFromRecents", "true");
        nx2.a("android", "exported", "false");
        nx2.a("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        nx.a(nx2);
        Nx nx3 = new Nx("service");
        nx3.a("android", "name", "com.google.android.gms.auth.api.signin.RevocationBoundService");
        nx3.a("android", "exported", "true");
        nx3.a("android", "permission", "com.google.android.gms.auth.api.signin.permission.REVOCATION_NOTIFICATION");
        nx.a(nx3);
    }
}
