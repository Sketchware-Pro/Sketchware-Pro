package mod.remaker.util;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts.OpenDocumentTree;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SettingsContracts {
    public static class PickDirectory extends OpenDocumentTree {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, @Nullable Uri uri) {
            return super.createIntent(context, uri)
                .addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION | FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    public static class RequestStorageManagerPermission extends ActivityResultContract<String, Boolean> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, @NonNull String input) {
            Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            return intent;
        }

        @NonNull
        @Override
        public Boolean parseResult(int resultCode, @Nullable Intent intent) {
            return Environment.isExternalStorageManager();
        }

        @Nullable
        @Override
        public SynchronousResult<Boolean> getSynchronousResult(@NonNull Context context, @NonNull String input) {
            return Environment.isExternalStorageManager() ? new SynchronousResult<>(true) : null;
        }
    }
}