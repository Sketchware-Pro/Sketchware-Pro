package com.besome.sketch.lib.base;

import android.content.Intent;
import android.os.Bundle;

import com.besome.sketch.acc.LoginActivity;
import com.besome.sketch.acc.ProfileActivity;

import a.a.a.FA;
import a.a.a.GA;
import a.a.a.GB;
import a.a.a.HA;
import a.a.a.IA;
import a.a.a.JA;
import a.a.a.KA;
import a.a.a.Sp;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.xB;
import a.a.a.zd;

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
            bB.a(getBaseContext(), xB.b().a(getApplicationContext(), 2131624932), 0).show();
        } else {
            k = requestCode;
            if (!super.i.a()) {
                l(9001);
            } else if (super.i.g().isEmpty()) {
                m(9002);
            } else {
                a(k, super.i.f());
            }
        }
    }

    public void j(int requestCode) {
        if (!Sp.a) {
            aB dialog = new aB(this);
            dialog.b(xB.b().a(getApplicationContext(), 2131624962));
            dialog.a(2131165391);
            dialog.a(xB.b().a(getApplicationContext(), 2131624960));
            dialog.b(xB.b().a(getApplicationContext(), 2131625010), new FA(this, requestCode, dialog));
            dialog.a(xB.b().a(getApplicationContext(), 2131624974), new GA(this, dialog));
            dialog.setOnDismissListener(new HA(this));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }

    public boolean j() {
        return zd.a(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && zd.a(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public void k(int requestCode) {
        if (!Sp.a) {
            aB dialog = new aB(this);
            dialog.b(xB.b().a(getApplicationContext(), 2131624962));
            dialog.a(2131165391);
            dialog.a(xB.b().a(getApplicationContext(), 2131624961));
            dialog.b(xB.b().a(getApplicationContext(), 2131625036), new IA(this, requestCode, dialog));
            dialog.a(xB.b().a(getApplicationContext(), 2131624974), new JA(this, dialog));
            dialog.setOnDismissListener(new KA(this));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Sp.a = true;
        }
    }

    public abstract void l();

    public final void l(int requestCode) {
        bB.a(getBaseContext(), xB.b().a(getApplicationContext(), 2131624951), 0).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, requestCode);
    }

    public abstract void m();

    public final void m(int requestCode) {
        bB.a(getBaseContext(), xB.b().a(getApplicationContext(), 2131624952), 0).show();
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 9001) {
            if (requestCode == 9002 && resultCode == -1) {
                a(k, super.i.f());
            }
        } else if (resultCode == -1) {
            if (super.i.g().isEmpty()) {
                m(9002);
            } else {
                a(k, super.i.f());
            }
        }

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
