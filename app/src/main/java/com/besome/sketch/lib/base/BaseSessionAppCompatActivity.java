//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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

    public boolean f(int var1) {
        boolean var2 = this.j();
        if (!var2) {
            this.j(var1);
        }

        return var2;
    }

    public abstract void g(int var1);

    public abstract void h(int var1);

    public void i(int var1) {
        if (!GB.h(this.getApplicationContext())) {
            bB.a(this.getBaseContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
        } else {
            this.k = var1;
            if (!super.i.a()) {
                this.l(9001);
            } else if (super.i.g().isEmpty()) {
                this.m(9002);
            } else {
                this.a(this.k, super.i.f());
            }
        }
    }

    public void j(int var1) {
        if (!Sp.a) {
            aB var2 = new aB(this);
            var2.b(xB.b().a(this.getApplicationContext(), 2131624962));
            var2.a(2131165391);
            var2.a(xB.b().a(this.getApplicationContext(), 2131624960));
            var2.b(xB.b().a(this.getApplicationContext(), 2131625010), new FA(this, var1, var2));
            var2.a(xB.b().a(this.getApplicationContext(), 2131624974), new GA(this, var2));
            var2.setOnDismissListener(new HA(this));
            var2.setCancelable(false);
            var2.setCanceledOnTouchOutside(false);
            var2.show();
            Sp.a = true;
        }
    }

    public boolean j() {
        return zd.a(this.getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && zd.a(this.getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public void k(int var1) {
        if (!Sp.a) {
            aB var2 = new aB(this);
            var2.b(xB.b().a(this.getApplicationContext(), 2131624962));
            var2.a(2131165391);
            var2.a(xB.b().a(this.getApplicationContext(), 2131624961));
            var2.b(xB.b().a(this.getApplicationContext(), 2131625036), new IA(this, var1, var2));
            var2.a(xB.b().a(this.getApplicationContext(), 2131624974), new JA(this, var2));
            var2.setOnDismissListener(new KA(this));
            var2.setCancelable(false);
            var2.setCanceledOnTouchOutside(false);
            var2.show();
            Sp.a = true;
        }
    }

    public abstract void l();

    public final void l(int var1) {
        bB.a(this.getBaseContext(), xB.b().a(this.getApplicationContext(), 2131624951), 0).show();
        Intent var2 = new Intent(this.getApplicationContext(), LoginActivity.class);
        var2.setFlags(536870912);
        this.startActivityForResult(var2, var1);
    }

    public abstract void m();

    public final void m(int var1) {
        bB.a(this.getBaseContext(), xB.b().a(this.getApplicationContext(), 2131624952), 0).show();
        Intent var2 = new Intent(this.getApplicationContext(), ProfileActivity.class);
        var2.setFlags(536870912);
        this.startActivityForResult(var2, var1);
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 != 9001) {
            if (var1 == 9002 && var2 == -1) {
                this.a(this.k, super.i.f());
            }
        } else if (var2 == -1) {
            if (super.i.g().isEmpty()) {
                this.m(9002);
            } else {
                this.a(this.k, super.i.f());
            }
        }

    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
    }

    public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
        super.onRequestPermissionsResult(var1, var2, var3);
        int var4 = var2.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(var2[var5])) {
                if (var3.length <= 0 || var3[0] != 0 || var3[1] != 0) {
                    this.k(var1);
                    break;
                }

                this.g(var1);
            }
        }

    }
}
