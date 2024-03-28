//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a;

import androidx.core.content.ContextCompat;

public abstract class DA extends qA {
    public DA() {
    }

    public boolean a(int var1) {
        boolean var2 = this.c();
        if (!var2) {
            this.d(var1);
        }

        return var2;
    }

    public abstract void b(int var1);

    public abstract void c(int var1);

    public boolean c() {
        return ContextCompat.checkSelfPermission(this.getContext(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this.getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public abstract void d();

    public void d(int var1) {
        if (!Sp.a) {
            aB var2 = new aB(super.a);
            var2.b(xB.b().a(this.getContext(), 2131624962));
            var2.a(2131165391);
            var2.a(xB.b().a(this.getContext(), 2131624960));
            var2.b(xB.b().a(this.getContext(), 2131625010), new xA(this, var1, var2));
            var2.a(xB.b().a(this.getContext(), 2131624974), new yA(this, var2));
            var2.setOnDismissListener(new zA(this));
            var2.setCancelable(false);
            var2.setCanceledOnTouchOutside(false);
            var2.show();
            Sp.a = true;
        }
    }

    public abstract void e();

    public void e(int var1) {
        if (!Sp.a) {
            aB var2 = new aB(super.a);
            var2.b(xB.b().a(this.getContext(), 2131624962));
            var2.a(2131165391);
            var2.a(xB.b().a(this.getContext(), 2131624961));
            var2.b(xB.b().a(this.getContext(), 2131625036), new AA(this, var1, var2));
            var2.a(xB.b().a(this.getContext(), 2131624974), new BA(this, var2));
            var2.setOnDismissListener(new CA(this));
            var2.setCancelable(false);
            var2.setCanceledOnTouchOutside(false);
            var2.show();
            Sp.a = true;
        }
    }

    public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
        int var4 = var2.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            if ("android.permission.WRITE_EXTERNAL_STORAGE".equals(var2[var5])) {
                if (var3.length <= 0 || var3[0] != 0 || var3[1] != 0) {
                    this.e(var1);
                    break;
                }

                this.b(var1);
            }
        }

    }
}
