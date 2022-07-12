package com.besome.sketch.lib.base;

import android.content.Intent;
import android.os.Bundle;

import a.a.a.GB;
import a.a.a.Sp;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.nd;
import a.a.a.xB;
import a.a.a.zd;
import mod.hey.studios.util.Helper;

public abstract class BaseSessionAppCompatActivity extends BaseAppCompatActivity {
    public final int l = 9001;
    public final int m = 9002;
    public int k = 0;

    public BaseSessionAppCompatActivity() {
    }

    public abstract void a(int var1, String var2);

    public boolean f(int requestCode) {
        boolean readWriteAllowed = j();
        if (!readWriteAllowed) j(requestCode);
        return readWriteAllowed;
    }

    public abstract void g(int requestCode);

    public abstract void h(int var1);

    public void i(int requestCode) {
        if (!GB.h(getApplicationContext())) {
            bB.a(getBaseContext(), Helper.getResString(2131624932), 0).show();
        } else {
            k = requestCode;
            a(k, super.i.f());
        }
    }

    public void j(int requestCode) {
        if (!Sp.a) {
            aB dialog = new aB(this);
            dialog.b(Helper.getResString(2131624962));
            dialog.a(2131165391);
            dialog.a(Helper.getResString(2131624960));
            dialog.b(Helper.getResString(2131625010), view -> {
                if (!mB.a()) {
                    nd.a(BaseSessionAppCompatActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, requestCode);
                    dialog.dismiss();
                }
            });
            dialog.a(Helper.getResString(2131624974), view -> {
                l();
                dialog.dismiss();
            });
            dialog.setOnDismissListener(dialogInterface -> Sp.a = false);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }

    @Override
    public boolean j() {
        return zd.a(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && zd.a(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public void k(int requestCode) {
        if (!Sp.a) {
            aB dialog = new aB(this);
            dialog.b(Helper.getResString(2131624962));
            dialog.a(2131165391);
            dialog.a(Helper.getResString(2131624961));
            dialog.b(Helper.getResString(2131625036), view -> {
                if (!mB.a()) {
                    h(requestCode);
                    dialog.dismiss();
                }
            });
            dialog.a(Helper.getResString(2131624974), view -> {
                m();
                dialog.dismiss();
            });
            dialog.setOnDismissListener(dialogInterface -> Sp.a = false);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }

    public abstract void l();

    public abstract void m();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 9001) {
            if (requestCode == 9002 && resultCode == -1) {
                a(k, super.i.f());
            }
        } else if (resultCode == -1) {
            a(k, super.i.f());
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permission : permissions) {
            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(permission)) {
                if (grantResults.length <= 0 || grantResults[0] != 0 || grantResults[1] != 0) {
                    k(requestCode);
                    break;
                }
                g(requestCode);
            }
        }

    }
}
