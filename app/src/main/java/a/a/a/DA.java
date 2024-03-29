package a.a.a;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.sketchware.remod.R;

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
        return ContextCompat.checkSelfPermission(getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0
                && ContextCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public abstract void d();

    public void d(int var1) {
        if (!Sp.a) {
            aB dialog = new aB(super.a);
            dialog.b(xB.b().a(getContext(), R.string.common_message_permission_title_storage));
            dialog.a(R.drawable.break_warning_96_red);
            dialog.a(xB.b().a(getContext(), R.string.common_message_permission_storage));
            dialog.b(xB.b().a(getContext(), R.string.common_word_ok), view -> {
                if (!mB.a()) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
                            "android.permission.READ_EXTERNAL_STORAGE"}, var1);
                    dialog.dismiss();
                }
            });
            dialog.a(xB.b().a(getContext(), R.string.common_word_cancel), view -> {
                d();
                dialog.dismiss();
            });
            dialog.setOnDismissListener(dialog1 -> Sp.a = false);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }

    public abstract void e();

    public void e(int var1) {
        if (!Sp.a) {
            aB dialog = new aB(super.a);
            dialog.b(xB.b().a(getContext(), R.string.common_message_permission_title_storage));
            dialog.a(R.drawable.break_warning_96_red);
            dialog.a(xB.b().a(getContext(), R.string.common_message_permission_storage1));
            dialog.b(xB.b().a(getContext(), R.string.common_word_settings), view -> {
                if (!mB.a()) {
                    c(var1);
                    dialog.dismiss();
                }
            });
            dialog.a(xB.b().a(getContext(), R.string.common_word_cancel), view -> {
                e();
                dialog.dismiss();
            });
            dialog.setOnDismissListener(dialog1 -> Sp.a = false);
            dialog.setCancelable(false);
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
