package a.a.a;

public class jC {

    public static eC a;
    public static hC b;
    public static kC c;
    public static iC d;

    public static void a() {
        a = null;
        b = null;
        c = null;
        d = null;
    }

    public static void b() {
        a.i();
        a = null;
    }

    public static void c() {
        b.k();
        b = null;
    }

    public static void d() {
        d.j();
        d = null;
    }

    public static void e() {
        c.t();
        c = null;
    }

    public static synchronized hC b(String str) {
        return b(str, true);
    }

    public static synchronized iC c(String str) {
        return c(str, true);
    }

    public static synchronized kC d(String str) {
        return d(str, true);
    }

    public static synchronized hC b(String str, boolean z) {
        if (b != null && !str.equals(b.e)) {
            c();
        }
        if (b == null) {
            b = new hC(str);
            if (!z) {
                b.i();
            } else if (b.g()) {
                b.h();
            } else {
                b.i();
            }
        }
        return b;
    }

    public static synchronized iC c(String str, boolean z) {
        if (d != null && !str.equals(d.a)) {
            d();
        }
        if (d == null) {
            d = new iC(str);
            if (!z) {
                d.i();
            } else if (d.g()) {
                d.h();
            } else {
                d.i();
            }
        }
        return d;
    }

    public static synchronized kC d(String str, boolean z) {
        if (c != null && !str.equals(c.i)) {
            e();
        }
        if (c == null) {
            c = new kC(str);
            if (!z) {
                c.s();
            } else if (c.q()) {
                c.r();
            } else {
                c.s();
            }
        }
        return c;
    }

    public static synchronized eC a(String str) {
        return a(str, true);
    }

    public static synchronized eC a(String str, boolean z) {
        if (a != null && !str.equals(a.a)) {
            b();
        }
        if (a == null) {
            a = new eC(str);
            if (!z) {
                a.g();
                a.e();
            } else {
                if (a.d()) {
                    a.h();
                } else {
                    a.g();
                }
                if (a.c()) {
                    a.f();
                } else {
                    a.e();
                }
            }
        }
        return a;
    }
}
