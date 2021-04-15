package com.besome.sketch.lib.base;

import android.view.ViewGroup;

import a.a.a.Sp;
import a.a.a.aB;
import a.a.a.rA;
import a.a.a.sA;
import a.a.a.tA;
import a.a.a.uA;
import a.a.a.vA;
import a.a.a.wA;
import a.a.a.xB;
import a.a.a.zd;

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
            aB aBVar = new aB(this);
            aBVar.b(xB.b().a(getApplicationContext(), 2131624962));
            aBVar.a(2131165391);
            aBVar.a(xB.b().a(getApplicationContext(), 2131624960));
            aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new rA(this, i, aBVar));
            aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new sA(this, aBVar));
            aBVar.setOnDismissListener(new tA(this));
            aBVar.setCancelable(false);
            aBVar.setCanceledOnTouchOutside(false);
            aBVar.show();
            Sp.a = true;
        }
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public boolean j() {
        return zd.a(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && zd.a(getApplicationContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public abstract void l();

    public abstract void m();

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        BasePermissionAppCompatActivity.super.onRequestPermissionsResult(i, strArr, iArr);
        for (String str : strArr) {
            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(str)) {
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
            aB aBVar = new aB(this);
            aBVar.b(xB.b().a(getApplicationContext(), 2131624962));
            aBVar.a(2131165391);
            aBVar.a(xB.b().a(getApplicationContext(), 2131624961));
            aBVar.b(xB.b().a(getApplicationContext(), 2131625036), new uA(this, i, aBVar));
            aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new vA(this, aBVar));
            aBVar.setOnDismissListener(new wA(this));
            aBVar.setCancelable(false);
            aBVar.setCanceledOnTouchOutside(false);
            aBVar.show();
            Sp.a = true;
        }
    }

    public abstract Object a(ViewGroup viewGroup, int i);
}
