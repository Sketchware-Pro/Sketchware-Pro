package a.a.a;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import pro.sketchware.R;

public abstract class DA extends qA {

    private int pendingRequestCode;

    private final ActivityResultLauncher<String[]> storagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                boolean allGranted = !permissions.isEmpty();
                for (Boolean granted : permissions.values()) {
                    if (granted == null || !granted) {
                        allGranted = false;
                        break;
                    }
                }
                if (allGranted) {
                    b(pendingRequestCode);
                } else {
                    e(pendingRequestCode);
                }
            });

    public DA() {
    }

    public boolean a(int var1) {
        boolean var2 = c();
        if (!var2) {
            d(var1);
        }

        return var2;
    }

    public abstract void b(int var1);

    public abstract void c(int var1);

    public boolean c() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public abstract void d();

    public void d(int var1) {
        if (!Sp.a) {
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(super.a);
            dialog.setTitle(R.string.common_message_permission_title_storage);
            dialog.setIcon(R.drawable.break_warning_96_red);
            dialog.setMessage(R.string.common_message_permission_storage);
            dialog.setPositiveButton(R.string.common_word_ok, (view, which) -> {
                if (!mB.a()) {
                    requestStoragePermission(var1);
                    view.dismiss();
                }
            });
            dialog.setNegativeButton(R.string.common_word_cancel, (view, which) -> {
                d();
                view.dismiss();
            });
            dialog.setOnDismissListener(dialog1 -> Sp.a = false);
            dialog.setCancelable(false);
            dialog.create().setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }

    public void requestStoragePermission(int requestCode) {
        pendingRequestCode = requestCode;
        storagePermissionLauncher.launch(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        });
    }

    public abstract void e();

    public void e(int var1) {
        if (!Sp.a) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(super.a);
            builder.setTitle(R.string.common_message_permission_title_storage);
            builder.setIcon(R.drawable.break_warning_96_red);
            builder.setMessage(R.string.common_message_permission_storage1);
            builder.setPositiveButton(R.string.common_word_settings, (view, which) -> {
                if (!mB.a()) {
                    c(var1);
                    view.dismiss();
                }
            });
            builder.setNegativeButton(R.string.common_word_cancel, (view, which) -> {
                e();
                view.dismiss();
            });
            builder.setOnDismissListener(dialog1 -> Sp.a = false);
            builder.setCancelable(false);

            var dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }
}
