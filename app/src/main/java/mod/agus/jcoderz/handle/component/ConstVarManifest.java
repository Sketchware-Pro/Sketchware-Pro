package mod.agus.jcoderz.handle.component;

import android.Manifest;

import com.sketchware.remod.xml.XmlBuilder;
import mod.agus.jcoderz.editor.manifest.EditorManifest;

/**
 * A class responsible for adding entries to AndroidManifest.xml if Agus' blocks/components were used.
 */
public class ConstVarManifest {

    public static void handlePermissionComponent(XmlBuilder nx, ConstVarComponent component) {
        if (component.isFCMUsed) {
            writePermission(nx, Manifest.permission.WAKE_LOCK);
            writePermission(nx, "com.google.android.c2dm.permission.RECEIVE");
        }
        if (component.isOneSignalUsed) {
            if (!component.pkgName.isEmpty()) {
                writePermissionMultiLine(nx, component.pkgName + ".permission.C2D_MESSAGE");
                writePermission(nx, component.pkgName + ".permission.C2D_MESSAGE");
            }
            writePermission(nx, Manifest.permission.WAKE_LOCK);
            writePermission(nx, Manifest.permission.VIBRATE);
            writePermission(nx, Manifest.permission.RECEIVE_BOOT_COMPLETED);
            writePermission(nx, "com.sec.android.provider.badge.permission.READ");
            writePermission(nx, "com.sec.android.provider.badge.permission.WRITE");
            writePermission(nx, "com.htc.launcher.permission.READ_SETTINGS");
            writePermission(nx, "com.htc.launcher.permission.UPDATE_SHORTCUT");
            writePermission(nx, "com.sonyericsson.home.permission.BROADCAST_BADGE");
            writePermission(nx, "com.sonymobile.home.permission.PROVIDER_INSERT_BADGE");
            writePermission(nx, "com.anddoes.launcher.permission.UPDATE_COUNT");
            writePermission(nx, "com.majeur.launcher.permission.UPDATE_BADGE");
            writePermission(nx, "com.huawei.android.launcher.permission.CHANGE_BADGE");
            writePermission(nx, "com.huawei.android.launcher.permission.READ_SETTINGS");
            writePermission(nx, "com.huawei.android.launcher.permission.WRITE_SETTINGS");
            writePermission(nx, "android.permission.READ_APP_BADGE");
            writePermission(nx, "com.oppo.launcher.permission.READ_SETTINGS");
            writePermission(nx, "com.oppo.launcher.permission.WRITE_SETTINGS");
            writePermission(nx, "me.everything.badger.permission.BADGE_COUNT_READ");
            writePermission(nx, "me.everything.badger.permission.BADGE_COUNT_WRITE");
        }
    }

    public static void handleBgTaskComponent(XmlBuilder nx, ConstVarComponent component) {
        if (component.isFCMUsed) {
            EditorManifest.writeDefFCM(nx);
        }
        if (component.isOneSignalUsed) {
            EditorManifest.manifestOneSignal(nx, component.pkgName, component.param);
        }
        if (component.isFBAdsUsed) {
            EditorManifest.manifestFBAds(nx, component.pkgName);
        }
        if (component.isFBGoogleUsed) {
            EditorManifest.manifestFBGoogleLogin(nx);
        }
    }

    public static void writePermission(XmlBuilder nx, String permissionName) {
        XmlBuilder usesPermissionTag = new XmlBuilder("uses-permission");
        usesPermissionTag.addAttribute("android", "name", permissionName);
        nx.a(usesPermissionTag);
    }

    public static void writePermissionMultiLine(XmlBuilder nx, String permissionName) {
        XmlBuilder permissionTag = new XmlBuilder("permission");
        permissionTag.addAttribute("android", "name", permissionName);
        permissionTag.addAttribute("android", "protectionLevel", "signature");
        nx.a(permissionTag);
    }
}
