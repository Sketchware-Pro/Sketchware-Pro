package com.besome.sketch.lib.base;

import android.Manifest;

import com.sketchware.remod.R;

import a.a.a.Sp;
import a.a.a.aB;
import a.a.a.mB;
import a.a.a.nd;
import a.a.a.xB;
import a.a.a.zd;
import mod.hey.studios.util.Helper;

public abstract class BasePermissionAppCompatActivity extends BaseAppCompatActivity {

    public boolean f(int i) {
        boolean j = j();
        if (!j) {
            i(i);
        }
        return j;
    }

    public abstract void g(int i);

    public abstract void h(int i);

    public void i(int i) {
        if (!Sp.a) {
            aB dialog = new aB(this);
            dialog.b(Helper.getResString(R.string.common_message_permission_title_storage));
            dialog.a(R.drawable.break_warning_96_red);
            dialog.a(Helper.getResString(R.string.common_message_permission_storage));
            dialog.b(Helper.getResString(R.string.common_word_ok), v -> {
                if (!mB.a()) {
                    nd.a(BasePermissionAppCompatActivity.this,
                            new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            },
                            i);
                    dialog.dismiss();
                }
            });
            dialog.a(Helper.getResString(R.string.common_word_cancel), v -> {
                l();
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
    public boolean j() {
        return zd.a(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0
                && zd.a(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == 0;
    }

    public abstract void l();

    public abstract void m();

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        BasePermissionAppCompatActivity.super.onRequestPermissionsResult(i, strArr, iArr);
        for (String str : strArr) {
            if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(str)) {
                if (iArr.length > 0 && iArr[0] == 0 && iArr[1] == 0) {
                    g(i);
                } else {
                    j(i);
                    return;
                }
            }
        }
    }

    public void j(int i) {
        if (!Sp.a) {
            aB dialog = new aB(this);
            dialog.b(Helper.getResString(R.string.common_message_permission_title_storage));
            dialog.a(R.drawable.break_warning_96_red);
            dialog.a(Helper.getResString(R.string.common_message_permission_storage1));
            dialog.b(Helper.getResString(R.string.common_word_settings), v -> {
                if (!mB.a()) {
                    h(i);
                    dialog.dismiss();
                }
            });
            dialog.a(Helper.getResString(R.string.common_word_cancel), v -> {
                m();
                dialog.dismiss();
            });
            dialog.setOnDismissListener(dialog1 -> Sp.a = false);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }
}
