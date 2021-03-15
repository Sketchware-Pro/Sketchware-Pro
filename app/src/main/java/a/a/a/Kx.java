package a.a.a;

// $FF: synthetic class
public class Kx {
    // $FF: synthetic field
    public static final int[] a = new int[Lx.a.values().length];

    static {
        try {
            a[Lx.a.a.ordinal()] = 1;
        } catch (NoSuchFieldError ignored) {
        }

        try {
            a[Lx.a.b.ordinal()] = 2;
        } catch (NoSuchFieldError ignored) {
        }

        try {
            a[Lx.a.c.ordinal()] = 3;
        } catch (NoSuchFieldError ignored) {
        }

    }
}
