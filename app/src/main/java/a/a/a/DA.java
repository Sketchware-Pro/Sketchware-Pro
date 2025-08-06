package a.a.a;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import pro.sketchware.R;

public abstract class DA extends qA {
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
        return ContextCompat.checkSelfPermission(requireContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED;
    }

    public abstract void d();

    public void d(int var1) {
        if (!Sp.a) {
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(super.a);
            dialog.setTitle(xB.b().a(getContext(), R.string.common_message_permission_title_storage));
            dialog.setIcon(R.drawable.break_warning_96_red);
            dialog.setMessage(xB.b().a(getContext(), R.string.common_message_permission_storage));
            dialog.setPositiveButton(xB.b().a(getContext(), R.string.common_word_ok), (view, which) -> {
                if (!mB.a()) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
                            "android.permission.READ_EXTERNAL_STORAGE"}, var1);
                    view.dismiss();
                }
            });
            dialog.setNegativeButton(xB.b().a(getContext(), R.string.common_word_cancel), (view, which) -> {
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

    public abstract void e();

    public void e(int var1) {
        if (!Sp.a) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(super.a);
            builder.setTitle(xB.b().a(getContext(), R.string.common_message_permission_title_storage));
            builder.setIcon(R.drawable.break_warning_96_red);
            builder.setMessage(xB.b().a(getContext(), R.string.common_message_permission_storage1));
            builder.setPositiveButton(xB.b().a(getContext(), R.string.common_word_settings), (view, which) -> {
                if (!mB.a()) {
                    c(var1);
                    view.dismiss();
                }
            });
            builder.setNegativeButton(xB.b().a(getContext(), R.string.common_word_cancel), (view, which) -> {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        for (String permission : permissions) {
            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(permission)) {
                if (grantResults.length == 0 || grantResults[0] != 0 || grantResults[1] != 0) {
                    e(requestCode);
                    break;
                }
                b(requestCode);
            }
        }

    }
}
