package mod.remaker.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class UriUtils {
    private static final String PRIMARY_VOLUME_NAME = "primary";

    private UriUtils() {
    }

    public static void takePermissions(Context context, Uri uri, int flags) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.takePersistableUriPermission(uri, flags);
    }

    public static File resolveUri(Context context, Uri uri) {
        if (uri.getScheme().equals("file")) {
            return new File(uri.getPath());
        } else {
            return resolveFile(context, uri);
        }
    }

    public static File resolveFile(Context context, Uri uri) {
        String volumeId = getVolumeIdFromTreeUri(uri);
        if (volumeId == null) {
            return null;
        }

        String volumePath = removeSuffix(File.separatorChar, getVolumePath(volumeId, context));
        String documentPath = removeSuffix(File.separatorChar, getDocumentPathFromTreeUri(uri));

        return new File(
            documentPath.isEmpty() ? volumePath : volumePath + File.separator + documentPath
        );
    }

    private static String removeSuffix(char suffix, String text) {
        String last = text.substring(0, text.length() - 1);
        if (last != null && last.equals(suffix)) {
            return last;
        }
        return text;
    }

    private static String getVolumePath(String volumeId, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return getVolumePathForAndroid11AndAbove(volumeId, context);
        } else {
            return getVolumePathBeforeAndroid11(volumeId, context);
        }
    }

    private static String getVolumePathBeforeAndroid11(String volumeId, Context context) {
        try {
            StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?> storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getUuid = storageVolumeClazz.getMethod("getUuid");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isPrimary = storageVolumeClazz.getMethod("isPrimary");
            Object result = getVolumeList.invoke(mStorageManager);
            int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String uuid = (String) getUuid.invoke(storageVolumeElement);
                boolean primary = (boolean) isPrimary.invoke(storageVolumeElement);
                if ((primary && volumeId.equals(PRIMARY_VOLUME_NAME)) || uuid.equals(volumeId)) {
                    return (String) getPath.invoke(storageVolumeElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.R)
    private static String getVolumePathForAndroid11AndAbove(String volumeId, Context context) {
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            for (StorageVolume volume : storageManager.getStorageVolumes()) {
                if (volume.isPrimary() && volumeId.equals(PRIMARY_VOLUME_NAME)) {
                    return volume.getDirectory().getPath();
                } else if (volume.getUuid() != null && volume.getUuid().equals(volumeId)) {
                    return volume.getDirectory().getPath();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getVolumeIdFromTreeUri(Uri treeUri) {
        String docId = DocumentsContract.getTreeDocumentId(treeUri);
        String[] split = docId.split(":".replace(".", ""));
        return split[0];
    }

    private static String getDocumentPathFromTreeUri(Uri treeUri) {
        String docId = DocumentsContract.getTreeDocumentId(treeUri);
        String[] split = docId.split(":".replace(".", ""));
        return split.length > 1 ? split[1] : "";
    }
}
