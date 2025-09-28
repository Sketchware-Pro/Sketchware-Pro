package mod.agus.jcoderz.editor.manifest;

import java.util.ArrayList;
import java.util.HashMap;

import pro.sketchware.xml.XmlBuilder;

/**
 * A helper class to add various elements to AndroidManifest.xml if components have been added,
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
        firebaseMessagingServiceIntentFilterTag.addChildNode(messagingEventActionTag);
        firebaseMessagingServiceTag.addChildNode(firebaseMessagingServiceIntentFilterTag);
        XmlBuilder firebaseInstanceIdReceiverTag = new XmlBuilder("receiver");
        firebaseInstanceIdReceiverTag.addAttribute("android", "name", "com.google.firebase.iid.FirebaseInstanceIdReceiver");
        firebaseInstanceIdReceiverTag.addAttribute("android", "exported", "true");
        firebaseInstanceIdReceiverTag.addAttribute("android", "permission", "com.google.android.c2dm.permission.SEND");
        XmlBuilder firebaseInstanceIdReceiverIntentFilterTag = new XmlBuilder("intent-filter");
        XmlBuilder receiveActionTag = new XmlBuilder("action");
        receiveActionTag.addAttribute("android", "name", "com.google.android.c2dm.intent.RECEIVE");
        firebaseInstanceIdReceiverIntentFilterTag.addChildNode(receiveActionTag);
        firebaseInstanceIdReceiverTag.addChildNode(firebaseInstanceIdReceiverIntentFilterTag);
        applicationTag.addChildNode(firebaseMessagingServiceTag);
        applicationTag.addChildNode(firebaseInstanceIdReceiverTag);
    }

    public static void manifestFBGoogleLogin(XmlBuilder applicationTag) {
        XmlBuilder activityTag = new XmlBuilder("activity");
        activityTag.addAttribute("android", "name", "com.google.android.gms.auth.api.signin.internal.SignInHubActivity");
        activityTag.addAttribute("android", "excludeFromRecents", "true");
        activityTag.addAttribute("android", "exported", "false");
        activityTag.addAttribute("android", "theme", "@android:style/Theme.Translucent.NoTitleBar");
        applicationTag.addChildNode(activityTag);
        XmlBuilder serviceTag = new XmlBuilder("service");
        serviceTag.addAttribute("android", "name", "com.google.android.gms.auth.api.signin.RevocationBoundService");
        serviceTag.addAttribute("android", "exported", "true");
        serviceTag.addAttribute("android", "permission", "com.google.android.gms.auth.api.signin.permission.REVOCATION_NOTIFICATION");
        applicationTag.addChildNode(serviceTag);
    }
}
