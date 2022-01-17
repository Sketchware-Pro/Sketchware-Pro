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
        hC b2;
        synchronized (jC.class) {
            b2 = b(str, true);
        }
        return b2;
    }

    public static synchronized iC c(String str) {
        iC c2;
        synchronized (jC.class) {
            c2 = c(str, true);
        }
        return c2;
    }

    public static synchronized kC d(String str) {
        kC d2;
        synchronized (jC.class) {
            d2 = d(str, true);
        }
        return d2;
    }

    public static synchronized hC b(String str, boolean z) {
        hC hCVar;
        synchronized (jC.class) {
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
            hCVar = b;
        }
        return hCVar;
    }

    public static synchronized iC c(String str, boolean z) {
        iC iCVar;
        synchronized (jC.class) {
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
            iCVar = d;
        }
        return iCVar;
    }

    public static synchronized kC d(String str, boolean z) {
        kC kCVar;
        synchronized (jC.class) {
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
            kCVar = c;
        }
        return kCVar;
    }

    public static synchronized eC a(String str) {
        eC a2;
        synchronized (jC.class) {
            a2 = a(str, true);
        }
        return a2;
    }

    public static synchronized eC a(String str, boolean z) {
        eC eCVar;
        synchronized (jC.class) {
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
            eCVar = a;
        }
        return eCVar;
    }
}