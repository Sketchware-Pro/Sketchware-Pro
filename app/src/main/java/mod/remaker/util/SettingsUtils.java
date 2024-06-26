package mod.remaker.util;

import static mod.remaker.util.SettingsContracts.RequestStorageManagerPermission;

import android.os.Build;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;

public class SettingsUtils {
    private SettingsUtils() {
    }

    public static ActivityResultContract<String, Boolean> getPermissionContract() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return new RequestStorageManagerPermission();
        } else {
            return new RequestPermission();
        }
    }
}
